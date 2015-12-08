package org.kuali.ole.spring.batch.processor;

import org.apache.commons.io.FileUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.kuali.incubator.SolrRequestReponseHandler;
import org.kuali.ole.converter.MarcXMLConverter;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by pvsubrah on 12/7/15.
 */
public class BatchFileProcessor {
    public void processBatch(File file){
        MarcXMLConverter marcXMLConverter = new MarcXMLConverter();
        try {
            String rawMarc = FileUtils.readFileToString(file);
            List<Record> records = marcXMLConverter.convertRawMarchToMarc(rawMarc);
            for (Iterator<Record> iterator = records.iterator(); iterator.hasNext(); ) {
                Record marcRecord = iterator.next();
                List<VariableField> dataFields = marcRecord.getVariableFields("980");
                for (Iterator<VariableField> variableFieldIterator = dataFields.iterator(); variableFieldIterator.hasNext(); ) {
                    DataField dataField = (DataField) variableFieldIterator.next();
                    List<Subfield> subFields = dataField.getSubfields("a");
                    for (Iterator<Subfield> subfieldIterator = subFields.iterator(); subfieldIterator.hasNext(); ) {
                        Subfield subfield = subfieldIterator.next();
                        String matchPoint1 = subfield.getData();
                        SolrRequestReponseHandler solrRequestReponseHandler = new SolrRequestReponseHandler();
                        List results = solrRequestReponseHandler.retriveResults("mdf_980a:" + "\"" + matchPoint1 + "\"");
                        if(null != results && results.size() == 1){
                            JSONArray jsonArray = new JSONArray();
                            JSONObject jsonObject = new JSONObject();
                            Map<String,Object> fieldMap = (Map<String, Object>) results.get(0);
                            for (Iterator<String> fieldMapIterator = fieldMap.keySet().iterator(); fieldMapIterator.hasNext(); ) {
                                String key = fieldMapIterator.next();
                                try {
                                    jsonObject.put(key,fieldMap.get(key));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                            jsonArray.put(jsonObject);
                        }
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}