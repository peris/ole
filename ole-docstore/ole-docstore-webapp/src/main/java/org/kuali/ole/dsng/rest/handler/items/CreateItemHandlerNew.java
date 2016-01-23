package org.kuali.ole.dsng.rest.handler.items;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.kuali.ole.DocumentUniqueIDPrefix;
import org.kuali.ole.constants.OleNGConstants;
import org.kuali.ole.docstore.common.document.PHoldings;
import org.kuali.ole.docstore.engine.service.storage.rdbms.pojo.HoldingsRecord;
import org.kuali.ole.docstore.engine.service.storage.rdbms.pojo.ItemRecord;
import org.kuali.ole.dsng.rest.Exchange;
import org.kuali.ole.dsng.rest.handler.Handler;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by SheikS on 12/26/2015.
 */
public class CreateItemHandlerNew extends Handler {

    @Override
    public Boolean isInterested(String operation) {
        List<String> operationsList = getListFromJSONArray(operation);
        for (Iterator iterator = operationsList.iterator(); iterator.hasNext(); ) {
            String op = (String) iterator.next();
            if (op.equals("131") || op.equals("231")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void process(JSONObject requestJsonObject, Exchange exchange) {
        JSONObject itemJSONObject;
        List<ItemRecord> itemRecords = null;
        try {
            itemJSONObject = requestJsonObject.getJSONObject(OleNGConstants.ITEM);
            itemRecords = (List<ItemRecord>) exchange.get(OleNGConstants.ITEM);
            for (Iterator<ItemRecord> iterator = itemRecords.iterator(); iterator.hasNext(); ) {
                ItemRecord itemRecord = iterator.next();
                itemRecord.setHoldingsId(itemRecord.getHoldingsRecord().getHoldingsId());
                exchange.add(OleNGConstants.ITEM_RECORD, itemRecord);
                processDataMappings(itemJSONObject, exchange);
                setCommonValuesToItemRecord(requestJsonObject, itemRecord);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        getItemDAO().saveAll(itemRecords);


    }

    private void setCommonValuesToItemRecord(JSONObject requestJsonObject, ItemRecord itemRecord) {
        String createdDateString = getStringValueFromJsonObject(requestJsonObject, OleNGConstants.UPDATED_DATE);
        Timestamp createdDate = getDateTimeStamp(createdDateString);
        String createdBy = getStringValueFromJsonObject(requestJsonObject, OleNGConstants.UPDATED_BY);
        itemRecord.setCreatedBy(createdBy);
        itemRecord.setCreatedDate(createdDate);
        itemRecord.setUniqueIdPrefix(DocumentUniqueIDPrefix.PREFIX_WORK_ITEM_OLEML);
    }

    @Override
    public List<Handler> getMetaDataHandlers() {
        if (null == metaDataHandlers) {
            metaDataHandlers = new ArrayList<Handler>();
            metaDataHandlers.add(new CallNumberHandler());
            metaDataHandlers.add(new CallNumberPrefixHandler());
            metaDataHandlers.add(new CallNumberTypeHandler());
            metaDataHandlers.add(new ChronologyHandler());
            metaDataHandlers.add(new CopyNumberHandler());
            metaDataHandlers.add(new DonorCodeHandler());
            metaDataHandlers.add(new DonorNoteHandler());
            metaDataHandlers.add(new DonorPublicDisplayHandler());
            metaDataHandlers.add(new EnumerationHandler());
            metaDataHandlers.add(new ItemHoldingLocationHandler());
            metaDataHandlers.add(new ItemBarcodeHandler());
            metaDataHandlers.add(new ItemStatusHandler());
            metaDataHandlers.add(new ItemTypeHandler());
            metaDataHandlers.add(new ItemLocationHandler());
            metaDataHandlers.add(new StatisticalSearchCodeHandler());
            metaDataHandlers.add(new VendorLineItemIdHandler());
        }
        return metaDataHandlers;
    }
}
