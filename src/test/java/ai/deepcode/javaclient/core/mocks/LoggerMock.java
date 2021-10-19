package ai.deepcode.javaclient.core.mocks;

import ai.deepcode.javaclient.core.DCLoggerBase;

public class LoggerMock extends DCLoggerBase {

  public LoggerMock() {
    super(
            () -> System.out::println,
            () -> System.out::println,
            () -> true,
            () -> true,
            "ai.deepcode",
            "");
  }

  @Override
  protected String getExtraInfo() {
    return "";
  }
}
