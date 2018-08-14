package com.zgty.oarobot.bluetools;

import com.inuker.bluetooth.library.connect.options.BleConnectOptions;
import com.inuker.bluetooth.library.connect.response.BleConnectResponse;
import com.inuker.bluetooth.library.connect.response.BleWriteResponse;
import com.inuker.bluetooth.library.model.BleGattProfile;
import com.inuker.bluetooth.library.utils.BluetoothLog;
import com.inuker.bluetooth.library.utils.BluetoothUtils;
import com.inuker.bluetooth.library.utils.ByteUtils;

import java.util.UUID;

import static com.inuker.bluetooth.library.Constants.REQUEST_SUCCESS;

public class BuleToolsOpenDoor {
    private String mMac = "A4:C1:38:77:24:83";  //mac地址
    private UUID mService = UUID.fromString("0000FFE5-0000-1000-8000-00805F9B34FB"); //服务
    private UUID mCharacter = UUID.fromString("0000FFE9-0000-1000-8000-00805F9B34FB"); //特征服务
    private String openTime = "083838D67C52720000"; //开门动作持续5秒指令
    private String mDictat = "063838D63B0000";  //远程开门指令

    /**
     *其他指令:
     * 设置地址码：08A9AAD6CC0E0E0000
     * 取分组号和地址：06A9AAD6E90000
     * 取门参数：063838D6560000
     * 设置门参数 继电器动作0.5秒 ：083838D67C0E720000
     * 设置门参数 继电器动作1秒：083838D67C72720000
     * 取随机数：063838D6440000
     * 系统初始化：063838D6FF0000
     */

    //初始化连接
    public void initConnectData() {
        BleConnectOptions options = new BleConnectOptions.Builder()
                .setConnectRetry(10)  //连接重试次数
                .setConnectTimeout(3000)  //连接超时时间
                .setServiceDiscoverRetry(10)  //服务发现重试次数
                .setServiceDiscoverTimeout(1000) //服务发现超时时间
                .build();

        ClientManager.getClient().connect(BluetoothUtils.getRemoteDevice(mMac).getAddress(), options, new BleConnectResponse() {
            @Override
            public void onResponse(int code, BleGattProfile profile) {
                BluetoothLog.v(String.format("profile:\n%s", profile));

                if (code == REQUEST_SUCCESS) {
                    CommonUtils.toast("Bluetooth connect success!");
                    //发送开门动作持续5秒指令
                    ClientManager.getClient().write(mMac, mService, mCharacter,
                            ByteUtils.stringToBytes(openTime), null);
                } else {
                    CommonUtils.toast("Bluetooth connect failed!");
                }

            }
        });
    }

    //开门动作
    public void openDoorOperation() {
        ClientManager.getClient().write(mMac, mService, mCharacter,
                ByteUtils.stringToBytes(mDictat), new BleWriteResponse() {
                    @Override
                    public void onResponse(int code) {
                        if (code == REQUEST_SUCCESS) {
                            CommonUtils.toast("Bluetooth open the door success!");
                        } else {
                            CommonUtils.toast("Bluetooth open the door failed!");
                            initConnectData();  //连接重试
                        }
                    }
                });
    }

    //结果提示
    private final BleWriteResponse mWriteRsp = new BleWriteResponse() {
        @Override
        public void onResponse(int code) {
            if (code == REQUEST_SUCCESS) {
                CommonUtils.toast("success");
            } else {
                CommonUtils.toast("failed");
            }
        }
    };

}
