package com.example.ruby.mygetgps.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.ruby.mygetgps.R;
import com.example.ruby.mygetgps.models.AuthTokenWS;
import com.example.ruby.mygetgps.models.TravelerWS;
import com.example.ruby.mygetgps.utils.PreferencesManager;
import com.example.ruby.mygetgps.utils.retrofit.RequestManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class RegistrationActivity extends AppCompatActivity {

    @BindView(R.id.et_first_name)
    TextInputLayout et_first_name;
    @BindView(R.id.et_last_name)
    TextInputLayout et_last_name;
    @BindView(R.id.et_email)
    TextInputLayout et_email;
    @BindView(R.id.et_password)
    TextInputLayout et_password;
    @BindView(R.id.et_re_password)
    TextInputLayout et_re_password;
    @BindView(R.id.rg_options)
    RadioGroup radioGroup;
    @BindView(R.id.bt_save)
    Button bt_save;
    @BindView(R.id.pb_loader)
    View pb_loader;

    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        setupView();

        if (!PreferencesManager.getInstance(RegistrationActivity.this).getAuthToken().isEmpty()) {
            Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void setupView() {
        ((RadioButton) radioGroup.getChildAt(0)).setChecked(true);

        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkForRegistration();
            }
        });
    }

    private void checkForRegistration() {
        boolean valid = true;
        et_first_name.setError(null);
        et_last_name.setError(null);
        et_email.setError(null);
        et_password.setError(null);
        et_re_password.setError(null);

        if (TextUtils.isEmpty(et_first_name.getEditText().getText().toString())) {
            valid = false;
            et_first_name.setError("Campo vacío");
        }
        if (TextUtils.isEmpty(et_last_name.getEditText().getText().toString())) {
            valid = false;
            et_last_name.setError("Campo vacío");
        }
        if (TextUtils.isEmpty(et_email.getEditText().getText().toString())) {
            valid = false;
            et_email.setError("Campo vacío");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(et_email.getEditText().getText().toString()).matches()) {
            valid = false;
            et_email.setError("Formato inválido");
        }
        if (TextUtils.isEmpty(et_password.getEditText().getText().toString())) {
            valid = false;
            et_password.setError("Campo vacío");
        }
        if (TextUtils.isEmpty(et_re_password.getEditText().getText().toString())) {
            valid = false;
            et_re_password.setError("Campo vacío");
        } else if (!et_password.getEditText().getText().toString()
                .equals(et_re_password.getEditText().getText().toString())) {
            valid = false;
            et_re_password.setError("Contraseña no coincide");
        }

        if (valid) {
            sendRegistration();
        }
    }

    private void sendRegistration() {
        pb_loader.setVisibility(View.VISIBLE);

        email = et_email.getEditText().getText().toString();
        password = et_password.getEditText().getText().toString();

        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) findViewById(selectedId);
        int vehicleId = Integer.parseInt((String) radioButton.getTag());
        PreferencesManager.getInstance(RegistrationActivity.this).saveVehicleType(vehicleId);

        RequestManager.getDefault(getApplicationContext()).uploadRegistration(
                et_first_name.getEditText().getText().toString(),
                et_last_name.getEditText().getText().toString(),
                email,
                password,
                et_re_password.getEditText().getText().toString(),
                vehicleId)
                .enqueue(new Callback<TravelerWS>() {
                    @Override
                    public void onResponse(Call<TravelerWS> call, Response<TravelerWS> response) {
                        if (response.isSuccessful()) {
                            Timber.d("method=onResponse action='Registration saved in WS'");
                            //tripSent = response.body();
                            Toast.makeText(RegistrationActivity.this, "Usuario creado", Toast.LENGTH_SHORT).show();

                            //Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                            //startActivity(intent);
                            login();
                        } else {
                            Toast.makeText(RegistrationActivity.this, "Hubo un error", Toast.LENGTH_SHORT).show();
                        }
                        pb_loader.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<TravelerWS> call, Throwable t) {
                        Timber.d("method=onFailure");
                        Toast.makeText(RegistrationActivity.this, "No hay internet", Toast.LENGTH_SHORT).show();
                        pb_loader.setVisibility(View.GONE);
                    }

                });
    }

    private void login() {
        pb_loader.setVisibility(View.VISIBLE);

        RequestManager.getDefault(getApplicationContext()).login(
                email,
                password)
                .enqueue(new Callback<AuthTokenWS>() {
                    @Override
                    public void onResponse(Call<AuthTokenWS> call, Response<AuthTokenWS> response) {
                        if (response.isSuccessful()) {
                            Timber.d("method=onResponse action='Registration saved in WS'");
                            Toast.makeText(RegistrationActivity.this, "Entrando...", Toast.LENGTH_SHORT).show();

                            PreferencesManager.getInstance(RegistrationActivity.this).saveAuthToken(response.body().getAuth_token());

                            Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(RegistrationActivity.this, "Hubo un error", Toast.LENGTH_SHORT).show();
                        }
                        pb_loader.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<AuthTokenWS> call, Throwable t) {
                        Timber.d("method=onFailure");
                        Toast.makeText(RegistrationActivity.this, "No hay internet", Toast.LENGTH_SHORT).show();
                        pb_loader.setVisibility(View.GONE);
                    }

                });
    }
}
