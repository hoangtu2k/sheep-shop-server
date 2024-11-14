package com.thocodeonline.sheepshop.request;


import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class AccountReq {
    private  Long id;
    private String username;
    private String password;
    private Integer status;
    private String roleName;
}
