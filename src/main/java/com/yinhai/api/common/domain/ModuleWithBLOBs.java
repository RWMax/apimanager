package com.yinhai.api.common.domain;

public class ModuleWithBLOBs extends Module {
    private String requestheaders;

    private String requestargs;

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
}