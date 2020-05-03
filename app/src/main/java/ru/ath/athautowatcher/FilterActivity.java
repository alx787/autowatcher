package ru.ath.athautowatcher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class FilterActivity extends AppCompatActivity {

    private EditText editTextInvnom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        editTextInvnom = (EditText) findViewById(R.id.editTextInvnom);
    }

    public void onClickFilter(View view) {
        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra("setfilter", "");
        intent.putExtra("invnom", editTextInvnom.getText());

        startActivity(intent);
    }
}
