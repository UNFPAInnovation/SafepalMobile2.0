package com.unfpa.safepal.ProvideHelp.RVCsoModel;

/**
 * Created by Jjingo on 17/05/2017.
 */

public class BeforeCsoInfo {
    private String before_cso_name;
    private double before_cso_lat;
    private double before_cso_long;
    private String before_cso_phonenumber;

    public BeforeCsoInfo(String before_cso_name, double before_cso_lat, double before_cso_long, String before_cso_phonenumber) {
        this.before_cso_name = before_cso_name;
        this.before_cso_lat = before_cso_lat;
        this.before_cso_long = before_cso_long;
        this.before_cso_phonenumber = before_cso_phonenumber;
    }

    public String getBefore_cso_name() {
        return before_cso_name;
    }

    public void setBefore_cso_name(String before_cso_name) {
        this.before_cso_name = before_cso_name;
    }

    public double getBefore_cso_lat() {
        return before_cso_lat;
    }

    public void setBefore_cso_lat(double before_cso_lat) {
        this.before_cso_lat = before_cso_lat;
    }

    public double getBefore_cso_long() {
        return before_cso_long;
    }

    public void setBefore_cso_long(double before_cso_long) {
        this.before_cso_long = before_cso_long;
    }

    public String getBefore_cso_phonenumber() {
        return before_cso_phonenumber;
    }

    public void setBefore_cso_phonenumber(String before_cso_phonenumber) {
        this.before_cso_phonenumber = before_cso_phonenumber;
    }
}
