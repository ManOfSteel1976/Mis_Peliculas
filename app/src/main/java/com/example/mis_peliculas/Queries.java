package com.example.mis_peliculas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Scanner;

public class Queries extends AppCompatActivity {

    // Atributos para manejar la BD
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    private EditText editTextFilmTitle;
    private EditText editTextFilmYear;
    private EditText editTextGenre;
    private EditText textMin;
    private EditText textMax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queries);
        dbHelper = new DBHelper(getApplicationContext(), "peliculas4.db");
        db = dbHelper.getWritableDatabase();

        editTextFilmTitle = (EditText) findViewById(R.id.editTextFilmTitle);
        editTextFilmYear = (EditText) findViewById(R.id.editTextFilmYear);
        editTextGenre = (EditText) findViewById(R.id.editTextGenre);
        textMin = (EditText) findViewById(R.id.textMin);
        textMax = (EditText) findViewById(R.id.textMax);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    public void onClick(View view){
        switch(view.getId()){
            case R.id.buttonFind:SearchFilms(); break;
        }
    }

    public void SearchFilms () {
        String title = editTextFilmTitle.getText().toString();
        String year = editTextFilmYear.getText().toString();
        String genres = editTextGenre.getText().toString();
        String timeMin = textMin.getText().toString();
        String timeMax = textMax.getText().toString();

        if (title.isEmpty()){
            if(year.isEmpty()){
                if(genres.isEmpty()){
                    if(timeMin.isEmpty() && timeMax.isEmpty()){
                        // 0000
                        Toast.makeText(this, R.string.withoutCriteria, Toast.LENGTH_LONG).show();
                    }else if (!timeMin.isEmpty() && !timeMax.isEmpty()){
                        Toast.makeText(this, "0001", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(this, R.string.invalidInterval, Toast.LENGTH_LONG).show();
                    }
                }else{
                    if(timeMin.isEmpty() && timeMax.isEmpty()){
                        // 0010
                        Toast.makeText(this, "0010", Toast.LENGTH_LONG).show();
                    }else if (!timeMin.isEmpty() && !timeMax.isEmpty()){
                        Toast.makeText(this, "0011", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(this, R.string.invalidInterval, Toast.LENGTH_LONG).show();
                    }
                }
            }else{
                if(genres.isEmpty()){
                    if(timeMin.isEmpty() && timeMax.isEmpty()){
                        // 0100
                        Toast.makeText(this, "0100", Toast.LENGTH_LONG).show();
                    }else if (!timeMin.isEmpty() && !timeMax.isEmpty()){
                        Toast.makeText(this, "0101", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(this, R.string.invalidInterval, Toast.LENGTH_LONG).show();
                    }
                }else{
                    if(timeMin.isEmpty() && timeMax.isEmpty()){
                        // 0110
                        Toast.makeText(this, "0110", Toast.LENGTH_LONG).show();
                    }else if (!timeMin.isEmpty() && !timeMax.isEmpty()){
                        Toast.makeText(this, "0101", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(this, R.string.invalidInterval, Toast.LENGTH_LONG).show();
                    }
                }
            }
        }else{
            if(year.isEmpty()){
                if(genres.isEmpty()){
                    if(timeMin.isEmpty() && timeMax.isEmpty()){
                        // 1000
                        Toast.makeText(this, "1000", Toast.LENGTH_LONG).show();
                    }else if (!timeMin.isEmpty() && !timeMax.isEmpty()){
                        Toast.makeText(this, "1001", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(this, R.string.invalidInterval, Toast.LENGTH_LONG).show();
                    }
                }else{
                    if(timeMin.isEmpty() && timeMax.isEmpty()){
                        // 1010
                        Toast.makeText(this, "1010", Toast.LENGTH_LONG).show();
                    }else if (!timeMin.isEmpty() && !timeMax.isEmpty()){
                        Toast.makeText(this, "1011", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(this, R.string.invalidInterval, Toast.LENGTH_LONG).show();
                    }
                }
            }else{
                if(genres.isEmpty()){
                    if(timeMin.isEmpty() && timeMax.isEmpty()){
                        // 1100
                        Toast.makeText(this, R.string.withoutCriteria, Toast.LENGTH_LONG).show();
                    }else if (!timeMin.isEmpty() && !timeMax.isEmpty()){
                        Toast.makeText(this, "1101", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(this, R.string.invalidInterval, Toast.LENGTH_LONG).show();
                    }
                }else{
                    if(timeMin.isEmpty() && timeMax.isEmpty()){
                        // 1110
                        Toast.makeText(this, "1110", Toast.LENGTH_LONG).show();
                    }else if (!timeMin.isEmpty() && !timeMax.isEmpty()){
                        Toast.makeText(this, "1111", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(this, R.string.invalidInterval, Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

}