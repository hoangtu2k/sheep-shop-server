package com.thocodeonline.sheepshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private  String name;
    private  Integer status;

    @JsonIgnore
    @OneToMany(mappedBy = "role")
    private Set<Account> employees = new HashSet<Account>();

}
