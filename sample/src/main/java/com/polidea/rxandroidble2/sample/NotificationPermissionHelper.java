package com.polidea.rxandroidble2.sample;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

public class NotificationPermissionHelper {
    public static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 1;
    private Activity activity;

    public NotificationPermissionHelper(Activity activity) {
        this.activity = activity;
    }

    public boolean isNotificationPermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
            return notificationManager.areNotificationsEnabled();
        } else {
            String packageName = activity.getPackageName();
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(activity);
            return notificationManagerCompat.areNotificationsEnabled() &&
                    notificationManagerCompat.getImportance() != NotificationManagerCompat.IMPORTANCE_NONE;
        }
    }

    public void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Redirect the user to the app's notification settings
            activity.startActivity(new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                    .putExtra(Settings.EXTRA_APP_PACKAGE, activity.getPackageName()));
        } else {
            // Request the notification permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                activity.requestPermissions(new String[]{Manifest.permission.VIBRATE}, NOTIFICATION_PERMISSION_REQUEST_CODE);
            }
        }
    }

    public boolean hasNotificationPermission() {
        return PackageManager.PERMISSION_GRANTED ==
                ContextCompat.checkSelfPermission(activity, Manifest.permission.VIBRATE);
    }
}
