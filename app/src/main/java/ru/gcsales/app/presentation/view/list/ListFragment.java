package ru.gcsales.app.presentation.view.list;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import ru.gcsales.app.App;
import ru.gcsales.app.R;
import ru.gcsales.app.data.model.internal.ListEntry;
import ru.gcsales.app.presentation.Router;
import ru.gcsales.app.presentation.presenter.ListPresenter;

/**
 * Grocery list fragment.
 *
 * @author Maxim Surovtsev
 * @since 04/05/2019
 */
public class ListFragment extends MvpAppCompatFragment implements ListView, View.OnClickListener, NewItemDialogFragment.OnNewEntryListener {

    public static final String TAG = "ListFragment";

    @Inject
    Provider<ListPresenter> mPresenterProvider;
    @Inject
    Router mRouter;

    @InjectPresenter
    ListPresenter mPresenter;

    private View mPlaceholderView;
    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mFloatingActionButton;
    private ListEntriesAdapter mAdapter;

    /**
     * Creates a new instance of this fragment.
     *
     * @return new fragment instance
     */
    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        App.getComponent().inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mPlaceholderView = view.findViewById(R.id.placeholder);
        mProgressBar = view.findViewById(R.id.progress_bar);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mAdapter = new ListEntriesAdapter(mPresenter::openItems, mPresenter::removeEntry);
        mRecyclerView.setAdapter(mAdapter);
        mFloatingActionButton = view.findViewById(R.id.fab);
        mFloatingActionButton.setOnClickListener(this);
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
    public void setEntries(@NonNull List<ListEntry> entries) {
        mAdapter.setEntries(entries);
        mRecyclerView.setVisibility(entries.size() > 0 ? View.VISIBLE : View.GONE);
        mPlaceholderView.setVisibility(entries.size() > 0 ? View.GONE : View.VISIBLE);
    }

    @Override
    public void startItemsFlow(@NonNull ListEntry entry) {
        mRouter.startItemsFlow(getActivity(), entry.getName(), null, entry.getName());
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag(NewItemDialogFragment.TAG);
        if (prev != null) {
            transaction.remove(prev);
        }

        NewItemDialogFragment fragment = NewItemDialogFragment.newInstance();
        fragment.setTargetFragment(this, 0);
        fragment.show(transaction, NewItemDialogFragment.TAG);

        mFloatingActionButton.hide();
    }

    @Override
    public void onNewEntry(@Nullable String entry) {
        mPresenter.addEntry(entry);
        mFloatingActionButton.show();
    }

    @ProvidePresenter
    ListPresenter providePresenter() {
        return mPresenterProvider.get();
    }
}
