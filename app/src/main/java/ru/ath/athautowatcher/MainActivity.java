package ru.ath.athautowatcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

    private boolean filtered; // признак фильтрации

    private String filterRegnom;
    private String filterInvnom;
    private String filterAutocol;
    private String filterDepartment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("АТХ наблюдатель");

        filtered = false;

        Intent intent = getIntent();
        if (intent != null) {
            // проверяем фильтрацию
            if (intent.hasExtra("setfilter")) {
                filterRegnom = null;
                filterInvnom = null;
                filterAutocol = null;

                if (intent.hasExtra("regnom")) {
                    filterRegnom = intent.getStringExtra("regnom");
                    if (filterRegnom.isEmpty()) {
                        filterRegnom = null;
                    } else {
                        filterRegnom = filterRegnom;
                    }
                }

                if (intent.hasExtra("invnom")) {
                    filterInvnom = intent.getStringExtra("invnom");
                    if (filterInvnom.isEmpty()) {
                        filterInvnom = null;
                    }
                }

                if (intent.hasExtra("autocol")) {
                    filterAutocol = intent.getStringExtra("autocol");
                    if (filterAutocol.isEmpty()) {
                        filterAutocol = null;
                    }
                }

                if (intent.hasExtra("department")) {
                    filterDepartment = intent.getStringExtra("department");
                    if (filterDepartment.isEmpty()) {
                        filterDepartment = null;
                    }
                }

                if (filterRegnom != null || filterInvnom != null || filterAutocol != null || filterDepartment != null) {
                    filtered = true;
                }
            }
//            Toast.makeText(this,"+", Toast.LENGTH_SHORT).show();
        }

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        recyclerViewTransports = findViewById(R.id.recyclerViewTransports);

        JsonObject jsonObject = NetworkUtils.getJsonAllObjects();
        if (jsonObject != null) {
//            Toast.makeText(this,"++", Toast.LENGTH_SHORT).show();
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

                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("id", tr.getId());
                startActivity(intent);

            }
        });

        LiveData<List<Transport>> transportsFromLiveData;
        if (filtered) {
//            Log.i("myres", filterInvnom);
            String filtLikeRegnom = null;

            if (filterRegnom != null && !filterRegnom.isEmpty()) {
                filtLikeRegnom = "%" + filterRegnom + "%";
            }

            transportsFromLiveData = viewModel.getTransportByFilter(filtLikeRegnom, filterInvnom, filterAutocol, filterDepartment);
        } else {
            transportsFromLiveData = viewModel.getTransport();
        }

        transportsFromLiveData.observe(this, new Observer<List<Transport>>() {
            @Override
            public void onChanged(List<Transport> transports) {
                adapter.setTransports(transports);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                //Toast.makeText(MainActivity.this, "Выбраны настройки", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickFilter(View view) {
        Intent intent = new Intent(this, FilterActivity.class);

        if (filterRegnom == null || filterRegnom.isEmpty()) {
            intent.putExtra("regnom", "");
        } else {
            intent.putExtra("regnom", filterRegnom);
        }

        if (filterInvnom == null || filterInvnom.isEmpty()) {
            intent.putExtra("invnom", "");
        } else {
            intent.putExtra("invnom", filterInvnom);
        }

        if (filterAutocol == null || filterAutocol.isEmpty()) {
            intent.putExtra("autocol", "");
        } else {
            intent.putExtra("autocol", filterAutocol);
        }

        if (filterDepartment == null || filterDepartment.isEmpty()) {
            intent.putExtra("department", "");
        } else {
            intent.putExtra("department", filterDepartment);
        }

        startActivity(intent);
    }
}
