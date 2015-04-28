package org.kuali.ole.select.keyvalue;

import org.kuali.ole.select.bo.OLEAuthenticationType;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.kuali.rice.krad.service.KRADServiceLocator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: srinivasane
 * Date: 6/26/13
 * Time: 3:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class OLEAuthenticationTypeKeyValues extends KeyValuesBase {

    @Override
    public List getKeyValues() {
        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        Collection<OLEAuthenticationType> oleAuthenticationTypes = KRADServiceLocator.getBusinessObjectService().findAll(OLEAuthenticationType.class);
        keyValues.add(new ConcreteKeyValue("", ""));
        for (OLEAuthenticationType oleAuthenticationType : oleAuthenticationTypes) {
            if (oleAuthenticationType.isActive()) {
                keyValues.add(new ConcreteKeyValue(oleAuthenticationType.getOleAuthenticationTypeId(), oleAuthenticationType.getOleAuthenticationTypeName()));
            }
        }
        return keyValues;
    }
}
