package ru.squel.myrssreader.postlist;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ru.squel.myrssreader.R;
import ru.squel.myrssreader.data.dataTypes.PostFromRss;

/**
 * Created by sq on 07.07.2017.
 */
public class PostFragmentListAdapter extends RecyclerView.Adapter<PostFragmentListAdapter.ViewHolder> {

    private static final String LOG_TAG = "PostFragListAdapter";

    ///local storage of posts to display in adapter
    private ArrayList<PostFromRss> listOfPosts = new ArrayList<PostFromRss>();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitle;
        public TextView mDescr;

        public ViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.item_of_post_list_title);
            mDescr = (TextView) itemView.findViewById(R.id.item_of_post_list_description);
        }
    }

    public PostFragmentListAdapter() {
    }

    public void setPostSourcesList(ArrayList<PostFromRss> list) {
        listOfPosts = list;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PostFragmentListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_of_post_list, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTitle.setText(listOfPosts.get(position).getTitle());
        holder.mDescr.setText(Html.fromHtml(listOfPosts.get(position).getShortDescription()));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return listOfPosts.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public PostFromRss getItem(int position){
        return listOfPosts.get(position);
    }

}
