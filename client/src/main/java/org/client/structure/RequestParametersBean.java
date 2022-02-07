package org.client.structure;

public class RequestParametersBean {
    private final String parameterName;
    private final String parameterValue;

    public RequestParametersBean(String parameterName, String parameterValue) {
        this.parameterName = parameterName;
        this.parameterValue = parameterValue;
    }

    public String getParameterName() {
        return parameterName;
    }

    public String getParameterValue() {
        return parameterValue;
    }

    @Override
    public String toString() {
        return "RequestParametersBean{" +
                "parameterName='" + parameterName + '\'' +
                ", parameterValue='" + parameterValue + '\'' +
                '}';
    }
}
