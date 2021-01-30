package in.bitcode.progressdialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

class DownloadThread extends AsyncTask<String, Object, Float> {
    private Handler mHandler;

    public DownloadThread(Handler handler) {

        mHandler = handler;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Float doInBackground(String... files) {

        Log.e("tag", "doInBg: " + Thread.currentThread().getName());

        for(String filePath: files) {

            for (int i = 0; i <= 100; i++) {
                Log.e("tag", i + "%");
                //mBtnDownload.setText(i + "%"); Can not touch the views here

                Object[] progress = new Object[2];
                progress[0] = i;
                progress[1] = filePath;
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
    protected void onProgressUpdate(Object... values) {
        super.onProgressUpdate(values);
        Message message = new Message();
        message.arg1 = 1;
        message.obj = values;
        mHandler.sendMessage(message);
        //mHandler.handleMessage(message);

    }

    @Override
    protected void onPostExecute(Float res) {
        super.onPostExecute(res);
        Message message = new Message();
        message.arg1 = 2;
        message.obj = res;
        mHandler.sendMessage(message);

    }
}



//you can send a message to the handler object only from the thread on which
//the handler object is created











