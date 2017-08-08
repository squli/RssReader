package ru.squel.myrssreader.presenter;

import android.app.LoaderManager;
import android.content.Context;

import java.util.ArrayList;

import ru.squel.myrssreader.data.DataRepository;
import ru.squel.myrssreader.data.dataTypes.FeedSource;
import ru.squel.myrssreader.data.dataTypes.PostFromRss;
import ru.squel.myrssreader.data.interfaces.FeedsWasLoadedCallback;
import ru.squel.myrssreader.data.interfaces.PostsWasLoadedCallback;
import ru.squel.myrssreader.ui.EditFeedSourceCallback;

/**
 * Created by Саша on 04.08.2017.
 */
public class MainActivityPresenter implements FeedsWasLoadedCallback, EditFeedSourceCallback, PostsWasLoadedCallback {

    private static final String LOG_TAG = MainActivityPresenter.class.getSimpleName();

    private ViewPresenterContract.View viewOfPresenter;

    private Context context;
    private LoaderManager loaderManager;
    private DataRepository dataRepository;


    public MainActivityPresenter(ViewPresenterContract.View viewOfPresenter, Context context, LoaderManager loaderManager) {
        this.viewOfPresenter = viewOfPresenter;
        this.context = context;
        this.loaderManager = loaderManager;
        dataRepository = DataRepository.getInstance(context, loaderManager);
    }

    /**
     * Обработка нажатий в левом меню для отображения
     * Запрос списка лент
     */
    public void getFeedSource() {

        dataRepository.setFeedResponceCallback(this);
        dataRepository.getFeedsList();
    }

    /**
     * Коллбэк получения списка лент
     * @param response
     */
    @Override
    public void feedsResponceCallback(ArrayList<FeedSource> response) {
        viewOfPresenter.updateFeedAdapter(response);
    }

    /**
     * Удаление ленты из списка
     * @param deleteMe
     */
    public void feedSourceDelete(FeedSource deleteMe) {

        dataRepository.removeFeedData(deleteMe);
    }

    /**
     * Коллбэк для редактирования ленты в списке
     * @param newValue
     * @param oldValue
     */
    @Override
    public void EditFeedSourceCallback(FeedSource oldValue, FeedSource newValue) {

        dataRepository.removeFeedData(oldValue);
        dataRepository.addFeedsData(newValue);
        getFeedSource();
    }

    @Override
    public void AddFeedSourceCallback(FeedSource newValue) {

        dataRepository.addFeedsData(newValue);
        getFeedSource();
    }

    /**
     * Обновить список постов
     * @param link
     */
    public void updatePostList(String link) {
        dataRepository.setPostResponceCallback(this);
        dataRepository.getPostList(link);
    }

    /**
     * Коллбэк получения списка постов по ссылке из ленты
     * @param response
     */
    @Override
    public void postsResponceCallback(ArrayList<PostFromRss> response) {

        viewOfPresenter.updatePostAdapter(response);
    }
}
