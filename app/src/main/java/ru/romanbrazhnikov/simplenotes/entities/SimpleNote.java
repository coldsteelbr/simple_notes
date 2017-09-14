package ru.romanbrazhnikov.simplenotes.entities;

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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
