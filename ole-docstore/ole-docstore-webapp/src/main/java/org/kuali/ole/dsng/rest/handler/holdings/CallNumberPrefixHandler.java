package org.kuali.ole.dsng.rest.handler.holdings;

import org.apache.commons.beanutils.converters.BooleanArrayConverter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.kuali.ole.constants.OleNGConstants;
import org.kuali.ole.docstore.engine.service.storage.rdbms.pojo.HoldingsRecord;
import org.kuali.ole.dsng.rest.Exchange;

import java.util.List;

/**
 * Created by SheikS on 12/20/2015.
 */
public class CallNumberPrefixHandler extends HoldingsHandler {

    private final String TYPE = "Call Number Prefix";

    @Override
    public void processDataMappings(JSONObject requestJsonObject, Exchange exchange) {
        JSONArray jsonArrayeFromJsonObject = getJSONArrayeFromJsonObject(requestJsonObject, TYPE);
        List<String> listFromJSONArray = getListFromJSONArray(jsonArrayeFromJsonObject.toString());
        if(CollectionUtils.isNotEmpty(listFromJSONArray)) {
            String callNumberPrefix = listFromJSONArray.get(0);
            HoldingsRecord holdingRecord = (HoldingsRecord) exchange.get(OleNGConstants.HOLDINGS_RECORD);
            holdingRecord.setCallNumberPrefix(callNumberPrefix);
        }
    }

    @Override
    public Boolean isInterested(String operation) {
        return operation.equals(TYPE);
    }

    @Override
    public void process(JSONObject requestJsonObject, Exchange exchange) {
        HoldingsRecord holdingRecord = (HoldingsRecord) exchange.get(OleNGConstants.HOLDINGS_RECORD);
        String callNumberPrefix = getStringValueFromJsonObject(requestJsonObject, TYPE);
        if(StringUtils.equals(holdingRecord.getCallNumberPrefix(),callNumberPrefix)) {
            exchange.add(OleNGConstants.MATCHED_HOLDINGS, Boolean.TRUE);
        }
    }
}