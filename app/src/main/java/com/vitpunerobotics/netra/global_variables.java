package com.vitpunerobotics.netra;

import android.app.Application;

public class global_variables extends Application {

    public static int a = 0;
    public static int b = 0;
    public static int c = 0;
    public static int d = 0;
    public static int e = 0;
    public static int f = 0;
    public static int BT = 0;

    public global_variables() {
        super();
    }

    public static int getBT() {
        return BT;
    }

    public static void setBT(int BT) {
        global_variables.BT = BT;
    }

    public static int getA() {
        return a;
    }

    public static int getB() {
        return b;
    }

    public static int getC() {
        return c;
    }

    public static int getD() {
        return d;
    }

    public static int getE() {
        return e;
    }

    public static int getF() {
        return f;
    }
}

