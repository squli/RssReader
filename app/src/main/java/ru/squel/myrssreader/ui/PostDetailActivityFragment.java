package ru.squel.myrssreader.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.squel.myrssreader.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class PostDetailActivityFragment extends Fragment {

    String currentLink;

    public PostDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post_detail, container, false);
    }
}
