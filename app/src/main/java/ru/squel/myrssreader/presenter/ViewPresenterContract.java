package ru.squel.myrssreader.presenter;

import java.util.ArrayList;

import ru.squel.myrssreader.data.dataTypes.FeedSource;
import ru.squel.myrssreader.data.dataTypes.PostFromRss;

/**
 * Created by Саша on 06.08.2017.
 */
public interface ViewPresenterContract {

    interface View {
        void updateFeedAdapter(ArrayList<FeedSource> response);
        void updatePostAdapter(ArrayList<PostFromRss> response);
    }
}
