package com.golor.mynote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private NotesCRUD notesCRUD;
    private NoteAdapter adapter;
    private FloatingActionButton addBtn;
    private ListView lv;
    private EditText search;
    private Intent intent;

    private List<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView() {
        // load database
        notesCRUD = new NotesCRUD(this);
        //bind widget
        lv = findViewById(R.id.listView);
        addBtn = findViewById(R.id.fb_addButton);
        search = findViewById(R.id.search);
        // set listener
        addBtn.setOnClickListener(this);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, SelectItem.class);
                intent.putExtra(NotesDB.ID, notes.get(position).getId());
                intent.putExtra(NotesDB.TITLE, notes.get(position).getTitle());
                intent.putExtra(NotesDB.CONTENT, notes.get(position).getContent());
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View v) {
        intent = new Intent(this, EditNote.class);
        intent.putExtra("Flag", '1');
//        Toast.makeText(this, "跳转到EditNote页面", Toast.LENGTH_LONG).show();
        startActivity(intent);
    }

    public void loadAdapter() {
        notesCRUD.open();
        notes = notesCRUD.getAllNote();
        notesCRUD.close();
        adapter = new NoteAdapter(this, notes);
        lv.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAdapter();
    }
}