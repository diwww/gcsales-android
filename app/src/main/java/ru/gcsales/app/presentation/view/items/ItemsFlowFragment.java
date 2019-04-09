package ru.gcsales.app.presentation.view.items;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import ru.gcsales.app.App;
import ru.gcsales.app.R;
import ru.gcsales.app.data.model.Item;
import ru.gcsales.app.data.model.Shop;
import ru.gcsales.app.presentation.Router;
import ru.gcsales.app.presentation.presenter.items.ItemsPresenter;
import ru.gcsales.app.presentation.view.ItemClickListener;

/**
 * Fragment for displaying items for concrete shop.
 *
 * @author Maxim Surovtsev
 * @since 06/04/2019
 */
public class ItemsFlowFragment extends MvpAppCompatFragment implements ItemsView, ItemClickListener<Item> {

    public static final String TAG = "ItemsFlowFragment";

    private static final String KEY_SHOP = "KEY_SHOP";

    @Inject
    Router mRouter;
    @Inject
    Provider<ItemsPresenter> mPresenterProvider;

    @InjectPresenter
    ItemsPresenter mPresenter;

    private Toolbar mToolbar;
    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    private ItemsAdapter mAdapter;

    /**
     * Creates a new instance of this fragment.
     *
     * @param shop shop model
     * @return new fragment instance
     */
    public static ItemsFlowFragment newInstance(@NonNull Shop shop) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_SHOP, shop);
        ItemsFlowFragment fragment = new ItemsFlowFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        App.getComponent().inject(this);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_items, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void showProgress(boolean show) {
        mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        mRecyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    @Override
    public void showError(Throwable throwable) {
        Toast.makeText(getActivity(), R.string.loading_error, Toast.LENGTH_SHORT).show();
        Log.e(TAG, throwable.toString());
    }

    @Override
    public void setItems(@NonNull List<Item> items) {
        mAdapter.setItems(items);
    }

    @Override
    public void onItemClicked(Item item) {
        mPresenter.addToList(item);
    }

    @ProvidePresenter
    ItemsPresenter providePresenter() {
        ItemsPresenter presenter = mPresenterProvider.get();
        presenter.setShopId(getShop().getId());
        return presenter;
    }

    private void initViews(View view) {
        mToolbar = view.findViewById(R.id.toolbar);
        mToolbar.setTitle(getShop().getName());
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        mToolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());
        mProgressBar = view.findViewById(R.id.progress_bar);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mAdapter = new ItemsAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Nullable
    private Shop getShop() {
        if (getArguments() != null) {
            return (Shop) getArguments().getSerializable(KEY_SHOP);
        }
        return null;
    }
}
