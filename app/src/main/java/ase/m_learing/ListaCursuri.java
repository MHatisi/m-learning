package ase.m_learing;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

public class ListaCursuri extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_cursuri);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Intent receivedIntent = getIntent();
        String categoryName = receivedIntent.getStringExtra("categoryName");
        String categoryId = receivedIntent.getStringExtra("categoryId");

        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase("database.sqlite", null, 0); //TODO real path for database
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT title, id FROM articles WHERE categoryId=" + categoryId, null);
        if (cursor != null && cursor.getCount() > 0) {
            LinearLayout layout = new LinearLayout(this);
            while(cursor.moveToNext()) {
                final String articleId = cursor.getString(cursor.getColumnIndex("id"));
                final String articleTitle = cursor.getString(cursor.getColumnIndex("title"));

                Button btnTag = new Button(this);
                btnTag.setLayoutParams(new WindowManager.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                btnTag.setText(articleTitle);
                btnTag.setId(cursor.getPosition());
                btnTag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(this, AfiseazaCurs.class);
                        intent.putExtra("articleId", articleId);
                        startActivity(intent);
                    }
                });
                layout.addView(btnTag);
            }
    }

}
