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
    private Button mWipeButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.remote_control_layout, container, false);
        mLockSwitch = (Switch) view.findViewById(R.id.locked_switch);
        mEncryptionSwitch = (Switch) view.findViewById(R.id.storage_encryption_switch);
        mRebootSwitch = (Switch) view.findViewById(R.id.reboot);
        mWipeButton = (Button) view.findViewById(R.id.wipe_data);

        mLockSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(getActivity().getApplicationContext(), "Locked : " + isChecked,
                        Toast.LENGTH_SHORT).show();
            }
        });

        mEncryptionSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(getActivity().getApplicationContext(), "Storage Ecnrypted : " + isChecked,
                        Toast.LENGTH_SHORT).show();
            }
        });

        mRebootSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(getActivity().getApplicationContext(), "Reboot on Start-Up : " + isChecked,
                        Toast.LENGTH_SHORT).show();
            }
        });

        mWipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "Wiped : ", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
