package com.test.smsgateapi;

/**
 * Created by admin on 27.05.2016.
 */

public class XmlResponse {
    public static int SUCCESS = 0;
    private String id;
    private int status;
    private String phones;
    private String smscnt;
    private String message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPhones() {
        return phones;
    }

    public void setPhones(String phones) {
        this.phones = phones;
    }

    public String getSmscnt() {
        return smscnt;
    }

    public void setSmscnt(String smscnt) {
        this.smscnt = smscnt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}