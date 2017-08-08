package ru.squel.myrssreader.data.dataTypes;

/**
 * Created by Саша on 25.06.2017.
 * Элемент RSS-источника данных для хранения в таблице базы
 */
public class FeedSource {
        private int id = 0;
        private String name;
        private String link;

        public FeedSource(int id, String n, String l) {
            this.id = id;
            name = n;
            link = l;
        }

        public FeedSource(String n, String l) {
            name = n;
            link = l;
        }

        public FeedSource(FeedSource sf) {
            id = sf.id;
            name = sf.name;
            link = sf.link;
        }

        public int getId(){return id;}
        public String getName(){return name;}
        public String getLink(){return link;}
}
