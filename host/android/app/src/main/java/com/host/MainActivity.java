package com.host;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.host.response.HomeResponse;
import com.host.utils.DispatchUtils;
import com.host.utils.ZipUtils;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends Activity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private LinearLayout linearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        linearLayout = findViewById(R.id.container);

        getHomeInfo();
    }

    /**
     * 获取首页有哪些可以展示的模块
     */
    private void getHomeInfo() {

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url("http://192.168.100.14:8080/home").method("GET", null).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                HomeResponse homeResponse = new Gson().fromJson(response.body().string(), HomeResponse.class);

                for (final HomeResponse.Bundle bundle : homeResponse.data) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Button button = new Button(MainActivity.this);
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            params.setMargins(10, 10, 10, 10);
                            button.setLayoutParams(params);
                            button.setText(bundle.desc);
                            button.setTextColor(Color.WHITE);
                            button.setBackground(getResources().getDrawable(R.drawable.bg));
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    // 检查是否下载过，如果已经下载过则直接打开
                                    String f = MainActivity.this.getFilesDir().getAbsolutePath() + "/" + bundle.name + "/" + bundle.name + ".bundle";
                                    File file = new File((f));
                                    if (file.exists()) {
                                        DispatchUtils.dispatchModel = bundle.name;
                                        DispatchActivity.start(MainActivity.this);
                                    } else {
                                        download(bundle.name);
                                    }

                                }
                            });

                            linearLayout.addView(button);
                        }
                    });

                }

            }
        });
    }


    /**
     * 下载对应的bundle
     *
     * @param bundleName
     */
    private void download(final String bundleName) {
        FileDownloader.setup(this);
        FileDownloader.getImpl().create("http://192.168.100.14:8080/download/bundle/" + bundleName).setPath(this.getFilesDir().getAbsolutePath(), true)

                .setListener(new FileDownloadListener() {
                    @Override
                    protected void started(BaseDownloadTask task) {
                        super.started(task);
                    }

                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {

                        try {
                            //下载之后解压，然后打开
                            ZipUtils.unzip(MainActivity.this.getFilesDir().getAbsolutePath() + "/" + bundleName + ".zip", MainActivity.this.getFilesDir().getAbsolutePath());

                            DispatchUtils.dispatchModel = bundleName;
                            DispatchActivity.start(MainActivity.this);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        Log.d(TAG, "error");
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {

                    }
                }).start();
    }
}
