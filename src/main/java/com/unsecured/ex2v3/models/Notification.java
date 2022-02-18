package com.unsecured.ex2v3.models;

import lombok.Data;

@Data
public class Notification {

    public Integer id;
    public String senderEmail;
    public String recipientEmail;
    public String messageTopic;


}
