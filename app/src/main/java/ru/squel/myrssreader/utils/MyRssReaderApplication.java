package ru.squel.myrssreader.utils;

import android.app.Application;

/**
 * Created by sq on 06.08.2017.
 */
public class MyRssReaderApplication  extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Injector.instance().init(this);
    }
}
