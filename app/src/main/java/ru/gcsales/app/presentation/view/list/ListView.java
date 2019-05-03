package ru.gcsales.app.presentation.view.list;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import androidx.annotation.NonNull;
import ru.gcsales.app.data.model.internal.ListEntry;
import ru.gcsales.app.presentation.view.base.ErrorView;
import ru.gcsales.app.presentation.view.base.ProgressView;

/**
 * MVP view for shopping list screen.
 *
 * @author Maxim Surovtsev
 * @since 11/04/2019
 */
public interface ListView extends MvpView, ProgressView, ErrorView {

    /**
     * Sets the list of entries.
     *
     * @param entries list of entries
     */
    void setEntries(@NonNull List<ListEntry> entries);

    /**
     * Starts map flow.
     *
     * @param entry shopping list entry
     */
    void startMapFlow(@NonNull ListEntry entry);
}
