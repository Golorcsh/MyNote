package com.golor.mynote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class NotesCRUD {
    String TAG = "CRUD";
    private SQLiteOpenHelper dbHandler;
    private SQLiteDatabase db;

    public NotesCRUD(Context context) {
        // get database
        dbHandler = new NotesDB(context);
    }

    public void open() {
        // connect database with writable mode
        db = dbHandler.getWritableDatabase();
    }

    public void close() {
        // close connect
        dbHandler.close();
    }

    public void addNote(Note note) {
        // add note obj to database
        ContentValues cv = new ContentValues();
        cv.put(NotesDB.TITLE, note.getTitle());
        cv.put(NotesDB.CONTENT, note.getContent());
        cv.put(NotesDB.CREATE_TIME, note.getCreateTime());
        db.insert(NotesDB.TABLE_NAME, null, cv);
    }

    public List<Note> getAllNote() {
        List<Note> notes = new ArrayList<>();
        String sql = "select * from notes;";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Note note = new Note();
                note.setId(cursor.getLong(cursor.getColumnIndex(NotesDB.ID)));
                note.setTitle(cursor.getString(cursor.getColumnIndex(NotesDB.TITLE)));
                note.setContent(cursor.getString(cursor.getColumnIndex(NotesDB.CONTENT)));
                note.setCreateTime(cursor.getString(cursor.getColumnIndex(NotesDB.CREATE_TIME)));
                Log.d(TAG, note.info());
                notes.add(note);
            }
        } else {
            Log.d(TAG, "No record");
        }
        return notes;
    }

    // delete note by id
    public void deleteNoteByID(long id) {
        db.delete(NotesDB.TABLE_NAME, NotesDB.ID + "=" + id, null);
    }


}
