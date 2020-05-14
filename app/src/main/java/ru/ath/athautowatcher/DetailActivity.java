package ru.ath.athautowatcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import ru.ath.athautowatcher.data.MainViewModel;
import ru.ath.athautowatcher.data.Transport;
import ru.ath.athautowatcher.utils.NetworkUtils;

public class DetailActivity extends AppCompatActivity {

//    private TextView textViewTitleVal;
    private TextView textViewVehicletypeVal;
    private TextView textViewGrossvehicleweightVal;
    private TextView textViewRegistrationplateVal;
    private TextView textViewVinVal;
    private TextView textViewBrandVal;
    private TextView textViewModelVal;
    private TextView textViewProdyearVal;
    private TextView textViewColorVal;

    private TextView textViewEnginemodelVal;
    private TextView textViewPrimaryfueltypeVal;
    private TextView textViewEnginepowerVal;
    private TextView textViewAtinvnomVal;
    private TextView textViewAtinstalldateVal;
    private TextView textViewAtwheelformulaVal;
    private TextView textViewAtdepartmentVal;
    private TextView textViewAtautocolVal;
    private TextView textViewAtlocationVal;
    private TextView textViewAtbaseVal;
    private TextView textViewAtresVal;

    private TextView textViewTimeVal;
    private TextView textViewLongVal;
    private TextView textViewLatVal;
    private TextView textViewSatVal;
    private TextView textViewSpeedVal;
    private TextView textViewCourseVal;

    private Button buttonShowLastPosOnTheMap;


    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setTitle("Информация по ТС");

//        textViewTitleVal = (TextView) findViewById(R.id.textViewTitleVal);
        textViewVehicletypeVal = (TextView) findViewById(R.id.textViewVehicletypeVal);
        textViewGrossvehicleweightVal = (TextView) findViewById(R.id.textViewGrossvehicleweightVal);
        textViewRegistrationplateVal = (TextView) findViewById(R.id.textViewRegistrationplateVal);
        textViewVinVal = (TextView) findViewById(R.id.textViewVinVal);
        textViewBrandVal = (TextView) findViewById(R.id.textViewBrandVal);
        textViewModelVal = (TextView) findViewById(R.id.textViewModelVal);
        textViewProdyearVal = (TextView) findViewById(R.id.textViewProdyearVal);
        textViewColorVal = (TextView) findViewById(R.id.textViewColorVal);

        textViewEnginemodelVal = (TextView) findViewById(R.id.textViewEnginemodelVal);
        textViewPrimaryfueltypeVal = (TextView) findViewById(R.id.textViewPrimaryfueltypeVal);
        textViewEnginepowerVal = (TextView) findViewById(R.id.textViewEnginepowerVal);

        textViewAtinvnomVal = (TextView) findViewById(R.id.textViewAtinvnomVal);
        textViewAtinstalldateVal = (TextView) findViewById(R.id.textViewAtinstalldateVal);
        textViewAtwheelformulaVal = (TextView) findViewById(R.id.textViewAtwheelformulaVal);
        textViewAtdepartmentVal = (TextView) findViewById(R.id.textViewAtdepartmentVal);
        textViewAtautocolVal = (TextView) findViewById(R.id.textViewAtautocolVal);
        textViewAtlocationVal = (TextView) findViewById(R.id.textViewAtlocationVal);
        textViewAtbaseVal = (TextView) findViewById(R.id.textViewAtbaseVal);
        textViewAtresVal = (TextView) findViewById(R.id.textViewAtresVal);

        textViewTimeVal = (TextView) findViewById(R.id.textViewTimeVal);
        textViewLongVal = (TextView) findViewById(R.id.textViewLongVal);
        textViewLatVal = (TextView) findViewById(R.id.textViewLatVal);
        textViewSatVal = (TextView) findViewById(R.id.textViewSatVal);
        textViewSpeedVal = (TextView) findViewById(R.id.textViewSpeedVal);
        textViewCourseVal = (TextView) findViewById(R.id.textViewCourseVal);



        buttonShowLastPosOnTheMap = (Button) findViewById(R.id.buttonShowLastPosOnTheMap);
        // пока запретим нажатие
        buttonShowLastPosOnTheMap.setEnabled(false);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("id")) {
                Transport tr = viewModel.getTransportById(intent.getIntExtra("id", -1));

                if (tr != null) {
                    textViewVehicletypeVal.setText(tr.getVehicletype());
                    textViewGrossvehicleweightVal.setText(tr.getGrossvehicleweight());
                    textViewRegistrationplateVal.setText(tr.getRegistrationplate());
                    textViewVinVal.setText(tr.getVin());
                    textViewBrandVal.setText(tr.getBrand());
                    textViewModelVal.setText(tr.getModel());
                    textViewProdyearVal.setText(tr.getProdyear());
                    textViewColorVal.setText(tr.getColor());

                    textViewEnginemodelVal.setText(tr.getEnginemodel());
                    textViewPrimaryfueltypeVal.setText(tr.getPrimaryfueltype());
                    textViewEnginepowerVal.setText(tr.getEnginepower());

                    textViewAtinvnomVal.setText(tr.getAtinvnom());
                    textViewAtinstalldateVal.setText(tr.getAtinstalldate());
                    textViewAtwheelformulaVal.setText(tr.getAtwheelformula());
                    textViewAtdepartmentVal.setText(tr.getAtdepartment());
                    textViewAtautocolVal.setText(tr.getAtautocol());
                    textViewAtlocationVal.setText(tr.getAtlocation());
                    textViewAtbaseVal.setText(tr.getAtbase());
                    textViewAtresVal.setText(tr.getAtres());

                    textViewTimeVal.setText("-");
                    textViewLongVal.setText("-");
                    textViewLatVal.setText("-");
                    textViewSatVal.setText("-");
                    textViewSpeedVal.setText("-");
                    textViewCourseVal.setText("-");

                    // получим данные из мониторинга
                    setLastPosDataText(tr.getAtinvnom());
                }
            }
        }


    }

    private void setLastPosDataText(String invnom) {
        // запросим на сервере последнее место положение
        JsonObject jsonObject = NetworkUtils.getJsonLastPosition(invnom);
        if (jsonObject == null) {
            Toast.makeText(this, "Ошибка, не получен ответ от сервера, геоданные не определены", Toast.LENGTH_SHORT).show();
            return;
        }

        if (jsonObject.has("status")) {
            if (jsonObject.get("status").getAsString().equals("error")) {
                Toast.makeText(this, "Ошибка, ошибка в ответе сервера, геоданные не определены", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            Toast.makeText(this, "Ошибка, нет данных, геоданные не определены", Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObject contentJson = jsonObject.get("content").getAsJsonObject();

        textViewTimeVal.setText(contentJson.get("time").getAsString());
        //        "y":<double>,         /* широта */ lat
        //        "x":<double>,         /* долгота */ long
        textViewLongVal.setText(contentJson.get("x").getAsString());
        textViewLatVal.setText(contentJson.get("y").getAsString());
        textViewSatVal.setText(contentJson.get("sat").getAsString());
        textViewSpeedVal.setText(contentJson.get("speed").getAsString());
        textViewCourseVal.setText(contentJson.get("course").getAsString());

        // разрешим нажатие на кнопку
        buttonShowLastPosOnTheMap.setEnabled(true);
    }

    public void onClickShowLastPosOnTheMap(View view) {
        Intent intent = new Intent(this, YandexActivity.class);
        intent.putExtra("regnom", textViewAtinvnomVal.getText().toString());
        intent.putExtra("x", textViewLongVal.getText().toString());
        intent.putExtra("y", textViewLatVal.getText().toString());
        startActivity(intent);
    }

    public void onClickSetupTrack(View view) {
        Intent intent = new Intent(this, TrackSetupActivity.class);
        intent.putExtra("regnom", textViewRegistrationplateVal.getText().toString());
        intent.putExtra("invnom", textViewAtinvnomVal.getText().toString());
        startActivity(intent);
    }
}
