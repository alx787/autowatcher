package ru.ath.athautowatcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        transports.add(new Transport(1, "", "номер 1 ру", "", "", "", "", "", "модель 1", "", "", "", "", "", "1111", "", "", "", "", "location 1", "", ""));
        transports.add(new Transport(2, "", "номер 2 ру", "", "", "", "", "", "модель 2", "", "", "", "", "", "2222", "", "", "", "", "location 2", "", ""));

        // привязываем адаптер
        TransportsAdapter adapter = new TransportsAdapter(transports);

        // тут можно настроить расположение - последовательное
        recyclerViewTransports.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTransports.setAdapter(adapter);

//        JsonObject jsonObject = NetworkUtils.getJsonAllObjects();
//        ArrayList<Transport> transports = JsonUtils.getTransportListFromJson(jsonObject);
//
//        StringBuilder builder = new StringBuilder();
//        for (Transport oneTr:transports) {
//            builder.append(oneTr.getRegistrationplate()).append("\n");
//        }
//        Log.i("myresult", builder.toString());

//        if (jsonObject != null) {
//            Toast.makeText(this,"Успешно", Toast.LENGTH_SHORT).show();
////            Gson gson = new Gson();
////            Log.i("myresult", gson.toJson(jsonObject));
//        } else {
//            Toast.makeText(this,"произошла ошибка", Toast.LENGTH_SHORT).show();
//        }
    }
}
