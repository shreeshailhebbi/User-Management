package com.flowable.usermanagement.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "USER_DETAILS")
@Data
public class User {
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "FIRST_NAME_")
    private String firstName;
    @Column(name = "LAST_NAME_")
    private String lastName;
    @Column(name = "EMAIL_", unique = true, nullable = false)
    private String email;
    @Column(name = "PHONE_NO_", unique = true)
    private long phoneNo;
    @Column(name = "DOB_")
    private Date dob;
    @Column(name = "GENDER_")
    private String gender;
    @Column(name = "COUNTRY_")
    private Integer country;
    @Column(name = "STATE_")
    private Integer state;
    @Column(name = "CITY_")
    private Integer city;
    @Column(name = "PASSWORD_")
    private String password;
    @Column(name = "STATUS_")
    private String status;
}
