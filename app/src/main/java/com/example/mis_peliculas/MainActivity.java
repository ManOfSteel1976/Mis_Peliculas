package com.example.mis_peliculas;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    // Atributos para manejar la BD
    //private DBHelper dbHelper;
    /*private SQLiteDatabase db;

    private TextView textView;*/

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        textView = (TextView) findViewById(R.id.textView);

        dbHelper = new DBHelper(getApplicationContext(), "peliculas2.db");
        db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBContract.FilmEntry.COLUMN_NAME_TITLE, "1941");
        values.put(DBContract.FilmEntry.COLUMN_NAME_ORIG_TITLE, "1941");
        values.put(DBContract.FilmEntry.COLUMN_NAME_YEAR, 1979);
        values.put(DBContract.FilmEntry.COLUMN_NAME_RUNNING_TIME, 120);
        values.put(DBContract.FilmEntry.COLUMN_NAME_GENRE, "COMEDIA,ACCIÓN");
        values.put(DBContract.FilmEntry.COLUMN_NAME_VIEWINGS, "2018/12/01");

        long id = db.insert(DBContract.FilmEntry.TABLE_NAME, null, values);

        textView.setText(""+id);*/
    }

    /*@Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }*/

    public void goAddMovie (View view){
        Intent i = new Intent(this, AddMovie.class);
        startActivity(i);
    }

    public void goQueries (View view){
        Intent i = new Intent(this, Queries.class);
        startActivity(i);
    }
}