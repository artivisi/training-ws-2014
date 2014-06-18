package com.artivisi.training.ws.storage.android;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.artivisi.training.ws.storage.android.service.StorageService;
import com.google.common.io.ByteStreams;
import com.google.common.io.Files;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class HelloWorldActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void prosesUpload(View view){
        String serverUrl = ((EditText)findViewById(R.id.txtServerUrl)).getText().toString();
        new UploadTask().execute(serverUrl);
    }

    private class UploadTask extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... serverUrl) {
            try {
                File f = File.createTempFile("upload", "xx");
                ByteStreams.copy(getAssets().open("curl-upload.pdf"), new FileOutputStream(f));
                StorageService ss = new StorageService(serverUrl[0]);
                ss.upload(f);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
