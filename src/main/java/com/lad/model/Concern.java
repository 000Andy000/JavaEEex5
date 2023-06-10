package com.lad.model;

import lombok.Data;

import java.util.Date;


@Data
public class Concern {
    private Integer id;
    private Integer concernerId;
    private Integer concernedId;
    private Date concernTime;
}
