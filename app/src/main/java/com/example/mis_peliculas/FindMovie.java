package com.example.mis_peliculas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.SortedList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class FindMovie extends AppCompatActivity {

    private EditText inputFilmName;
    private ListView listviewTopFilm;

    private ArrayList<String> films;

    public void onClick(View view){
        switch(view.getId()){
            case R.id.buttonSearchFilm:SearchFilm(); break;
            case R.id.buttonEditFilm:
                Intent i = new Intent(this, AddViewingToFilm.class);
                startActivity(i); break;
        }
    }

    private void hideSoftKeyboard (View v) {
        InputMethodManager inputMethodManager;
        inputMethodManager = (InputMethodManager) getSystemService (INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),0);
    }

    private void SearchFilm(){
        String word = inputFilmName.getText().toString().trim().toLowerCase();
        if (word.isEmpty()){
            Toast.makeText(this, R.string.badInput,Toast.LENGTH_LONG).show();
        }
        inputFilmName.setText("");
        hideSoftKeyboard (inputFilmName);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_movie);

        inputFilmName = (EditText) findViewById(R.id.editTextFilmSearch);
        listviewTopFilm = findViewById(R.id.listviewTopFilm);

        films = new ArrayList<>();

        films.add("Peli 1");
        films.add("Peli 2");
        films.add("Peli 3");
        films.add("Peli 4");
        films.add("Peli 5");
        films.add("Peli 6");
        films.add("Peli 7");
        films.add("Peli 8");
        films.add("Peli 9");
        films.add("Peli 10");
        films.add("Peli 11");
        films.add("Peli 12");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,films);
        listviewTopFilm.setAdapter(adapter);
    }

    public void goAddEditMovie (View view){
        Intent i = new Intent(this, AddEditMovie.class);
        startActivity(i);
    }

}