package com.example.ruby.mygetgps.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.ruby.mygetgps.R;
import com.example.ruby.mygetgps.utils.PreferencesManager;

public class VehicleActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private Button bt_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);

        setupView();
        setupAction();
    }

    private void setupView() {
        radioGroup = (RadioGroup) findViewById(R.id.rg_options);
        bt_save = (Button) findViewById(R.id.bt_change_vehicle);

        int vehicleId = PreferencesManager.getInstance(VehicleActivity.this).getVehicleType();
        ((RadioButton)radioGroup.getChildAt(vehicleId-1)).setChecked(true);
    }

    public void setupAction() {
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                int vehicleId = Integer.parseInt((String) radioButton.getTag());
                PreferencesManager.getInstance(VehicleActivity.this).saveVehicleType(vehicleId);
                finish();
            }
        });
    }
}
