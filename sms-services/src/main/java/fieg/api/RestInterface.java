package fieg.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.logging.Logger;

public interface RestInterface {

  default void debug(Object dto) {
    try {
      String json = new ObjectMapper().writeValueAsString(dto);
      getLogger().fine(json);
    } catch (JsonProcessingException e) {
      getLogger().fine("Impossible to log");;
    }
  }
  Logger getLogger();
}
