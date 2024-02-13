package io.camunda.errors;

import io.camunda.connector.errors.explicitworker.FailExplicitWorker;
import io.camunda.zeebe.client.api.worker.JobWorker;
import io.camunda.zeebe.spring.client.event.ZeebeClientCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class ErrorApplication {
  static Logger LOGGER = LoggerFactory.getLogger(ErrorApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(ErrorApplication.class, args);
  }


  /**
   * The connector runtime does not use the JobWorker annotation to detect them. So, workers must start explicitly
  * Let's do the same as the connectorRuntime to get the ZeebeClient and to start them.
   */
  @EventListener
  public void handleStart(ZeebeClientCreatedEvent evt) {
    LOGGER.info("start [fail-explicit-worker] worker");
    JobWorker failExplicitWorker = evt.getClient().newWorker().jobType("fail-explicit-worker").handler(new FailExplicitWorker()).open();
  }


}
