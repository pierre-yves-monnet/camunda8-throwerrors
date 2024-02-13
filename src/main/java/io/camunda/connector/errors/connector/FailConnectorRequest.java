package io.camunda.connector.errors.connector;

public class FailConnectorRequest {

  private Boolean throwErrorPlease;
  private String logMessage;

  public void setThrowErrorPlease(Boolean throwErrorPlease) {
    this.throwErrorPlease = throwErrorPlease;
  }

  public void setLogMessage(String logMessage) {
    this.logMessage = logMessage;
  }

  public Boolean getThrowErrorPlease() {
    return throwErrorPlease;
  }

  public String getLogMessage() {
    return logMessage;
  }
}