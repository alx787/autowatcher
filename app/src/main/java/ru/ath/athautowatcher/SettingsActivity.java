package ru.ath.athautowatcher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import ru.ath.athautowatcher.utils.Globals;

public class SettingsActivity extends AppCompatActivity {

    private TextView textViewLogin;
    private TextView textViewPassword;
    private TextView textViewServer;
    private TextView textViewPort;

    private EditText editTextLogin;
    private EditText editTextPassword;
    private EditText editTextServer;
    private EditText editTextPort;

    private SharedPreferences mSettings;

    private final String SAME_PASSWORD_TEXT = "samepassword_1234%";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setTitle("Настройки");

        editTextLogin = (EditText) findViewById(R.id.editTextLogin);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextServer = (EditText) findViewById(R.id.editTextServer);
        editTextPort = (EditText) findViewById(R.id.editTextPort);

        mSettings = getSharedPreferences(Globals.APP_PREFERENCES, Context.MODE_PRIVATE);

        // восстанавливаем значения
        editTextLogin.setText(mSettings.getString(Globals.SRV_USERNAME, ""));
        editTextServer.setText(mSettings.getString(Globals.SRV_ADDRESS, ""));
        editTextPort.setText(mSettings.getString(Globals.SRV_PORT, ""));

        // с паролем разберемся отдельно
        // суть в том что будем сохранять пароль только если он будет не пустым и не равен samepassword
        String savedValue = mSettings.getString(Globals.SRV_PASSWORD, "");
        if (!savedValue.equals("")) {
            editTextPassword.setText(SAME_PASSWORD_TEXT);
        } else {
            editTextPassword.setText("");
        }

    }

    public void onClickSaveSettings(View view) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(Globals.SRV_USERNAME, editTextLogin.getText().toString());
        editor.putString(Globals.SRV_ADDRESS, editTextServer.getText().toString());

        // порт
        String savedValue = editTextPort.getText().toString();
        if (savedValue == null) {
            savedValue = "";
        }

        if (savedValue.equals("")) {
            savedValue = "8080";
        }

        // проверка на число
        try {
            Integer.parseInt(savedValue);
        } catch (Exception e) {
            Toast.makeText(this, "Неправильное значение ПОРТ, должны быть только цифры", Toast.LENGTH_SHORT).show();
            return;
        }

        editor.putString(Globals.SRV_PORT, savedValue);

        // пароль
        savedValue = editTextPassword.getText().toString();

        if (savedValue == null) {
            savedValue = "";
        }

        if (savedValue.equals("")) {
            Toast.makeText(this, "Неправильное значение ПАРОЛЬ, пустой пароль недопустим", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!savedValue.equals(SAME_PASSWORD_TEXT)) {
            editor.putString(Globals.SRV_PASSWORD, getHash(savedValue));
//            Log.i("myres", savedValue);
//            Log.i("myres", getHash(savedValue));
        }

        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    private String getHash(String stringParam) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        byte[] encodedhash = digest.digest(stringParam.getBytes(StandardCharsets.UTF_8));

        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < encodedhash.length; i++) {
            String hex = Integer.toHexString(0xff & encodedhash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }
}
