package ru.gcsales.app.presentation.view.cart;

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

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import ru.gcsales.app.App;
import ru.gcsales.app.R;
import ru.gcsales.app.data.model.internal.CartEntry;
import ru.gcsales.app.presentation.Router;
import ru.gcsales.app.presentation.presenter.CartPresenter;

/**
 * Fragment which contains shopping cart.
 *
 * @author Maxim Surovtsev
 * @since 01/04/2019
 */
public class CartFragment extends MvpAppCompatFragment implements CartView {

    public static final String TAG = "CartFragment";

    @Inject
    Router mRouter;
    @Inject
    Provider<CartPresenter> mListPresenterProvider;

    @InjectPresenter
    CartPresenter mPresenter;

    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    private CartEntriesAdapter mAdapter;

    /**
     * Creates a new instance of this fragment.
     *
     * @return new fragment instance
     */
    public static CartFragment newInstance() {
        return new CartFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        App.getComponent().inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.attachListener();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.detachListener();
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
    public void setEntries(@NonNull List<CartEntry> entries) {
        mAdapter.setEntries(entries);
    }

    @Override
    public void startMapFlow(@NonNull CartEntry entry) {
        mRouter.startMapFlow(getActivity(), entry.getShop());
    }

    @ProvidePresenter
    CartPresenter providePresenter() {
        return mListPresenterProvider.get();
    }

    private void initViews(View view) {
        mProgressBar = view.findViewById(R.id.progress_bar);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mAdapter = new CartEntriesAdapter(mPresenter::incrementCount, mPresenter::decrementCount, mPresenter::openMap);
        mRecyclerView.setAdapter(mAdapter);
    }
}
