package pete.eremeykin.todo.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class ObjectUtils {

  public static Map<String, Object> transformToMap(Object object) {
    ObjectMapper mapper = new ObjectMapper();
    Map<String, Object> map =
        mapper.convertValue(object, new TypeReference<Map<String, Object>>() {
        });
    return map;
  }
}
