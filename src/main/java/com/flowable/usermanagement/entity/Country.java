package com.flowable.usermanagement.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "COUNTRY")
public class Country {
    @Id
    @GeneratedValue
    @Column(name = "COUNTRY_ID_")
    private Integer id;
    @Column(name = "COUNTRY_NAME_")
    private String name;
}
