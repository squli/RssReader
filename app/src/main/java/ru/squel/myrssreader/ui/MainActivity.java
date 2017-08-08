package ru.squel.myrssreader.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import ru.squel.myrssreader.R;
import ru.squel.myrssreader.data.dataTypes.FeedSource;
import ru.squel.myrssreader.data.dataTypes.PostFromRss;
import ru.squel.myrssreader.feedsList.FeedListAdapter;
import ru.squel.myrssreader.feedsList.OnNavigationViewListener;
import ru.squel.myrssreader.feedsList.RecyclerItemClickListener;
import ru.squel.myrssreader.postlist.PostFragmentListAdapter;
import ru.squel.myrssreader.presenter.MainActivityPresenter;
import ru.squel.myrssreader.presenter.ViewPresenterContract;

public class MainActivity extends AppCompatActivity implements OnNavigationViewListener, ViewPresenterContract.View {
    private static final String LOG_TAG = "MAIN_ACTIVITY";

    /// для запроса разрешений в процессе работы
    public static final int MY_PERMISSIONS_WRTIE_EXT_STORAGE = 0;

    /// url, который отображается во фрагменте детального отображения
    public String currentLink;

    /// для отображения списка публикаций
    private PostFragmentListAdapter postListAdapter;
    /// для отображения списка RSS лент
    private FeedListAdapter feedListAdapter;

    /// сам список публикаций
    private ArrayList<PostFromRss> postsFromRss = new ArrayList<PostFromRss>();

    ///
    private PostFragment fragmentFeedDetail = null;


    private MainActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ///создать левое выползающее меню
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        if (presenter == null)
            presenter = new MainActivityPresenter(this, this, getLoaderManager());

        /**
         * ЗАПОЛНЕНИЕ ВЫПОЛЗАЮЩЕГО МЕНЮ
         */
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        feedListAdapter = new FeedListAdapter(this, this);
        RecyclerView mFeedsRecyclerView = (RecyclerView) findViewById(R.id.menuList);
        mFeedsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFeedsRecyclerView.setAdapter(feedListAdapter);

        TextView textViewAddNewFeed = (TextView) findViewById(R.id.left_menu_add_new_feed);
        textViewAddNewFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditFeedDialogFragment editFeedDialog = new EditFeedDialogFragment();
                /// устновка обработчика добавления новой ленты(его реализует презентер)
                editFeedDialog.setFeedSourceToAdd(presenter);
                // показать диалог
                editFeedDialog.show(getFragmentManager(), "Add new feed dialog");
            }
        });
        //----------------------------------

        /**
         * Проверка сохраненных состояний - отображение детального фрагмента
         */
        FragmentManager fm = getSupportFragmentManager();
        fragmentFeedDetail = (PostFragment) fm.findFragmentByTag("linkOfFragment");
        final Intent intent = new Intent(this, PostDetailActivity.class);
        if (fragmentFeedDetail == null) {
            /// Если ориентация
            fragmentFeedDetail = (PostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_detail);
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                String link = extras.getString("selectedValue");
                if (link != null && fragmentFeedDetail != null && fragmentFeedDetail.isInLayout()) {
                    fragmentFeedDetail.displayLink(link);
                }
            }
        }
        //----------------------------------

        /**
         * Список постов
         */
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.fragment_post_list);
        postListAdapter = new PostFragmentListAdapter();

        if (mRecyclerView != null) {
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(postListAdapter);

            mRecyclerView.addOnItemTouchListener(
                    new RecyclerItemClickListener(this,
                            mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                        // обработчик нажатия на элемент списка
                        @Override
                        public void onItemClick(View view, int position) {
                            currentLink = postListAdapter.getItem(position).getLink();
                            int orientation = getResources().getConfiguration().orientation;
                            if (orientation != Configuration.ORIENTATION_PORTRAIT)
                                fragmentFeedDetail.displayLink(currentLink);
                            else {
                                intent.putExtra("selectedValue", currentLink);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onLongItemClick(View view, int position) {
                            // do whatever
                            long id = postListAdapter.getItemId(position);
                            Log.d(LOG_TAG, "RecyclerItemClickListener: onLongItemClick: position = " + id);
                        }
                    })
            );
        }
    }

    @Override
    protected void onStart () {
        super.onStart();
        presenter.getFeedSource();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, EditFeedDialogFragment.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_send_DB_by_email) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Обновление фрагмента со списком постов заданного feedList
     * @param link
     */
    @Override
    public void updatePostsList(String link) {
        if (!link.equals(currentLink)) {
            currentLink = link;
            presenter.updatePostList(link);
        }
    }

    //---------------------- CONTEXT MENU--------------------------------------------
    @Override
    public void registerContextmenu(View v) {
        registerForContextMenu(v);
    }

    @Override
    public void createContexMenu(ContextMenu menu,
                                 View v,
                                 ContextMenu.ContextMenuInfo menuInfo,
                                 final FeedSource feedSource) {

        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle(getResources().getString(R.string.context_menu_title));

        /// Кнопка редактирования ленты из списка - вызов диалога редактирования
        menu.add(0, v.getId(), 0, getResources().getString(R.string.context_menu_edit_title))
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                EditFeedDialogFragment editFeedDialog = new EditFeedDialogFragment();
                /// устновка выбранной ленты и обработчика(его реализует презентер)
                editFeedDialog.setFeedSourceToEdit(feedSource, presenter);
                // показать диалог
                editFeedDialog.show(getFragmentManager(), "Edit feed dialog");
                Log.d(LOG_TAG, "Long clicked on position " + feedSource.getLink() + " on Edit button");
                return false;
            }
        });
        /// Кнопка удаления ленты из списка
        menu.add(0, v.getId(), 0, getResources().getString(R.string.context_menu_delete_title))
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                presenter.feedSourceDelete(feedSource);
                Log.d(LOG_TAG, "Long clicked on position " + feedSource.getLink() + " on Delete button");
                presenter.getFeedSource();
                return false;
            }
        });
    }
    //----------------------//CONTEXT MENU--------------------------------------------

    @Override
    public void updateFeedAdapter(ArrayList<FeedSource> response) {
        feedListAdapter.setFeedSourcesList(response);
        feedListAdapter.notifyDataSetChanged();
    }

    public void updatePostAdapter(ArrayList<PostFromRss> response) {
        postListAdapter.setPostSourcesList(response);
        postListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Intent intent = new Intent(this, FragmentActivity.class);
            intent.putExtra("selectedValue", currentLink);
            startActivity(intent);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // store the data in the fragment
        fragmentFeedDetail.setLinkOfFragment(currentLink);
    }


}
