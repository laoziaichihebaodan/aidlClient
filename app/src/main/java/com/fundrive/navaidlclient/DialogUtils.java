package com.fundrive.navaidlclient;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class DialogUtils {
    private static EditText editText;
    private static View button;
    private static View view;
    private static AlertDialog dialog;

    public static void initDialog(Activity mContext, String message, final View.OnClickListener onClickListener) {
        if (null == view) {
            view = LayoutInflater.from(mContext).inflate(R.layout.dialog_layout, null);
            editText = (EditText) view.findViewById(R.id.et_content);
            button = view.findViewById(R.id.btn_commit);
            dialog = new AlertDialog.Builder(mContext).setTitle("发送内容").setView(view)
                    .create();
        }

        editText.setText(message);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(editText);
                dialog.dismiss();
                view = null;
            }
        });
        dialog.show();
    }

}
