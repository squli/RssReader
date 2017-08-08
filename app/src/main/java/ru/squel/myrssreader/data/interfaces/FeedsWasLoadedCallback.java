package ru.squel.myrssreader.data.interfaces;

import java.util.ArrayList;

import ru.squel.myrssreader.data.dataTypes.FeedSource;

/**
 * Created by sq on 05.08.2017.
 */
public interface FeedsWasLoadedCallback {

    void feedsResponceCallback(ArrayList<FeedSource> response);

}
