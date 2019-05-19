package ru.gcsales.app.presentation.view.list;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import ru.gcsales.app.R;

/**
 * Dialog for adding a new list item.
 *
 * @author Maxim Surovtsev
 * @since 19/05/2019
 */
public class NewItemDialogFragment extends AppCompatDialogFragment implements TextView.OnEditorActionListener, View.OnFocusChangeListener {

    public static final String TAG = "NewItemDialogFragment";

    private EditText mNewItemEditText;

    /**
     * Creates a new instance of this fragment.
     *
     * @return new fragment instance
     */
    public static NewItemDialogFragment newInstance() {
        return new NewItemDialogFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_new_entry, null, false);
        mNewItemEditText = view.findViewById(R.id.edit_text_new_item);
        mNewItemEditText.setOnEditorActionListener(this);
        mNewItemEditText.setOnFocusChangeListener(this);

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity())
                .setView(view)
                .setTitle(R.string.new_entry)
                .setPositiveButton(R.string.ok, (d, w) -> mNewItemEditText.onEditorAction(EditorInfo.IME_ACTION_DONE))
                .setNegativeButton(R.string.cancel, null);

        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();
        mNewItemEditText.requestFocus();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            sendData(mNewItemEditText.getText().toString());
            if (getDialog() != null) {
                getDialog().dismiss();
            }
        }
        return false;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        sendData(null);
        super.onDismiss(dialog);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v.hasFocus() && getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        }
    }

    private void sendData(@Nullable String data) {
        if (getTargetFragment() instanceof OnNewEntryListener) {
            ((OnNewEntryListener) getTargetFragment()).onNewEntry(data);
        }
    }

    /**
     * Interface to pass the result to the host fragment.
     */
    public interface OnNewEntryListener {

        void onNewEntry(@Nullable String entry);
    }
}
