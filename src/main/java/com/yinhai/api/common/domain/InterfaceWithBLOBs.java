package com.yinhai.api.common.domain;

public class InterfaceWithBLOBs extends Interface {
    private String example;

    private String requestheaders;

    private String requestargs;

    private String responseargs;

    private InterfaceWS interfaceWS;

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example == null ? null : example.trim();
    }

    public String getRequestheaders() {
        return requestheaders;
    }

    public void setRequestheaders(String requestheaders) {
        this.requestheaders = requestheaders == null ? null : requestheaders.trim();
    }

    public String getRequestargs() {
        return requestargs;
    }

    public void setRequestargs(String requestargs) {
        this.requestargs = requestargs == null ? null : requestargs.trim();
    }

    public String getResponseargs() {
        return responseargs;
    }

    public void setResponseargs(String responseargs) {
        this.responseargs = responseargs == null ? null : responseargs.trim();
    }

    public InterfaceWS getInterfaceWS() {
        return interfaceWS;
    }

    public void setInterfaceWS(InterfaceWS interfaceWS) {
        this.interfaceWS = interfaceWS;
    }
}