package in.bitcode.progressdialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mBtnDownload;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e("tag", "onCreate: " + Thread.currentThread().getName());

        init();
    }

    private void init() {
        mBtnDownload = findViewById(R.id.btnDownload);
        mBtnDownload.setOnClickListener(new BtnDownloadClickListener());
    }

    private class BtnDownloadClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            //start a thread to download the data
            String [] files = {
                    "http://bitcode.in/file1",
                    "http://bitcode.in/file2",
                    "http://bitcode.in/file3"
            };
            //new DownloadThread().execute(files);
            MyHandler myHandler = new MyHandler();
            new DownloadThread( myHandler)
                    .execute(files);

            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("BitCode");
            progressDialog.setMessage("Downloading...");
            //progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }
    }

    public class MyHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg != null && msg.arg1 == 1) {

                progressDialog.setMessage("Downloading " + ((Object[])msg.obj)[1]);
                progressDialog.setProgress((int)((Object[])msg.obj)[0]);

                mBtnDownload.setText( (int)((Object[])msg.obj)[0] + "%");
            }
            if(msg != null && msg.arg1 == 2) {
                mBtnDownload.setText( (Float)msg.obj + "%");
                progressDialog.dismiss();
            }
        }
    }

    /*
    class DownloadThread extends AsyncTask<String, Integer, Float> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("BitCode");
            progressDialog.setMessage("Downloading...");
            //progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected Float doInBackground(String... files) {

            Log.e("tag", "doInBg: " + Thread.currentThread().getName());

            for(String filePath: files) {

                progressDialog.setMessage("Downloading " + filePath);

                for (int i = 0; i <= 100; i++) {
                    Log.e("tag", i + "%");
                    progressDialog.setProgress(i);

                    //mBtnDownload.setText(i + "%"); Can not touch the views here
                    Integer[] progress = new Integer[1];
                    progress[0] = i;
                    publishProgress(progress);

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            return 12.12f;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mBtnDownload.setText(values[0] + "%");
        }

        @Override
        protected void onPostExecute(Float res) {
            super.onPostExecute(res);
            progressDialog.dismiss();
            mBtnDownload.setText(res + "");
        }
    }
    */
}