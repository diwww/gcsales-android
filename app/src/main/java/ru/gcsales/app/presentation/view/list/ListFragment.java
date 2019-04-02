package ru.gcsales.app.presentation.view.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.gcsales.app.R;

/**
 * Fragment which contains shopping list.
 *
 * @author Maxim Surovtsev
 * @since 01/04/2019
 */
public class ListFragment extends Fragment {

    public static final String TAG = "ListFragment";

    /**
     * Creates a new instance of this fragment.
     *
     * @return new fragment instance
     */
    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
