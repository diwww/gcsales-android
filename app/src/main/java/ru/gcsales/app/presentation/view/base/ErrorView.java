package ru.gcsales.app.presentation.view.base;

/**
 * View for showing errors.
 *
 * @author Maxim Surovtsev
 * @since 07/04/2019
 */
public interface ErrorView {

    /**
     * Shows an error message.
     *
     * @param throwable exception object
     */
    void showError(Throwable throwable);
}
