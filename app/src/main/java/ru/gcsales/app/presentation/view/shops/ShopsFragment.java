package ru.gcsales.app.presentation.view.shops;

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
import androidx.recyclerview.widget.RecyclerView;
import ru.gcsales.app.App;
import ru.gcsales.app.R;
import ru.gcsales.app.data.model.internal.Shop;
import ru.gcsales.app.presentation.Router;
import ru.gcsales.app.presentation.presenter.ShopsPresenter;

/**
 * Fragment which contains available shops.
 *
 * @author Maxim Surovtsev
 * @since 01/04/2019
 */
public class ShopsFragment extends MvpAppCompatFragment implements ShopsView {

    public static final String TAG = "ShopsFragment";

    @Inject
    Router mRouter;
    @Inject
    Provider<ShopsPresenter> mPresenterProvider;

    @InjectPresenter
    ShopsPresenter mPresenter;

    private View mPlaceholderView;
    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    private ShopsAdapter mAdapter;

    /**
     * Creates a new instance of this fragment.
     *
     * @return new fragment instance
     */
    public static ShopsFragment newInstance() {
        return new ShopsFragment();
    }

    @Override
    public void onAttach(@NotNull Context context) {
        App.getComponent().inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shops, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mPlaceholderView = view.findViewById(R.id.placeholder);
        mProgressBar = view.findViewById(R.id.progress_bar);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mAdapter = new ShopsAdapter(mPresenter::openItems);
        mRecyclerView.setAdapter(mAdapter);
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
    public void setShops(@NotNull List<Shop> shops) {
        mAdapter.setShops(shops);
        mRecyclerView.setVisibility(shops.size() > 0 ? View.VISIBLE : View.GONE);
        mPlaceholderView.setVisibility(shops.size() > 0 ? View.GONE : View.VISIBLE);
    }

    @Override
    public void startItemsFlow(@NonNull Shop shop) {
        mRouter.startItemsFlow(getActivity(), shop.getName(), shop.getName(), null);
    }

    @ProvidePresenter
    ShopsPresenter providePresenter() {
        return mPresenterProvider.get();
    }
}
