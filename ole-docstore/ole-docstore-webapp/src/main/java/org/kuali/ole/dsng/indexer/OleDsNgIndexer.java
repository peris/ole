package org.kuali.ole.dsng.indexer;

import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.SolrInputField;
import org.kuali.incubator.SolrRequestReponseHandler;
import org.kuali.ole.docstore.common.constants.DocstoreConstants;
import org.kuali.ole.docstore.common.exception.DocstoreIndexException;
import org.kuali.ole.dsng.util.OleDsHelperUtil;

import java.util.*;

/**
 * Created by SheikS on 11/26/2015.
 */
public abstract class OleDsNgIndexer  implements DocstoreConstants {

    private SolrRequestReponseHandler solrRequestReponseHandler;
    public static final String ID_FIELD_PREFIX = "id_disc_";
    private OleDsHelperUtil oleDsHelperUtil;

    public abstract void indexDocument(Object object);

    public abstract void updateDocument(Object object);

    public abstract List<SolrInputDocument> buildSolrInputDocument(Object object);

    protected void assignUUIDs(List<SolrInputDocument> solrDocs, List<String> uuids) throws Exception {
        if ((null == solrDocs) || (solrDocs.size() == 0)) {
            return;
        }
        if ((null != uuids) && (uuids.size() < solrDocs.size())) {
            throw new Exception(
                    "Insufficient UUIDs(" + uuids.size() + ") specified for documents(" + solrDocs.size() + ".");
        }
        for (int i = 0; i < solrDocs.size(); i++) {
            SolrInputDocument solrInputDocument = solrDocs.get(i);
            SolrInputField idField = solrInputDocument.getField("id");
            String uuid = null;
            if (null != uuids) {
                // Get the supplied UUID.
                uuid = uuids.get(i);
            }
            if (null == idField) {
                if (null == uuid) {
                    // Generate UUID.
                    uuid = UUID.randomUUID().toString();
                    uuid = ID_FIELD_PREFIX + uuid; // identifies the uuid generated by discovery module.
                }
                solrInputDocument.addField(ID, uuid);
                solrInputDocument.addField(UNIQUE_ID, uuid);
            } else {
                if (null != uuid) {
                    // Use the supplied UUID.
                    solrInputDocument.setField(ID, uuid);
                    solrInputDocument.setField(UNIQUE_ID, uuid);
                } else {

                    if (idField.getValue() instanceof List) {
                        List<String> uuidList = (List<String>) idField.getValue();
                        uuid = uuidList.get(0);
                    } else if (idField.getValue() instanceof String) {
                        uuid = (String) idField.getValue();
                    }

                    if (null == uuid) {
                        // Generate UUID.
                        uuid = UUID.randomUUID().toString();
                        uuid = ID_FIELD_PREFIX + uuid; // identifies the uuid generated by discovery module.
                        idField.setValue(uuid, 1.0f);
                    }
                    SolrInputField uniqueIdField = solrInputDocument.getField(UNIQUE_ID);
                    if (null == uniqueIdField) {
                        solrInputDocument.setField(UNIQUE_ID, uuid);
                    } else {
                        if (uniqueIdField.getValue() == null) {
                            solrInputDocument.setField(UNIQUE_ID, uuid);
                        }
                    }
                }
            }
        }
    }

    public List getSolrDocumentBySolrId(String uniqueId) {
        String query = "(" + UNIQUE_ID + ":" + uniqueId + ")";
        return getSolrRequestReponseHandler().retriveResults(query);
    }

    protected SolrDocument getSolrDocumentByUUID(String identifier) {
        String query = "id:" + identifier;
        return null;
    }

    public void commitDocumentToSolr(List<SolrInputDocument> solrInputDocuments){
        getSolrRequestReponseHandler().updateSolr(solrInputDocuments);
    }

    public void buildSolrInputDocFromSolrDoc(Map<String,Object> solrFieldMap, SolrInputDocument solrInputDocument) {
        if (solrFieldMap != null && solrFieldMap.size() > 0) {
            Set<String> resultField = solrFieldMap.keySet();
            for (Iterator<String> iterator1 = resultField.iterator(); iterator1.hasNext(); ) {
                String key = iterator1.next();
                Object value = solrFieldMap.get(key);
                solrInputDocument.addField(key, value);
            }
        }
    }

    public SolrInputDocument buildSolrInputDocFromSolrDoc(SolrDocument solrDocument) {
        SolrInputDocument solrInputDocument = new SolrInputDocument();
        if (null != solrDocument) {
            Map<String, Collection<Object>> solrDocMap = solrDocument.getFieldValuesMap();
            if (solrDocMap != null && solrDocMap.size() > 0) {
                Set<String> keySet = solrDocMap.keySet();
                for (Iterator<String> iterator1 = keySet.iterator(); iterator1.hasNext(); ) {
                    String key = iterator1.next();
                    Object value = solrDocMap.get(key);
                    solrInputDocument.addField(key, value);
                }
            }
        }
        return solrInputDocument;
    }

    protected void removeFieldFromSolrInputDocument(SolrInputDocument holdingsSolrInputDoc) {
        holdingsSolrInputDoc.removeField(TITLE_SORT);
        holdingsSolrInputDoc.removeField(TITLE_SEARCH);
        holdingsSolrInputDoc.removeField(AUTHOR_SEARCH);
        holdingsSolrInputDoc.removeField(PUBLISHER_SEARCH);
        holdingsSolrInputDoc.removeField(ISSN_SEARCH);
        holdingsSolrInputDoc.removeField(ISBN_SEARCH);
        holdingsSolrInputDoc.removeField(FORMAT_SEARCH);
        holdingsSolrInputDoc.removeField(LANGUAGE_SEARCH);
        holdingsSolrInputDoc.removeField(PUBLICATIONDATE_SEARCH);
        holdingsSolrInputDoc.removeField(TITLE_DISPLAY);
        holdingsSolrInputDoc.removeField(AUTHOR_DISPLAY);
        holdingsSolrInputDoc.removeField(PUBLISHER_DISPLAY);
        holdingsSolrInputDoc.removeField(ISSN_DISPLAY);
        holdingsSolrInputDoc.removeField(ISBN_DISPLAY);
        holdingsSolrInputDoc.removeField(FORMAT_DISPLAY);
        holdingsSolrInputDoc.removeField(LANGUAGE_DISPLAY);
        holdingsSolrInputDoc.removeField(PUBLICATIONDATE_DISPLAY);
        holdingsSolrInputDoc.removeField(HOLDINGS_CALLNUMBER_DISPLAY);
        holdingsSolrInputDoc.removeField(HOLDINGS_CALLNUMBER_SEARCH);
        holdingsSolrInputDoc.removeField(HOLDINGS_LOCATION_DISPLAY);
        holdingsSolrInputDoc.removeField(HOLDINGS_LOCATION_SEARCH);
    }

    protected void addBibInfoForHoldingsOrItems(SolrInputDocument solrInputDocument, Map<String,Object> solrFieldMap) {
        solrInputDocument.addField(TITLE_SEARCH, solrFieldMap.get(TITLE_SEARCH));
        solrInputDocument.addField(TITLE_SORT, solrFieldMap.get(TITLE_SORT));
        solrInputDocument.addField(AUTHOR_SEARCH, solrFieldMap.get(AUTHOR_SEARCH));
        solrInputDocument.addField(PUBLICATIONPLACE_DISPLAY, solrFieldMap.get(PUBLICATIONPLACE_DISPLAY));
        solrInputDocument.addField(PUBLISHER_SEARCH, solrFieldMap.get(PUBLISHER_SEARCH));
        solrInputDocument.setField(PUBLISHER_SORT, solrFieldMap.get(PUBLISHER_SORT));
        solrInputDocument.addField(ISSN_SEARCH, solrFieldMap.get(ISSN_SEARCH));
        solrInputDocument.addField(ISBN_SEARCH, solrFieldMap.get(ISBN_SEARCH));
        solrInputDocument.addField(FORMAT_SEARCH, solrFieldMap.get(FORMAT_SEARCH));
        solrInputDocument.addField(LANGUAGE_SEARCH, solrFieldMap.get(LANGUAGE_SEARCH));
        solrInputDocument.addField(PUBLICATIONDATE_SEARCH, solrFieldMap.get(PUBLICATIONDATE_SEARCH));
        solrInputDocument.addField(MDF_035A, solrFieldMap.get(MDF_035A));
        solrInputDocument.addField(TITLE_DISPLAY, solrFieldMap.get(TITLE_DISPLAY));
        solrInputDocument.addField(AUTHOR_DISPLAY, solrFieldMap.get(AUTHOR_DISPLAY));
        solrInputDocument.addField(PUBLISHER_DISPLAY, solrFieldMap.get(PUBLISHER_DISPLAY));
        solrInputDocument.addField(ISSN_DISPLAY, solrFieldMap.get(ISSN_DISPLAY));
        solrInputDocument.addField(ISBN_DISPLAY, solrFieldMap.get(ISBN_DISPLAY));
        solrInputDocument.addField(FORMAT_DISPLAY, solrFieldMap.get(FORMAT_DISPLAY));
        solrInputDocument.addField(LANGUAGE_DISPLAY, solrFieldMap.get(LANGUAGE_DISPLAY));
        solrInputDocument.addField(PUBLICATIONDATE_DISPLAY, solrFieldMap.get(PUBLICATIONDATE_DISPLAY));
    }

    protected void addHoldingsInfoToItem(SolrInputDocument solrInputDocument, SolrInputDocument sourceInputDocument) {
        solrInputDocument.addField(HOLDINGS_LOCATION_SEARCH, sourceInputDocument.getFieldValue(LOCATION_LEVEL_SEARCH));
        solrInputDocument.addField(HOLDINGS_CALLNUMBER_SEARCH, sourceInputDocument.getFieldValue(CALL_NUMBER_SEARCH));
        solrInputDocument.addField(HOLDINGS_LOCATION_DISPLAY, sourceInputDocument.getFieldValue(LOCATION_LEVEL_DISPLAY));
        solrInputDocument.addField(HOLDINGS_CALLNUMBER_DISPLAY, sourceInputDocument.getFieldValue(CALL_NUMBER_DISPLAY));
        solrInputDocument.addField(HOLDINGS_COPYNUMBER_DISPLAY, sourceInputDocument.getFieldValue(COPY_NUMBER_DISPLAY));
        solrInputDocument.addField(HOLDINGS_COPYNUMBER_SEARCH, sourceInputDocument.getFieldValue(COPY_NUMBER_SEARCH));
        solrInputDocument.addField(HOLDINGS_CALLNUMBER_PREFIX_SEARCH, sourceInputDocument.getFieldValue(CALL_NUMBER_PREFIX_SEARCH));
        solrInputDocument.addField(HOLDINGS_CALLNUMBER_PREFIX_DISPLAY, sourceInputDocument.getFieldValue(CALL_NUMBER_PREFIX_DISPLAY));
        solrInputDocument.addField(HOLDINGS_SHELVING_SCHEME_CODE_SEARCH, sourceInputDocument.getFieldValue(SHELVING_SCHEME_CODE_SEARCH));
        solrInputDocument.addField(HOLDINGS_SHELVING_SCHEME_CODE_DISPLAY, sourceInputDocument.getFieldValue(SHELVING_SCHEME_CODE_DISPLAY));
        solrInputDocument.addField(HOLDINGS_SHELVING_SCHEME_VALUE_SEARCH, sourceInputDocument.getFieldValue(SHELVING_SCHEME_VALUE_SEARCH));
        solrInputDocument.addField(HOLDINGS_SHELVING_SCHEME_VALUE_DISPLAY, sourceInputDocument.getFieldValue(SHELVING_SCHEME_VALUE_DISPLAY));
    }



    protected void addDetails(SolrInputDocument solrInputDocument, SolrDocument solrDocument, String docfiled) {
        List<Object> docFieldValues = new ArrayList<Object>();
        if(solrDocument.containsKey(docfiled)){
            docFieldValues = (List<Object>) solrDocument.get(docfiled);
        }
        if(!docFieldValues.contains(solrInputDocument.getFieldValue(docfiled)) && null != solrInputDocument.getFieldValue(docfiled)){
            docFieldValues.add(solrInputDocument.getFieldValue(docfiled));
        }

        solrDocument.setField(docfiled, docFieldValues);
    }


    public SolrRequestReponseHandler getSolrRequestReponseHandler() {
        if(null == solrRequestReponseHandler) {
            solrRequestReponseHandler = new SolrRequestReponseHandler();
        }
        return solrRequestReponseHandler;
    }

    public void setSolrRequestReponseHandler(SolrRequestReponseHandler solrRequestReponseHandler) {
        this.solrRequestReponseHandler = solrRequestReponseHandler;
    }

    public OleDsHelperUtil getOleDsHelperUtil() {
        if(null == oleDsHelperUtil){
            oleDsHelperUtil = new OleDsHelperUtil();
        }
        return oleDsHelperUtil;
    }
}
