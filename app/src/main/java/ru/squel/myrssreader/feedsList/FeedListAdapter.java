package ru.squel.myrssreader.feedsList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ru.squel.myrssreader.R;
import ru.squel.myrssreader.data.dataTypes.FeedSource;

/**
 * Created by sq on 02.07.2017.
 */
public class FeedListAdapter extends RecyclerView.Adapter<FeedListAdapter.ViewHolder> {

    private static final String LOG_TAG = "FL_ADAPT";

    // родительский контекст
    private Context ctx;

    // что-то, что умеет обновлять список постов
    private OnNavigationViewListener callback;

    // данные
    public ArrayList<FeedSource> feedSourcesList = new ArrayList<>();

    /**
     * КЛАСС ХОЛДЕРА ДЛЯ ОТОБРАЖЕНИЯ
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        public FeedSource mItem;
        public final TextView name;
        public final TextView link;

        public ViewHolder(final View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.item_of_feed_list_name);
            link = (TextView) itemView.findViewById(R.id.item_of_feed_list_link);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    final int position = getAdapterPosition();
                    Log.d(LOG_TAG, "Clicked on position " + position);
                    callback.updatePostsList(feedSourcesList.get(position).getLink());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    callback.registerContextmenu(v);
                    v.setOnCreateContextMenuListener(ViewHolder.this);
                    return false;
                }
            });
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            callback.createContexMenu(menu, v, menuInfo, feedSourcesList.get(getAdapterPosition()));
        }
    }

    public FeedListAdapter (Context context, OnNavigationViewListener callback)
    {
        ctx = context;
        this.callback = callback;
    }

    public void setFeedSourcesList(ArrayList<FeedSource> feedSourcesList) {
        this.feedSourcesList = feedSourcesList;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_of_feed_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FeedListAdapter.ViewHolder holder, int position) {

        holder.mItem = feedSourcesList.get(position);
        holder.name.setText(feedSourcesList.get(position).getName());
        holder.link.setText(feedSourcesList.get(position).getLink());
    }

    @Override
    public int getItemCount() {
        return feedSourcesList.size();
    }


}
