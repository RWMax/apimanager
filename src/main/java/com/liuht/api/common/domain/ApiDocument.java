package com.liuht.api.common.domain;

import java.util.List;

/**
 * @author:TANQINGPING
 * @version:1.0 2017/1/5
 * package:com.liuht.api.common.domain
 * <p>Title: ApiDocument.java</p>
 * <p>Description: 文档结构类</p>
 * <p>Copyright: Copyright (c) 2016</p>
 *
 */

public class ApiDocument {
    private List<Environments> environments;
    private List<Module> modules;

    public List<Environments> getEnvironments() {
        return environments;
    }

    public void setEnvironments(List<Environments> environments) {
        this.environments = environments;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }
}
