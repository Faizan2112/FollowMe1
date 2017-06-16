package com.dreamworld.followme.glideutills;

import java.io.File;


public class FileUtils {

    public static boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public static boolean fileIsExists(File f) {
        try {
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
