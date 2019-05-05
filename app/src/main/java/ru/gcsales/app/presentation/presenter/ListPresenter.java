package ru.gcsales.app.presentation.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.gcsales.app.data.model.internal.ListEntry;
import ru.gcsales.app.data.repository.ListRepository;
import ru.gcsales.app.presentation.view.list.ListView;

/**
 * MVP presenter for grocery list screen.
 *
 * @author Maxim Surovtsev
 * @since 04/05/2019
 */
@InjectViewState
public class ListPresenter extends MvpPresenter<ListView> {

    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private final ListRepository mListRepository;
    private Disposable mListenerDisposable;

    public ListPresenter(@NonNull ListRepository listRepository) {
        mListRepository = listRepository;
    }

    @Override
    public void onDestroy() {
        mCompositeDisposable.dispose();
    }

    public void attachListener() {
        mListenerDisposable = mListRepository.getUpdateObservable()
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

    public void addEntry(@Nullable String name) {
        if (name != null && !name.isEmpty()) {
            ListEntry entry = new ListEntry();
            entry.setName(name);
            Disposable disposable = mListRepository.addEntry(entry)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onComplete, this::onError);
            mCompositeDisposable.add(disposable);
        }
    }

    public void removeEntry(@NonNull ListEntry entry) {
        Disposable disposable = mListRepository.removeEntry(entry)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onComplete, this::onError);
        mCompositeDisposable.add(disposable);
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
