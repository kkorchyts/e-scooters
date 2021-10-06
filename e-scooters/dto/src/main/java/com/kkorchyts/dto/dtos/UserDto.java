package com.kkorchyts.dto.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kkorchyts.domain.enums.UserRole;

import java.util.HashSet;
import java.util.Set;

public class UserDto {
    private Integer id;
    private String login;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String name;
    private String email;
    private String address;
    private Set<UserRole> roles;

    public UserDto() {
    }

    public UserDto(Integer id, String login, String password, String name, String email, String address, Set<UserRole> roles) {
        this.id = id;
        if (login != null) {
            this.login = login.toLowerCase();
        }
        this.password = password;
        this.name = name;
        this.email = email;
        this.address = address;
        this.roles = roles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        if (login != null) {
            this.login = login.toLowerCase();
        }
    }

    //@JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }
}
