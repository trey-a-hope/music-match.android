package com.example.musicmatch.app;

import Adapters.SongListAdapter;
import DTO.Song;
import Listeners.RecyclerItemClickListener;
import Miscellaneous.DividerDecoration;
import Navigation.AppNavigator;
import Services.MediaService;
import Services.ModalService;
import android.app.Activity;
import android.content.*;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.*;
import android.widget.TextView;
import android.provider.MediaStore;
import android.widget.Toast;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private AppNavigator appNavigator;
    private TextView tvHeader;
    private ActionBar actionBar;
    private MediaService _mediaService;
    private RecyclerView songListRecylcerView;
    private SongListAdapter songItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUI();
        initObjects();
        setValues();
        initRecyclerView();


    }

    private class SongItemAdapter extends SongListAdapter<SongListAdapter.SongViewHolder> {

        @Override
        public SongListAdapter.SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_item_view, parent, false);
            return new SongViewHolder(view) {
            };
        }

        @Override
        public void onBindViewHolder(SongViewHolder songViewHolder, int position) {
            Song song = songItemAdapter.getItem(position);
            if (songViewHolder instanceof SongViewHolder) {
                songViewHolder.name.setText(song.title);
                songViewHolder.artist.setText(song.artist);
                songViewHolder.dateModified.setText(song.dateModified);
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }
    }

    private void initUI() {
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nvView);
        tvHeader = (TextView) findViewById(R.id.headerTV);
        songListRecylcerView = (RecyclerView) findViewById(R.id.rv);
    }

    private void initObjects() {
        appNavigator = new AppNavigator(this, getApplicationContext());
        toolbar.setBackgroundColor(getResources().getColor(R.color.Red900));
        setSupportActionBar(toolbar);
        setTitle("Music Match");
        actionBar = getSupportActionBar();
        drawerToggle = setupDrawerToggle();
        _mediaService = new MediaService(this);
        songItemAdapter = new SongItemAdapter();
    }

    private void setValues() {
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        drawerLayout.setDrawerListener(drawerToggle);
        tvHeader.setText("Trey's Profile");
        navigationView.setBackgroundColor(getResources().getColor(R.color.Indigo50));
        setupDrawerContent(navigationView);
    }

    private void initRecyclerView() {
        // Set adapter populated with example dummy data
        songItemAdapter.addAll(_mediaService.getSongsFromDevice());
        songListRecylcerView.setAdapter(songItemAdapter);

        // Set layout manager
        final LinearLayoutManager layoutManager = new LinearLayoutManager(songListRecylcerView.getContext());
        songListRecylcerView.setLayoutManager(layoutManager);

        // Add decoration for dividers between list items
        songListRecylcerView.addItemDecoration(new DividerDecoration(MainActivity.this));

        songListRecylcerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Song song = songItemAdapter.getItem(position);
                ModalService.displayNotification(song.title, song.artist, MainActivity.this);
            }
        }));

        songItemAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                //headersDecor.invalidateHeaders();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        appNavigator.navigate(menuItem.getItemId());

        // Highlight the selected item, update the title, and close the drawer
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        drawerLayout.closeDrawers();
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
    }

}
