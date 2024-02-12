package com.camunda.errors;

import jakarta.validation.constraints.NotEmpty;

public class FailConnectorRequest {

  @NotEmpty
  private String input1;

  @NotEmpty
  private String input2;

  public void setInput1(String input1) {
    this.input1 = input1;
  }

  public void setInput2(String input2) {
    this.input2 = input2;
  }

  public String getInput1() {
    return input1;
  }

  public String getInput2() {
    return input2;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((input1 == null) ? 0 : input1.hashCode());
    result = prime * result + ((input2 == null) ? 0 : input2.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    FailConnectorRequest other = (FailConnectorRequest) obj;
    if (input1 == null) {
      if (other.input1 != null)
        return false;
    } else if (!input1.equals(other.input1))
      return false;
    if (input2 == null) {
      if (other.input2 != null)
        return false;
    } else if (!input2.equals(other.input2))
      return false;
    return true;
  }
  @Override
  public String toString() {
    return "[input1=" + input1 + ", input2=" + input2 + "]";
  }
}