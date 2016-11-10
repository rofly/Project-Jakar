package com.jakarinc.jakar.RemoteIO;

/**
 * Created by Henrique on 09/11/2016.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;

public class MyCustomProgressDialog extends ProgressDialog {
    private AnimationDrawable animation;

    public MyCustomProgressDialog(Context context) {
        super(context);
    }

    public MyCustomProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    public static ProgressDialog ctor(Context context) {
        MyCustomProgressDialog dialog = new MyCustomProgressDialog(context);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        return dialog;
    }

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.drawable.view_custom_progress_dialog);

        ImageView la = (ImageView) findViewById(R.id.animation);
        la.setBackgroundResource(R.drawable.custom_progress_dialog_animation);
        animation = (AnimationDrawable) la.getBackground();
    }*/

    @Override
    public void show() {
        super.show();
        animation.start();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        animation.stop();
    }
}