package ru.squel.myrssreader.data;

import android.app.LoaderManager;
import android.content.Context;

import ru.squel.myrssreader.data.dataTypes.FeedSource;
import ru.squel.myrssreader.data.interfaces.FeedsDataManager;
import ru.squel.myrssreader.data.interfaces.FeedsWasLoadedCallback;
import ru.squel.myrssreader.data.interfaces.PostDataManager;
import ru.squel.myrssreader.data.interfaces.PostsWasLoadedCallback;
import ru.squel.myrssreader.data.local.DataBaseHelper;
import ru.squel.myrssreader.data.local.LocalFeedListLoader;
import ru.squel.myrssreader.data.remote.RemotePostLoader;

/**
 * Created by sq on 05.08.2017.
 * Класс для управления доступом к данным
 */
public class DataRepository implements FeedsDataManager, PostDataManager {

    private Context context;

    // для синглтона
    private static volatile DataRepository INSTANCE;
    private static final Object lock = new Object();

    // для получения постов из сети
    private RemotePostLoader remotePostLoader;

    // TODO сделать хранение и получение постов из базы
    // для получения постов из базы
    //private localPostLoader localPostLoader;

    // для получения списка RSS-лент, из базы
    private LocalFeedListLoader localFeedListLoader;

    // Колбэки для получения данных
    PostsWasLoadedCallback postsWasLoadedCallback;
    FeedsWasLoadedCallback feedsWasLoadedCallback;


    //------------------------FeedsDataManager----------------------------------
    @Override
    public void getFeedsList() {
        if (feedsWasLoadedCallback != null) {
            localFeedListLoader.setFeedResponceCallback(feedsWasLoadedCallback);
            localFeedListLoader.getFeedsList();
        }
    }

    @Override
    public void addFeedsData(FeedSource newFeedSource) {
        localFeedListLoader.addFeedsData(newFeedSource);
    }

    @Override
    public void removeFeedData(FeedSource deleteMeFeedSource) {
        localFeedListLoader.removeFeedData(deleteMeFeedSource);
    }
    //------------------------//FeedsDataManager----------------------------------

    /**
     * Многопоточный синглтон
     * @param context
     * @param loaderManager
     * @return
     */
    public static DataRepository getInstance(Context context, LoaderManager loaderManager) {
        DataRepository localINSTANCE = INSTANCE;
        if (localINSTANCE == null) {
            synchronized (lock) {               // While we were waiting for the lock, another
                localINSTANCE = INSTANCE;       // thread may have instantiated the object.
                if (localINSTANCE == null) {
                    localINSTANCE = new DataRepository(context, loaderManager);
                    INSTANCE = localINSTANCE;
                }
            }
        }
        return localINSTANCE;
    }

    /**
     * Локальный конструктор
     * @param context
     * @param loaderManager
     */
    private DataRepository(Context context, LoaderManager loaderManager) {
        this.context = context;
        this.remotePostLoader = new RemotePostLoader(loaderManager);
        localFeedListLoader = new LocalFeedListLoader(DataBaseHelper.getInstance(this.context));
    }

    @Override
    public void getPostList(String link) {
        //TODO когда посты можно будте хранить в базе, добавить логику выбора
        if (postsWasLoadedCallback != null) {
            // установил коллбэк загрузки данных
            remotePostLoader.setPostResponceCallback(postsWasLoadedCallback);
            // запросил данные
            remotePostLoader.getPostList(link);
        }
    }

    /**
     * Установка коллбэка для загрузки постов
     * @param callback
     */
    @Override
    public void setPostResponceCallback(PostsWasLoadedCallback callback) {
        postsWasLoadedCallback = callback;
    }

    @Override
    public void setFeedResponceCallback(FeedsWasLoadedCallback callback) {
        feedsWasLoadedCallback = callback;
    }
}
