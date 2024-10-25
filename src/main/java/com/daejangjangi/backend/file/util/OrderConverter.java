package com.daejangjangi.backend.file.util;

import com.daejangjangi.backend.file.exception.NotSupportedFileFormatException;

public class OrderConverter {

  public static Integer getOrder(String fileName) {
    String pureName = fileName.substring(0, fileName.indexOf("."));
    Integer order = null;
    try {
      order = Integer.parseInt(pureName);
    } catch (NumberFormatException e) {
      throw new NotSupportedFileFormatException(pureName);
    }
    return order;
  }
}
