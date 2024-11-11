package com.thocodeonline.sheepshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String code;
    private String name;
    private String phone;
    private String email;
    private Date dateOfBirth;
    private Integer gender;
    private String address;
    private Integer status;
    private String image;
    private boolean enabled;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

}
