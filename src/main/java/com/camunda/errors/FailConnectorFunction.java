package com.camunda.errors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.camunda.connector.api.annotation.OutboundConnector;
import io.camunda.connector.api.outbound.OutboundConnectorContext;
import io.camunda.connector.api.outbound.OutboundConnectorFunction;
import io.camunda.example.dto.ConcatenationConnectorRequest;
import io.camunda.example.dto.ConcatenationConnectorResult;

@OutboundConnector(
    name = "concatenation-connector",
    inputVariables = {"input1", "input2"},
    type = "io.camunda:concatenation-api:1")

public class FailConnectorFunction implements OutboundConnectorFunction {

    private static final Logger LOGGER = LoggerFactory.getLogger(FailConnectorFunction.class);

    @Override
    public Object execute(OutboundConnectorContext context) {
      final var connectorRequest = context.bindVariables(FailConnectorRequest.class);
      return executeConnector(connectorRequest);
    }

    private ConcatenationConnectorResult executeConnector(final FailConnectorRequest connectorRequest) {
      LOGGER.info("Executing my connector with request {}", connectorRequest);
      String concatenationResult = connectorRequest.getInput1() + " " +connectorRequest.getInput2();

      Boolean throwErrorPlease = activatedJob.getVariablesAsMap().get(INPUT_THROWERRORPLEASE);

      if (Boolean.TRUE.equals(throwErrorPlease)) {

        throw new ConnectorException(ERROR_BAD_HUMIDITY, "Weather is rainy");
      }



      var result = new ConcatenationConnectorResult();
      //Result of the concatenation
      result.setConcatenationResult(concatenationResult);
      LOGGER.info("Connector executed with result {}:", result);
      return result;
    }
  }

}
