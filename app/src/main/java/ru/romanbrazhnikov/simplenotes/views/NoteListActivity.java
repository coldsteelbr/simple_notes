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
import android.widget.Toast;

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

    private class NoteHolder extends RecyclerView.ViewHolder
                            implements View.OnClickListener
    {

        private TextView tvTitle;
        private TextView tvContent;

        public NoteHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            tvTitle = itemView.findViewById(R.id.tv_title);
            tvContent = itemView.findViewById(R.id.tv_content);
        }

        public void bindNote(SimpleNote note){
            tvTitle.setText(note.getTitle());
            tvContent.setText(note.getContent());
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(NoteListActivity.this, tvTitle.getText(), Toast.LENGTH_SHORT)
                    .show();
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


    }
}
