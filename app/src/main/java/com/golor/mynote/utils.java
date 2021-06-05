package com.golor.mynote;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.CopyOnWriteArrayList;

public class utils {

    public static void save(Context context, Note note) {
        NotesCRUD notesCRUD = new NotesCRUD(context);
        if (!note.getContent().isEmpty()) {
            notesCRUD.open();
            notesCRUD.addNote(note);
            // Toast remind
            Toast.makeText(context, "Record to save", Toast.LENGTH_SHORT).show();
            notesCRUD.close();
        } else {
            Toast.makeText(context, "Noting to save", Toast.LENGTH_SHORT).show();
        }
    }


    public static void re_save(Context context, Note note) {
        NotesCRUD notesCRUD = new NotesCRUD(context);
        notesCRUD.open();
        notesCRUD.updateNote(note);
        // Toast remind
        Toast.makeText(context, "Changed to save", Toast.LENGTH_SHORT).show();
        notesCRUD.close();
    }


    public static void share(Context context, Note note) {
        NotesCRUD notesCRUD = new NotesCRUD(context);
        if (!note.getContent().isEmpty()) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            // set send content
            sendIntent.putExtra(Intent.EXTRA_TEXT, note.info());
            // set send type
            sendIntent.setType("text/plain");
            // star share
            context.startActivity(Intent.createChooser(sendIntent, "Share to"));
        } else {
            Toast.makeText(context, "Noting to share", Toast.LENGTH_SHORT).show();
        }
    }

    public static String dateStr() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd- HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        Date date = new Date();
        return simpleDateFormat.format(date);
    }

}
