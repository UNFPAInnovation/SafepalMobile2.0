package com.unfpa.safepal.ProvideHelp.RVCsoModel;

/**
 * Created by Kisa on 11/3/2016.
 */

public class TheCSO {
    private String cso_name;
    private String cso_distance;
    private String cso_phonenumber;

    public TheCSO() {
    }

    public TheCSO(String cso_name, String cso_distance, String cso_phonenumber) {
        this.cso_name = cso_name;
        this.cso_distance = cso_distance;
        this.cso_phonenumber = cso_phonenumber;
    }

    public String getCso_name() {
        return cso_name;
    }

    public void setCso_name(String cso_name) {
        this.cso_name = cso_name;
    }

    public String getCso_distance() {
        return cso_distance;
    }

    public void setCso_distance(String cso_distance) {
        this.cso_distance = cso_distance;
    }

    public String getCso_phonenumber() {
        return cso_phonenumber;
    }

    public void setCso_phonenumber(String cso_phonenumber) {
        this.cso_phonenumber = cso_phonenumber;
    }
}
