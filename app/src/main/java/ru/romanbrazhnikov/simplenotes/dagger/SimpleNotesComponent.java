package ru.romanbrazhnikov.simplenotes.dagger;

import javax.inject.Singleton;

import dagger.Component;
import ru.romanbrazhnikov.simplenotes.views.NoteEditorActivity;
import ru.romanbrazhnikov.simplenotes.views.NoteListFragment;

/**
 * Created by roman on 12.09.17.
 */

@Singleton
@Component(modules = {AppModule.class, ObjectBoxModule.class})
public interface SimpleNotesComponent {
    void inject(NoteEditorActivity activity);

    void inject(NoteListFragment fragment);
}
