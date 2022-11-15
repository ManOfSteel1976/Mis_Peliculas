package com.example.mis_peliculas;

public class Date {
    private final int day ;
    private final int month ;
    private final int year ;

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public Date(int day, int month, int year) {
        this.day = day ;
        this.month = month ;
        this.year = year ;
    }

    private boolean esBisiesto(int anyo){
        boolean bisiesto = false;
        if (anyo % 100 == 0) {
            if (anyo % 400 == 0) {
                bisiesto = true;
            }else{
                bisiesto = false;
            }
        }else {
            if (anyo % 4 == 0) {
                bisiesto = true;
            }
        }
        return bisiesto;
    }

    private int diasPorMes(int mes){
        int res = 31;
        if (mes == 4 || mes == 6 || mes == 9 || mes == 11){
            res = 30;
        }else if(mes == 2){
            if (esBisiesto(this.year)){
                res = 29;
            }else{
                res = 28;
            }
        }
        return res;
    }

    public boolean validate() {
        boolean validDate = true;
        if (getYear() < 1900 || getYear() > 2100){
            validDate = false;
        }
        if (getMonth()<1 || getMonth()>12){
            validDate = false;
        }
        if (getDay()<1 || getDay()>diasPorMes(getMonth())){
            validDate = false;
        }
        return validDate;
    }
}
