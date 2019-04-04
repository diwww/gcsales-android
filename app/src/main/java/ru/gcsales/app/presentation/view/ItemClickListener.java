package ru.gcsales.app.presentation.view;

/**
 * Item click listener.
 *
 * @author Maxim Surovtsev
 * @since 04/04/2019
 */
public interface ItemClickListener<T> {

    /**
     * Executed when item is clicked.
     *
     * @param item clicked item
     */
    void onItemClicked(T item);
}
