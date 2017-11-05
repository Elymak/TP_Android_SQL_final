package com.breuzon.tpsql.bdd.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.breuzon.tpsql.bdd.MySQLiteHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by serial on 30/10/2017.
 *
 * Cette classe est utile si on veut créer une base avec plusieurs tables
 *
 */

public abstract class MyAbstractDao<M> {

    // Champs de la base de données
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    MyAbstractDao(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public abstract M createNewModel(M model);

    public abstract void deleteModel(M model);

    List<M> getAllModel(String tableName, String[] columns){
        List<M> models = new ArrayList<>();

        Cursor cursor = database.query(tableName, columns, null, null, null, null, null);
        //Cursor cursor = database.rawQuery("select * from ?", new String[]{tableName});

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            M comment = cursorToModel(cursor);
            models.add(comment);
            cursor.moveToNext();
        }
        //fermeture du curseur
        cursor.close();
        return models;
    }

    protected abstract M cursorToModel(Cursor cursor);

    public abstract List<M> getAllModel();

    SQLiteDatabase getDatabase() {
        return database;
    }
}