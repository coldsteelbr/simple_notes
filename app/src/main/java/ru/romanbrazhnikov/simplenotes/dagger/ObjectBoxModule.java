package ru.romanbrazhnikov.simplenotes.dagger;

import android.app.Application;

import dagger.Module;
import dagger.Provides;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import ru.romanbrazhnikov.simplenotes.entities.MyObjectBox;
import ru.romanbrazhnikov.simplenotes.entities.SimpleNote;

/**
 * Created by roman on 12.09.17.
 */
@Module
public class ObjectBoxModule {
    private BoxStore mBoxStore;

    public ObjectBoxModule(Application myApp) {
        mBoxStore = MyObjectBox.builder().androidContext(myApp).build();
    }

    @Provides
    BoxStore providesBoxStore() {
        return mBoxStore;
    }

    @Provides
    Box<SimpleNote> provideBoxForSimpleNote() {
        return mBoxStore.boxFor(SimpleNote.class);
    }
}
