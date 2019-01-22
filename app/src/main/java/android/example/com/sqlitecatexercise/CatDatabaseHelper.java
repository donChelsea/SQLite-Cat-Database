package android.example.com.sqlitecatexercise;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class CatDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "cats.db";
    private static final String TABLE_NAME = "cats";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "CatDatabaseHelper";

    public CatDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT, breed TEXT, age INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addCat(Cat cat) {
        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT * FROM " + TABLE_NAME + " WHERE name = '" + cat.getName() +
                     "' AND breed = '" + cat.getBreed() + "' AND age = '" + cat.getAge() + "';'", null);
        if (cursor.getCount() == 0) {
            getWritableDatabase().execSQL("INSERT INTO " + TABLE_NAME +
                    "(name, breed, age) VALUES('" +
                    cat.getName() + "', '" +
                    cat.getBreed() + "', '" +
                    cat.getAge() + "');");
        }
        Log.d(TAG, "addCat: " + cat.getName() + cat.getBreed() + cat.getAge());
        cursor.close();
    }

    public List<Cat> getCatList() {
        List<Cat> catList = new ArrayList<>();
        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT * FROM " + TABLE_NAME + ";", null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Cat cat = new Cat(
                            cursor.getString(cursor.getColumnIndex("name")),
                            cursor.getString(cursor.getColumnIndex("breed")),
                            cursor.getInt(cursor.getColumnIndex("age")));
                    catList.add(cat);
                } while (cursor.moveToNext());
            }
        }
        return catList;
    }

    public void removeCat(Cat cat) {
        Cursor cursor = getReadableDatabase().rawQuery(
                "DELETE FROM " + TABLE_NAME + " WHERE name = '" +
                        cat.getName() + "' AND breed = '" +
                        cat.getBreed() + "' AND age = '" +
                        cat.getAge() + "';'", null);

        Log.d(TAG, "removeCat: " + cat.getName());
    }
}
