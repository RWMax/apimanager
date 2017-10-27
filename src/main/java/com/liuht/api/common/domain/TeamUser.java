package com.liuht.api.common.domain;

import java.util.Date;

public class TeamUser {
    private Integer id;

    private Integer teamid;

    private Integer userid;

    private Date createtime;

    private String teamusertype;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTeamid() {
        return teamid;
    }

    public void setTeamid(Integer teamid) {
        this.teamid = teamid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getTeamusertype() {
        return teamusertype;
    }

    public void setTeamusertype(String teamusertype) {
        this.teamusertype = teamusertype == null ? null : teamusertype.trim();
    }
}