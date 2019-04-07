package ru.gcsales.app.presentation.view.items;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.gcsales.app.R;

/**
 * Fragment for displaying items for concrete shop.
 *
 * @author Maxim Surovtsev
 * @since 06/04/2019
 */
public class ItemsFlowFragment extends Fragment {

    /**
     * Creates a new instance of this fragment.
     *
     * @return new fragment instance
     */
    public static ItemsFlowFragment newInstance() {
        return new ItemsFlowFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_items, container, false);
    }



}
