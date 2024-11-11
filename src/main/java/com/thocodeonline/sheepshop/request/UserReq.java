package com.thocodeonline.sheepshop.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class UserReq {
    private Long id;
    private String code;
    private String name;
    private String phone;
    private String email;
    private Date dateOfBirth;
    private Integer gender;
    private String address;
    private Integer status;
    private boolean enabled;
    private String roleName;
    private String username;
    private Long accountId;

}
