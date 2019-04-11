package ru.gcsales.app.presentation.presenter;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import androidx.annotation.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import ru.gcsales.app.data.model.ListEntry;
import ru.gcsales.app.data.repository.ListRepository;
import ru.gcsales.app.presentation.view.list.ListView;

/**
 * MVP presenter for shopping list screen.
 *
 * @author Maxim Surovtsev
 * @since 11/04/2019
 */
@InjectViewState
public class ListPresenter extends MvpPresenter<ListView> {

    private final ListRepository mRepository;
    private ListenerRegistration mListenerRegistration;

    public ListPresenter(@NonNull ListRepository repository) {
        mRepository = repository;
    }

    @Override
    protected void onFirstViewAttach() {
        getViewState().showProgress(true);
    }

    public void attachListener() {
        mListenerRegistration = mRepository.addSnapshotListener((snapshots, e) -> {
            if (e != null) {
                ListPresenter.this.getViewState().showError(e);
            }

            List<ListEntry> entries = new ArrayList<>();
            if (snapshots != null) {
                for (QueryDocumentSnapshot snapshot : snapshots) {
                    ListEntry entry = snapshot.toObject(ListEntry.class);
                    entry.setId(snapshot.getId());
                    entries.add(entry);
                }
            }
            ListPresenter.this.getViewState().setEntries(entries);
            getViewState().showProgress(false);
        });
    }

    public void detachListener() {
        mListenerRegistration.remove();
        mListenerRegistration = null;
    }


    public void incrementCount(ListEntry entry) {
        Disposable disposable = mRepository.incrementCount(entry)
                .subscribe(() -> {
                }, throwable -> {
                    getViewState().showError(throwable);
                });
    }

    public void decrementCount(ListEntry entry) {
        Disposable disposable = mRepository.decrementCount(entry)
                .subscribe(() -> {
                }, throwable -> {
                    getViewState().showError(throwable);
                });
    }
}
