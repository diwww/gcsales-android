package ru.gcsales.app.presentation.presenter;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
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

    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    private final ListRepository mRepository;
    private Disposable mListenerDisposable;

    public ListPresenter(@NonNull ListRepository repository) {
        mRepository = repository;
    }

    @Override
    public void onDestroy() {
        mCompositeDisposable.dispose();
    }

    public void attachListener() {
        mListenerDisposable = mRepository.getUpdateObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(__ -> getViewState().showProgress(true))
                .doOnNext(__ -> getViewState().showProgress(false))
                .doOnTerminate(() -> getViewState().showProgress(false))
                .subscribe(this::onEntriesLoaded, this::onError);
        mCompositeDisposable.add(mListenerDisposable);
    }

    public void detachListener() {
        mListenerDisposable.dispose();
    }

    public void incrementCount(@NonNull ListEntry entry) {
        Disposable disposable = mRepository.incrementCount(entry)
                .subscribe(this::onComplete, this::onError);
        mCompositeDisposable.add(disposable);
    }

    public void decrementCount(@NonNull ListEntry entry) {
        Disposable disposable = mRepository.decrementCount(entry)
                .subscribe(this::onComplete, this::onError);
        mCompositeDisposable.add(disposable);
    }

    public void openMap(@NonNull ListEntry entry) {
        getViewState().startMapFlow(entry);
    }

    private void onEntriesLoaded(List<ListEntry> entries) {
        getViewState().setEntries(entries);
    }

    private void onComplete() {
        // TODO: maybe logging
    }

    private void onError(Throwable throwable) {
        getViewState().showError(throwable);
    }
}
