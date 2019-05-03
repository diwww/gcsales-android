package ru.gcsales.app.presentation.view.map;

import android.location.Location;

import com.arellomobile.mvp.MvpView;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import androidx.annotation.NonNull;
import ru.gcsales.app.presentation.view.base.ErrorView;
import ru.gcsales.app.presentation.view.base.ProgressView;

/**
 * MVP view for map screen.
 *
 * @author Maxim Surovtsev
 * @since 03/05/2019
 */
public interface MapFlowView extends MvpView, ProgressView, ErrorView {

    void setCurrentLocation(@NonNull Location location);

    void addMarkers(@NonNull List<MarkerOptions> markers);
}
