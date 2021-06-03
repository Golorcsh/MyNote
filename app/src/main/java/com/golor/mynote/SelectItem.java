package com.golor.mynote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SelectItem extends AppCompatActivity implements View.OnClickListener {
    private EditText et_title, et_content;
    private FloatingActionButton deleteBtn;
    private NotesCRUD notesCRUD;
    private String title, content;
    private long id;

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
}