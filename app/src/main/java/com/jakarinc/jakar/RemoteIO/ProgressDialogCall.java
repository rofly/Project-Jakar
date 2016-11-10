package com.jakarinc.jakar.RemoteIO;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by Henrique on 10/11/2016.
 */

public class ProgressDialogCall extends AsyncTask<Void, Void, Void> {
    private final ProgressDialog progressDialog;

    public ProgressDialogCall(Context ctx) {
        progressDialog = MyCustomProgressDialog.ctor(ctx);
    }

    @Override
    protected  void onPreExecute() {
        super.onPreExecute();

        progressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        // sleep for 5 seconds
        try { Thread.sleep(5000); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        progressDialog.hide();
    }
}