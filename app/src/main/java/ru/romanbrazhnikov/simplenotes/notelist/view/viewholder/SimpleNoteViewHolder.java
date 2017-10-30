package ru.romanbrazhnikov.simplenotes.notelist.view.viewholder;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ru.romanbrazhnikov.simplenotes.R;
import ru.romanbrazhnikov.simplenotes.base.view.viewholder.BaseViewHolder;
import ru.romanbrazhnikov.simplenotes.entities.SimpleNote;
import ru.romanbrazhnikov.simplenotes.noteeditor.view.NoteEditorActivity;

/**
 * Created by roman on 30.10.17.
 */

public class SimpleNoteViewHolder extends BaseViewHolder<SimpleNote>
        implements
        View.OnClickListener,
        View.OnLongClickListener {
    private long mId;

    // TODO: ButterKnife
    private TextView tvTitle;
    private TextView tvContent;
    private TextView tvDate;
    private Context mContext;

    public SimpleNoteViewHolder(View itemView, Context context) {
        super(itemView);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);

        tvTitle = itemView.findViewById(R.id.tv_title);
        tvContent = itemView.findViewById(R.id.tv_content);
        tvDate = itemView.findViewById(R.id.tv_date);

        mContext = context;
    }

    @Override
    public void bindItem(SimpleNote note) {
        mId = note.getId();
        tvTitle.setText(note.getTitle());
        tvContent.setText(note.getContent());
        tvDate.setText(note.getFormattedDate());
    }


    @Override
    public void onClick(View view) {
        // opening a given note in the editor
        NoteEditorActivity.openActivityWithNote(mContext, mId);
    }

    @Override
    public boolean onLongClick(View view) {
        // showing DeleteDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("Delete the note?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

        // don't call "onClick"
        return true;
    }

    // Delete dialog listener
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    //Yes button clicked
                    Toast.makeText(mContext, "Deleting.", Toast.LENGTH_SHORT).show();
                    // DELETING the note
                    // FIXME: use interactor/repository/view's action
                    // mSimpleNotesBox.remove(mId);
                    // Refreshing UI
                    // FIXME: updateUI()
                    //updateUI();
                    // Closing the dialog
                    dialog.dismiss();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    // Not clicked. Just closing the dialog
                    dialog.dismiss();
                    break;
            }
        }
    };
}
