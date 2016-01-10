package ase.m_learing.helpers;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "mLearning.db";
    public static final String DATABASE_SQL = "mLearning.sql";

    private static final String CATEGORIES_TABLE_NAME = "categories";
    private static final String CATEGORIES_COLUMN_NAME = "name";
    private static final String CATEGORIES_COLUMN_ID = "id";

    private static final String ARTICLES_TABLE_NAME = "articles";
    private static final String ARTICLES_COLUMN_CATEGORYID = "categoryId";
    private static final String ARTICLES_COLUMN_ID = "id";

    private final Context myContext;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 5);
        this.myContext = context;
    }

    @Override
    //Sunt create tabelele sql si importate date din json
    public void onCreate(SQLiteDatabase db) {
        DBInstaller dbInstaller = new DBInstaller(this.myContext, db);
        dbInstaller.run();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        DBInstaller dbInstaller = new DBInstaller(this.myContext, db);
        dbInstaller.clean();
        dbInstaller.run();
    }

    public ArrayList<String> getAllCategories() {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CATEGORIES_TABLE_NAME, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex(CATEGORIES_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> getAllArticlesByCategory(int categoryID) {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + ARTICLES_TABLE_NAME + " WHERE " + ARTICLES_COLUMN_CATEGORYID + " = " + categoryID, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex(ARTICLES_COLUMN_ID)));
            res.moveToNext();
        }
        return array_list;
    }

    protected String queryBuilder(InputStream myStream) {

        BufferedReader r = new BufferedReader(new InputStreamReader(myStream));
        StringBuilder total = new StringBuilder();
        String line;
        try {
            while ((line = r.readLine()) != null) {
                total.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return total.toString();
    }


}
