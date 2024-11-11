package com.thocodeonline.sheepshop.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String username;
    private String password;
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @JsonBackReference
    private Role role;

}
