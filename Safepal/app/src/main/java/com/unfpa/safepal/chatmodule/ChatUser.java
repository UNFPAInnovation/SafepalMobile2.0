package com.unfpa.safepal.chatmodule;

import java.util.Calendar;
import java.util.Date;

class ChatUser {
    private String username, ipaddress;
    private Date createdAt;

    public ChatUser(String username, String ipaddress) {
        this.username = username;
        this.ipaddress = ipaddress;
        this.createdAt = Calendar.getInstance().getTime();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
