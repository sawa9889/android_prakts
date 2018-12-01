package com.example.dialogapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;

import java.util.Objects;

public class MyDialogFragment extends AppCompatDialogFragment {

    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder
                = new AlertDialog.Builder
                    (Objects.requireNonNull
                        (getActivity()
                        )
                    );
        builder.setTitle("Danger!!!")
                .setMessage("This message is very essential.")
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((MainActivity) getActivity()).okClicked();
                        dialog.cancel();
                    }
                }).setNeutralButton("What?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((MainActivity) getActivity()).neutralClicked();
                dialog.cancel();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((MainActivity) getActivity()).noClicked();
                dialog.cancel();
            }
        }).setCancelable(true);

        return builder.create();
    }


}
