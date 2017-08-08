package ru.squel.myrssreader.data.interfaces;

/**
 * Created by sq on 05.08.2017.
 */
public interface PostDataManager {

    void getPostList(String link);
    void setPostResponceCallback(PostsWasLoadedCallback callback);
}
