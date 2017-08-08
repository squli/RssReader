package ru.squel.myrssreader.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import ru.squel.myrssreader.R;

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class PostFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;


    private String linkOfFragment;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PostFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         // для сохранения состояния фрагмента при пересоздании Активити, в которой он отображается
        setRetainInstance(true);
    }

    /*
    Система вызывает этот метод при первом отображении пользовательского интерфейса фрагмента на дисплее.
    Для прорисовки пользовательского интерфейса фрагмента следует возвратить из этого метода объект View,
    который является корневым в макете фрагмента.
    */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_detail, container, false);
        return view;
    }

    public void displayLink(String item){
        WebView viewer = (WebView)getView().findViewById(R.id.webView);
        //viewer.getSettings().setJavaScriptEnabled(true);
        viewer.getSettings().setUserAgentString("Mozilla/5.0 (Linux; U; Android 2.2; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");
        viewer.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return true;
            }
        });
        linkOfFragment = item;
        viewer.loadUrl(item);
    }

    public void setLinkOfFragment(String linkOfFragment) {
        this.linkOfFragment = linkOfFragment;
    }

    public String getlinkOfFragment() {
        return linkOfFragment;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        //void onListFragmentInteraction(DummyItem item);
    }
}
