package com.example.mis_peliculas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Queries extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queries);
    }

    public void goMovieData (View view){
        Intent i = new Intent(this, MovieData.class);
        startActivity(i);
    }

}