package com.thocodeonline.sheepshop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
