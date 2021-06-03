package com.golor.mynote;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;

public class NoteAdapter extends BaseAdapter {
    private Context context;
    private List<Note> notes;
    private ConstraintLayout layout;

    public NoteAdapter(Context context, List<Note> notes) {
        this.context = context;
        this.notes = notes;
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int position) {
        return notes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        layout = (ConstraintLayout) inflater.inflate(R.layout.note_item, null);
        // bind widget
        TextView tv_content = layout.findViewById(R.id.note_item_content);
        TextView tv_createTime = layout.findViewById(R.id.note_item_createTime);

        // get value from list

        String content = notes.get(position).getContent();
        content = content.length() >= 20 ? content.substring(0, 20) : content;
        String createTime = notes.get(position).getCreateTime();

        // set value to widget
        tv_content.setText(content);
        tv_createTime.setText(createTime);
        return layout;
    }
}
