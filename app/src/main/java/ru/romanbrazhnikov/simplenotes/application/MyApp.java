package ru.romanbrazhnikov.simplenotes.application;

import android.app.Application;

import ru.romanbrazhnikov.simplenotes.dagger.AppComponent;
import ru.romanbrazhnikov.simplenotes.dagger.AppModule;

import ru.romanbrazhnikov.simplenotes.dagger.DaggerAppComponent;
import ru.romanbrazhnikov.simplenotes.dagger.ObjectBoxModule;

/**
 * Created by roman on 12.09.17.
 */

public class MyApp extends Application {
    private AppComponent mSimpleNotesComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mSimpleNotesComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .objectBoxModule(new ObjectBoxModule(this))
                .build();
    }

    public AppComponent getAppComponent(){
        return mSimpleNotesComponent;
    }
}
