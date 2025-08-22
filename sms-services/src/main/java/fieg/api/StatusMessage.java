package fieg.api;

import java.io.Serializable;

public class StatusMessage implements Serializable {

  private String message;
  private Integer code;

  public StatusMessage() {
  }

  public StatusMessage(String message, Integer code) {
    this.message = message;
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }
}