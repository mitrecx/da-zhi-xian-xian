package top.mitrecx.dazhixianxian.common.dataformat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import javax.annotation.Nullable;
import java.io.IOException;

public class ObjectMappers {
    private static final ObjectMapper DEFAULT_INSTANCE = new ObjectMapper();

    private static final ObjectMapper SNAKE_CASE_INSTANCE = new ObjectMapper();

    private static final ObjectMapper COMPACT_INSTANCE = new ObjectMapper();

    private static final ObjectMapper YAML_INSTANCE = new ObjectMapper(new YAMLFactory());

    private static final XmlMapper XML_INSTANCE = new XmlMapper();

    static {
        DEFAULT_INSTANCE.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        DEFAULT_INSTANCE.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        DEFAULT_INSTANCE.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);

        SNAKE_CASE_INSTANCE.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        SNAKE_CASE_INSTANCE.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        SNAKE_CASE_INSTANCE.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SNAKE_CASE_INSTANCE.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);

        COMPACT_INSTANCE.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
        COMPACT_INSTANCE.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        COMPACT_INSTANCE.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);

        XML_INSTANCE.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }


    public static ObjectMapper get() {
        return DEFAULT_INSTANCE;
    }

    public static ObjectMapper snakeCase() {
        return SNAKE_CASE_INSTANCE;
    }

    public static ObjectMapper yaml() {
        return YAML_INSTANCE;
    }

    public static ObjectMapper xml() {
        return XML_INSTANCE;
    }

    public static <T> T mustReadValue(@Nullable String json, Class<T> clazz) {
        if (json == null) {
            return null;
        }
        try {
            return DEFAULT_INSTANCE.readValue(json, clazz);
        } catch (IOException e) {
            throw new DataFormatException(e);
        }
    }

    public static <T> T mustReadValue(@Nullable String json, TypeReference<T> typeRef) {
        if (json == null) {
            return null;
        }
        try {
            return DEFAULT_INSTANCE.readValue(json, typeRef);
        } catch (IOException e) {
            throw new DataFormatException(e);
        }
    }

    public static JsonNode mustReadTree(@Nullable String json) {
        if (json == null) {
            return null;
        }
        try {
            return DEFAULT_INSTANCE.readTree(json);
        } catch (IOException e) {
            throw new DataFormatException(e);
        }
    }

    public static String mustWriteValue(@Nullable Object o) {
        if (o == null) {
            return null;
        }
        try {
            return DEFAULT_INSTANCE.writeValueAsString(o);
        } catch (IOException e) {
            throw new DataFormatException(e);
        }
    }

    public static String mustWriteValuePretty(@Nullable Object o) {
        if (o == null) {
            return null;
        }
        try {
            return DEFAULT_INSTANCE.writerWithDefaultPrettyPrinter().writeValueAsString(o);
        } catch (IOException e) {
            throw new DataFormatException(e);
        }
    }

    public static String writeObjectCompact(@Nullable Object o) {
        try {
            try {
                return COMPACT_INSTANCE.writeValueAsString(o);
            } catch (JsonMappingException e) {
                return DEFAULT_INSTANCE.writeValueAsString(o);
            }
        } catch (IOException e) {
            throw new DataFormatException(e);
        }
    }
}
