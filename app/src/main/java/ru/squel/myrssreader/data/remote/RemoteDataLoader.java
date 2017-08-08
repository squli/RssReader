package ru.squel.myrssreader.data.remote;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import ru.squel.myrssreader.data.dataTypes.PostFromRss;

/**
 * Created by sq on 06.08.2017.
 */
public class RemoteDataLoader extends AsyncTaskLoader<ArrayList<PostFromRss>> implements Constants {

    public static final String LOG_TAG = RemoteDataLoader.class.getSimpleName();

    /// настройки для http запроса:
    private final int ReadTimeout_ms = 10000;
    private final int ConnectTimeout_ms = 15000;

    /// url для получения данных
    private String mUrl;

    public RemoteDataLoader(Context context, Bundle bundle) {
        super(context);
        if (bundle != null){
            mUrl = bundle.getString(Constants.LINK_NAME);
        }
    }

    @Override
    public void forceLoad() {
        Log.d(LOG_TAG, "forceLoad");
        super.forceLoad();
    }

    @Override
    public ArrayList<PostFromRss> loadInBackground() {
        Log.d(LOG_TAG, "loadInBackground finished");
        return getXMLFromUrl(mUrl);
    }

    /**
     *
     * @param url
     * @return
     */
    private ArrayList<PostFromRss> getXMLFromUrl(String url) {

        XmlPullParserFactory xmlFactoryObject;
        ArrayList<PostFromRss> response = new ArrayList<PostFromRss>();

        try {
            HttpURLConnection conn = (HttpURLConnection)(new URL(url)).openConnection();

            conn.setReadTimeout(ReadTimeout_ms);
            conn.setConnectTimeout(ConnectTimeout_ms);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            // Starts the query
            conn.connect();
            InputStream stream = conn.getInputStream();

            xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser myparser = xmlFactoryObject.newPullParser();

            myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            myparser.setInput(stream, null);

            response = parseXML(myparser);
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return response;
    }

    /**
     * разбор полученной RSS ленты
     */
    public ArrayList<PostFromRss> parseXML(XmlPullParser myParser) {
        int event;
        String text=null;
        ArrayList<PostFromRss> list = new ArrayList<PostFromRss>();

        try {
            event = myParser.getEventType();

            String title = "";
            String link = "";
            String description = "";

            while (event != XmlPullParser.END_DOCUMENT) {
                String name = myParser.getName();

                switch (event){
                    case XmlPullParser.START_TAG:
                        break;

                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;

                    case XmlPullParser.END_TAG:

                        if(name.equals("title")){
                            title = text;
                        }

                        else if(name.equals("link")){
                            link = text;
                        }

                        else if(name.equals("description")){
                            description = text;
                            if (title != "" &&  link != "")
                            {
                                PostFromRss newPost = new PostFromRss(link, title, description);
                                list.add(newPost);
                                title = "";
                                link = "";
                            }
                        }

                        else{
                        }

                        break;
                }

                event = myParser.next();
            }
        }

        catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


}
