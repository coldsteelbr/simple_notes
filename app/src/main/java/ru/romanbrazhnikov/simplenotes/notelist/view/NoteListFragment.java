package ru.romanbrazhnikov.simplenotes.notelist.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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

import io.objectbox.Box;
import io.objectbox.BoxStore;
import ru.romanbrazhnikov.simplenotes.R;
import ru.romanbrazhnikov.simplenotes.application.MyApp;
import ru.romanbrazhnikov.simplenotes.entities.SimpleNote;
import ru.romanbrazhnikov.simplenotes.entities.SimpleNote_;
import ru.romanbrazhnikov.simplenotes.noteeditor.view.NoteEditorActivity;

/**
 * Created by roman on 30.10.17.
 */

// Instances of this class are fragments representing a single
// object in our collection.
public class NoteListFragment extends Fragment {
    // CONSTANTS
    public static final String ARG_OBJECT = "object";

    // WIDGETS
    RecyclerView rvNoteList;

    // FIELDS
    NoteListAdapter mNoteListAdapter;
    @Inject
    BoxStore mBoxStore;
    Box<SimpleNote> mSimpleNotesBox;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        ((MyApp) getActivity().getApplication()).getSimpleNotesComponent().inject(this);
        mSimpleNotesBox = mBoxStore.boxFor(SimpleNote.class);

        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.fra_note_list, container, false);
        Bundle args = getArguments();
        ((TextView) rootView.findViewById(R.id.tv_page_title)).setText(
                Integer.toString(args.getInt(ARG_OBJECT)));

        rvNoteList = rootView.findViewById(R.id.rv_note_list);
        rvNoteList.setLayoutManager(
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        return rootView;
    }

    @Override
    public void onResume() {
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

        // TODO: ButterKnife
        private TextView tvTitle;
        private TextView tvContent;
        private TextView tvDate;

        public NoteHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            tvTitle = itemView.findViewById(R.id.tv_title);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvDate = itemView.findViewById(R.id.tv_date);
        }

        public void bindNote(SimpleNote note) {
            mId = note.getId();
            tvTitle.setText(note.getTitle());
            tvContent.setText(note.getContent());
            tvDate.setText(note.getFormattedDate());
        }

        @Override
        public void onClick(View view) {
            // opening a given note in the editor
            NoteEditorActivity.openActivityWithNote(getContext(), mId);
        }

        @Override
        public boolean onLongClick(View view) {
            // showing DeleteDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
                        Toast.makeText(getContext(), "Deleting.", Toast.LENGTH_SHORT).show();
                        // DELETING the note
                        mSimpleNotesBox.remove(mId);
                        // Refreshing UI
                        updateUI();
                        // Closing the dialog
                        dialog.dismiss();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        // Not clicked. Just closing the dialog
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
            LayoutInflater li = LayoutInflater.from(getContext());
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
