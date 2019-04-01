package ru.gcsales.app.presentation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.gcsales.app.R;

/**
 * Fragment which contains available shops.
 *
 * @author Maxim Surovtsev
 * @since 01/04/2019
 */
public class ShopsFragment extends Fragment {

    public static final String TAG = "ShopsFragment";

    /**
     * Creates a new instance of this fragment.
     *
     * @return new fragment instance
     */
    public static ShopsFragment newInstance() {
        return new ShopsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shops, container, false);
    }
}
