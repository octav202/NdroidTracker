package com.ndroid.ndroidtracker.ui;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.ndroid.ndroidtracker.R;
import com.ndroid.ndroidtracker.server.SendDeviceStatusTask;
import com.ndroid.ndroidtracker.server.ServerApi;
import com.ndroid.ndroidtracker.models.DeviceStatus;

public class RemoteControlFragment extends Fragment {

    private Switch mLockSwitch;
    private Switch mEncryptionSwitch;
    private Switch mRebootSwitch;
    private Switch mWipeSwitch;
    private Switch mRingSwitch;
    private Switch mTrackLocationSwitch;
    private LinearLayout mFrequencyLayout;
    private TextView mFrequencyText;
    private SeekBar mFrequencyBar;
    private Button mSignOutButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.remote_control_layout, container, false);
        mLockSwitch = (Switch) view.findViewById(R.id.locked_switch);
        mEncryptionSwitch = (Switch) view.findViewById(R.id.storage_encryption_switch);
        mRebootSwitch = (Switch) view.findViewById(R.id.reboot);
        mWipeSwitch = (Switch) view.findViewById(R.id.wipe_data);
        mRingSwitch = (Switch) view.findViewById(R.id.ring);
        mTrackLocationSwitch = (Switch) view.findViewById(R.id.track);
        mFrequencyLayout = (LinearLayout) view.findViewById(R.id.frequencyLayout);
        mFrequencyText = (TextView) view.findViewById(R.id.frequencyText);
        mFrequencyBar = (SeekBar) view.findViewById(R.id.frequencyBar);
        mSignOutButton = (Button) view.findViewById(R.id.signOutBtn);

        final DeviceStatus deviceStatus = ServerApi.getCurrentDeviceStatus();

        if (deviceStatus != null) {
            mLockSwitch.setChecked(deviceStatus.getLock() == 1 ? true : false);
            mEncryptionSwitch.setChecked(deviceStatus.getEncryptStorage() == 1 ? true : false);
            mRebootSwitch.setChecked(deviceStatus.getReboot() == 1 ? true : false);
            mWipeSwitch.setChecked(deviceStatus.getWipeData() == 1 ? true : false);
            mRingSwitch.setChecked(deviceStatus.getRing() == 1 ? true : false);
            mTrackLocationSwitch.setChecked(deviceStatus.getLocationFrequency() == 0 ? false : true);
            mFrequencyBar.setProgress(deviceStatus.getLocationFrequency());
            mFrequencyText.setText("Frequency: " + deviceStatus.getLocationFrequency() + " sec.");

            if (deviceStatus.getLocationFrequency() == 0) {
                mFrequencyLayout.setEnabled(false);
            }
        }

        mLockSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (deviceStatus != null) {
                    deviceStatus.setLock(isChecked ? 1 : 0);
                    deviceStatus.setTriggered(0);
                    updateDeviceStatus();
                }
            }
        });

        mEncryptionSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (deviceStatus != null) {
                    deviceStatus.setEncryptStorage(isChecked ? 1 : 0);
                    deviceStatus.setTriggered(0);
                    updateDeviceStatus();
                }
            }
        });

        mRebootSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (deviceStatus != null) {
                    deviceStatus.setReboot(isChecked ? 1 : 0);
                    deviceStatus.setTriggered(0);
                    updateDeviceStatus();
                }
            }
        });

        mWipeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (deviceStatus != null) {
                    deviceStatus.setWipeData(isChecked ? 1 : 0);
                    deviceStatus.setTriggered(0);
                    updateDeviceStatus();
                }
            }
        });

        mRingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (deviceStatus != null) {
                    deviceStatus.setRing(isChecked ? 1 : 0);
                    deviceStatus.setTriggered(0);
                    updateDeviceStatus();
                }
            }
        });

        mTrackLocationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (deviceStatus != null) {
                    mFrequencyLayout.setEnabled(isChecked);
                    mFrequencyLayout.setClickable(isChecked);
                    mFrequencyBar.setEnabled(isChecked);

                    deviceStatus.setLocationFrequency(isChecked ? mFrequencyBar.getProgress() : 0);
                    deviceStatus.setTriggered(0);
                    updateDeviceStatus();
                }

            }
        });

        mFrequencyBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (!mTrackLocationSwitch.isChecked()) return;

                mFrequencyText.setText("Frequency : " + progress + " sec.");
                if (deviceStatus != null) {
                    deviceStatus.setLocationFrequency(mFrequencyBar.getProgress());
                    deviceStatus.setTriggered(0);
                    updateDeviceStatus();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mSignOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServerApi.setCurrentDeviceStatus(null);
                ServerApi.setCurrentDeviceId(0);
                Intent intent = new Intent(getActivity().getApplicationContext(), SignInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
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
                    Toast.makeText(getActivity().getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }).execute(ServerApi.getCurrentDeviceStatus());
    }
}
