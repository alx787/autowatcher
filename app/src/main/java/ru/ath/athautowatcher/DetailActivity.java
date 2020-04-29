package ru.ath.athautowatcher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import ru.ath.athautowatcher.utils.NetworkUtils;

public class DetailActivity extends AppCompatActivity {

    private TextView textViewNm;
    private TextView textViewVehicletype;
    private TextView textViewModel;

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        textViewNm = findViewById(R.id.textViewNm);
        textViewVehicletype = findViewById(R.id.textViewVehicletype);
        textViewModel = findViewById(R.id.textViewModel);

        Intent intent = getIntent();
        if ((intent != null) && (intent.hasExtra("id"))) {
            id = intent.getIntExtra("id", -1);
        } else {
            finish();
        }
    }
}
