package ru.gcsales.app.presentation.view.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import ru.gcsales.app.App;
import ru.gcsales.app.R;
import ru.gcsales.app.data.manager.AuthManager;
import ru.gcsales.app.presentation.Router;
import ru.gcsales.app.presentation.view.list.ListFragment;
import ru.gcsales.app.presentation.view.shops.ShopsFragment;

/**
 * Main flow fragment of the app.
 *
 * @author Maxim Surovtsev
 * @since 30/03/2019
 */
public class MainFlowFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Inject
    AuthManager mAuthManager;
    @Inject
    Router mRouter;

    private ShopsFragment mShopsFragment;
    private ListFragment mListFragment;

    /**
     * Creates a new instance of this fragment.
     *
     * @return new fragment instance
     */
    public static MainFlowFragment newInstance() {
        return new MainFlowFragment();
    }

    @Override
    public void onAttach(@NotNull Context context) {
        App.getComponent().inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        initViews(view);
        initFragments();
        displayFragment(mShopsFragment);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.navigation_shops:
                fragment = mShopsFragment;
                break;
            case R.id.navigation_list:
                fragment = mListFragment;
                break;
            default:
                fragment = mShopsFragment;
        }
        displayFragment(fragment);
        return true;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_toolbar_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_sign_out) {
            showSignOutDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initViews(@NonNull View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        BottomNavigationView bottomNavigation = view.findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(this);
    }

    private void initFragments() {
        mShopsFragment = ShopsFragment.newInstance();
        mListFragment = ListFragment.newInstance();
    }

    private void showSignOutDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.sign_out)
                .setMessage(R.string.sign_out_dialog_message)
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    mAuthManager.signOut();
                    mRouter.startSignInFlow(getActivity());
                })
                .setNegativeButton(R.string.no, (dialog, which) -> {
                })
                .create()
                .show();
    }

    /**
     * Displays given fragment and hides other fragments.
     *
     * @param fragment fragment to display
     */
    private void displayFragment(@NonNull Fragment fragment) {
        FragmentManager fm = getChildFragmentManager();

        List<Fragment> addedFragments = fm.getFragments();
        addedFragments.remove(fragment);

        FragmentTransaction transaction = fm.beginTransaction();
        for (Fragment f : addedFragments) {
            transaction.hide(f);
        }
        if (fragment.isAdded()) {
            transaction.show(fragment);
        } else {
            transaction.add(R.id.container, fragment);
        }
        transaction.commit();
    }
}
