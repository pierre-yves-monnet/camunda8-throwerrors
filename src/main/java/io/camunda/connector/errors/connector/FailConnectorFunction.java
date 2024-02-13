package io.camunda.connector.errors.connector;

import io.camunda.connector.api.annotation.OutboundConnector;
import io.camunda.connector.api.error.ConnectorException;
import io.camunda.connector.api.outbound.OutboundConnectorContext;
import io.camunda.connector.api.outbound.OutboundConnectorFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@OutboundConnector(name = "failConnector",
    inputVariables = { "throwErrorPlease",
    "logMessage" }, type = "fail-connector")
public class FailConnectorFunction implements OutboundConnectorFunction {

  private final static String ERROR_BAD_HUMIDITY = "badHumidity";

  private static final Logger LOGGER = LoggerFactory.getLogger(FailConnectorFunction.class);

  @Override
  public Object execute(OutboundConnectorContext context) {
    LOGGER.debug("FailConnectorRequest: start {}", context.getJobContext().getElementInstanceKey());
    final var connectorRequest = context.bindVariables(FailConnectorRequest.class);
    Boolean throwErrorPlease = connectorRequest.getThrowErrorPlease();
    String logMessage = connectorRequest.getLogMessage();

    if (Boolean.TRUE.equals(throwErrorPlease)) {
      LOGGER.info("FailConnectorRequest: error jobKey[{}] message[{}]",context.getJobContext().getElementInstanceKey(),logMessage);
      throw new ConnectorException(ERROR_BAD_HUMIDITY, "Weather is rainy");
    }

    FailConnectorResult result = new FailConnectorResult();
    //Result of the concatenation
    LOGGER.info("FailConnectorRequest: end jobKey[{}] message[{}]",context.getJobContext().getElementInstanceKey(),logMessage);
    return result;
  }

}
