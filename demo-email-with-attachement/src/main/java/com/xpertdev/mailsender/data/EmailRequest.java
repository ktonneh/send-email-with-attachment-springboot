package com.xpertdev.mailsender.data;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmailRequest implements Serializable {

    private String message;

    private String recipient;

}
