package ru.romanbrazhnikov.simplenotes.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import ru.romanbrazhnikov.simplenotes.R;
import ru.romanbrazhnikov.simplenotes.application.MyApp;
import ru.romanbrazhnikov.simplenotes.entities.SimpleNote;

/**
 * Created by roman on 13.09.17.
 */

public class NoteListActivity extends AppCompatActivity
{
    @Inject
    BoxStore mBoxStore;
    Box<SimpleNote> mSimpleNotesBox;

    // Widgets
    RecyclerView rvNoteList;

    // Fields
    NoteListAdapter mNoteListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_note_list);


        rvNoteList = findViewById(R.id.rv_note_list);
        rvNoteList.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

        ((MyApp) getApplication()).getSimpleNotesComponent().inject(this);
        mSimpleNotesBox = mBoxStore.boxFor(SimpleNote.class);
        updateUI();
    }



    private void updateUI() {
        // getting notes and setting adapter
        List<SimpleNote> notes = mSimpleNotesBox.getAll();
        mNoteListAdapter = new NoteListAdapter(notes);
        rvNoteList.setAdapter(mNoteListAdapter);
    }

    private class NoteHolder extends RecyclerView.ViewHolder{

        public TextView mTextView;

        public NoteHolder(View itemView) {
            super(itemView);

            mTextView = (TextView)itemView;
        }
    }

    private class NoteListAdapter extends RecyclerView.Adapter<NoteHolder>
    {
        private List<SimpleNote> mNoteList;

        public NoteListAdapter(List<SimpleNote> noteList) {
            mNoteList = noteList;
        }

        @Override
        public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater li = LayoutInflater.from(NoteListActivity.this);
            View view = li.inflate(android.R.layout.simple_list_item_1, parent, false);
            return new NoteHolder(view);
        }

        @Override
        public void onBindViewHolder(NoteHolder holder, int position) {
            SimpleNote note = mNoteList.get(position);
            holder.mTextView.setText(note.getTitle());
        }

        @Override
        public int getItemCount() {
            return mNoteList.size();
        }
    }
}
