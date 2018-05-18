package com.fundrive.navaidlclient.view;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;

/**
 * 本类主要从键盘获取输入内容,传递给底层
 */
public class NavInputConnection extends BaseInputConnection {
    private String TAG = "NavInputConnection";

    public NavInputConnection(View targetView, boolean fullEditor) {
        super(targetView, fullEditor);
    }

    /**
     * 键盘输入内容后的回调
     * @param text
     * @param newCursorPosition
     * @return
     */
    @Override
    public boolean commitText(CharSequence text, int newCursorPosition) {
        Log.e(TAG, "commitText: " + text);
        sendInputContent(text.toString());
        Log.d(TAG, "commitText: sendContent:"+text.toString());
        return super.commitText(text, newCursorPosition);
    }

    /**
     * 键盘按键的回调,这里主要处理del键
     * @param event
     * @return
     */
    @Override
    public boolean sendKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            //删除键处理
            if (event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                sendInputContent("\b");
            }
            //回车的处理
            if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                sendInputContent("\r");
            }
        }

        return true;
    }

    /**
     * 键盘关闭时的回调
     * @return
     */
    @Override
    public boolean finishComposingText() {
        Log.d(TAG, "finishComposingText: ");
        return super.finishComposingText();

    }

    /**
     * 删除备选文字时的回调
     * @param beforeLength
     * @param afterLength
     * @return
     */
    @Override
    public boolean deleteSurroundingText(int beforeLength, int afterLength) {
        Log.d(TAG, "deleteSurroundingText: " + beforeLength + " ==> " + afterLength);
        for (int i = 0; i < beforeLength; i++) {
            sendInputContent("\b");
        }
        return super.deleteSurroundingText(beforeLength, afterLength);
    }

    /**
     * 消息发送
     *
     * @param text
     */
    private void sendInputContent(CharSequence text) {
        if (null != inputText) {
            inputText.inPut(text);
        }
    }
    private InputText inputText;

    public void setInputText(InputText inputText) {
        this.inputText = inputText;
    }
    public interface InputText{
        void inPut(CharSequence text);
    }

}
