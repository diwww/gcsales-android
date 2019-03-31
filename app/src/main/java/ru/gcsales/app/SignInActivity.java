package ru.gcsales.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;

import java.util.Arrays;
import java.util.List;

/**
 * Sign in activity.
 * This activity is launched if the user is not signed in.
 * There is a single button which initiates the sign in flow.
 *
 * @author Maxim Surovtsev
 * @since 31/03/2019
 */
public class SignInActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 111;

    private Button mSignInButton;

    /**
     * Creates a new intent to launch this activity.
     *
     * @param context context
     * @return valid {@link Intent} instance
     */
    public static Intent newIntent(@NonNull Context context) {
        return new Intent(context, SignInActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mSignInButton = findViewById(R.id.button_sign_in);
        mSignInButton.setOnClickListener(v -> startSignInFlow());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            handleSignInResponse(resultCode, data);
        }
    }

    private void handleSignInResponse(int resultCode, @Nullable Intent data) {
        IdpResponse response = IdpResponse.fromResultIntent(data);

        if (resultCode == RESULT_OK) {
            startMainActivity();
        } else {
            if (response == null) {
                showToast(R.string.sign_in_cancelled);
                return;
            }
            if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                showToast(R.string.no_internet_connection);
                return;
            }

            showToast(R.string.unknown_error);
        }
    }

    private void startSignInFlow() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTheme(R.style.AuthTheme)
                        .build(),
                RC_SIGN_IN);
    }

    private void startMainActivity() {
        startActivity(MainActivity.newIntent(this));
        finish();
    }

    private void showToast(@StringRes int textId) {
        Toast.makeText(this, textId, Toast.LENGTH_SHORT).show();
    }
}
