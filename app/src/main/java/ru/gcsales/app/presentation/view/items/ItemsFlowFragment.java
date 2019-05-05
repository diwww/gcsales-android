package ru.gcsales.app.presentation.view.items;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.android.material.snackbar.Snackbar;

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
import ru.gcsales.app.data.model.internal.Item;
import ru.gcsales.app.presentation.Router;
import ru.gcsales.app.presentation.presenter.ItemsPresenter;

/**
 * Fragment for displaying items for concrete shop.
 *
 * @author Maxim Surovtsev
 * @since 06/04/2019
 */
public class ItemsFlowFragment extends MvpAppCompatFragment implements ItemsView {

    public static final String TAG = "ItemsFlowFragment";

    private static final String KEY_TITLE = "KEY_TITLE";
    private static final String KEY_SHOP = "KEY_SHOP";
    private static final String KEY_KEYWORD = "KEY_KEYWORD";

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
     * @param title   title
     * @param shop    shop name
     * @param keyword search keyword
     * @return new fragment instance
     */
    public static ItemsFlowFragment newInstance(@NonNull String title, @Nullable String shop, @Nullable String keyword) {
        Bundle args = new Bundle();
        args.putString(KEY_TITLE, title);
        args.putString(KEY_SHOP, shop);
        args.putString(KEY_KEYWORD, keyword);
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
        Snackbar.make(getView(), R.string.loading_error, Snackbar.LENGTH_SHORT).show();
        Log.e(TAG, throwable.toString());
    }

    @Override
    public void setItems(@NotNull @NonNull List<Item> items) {
        mAdapter.setItems(items);
    }

    @Override
    public void showSnackbar() {
        Snackbar.make(getView(), R.string.item_added, Snackbar.LENGTH_SHORT).show();
    }

    @ProvidePresenter
    ItemsPresenter providePresenter() {
        ItemsPresenter presenter = mPresenterProvider.get();
        presenter.setShop(getShop());
        presenter.setKeyword(getKeyword());
        return presenter;
    }

    private void initViews(View view) {
        mToolbar = view.findViewById(R.id.toolbar);
        mToolbar.setTitle(getTitle());
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        mToolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());
        mProgressBar = view.findViewById(R.id.progress_bar);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mAdapter = new ItemsAdapter(item -> mPresenter.addToList(item));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Nullable
    private String getTitle() {
        if (getArguments() != null) {
            return getArguments().getString(KEY_TITLE);
        }
        return null;
    }

    @Nullable
    private String getShop() {
        if (getArguments() != null) {
            return getArguments().getString(KEY_SHOP);
        }
        return null;
    }

    @Nullable
    private String getKeyword() {
        if (getArguments() != null) {
            return getArguments().getString(KEY_KEYWORD);
        }
        return null;
    }
}
