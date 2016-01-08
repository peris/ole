package org.kuali.ole.deliver.util;

import oracle.sql.CLOB;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.ojb.broker.accesslayer.conversions.ConversionException;
import org.apache.ojb.broker.accesslayer.conversions.FieldConversion;
import org.kuali.rice.core.api.config.property.ConfigContext;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.sql.*;
import java.util.Arrays;

/**
 * Created by maheswarang on 12/29/15.
 */
public class OleOjbClobConversionUtil implements FieldConversion {
        @Override
        public Object javaToSql(Object source) throws ConversionException {
            return source;
        }

        @Override
        public Object sqlToJava(Object source) throws ConversionException {
            byte[] bytes = null;
            if (source == null) {
                return null;
            }
            try {
                if (source instanceof java.sql.Clob) {
                    Clob b = (Clob) source;
                     bytes = b.getSubString(1, (int) b.length()).getBytes();
                    return bytes;
                } else if(source instanceof String){
                     String content = (String)source;
                    if(!content.contains("HTML") || !content.contains("HTML")){
                    bytes = Hex.decodeHex(((String) source).toCharArray());
                    return bytes;
                    }else{
                        return content.getBytes();
                    }
                }
            } catch(java.sql.SQLException se) {
                throw new ConversionException(se);
            } catch (DecoderException e) {
                e.printStackTrace();
            }
            return source;
        }

    }