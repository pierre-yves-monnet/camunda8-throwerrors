package io.camunda.connector.errors.explicitworker;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FailExplicitWorker implements JobHandler {
  private static final Logger LOGGER = LoggerFactory.getLogger(FailExplicitWorker.class);

  private final String INPUT_ERRORPOLICY = "errorPolicy";
  private final String INPUT_LOGMESSAGE = "logMessage";

  private final static String ERROR_BAD_TEMPERATURE = "BadTemperature";

  @Override
  public void handle(JobClient jobClient, ActivatedJob activatedJob) throws Exception {
    LOGGER.debug("failExplicitWorker: start jobKey[{}] ", activatedJob.getKey());
    String errorPolicy = (String) activatedJob.getVariablesAsMap().get(INPUT_ERRORPOLICY);
    String logMessage = (String) activatedJob.getVariablesAsMap().get(INPUT_LOGMESSAGE);


    if ("THROWERROR".equals(errorPolicy)) {
      LOGGER.info("FailsExplicitWorker: THROWERROR jobKey[{}] message[{}]", activatedJob.getKey(), logMessage);
      jobClient.newThrowErrorCommand(activatedJob.getKey()).errorCode(ERROR_BAD_TEMPERATURE)
          //.retryBackoff(Duration.ofSeconds(5))
          .send().exceptionally((throwable -> {
            throw new RuntimeException("Could not complete job", throwable);
          }));
    } else if ("FAILWITHMESSAGE".equals(errorPolicy)) {
      LOGGER.info("FailsExplicitWorker: FAILWITHMESSAGE jobKey[{}] message[{}]", activatedJob.getKey(), logMessage);

      jobClient
          .newFailCommand(activatedJob.getKey())
          .retries(activatedJob.getRetries()-1)
          .errorMessage("BadTemperature")
          .send()
          .exceptionally((
              throwable -> {
                throw new RuntimeException("Could not complete job", throwable);
              }
          ));
    } else if ("FAIL".equals(errorPolicy)) {
      LOGGER.info("FailsExplicitWorker: FAIL jobKey[{}] message[{}]", activatedJob.getKey(), logMessage);

      jobClient
          .newFailCommand(activatedJob.getKey())
          .retries(0)
          .send()
          .exceptionally((
              throwable -> {
                throw new RuntimeException("Could not complete job", throwable);
              }
          ));


    } else {
      LOGGER.info("FailsExplicitWorker: ends jobKey[{}] message[{}]",activatedJob.getKey(),logMessage);
      jobClient
          .newCompleteCommand(activatedJob.getKey())
          .send()
          .exceptionally((
              throwable -> {
                throw new RuntimeException("Could not complete job", throwable);
              }
          ));

    }
  }


}
