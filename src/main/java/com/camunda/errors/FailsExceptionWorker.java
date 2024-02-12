package com.camunda.errors;

public class FailsExceptionWorker {

private final String INPUT_THROWERRORPLEASE ="throwErrorPlease";
  private final String INPUT_MESSAGETOLOG = "messageToLog";

  private final String ERROR_BAD_WEATHER ="BadWeather";

  public void execute(final JobClient jobClient, final ActivatedJob activatedJob, ContextExecution contextExecution) {

    String messageToLog = activatedJob.getVariablesAsMap().get(INPUT_MESSAGETOLOG);

    Boolean throwErrorPlease = activatedJob.getVariablesAsMap().get(INPUT_THROWERRORPLEASE);

    if (Boolean.TRUE.equals(throwErrorPlease)) {

      throw new ConnectorException(ERROR_BAD_WEATHER, "Weather is rainy");
    }

  }
}
