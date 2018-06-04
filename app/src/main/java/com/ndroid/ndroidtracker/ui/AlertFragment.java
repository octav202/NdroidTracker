package com.ndroid.ndroidtracker.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.ndroid.ndroidtracker.R;
import com.ndroid.ndroidtracker.models.DeviceAlert;
import com.ndroid.ndroidtracker.models.DeviceStatus;
import com.ndroid.ndroidtracker.server.SendDeviceAlertTask;
import com.ndroid.ndroidtracker.server.SendDeviceStatusTask;
import com.ndroid.ndroidtracker.server.ServerApi;

public class AlertFragment extends Fragment {

    private Switch mAlertSwitch;
    private EditText mPhoneText;
    private EditText mEmailText;
    private EditText mDescriptionText;
    private Button mSendButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.alert_layout, container, false);
        mAlertSwitch = (Switch) view.findViewById(R.id.alert_switch);
        mPhoneText = (EditText) view.findViewById(R.id.phoneText);
        mEmailText = (EditText) view.findViewById(R.id.emailText);
        mDescriptionText = (EditText) view.findViewById(R.id.descriptionText);
        mSendButton = (Button) view.findViewById(R.id.sendButton);

        final DeviceStatus deviceStatus = ServerApi.getCurrentDeviceStatus();
        final DeviceAlert deviceAlert = ServerApi.getCurrentDeviceAlert();

        if (deviceStatus != null) {
            mAlertSwitch.setChecked(deviceStatus.getAlert() == 1 ? true : false);

            if (deviceStatus.getAlert() == 0) {
                enableControls(false);
            } else {
                enableControls(true);
            }

            if (deviceAlert != null) {
                mPhoneText.setText(deviceAlert.getPhone());
                mEmailText.setText(deviceAlert.getEmail());
                mDescriptionText.setText(deviceAlert.getDescription());
            }
        }

        mAlertSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (deviceStatus != null) {
                    deviceStatus.setAlert(isChecked ? 1 : 0);

                    enableControls(isChecked);
                }
            }
        });

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDeviceAlert();
            }
        });

        return view;
    }

    /**
     * Send Status.
     */
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

    /**
     * Send Alert.
     */
    private void updateDeviceAlert() {

        ServerApi.getCurrentDeviceAlert().setPhone(mPhoneText.getText().toString());
        ServerApi.getCurrentDeviceAlert().setEmail(mEmailText.getText().toString());
        ServerApi.getCurrentDeviceAlert().setDescription(mDescriptionText.getText().toString());

        new SendDeviceAlertTask(new SendDeviceAlertTask.SendDeviceAlertCallback() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onFinished(Boolean result) {
                updateDeviceStatus();
            }
        }).execute(ServerApi.getCurrentDeviceAlert());

    }

    private void enableControls(boolean status) {
        if (status) {
            mPhoneText.setEnabled(true);
            mEmailText.setEnabled(true);
            mDescriptionText.setEnabled(true);
        } else {
            mPhoneText.setEnabled(false);
            mEmailText.setEnabled(false);
            mDescriptionText.setEnabled(false);
        }
    }
}
