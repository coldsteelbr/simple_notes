package ru.romanbrazhnikov.simplenotes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import javax.inject.Inject;

import io.objectbox.BoxStore;
import ru.romanbrazhnikov.simplenotes.application.MyApp;
import ru.romanbrazhnikov.simplenotes.dagger.SimpleNotesComponent;

public class MainActivity extends AppCompatActivity {

    @Inject
    BoxStore mBoxStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((MyApp)getApplication()).getSimpleNotesComponent().inject(this);
    }

}
