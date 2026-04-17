package br.gov.cmb.common.util;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import br.gov.cmb.common.exception.runtime.CMBParseException;

public class JsonUtils {

    public static <T>T parse(String json, Class<T> tipo) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, tipo);
        } catch (IOException e) {
            throw new CMBParseException(e);
        }
    }
    
    public static <T>T parse(File arquivo, Class<T> tipo) {
        try {
            ObjectMapper mapper = new ObjectMapper();            
            return mapper.readValue(arquivo, tipo);
        } catch (IOException e) {
            throw new CMBParseException(e);
        }
    }
    
    public static <T> Collection<T> parseCollection(String json, Class<T> tipo) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, TypeFactory.defaultInstance().constructCollectionType(Collection.class, tipo));
        } catch (IOException e) {
            throw new CMBParseException(e);
        }
    }    
    
    public static <T> Collection<T> parseCollection(File arquivo,  Class<T> tipo) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(arquivo, TypeFactory.defaultInstance().constructCollectionType(Collection.class, tipo));
        } catch (IOException e) {
            throw new CMBParseException(e);
        }
    }    

    public static String toJson(Object object) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            throw new CMBParseException(e);
        }
    }

}
