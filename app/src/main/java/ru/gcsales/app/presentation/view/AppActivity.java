package ru.gcsales.app.presentation.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import javax.inject.Inject;

import ru.gcsales.app.App;
import ru.gcsales.app.AuthManager;
import ru.gcsales.app.R;
import ru.gcsales.app.presentation.Router;

/**
 * Main and the only activity of the app.
 *
 * @author Maxim Surovtsev
 * @since 06/04/2019
 */
public class AppActivity extends AppCompatActivity {

    @Inject
    AuthManager mAuthManager;
    @Inject
    Router mRouter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        App.getComponent().inject(this);

        if (mAuthManager.isSignedIn()) {
            mRouter.startMainFlow(this);
        } else {
            mRouter.startSignInFlow(this);
        }
    }
}