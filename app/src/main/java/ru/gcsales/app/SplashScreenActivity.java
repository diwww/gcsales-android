package ru.gcsales.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import ru.gcsales.app.auth.AuthManager;

/**
 * Splash screen activity.
 *
 * <p>
 * Checks whether the user is signed in.
 * If the user is signed in, then {@link MainActivity} is launched.
 * Otherwise {@link SignInActivity} is launched.
 * </p>
 *
 * @author Maxim Surovtsev
 * @since 30/03/2019
 */
public class SplashScreenActivity extends AppCompatActivity {

    @Inject
    AuthManager mAuthManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        App.getComponent().inject(this);
        checkSignIn();
    }

    private void checkSignIn() {
        boolean signedIn = mAuthManager.isSignedIn();
        if (signedIn) {
            startMainActivity();
        } else {
            startSignInActivity();
        }
    }

    private void startMainActivity() {
        startActivity(MainActivity.newIntent(this));
        finish();
    }

    private void startSignInActivity() {
        startActivity(SignInActivity.newIntent(this));
        finish();
    }
}
