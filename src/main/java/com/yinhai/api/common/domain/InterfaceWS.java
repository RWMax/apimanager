package com.yinhai.api.common.domain;

public class InterfaceWS {
    private Integer id;

    private Integer apiid;

    private String targetnamespace;

    private String endpointaddress;

    private String methodname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getApiid() {
        return apiid;
    }

    public void setApiid(Integer apiid) {
        this.apiid = apiid;
    }

    public String getTargetnamespace() {
        return targetnamespace;
    }

    public void setTargetnamespace(String targetnamespace) {
        this.targetnamespace = targetnamespace == null ? null : targetnamespace.trim();
    }

    public String getEndpointaddress() {
        return endpointaddress;
    }

    public void setEndpointaddress(String endpointaddress) {
        this.endpointaddress = endpointaddress == null ? null : endpointaddress.trim();
    }

    public String getMethodname() {
        return methodname;
    }

    public void setMethodname(String methodname) {
        this.methodname = methodname == null ? null : methodname.trim();
    }
}