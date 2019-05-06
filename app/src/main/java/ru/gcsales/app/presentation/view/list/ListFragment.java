package ru.gcsales.app.presentation.view.list;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import ru.gcsales.app.App;
import ru.gcsales.app.R;
import ru.gcsales.app.data.model.internal.ListEntry;
import ru.gcsales.app.data.model.internal.Shop;
import ru.gcsales.app.presentation.Router;
import ru.gcsales.app.presentation.presenter.ListPresenter;

/**
 * Grocery list fragment.
 *
 * @author Maxim Surovtsev
 * @since 04/05/2019
 */
public class ListFragment extends MvpAppCompatFragment implements ListView, View.OnClickListener, TextView.OnEditorActionListener {

    public static final String TAG = "ListFragment";

    @Inject
    Provider<ListPresenter> mPresenterProvider;
    @Inject
    Router mRouter;

    @InjectPresenter
    ListPresenter mPresenter;

    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    private EditText mNewItemEditText;
    private ImageButton mAddButton;
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
        mProgressBar = view.findViewById(R.id.progress_bar);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mAdapter = new ListEntriesAdapter(mPresenter::openItems, mPresenter::removeEntry);
        mRecyclerView.setAdapter(mAdapter);
        mNewItemEditText = view.findViewById(R.id.edit_text_new_item);
        mNewItemEditText.setOnEditorActionListener(this);
        mAddButton = view.findViewById(R.id.button_add);
        mAddButton.setOnClickListener(this);
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
    }

    @Override
    public void startItemsFlow(@NonNull ListEntry entry) {
        mRouter.startItemsFlow(getActivity(), entry.getName(), null, entry.getName());
    }

    @ProvidePresenter
    ListPresenter providePresenter() {
        return mPresenterProvider.get();
    }

    @Override
    public void onClick(View v) {
        mNewItemEditText.onEditorAction(EditorInfo.IME_ACTION_DONE);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            mPresenter.addEntry(mNewItemEditText.getText().toString());
            mNewItemEditText.setText(null);
        }
        return false;
    }
}
