package com.golor.mynote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView() {
        lv = findViewById(R.id.listView);
        addBtn = findViewById(R.id.fb_addButton);
        search = findViewById(R.id.search);

        addBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        intent = new Intent(this, EditNote.class);
        intent.putExtra("Flag", '1');
        Toast.makeText(this, "跳转到EditNote页面", Toast.LENGTH_LONG).show();
        startActivity(intent);
    }

    public void loadAdapter() {
        notesCRUD = new NotesCRUD(this);
        notesCRUD.open();
        List<Note> notes = notesCRUD.getAllNote();
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