package pete.eremeykin.todo.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.Map;

public final class ObjectUtils {

  public static Map<String, Object> transformToMap(Object object) {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    Map<String, Object> map =
        mapper.convertValue(object, new TypeReference<Map<String, Object>>() {
        });
    return map;
  }
}
