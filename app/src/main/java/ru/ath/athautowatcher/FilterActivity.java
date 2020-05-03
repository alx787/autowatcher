package ru.ath.athautowatcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import ru.ath.athautowatcher.data.MainViewModel;

public class FilterActivity extends AppCompatActivity {

    private MainViewModel viewModel;

    private EditText editTextInvnom;
    private Spinner spinnerAutocol;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        editTextInvnom = (EditText) findViewById(R.id.editTextInvnom);
        spinnerAutocol = (Spinner)  findViewById(R.id.spinnerAutocol);

        // заполним спиннер
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        final List<String> autocolsList = viewModel.getAllAutoCols();
        final ArrayAdapter<String> spinnerAutocolArrayAdapter = new ArrayAdapter<String>(this, R.layout.autocol_filter_spinner_item, autocolsList);
        spinnerAutocolArrayAdapter.setDropDownViewResource(R.layout.autocol_filter_spinner_item);
        spinnerAutocol.setAdapter(spinnerAutocolArrayAdapter);

    }

    public void onClickFilter(View view) {
        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra("setfilter", "");
        intent.putExtra("invnom", editTextInvnom.getText().toString());

//        Log.i("myres", editTextInvnom.getText());

        startActivity(intent);
    }
}
