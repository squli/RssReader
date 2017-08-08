package ru.squel.myrssreader.data.local;

import ru.squel.myrssreader.data.dataTypes.FeedSource;
import ru.squel.myrssreader.data.interfaces.FeedsDataManager;
import ru.squel.myrssreader.data.interfaces.FeedsWasLoadedCallback;

/**
 * Created by sq on 05.08.2017.
 */
public class LocalFeedListLoader implements FeedsDataManager {

    // для работы с базой
    private DataBaseHelper dataBaseHelper;

    private FeedsWasLoadedCallback callback;

    public LocalFeedListLoader(DataBaseHelper dataBaseHelper){
        this.dataBaseHelper = dataBaseHelper;
    }

    @Override
    public void getFeedsList() {
        callback.feedsResponceCallback(dataBaseHelper.getAllStoredFeeds());
    }

    @Override
    public void addFeedsData(FeedSource newFeedSource) {
        dataBaseHelper.addNewFeed(newFeedSource);
    }

    @Override
    public void removeFeedData(FeedSource deleteMeFeedSource) {
        dataBaseHelper.deleteStoredFeed(deleteMeFeedSource.getName());
    }

    @Override
    public void setFeedResponceCallback(FeedsWasLoadedCallback callback) {
        this.callback = callback;
    }
}
