package com.example.meds;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context ctx;
    private static final String DATABASE_NAME = "db_medicine";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "medicine";
    private static final String FIELD_ID = "id";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_QUANTITY = "quantity";
    private static final String FIELD_PRICE = "price";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
        this.ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FIELD_NAME + " VARCHAR(50)," +
                FIELD_QUANTITY + " TEXT," +
                FIELD_PRICE + " INTEGER ); "
                ;

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long addMedicine(String name, String quantity, String price){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date date = inputFormat.parse(quantity);
            String formattedDate = outputFormat.format(date);
            cv.put(FIELD_NAME, name);
            cv.put(FIELD_QUANTITY, formattedDate);
            cv.put(FIELD_PRICE, Integer.parseInt(price));

        } catch (ParseException e) {
            e.printStackTrace();
        }




        long status = db.insert(TABLE_NAME, null, cv);
        return status;
    }

    public Cursor displayMedicine(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + FIELD_QUANTITY + " ASC;";


        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    public long deleteMedicine(String id){
        SQLiteDatabase db = this.getWritableDatabase();

        long status = db.delete(TABLE_NAME, "id = ?", new String[]{id});

        return status;
    }

    public long updateMedicine(String id, String name, String quantity, String price){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(FIELD_NAME, name);
        cv.put(FIELD_QUANTITY, quantity);
        cv.put(FIELD_PRICE, price);

        long status = db.update(TABLE_NAME, cv, "id = ?", new String[]{id});
        return status;
    }
}
