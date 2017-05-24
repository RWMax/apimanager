package com.yinhai.api.common.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Module {
    private Integer id;

    private String name;

    private Date lastupdatetime;

    private Integer projectid;

    private Date createtime;

    private String requestHeaders;

    private String requestArgs;

    private List<InterfaceFolder> folders = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getLastupdatetime() {
        return lastupdatetime;
    }

    public void setLastupdatetime(Date lastupdatetime) {
        this.lastupdatetime = lastupdatetime;
    }

    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public List<InterfaceFolder> getFolders() {
        return folders;
    }

    public void setFolders(List<InterfaceFolder> folders) {
        this.folders = folders;
    }

    public String getRequestHeaders() {
        return requestHeaders;
    }

    public void setRequestHeaders(String requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    public String getRequestArgs() {
        return requestArgs;
    }

    public void setRequestArgs(String requestArgs) {
        this.requestArgs = requestArgs;
    }
}