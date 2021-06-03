package com.golor.mynote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class SelectItem extends AppCompatActivity implements View.OnClickListener {
    private EditText et_title, et_content;
    private FloatingActionButton deleteBtn;
    private NotesCRUD notesCRUD;
    private String title, content;
    private long id;
    private Boolean titleChanged = false;
    private Boolean contentChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_item);
        notesCRUD = new NotesCRUD(this);
        // bind widget
        et_title = findViewById(R.id.et_title_item);
        et_content = findViewById(R.id.et_content_item);
        deleteBtn = findViewById(R.id.fb_deleteButton_item);

        // get value from intent
        title = getIntent().getStringExtra(NotesDB.TITLE);
        content = getIntent().getStringExtra(NotesDB.CONTENT);
        id = getIntent().getLongExtra(NotesDB.ID, 0);
        //set value to widget
        et_title.setText(title);
        et_content.setText(content);

        // set listener
        deleteBtn.setOnClickListener(this);

        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                content = et_content.getText().toString();
                contentChanged = true;
            }
        });

        et_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                title = et_title.getText().toString();
                titleChanged = true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // delete button
            case R.id.fb_deleteButton_item:
                notesCRUD.open();
                notesCRUD.deleteNoteByID(id);
                notesCRUD.close();
                finish();
                break;
            case 0:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // listen the back_key
        if (keyCode == KeyEvent.KEYCODE_BACK) {//返回键
            if (titleChanged || contentChanged) {
                re_save();
            }
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void re_save() {
        Note note = new Note(title, content);
        note.setId(id);
        notesCRUD.open();
        notesCRUD.updateNote(note);
        // Toast remind
        Toast.makeText(this, "Changed to save", Toast.LENGTH_SHORT).show();
        notesCRUD.close();
    }
}