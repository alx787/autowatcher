package ru.ath.athautowatcher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TrackSetupActivity extends AppCompatActivity {

    private TextView textViewTransportInfo;
    private EditText editTextDateBeg;
    private EditText editTextDateEnd;
    private Button buttonSetBeg;
    private Button buttonSetEnd;
    private Button buttonViewProbeg;
    private DatePicker datePicker;

    private String invnom;

    private SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_setup);

        textViewTransportInfo = (TextView) findViewById(R.id.textViewTransportInfo);
        editTextDateBeg = (EditText) findViewById(R.id.editTextDateBeg);
        editTextDateEnd = (EditText) findViewById(R.id.editTextDateEnd);
        buttonSetBeg = (Button) findViewById(R.id.buttonSetBeg);
        buttonSetEnd = (Button) findViewById(R.id.buttonSetEnd);
        buttonViewProbeg = (Button) findViewById(R.id.buttonViewProbeg);
        datePicker = (DatePicker) findViewById(R.id.datePicker);

        editTextDateBeg.setText(formatDate.format(new Date()));
        editTextDateEnd.setText(formatDate.format(new Date()));

    }

    public void onClickDateBeg(View view) {
        Calendar c = Calendar.getInstance();
        c.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
        editTextDateBeg.setText(formatDate.format(c.getTime()));
    }

    public void onClickDateEnd(View view) {
        Calendar c = Calendar.getInstance();
        c.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
        editTextDateEnd.setText(formatDate.format(c.getTime()));
    }

    public void onClickViewProbeg(View view) {

        String sDateBeg = editTextDateBeg.getText().toString();
        String sDateEnd = editTextDateEnd.getText().toString();

        if (sDateBeg.isEmpty()) {
            Toast.makeText(this, "не заполнена дата начала", Toast.LENGTH_SHORT).show();
            return;
        }

        if (sDateEnd.isEmpty()) {
            Toast.makeText(this, "не заполнена дата окончания", Toast.LENGTH_SHORT).show();
            return;
        }

        Date dDateBeg = null;
        Date dDateEnd = null;
        try {
            dDateBeg = formatDate.parse(sDateBeg);
            dDateEnd = formatDate.parse(sDateEnd);
        } catch (ParseException e) {
            //e.printStackTrace();
            Toast.makeText(this, "ошибка преобразования даты", Toast.LENGTH_SHORT).show();
            return;
        }

        long milliseconds = dDateEnd.getTime() - dDateBeg.getTime();
        int diff = ((int) (milliseconds / (24 * 60 * 60 * 1000)));
        if (diff < 0 || diff > 7) {
            Toast.makeText(this, "нельзя задавать период меньше 0 дней и больше 7 дней", Toast.LENGTH_SHORT).show();
            return;
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Intent intent = new Intent(this, TrackViewActivity.class);
//        intent.putExtra("datebeg", format.format(dDateBeg));
//        intent.putExtra("dateend", format.format(dDateEnd));
//        intent.putExtra("invnom", invnom);
        startActivity(intent);
    }
}
