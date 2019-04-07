package ru.gcsales.app.presentation.view.base;

/**
 * View for showing progress.
 *
 * @author Maxim Surovtsev
 * @since 07/04/2019
 */
public interface ProgressView {

    /**
     * Shows/hides progress or content.
     *
     * @param show {@code true} - show progress and hide content
     */
    void showProgress(boolean show);
}
