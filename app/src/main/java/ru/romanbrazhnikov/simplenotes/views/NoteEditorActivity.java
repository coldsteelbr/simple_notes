package ru.romanbrazhnikov.simplenotes.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import javax.inject.Inject;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import ru.romanbrazhnikov.simplenotes.R;
import ru.romanbrazhnikov.simplenotes.application.MyApp;
import ru.romanbrazhnikov.simplenotes.entities.SimpleNote;

public class NoteEditorActivity extends AppCompatActivity {

    @Inject
    BoxStore mBoxStore;
    Box<SimpleNote> mSimpleNotesBox;

    // widgets
    Button bSave;
    EditText etTitle;
    EditText etContent;

    private void setWidgets() {
        // TODO: ButterKnife or DataBinding
        bSave = findViewById(R.id.b_save);
        etTitle = findViewById(R.id.et_title);
        etContent = findViewById(R.id.et_content);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_note_editor);

        // Widgets
        setWidgets();

        // Injection and ObjectBox
        ((MyApp) getApplication()).getSimpleNotesComponent().inject(this);
        mSimpleNotesBox = mBoxStore.boxFor(SimpleNote.class);


        // populating the fields
        SimpleNote note = null;
        if (mSimpleNotesBox.count() == 1) {
            // get it
            List<SimpleNote> noteList = mSimpleNotesBox.getAll();
            note = noteList.get(0);
        }
        if (note != null) {
            etTitle.setText(note.getTitle());
            etContent.setText(note.getContent());
        }

        // setting event listeners
        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SimpleNote note;

                if (mSimpleNotesBox.count() == 1) {
                    // get it
                    List<SimpleNote> noteList = mSimpleNotesBox.getAll();
                    note = noteList.get(0);
                    note.setTitle(etTitle.getText().toString());
                    note.setContent(etContent.getText().toString());
                } else {
                    // create new
                    note = new SimpleNote(
                            etTitle.getText().toString(),
                            etContent.getText().toString());
                }
                // create or update
                mSimpleNotesBox.put(note);
            }
        });
    }

}
