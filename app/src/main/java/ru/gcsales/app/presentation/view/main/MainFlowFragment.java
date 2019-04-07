package ru.gcsales.app.presentation.view.main;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import ru.gcsales.app.App;
import ru.gcsales.app.AuthManager;
import ru.gcsales.app.R;
import ru.gcsales.app.presentation.Router;
import ru.gcsales.app.presentation.view.list.ListFragment;
import ru.gcsales.app.presentation.view.map.MapFragment;
import ru.gcsales.app.presentation.view.shops.ShopsFragment;

/**
 * Main flow fragment of the app.
 *
 * @author Maxim Surovtsev
 * @since 30/03/2019
 */
public class MainFlowFragment extends Fragment {

    private static final String TAG = "MainActivity";

    @Inject
    AuthManager mAuthManager;
    @Inject
    Router mRouter;

    /**
     * Creates a new instance of this fragment.
     *
     * @return new fragment instance
     */
    public static MainFlowFragment newInstance() {
        return new MainFlowFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        App.getComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        loadFragments();
    }

    private void initViews(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        BottomNavigationView bottomNavigation = view.findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new OnNavigationItemSelectedListener());
    }

    private void loadFragments() {


        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.container, ShopsFragment.newInstance())
                .commit();
    }

    private class OnNavigationItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fm = getChildFragmentManager();
            switch (item.getItemId()) {
                case R.id.navigation_shops:
                    fm.beginTransaction()
                            .replace(R.id.container, ShopsFragment.newInstance())
                            .addToBackStack(null)
                            .commit();
                    break;
                case R.id.navigation_list:
                    fm.beginTransaction()
                            .replace(R.id.container, ListFragment.newInstance())
                            .commit();
                    break;
                case R.id.navigation_map:
                    fm.beginTransaction()
                            .replace(R.id.container, MapFragment.newInstance())
                            .commit();
                    break;
            }

            return true;
        }
    }
}
