package ru.romanbrazhnikov.simplenotes.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import javax.inject.Inject;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import ru.romanbrazhnikov.simplenotes.R;
import ru.romanbrazhnikov.simplenotes.application.MyApp;
import ru.romanbrazhnikov.simplenotes.entities.SimpleNote;

public class NoteEditorActivity extends AppCompatActivity {

    public static final String EXTRA_NOTE_ID = "EXTRA_NOTE_ID";

    // fields
    SimpleNote mSimpleNote = null;

    @Inject
    BoxStore mBoxStore;
    Box<SimpleNote> mSimpleNotesBox;

    // widgets
    Button bSave;
    EditText etTitle;
    EditText etContent;

    public static void openActivity(Context context) {
        openActivityWithNote(context, null);
    }

    public static void openActivityWithNote(Context context, Long noteId) {
        Intent intent = new Intent(context, NoteEditorActivity.class);
        if (noteId != null) {
            intent.putExtra(EXTRA_NOTE_ID, noteId);
        }
        context.startActivity(intent);
    }

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

        // SimpleNote Init
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_NOTE_ID)) {
            mSimpleNote = mSimpleNotesBox.get(intent.getLongExtra(EXTRA_NOTE_ID, 0));
        }


        // populating the fields
        if (mSimpleNote != null) {
            etTitle.setText(mSimpleNote.getTitle());
            etContent.setText(mSimpleNote.getContent());
        }

        // setting event listeners
        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Saving a note if it was not empty
                if (mSimpleNote != null) {
                    mSimpleNote.setTitle(etTitle.getText().toString());
                    mSimpleNote.setContent(etContent.getText().toString());
                } else {
                    // creating a new note
                    mSimpleNote = new SimpleNote(
                            etTitle.getText().toString(),
                            etContent.getText().toString());
                }
                // create or update
                mSimpleNotesBox.put(mSimpleNote);
            }
        });
    }

}
