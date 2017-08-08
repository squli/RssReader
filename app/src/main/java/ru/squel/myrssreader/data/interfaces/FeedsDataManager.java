package ru.squel.myrssreader.data.interfaces;

import ru.squel.myrssreader.data.dataTypes.FeedSource;

/**
 * Created by sq on 05.08.2017.
 * интерфейс для доступа к данным о списке feed лент,
 * они хранятся в базе
 */
public interface FeedsDataManager {

    void getFeedsList();
    void addFeedsData(FeedSource newFeedSource);
    void removeFeedData(FeedSource newFeedSource);
    void setFeedResponceCallback(FeedsWasLoadedCallback callback);

}
