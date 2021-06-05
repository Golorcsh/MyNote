package com.golor.mynote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class EditNote extends AppCompatActivity {
    private EditText et_title, et_content;
    private Note note;
    private NotesCRUD notesCRUD;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        //connect database
        notesCRUD = new NotesCRUD(this);

        // bind edit_layout widget
        et_title = findViewById(R.id.et_title);
        et_content = findViewById(R.id.et_content);

        toolbar = (Toolbar) findViewById(R.id.note_toolbar);
        toolbar.setTitle("Note");
        toolbar.setSubtitle("write something");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_note_item, menu);
        return true;
    }

    // toolbar item listener
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        note = new Note(et_title.getText().toString(), et_content.getText().toString(), utils.dateStr());
        switch (item.getItemId()) {
            case R.id.note_toolbar_save:
                // User chose the "Settings" item, show the app settings UI...
                utils.save(this, note);
                finish();
                return true;

            case R.id.note_toolbar_share:
                utils.save(this, note);
                utils.share(this, note);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // listen the back_key
        if (keyCode == KeyEvent.KEYCODE_BACK) {//返回键
            Note note = new Note(et_title.getText().toString(), et_content.getText().toString(), utils.dateStr());
            utils.save(this, note);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }


}