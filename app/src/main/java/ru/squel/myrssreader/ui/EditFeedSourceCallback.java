package ru.squel.myrssreader.ui;

import ru.squel.myrssreader.data.dataTypes.FeedSource;

/**
 * Created by sq on 06.08.2017.
 */
public interface EditFeedSourceCallback {

    void EditFeedSourceCallback(FeedSource oldValue, FeedSource newValue);
    void AddFeedSourceCallback(FeedSource newValue);
}
