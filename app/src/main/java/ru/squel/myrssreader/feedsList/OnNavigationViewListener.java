package ru.squel.myrssreader.feedsList;

import android.view.ContextMenu;
import android.view.View;

import ru.squel.myrssreader.data.dataTypes.FeedSource;

/**
 * Created by sq on 05.08.2017.
 * Интерфейс обновления списка постов по известной ссылке на rss-ленту
 * Используется в адаптере FeedList при нажатии на соответствующий пункт списка лент
 */
public interface OnNavigationViewListener {

    /// обновление списка постов по клику на название ленты
    void updatePostsList(String link);
    /// регистрация контекстного меню для вызова диалога управления по названию ленты
    void registerContextmenu(View v);
    /// создает контектсное меню для долгих нажатий на пунктах списка NawView
    void createContexMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo, FeedSource feedSource);
}
