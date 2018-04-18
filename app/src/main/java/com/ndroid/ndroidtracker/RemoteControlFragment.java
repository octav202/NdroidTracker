package com.ndroid.ndroidtracker;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class RemoteControlFragment extends Fragment {

    private Switch mLockSwitch;
    private Switch mEncryptionSwitch;
    private Switch mRebootSwitch;
    private Switch mWipeSwitch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.remote_control_layout, container, false);
        mLockSwitch = (Switch) view.findViewById(R.id.locked_switch);
        mEncryptionSwitch = (Switch) view.findViewById(R.id.storage_encryption_switch);
        mRebootSwitch = (Switch) view.findViewById(R.id.reboot);
        mWipeSwitch = (Switch) view.findViewById(R.id.wipe_data);

        final DeviceStatus deviceStatus = Service.getCurrentDeviceStatus();

        if (deviceStatus != null) {
            mLockSwitch.setChecked(deviceStatus.getLock() == 1 ? true : false);
            mEncryptionSwitch.setChecked(deviceStatus.getEncryptStorage() == 1 ? true : false);
            mRebootSwitch.setChecked(deviceStatus.getReboot() == 1 ? true : false);
            mWipeSwitch.setChecked(deviceStatus.getWipeData() == 1 ? true : false);
        }

        mLockSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (deviceStatus != null) {
                    deviceStatus.setLock(isChecked ? 1 : 0);
                    updateDeviceStatus();
                }
            }
        });

        mEncryptionSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (deviceStatus != null) {
                    deviceStatus.setEncryptStorage(isChecked ? 1 : 0);
                    updateDeviceStatus();
                }
            }
        });

        mRebootSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (deviceStatus != null) {
                    deviceStatus.setReboot(isChecked ? 1 : 0);
                    updateDeviceStatus();
                }
            }
        });

        mWipeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (deviceStatus != null) {
                    deviceStatus.setWipeData(isChecked ? 1 : 0);
                    updateDeviceStatus();
                }
            }
        });
        return view;
    }

    private void updateDeviceStatus() {
        new SendDeviceStatusTask(new SendDeviceStatusTask.SendDeviceStatusCallback() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onFinished(Boolean result) {
                if (!result) {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }).execute(Service.getCurrentDeviceStatus());
    }
}
