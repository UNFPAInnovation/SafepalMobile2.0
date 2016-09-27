package com.unfpa.safepal.Referral;

/**
 * Created by Brian on 21-Sep-16.
 */

public class Util {

    public static boolean isEmailValid(String email) {
        if ((email.contains("@")) && email.endsWith(".com")) {
            return true;
        } else {
            return false;
        }
    }

    public static void sleepMilli(long time) {
        try {
            Thread.sleep( time );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
