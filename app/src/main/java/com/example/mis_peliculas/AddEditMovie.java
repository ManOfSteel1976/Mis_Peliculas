package com.example.mis_peliculas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AddEditMovie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_movie);
    }

    public void onClick(View view){
        switch(view.getId()){
            case R.id.buttonEdit:SaveFilm(); break;
        }
    }

    private void SaveFilm (){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}