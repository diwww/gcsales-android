package ru.gcsales.app.presentation.view.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import javax.inject.Inject;

import ru.gcsales.app.App;
import ru.gcsales.app.R;
import ru.gcsales.app.auth.AuthManager;
import ru.gcsales.app.presentation.view.list.ListFragment;
import ru.gcsales.app.presentation.view.map.MapFragment;
import ru.gcsales.app.presentation.view.shops.ShopsFragment;

/**
 * Main activity of the app.
 *
 * @author Maxim Surovtsev
 * @since 30/03/2019
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Toolbar mToolbar;
    private BottomNavigationView mBottomNavigation;

    private ShopsFragment mShopsFragment;
    private ListFragment mListFragment;
    private MapFragment mMapFragment;

    /**
     * Creates a new intent to launch this activity.
     *
     * @param context context
     * @return valid {@link Intent} instance
     */
    public static Intent newIntent(@NonNull Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.getComponent().inject(this);

        initViews();
        loadFragments();
    }

    private void initViews() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mBottomNavigation = findViewById(R.id.bottom_navigation);
        mBottomNavigation.setOnNavigationItemSelectedListener(new OnNavigationItemSelectedListener());
    }

    private void loadFragments() {
        mShopsFragment = ShopsFragment.newInstance();
        mListFragment = ListFragment.newInstance();
        mMapFragment = MapFragment.newInstance();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, mShopsFragment, ShopsFragment.TAG)
                .add(R.id.fragment_container, mListFragment, ListFragment.TAG)
                .add(R.id.fragment_container, mMapFragment, MapFragment.TAG)
                .show(mShopsFragment)
                .hide(mListFragment)
                .hide(mMapFragment)
                .commit();
    }

    private class OnNavigationItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fm = getSupportFragmentManager();
            switch (item.getItemId()) {
                case R.id.navigation_shops:
                    fm.beginTransaction()
                            .show(mShopsFragment)
                            .hide(mListFragment)
                            .hide(mMapFragment)
                            .commit();
                    break;
                case R.id.navigation_list:
                    fm.beginTransaction()
                            .hide(mShopsFragment)
                            .show(mListFragment)
                            .hide(mMapFragment)
                            .commit();
                    break;
                case R.id.navigation_map:
                    fm.beginTransaction()
                            .hide(mShopsFragment)
                            .hide(mListFragment)
                            .show(mMapFragment)
                            .commit();
                    break;
            }

            return true;
        }
    }
}
