package ru.gcsales.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;

import javax.inject.Inject;

import ru.gcsales.app.auth.AuthManager;

/**
 * Main activity of the app.
 *
 * @author Maxim Surovtsev
 * @since 30/03/2019
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Inject
    AuthManager mAuthManager;

    private Button mButton;

    /**
     * Creates a new intent to launch this activity.
     *
     * @param context context
     * @return valid {@link Intent} instance
     */
    public static Intent newIntent(@NonNull Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.getComponent().inject(this);

        mButton = findViewById(R.id.button);
        mButton.setOnClickListener(v -> mAuthManager.signOut()
                .addOnCompleteListener(task -> {
                    startActivity(new Intent(MainActivity.this, SplashScreenActivity.class));
                    finish();
                }));
    }
}
