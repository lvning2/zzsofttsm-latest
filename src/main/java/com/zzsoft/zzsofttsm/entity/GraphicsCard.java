package com.zzsoft.zzsofttsm.entity;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Entity
public class GraphicsCard {         // 显卡

    @Id
    @GenericGenerator(name="idGenerator", strategy="guid")
    @GeneratedValue(generator="idGenerator")
    @Column(name = "id", length = 64)
    private String id;

    @Column(name = "name")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "GraphicsCard{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
