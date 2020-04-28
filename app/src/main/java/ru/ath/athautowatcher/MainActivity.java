package ru.ath.athautowatcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import ru.ath.athautowatcher.data.Transport;
import ru.ath.athautowatcher.utils.JsonUtils;
import ru.ath.athautowatcher.utils.NetworkUtils;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewTransports;
    private ArrayList<Transport> transports = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewTransports = findViewById(R.id.recyclerViewTransports);

        JsonObject jsonObject = NetworkUtils.getJsonAllObjects();
        if (jsonObject != null) {
            transports = JsonUtils.getTransportListFromJson(jsonObject);
        } else {
            Toast.makeText(this,"Ошибка загрузки объектов", Toast.LENGTH_SHORT).show();
        }

        // привязываем адаптер
        TransportsAdapter adapter = new TransportsAdapter();
        adapter.setTransports(transports);

        // тут можно настроить расположение - последовательное
        recyclerViewTransports.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTransports.setAdapter(adapter);

        adapter.setOnTransportClickListener(new TransportsAdapter.OnTransportClickListener() {
            @Override
            public void onTransportClick(int position) {
                Toast.makeText(MainActivity.this, "Номер позиции: " + position, Toast.LENGTH_SHORT).show();
            }
        });

//        StringBuilder builder = new StringBuilder();
//        for (Transport oneTr:transports) {
//            builder.append(oneTr.getRegistrationplate()).append("\n");
//        }
//        Log.i("myresult", builder.toString());
    }
}
