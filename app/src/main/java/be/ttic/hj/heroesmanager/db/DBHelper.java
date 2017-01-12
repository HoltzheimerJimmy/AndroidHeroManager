package be.ttic.hj.heroesmanager.db;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jimmy on 08/12/2016.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "heroes_manager_db";
    public static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    //Constructeur corrigé
/*    public DBHelper(Context context, String DB_NAME, SQLiteDatabase.CursorFactory factory, int DB_VERSION) {
        super(context, DB_NAME, factory, DB_VERSION);
    }*/

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(HeroDAO.CREATE_REQUEST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(HeroDAO.UPGRADE_REQUEST);
        onCreate(db);
    }
}
