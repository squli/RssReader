package ru.squel.myrssreader.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import ru.squel.myrssreader.R;
import ru.squel.myrssreader.data.dataTypes.FeedSource;

/**
 *
 */
public class EditFeedDialogFragment extends DialogFragment {

    private static final String LOG_TAG = EditFeedDialogFragment.class.getSimpleName();

    /// редактируемая лента
    private FeedSource feedSourceToEdit;

    /// колбэк после редактирования
    private EditFeedSourceCallback callback;

    /// элементы гуя
    private EditText editTextName;
    private EditText editTextLink;

    /// добавление нового или правка имеющегося
    private boolean addNewFeedSource = false;

    /**
     * Заполнение значений полей
     * @param feedSourceToEdit
     * @param callback
     */
    public void setFeedSourceToEdit(FeedSource feedSourceToEdit, EditFeedSourceCallback callback) {
        this.feedSourceToEdit = feedSourceToEdit;
        this.callback = callback;
    }

    public void setFeedSourceToAdd(EditFeedSourceCallback callback) {
        addNewFeedSource = true;
        this.callback = callback;
    }

    // Создание и возвращение объекта AlertDialog
    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        // Создание диалогового окна
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());
        View colorDialogView = getActivity().getLayoutInflater().inflate(
                R.layout.edit_feed_fragment, null);
        builder.setView(colorDialogView); // Добавление GUI в диалоговое окно
        editTextName = (EditText) colorDialogView.findViewById(R.id.editFeedName);
        editTextLink = (EditText) colorDialogView.findViewById(R.id.editFeedLink);

        if (!addNewFeedSource) {
            // Назначение сообщения AlertDialog
            builder.setTitle(R.string.edit_feed_dialog_title);
            editTextName.setText(feedSourceToEdit.getName());
            editTextLink.setText(feedSourceToEdit.getLink());
        }
        else
            builder.setTitle(R.string.add_feed_dialog_title);

        // Добавление кнопки подтверждения внесения изменений
        builder.setPositiveButton(R.string.edit_feed_dialog_ok_button,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        FeedSource newValue = new FeedSource(
                                editTextName.getText().toString(),
                                editTextLink.getText().toString());
                        if (!addNewFeedSource)
                            callback.EditFeedSourceCallback(feedSourceToEdit, newValue);
                        else
                            callback.AddFeedSourceCallback(newValue);
                    }
                }
        );

        // Добавление кнопки отмены изменений
        builder.setNegativeButton(R.string.edit_feed_dialog_cancel_button,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //close dialog
                    }
                }
        );

        return builder.create(); // Возвращение диалогового окна
    }

    /**
     * Заполнить значениями по умолчанию
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }
}
