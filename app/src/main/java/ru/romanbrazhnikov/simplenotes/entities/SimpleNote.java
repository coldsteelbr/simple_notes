package ru.romanbrazhnikov.simplenotes.entities;

import java.util.Date;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Created by roman on 12.09.17.
 */

@Entity
public class SimpleNote {
    @Id
    long id;

    String title;
    String content;
    Date date;

    public SimpleNote() {
    }

    public SimpleNote(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
