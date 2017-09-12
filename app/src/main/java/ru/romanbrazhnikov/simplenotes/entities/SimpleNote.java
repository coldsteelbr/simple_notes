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
}
