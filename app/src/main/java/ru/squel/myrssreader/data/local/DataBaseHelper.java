package ru.squel.myrssreader.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import ru.squel.myrssreader.data.dataTypes.FeedSource;

/**
 * Created by sq on 19.06.2017.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String LOG_TAG = "DB";

    /**
     * for using as singletone
     * volatile - happens before
     */
    private static volatile DataBaseHelper mInstance = null;

    /**
     * DB parameters
     */
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "rssReaderDB";
    private static final String TABLE_RSS_FEEDS = "rssFeeds";
    private static final String KEY_ID = "id";
    private static final String KEY_DISPLAY_NAME = "name";
    private static final String KEY_LINK_TO_FEED = "link";
    private static final String LAST_UPDATE = "lastupdate";

    private DataBaseHelper (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static String getDBName() {
        return DATABASE_NAME;
    }

    /**
     * Get access to db-object as singletone
     * @return
     */
    public static DataBaseHelper getInstance(Context context) {
        if (mInstance == null) {
            // если указатель нулевой, то нужна блокировка
            synchronized (DataBaseHelper.class) {
                if (mInstance == null)
                    mInstance = new DataBaseHelper(context.getApplicationContext());
            }
        }
        return mInstance;
    }

    /**
     * Create database
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_RSS_FEEDS = "CREATE TABLE " + TABLE_RSS_FEEDS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + KEY_DISPLAY_NAME + " TEXT UNIQUE,"
                + KEY_LINK_TO_FEED + " TEXT" + LAST_UPDATE + " INTEGER" + ")";
        db.execSQL(CREATE_TABLE_RSS_FEEDS);
    }

    /**
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RSS_FEEDS);
        onCreate(db);
    }

    /**
     *
     * @param newValue
     */
    public void addNewFeed(FeedSource newValue) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DISPLAY_NAME, newValue.getName());
        values.put(KEY_LINK_TO_FEED, newValue.getLink());

        db.insert(TABLE_RSS_FEEDS, null, values);
        db.close();
    }

    /**
     *
     * @param name
     * @return
     */
    public int getIdByName(String name)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_RSS_FEEDS, new String[]{KEY_ID,
                        KEY_DISPLAY_NAME, KEY_LINK_TO_FEED}, KEY_DISPLAY_NAME + "=?",
                new String[]{name}, null, null, null, null);

        FeedSource rss_feed = null;

        if(cursor.moveToFirst() && cursor.getCount() >= 1) {
            rss_feed = new FeedSource(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
        }

        cursor.close();
        db.close();
        return rss_feed.getId();
    }

    /**
     *
     * @param name
     * @return
     */
    public FeedSource getFeedByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_RSS_FEEDS, new String[]{KEY_ID,
                        KEY_DISPLAY_NAME, KEY_LINK_TO_FEED}, KEY_DISPLAY_NAME + "=?",
                new String[]{name}, null, null, null, null);

        FeedSource rss_feed = null;

        if(cursor.moveToFirst() && cursor.getCount() >= 1) {
            rss_feed = new FeedSource(cursor.getString(1), cursor.getString(2));
        }

        cursor.close();
        db.close();
        return rss_feed;
    }

    /**
     *
     * @param id
     * @return
     */
    @Nullable
    public FeedSource getFeedById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_RSS_FEEDS, new String[]{KEY_ID,
                        KEY_DISPLAY_NAME, KEY_LINK_TO_FEED}, KEY_ID + "=?",
                new String[]{Integer.toString(id)}, null, null, null, null);

        FeedSource rss_feed = null;

        if(cursor.moveToFirst() && cursor.getCount() >= 1) {
            rss_feed = new FeedSource(cursor.getString(1), cursor.getString(2));
        }

        cursor.close();
        db.close();
        return rss_feed;
    }

    /**
     *
     * @return
     */
    public ArrayList<FeedSource> getAllStoredFeeds() {
        ArrayList<FeedSource> feedsList = new ArrayList<FeedSource>();
        String selector = "SELECT * FROM " + TABLE_RSS_FEEDS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selector, null);

        if (cursor.moveToFirst()) {
            do {
                FeedSource storedFeed = new FeedSource(cursor.getString(1), cursor.getString(2));
                feedsList.add(storedFeed);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return feedsList;
    }

    /**
     *
     * @param sfd
     * @return
     */
    public int updateStoredFeed(FeedSource sfd) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DISPLAY_NAME, sfd.getName());
        values.put(KEY_LINK_TO_FEED, sfd.getLink());

        int res = db.update(TABLE_RSS_FEEDS, values, KEY_DISPLAY_NAME + " = ?",
                new String[]{String.valueOf(sfd.getName())});
        db.close();
        return res;
    }

    /**
     *
     * @param id
     */
    public void deleteStoredFeed(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RSS_FEEDS, KEY_ID + " = " + id, null);
        db.close();
    }

    /**
     *
     * @param Name
     */
    public void deleteStoredFeed(String Name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RSS_FEEDS, KEY_DISPLAY_NAME + " = '" + Name + "'", null);
        db.close();
    }

    /**
     *
     */
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RSS_FEEDS, null, null);
        db.close();
    }

    /**
     *
     * @return
     */
    public int getStoredFeedCount() {
        String countQuery = "SELECT * FROM " + TABLE_RSS_FEEDS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }
}
