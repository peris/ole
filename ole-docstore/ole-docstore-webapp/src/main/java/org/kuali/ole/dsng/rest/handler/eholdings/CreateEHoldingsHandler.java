package org.kuali.ole.dsng.rest.handler.eholdings;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.kuali.ole.DocumentUniqueIDPrefix;
import org.kuali.ole.constants.OleNGConstants;
import org.kuali.ole.docstore.common.document.PHoldings;
import org.kuali.ole.docstore.engine.service.storage.rdbms.pojo.HoldingsRecord;
import org.kuali.ole.dsng.rest.Exchange;
import org.kuali.ole.dsng.rest.handler.Handler;
import org.kuali.ole.dsng.rest.handler.holdings.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by SheikS on 1/22/2016.
 */
public class CreateEHoldingsHandler extends Handler {

    @Override
    public Boolean isInterested(String operation) {
        List<String> operationsList = getListFromJSONArray(operation);
        for (Iterator iterator = operationsList.iterator(); iterator.hasNext(); ) {
            String op = (String) iterator.next();
            if (op.equals("141") || op.equals("241")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void process(JSONObject requestJsonObject, Exchange exchange) {
        List<HoldingsRecord> holdingsRecords = (List<HoldingsRecord>) exchange.get(OleNGConstants.EHOLDINGS);

        try {
          JSONObject eHoldings =  requestJsonObject.getJSONObject(OleNGConstants.EHOLDINGS);
            JSONArray dataMappings = eHoldings.getJSONArray(OleNGConstants.DATAMAPPING);
            int index = 0;

            for (Iterator<HoldingsRecord> iterator = holdingsRecords.iterator(); iterator.hasNext(); ) {
                HoldingsRecord holdingsRecord = iterator.next();
                holdingsRecord.setBibId(holdingsRecord.getBibRecords().get(0).getBibId());
                exchange.add(OleNGConstants.HOLDINGS_RECORD, holdingsRecord);
                try {
                    JSONObject holdingsJSONObject = requestJsonObject.getJSONObject(OleNGConstants.EHOLDINGS);
                    exchange.add(OleNGConstants.DATAMAPPING, dataMappings.get(index));
                    index++;
                    processDataMappings(holdingsJSONObject, exchange);
                    setCommonValuesToHoldingsRecord(requestJsonObject, holdingsRecord);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            exchange.remove(OleNGConstants.HOLDINGS_RECORD);
            getHoldingDAO().saveAll(holdingsRecords);



        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    private void setCommonValuesToHoldingsRecord(JSONObject requestJsonObject, HoldingsRecord holdingsRecord) {
        String createdDateString = getStringValueFromJsonObject(requestJsonObject, OleNGConstants.UPDATED_DATE);
        Timestamp createdDate = getDateTimeStamp(createdDateString);
        String createdBy = getStringValueFromJsonObject(requestJsonObject, OleNGConstants.UPDATED_BY);
        holdingsRecord.setCreatedBy(createdBy);
        holdingsRecord.setCreatedDate(createdDate);

        holdingsRecord.setUniqueIdPrefix(DocumentUniqueIDPrefix.PREFIX_WORK_HOLDINGS_OLEML);
    }

    @Override
    public List<Handler> getMetaDataHandlers() {
        if (null == metaDataHandlers) {
            metaDataHandlers = new ArrayList<Handler>();
            metaDataHandlers.add(new HoldingsLocationHandler());
            metaDataHandlers.add(new CallNumberHandler());
            metaDataHandlers.add(new CallNumberTypeHandler());
            metaDataHandlers.add(new CallNumberPrefixHandler());
            metaDataHandlers.add(new CopyNumberHandler());
            metaDataHandlers.add(new AccessLocationHandler());
            metaDataHandlers.add(new AccessPasswordHandler());
            metaDataHandlers.add(new AccessStatusHandler());
            metaDataHandlers.add(new AccessUserNameHandler());
            metaDataHandlers.add(new AccessPasswordHandler());
            metaDataHandlers.add(new AdminUrlHandler());
            metaDataHandlers.add(new AdminUserNameHandler());
            metaDataHandlers.add(new AuthenticationTypeHandler());
            metaDataHandlers.add(new CancellationDecisionDateHandler());
            metaDataHandlers.add(new CancellationEffectiveDateHandler());
            metaDataHandlers.add(new CancellationReasonHandler());
            metaDataHandlers.add(new CoverageStartDateHandler());
            metaDataHandlers.add(new CoverageStartIssueHandler());
            metaDataHandlers.add(new CoverageStartVolumeHandler());
            metaDataHandlers.add(new CoverageEndDateHandler());
            metaDataHandlers.add(new CoverageEndIssueHandler());
            metaDataHandlers.add(new CoverageEndVolumeHandler());
            metaDataHandlers.add(new CurrentSubscriptionEndDateHandler());
            metaDataHandlers.add(new CurrentSubscriptionStartDateHandler());
            metaDataHandlers.add(new org.kuali.ole.dsng.rest.handler.eholdings.DonorCodeHandler());
            metaDataHandlers.add(new org.kuali.ole.dsng.rest.handler.eholdings.DonorNoteHandler());
            metaDataHandlers.add(new org.kuali.ole.dsng.rest.handler.eholdings.DonorPublicDisplayHandler());
            metaDataHandlers.add(new EResourceIdHandler());
            metaDataHandlers.add(new NoOfSumultaneousUserHander());
            metaDataHandlers.add(new InitialSubscriptionEndDateHandler());
            metaDataHandlers.add(new PerpetualAccessStartDateHandler());
            metaDataHandlers.add(new PerpetualAccessStartIssueHandler());
            metaDataHandlers.add(new PerpetualAccessStartVolumeHandler());
            metaDataHandlers.add(new PerpetualAccessEndDateHandler());
            metaDataHandlers.add(new PerpetualAccessEndIssueHandler());
            metaDataHandlers.add(new PerpetualAccessEndVolumeHandler());
            metaDataHandlers.add(new PersistentLinkHandler());
            metaDataHandlers.add(new PlatformHandler());
            metaDataHandlers.add(new ProxiedHandler());
            metaDataHandlers.add(new PublisherHandler());
            metaDataHandlers.add(new org.kuali.ole.dsng.rest.handler.eholdings.StatisticalSearchCodeHandler());
            metaDataHandlers.add(new SubscriptionStatusHandler());
            metaDataHandlers.add(new UrlHandler());
            metaDataHandlers.add(new LinkTextHandler());
            metaDataHandlers.add(new ImprintHandler());
            metaDataHandlers.add(new NonPublicNoteHandler());
            metaDataHandlers.add(new PublicNoteHandler());
            metaDataHandlers.add(new NoOfSumultaneousUserHander());
        }
        return metaDataHandlers;
    }
}