package org.kuali.ole.oleng.resolvers;

import org.kuali.ole.oleng.OleNGConstants;
import org.kuali.ole.pojo.OleTxRecord;

/**
 * Created by pvsubrah on 9/3/15.
 */
public class VendorNumberValueResolver extends TxValueResolver {

    @Override
    public boolean isInterested(String attributeName) {
        return OleNGConstants.BatchProcess.VENDOR_NUMBER.equals(attributeName);
    }

    @Override
    public void setAttributeValue(OleTxRecord oleTxRecord, String attributeValue) {
        oleTxRecord.setVendorNumber(attributeValue);
    }
}