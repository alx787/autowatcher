package ru.ath.athautowatcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import ru.ath.athautowatcher.data.MainViewModel;
import ru.ath.athautowatcher.data.Transport;
import ru.ath.athautowatcher.utils.JsonUtils;
import ru.ath.athautowatcher.utils.NetworkUtils;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewTransports;
    private ArrayList<Transport> transports = new ArrayList<>();
    private TransportsAdapter adapter;
    private MainViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        recyclerViewTransports = findViewById(R.id.recyclerViewTransports);

        JsonObject jsonObject = NetworkUtils.getJsonAllObjects();
        if (jsonObject != null) {
            transports = JsonUtils.getTransportListFromJson(jsonObject);
            if (transports != null && !transports.isEmpty()) {
                viewModel.deleteAllTransport();
                for (Transport oneTr : transports) {
                    viewModel.insertTransport(oneTr);
                }
            }
        } else {
            Toast.makeText(this,"Ошибка загрузки объектов", Toast.LENGTH_SHORT).show();
        }

        // привязываем адаптер
        adapter = new TransportsAdapter();
        //adapter.setTransports(transports);

        // тут можно настроить расположение - последовательное
        recyclerViewTransports.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTransports.setAdapter(adapter);

        adapter.setOnTransportClickListener(new TransportsAdapter.OnTransportClickListener() {
            @Override
            public void onTransportClick(int position) {
                Transport tr = adapter.getTransports().get(position);

                // запросим на сервере последнее место положение
                JsonObject jsonObject = NetworkUtils.getJsonLastPosition(tr.getAtinvnom());
                if (jsonObject == null) {
//                    Toast.makeText(this, "Ошибка, не получен ответ от сервера", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (jsonObject.has("status")) {
                    if (jsonObject.get("status").getAsString().equals("error")) {
                        return;
                    }
                } else {
                    return;
                }

                JsonObject contentJson = jsonObject.get("content").getAsJsonObject();

//                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                Intent intent = new Intent(MainActivity.this, YandexActivity.class);

                intent.putExtra("regnom", tr.getRegistrationplate());
                intent.putExtra("x", contentJson.get("x").getAsString());
                intent.putExtra("y", contentJson.get("y").getAsString());

                startActivity(intent);

            }
        });

        LiveData<List<Transport>> transportsFromLiveData = viewModel.getTransport();
        transportsFromLiveData.observe(this, new Observer<List<Transport>>() {
            @Override
            public void onChanged(List<Transport> transports) {
                adapter.setTransports(transports);
            }
        });
    }

//    private void downloadData() {
//        JsonObject jsonObject = NetworkUtils.getJsonAllObjects();
//        if (jsonObject != null) {
//            transports = JsonUtils.getTransportListFromJson(jsonObject);
//        } else {
//            Toast.makeText(this,"Ошибка загрузки объектов", Toast.LENGTH_SHORT).show();
//        }
//
//        // привязываем адаптер
//        final TransportsAdapter adapter = new TransportsAdapter();
//        adapter.setTransports(transports);
//    }
}
