package ru.romanbrazhnikov.simplenotes.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.romanbrazhnikov.simplenotes.R;

/**
 * Created by roman on 13.09.17.
 */

public class NoteListActivity extends AppCompatActivity {

    // WIDGETS
    RecyclerView rvNoteList;

    @BindView(R.id.b_new)
    FloatingActionButton bNew;

    @BindView(R.id.ll_filter_sorting)
    LinearLayout ll_filter;


    // VIEW PAGER
    // When requested, this adapter returns a NoteListFragment,
    // representing an object in the collection.
    NoteListPagerAdapter mNoteListPagerAdapter;
    @BindView(R.id.pager)
    ViewPager mViewPager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_note_list);

        ButterKnife.bind(this);

        Toolbar myToolbar = findViewById(R.id.tb_list);
        setSupportActionBar(myToolbar);

        /*
        rvNoteList = findViewById(R.id.rv_note_list);
        rvNoteList.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        */


        initWidgets();

        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        mNoteListPagerAdapter =
                new NoteListPagerAdapter(
                        getSupportFragmentManager());
        mViewPager.setAdapter(mNoteListPagerAdapter);
    }

    private void initWidgets() {
        bNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NoteEditorActivity.openActivity(NoteListActivity.this);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.listmenu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
                ll_filter.setVisibility(View.VISIBLE);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
