package ru.romanbrazhnikov.simplenotes.dagger;

import android.app.Application;

import dagger.Module;
import dagger.Provides;
import io.objectbox.BoxStore;
import ru.romanbrazhnikov.simplenotes.entities.MyObjectBox;

/**
 * Created by roman on 12.09.17.
 */
@Module
public class ObjectBoxModule {

    @Provides
    BoxStore providesBoxStore(Application myApp){
        return MyObjectBox.builder().androidContext(myApp).build();
    }
}
