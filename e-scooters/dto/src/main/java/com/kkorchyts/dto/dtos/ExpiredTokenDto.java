package com.kkorchyts.dto.dtos;

public class ExpiredTokenDto {
    private Integer id;
    private String token;

    public ExpiredTokenDto() {
    }

    public ExpiredTokenDto(String token) {
        this.token = token;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
