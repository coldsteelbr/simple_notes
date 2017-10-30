package ru.romanbrazhnikov.simplenotes.notelist.view;

import android.annotation.SuppressLint;
import android.view.View;

import java.util.List;

import javax.inject.Inject;

import io.objectbox.Box;
import io.objectbox.query.Query;
import ru.romanbrazhnikov.simplenotes.R;
import ru.romanbrazhnikov.simplenotes.base.view.BasicListSupportFragment;
import ru.romanbrazhnikov.simplenotes.base.view.viewholder.BaseViewHolder;
import ru.romanbrazhnikov.simplenotes.entities.SimpleNote;
import ru.romanbrazhnikov.simplenotes.notelist.view.viewholder.SimpleNoteViewHolder;

/**
 * Created by roman on 30.10.17.
 */

@SuppressLint("ValidFragment")
public class NoteListFragment extends BasicListSupportFragment {
    // CONSTANTS
    public static final String ARG_OBJECT = "object";

    // ENUMS
    public enum RANGES {
        DAY,
        WEEK,
        MONTH,
        YEAR,
        ALL_TIME
    }


    // FIELDS
    @Inject
    Box<SimpleNote> mSimpleNotesBox;

    private final int mPage;
    private final RANGES mRange;

    public static NoteListFragment getInstance(int page, RANGES range) {
        NoteListFragment fragment = new NoteListFragment(page, range);

        return fragment;
    }

    private NoteListFragment(int page, RANGES range) {
        mPage = page;
        mRange = range;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    protected void inject() {
        getAppComponent().inject(this);
    }

    @Override
    protected void updateUI() {
        // TODO: Use RANGE & PAGE here
        // getting items and refreshing list
        Query<SimpleNote> queryAll
                = mSimpleNotesBox.query()
                .build();

        List<SimpleNote> filteredRecords = queryAll.find();

        refreshList(filteredRecords);
    }

    @Override
    protected int getRecyclerViewID() {
        return R.id.rv_note_list;
    }

    @Override
    protected int getItemLayoutID() {
        return R.layout.item_simple_note;
    }

    @Override
    protected BaseViewHolder newViewHolder(View itemView) {
        return new SimpleNoteViewHolder(itemView, getActivity());
    }

    @Override
    protected int getScreenLayout() {
        return R.layout.fra_note_list;
    }

}
