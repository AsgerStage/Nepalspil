package com.example.asger.nepalspil.diverse;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.asger.nepalspil.BuildConfig;
import com.example.asger.nepalspil.R;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/**
 * Hjælpeklasse, der tjekker om der er kommet en ny version af APK'en.
 * Bruges til udvikling - kald ignoreres i produktion
 * Created by j on 07-12-15.
 */
public class AppOpdatering {

  public static final String APK_URL = "http://javabog.dk/privat/nepalspil.apk";
  public static Date nyApkErTilgængelig;

  public static Long findTidsstempelForSenesteAPK() throws Exception {
    /*
    final PackageManager pm = getPackageManager();
    String apkName = "example.apk";
    String fullPath = Environment.getExternalStorageDirectory() + "/" + apkName;
    PackageInfo info = pm.getPackageArchiveInfo(fullPath, 0);
    Toast.makeText(this, "VersionCode : " + info.versionCode + ", VersionName : " + info.versionName , Toast.LENGTH_LONG).show();
    */
    URL url = new URL(APK_URL);
    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
    if (urlConnection.getResponseCode()!= HttpURLConnection.HTTP_OK) return null;
    if (!url.getHost().equals(urlConnection.getURL().getHost())) return null; // ingen omdirigeringer
    long lm = urlConnection.getLastModified();
    return lm;
  }

  public static void tjekForNyAPK(final Context ctx) {
    if (!BuildConfig.DEBUG) return; // kun når APKen ikke er signeret med en publiceringsnøgle
    final SharedPreferences prefs = ctx.getSharedPreferences("AppOpdatering",0);
    new AsyncTask<Long,Long,Long>() {
      @Override
      protected Long doInBackground(Long... params) {
        try {
          return AppOpdatering.findTidsstempelForSenesteAPK();
        } catch (Exception e) {
          Log.d("AppOpdatering", "kunne ikke tjekke for ny version:"+e);
        }
        return null;
      };

      @Override
      protected void onPostExecute(Long tidsstempel) {
        if (tidsstempel==null) return;
        String NØGLE = "tidsstempelForSenesteAPK";
        long glTidsstempel = prefs.getLong(NØGLE, 0);
        if (tidsstempel>glTidsstempel && glTidsstempel>0) try {
          // Tjek også at der ikke allerede er installeret en ny udgave på anden vis (f.eks. USB-kabel)
          final PackageManager pm = ctx.getPackageManager();
          PackageInfo info = pm.getPackageInfo(ctx.getPackageName(), 0);
          if (info.lastUpdateTime>=tidsstempel) {
            Toast.makeText(ctx, "Ny version kommet, men denne app er nyere.", Toast.LENGTH_LONG).show();
          } else {

            android.support.v4.app.NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ctx)
                    .setSmallIcon(R.drawable.appikon)
                    .setContentTitle("Ny version")
                    .setContentText("Der er kommet en ny version af "+ctx.getString(R.string.app_name)+"\nTryk her for at hente den.");
            Intent notifyIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(AppOpdatering.APK_URL));

            PendingIntent notifyPendingIntent = PendingIntent.getActivity( ctx, 0,notifyIntent,PendingIntent.FLAG_UPDATE_CURRENT);

            mBuilder.setContentIntent(notifyPendingIntent);
            NotificationManager mNotificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(0, mBuilder.build());

            Toast.makeText(ctx, "Der er kommet en ny version af app'en.\nVælg notifikationen 'Ny version' øverst for at hente den.", Toast.LENGTH_LONG).show();
            nyApkErTilgængelig = new Date(tidsstempel);
          }
        } catch (Exception e) { e.printStackTrace(); }
        if (tidsstempel>glTidsstempel || glTidsstempel==0) {
          prefs.edit().putLong(NØGLE, tidsstempel).commit();
        }
      }
    }.execute();
  }
}
