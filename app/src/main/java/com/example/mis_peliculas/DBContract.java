package com.example.mis_peliculas;

import android.provider.BaseColumns;

public final class DBContract {
    private DBContract() {
    }

    public static abstract class FilmEntry implements BaseColumns {
        public static final String TABLE_NAME = "Peliculas";
        public static final String COLUMN_NAME_TITLE = "titulo";
        public static final String COLUMN_NAME_ORIG_TITLE = "titulo_original";
        public static final String COLUMN_NAME_YEAR = "anyo";
        public static final String COLUMN_NAME_RUNNING_TIME = "duracion";
        public static final String COLUMN_NAME_GENRE = "generos";
        public static final String COLUMN_NAME_VIEWINGS = "visionados";
    }
}
