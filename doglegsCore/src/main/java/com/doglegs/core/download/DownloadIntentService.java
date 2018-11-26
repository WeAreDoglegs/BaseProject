package com.doglegs.core.download;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.doglegs.core.R;
import com.doglegs.core.rx.RxBus;
import com.doglegs.core.utils.AppUtils;

import io.reactivex.disposables.CompositeDisposable;

public class DownloadIntentService extends IntentService {

    private static final String ACTION_DOWNLOAD = "intentservice.action.download";
    private static final String DOWNLOAD_URL = "download_url";
    private static final String APK_PATH = "apk_path";
    private static final String HTTP_ADDRESS = "HTTP_ADDRESS";

    private CompositeDisposable cd = new CompositeDisposable();
    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;
    private DownloadManager downloadManager;

    public DownloadIntentService() {
        super("DownloadIntentService");
    }

    public static void startDownloadService(Context context, String httpAddress, String url, String path) {
        Intent intent = new Intent(context, DownloadIntentService.class);
        intent.setAction(ACTION_DOWNLOAD);
        intent.putExtra(DOWNLOAD_URL, url);
        intent.putExtra(APK_PATH, path);
        intent.putExtra(HTTP_ADDRESS, httpAddress);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            if (ACTION_DOWNLOAD.equals(action)) {
                notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                builder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("版本更新")
                        .setAutoCancel(true);

                notificationManager.notify(0, builder.build());

                String url = intent.getStringExtra(DOWNLOAD_URL);
                String apkPath = intent.getStringExtra(APK_PATH);
                downloadManager = new DownloadManager(intent.getStringExtra(HTTP_ADDRESS));
                handleUpdate(url, apkPath);
            }
        }
    }

    private void handleUpdate(String url, String apkPath) {
        subscribeEvent();
        downloadManager.downloadApk(url, apkPath, cd, (url1, path) -> {
            AppUtils.installApp(this, apkPath);
        });
    }

    private void subscribeEvent() {
        RxBus.getInstance().toFlowable(DownloadBean.class).subscribe(downloadBean -> {
            int progress = (int) Math.round(downloadBean.getBytesReaded() / (double) downloadBean.getTotal() * 100);
            builder.setContentText(String.valueOf(progress) + "%").setProgress(100, progress, false);
            notificationManager.notify(0, builder.build());
            if (progress == 100)
                notificationManager.cancel(0);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
