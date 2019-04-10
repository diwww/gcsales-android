package ru.gcsales.app.data.manager;

import android.content.Context;

import androidx.annotation.NonNull;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Firebase authentication manager.
 * <p>
 * This class is intended to perform following actions:
 * <ul>
 * <li>check sign in status</li>
 * <li>sign out</li>
 * <li>renew session</li>
 * </ul>
 * </p>
 *
 * @author Maxim Surovtsev
 * @since 31/03/2019
 */
public class AuthManager {

    private final Context mContext;
    private final FirebaseAuth mAuth;

    public AuthManager(@NonNull Context context, @NonNull FirebaseAuth auth) {
        mContext = context;
        mAuth = auth;
    }

    /**
     * Checks whether the user is signed in.
     */
    public boolean isSignedIn() {
        return mAuth.getCurrentUser() != null;
    }

    /**
     * Performs a sign out.
     *
     * @return {@link Task} with the result of sign out
     */
    public Task<Void> signOut() {
        return AuthUI.getInstance().signOut(mContext);
    }

    public void renewSession() {
        // TODO
    }
}
