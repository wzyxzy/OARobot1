package com.zgty.oarobot.bluetools;

import android.widget.Toast;

import com.zgty.oarobot.common.OARobotApplication;

/**
 * by Viking
 */
public class CommonUtils {

    public static void toast(String text) {
        Toast.makeText(OARobotApplication.getInstance(), text, Toast.LENGTH_SHORT).show();
    }
}
