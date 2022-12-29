package com.flowable.usermanagement.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "STATE")
public class State {
    @Id
    @GeneratedValue
    @Column(name = "STATE_ID_")
    private Integer id;
    @Column(name = "STATE_NAME_")
    private String name;
    @Column(name = "COUNTRY_")
    private Integer country;
}
