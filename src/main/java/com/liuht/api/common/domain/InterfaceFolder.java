package com.liuht.api.common.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InterfaceFolder {
    private Integer id;

    private String name;

    private Date createtime;

    private Integer moduleid;

    private Integer projectid;

    private List<InterfaceWithBLOBs> interfaces = new ArrayList<>();
    
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

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getModuleid() {
        return moduleid;
    }

    public void setModuleid(Integer moduleid) {
        this.moduleid = moduleid;
    }

    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public List<InterfaceWithBLOBs> getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(List<InterfaceWithBLOBs> interfaces) {
        this.interfaces = interfaces;
    }
}