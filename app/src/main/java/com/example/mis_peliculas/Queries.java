package com.example.mis_peliculas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Queries extends AppCompatActivity {

    private ListView listView;

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
        dbHelper = new DBHelper(getApplicationContext(), "peliculas5.db");
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

    private void hideSoftKeyboard(View v) {
        InputMethodManager inputMethodManager;
        inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public void SearchFilms () {

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

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                DBContract.FilmEntry.COLUMN_NAME_VIEWINGS + " DESC";

        Cursor cursor;

        String title = editTextFilmTitle.getText().toString();
        String year = editTextFilmYear.getText().toString();
        String genres = editTextGenre.getText().toString();
        String timeMin = textMin.getText().toString();
        String timeMax = textMax.getText().toString();

        if (title.isEmpty()){
            if(year.isEmpty()){
                if(genres.isEmpty()){
                    if(timeMin.isEmpty() && timeMax.isEmpty()){
                        // 0000 No hay ning??n criterio definido
                        Toast.makeText(this, R.string.withoutCriteria, Toast.LENGTH_LONG).show();
                    }else if (!timeMin.isEmpty() && !timeMax.isEmpty()){
                        // 0001 B??squeda por duraci??n
                        if(Integer.parseInt(timeMin)<Integer.parseInt(timeMax)){
                            String where = DBContract.FilmEntry.COLUMN_NAME_RUNNING_TIME + " >= ? AND " + DBContract.FilmEntry.COLUMN_NAME_RUNNING_TIME + "<= ?";
                            String[] whereArgs = {timeMin, timeMax};

                            cursor = db.query(
                                    DBContract.FilmEntry.TABLE_NAME,   // The table to query
                                    projection,             // The array of columns to return (pass null to get all)
                                    where,                  // The columns for the WHERE clause
                                    whereArgs,              // The values for the WHERE clause
                                    null,                   // don't group the rows
                                    null,                   // don't filter by row groups
                                    sortOrder               // The sort order
                            );

                            while (cursor.moveToNext()) {
                                String titulo = cursor.getString(
                                        cursor.getColumnIndexOrThrow(DBContract.FilmEntry.COLUMN_NAME_TITLE));
                                String anyo = cursor.getString(
                                        cursor.getColumnIndexOrThrow(DBContract.FilmEntry.COLUMN_NAME_YEAR));
                                String fecha = cursor.getString(
                                        cursor.getColumnIndexOrThrow(DBContract.FilmEntry.COLUMN_NAME_VIEWINGS));
                                films.add(titulo + " [" + anyo + "]" + " (" + fecha + (")"));
                            }
                            cursor.close();
                        }else{
                            Toast.makeText(this, R.string.invalidInterval, Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(this, R.string.invalidInterval, Toast.LENGTH_LONG).show();
                    }
                }else{
                    if(timeMin.isEmpty() && timeMax.isEmpty()){
                        // 0010 B??squeda por g??nero
                        String where = DBContract.FilmEntry.COLUMN_NAME_GENRE + " LIKE ?";
                        String[] whereArgs = { "%" + genres + "%"};

                        cursor = db.query(
                                DBContract.FilmEntry.TABLE_NAME,   // The table to query
                                projection,             // The array of columns to return (pass null to get all)
                                where,                  // The columns for the WHERE clause
                                whereArgs,              // The values for the WHERE clause
                                null,                   // don't group the rows
                                null,                   // don't filter by row groups
                                sortOrder               // The sort order
                        );

                        while(cursor.moveToNext()) {
                            String titulo = cursor.getString(
                                    cursor.getColumnIndexOrThrow(DBContract.FilmEntry.COLUMN_NAME_TITLE));
                            String anyo = cursor.getString(
                                    cursor.getColumnIndexOrThrow(DBContract.FilmEntry.COLUMN_NAME_YEAR));
                            String fecha = cursor.getString(
                                    cursor.getColumnIndexOrThrow(DBContract.FilmEntry.COLUMN_NAME_VIEWINGS));
                            films.add(titulo+" ["+anyo+"]"+" ("+fecha+(")"));
                        }
                        cursor.close();
                    }else if (!timeMin.isEmpty() && !timeMax.isEmpty()){
                        // 0011 B??squeda por g??nero y duraci??n
                        if(Integer.parseInt(timeMin)<Integer.parseInt(timeMax)){
                            String where = DBContract.FilmEntry.COLUMN_NAME_RUNNING_TIME + " >= ? AND " + DBContract.FilmEntry.COLUMN_NAME_RUNNING_TIME + "<= ? AND " +
                                    DBContract.FilmEntry.COLUMN_NAME_GENRE + " LIKE ?";
                            String[] whereArgs = { timeMin, timeMax, "%" + genres + "%"};

                            cursor = db.query(
                                    DBContract.FilmEntry.TABLE_NAME,   // The table to query
                                    projection,             // The array of columns to return (pass null to get all)
                                    where,                  // The columns for the WHERE clause
                                    whereArgs,              // The values for the WHERE clause
                                    null,                   // don't group the rows
                                    null,                   // don't filter by row groups
                                    sortOrder               // The sort order
                            );

                            while (cursor.moveToNext()) {
                                String titulo = cursor.getString(
                                        cursor.getColumnIndexOrThrow(DBContract.FilmEntry.COLUMN_NAME_TITLE));
                                String anyo = cursor.getString(
                                        cursor.getColumnIndexOrThrow(DBContract.FilmEntry.COLUMN_NAME_YEAR));
                                String fecha = cursor.getString(
                                        cursor.getColumnIndexOrThrow(DBContract.FilmEntry.COLUMN_NAME_VIEWINGS));
                                films.add(titulo + " [" + anyo + "]" + " (" + fecha + (")"));
                            }
                            cursor.close();
                        }else{
                            Toast.makeText(this, R.string.invalidInterval, Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(this, R.string.invalidInterval, Toast.LENGTH_LONG).show();
                    }
                }
            }else{
                if(genres.isEmpty()){
                    if(timeMin.isEmpty() && timeMax.isEmpty()){
                        // 0100 B??squeda por a??o
                        String where = DBContract.FilmEntry.COLUMN_NAME_YEAR + "= ?";
                        String[] whereArgs = { year.toString() };

                        cursor = db.query(
                                DBContract.FilmEntry.TABLE_NAME,   // The table to query
                                projection,             // The array of columns to return (pass null to get all)
                                where,                  // The columns for the WHERE clause
                                whereArgs,              // The values for the WHERE clause
                                null,                   // don't group the rows
                                null,                   // don't filter by row groups
                                sortOrder               // The sort order
                        );

                        while(cursor.moveToNext()) {
                            String titulo = cursor.getString(
                                    cursor.getColumnIndexOrThrow(DBContract.FilmEntry.COLUMN_NAME_TITLE));
                            String anyo = cursor.getString(
                                    cursor.getColumnIndexOrThrow(DBContract.FilmEntry.COLUMN_NAME_YEAR));
                            String fecha = cursor.getString(
                                    cursor.getColumnIndexOrThrow(DBContract.FilmEntry.COLUMN_NAME_VIEWINGS));
                            films.add(titulo+" ["+anyo+"]"+" ("+fecha+(")"));
                        }
                        cursor.close();
                    }else if (!timeMin.isEmpty() && !timeMax.isEmpty()){
                        // 0101 B??squeda por a??o y duraci??n
                        if(Integer.parseInt(timeMin)<Integer.parseInt(timeMax)){
                            String where = DBContract.FilmEntry.COLUMN_NAME_RUNNING_TIME + " >= ? AND " + DBContract.FilmEntry.COLUMN_NAME_RUNNING_TIME + "<= ? AND " +
                                    DBContract.FilmEntry.COLUMN_NAME_YEAR + " = ?";
                            String[] whereArgs = { timeMin, timeMax, year};

                            cursor = db.query(
                                    DBContract.FilmEntry.TABLE_NAME,   // The table to query
                                    projection,             // The array of columns to return (pass null to get all)
                                    where,                  // The columns for the WHERE clause
                                    whereArgs,              // The values for the WHERE clause
                                    null,                   // don't group the rows
                                    null,                   // don't filter by row groups
                                    sortOrder               // The sort order
                            );

                            while (cursor.moveToNext()) {
                                String titulo = cursor.getString(
                                        cursor.getColumnIndexOrThrow(DBContract.FilmEntry.COLUMN_NAME_TITLE));
                                String anyo = cursor.getString(
                                        cursor.getColumnIndexOrThrow(DBContract.FilmEntry.COLUMN_NAME_YEAR));
                                String fecha = cursor.getString(
                                        cursor.getColumnIndexOrThrow(DBContract.FilmEntry.COLUMN_NAME_VIEWINGS));
                                films.add(titulo + " [" + anyo + "]" + " (" + fecha + (")"));
                            }
                            cursor.close();
                        }else{
                            Toast.makeText(this, R.string.invalidInterval, Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(this, R.string.invalidInterval, Toast.LENGTH_LONG).show();
                    }
                }else{
                    if(timeMin.isEmpty() && timeMax.isEmpty()){
                        // 0110 B??squeda por a??o y g??nero
                        String where = DBContract.FilmEntry.COLUMN_NAME_YEAR + " = ? AND " + DBContract.FilmEntry.COLUMN_NAME_GENRE + " LIKE ?";
                        String[] whereArgs = { year, "%" + genres + "%"};

                        cursor = db.query(
                                DBContract.FilmEntry.TABLE_NAME,   // The table to query
                                projection,             // The array of columns to return (pass null to get all)
                                where,                  // The columns for the WHERE clause
                                whereArgs,              // The values for the WHERE clause
                                null,                   // don't group the rows
                                null,                   // don't filter by row groups
                                sortOrder               // The sort order
                        );

                        while(cursor.moveToNext()) {
                            String titulo = cursor.getString(
                                    cursor.getColumnIndexOrThrow(DBContract.FilmEntry.COLUMN_NAME_TITLE));
                            String anyo = cursor.getString(
                                    cursor.getColumnIndexOrThrow(DBContract.FilmEntry.COLUMN_NAME_YEAR));
                            String fecha = cursor.getString(
                                    cursor.getColumnIndexOrThrow(DBContract.FilmEntry.COLUMN_NAME_VIEWINGS));
                            films.add(titulo + " [" + anyo + "]" + " (" + fecha + (")"));
                        }
                        cursor.close();
                    }else if (!timeMin.isEmpty() && !timeMax.isEmpty()){
                        // 0111 B??squeda por a??o, g??nero y duraci??n
                        if(Integer.parseInt(timeMin)<Integer.parseInt(timeMax)){
                            String where = DBContract.FilmEntry.COLUMN_NAME_RUNNING_TIME + " >= ? AND " + DBContract.FilmEntry.COLUMN_NAME_RUNNING_TIME + "<= ? AND " +
                                    DBContract.FilmEntry.COLUMN_NAME_YEAR + " = ? AND " + DBContract.FilmEntry.COLUMN_NAME_GENRE + " LIKE ?";
                            String[] whereArgs = { timeMin, timeMax, year, "%" + genres + "%"};

                            cursor = db.query(
                                    DBContract.FilmEntry.TABLE_NAME,   // The table to query
                                    projection,             // The array of columns to return (pass null to get all)
                                    where,                  // The columns for the WHERE clause
                                    whereArgs,              // The values for the WHERE clause
                                    null,                   // don't group the rows
                                    null,                   // don't filter by row groups
                                    sortOrder               // The sort order
                            );

                            while (cursor.moveToNext()) {
                                String titulo = cursor.getString(
                                        cursor.getColumnIndexOrThrow(DBContract.FilmEntry.COLUMN_NAME_TITLE));
                                String anyo = cursor.getString(
                                        cursor.getColumnIndexOrThrow(DBContract.FilmEntry.COLUMN_NAME_YEAR));
                                String fecha = cursor.getString(
                                        cursor.getColumnIndexOrThrow(DBContract.FilmEntry.COLUMN_NAME_VIEWINGS));
                                films.add(titulo + " [" + anyo + "]" + " (" + fecha + (")"));
                            }
                            cursor.close();
                        }else{
                            Toast.makeText(this, R.string.invalidInterval, Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(this, R.string.invalidInterval, Toast.LENGTH_LONG).show();
                    }
                }
            }
        }else{
            if(year.isEmpty()){
                if(genres.isEmpty()){
                    if(timeMin.isEmpty() && timeMax.isEmpty()){
                        // 1000 (B??squeda por t??tulo)
                        String where = DBContract.FilmEntry.COLUMN_NAME_TITLE + " LIKE ?";
                        String[] whereArgs = { "%" + title + "%"};

                        cursor = db.query(
                                DBContract.FilmEntry.TABLE_NAME,   // The table to query
                                projection,             // The array of columns to return (pass null to get all)
                                where,                  // The columns for the WHERE clause
                                whereArgs,              // The values for the WHERE clause
                                null,                   // don't group the rows
                                null,                   // don't filter by row groups
                                sortOrder               // The sort order
                        );

                        while(cursor.moveToNext()) {
                            String titulo = cursor.getString(
                                    cursor.getColumnIndexOrThrow(DBContract.FilmEntry.COLUMN_NAME_TITLE));
                            String anyo = cursor.getString(
                                    cursor.getColumnIndexOrThrow(DBContract.FilmEntry.COLUMN_NAME_YEAR));
                            String fecha = cursor.getString(
                                    cursor.getColumnIndexOrThrow(DBContract.FilmEntry.COLUMN_NAME_VIEWINGS));
                            films.add(titulo + " [" + anyo + "]" + " (" + fecha + (")"));
                        }
                        cursor.close();
                    }else if (!timeMin.isEmpty() && !timeMax.isEmpty()){
                        // 1001 B??squeda por t??tulo y duraci??n
                        if(Integer.parseInt(timeMin)<Integer.parseInt(timeMax)){
                            String where = DBContract.FilmEntry.COLUMN_NAME_RUNNING_TIME + " >= ? AND " + DBContract.FilmEntry.COLUMN_NAME_RUNNING_TIME + "<= ? AND " +
                                    DBContract.FilmEntry.COLUMN_NAME_TITLE + " LIKE ?";
                            String[] whereArgs = { timeMin, timeMax, "%" + title + "%"};

                            cursor = db.query(
                                    DBContract.FilmEntry.TABLE_NAME,   // The table to query
                                    projection,             // The array of columns to return (pass null to get all)
                                    where,                  // The columns for the WHERE clause
                                    whereArgs,              // The values for the WHERE clause
                                    null,                   // don't group the rows
                                    null,                   // don't filter by row groups
                                    sortOrder               // The sort order
                            );

                            while (cursor.moveToNext()) {
                                String titulo = cursor.getString(
                                        cursor.getColumnIndexOrThrow(DBContract.FilmEntry.COLUMN_NAME_TITLE));
                                String anyo = cursor.getString(
                                        cursor.getColumnIndexOrThrow(DBContract.FilmEntry.COLUMN_NAME_YEAR));
                                String fecha = cursor.getString(
                                        cursor.getColumnIndexOrThrow(DBContract.FilmEntry.COLUMN_NAME_VIEWINGS));
                                films.add(titulo + " [" + anyo + "]" + " (" + fecha + (")"));
                            }
                            cursor.close();
                        }else{
                            Toast.makeText(this, R.string.invalidInterval, Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(this, R.string.invalidInterval, Toast.LENGTH_LONG).show();
                    }
                }else{
                    if(timeMin.isEmpty() && timeMax.isEmpty()){
                        // 1010 B??squeda por t??tulo y g??nero
                        String where = DBContract.FilmEntry.COLUMN_NAME_TITLE + " LIKE ? AND " + DBContract.FilmEntry.COLUMN_NAME_GENRE + " LIKE ?" ;
                        String[] whereArgs = { "%" + title + "%", "%" + genres + "%"};

                        cursor = db.query(
                                DBContract.FilmEntry.TABLE_NAME,   // The table to query
                                projection,             // The array of columns to return (pass null to get all)
                                where,                  // The columns for the WHERE clause
                                whereArgs,              // The values for the WHERE clause
                                null,                   // don't group the rows
                                null,                   // don't filter by row groups
                                sortOrder               // The sort order
                        );

                        while(cursor.moveToNext()) {
                            String titulo = cursor.getString(
                                    cursor.getColumnIndexOrThrow(DBContract.FilmEntry.COLUMN_NAME_TITLE));
                            String anyo = cursor.getString(
                                    cursor.getColumnIndexOrThrow(DBContract.FilmEntry.COLUMN_NAME_YEAR));
                            String fecha = cursor.getString(
                                    cursor.getColumnIndexOrThrow(DBContract.FilmEntry.COLUMN_NAME_VIEWINGS));
                            films.add(titulo + " [" + anyo + "]" + " (" + fecha + (")"));
                        }
                        cursor.close();
                    }else if (!timeMin.isEmpty() && !timeMax.isEmpty()){
                        // 1011 B??squeda por t??tulo, g??nero y duraci??n
                        if(Integer.parseInt(timeMin)<Integer.parseInt(timeMax)){
                            String where = DBContract.FilmEntry.COLUMN_NAME_RUNNING_TIME + " >= ? AND " + DBContract.FilmEntry.COLUMN_NAME_RUNNING_TIME + "<= ? AND " +
                                    DBContract.FilmEntry.COLUMN_NAME_TITLE + " LIKE ? AND " + DBContract.FilmEntry.COLUMN_NAME_GENRE + " LIKE ?";
                            String[] whereArgs = { timeMin, timeMax, "%" + title + "%", "%" + genres + "%"};

                            cursor = db.query(
                                    DBContract.FilmEntry.TABLE_NAME,   // The table to query
                                    projection,             // The array of columns to return (pass null to get all)
                                    where,                  // The columns for the WHERE clause
                                    whereArgs,              // The values for the WHERE clause
                                    null,                   // don't group the rows
                                    null,                   // don't filter by row groups
                                    sortOrder               // The sort order
                            );

                            while (cursor.moveToNext()) {
                                String titulo = cursor.getString(
                                        cursor.getColumnIndexOrThrow(DBContract.FilmEntry.COLUMN_NAME_TITLE));
                                String anyo = cursor.getString(
                                        cursor.getColumnIndexOrThrow(DBContract.FilmEntry.COLUMN_NAME_YEAR));
                                String fecha = cursor.getString(
                                        cursor.getColumnIndexOrThrow(DBContract.FilmEntry.COLUMN_NAME_VIEWINGS));
                                films.add(titulo + " [" + anyo + "]" + " (" + fecha + (")"));
                            }
                            cursor.close();
                        }else{
                            Toast.makeText(this, R.string.invalidInterval, Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(this, R.string.invalidInterval, Toast.LENGTH_LONG).show();
                    }
                }
            }else{
                if(genres.isEmpty()){
                    if(timeMin.isEmpty() && timeMax.isEmpty()){
                        // 1100 B??squeda por t??tulo y a??o
                        String where = DBContract.FilmEntry.COLUMN_NAME_TITLE + " LIKE ? AND " + DBContract.FilmEntry.COLUMN_NAME_YEAR + " = ?" ;
                        String[] whereArgs = { "%" + title + "%", year};

                        cursor = db.query(
                                DBContract.FilmEntry.TABLE_NAME,   // The table to query
                                projection,             // The array of columns to return (pass null to get all)
                                where,                  // The columns for the WHERE clause
                                whereArgs,              // The values for the WHERE clause
                                null,                   // don't group the rows
                                null,                   // don't filter by row groups
                                sortOrder               // The sort order
                        );

                        while(cursor.moveToNext()) {
                            String titulo = cursor.getString(
                                    cursor.getColumnIndexOrThrow(DBContract.FilmEntry.COLUMN_NAME_TITLE));
                            String anyo = cursor.getString(
                                    cursor.getColumnIndexOrThrow(DBContract.FilmEntry.COLUMN_NAME_YEAR));
                            String fecha = cursor.getString(
                                    cursor.getColumnIndexOrThrow(DBContract.FilmEntry.COLUMN_NAME_VIEWINGS));
                            films.add(titulo + " [" + anyo + "]" + " (" + fecha + (")"));
                        }
                        cursor.close();
                    }else if (!timeMin.isEmpty() && !timeMax.isEmpty()){
                        // 1101 B??squeda por t??tulo, a??o y duraci??n
                        if(Integer.parseInt(timeMin)<Integer.parseInt(timeMax)){
                            String where = DBContract.FilmEntry.COLUMN_NAME_RUNNING_TIME + " >= ? AND " + DBContract.FilmEntry.COLUMN_NAME_RUNNING_TIME + "<= ? AND " +
                                    DBContract.FilmEntry.COLUMN_NAME_TITLE + " LIKE ? AND " + DBContract.FilmEntry.COLUMN_NAME_YEAR + " = ?";
                            String[] whereArgs = { timeMin, timeMax, "%" + title + "%", year};

                            cursor = db.query(
                                    DBContract.FilmEntry.TABLE_NAME,   // The table to query
                                    projection,             // The array of columns to return (pass null to get all)
                                    where,                  // The columns for the WHERE clause
                                    whereArgs,              // The values for the WHERE clause
                                    null,                   // don't group the rows
                                    null,                   // don't filter by row groups
                                    sortOrder               // The sort order
                            );

                            while (cursor.moveToNext()) {
                                String titulo = cursor.getString(
                                        cursor.getColumnIndexOrThrow(DBContract.FilmEntry.COLUMN_NAME_TITLE));
                                String anyo = cursor.getString(
                                        cursor.getColumnIndexOrThrow(DBContract.FilmEntry.COLUMN_NAME_YEAR));
                                String fecha = cursor.getString(
                                        cursor.getColumnIndexOrThrow(DBContract.FilmEntry.COLUMN_NAME_VIEWINGS));
                                films.add(titulo + " [" + anyo + "]" + " (" + fecha + (")"));
                            }
                            cursor.close();
                        }else{
                            Toast.makeText(this, R.string.invalidInterval, Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(this, R.string.invalidInterval, Toast.LENGTH_LONG).show();
                    }
                }else{
                    if(timeMin.isEmpty() && timeMax.isEmpty()){
                        // 1110 Buscar por t??tulo, a??o y g??nero
                        String where = DBContract.FilmEntry.COLUMN_NAME_TITLE + " LIKE ? AND " + DBContract.FilmEntry.COLUMN_NAME_YEAR + " = ? AND " +
                                DBContract.FilmEntry.COLUMN_NAME_GENRE + " LIKE ?";
                        String[] whereArgs = { "%" + title + "%", year, "%" + genres + "%"};

                        cursor = db.query(
                                DBContract.FilmEntry.TABLE_NAME,   // The table to query
                                projection,             // The array of columns to return (pass null to get all)
                                where,                  // The columns for the WHERE clause
                                whereArgs,              // The values for the WHERE clause
                                null,                   // don't group the rows
                                null,                   // don't filter by row groups
                                sortOrder               // The sort order
                        );

                        while(cursor.moveToNext()) {
                            String titulo = cursor.getString(
                                    cursor.getColumnIndexOrThrow(DBContract.FilmEntry.COLUMN_NAME_TITLE));
                            String anyo = cursor.getString(
                                    cursor.getColumnIndexOrThrow(DBContract.FilmEntry.COLUMN_NAME_YEAR));
                            String fecha = cursor.getString(
                                    cursor.getColumnIndexOrThrow(DBContract.FilmEntry.COLUMN_NAME_VIEWINGS));
                            films.add(titulo + " [" + anyo + "]" + " (" + fecha + (")"));
                        }
                        cursor.close();
                    }else if (!timeMin.isEmpty() && !timeMax.isEmpty()){
                        // 1111 B??squeda por t??tulo, a??o, g??nero y duraci??n
                        if(Integer.parseInt(timeMin)<Integer.parseInt(timeMax)){
                            String where = DBContract.FilmEntry.COLUMN_NAME_RUNNING_TIME + " >= ? AND " + DBContract.FilmEntry.COLUMN_NAME_RUNNING_TIME + "<= ? AND " +
                                    DBContract.FilmEntry.COLUMN_NAME_TITLE + " LIKE ? AND " + DBContract.FilmEntry.COLUMN_NAME_YEAR + " = ? AND " +
                                    DBContract.FilmEntry.COLUMN_NAME_GENRE + " LIKE ?";
                            String[] whereArgs = { timeMin, timeMax, "%" + title + "%", year, "%" + genres + "%"};

                            cursor = db.query(
                                    DBContract.FilmEntry.TABLE_NAME,   // The table to query
                                    projection,             // The array of columns to return (pass null to get all)
                                    where,                  // The columns for the WHERE clause
                                    whereArgs,              // The values for the WHERE clause
                                    null,                   // don't group the rows
                                    null,                   // don't filter by row groups
                                    sortOrder               // The sort order
                            );

                            while (cursor.moveToNext()) {
                                String titulo = cursor.getString(
                                        cursor.getColumnIndexOrThrow(DBContract.FilmEntry.COLUMN_NAME_TITLE));
                                String anyo = cursor.getString(
                                        cursor.getColumnIndexOrThrow(DBContract.FilmEntry.COLUMN_NAME_YEAR));
                                String fecha = cursor.getString(
                                        cursor.getColumnIndexOrThrow(DBContract.FilmEntry.COLUMN_NAME_VIEWINGS));
                                films.add(titulo + " [" + anyo + "]" + " (" + fecha + (")"));
                            }
                            cursor.close();
                        }else{
                            Toast.makeText(this, R.string.invalidInterval, Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(this, R.string.invalidInterval, Toast.LENGTH_LONG).show();
                    }
                }
            }
        }


        listView = findViewById(R.id.listView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,films);
        listView.setAdapter(adapter);
        hideSoftKeyboard(listView);

    }

}