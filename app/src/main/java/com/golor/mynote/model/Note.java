package com.golor.mynote.model;

public class Note {
    // note obj
    private long id;
    private String title;
    private String content;
    private String createTime;

    public Note() {
    }

    public Note(String title, String content, String createTime) {
        this.title = title;
        this.content = content;
        this.createTime = createTime;
    }

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String info() {
        // return the note info

        String info = "";
        if (!this.title.isEmpty())
            info += this.title + "\n";
        if (!this.content.isEmpty())
            info += this.content + "\n";
        info += createTime + "\n";
        return info;
    }
}
