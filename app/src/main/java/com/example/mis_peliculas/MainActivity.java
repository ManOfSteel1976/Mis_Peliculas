package com.example.mis_peliculas;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    // Atributos para manejar la BD
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(getApplicationContext(), "peliculas5.db");
        db = dbHelper.getWritableDatabase();


        List films = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

    // Define a projection that specifies which columns from the database
    // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                DBContract.FilmEntry.COLUMN_NAME_TITLE,
                DBContract.FilmEntry.COLUMN_NAME_ORIG_TITLE,
                DBContract.FilmEntry.COLUMN_NAME_YEAR,
                DBContract.FilmEntry.COLUMN_NAME_RUNNING_TIME,
                DBContract.FilmEntry.COLUMN_NAME_GENRE,
                DBContract.FilmEntry.COLUMN_NAME_VIEWINGS,
        };

    // Filter results WHERE "title" = 'My Title'
        //   String selection = DBContract.FilmEntry.COLUMN_NAME_TITLE + " = ?";
        //  String[] selectionArgs = { "My Title" };

    // How you want the results sorted in the resulting Cursor
        String sortOrder =
                DBContract.FilmEntry.COLUMN_NAME_VIEWINGS + " DESC";

        Cursor cursor = db.query(
                DBContract.FilmEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );


        while(cursor.moveToNext()) {
            String titulo = cursor.getString(
                    cursor.getColumnIndexOrThrow(DBContract.FilmEntry.COLUMN_NAME_TITLE));
            String fecha = cursor.getString(
                    cursor.getColumnIndexOrThrow(DBContract.FilmEntry.COLUMN_NAME_VIEWINGS));
            films.add(titulo+" ("+fecha+(")"));
        }
        cursor.close();

        listView = findViewById(R.id.ListViewTopFilms);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,films);
        listView.setAdapter(adapter);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    public void goAddMovie (View view){
        Intent i = new Intent(this, AddMovie.class);
        startActivity(i);
    }

    public void goQueries (View view){
        Intent i = new Intent(this, Queries.class);
        startActivity(i);
    }
}