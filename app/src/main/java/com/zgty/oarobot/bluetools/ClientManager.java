package com.zgty.oarobot.bluetools;

import com.inuker.bluetooth.library.BluetoothClient;
import com.zgty.oarobot.common.OARobotApplication;

/**
 * by Viking
 */
public class ClientManager {

    private static BluetoothClient mClient;

    public static BluetoothClient getClient() {
        if (mClient == null) {
            synchronized (ClientManager.class) {
                if (mClient == null) {
                    mClient = new BluetoothClient(OARobotApplication.getInstance());
                }
            }
        }
        return mClient;
    }
}
