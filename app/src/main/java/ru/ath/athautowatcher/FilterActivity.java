package ru.ath.athautowatcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import ru.ath.athautowatcher.data.MainViewModel;

public class FilterActivity extends AppCompatActivity {

    private MainViewModel viewModel;

    private EditText editTextInvnom;
    private Spinner spinnerAutocol;
    private Spinner spinnerDepartment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        editTextInvnom = (EditText) findViewById(R.id.editTextInvnom);
        spinnerAutocol = (Spinner) findViewById(R.id.spinnerAutocol);
        spinnerDepartment = (Spinner) findViewById(R.id.spinnerDepartment);

        // заполним спиннер автоколонны
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        final List<String> autocolsList = viewModel.getAllAutoCols();
        final ArrayAdapter<String> spinnerAutocolArrayAdapter = new ArrayAdapter<String>(this, R.layout.autocol_filter_spinner_item, autocolsList);
        spinnerAutocolArrayAdapter.setDropDownViewResource(R.layout.autocol_filter_spinner_item);
        spinnerAutocol.setAdapter(spinnerAutocolArrayAdapter);

        spinnerAutocol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Log.i("myres 1", String.valueOf(position) + ((TextView) view).getText().toString());
//                Log.i("myres 2", spinnerAutocol.getSelectedItem().toString());

                final List<String> departList = viewModel.getDepartments(spinnerAutocol.getSelectedItem().toString());
                final ArrayAdapter<String> spinnerDepartsArrayAdapter = new ArrayAdapter<String>(FilterActivity.this, R.layout.autocol_filter_spinner_item, departList);
                spinnerDepartsArrayAdapter.setDropDownViewResource(R.layout.autocol_filter_spinner_item);
                spinnerDepartment.setAdapter(spinnerDepartsArrayAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void onClickFilter(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        intent.putExtra("setfilter", "");
        intent.putExtra("invnom", editTextInvnom.getText().toString());
        intent.putExtra("autocol", spinnerAutocol.getSelectedItem().toString());
        intent.putExtra("department", spinnerDepartment.getSelectedItem().toString());

//        Log.i("myres", editTextInvnom.getText());

        startActivity(intent);
    }
}
