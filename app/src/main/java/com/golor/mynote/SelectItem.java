package com.golor.mynote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SelectItem extends AppCompatActivity implements View.OnClickListener {
    private EditText et_title, et_content;
    private Toolbar toolbar;
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
        toolbar = (Toolbar) findViewById(R.id.note_edit_toolbar);
        toolbar.setTitle("Note");
        toolbar.setSubtitle("write something");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // get value from intent
        title = getIntent().getStringExtra(NotesDB.TITLE);
        content = getIntent().getStringExtra(NotesDB.CONTENT);
        id = getIntent().getLongExtra(NotesDB.ID, 0);
        //set value to widget
        et_title.setText(title);
        et_content.setText(content);

        // set listener
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_note_edit_item, menu);
        return true;
    }

    // toolbar item listener
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Note note = new Note(title, content);
        note.setId(id);
        switch (item.getItemId()) {
            case R.id.note_toolbar_save:
                // User chose the "Settings" item, show the app settings UI...
                if (titleChanged || contentChanged) {
                    utils.re_save(this, note);
                }
                finish();
                return true;

            case R.id.note_toolbar_share:
                if (titleChanged || contentChanged) {
                    utils.re_save(this, note);
                }
                utils.share(this, note);
                return true;

            case R.id.note_toolbar_delete:
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(500);//震动3秒
                notesCRUD.open();
                notesCRUD.deleteNoteByID(id);
                notesCRUD.close();
                finish();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onClick(View v) {
        //listener
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // listen the back_key
        if (keyCode == KeyEvent.KEYCODE_BACK) {//返回键
            if (titleChanged || contentChanged) {
                Note note = new Note(title, content);
                note.setId(id);
                utils.re_save(this, note);
            }
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

}