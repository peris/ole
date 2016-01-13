package org.kuali.ole.spring.batch.processor;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.solr.common.SolrDocument;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.kuali.ole.docstore.common.constants.DocstoreConstants;
import org.kuali.ole.oleng.batch.profile.model.BatchProcessProfile;
import org.kuali.ole.oleng.batch.profile.model.BatchProfileAddOrOverlay;
import org.kuali.ole.oleng.batch.profile.model.BatchProfileDataMapping;
import org.kuali.ole.oleng.batch.profile.model.BatchProfileMatchPoint;
import org.kuali.ole.oleng.describe.processor.bibimport.MatchPointProcessor;
import org.kuali.ole.utility.OleDsNgRestClient;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.marc4j.marc.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by SheikS on 12/9/2015.
 */
@Service("batchBibFileProcessor")
public class BatchBibFileProcessor extends BatchFileProcessor {
    private Map<String, String> operationIndMap;
    private Map<String, String> matchOptionIndMap;
    private Map<String, String> dataTypeIndMap;
    private static final Logger LOG = Logger.getLogger(BatchBibFileProcessor.class);

    @Autowired
    private MatchPointProcessor matchPointProcessor;

    @Override
    public String processRecords(List<Record> records, BatchProcessProfile batchProcessProfile) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        String response = "";
        for (Iterator<Record> iterator = records.iterator(); iterator.hasNext(); ) {
            JSONObject jsonObject = null;

            Record marcRecord = iterator.next();
            if (!batchProcessProfile.getBatchProfileMatchPointList().isEmpty()) {
                String query = getMatchPointProcessor().prepareSolrQueryMapForMatchPoint(marcRecord, batchProcessProfile.getBatchProfileMatchPointList());

                if (StringUtils.isNotBlank(query)) {
                    List results = getSolrRequestReponseHandler().getSolrDocumentList(query);
                    if (null == results || results.size() > 1) {
                        System.out.println("**** More than one record found for query : " + query);
                        return null;
                    }

                    if (null != results && results.size() == 1) {
                        SolrDocument solrDocument = (SolrDocument) results.get(0);
                        String bibId = (String) solrDocument.getFieldValue("LocalId_display");
                        jsonObject = prepareRequest(bibId, marcRecord, batchProcessProfile);
                    }

                    if (null != jsonObject) {
                        jsonArray.put(jsonObject);
                    }
                }
            } else {
                jsonObject = prepareRequest(null, marcRecord, batchProcessProfile);
            }
            jsonArray.put(jsonObject);
        }

        if (jsonArray.length() > 0) {
            response = getOleDsNgRestClient().postData(OleDsNgRestClient.Service.PROCESS_BIB_HOLDING_ITEM, jsonArray, OleDsNgRestClient.Format.JSON);
        }
        return response;
    }

    private String getOperationInd(String operation) {
        return getOperationIndMap().get(operation);
    }

    private String getMatchOptionInd(String matchOption) {
        return getMatchOptionIndMap().get(matchOption);
    }

    private String getDataTypeInd(String dataType) {
        return getDataTypeIndMap().get(dataType);
    }

    private JSONObject prepareRequest(String bibId, Record marcRecord, BatchProcessProfile batchProcessProfile) throws JSONException {
        LOG.info("Preparing JSON Request for Bib/Holdings/Items");

        JSONObject bibData = new JSONObject();
        String unmodifiedRecord = getMarcXMLConverter().generateMARCXMLContent(marcRecord);
        String updatedUserName = getUpdatedUserName();
        String updatedDate = DocstoreConstants.DOCSTORE_DATE_FORMAT.format(new Date());

        if (null != bibId) {
            bibData.put("id", bibId);
        }

        bibData.put("updatedBy", updatedUserName);
        bibData.put("updatedDate", updatedDate);
        bibData.put("unmodifiedContent", unmodifiedRecord);
        bibData.put("ops", getOverlayOps(batchProcessProfile));

        // Prepare data mapping before MARC Transformation
        Map<String, JSONObject> dataMappingsMapPreTransformation = prepareDataMapping(marcRecord, batchProcessProfile, "Pre Marc Transformation");


        //Transformations pertaining to MARC record (001,003,035$a etc..)
        handleBatchProfileTransformations(marcRecord, batchProcessProfile);
        String modifiedRecord = getMarcXMLConverter().generateMARCXMLContent(marcRecord);
        bibData.put("modifiedContent", modifiedRecord);

        // Prepare data mapping after MARC Transformation
        Map<String, JSONObject> dataMappingsMapPostTransformations = prepareDataMapping(marcRecord, batchProcessProfile, "Post Marc Transformation");


        JSONObject bibDataMappingsPreTrans = dataMappingsMapPreTransformation.get("bibDataMappings");
        JSONObject bibDataMappingsPostTrans = dataMappingsMapPostTransformations.get("bibDataMappings");
        bibData.put("dataMapping", buildOneObject(bibDataMappingsPreTrans, bibDataMappingsPostTrans));

        JSONObject holdingsData = prepareMatchPointsForHoldings(batchProcessProfile);
        JSONObject holdingsDataMappingsPreTrans = dataMappingsMapPreTransformation.get("holdingsDataMappings");
        JSONObject holdingsDataMappingsPostTrans = dataMappingsMapPostTransformations.get("holdingsDataMappings");
        holdingsData.put("dataMapping", buildOneObject(holdingsDataMappingsPreTrans, holdingsDataMappingsPostTrans));
        bibData.put("holdings", holdingsData);

        JSONObject eholdingsData = prepareMatchPointsForEHoldings(batchProcessProfile);
        JSONObject eholdingsDataMappingsPreTrans = dataMappingsMapPreTransformation.get("eHoldingsDataMappings");
        JSONObject eholdingsDataMappingsPostTrans = dataMappingsMapPostTransformations.get("eHoldingsDataMappings");
        eholdingsData.put("dataMapping", buildOneObject(eholdingsDataMappingsPreTrans, eholdingsDataMappingsPostTrans));
        bibData.put("eholdings", eholdingsData);

        JSONObject itemData = prepareMatchPointsForItem(batchProcessProfile);
        JSONObject itemsDataMappingsPreTrans = dataMappingsMapPreTransformation.get("itemDataMappings");
        JSONObject itemsDataMappingsPostTrans = dataMappingsMapPostTransformations.get("itemDataMappings");
        itemData.put("dataMapping", buildOneObject(itemsDataMappingsPreTrans, itemsDataMappingsPostTrans));
        bibData.put("items", itemData);

        return bibData;
    }

    private JSONObject buildOneObject(JSONObject bibDataMappingsPreTrans, JSONObject bibDataMappingsPostTrans) {
        JSONObject finalObject = new JSONObject();

        for (Iterator iterator = bibDataMappingsPreTrans.keys(); iterator.hasNext(); ) {
            String key = (String) iterator.next();
            try {
                finalObject.put(key, bibDataMappingsPreTrans.get(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (Iterator iterator = bibDataMappingsPostTrans.keys(); iterator.hasNext(); ) {
            String key = (String) iterator.next();
            try {
                finalObject.put(key, bibDataMappingsPostTrans.get(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return finalObject;
    }

    private Map<String, JSONObject> prepareDataMapping(Record marcRecord, BatchProcessProfile batchProcessProfile, String transformationOption) throws JSONException {
        Map<String, JSONObject> dataMappings = new HashMap<>();
        dataMappings.put("bibDataMappings", prepareDataMappings(marcRecord, batchProcessProfile, "bibliographic", transformationOption));
        dataMappings.put("holdingsDataMappings", prepareDataMappings(marcRecord, batchProcessProfile, "holdings", transformationOption));
        dataMappings.put("itemDataMappings", prepareDataMappings(marcRecord, batchProcessProfile, "item", transformationOption));
        dataMappings.put("eHoldingsDataMappings", prepareDataMappings(marcRecord, batchProcessProfile, "eholdings", transformationOption));

        return dataMappings;
    }

    public List getOverlayOps(BatchProcessProfile batchProcessProfile) {
        List addOverlayOps = new ArrayList();

        List<BatchProfileAddOrOverlay> batchProfileAddOrOverlayList = batchProcessProfile.getBatchProfileAddOrOverlayList();
        for (Iterator<BatchProfileAddOrOverlay> iterator = batchProfileAddOrOverlayList.iterator(); iterator.hasNext(); ) {
            BatchProfileAddOrOverlay batchProfileAddOrOverlay = iterator.next();
            String dataType = batchProfileAddOrOverlay.getDataType();
            String matchOption = batchProfileAddOrOverlay.getMatchOption();
            String operation = batchProfileAddOrOverlay.getOperation();

            String matchOptionInd = getMatchOptionInd(matchOption);
            String dataTypeInd = getDataTypeInd(dataType);
            String operationInd = getOperationInd(operation);
            addOverlayOps.add(matchOptionInd + dataTypeInd + operationInd);
        }
        return addOverlayOps;
    }

    private JSONObject prepareMatchPointsForItem(BatchProcessProfile batchProcessProfile) throws JSONException {
        JSONObject itemData = new JSONObject();
        JSONObject itemMatchPoints = prepareMatchPointsForDocType(batchProcessProfile.getBatchProfileMatchPointList(), "item");
        if (itemMatchPoints.length() > 0) {
            itemData.put("matchPoints", itemMatchPoints);
        }

        return itemData;
    }


    private JSONObject prepareMatchPointsForHoldings(BatchProcessProfile batchProcessProfile) throws JSONException {
        JSONObject holdingsData = new JSONObject();
        JSONObject holdingsMatchPoints = prepareMatchPointsForDocType(batchProcessProfile.getBatchProfileMatchPointList(), "holdings");
        if (holdingsMatchPoints.length() > 0) {
            holdingsData.put("matchPoints", holdingsMatchPoints);
        }
        return holdingsData;
    }

    private JSONObject prepareMatchPointsForEHoldings(BatchProcessProfile batchProcessProfile) throws JSONException {
        JSONObject holdingsData = new JSONObject();
        JSONObject holdingsMatchPoints = prepareMatchPointsForDocType(batchProcessProfile.getBatchProfileMatchPointList(), "eholdings");
        if (holdingsMatchPoints.length() > 0) {
            holdingsData.put("matchPoints", holdingsMatchPoints);
        }
        return holdingsData;
    }

    public JSONObject prepareDataMappings(Record marcRecord, BatchProcessProfile batchProcessProfile, String docType, String transformationOption) throws JSONException {
        JSONObject bibDataMappings = new JSONObject();
        Map<String, List<ValueByPriority>> valueByPriorityMap = new HashedMap();
        List<BatchProfileDataMapping> filteredDataMappings = filterDataMappingsByTransformationOption(batchProcessProfile.getBatchProfileDataMappingList(), transformationOption);

        sortDataMappings(filteredDataMappings);

        for (Iterator<BatchProfileDataMapping> iterator = filteredDataMappings.iterator(); iterator.hasNext(); ) {
            BatchProfileDataMapping batchProfileDataMapping = iterator.next();
            String destination = batchProfileDataMapping.getDestination();
            if (destination.equalsIgnoreCase(docType)) {
                String destinationField = batchProfileDataMapping.getField();
                String constantValue = "";
                String marcValue = "";

                constantValue = batchProfileDataMapping.getConstant();

                if (batchProfileDataMapping.getDataType().equalsIgnoreCase("Bib Marc")) {
                    String dataField = batchProfileDataMapping.getDataField();
                    if (StringUtils.isNotBlank(dataField)) {
                        if (getMarcRecordUtil().isControlField(dataField)) {
                            marcValue = getMarcRecordUtil().getControlFieldValue(marcRecord, dataField);
                        } else {
                            marcValue = getMarcRecordUtil().getDataFieldValue(marcRecord, dataField, batchProfileDataMapping.getSubField());
                        }
                    }
                }

                int priority = batchProfileDataMapping.getPriority();

                List<ValueByPriority> valueByPriorities;

                ValueByPriority valueByPriority = new ValueByPriority();
                valueByPriority.setField(destinationField);
                valueByPriority.setPriority(priority);
                String value = StringUtils.isBlank(marcValue) ? constantValue : marcValue;
                valueByPriority.addValues(value);

                if (valueByPriorityMap.containsKey(destinationField)) {
                    valueByPriorities = valueByPriorityMap.get(destinationField);

                    if(valueByPriorities.contains(valueByPriority)){
                        ValueByPriority existingValuePriority = valueByPriorities.get(valueByPriorities.indexOf(valueByPriority));
                        existingValuePriority.addValues(value);
                    } else {
                        valueByPriorities.add(valueByPriority);
                    }
                } else {
                    valueByPriorities = new ArrayList<>();
                    valueByPriorities.add(valueByPriority);
                }
                valueByPriorityMap.put(destinationField, valueByPriorities);
            }
        }

        for (Iterator<String> iterator = valueByPriorityMap.keySet().iterator(); iterator.hasNext(); ) {
            String destField =  iterator.next();
            StringBuilder finalValue = new StringBuilder();
            List<ValueByPriority> vals = valueByPriorityMap.get(destField);
            for (Iterator<ValueByPriority> iterator1 = vals.iterator(); iterator1.hasNext(); ) {
                ValueByPriority valueByPriority = iterator1.next();
                List<String> values = valueByPriority.getValues();
                if(CollectionUtils.isNotEmpty(values)){
                    for (Iterator<String> stringIterator = values.iterator(); stringIterator.hasNext(); ) {
                        String value = stringIterator.next();
                        finalValue.append(value);
                        if(stringIterator.hasNext()){
                            finalValue.append(" ");
                        }
                    }
                    break;
                }
            }
            bibDataMappings.put(destField, finalValue.toString());

        }

        return bibDataMappings;
    }


    private List<BatchProfileDataMapping> filterDataMappingsByTransformationOption(List<BatchProfileDataMapping> batchProfileDataMappingList, String transformationOption) {
        List<BatchProfileDataMapping> batchProfileDataMappings = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(batchProfileDataMappingList)) {
            for (Iterator<BatchProfileDataMapping> iterator = batchProfileDataMappingList.iterator(); iterator.hasNext(); ) {
                BatchProfileDataMapping batchProfileDataMapping = iterator.next();
                if (batchProfileDataMapping.getTransferOption().equalsIgnoreCase(transformationOption)) {
                    batchProfileDataMappings.add(batchProfileDataMapping);
                }
            }
        }
        return batchProfileDataMappings;
    }

    private void sortDataMappings(List<BatchProfileDataMapping> filteredDataMappings) {
        Collections.sort(filteredDataMappings, new Comparator<BatchProfileDataMapping>() {
            public int compare(BatchProfileDataMapping dataMapping1, BatchProfileDataMapping dataMapping2) {
                int priorityForDataMapping1 = dataMapping1.getPriority();
                int priorityForDataMapping2 = dataMapping2.getPriority();
                return new Integer(priorityForDataMapping1).compareTo(new Integer(priorityForDataMapping2));
            }
        });
    }

    private JSONObject prepareMatchPointsForDocType(List<BatchProfileMatchPoint> batchProfileMatchPoints, String docType) throws JSONException {
        JSONObject matchPoints = new JSONObject();
        if (CollectionUtils.isNotEmpty(batchProfileMatchPoints)) {
            for (Iterator<BatchProfileMatchPoint> iterator = batchProfileMatchPoints.iterator(); iterator.hasNext(); ) {
                BatchProfileMatchPoint batchProfileMatchPoint = iterator.next();
                if (batchProfileMatchPoint.getDataType().equalsIgnoreCase(docType)) {
                    String matchPoint = batchProfileMatchPoint.getMatchPointType();
                    String newValue;
                    if (matchPoints.has(matchPoint)) {
                        newValue = matchPoints.getString(matchPoint) + "," + getMatchPointValue(batchProfileMatchPoint);
                    } else {
                        newValue = getMatchPointValue(batchProfileMatchPoint);
                    }
                    matchPoints.put(matchPoint, newValue);
                }
            }
        }
        return matchPoints;
    }

    private String getMatchPointValue(BatchProfileMatchPoint batchProfileMatchPoint) {
        String matchPointValue = batchProfileMatchPoint.getMatchPointValue();
        if (StringUtils.isNotBlank(matchPointValue)) {
            return matchPointValue;
        }

        return batchProfileMatchPoint.getConstant();
    }

    public Map<String, String> getDataMappingMap(List<BatchProfileDataMapping> batchProfileDataMappingList) {
        Map<String, String> dataMappingMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(batchProfileDataMappingList)) {
            for (Iterator<BatchProfileDataMapping> iterator = batchProfileDataMappingList.iterator(); iterator.hasNext(); ) {
                BatchProfileDataMapping batchProfileDataMapping = iterator.next();
                String mapKey = batchProfileDataMapping.getDestination() + "-"
                        + batchProfileDataMapping.getField();
                String value = batchProfileDataMapping.getDataField() + " $" + batchProfileDataMapping.getSubField();
                if (dataMappingMap.containsKey(mapKey)) {
                    value = dataMappingMap.get(mapKey);
                    value = value + "$" + batchProfileDataMapping.getSubField();
                }
                dataMappingMap.put(mapKey, value);
            }
        }
        return dataMappingMap;
    }


    private void handleBatchProfileTransformations(Record record, BatchProcessProfile batchProcessProfile) {
        new StepsProcessor().processSteps(record, batchProcessProfile);
    }

    public Map<String, String> getOperationIndMap() {
        if (null == operationIndMap) {
            operationIndMap = new HashedMap();
            operationIndMap.put("Add", "1");
            operationIndMap.put("Overlay", "2");
            operationIndMap.put("Discard", "3");
        }
        return operationIndMap;
    }

    public void setOperationIndMap(Map<String, String> operationIndMap) {
        this.operationIndMap = operationIndMap;
    }

    public Map<String, String> getMatchOptionIndMap() {
        if (null == matchOptionIndMap) {
            matchOptionIndMap = new HashedMap();
            matchOptionIndMap.put("If Match Found", "1");
            matchOptionIndMap.put("If Match Not Found", "2");
        }
        return matchOptionIndMap;
    }

    public void setMatchOptionIndMap(Map<String, String> matchOptionIndMap) {
        this.matchOptionIndMap = matchOptionIndMap;
    }

    public Map<String, String> getDataTypeIndMap() {
        if (null == dataTypeIndMap) {
            dataTypeIndMap = new HashedMap();
            dataTypeIndMap.put("Bibliographic", "1");
            dataTypeIndMap.put("Holdings", "2");
            dataTypeIndMap.put("Item", "3");
            dataTypeIndMap.put("EHoldings", "4");
        }
        return dataTypeIndMap;
    }

    public void setDataTypeIndMap(Map<String, String> dataTypeIndMap) {
        this.dataTypeIndMap = dataTypeIndMap;
    }

    public MatchPointProcessor getMatchPointProcessor() {
        return matchPointProcessor;
    }

    public void setMatchPointProcessor(MatchPointProcessor matchPointProcessor) {
        this.matchPointProcessor = matchPointProcessor;
    }

    @Override
    public String getReportingFilePath() {
        return ConfigContext.getCurrentContextConfig().getProperty("batch.bibImport.directory");
    }


    private class ValueByPriority {
        private int priority;
        private String field;
        private List<String> values;

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public List<String> getValues() {
            if (null == values) {
                values = new ArrayList<>();
            }
            return values;
        }

        public void setValues(List<String> values) {
            this.values = values;
        }

        public void addValues(String value) {
            if (null != value) {
                getValues().add(value);
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ValueByPriority that = (ValueByPriority) o;

            if (priority != that.priority) return false;
            return field != null ? field.equals(that.field) : that.field == null;

        }

        @Override
        public int hashCode() {
            int result = priority;
            result = 31 * result + (field != null ? field.hashCode() : 0);
            return result;
        }
    }

}