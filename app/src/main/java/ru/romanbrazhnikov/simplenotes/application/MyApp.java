package ru.romanbrazhnikov.simplenotes.application;

import android.app.Application;

import ru.romanbrazhnikov.simplenotes.dagger.AppModule;
import ru.romanbrazhnikov.simplenotes.dagger.DaggerSimpleNotesComponent;
import ru.romanbrazhnikov.simplenotes.dagger.ObjectBoxModule;
import ru.romanbrazhnikov.simplenotes.dagger.SimpleNotesComponent;

/**
 * Created by roman on 12.09.17.
 */

public class MyApp extends Application {
    private SimpleNotesComponent mSimpleNotesComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mSimpleNotesComponent = DaggerSimpleNotesComponent.builder()
                .appModule(new AppModule(this))
                .objectBoxModule(new ObjectBoxModule(this))
                .build();
    }

    public SimpleNotesComponent getSimpleNotesComponent(){
        return mSimpleNotesComponent;
    }
}
