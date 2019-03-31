package ru.gcsales.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        checkSignIn();
    }

    private void checkSignIn() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
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
