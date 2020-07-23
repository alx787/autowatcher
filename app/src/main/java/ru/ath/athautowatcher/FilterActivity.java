package ru.ath.athautowatcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ru.ath.athautowatcher.data.MainViewModel;

public class FilterActivity extends AppCompatActivity {

    private MainViewModel viewModel;

    private EditText editTextRegnom;
    private EditText editTextInvnom;
    private Spinner spinnerAutocol;
    private Spinner spinnerDepartment;

    private CheckBox checkBoxUseAutocol;
    private CheckBox checkBoxUseDepartment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        setTitle("Фильтр");

        editTextRegnom = (EditText) findViewById(R.id.editTextRegnom);
        editTextInvnom = (EditText) findViewById(R.id.editTextInvnom);
        spinnerAutocol = (Spinner) findViewById(R.id.spinnerAutocol);
        spinnerDepartment = (Spinner) findViewById(R.id.spinnerDepartment);

        checkBoxUseAutocol = (CheckBox) findViewById(R.id.checkBoxUseAutocol);
        checkBoxUseDepartment = (CheckBox) findViewById(R.id.checkBoxUseDepartment);

        // заполним спиннер автоколонны
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        final List<String> autocolsList = viewModel.getAllAutoCols();
//        autocolsList.add("-");

        final ArrayAdapter<String> spinnerAutocolArrayAdapter = new ArrayAdapter<String>(this, R.layout.autocol_filter_spinner_item, autocolsList);
        spinnerAutocolArrayAdapter.setDropDownViewResource(R.layout.autocol_filter_spinner_item);
        spinnerAutocol.setAdapter(spinnerAutocolArrayAdapter);

        spinnerAutocol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                setDepartArrayAdapder();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        Intent intent = getIntent();
        if (intent != null) {
            ////////////////////////////////////////////////////
            // установка значений фильтра если он был задан ранее
            ////////////////////////////////////////////////////

            if (intent.hasExtra("regnom")) {
                editTextRegnom.setText(intent.getStringExtra("regnom"));
            }

            if (intent.hasExtra("invnom")) {
                editTextInvnom.setText(intent.getStringExtra("invnom"));
            }

            if (intent.hasExtra("autocol")) {
                String selectedValue = intent.getStringExtra("autocol");
                if (!selectedValue.equals("")) {
                    spinnerAutocol.setSelection(spinnerAutocolArrayAdapter.getPosition(selectedValue));
                }
            }

            if (intent.hasExtra("department")) {
                String selectedValue = intent.getStringExtra("department");
                if (!selectedValue.equals("")) {
                    setDepartArrayAdapder();
                    ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinnerDepartment.getAdapter();
                    spinnerDepartment.setSelection(adapter.getPosition(selectedValue));
                }

            }

        }
    }

    public void onClickFilter(View view) {

//        Toast.makeText(getApplicationContext(), "select " + spinnerAutocol.getSelectedItemPosition(), Toast.LENGTH_SHORT).show();
//        return;

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        intent.putExtra("setfilter", "");
        intent.putExtra("regnom", editTextRegnom.getText().toString());
        intent.putExtra("invnom", editTextInvnom.getText().toString());
        if (checkBoxUseAutocol.isChecked()) {
            intent.putExtra("autocol", spinnerAutocol.getSelectedItem().toString());
        } else {
            intent.putExtra("autocol", "");
        };

        if (checkBoxUseDepartment.isChecked()) {
            intent.putExtra("department", spinnerDepartment.getSelectedItem().toString());
        } else {
            intent.putExtra("department", "");
        };

        startActivity(intent);
    }


    private void setDepartArrayAdapder() {
        final List<String> departList = viewModel.getDepartments(spinnerAutocol.getSelectedItem().toString());

        final ArrayAdapter<String> spinnerDepartsArrayAdapter = new ArrayAdapter<String>(FilterActivity.this, R.layout.autocol_filter_spinner_item, departList);
        spinnerDepartsArrayAdapter.setDropDownViewResource(R.layout.autocol_filter_spinner_item);
        spinnerDepartment.setAdapter(spinnerDepartsArrayAdapter);
    }

 }
