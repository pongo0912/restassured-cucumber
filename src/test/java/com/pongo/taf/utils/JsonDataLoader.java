package com.pongo.taf.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.cucumber.messages.internal.com.google.gson.Gson;
import io.cucumber.messages.internal.com.google.gson.GsonBuilder;
import io.restassured.response.Response;
import org.junit.Assert;

import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Scanner;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class JsonDataLoader {

    private static final String NO_JSON_PROVIDED = "No json provided!";
    private static final String COULD_NOT_CONVERT_TO_JSON = "Could not convert to JSON";

    public <T> T getDataGson(String dataPath, Class<T> genericType) {
        Gson gson = new GsonBuilder().create();
        try {
            URL url = getUrl(dataPath);
            InputStreamReader reader = new InputStreamReader(url.openStream());
            return gson.fromJson(reader, genericType);
        } catch (IOException e) {
            Assert.fail(e.getMessage());
            return null;
        }
    }

    public <T> T getDataJackson(String dataPath, Class<T> genericType) {
        String jsonString = readFile(dataPath);
        T dataModel = convertFromJson(jsonString, genericType);
        return dataModel;
    }

    public static <T> T convertRestResponseToResponseObjectGson(Response response, Class<T> genericType) {
        T responseObject = response.as(genericType);
        return responseObject;
    }

    public static <T> T convertRestResponseToResponseObjectJackson(Response response, Class<T> genericType) {
        String responseString = response.getBody().asString();
        return convertFromJson(responseString, genericType);
    }

    public String readFile(String path) {
        String jsonString = "";
        try {
            URL url = getUrl(path);
            Scanner fileReader = new Scanner(url.openStream()).useDelimiter("[\t\r\n ]+");
            while (fileReader.hasNext()) {
                jsonString += fileReader.nextLine();
            }
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
        return jsonString;
    }

    public URL getUrl(String relativePath) {
        return getClass().getClassLoader().getResource(relativePath);
    }

    public static ObjectMapper serializingObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new SimpleModule().addSerializer(BigDecimal.class, new ToStringSerializer()));
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
        return objectMapper;
    }

    public static <T> String convertToJson(T pojo) {
        try {
            if (pojo == null) {
                throw new IOException(NO_JSON_PROVIDED);
            }
            return serializingObjectMapper().writeValueAsString(pojo);
        } catch (IOException e) {
            Assert.fail(COULD_NOT_CONVERT_TO_JSON + e.getMessage());
            return null;
        }
    }

    public static <T> JsonNode convertToJsonObject(T pojo) throws IOException {
        String jsonString = convertToJson(pojo);
        return serializingObjectMapper().readTree(jsonString);
    }

    public static <T> T convertFromJson(String json, Class<T> type) {
        return readValue(json, type, null);
    }

    private static <T> T readValue(String json, Class<T> type, TypeReference<T> typeReference) {
        try {
            if (isBlank(json)) {
                throw new IOException(NO_JSON_PROVIDED);
            }
            if (type != null) {
                return serializingObjectMapper().readValue(json, type);
            } else {
                return serializingObjectMapper().readValue(json, typeReference);
            }
        } catch (IOException e) {
            Assert.fail(COULD_NOT_CONVERT_TO_JSON + e.getMessage());
            return null;
        }
    }

}
