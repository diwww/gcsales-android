package ru.gcsales.app.presentation;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import ru.gcsales.app.R;
import ru.gcsales.app.presentation.view.items.ItemsFlowFragment;
import ru.gcsales.app.presentation.view.main.MainFlowFragment;
import ru.gcsales.app.presentation.view.signin.SignInFlowFragment;

/**
 * Router. Starts different flows of the app.
 *
 * @author Maxim Surovtsev
 * @since 06/04/2019
 */
public class Router {

    public void startMainFlow(@NonNull FragmentManager fm) {
        fm.beginTransaction()
                .replace(R.id.app_container, MainFlowFragment.newInstance())
                .commit();
    }

    public void startSignInFlow(@NonNull FragmentManager fm) {
        fm.beginTransaction()
                .replace(R.id.app_container, SignInFlowFragment.newInstance())
                .commit();
    }

    public void startItemsFlow(@NonNull FragmentManager fm) {
        fm.beginTransaction()
                .replace(R.id.app_container, ItemsFlowFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }
}
