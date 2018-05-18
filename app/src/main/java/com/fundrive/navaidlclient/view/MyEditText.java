package com.fundrive.navaidlclient.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;

public class MyEditText extends EditText {
    public MyEditText(Context context) {
        super(context);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        NavInputConnection navInputConnection = new NavInputConnection(this, true);
        navInputConnection.setInputText(inputText);
        return navInputConnection;
    }
    private NavInputConnection.InputText inputText;

    public void setInputTextCallback(NavInputConnection.InputText inputText) {
        this.inputText = inputText;
    }

}
