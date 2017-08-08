package ru.squel.myrssreader;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.util.Log;

import org.junit.Test;

import java.util.ArrayList;

import ru.squel.myrssreader.data.local.DataBaseHelper;
import ru.squel.myrssreader.data.dataTypes.FeedSource;

/**
 * Created by Саша on 20.06.2017.
 */
public class DataBaseHelperTest extends AndroidTestCase {

    private static final String LOG_TAG = "DB_tester";
    private DataBaseHelper db;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
        db = DataBaseHelper.getInstance(context);
    }

    @Override
    public void tearDown() throws Exception {
        db.deleteAll();
        super.tearDown();
    }

    @Test
    public void testOnCreate() throws Exception {
    }

    @Test
    public void testOnUpgrade() throws Exception {

    }

    @Test
    public void testAddNewFeed() throws Exception {
        FeedSource nf = new FeedSource("Name", "www.name.com");
        db.addNewFeed(nf);
        Log.d(LOG_TAG, "testAddNewFeed: " + db.getStoredFeedCount());

        assertEquals(1, db.getStoredFeedCount());
    }

    @Test
    public void testGetFeedById() throws Exception {
        FeedSource nf = new FeedSource("Name", "www.name.com");
        db.addNewFeed(nf);

        ArrayList<FeedSource>arr_nf = db.getAllStoredFeeds();
        Log.d(LOG_TAG, "testGetFeedById: " + arr_nf.get(0).getId() + " " + arr_nf.get(0).getName() + " " + arr_nf.get(0).getLink());

        nf = db.getFeedById(1);

        assertEquals("Name", nf.getName());
    }

    @Test
    public void testGetFeedByName() throws Exception {
        FeedSource nf = new FeedSource("Name", "www.name.com");
        db.addNewFeed(nf);

        ArrayList<FeedSource>arr_nf = db.getAllStoredFeeds();
        Log.d(LOG_TAG, "testGetFeedByName: " + arr_nf.get(0).getId() + " " + arr_nf.get(0).getName() + " " + arr_nf.get(0).getLink());

        nf = db.getFeedByName("Name");

        assertEquals("Name", nf.getName());
    }


    public void testGetAllStoredFeeds() throws Exception {
        FeedSource nf1 = new FeedSource("Name", "www.name0.com");
        FeedSource nf = new FeedSource("Name1", "www.name.com");
        db.addNewFeed(nf1);
        db.addNewFeed(nf);
        ArrayList<FeedSource>arr_nf = db.getAllStoredFeeds();

        assertEquals(2, arr_nf.size());
    }

    public void testUpdateStoredFeed() throws Exception {
        FeedSource nf = new FeedSource("Name0", "www.name0.com");
        db.addNewFeed(nf);
        FeedSource nf1 = new FeedSource("Name0", "www.name01.com");
        db.updateStoredFeed(nf1);
        nf = db.getFeedByName("Name0");

        assertEquals("www.name01.com", nf.getLink());
    }

    public void testDeleteStoredFeed() throws Exception {
        FeedSource nf = new FeedSource("Name0", "www.name0.com");
        db.addNewFeed(nf);
        nf = new FeedSource("Name01", "www.name01.com");
        db.addNewFeed(nf);

        db.deleteStoredFeed(1);
        assertEquals(1, db.getStoredFeedCount());
    }

    public void testDeleteAll() throws Exception {

    }

    public void testGetStoredFeedCount() throws Exception {
        FeedSource nf1 = new FeedSource("Name", "www.name0.com");
        FeedSource nf = new FeedSource("Name1", "www.name.com");
        db.addNewFeed(nf1);
        db.addNewFeed(nf);

        assertEquals(db.getStoredFeedCount(), 2);
    }
}