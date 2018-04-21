package com.ndroid.ndroidtracker.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ndroid.ndroidtracker.server.GetDeviceStatusTask;
import com.ndroid.ndroidtracker.R;
import com.ndroid.ndroidtracker.server.ServerApi;
import com.ndroid.ndroidtracker.server.SignInTask;
import com.ndroid.ndroidtracker.models.DeviceStatus;

public class SignInActivity extends AppCompatActivity {

    private RelativeLayout mSignInLayout;
    private EditText mNameText;
    private EditText mPassText;
    private Button mSignInBtn;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_layout);

        mSignInLayout = (RelativeLayout) findViewById(R.id.mainLayout);
        mNameText = (EditText) findViewById(R.id.device_name);
        mPassText = (EditText) findViewById(R.id.device_pass);
        mSignInBtn = (Button) findViewById(R.id.sign_in_btn);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mNameText.getText().toString();
                String pass = mPassText.getText().toString();

                if (name.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Invalid Credentials",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Sign User In
                new SignInTask(new SignInTask.SignInCallback() {
                    @Override
                    public void onStarted() {
                        mProgressBar.setVisibility(View.VISIBLE);
                        mSignInLayout.setAlpha(0.2f);
                    }

                    @Override
                    public void onFinished(int id) {
                        mProgressBar.setVisibility(View.GONE);
                        mSignInLayout.setAlpha(1f);

                        if (id != 0) {
                            // Get Device Status
                            new GetDeviceStatusTask(new GetDeviceStatusTask.GetDeviceStatusCallback() {
                                @Override
                                public void onStarted() {
                                }

                                @Override
                                public void onFinished(DeviceStatus status) {
                                    ServerApi.setCurrentDeviceStatus(status);
                                    // Go to Main Activity
                                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(i);

                                }
                            }).execute(id);

                        } else {
                            Toast.makeText(getApplicationContext(), "Invalid Credentials",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }).execute(name, pass);
            }
        });
    }
}
