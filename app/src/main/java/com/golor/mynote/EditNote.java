package com.golor.mynote;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditNote extends AppCompatActivity {
    private EditText et_title, et_content;
    private NotesCRUD notesCRUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        //connect database
        notesCRUD = new NotesCRUD(this);

        // bind edit_layout widget
        et_title = findViewById(R.id.et_title);
        et_content = findViewById(R.id.et_content);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // listen the back_key
        if (keyCode == KeyEvent.KEYCODE_BACK) {//返回键
            Note note = new Note(et_title.getText().toString(), et_content.getText().toString(), dateStr());
            notesCRUD.open();
            notesCRUD.addNote(note);
            notesCRUD.close();
            // Toast remind
            Toast.makeText(this, "Record to save", Toast.LENGTH_SHORT).show();
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }


    public String dateStr() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }
}