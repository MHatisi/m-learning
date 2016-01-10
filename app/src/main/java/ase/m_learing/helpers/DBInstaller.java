package ase.m_learing.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by hatisimihai on 10/01/16.
 */
public class DBInstaller {

    SQLiteDatabase db;
    private final Context appContext;

    DBInstaller(Context appContext, SQLiteDatabase database) {
        this.db = database;
        this.appContext = appContext;
    }

    public void run() {
        this.createCategoriesTable();
        this.createArticlesTable();

        try {
            this.populateCategories();
            this.populateArticles();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void clean() {
        this.dropArticlesTable();
        this.dropCategoriesTable();
    }

    protected void createCategoriesTable() {
        this.db.execSQL("CREATE TABLE `categories` (\n" +
                "\t`id`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
                "\t`name`\tVARCHAR NOT NULL\n" +
                ");\n");
    }

    protected void dropCategoriesTable() {
        db.execSQL("DROP TABLE IF EXISTS categories");
    }


    protected void createArticlesTable() {
        this.db.execSQL("CREATE TABLE `articles` (\n" +
                "\t`id`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
                "\t`categoryId`\tINTEGER NOT NULL,\n" +
                "\t`title`\tINTEGER NOT NULL,\n" +
                "\t`content`\tTEXT\n" +
                ");");
    }

    protected void dropArticlesTable() {
        db.execSQL("DROP TABLE IF EXISTS articles");
    }


    protected void populateCategories() throws JSONException {
        String jsonData = this.loadJSONFromAsset("categories.json");

        JSONArray jsonArray = new JSONArray(jsonData);
        for (int i = 0; i < jsonArray.length(); i++) {
            this.addCategory(jsonArray.getJSONObject(i).getString("name"));
        }

    }

    protected void populateArticles() throws JSONException {
        String jsonData = this.loadJSONFromAsset("articles.json");

        JSONArray jsonArray = new JSONArray(jsonData);
        for (int i = 0; i < jsonArray.length(); i++) {
            this.addArticle(jsonArray.getJSONObject(i).getInt("categoryID"), jsonArray.getJSONObject(i).getString("title"), jsonArray.getJSONObject(i).getString("content"));
        }

    }


    protected void addCategory(String categoryName) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", categoryName);
        db.insert("categories", null, contentValues);

    }

    protected void addArticle(int categoryID, String articleTitle, String articleContent) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("categoryId", categoryID);
        contentValues.put("title", articleTitle);
        contentValues.put("content", articleContent);
        db.insert("articles", null, contentValues);
    }

    public String loadJSONFromAsset(String fileName) {
        String json = null;
        try {

            InputStream is = appContext.getAssets().open(fileName);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

}
