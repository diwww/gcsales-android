package ru.gcsales.app.presentation.view.map;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.gcsales.app.R;

/**
 * Fragment with map of nearest shops.
 *
 * @author Maxim Surovtsev
 * @since 01/04/2019
 */
public class MapFragment extends Fragment {

    public static final String TAG = "MapFragment";

    /**
     * Creates a new instance of this fragment.
     *
     * @return new fragment instance
     */
    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }
}
