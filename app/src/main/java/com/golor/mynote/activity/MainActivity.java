package com.golor.mynote.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.golor.mynote.view.NoteAdapter;
import com.golor.mynote.R;
import com.golor.mynote.model.Note;
import com.golor.mynote.utils.NotesCRUD;
import com.golor.mynote.utils.NotesDB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Context context;
    private NotesCRUD notesCRUD;
    private NoteAdapter adapter;
    private FloatingActionButton addBtn;
    private ListView lv;
    private Toolbar toolbar;
    private Intent intent;
    private List<Note> notes;
    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        initView();
    }

    public void initView() {
        // load database
        notesCRUD = new NotesCRUD(context);
        //bind widget
        lv = findViewById(R.id.listView);
        addBtn = findViewById(R.id.fb_addButton);
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle("Note");
        toolbar.setSubtitle("write something");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // set listener
        addBtn.setOnClickListener(this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, SelectItemActivity.class);
                intent.putExtra(NotesDB.ID, notes.get(position).getId());
                intent.putExtra(NotesDB.TITLE, notes.get(position).getTitle());
                intent.putExtra(NotesDB.CONTENT, notes.get(position).getContent());
                intent.putExtra(NotesDB.CREATE_TIME, notes.get(position).getCreateTime());
                startActivity(intent);
            }
        });
    }

    // toolbar overflow item
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_item, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.toolbar_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // if note is searched , and reload adapter
                notesCRUD.open();
                List<Note> newNotes = notesCRUD.getNotesByKeyword(query);
                notesCRUD.close();
                Toast.makeText(context, newNotes.size() + " related record be found", Toast.LENGTH_SHORT).show();
                if (!newNotes.isEmpty())
                    notes = newNotes;
                adapter.setNotes(notes);
                adapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    // toolbar item listener
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        intent = new Intent(context, EditNoteActivity.class);
        intent.putExtra("Flag", '1');
        startActivity(intent);
    }

    public void loadAdapter() {
        notesCRUD.open();
        notes = notesCRUD.getAllNote();
        notesCRUD.close();
        adapter = new NoteAdapter(context, notes);
        lv.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAdapter();
    }
}