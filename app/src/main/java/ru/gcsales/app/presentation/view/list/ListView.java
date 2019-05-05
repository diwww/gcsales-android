package ru.gcsales.app.presentation.view.list;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import androidx.annotation.NonNull;
import ru.gcsales.app.data.model.internal.ListEntry;
import ru.gcsales.app.presentation.view.base.ErrorView;
import ru.gcsales.app.presentation.view.base.ProgressView;

/**
 * Grocery list MVP view.
 *
 * @author Maxim Surovtsev
 * @since 04/05/2019
 */
public interface ListView extends MvpView, ErrorView, ProgressView {

    /**
     * Sets the list of entries.
     *
     * @param entries list of entries
     */
    void setEntries(@NonNull List<ListEntry> entries);
}
