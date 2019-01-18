package com.example.notepad.DB;

import org.litepal.crud.LitePalSupport;

public class DBservice extends LitePalSupport {
    private String textname;
    private long texttime;
    private String text;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public String getTextname() {
        return textname;
    }

    public void setTextname(String textname) {
        this.textname = textname;
    }

    public long getTexttime() {
        return texttime;
    }

    public void setTexttime(long texttime) {
        this.texttime = texttime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
