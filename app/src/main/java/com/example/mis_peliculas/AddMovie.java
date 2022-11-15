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

public class AddMovie extends AppCompatActivity {

    // Atributos para manejar la BD
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    private EditText editTextFilmTitle;
    private EditText editTextFilmOriginalTitle;
    private EditText editTextFilmYear;
    private EditText editTextRunningTime;
    private EditText editTextFilmGenres;
    private EditText editTextFilmViewingDates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        dbHelper = new DBHelper(getApplicationContext(), "peliculas5.db");
        db = dbHelper.getWritableDatabase();

        editTextFilmTitle = (EditText) findViewById(R.id.editTextFilmTitle);
        editTextFilmOriginalTitle = (EditText) findViewById(R.id.editTextFilmOriginalTitle);
        editTextFilmYear = (EditText) findViewById(R.id.editTextFilmYear);
        editTextRunningTime = (EditText) findViewById(R.id.editTextRunningTime);
        editTextFilmGenres = (EditText) findViewById(R.id.editTextFilmGenres);
        editTextFilmViewingDates = (EditText) findViewById(R.id.editTextFilmViewingDates);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    public void onClick(View view){
        switch(view.getId()){
            case R.id.buttonSave:SaveFilm(); break;
        }
    }

    public void SaveFilm () {
        String title = editTextFilmTitle.getText().toString();
        String original_title = editTextFilmOriginalTitle.getText().toString();
        String year = editTextFilmYear.getText().toString();
        String running_time = editTextRunningTime.getText().toString();
        String genres = editTextFilmGenres.getText().toString();
        String viewing_date = editTextFilmViewingDates.getText().toString();
        
        if (title.isEmpty() || original_title.isEmpty() || year.isEmpty() || 
            running_time.isEmpty() || genres.isEmpty() || viewing_date.isEmpty()) {
            // Hay que rellenar todos los campos
            Toast.makeText(this, R.string.emptyFields, Toast.LENGTH_LONG).show();
        } else {
            Scanner sc = new Scanner(viewing_date);
            sc.useDelimiter("[/-]");
            int [] separateDate = new int[3];
            int cont = 0;
            while(cont<separateDate.length && sc.hasNext()){
                separateDate[cont] = sc.nextInt();
                cont++;
            }
            Date date = new Date(separateDate[0], separateDate[1], separateDate[2]);
            if (!date.validate()){
                Toast.makeText(this, R.string.invalidDate, Toast.LENGTH_LONG).show();
            }else {
                ContentValues values = new ContentValues();
                values.put(DBContract.FilmEntry.COLUMN_NAME_TITLE, title.toUpperCase());
                values.put(DBContract.FilmEntry.COLUMN_NAME_ORIG_TITLE, original_title.toUpperCase());
                values.put(DBContract.FilmEntry.COLUMN_NAME_YEAR, Integer.parseInt(year));
                values.put(DBContract.FilmEntry.COLUMN_NAME_RUNNING_TIME, Integer.parseInt(running_time));
                values.put(DBContract.FilmEntry.COLUMN_NAME_GENRE, genres.toUpperCase());
                String dayTextFromInt = "";
                String monthTextFromInt = "";
                if(separateDate[0]<10) {
                    dayTextFromInt = "0" + separateDate[0];
                }else{
                    dayTextFromInt = "" + separateDate[0];
                }
                if(separateDate[1]<10) {
                    monthTextFromInt = "0" + separateDate[1];
                }else{
                    monthTextFromInt = "" + separateDate[1];
                }
                String finalDate = separateDate[2] + "/" + monthTextFromInt + "/" + dayTextFromInt;
                values.put(DBContract.FilmEntry.COLUMN_NAME_VIEWINGS, finalDate);

                long id = db.insert(DBContract.FilmEntry.TABLE_NAME, null, values);

                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
            }
        }
    }
}