package com.zzsoft.zzsofttsm.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Cpu {          // cpu

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
        return "Cpu{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

}
