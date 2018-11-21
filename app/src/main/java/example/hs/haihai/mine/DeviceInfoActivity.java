package example.hs.haihai.mine;

import android.bluetooth.BluetoothClass;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.hs.baselibrary.widget.TitleBar;
import example.hs.haihai.R;
import example.hs.haihai.base.CommonActivity;
import example.hs.haihai.bean.DeviceInfo;
import example.hs.haihai.mine.adapter.DeviceInfoAdapter;
import example.hs.haihai.utils.DeviceUtils;

public class DeviceInfoActivity extends CommonActivity {

    @BindView(R.id.title_device_info)
    TitleBar titleDeviceInfo;
    @BindView(R.id.tv_device_info)
    TextView tvDeviceInfo;
    @BindView(R.id.rcv_device_info)
    RecyclerView rcvDeviceInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_device_info;
    }

    @Override
    protected void initView() {
        rcvDeviceInfo.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        List<DeviceInfo> deviceInfos = new ArrayList<>();
        //厂商
        deviceInfos.add(new DeviceInfo("厂商", DeviceUtils.getManufacturer(), "厂商"));
        //设备型号
        deviceInfos.add(new DeviceInfo("设备型号", DeviceUtils.getModel(), "设备型号"));
        //Android版本
        deviceInfos.add(new DeviceInfo("Android版本", DeviceUtils.getBuildVersion(), "Android版本"));
        //系统版本
        deviceInfos.add(new DeviceInfo("系统版本", String.valueOf(DeviceUtils.getSDKVersion()), "系统版本"));
        //设备ID
        deviceInfos.add(new DeviceInfo("设备ID", DeviceUtils.getAndroidID(activity), "设备ID"));
        //获取设备IMEI地址
        deviceInfos.add(new DeviceInfo("IMEI", DeviceUtils.getIMEI(activity), "IMEI"));
        //是否Root
        deviceInfos.add(new DeviceInfo("是否Root", DeviceUtils.isDeviceRooted() ? "是" : "否", "是否Root"));

        //获取设备MAC地址
        deviceInfos.add(new DeviceInfo("MAC地址", DeviceUtils.getMacAddressByWifiInfo(activity), "mac地址，需要打开WiFi开关"));

        //获取设备MAC地址
        deviceInfos.add(new DeviceInfo("MAC地址", DeviceUtils.getMacAddressByNetworkInterface(), "mac地址，高版本可能需要特殊权限"));

        //获取设备MAC地址
        deviceInfos.add(new DeviceInfo("MAC地址", DeviceUtils.getLocalMacAddressFromIp(), "mac地址，根据ip获取,联网才能用"));
        //获取设备IP地址
        //deviceInfos.add(new DeviceInfo("IP地址", DeviceUtils.getLocalInetAddress().getHostAddress(), "ip"));

        rcvDeviceInfo.setAdapter(new DeviceInfoAdapter(activity, deviceInfos));
    }

    @Override
    public boolean isStatusBarEnabled() {
        return false;
    }

}
