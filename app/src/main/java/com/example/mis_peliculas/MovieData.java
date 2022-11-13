package com.example.mis_peliculas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MovieData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_data);
    }

    public void goEditMovie (View view){
        Intent i = new Intent(this, AddEditMovie.class);
        startActivity(i);
    }

}