package com.unfpa.safepal.chatmodule;
import java.util.Calendar;
import java.util.Date;

public class Chat {
    private String name, message;
    private Date createdAt;

    public Chat() {
    }

    public Chat(String name, String message) {
        this.name = name;
        this.message = message;
        this.createdAt = Calendar.getInstance().getTime();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
