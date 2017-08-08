package ru.squel.myrssreader.postlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import ru.squel.myrssreader.ui.PostDetailActivity;
import ru.squel.myrssreader.ui.PostFragment;
import ru.squel.myrssreader.data.dataTypes.PostFromRss;
import ru.squel.myrssreader.R;

/**
 * Created by Саша on 06.07.2017.
 */
public class PostsFragmentList extends ListFragment {

    private static final String LOG_TAG = "PostsFragmentList";
    RecyclerView listView;
    public PostFragmentListAdapter dataAdapter;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        // retain this fragment
        // для сохранения состояния фрагмента при пересоздании Активити,
        // в которой он отображается
        setRetainInstance(true);
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (dataAdapter != null) {
            listView = (RecyclerView)getActivity().findViewById(R.id.fragment_post_list);
            listView.setAdapter(dataAdapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_list, null);
        return view;
    }

    @Override
   public void onListItemClick(ListView l, View v, int position, long id) {
        String linkToPost = ((PostFromRss) getListAdapter().getItem(position)).getLink();
        PostFragment fragment = (PostFragment)getFragmentManager().findFragmentById(R.id.fragment_detail);
        if (fragment != null && fragment.isInLayout()) {
            fragment.displayLink(linkToPost);
            Log.d(LOG_TAG, "onListItemClick: fragment != null && fragment.isInLayout()");
        } else {
            Intent intent = new Intent(getActivity().getApplicationContext(), PostDetailActivity.class);
            intent.putExtra("selectedValue", linkToPost);
            Log.d(LOG_TAG, "onListItemClick: fragment == null && fragment.isInLayout() linkToPost = " + linkToPost);
            startActivity(intent);
        }
    }
}
