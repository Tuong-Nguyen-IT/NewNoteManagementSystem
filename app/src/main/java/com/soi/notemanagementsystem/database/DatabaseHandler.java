package com.soi.notemanagementsystem.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.soi.notemanagementsystem.model.Category;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "NMS_DB";
    // tables list
    private static final String TABLE_CATEGORY = "category";
    private static final String TABLE_PRIORITY = "priority";
    private static final String TABLE_STATUS = "status";
    private static final String TABLE_NODE = "note";
    private static final String TABLE_USER = "user";

    // key words

    private static final String FRIMARY_KEY = "primary key";

    // version
    private static final int DATABASE_VERSION = 1;

    //init table category columns name
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String CREATE_DATE = "create_date";

    //init table priority columns name

    //init table status columns name

    //init table note columns name

    //init table user columns name



    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("EE", "onCreate: eeee");
        String createCategoryTable = String.format("create table %s(" +
                "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "%s TEXT , " +
                "%s TEXT)", TABLE_CATEGORY,ID, NAME, CREATE_DATE
        );
        db.execSQL(createCategoryTable);


    }

    public void addCategory(Category category) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, category.getName());
        contentValues.put(CREATE_DATE, category.getCreateDateFomated());
        db.insert(TABLE_CATEGORY,null, contentValues);
        db.close();
    }

    public ArrayList<Category> getAllCategories(){
        ArrayList<Category> categoriesList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_CATEGORY;
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Category category = new Category(cursor.getInt(0), cursor.getString(1), sdf.parse(cursor.getString(2)));
                categoriesList.add(category);
            } catch (ParseException e) {
                Log.e("ERROR", "getAllCategories: " + e.getMessage());e.printStackTrace();
            }
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return categoriesList;

    }

    public int editCateGory(Category category) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, category.getName());
        return db.update(TABLE_CATEGORY,contentValues,ID + "=?", new String[]{String.valueOf(category.getId())});

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
