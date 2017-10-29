package ru.romanbrazhnikov.simplenotes.views;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import ru.romanbrazhnikov.simplenotes.R;
import ru.romanbrazhnikov.simplenotes.application.MyApp;
import ru.romanbrazhnikov.simplenotes.entities.SimpleNote;
import ru.romanbrazhnikov.simplenotes.entities.SimpleNote_;

/**
 * Created by roman on 13.09.17.
 */

public class NoteListActivity extends AppCompatActivity {
    @Inject
    BoxStore mBoxStore;
    Box<SimpleNote> mSimpleNotesBox;

    // WIDGETS
    RecyclerView rvNoteList;

    @BindView(R.id.b_new)
    FloatingActionButton bNew;

    // FIELDS
    NoteListAdapter mNoteListAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_note_list);
        ButterKnife.bind(this);

        rvNoteList = findViewById(R.id.rv_note_list);
        rvNoteList.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        ((MyApp) getApplication()).getSimpleNotesComponent().inject(this);
        mSimpleNotesBox = mBoxStore.boxFor(SimpleNote.class);

        // widgets
        bNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NoteEditorActivity.openActivity(NoteListActivity.this);
            }
        });

        //updateUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        // getting notes and setting adapter
        List<SimpleNote> notes = mSimpleNotesBox.query().orderDesc(SimpleNote_.date).build().find();
        if (mNoteListAdapter == null) {
            mNoteListAdapter = new NoteListAdapter(notes);
            rvNoteList.setAdapter(mNoteListAdapter);
        } else {
            mNoteListAdapter.updateData(notes);
            mNoteListAdapter.notifyDataSetChanged();
        }

    }

    private class NoteHolder extends RecyclerView.ViewHolder
            implements
            View.OnClickListener,
            View.OnLongClickListener {
        private long mId;
        private TextView tvTitle;
        private TextView tvContent;

        public NoteHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            tvTitle = itemView.findViewById(R.id.tv_title);
            tvContent = itemView.findViewById(R.id.tv_content);
        }

        public void bindNote(SimpleNote note) {
            mId = note.getId();
            tvTitle.setText(note.getTitle());
            tvContent.setText(note.getContent());
        }

        @Override
        public void onClick(View view) {
            // opening a given note in the editor
            NoteEditorActivity.openActivityWithNote(NoteListActivity.this, mId);
        }

        @Override
        public boolean onLongClick(View view) {
            // showing DeleteDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(NoteListActivity.this);
            builder.setMessage("Delete the note?")
                    .setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();

            // don't call "onClick"
            return true;
        }

        // Delete dialog listener
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        Toast.makeText(NoteListActivity.this, "Deleting.", Toast.LENGTH_SHORT).show();
                        // DELETING the note
                        mSimpleNotesBox.remove(mId);
                        // Refreshing UI
                        updateUI();
                        // Closing the dialog
                        dialog.dismiss();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        // No clicked. Just closing the dialog
                        dialog.dismiss();
                        break;
                }
            }
        };
    }

    private class NoteListAdapter extends RecyclerView.Adapter<NoteHolder> {
        private List<SimpleNote> mNoteList = new ArrayList<>();

        public NoteListAdapter(List<SimpleNote> noteList) {
            mNoteList.addAll(noteList);
        }

        @Override
        public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater li = LayoutInflater.from(NoteListActivity.this);
            View view = li.inflate(R.layout.item_simple_note, parent, false);
            return new NoteHolder(view);
        }

        @Override
        public void onBindViewHolder(NoteHolder holder, int position) {
            SimpleNote note = mNoteList.get(position);
            holder.bindNote(note);
        }

        @Override
        public int getItemCount() {
            return mNoteList.size();
        }


        public void updateData(List<SimpleNote> newList) {
            mNoteList.clear();
            mNoteList.addAll(newList);
            this.notifyDataSetChanged();
        }
    }
}
