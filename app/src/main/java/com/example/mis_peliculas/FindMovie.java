package com.example.mis_peliculas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class FindMovie extends AppCompatActivity {

    private EditText inputFilmName;

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
    }

    public void goAddEditMovie (View view){
        Intent i = new Intent(this, AddEditMovie.class);
        startActivity(i);
    }

}