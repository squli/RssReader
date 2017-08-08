package ru.squel.myrssreader.data.remote;


import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import ru.squel.myrssreader.data.dataTypes.PostFromRss;
import ru.squel.myrssreader.data.interfaces.PostDataManager;
import ru.squel.myrssreader.data.interfaces.PostsWasLoadedCallback;
import ru.squel.myrssreader.utils.Injector;

/**
 * Created by sq on 05.08.2017.
 */
public class RemotePostLoader implements PostDataManager, LoaderManager.LoaderCallbacks< ArrayList<PostFromRss> >, Constants{

    private final static String LOG_TAG = RemotePostLoader.class.getSimpleName();

    /// хранилище id лоадеров
    public static HashMap<String, Integer> loadersId = new HashMap<>();

    /// лоадер-менеджер активити или фрагмента, который запрашивает данные
    private LoaderManager mLoaderManager;

    /// что-то, реализующее интерфейс коллбэка, который будет
    /// вызван после получения данных
    private PostsWasLoadedCallback dataWasLoadedCallback;

    /// что-то, реализующее интерфейс сохранения массива с постами в базу данных
    ///private PostLocalSaverCallback postLocalSaverCallback;

    public RemotePostLoader(LoaderManager loaderManager) {
        this.mLoaderManager = loaderManager;
    }

    /**
     * Получить список постов из сети
     * @param link
     */
    @Override
    public void getPostList(String link) {
        if (dataWasLoadedCallback != null) {
            Loader<String> loader;
            Bundle bundle = new Bundle();
            bundle.putString(Constants.LINK_NAME, link);

            /// получение id loder-а
            int id;
            if (loadersId.containsKey(link))
                id = loadersId.get(link);
            else {
                id = loadersId.size() + 1;
                loadersId.put(link, id);
            }

            loader = mLoaderManager.getLoader(id);

            if (loader != null && !loader.isReset()) {
                mLoaderManager.restartLoader(id, bundle, this);
            } else {
                mLoaderManager.initLoader(id, bundle, this);
            }

            loader = mLoaderManager.getLoader(id);
            loader.forceLoad();
        }
    }

    @Override
    public void setPostResponceCallback(PostsWasLoadedCallback callback) {
        dataWasLoadedCallback = callback;
    }


    //----------------- LOADER CALLBACKS -------------------------

    @Override
    public Loader<ArrayList<PostFromRss>> onCreateLoader(int id, Bundle args) {

        Loader<ArrayList<PostFromRss>> loader = new RemoteDataLoader(Injector.instance().getAppContext(), args);;
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<PostFromRss>> loader, ArrayList<PostFromRss> response) {

        Log.d(LOG_TAG, "onLoadFinished");

        // проверить, что данные похожи на правду и сохранить в базу
        if (response != null && response.size() != 0) {

        }

        if (dataWasLoadedCallback != null) {
            dataWasLoadedCallback.postsResponceCallback(response);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<PostFromRss>> loader) {

    }

    //----------------- //LOADER CALLBACKS -------------------------

}
