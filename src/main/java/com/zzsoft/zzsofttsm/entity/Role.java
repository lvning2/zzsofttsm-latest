package com.zzsoft.zzsofttsm.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "role")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class Role implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 父角色
     */
//    @Column(name = "parent_id")
//    private Long parentId;

    /**
     * 角色名称
     */
    @Column(name = "`name`")
    private String roleName;

    /**
     * 角色英文名称
     */
    @Column(name = "`enname`")
    private String enname;

    /**
     * 备注
     */
    @Column(name = "description")
    private String description;

    @Column(name = "created")
    private Date created;

    @Column(name = "updated")
    private Date updated;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinTable(name = "role_permission",joinColumns = {@JoinColumn(name = "role_id")},inverseJoinColumns = {@JoinColumn(name = "permission_id")})
    private Set<Permission> permissions;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH},mappedBy = "roles")
    private Set<User> users;

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setName(String roleName) {
        this.roleName = roleName;
    }

    public String getEnname() {
        return enname;
    }

    public void setEnname(String enname) {
        this.enname = enname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @JsonBackReference
    public Set<Permission> getPermissions() {
        return permissions;
    }


    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }


    public Set<User> getUsers() {
        return users;
    }

    @JsonBackReference
    public void setUsers(Set<User> users) {
        this.users = users;
    }


    private static final long serialVersionUID = 1L;
}
