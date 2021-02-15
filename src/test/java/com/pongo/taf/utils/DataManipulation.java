package com.pongo.taf.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.inject.Inject;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import static org.junit.Assert.fail;

public class DataManipulation {

    @Inject
    private JsonDataLoader jsonDataLoader;

    public String obfuscateString(String inputString) {
        char[] chars = inputString.toCharArray();
        int length = chars.length;
        int charsToObfuscate = (int) (length - Math.ceil(length / 3.0));
        for (int i = 0; i < charsToObfuscate; i++) {
            chars[i] = '*';
        }
        return String.copyValueOf(chars);
    }

    public String getJsonValue(String dataPath, String keyPath, String key) {
        JsonObject json = jsonDataLoader.getDataGson(dataPath, JsonObject.class);
        JsonObject jsonObject = json.getAsJsonObject(keyPath);
        JsonElement jsonElement = jsonObject.get(key);
        if (jsonElement == null) {
            fail(String.format("No value found for json key '%1$s' with key path '%2$s' in data path: %3$s ", key, keyPath, dataPath));
            return null;
        } else {
            return jsonElement.getAsString();
        }
    }

    public String getJsonValueFromArray(String dataPath, String keyPath, String key) {
        JsonElement json = jsonDataLoader.getDataGson(dataPath, JsonObject.class);
        JsonArray jsonArray = json.getAsJsonObject().getAsJsonArray(keyPath);
        JsonElement jsonElement = null;
        for (JsonElement element : jsonArray) {
            jsonElement = element.getAsJsonObject().get(key);
        }
        return jsonElement != null ? jsonElement.getAsString() : null;
    }

    public String getJsonValue(String dataPath, String envPath, String keyPath, String key) {
        JsonObject json = jsonDataLoader.getDataGson(dataPath, JsonObject.class);
        JsonObject envJsonObject = json.getAsJsonObject(envPath);
        JsonObject keyJsonObject = envJsonObject.getAsJsonObject(keyPath);
        JsonElement jsonElement = keyJsonObject.get(key);
        if (jsonElement == null) {
            fail(String.format("No value found for json key '%1$s' with key path '%2$s/%3$s' in data path: %4$s ", key, envPath, keyPath, dataPath));
            return null;
        } else {
            return jsonElement.getAsString();
        }
    }

    public String getJsonValue(String dataPath, String key) {
        JsonObject json = jsonDataLoader.getDataGson(dataPath, JsonObject.class);
        JsonElement jsonElement = json.get(key);
        if (json.get(key).isJsonNull()) {
            return null;
        } else {
            return jsonElement.getAsString();
        }
    }

    public <T> T getEnvSpecificJsonData(String dataPath, String envPath, Class<T> genericType) {
        JsonObject json = jsonDataLoader.getDataGson(dataPath, JsonObject.class);
        JsonArray envJsonObject = json.getAsJsonArray(envPath);
        T dataModel = jsonDataLoader.convertFromJson(envJsonObject.toString(), genericType);
        return dataModel;
    }

    public static String convertSoapMessageToString(SOAPMessage msg) throws IOException, SOAPException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        msg.writeTo(stream);
        return new String(stream.toByteArray(), "utf-8");
    }

}
