package ru.gcsales.app.presentation;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import ru.gcsales.app.R;
import ru.gcsales.app.data.model.internal.Shop;
import ru.gcsales.app.presentation.view.items.ItemsFlowFragment;
import ru.gcsales.app.presentation.view.main.MainFlowFragment;
import ru.gcsales.app.presentation.view.map.MapFlowFragment;
import ru.gcsales.app.presentation.view.signin.SignInFlowFragment;

/**
 * Router. Starts different flows of the app.
 *
 * @author Maxim Surovtsev
 * @since 06/04/2019
 */
public class Router {

    public void startMainFlow(@NonNull FragmentActivity activity) {
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.app_container, MainFlowFragment.newInstance())
                .commit();
    }

    public void startSignInFlow(@NonNull FragmentActivity activity) {
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.app_container, SignInFlowFragment.newInstance())
                .commit();
    }

    public void startItemsFlow(@NonNull FragmentActivity activity, @NonNull Shop shop) {
        FragmentManager fm = activity.getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.app_container);

        fm.beginTransaction()
                .add(R.id.app_container, ItemsFlowFragment.newInstance(shop))
                .hide(fragment)
                .addToBackStack(null)
                .commit();
    }

    public void startMapFlow(@NonNull FragmentActivity activity, @NonNull String shop) {
        FragmentManager fm = activity.getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.app_container);

        fm.beginTransaction()
                .add(R.id.app_container, MapFlowFragment.newInstance(shop))
                .hide(fragment)
                .addToBackStack(null)
                .commit();
    }
}
