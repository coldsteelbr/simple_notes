package ru.romanbrazhnikov.simplenotes.base.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import ru.romanbrazhnikov.simplenotes.application.MyApp;
import ru.romanbrazhnikov.simplenotes.dagger.AppComponent;

/**
 * Created by roman on 30.10.17.
 */

public abstract class BasicSupportFragment extends Fragment {
    private AppComponent mAppComponent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAppComponent = ((MyApp) getActivity().getApplication()).getAppComponent();
        inject();
    }

    protected AppComponent getAppComponent() {
        return mAppComponent;
    }

    protected abstract void inject();

}
