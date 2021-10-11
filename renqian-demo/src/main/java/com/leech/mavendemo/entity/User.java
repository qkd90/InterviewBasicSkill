package com.leech.mavendemo.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private static final long serialVersionUID = 8365334629295068557L;

    private String username;
    private String password;
}
