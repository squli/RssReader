package ru.squel.myrssreader.data.interfaces;

import java.util.ArrayList;

import ru.squel.myrssreader.data.dataTypes.PostFromRss;

/**
 * Created by sq on 05.08.2017.
 * Интерфейс коллбэка, который будет вызван после окончания загрузки данных
 */
public interface PostsWasLoadedCallback {

    void postsResponceCallback(ArrayList<PostFromRss> response);

}
