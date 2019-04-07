package ru.gcsales.app.presentation.view.signin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import ru.gcsales.app.App;
import ru.gcsales.app.R;
import ru.gcsales.app.presentation.Router;

import static android.app.Activity.RESULT_OK;

/**
 * Sign in flow fragment.
 * This fragment is displayed if the user is not signed in.
 * There is a single button which initiates the sign in flow.
 *
 * @author Maxim Surovtsev
 * @since 06/04/2019
 */
public class SignInFlowFragment extends Fragment {

    private static final int RC_SIGN_IN = 111;

    @Inject
    Router mRouter;

    private View mRootView;
    private Button mSignInButton;

    /**
     * Creates a new instance of this fragment.
     *
     * @return new fragment instance
     */
    public static SignInFlowFragment newInstance() {
        SignInFlowFragment fragment = new SignInFlowFragment();
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        App.getComponent().inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        mRootView = view.findViewById(R.id.root);
        mSignInButton = view.findViewById(R.id.button_sign_in);
        mSignInButton.setOnClickListener(v -> signIn());
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            handleSignInResponse(resultCode, data);
        }
    }

    private void handleSignInResponse(int resultCode, @Nullable Intent data) {
        IdpResponse response = IdpResponse.fromResultIntent(data);

        if (resultCode == RESULT_OK) {
            mRouter.startMainFlow(getActivity());
        } else {
            mRootView.setVisibility(View.VISIBLE);

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

    private void signIn() {
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

        mRootView.setVisibility(View.INVISIBLE);
    }

    private void showToast(@StringRes int textId) {
        Toast.makeText(getActivity(), textId, Toast.LENGTH_SHORT).show();
    }
}
