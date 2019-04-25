package ru.gcsales.app.presentation.view.items;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import androidx.annotation.NonNull;
import ru.gcsales.app.data.model.Item;
import ru.gcsales.app.presentation.view.base.ErrorView;
import ru.gcsales.app.presentation.view.base.ProgressView;

/**
 * MVP view for items screen.
 *
 * @author Maxim Surovtsev
 * @since 07/04/2019
 */
public interface ItemsView extends MvpView, ProgressView, ErrorView {

    /**
     * Sets the list of items.
     *
     * @param items list of items
     */
    void setItems(@NonNull List<Item> items);

    /**
     * Shows a snackbar which tells that
     * the item is added to the shopping list.
     */
    void showSnackbar();
}
