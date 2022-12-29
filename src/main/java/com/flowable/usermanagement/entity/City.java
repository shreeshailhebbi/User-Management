package com.flowable.usermanagement.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "CITY")
public class City {
    @Id
    @GeneratedValue
    @Column(name = "CITY_ID_")
    private Integer id;
    @Column(name = "CITY_NAME_")
    private String name;
    @Column(name = "STATE")
    private Integer state;
}
