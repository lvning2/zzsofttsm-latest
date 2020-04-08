package com.zzsoft.zzsofttsm.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

@Entity
@ToString
@ApiModel("用户实体类")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
public class User implements Serializable {

    @ApiModelProperty(value = "id",required = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ApiModelProperty("用户名，登录名")
    @Column(name="username")
    private String username;

    @ApiModelProperty("密码")
    @Column(name="password")
    private String password;

    @ApiModelProperty("姓名")
    @Column(name="name")
    private String name;

    @ApiModelProperty("组id")
    @Column(name="gid")
    private Integer gid;

    @ApiModelProperty("角色id")
    @Column(name="rid")
    private Integer rid;

    @Column(name = "lastLoginTime")
    private Date lastLoginTime;

    @Column(name = "lastLoginIp")
    private String lastLoginIp;

    @Column(name = "attendanceId")      // 考勤id
    private String attendanceId;


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",joinColumns = {@JoinColumn(name = "user_id")},inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    @JsonBackReference
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(String attendanceId) {
        this.attendanceId = attendanceId;
    }
}
