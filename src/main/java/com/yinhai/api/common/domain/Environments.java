package com.yinhai.api.common.domain;

import java.util.List;

public class Environments {
    private Integer id;

    private String name;

    private List<EnvironmentsList> items;

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

    public List<EnvironmentsList> getItems() {
        return items;
    }

    public void setItems(List<EnvironmentsList> items) {
        this.items = items;
    }
}