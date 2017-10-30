package ru.romanbrazhnikov.simplenotes.notelist.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.romanbrazhnikov.simplenotes.R;
import ru.romanbrazhnikov.simplenotes.noteeditor.view.NoteEditorActivity;

/**
 * Created by roman on 13.09.17.
 */

public class NoteListActivity extends AppCompatActivity {

    // WIDGETS
    @BindView(R.id.b_new)
    FloatingActionButton bNew;

    @BindView(R.id.ll_filter_sorting)
    LinearLayout ll_filter;

    @BindView(R.id.pager)
    ViewPager mViewPager;

    @BindView(R.id.tb_list)
    Toolbar mToolbar;

    // FIELDS
    NoteListPagerAdapter mNoteListPagerAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_note_list);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        // init view pager adapter
        mNoteListPagerAdapter =
                new NoteListPagerAdapter(
                        getSupportFragmentManager());

        initWidgets();
    }

    private void initWidgets() {
        bNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NoteEditorActivity.openActivity(NoteListActivity.this);
            }
        });

        mViewPager.setAdapter(mNoteListPagerAdapter);
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
