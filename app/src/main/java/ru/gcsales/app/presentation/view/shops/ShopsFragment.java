package ru.gcsales.app.presentation.view.shops;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import ru.gcsales.app.App;
import ru.gcsales.app.R;
import ru.gcsales.app.data.model.Shop;
import ru.gcsales.app.presentation.presenter.shops.ShopsPresenter;

/**
 * Fragment which contains available shops.
 *
 * @author Maxim Surovtsev
 * @since 01/04/2019
 */
public class ShopsFragment extends MvpAppCompatFragment implements ShopsView {

    public static final String TAG = "ShopsFragment";

    @Inject
    Provider<ShopsPresenter> mPresenterProvider;

    @InjectPresenter
    ShopsPresenter mPresenter;

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
    public void onAttach(Context context) {
        App.getComponent().inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shops, container, false);
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
    public void setShops(List<Shop> shops) {
        mAdapter.setShops(shops);
    }

    @ProvidePresenter
    ShopsPresenter providePresenter() {
        return mPresenterProvider.get();
    }

    private void initViews(View view) {
        mProgressBar = view.findViewById(R.id.progress_bar);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mAdapter = new ShopsAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }
}
