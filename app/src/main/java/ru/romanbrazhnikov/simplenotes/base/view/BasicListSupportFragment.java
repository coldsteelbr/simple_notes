package ru.romanbrazhnikov.simplenotes.base.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.romanbrazhnikov.simplenotes.R;
import ru.romanbrazhnikov.simplenotes.base.view.viewholder.BaseViewHolder;

/**
 * Created by roman on 30.10.17.
 */

public abstract class BasicListSupportFragment<T, V extends BaseViewHolder> extends BasicSupportFragment {
    // WIDGETS
    @BindView(R.id.rv_note_list)
    RecyclerView rvItemList;


    private int mRecyclerViewID;
    private int mItemLayout;

    private BaseAdapter mBaseAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mRecyclerViewID = getRecyclerViewID();
        mItemLayout = getItemLayoutID();

        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                getScreenLayout(), container, false);

        ButterKnife.bind(this, rootView);

        rvItemList.setLayoutManager(
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        return rootView;
    }

    protected abstract void updateUI();

    protected BaseAdapter getAdapter() {
        return mBaseAdapter;
    }


    protected void resetAdapter(List<T> items) {
        mBaseAdapter = new BaseAdapter(items);
        rvItemList.setAdapter(mBaseAdapter);
    }

    protected void updateAdapter(List<T> items) {
        mBaseAdapter.updateData(items);
        mBaseAdapter.notifyDataSetChanged();
    }

    protected void refreshList(List<T> items) {
        if (mBaseAdapter == null) {
            resetAdapter(items);
        } else {
            updateAdapter(items);
        }
    }


    /**
     * Returns recycler view id: R.id.rv_BlaBla
     */
    protected abstract int getRecyclerViewID();

    /**
     * Returns item layout id: R.layout.item_bla_bla
     */
    protected abstract int getItemLayoutID();


    /**
     * Returns another view holder
     */
    protected abstract V newViewHolder(View itemView);

    /**
     * Returns layout id for the screen
     */
    protected abstract int getScreenLayout();


    class BaseAdapter extends RecyclerView.Adapter<V> {

        private List<T> mItemList = new ArrayList<>();

        public BaseAdapter(List<T> itemList) {
            updateData(itemList);
        }

        @Override
        public V onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater li = getLayoutInflater();
            View view = li.inflate(mItemLayout, parent, false);
            return newViewHolder(view);
        }

        @Override
        public void onBindViewHolder(V holder, int position) {
            T set = mItemList.get(position);
            holder.bindItem(set);
        }

        @Override
        public int getItemCount() {
            return mItemList.size();
        }

        public void updateData(List<T> newList) {
            mItemList.clear();
            mItemList.addAll(newList);
            this.notifyDataSetChanged();
        }
    }
}
