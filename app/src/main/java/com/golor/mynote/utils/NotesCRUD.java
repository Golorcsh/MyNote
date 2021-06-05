package com.golor.mynote.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.golor.mynote.model.Note;

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
        String sql = "select * from " + NotesDB.TABLE_NAME + ";";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Note note = new Note();
                note.setId(cursor.getLong(cursor.getColumnIndex(NotesDB.ID)));
                note.setTitle(cursor.getString(cursor.getColumnIndex(NotesDB.TITLE)));
                note.setContent(cursor.getString(cursor.getColumnIndex(NotesDB.CONTENT)));
                note.setCreateTime(cursor.getString(cursor.getColumnIndex(NotesDB.CREATE_TIME)));
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

    public void updateNote(Note note) {
        ContentValues cv = new ContentValues();
        // set the need change value
        cv.put(NotesDB.TITLE, note.getTitle());
        cv.put(NotesDB.CONTENT, note.getContent());
        db.update(NotesDB.TABLE_NAME, cv, NotesDB.ID + "=?", new String[]{String.valueOf(note.getId())});
    }

    public List<Note> getNotesByKeyword(String keyword) {
        List<Note> notes = new ArrayList<>();
        String sql = "select * from " + NotesDB.TABLE_NAME + " where " + NotesDB.TITLE + " like '%" + keyword + "%'";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Note note = new Note();
                note.setId(cursor.getLong(cursor.getColumnIndex(NotesDB.ID)));
                note.setTitle(cursor.getString(cursor.getColumnIndex(NotesDB.TITLE)));
                note.setContent(cursor.getString(cursor.getColumnIndex(NotesDB.CONTENT)));
                note.setCreateTime(cursor.getString(cursor.getColumnIndex(NotesDB.CREATE_TIME)));
                notes.add(note);
            }
        } else {
            Log.d(TAG, "No record");
        }
        return notes;
    }

}
