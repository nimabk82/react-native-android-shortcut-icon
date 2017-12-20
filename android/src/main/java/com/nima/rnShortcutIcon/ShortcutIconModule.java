package com.nima.rnShortcutIcon;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import android.app.Activity;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReadableMap;

import java.io.InputStream;
import java.util.Map;
import java.util.HashMap;

public class ShortcutIconModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;
  private static final String DURATION_SHORT_KEY = "SHORT";
  private static final String DURATION_LONG_KEY = "LONG";
  public int ProfileID;
  public String ProfileName;

  public ShortcutIconModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "ShortcutIconModule";
  }

  @Override
  public Map<String, Object> getConstants() {
    final Map<String, Object> constants = new HashMap<>();
    constants.put(DURATION_SHORT_KEY, Toast.LENGTH_SHORT);
    constants.put(DURATION_LONG_KEY, Toast.LENGTH_LONG);
    return constants;
  }

  @ReactMethod
  private void ShortcutIcon(String ProfileName, int ProfileID){
    this.ProfileID = ProfileID;
    this.ProfileName = ProfileName;
    String imageURL = "https://gorgiasasia.blob.core.windows.net/images/profile-" + ProfileID + ".jpg?timestamp=123";
    new DownloadImage().execute(imageURL);
    Toast.makeText(getReactApplicationContext(), ProfileName, Toast.LENGTH_SHORT).show();
  }

  private class DownloadImage extends AsyncTask<String, Void, Bitmap> {

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(String... URL) {

      String imageURL = URL[0];

      Bitmap bitmap = null;
      try {
        // Download Image from URL
        InputStream input = new java.net.URL(imageURL).openStream();
        // Decode Bitmap
        bitmap = BitmapFactory.decodeStream(input);
      } catch (Exception e) {
        e.printStackTrace();
      }
      return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
      Intent shortcutIntent = new Intent(getReactApplicationContext(), getReactApplicationContext().getClass());

      shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

      Intent addIntent = new Intent();
      addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
      addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, ProfileName);
      addIntent.putExtra("duplicate", false);
      addIntent.putExtra("ProfileID",ProfileID);
      addIntent.putExtra("ProfileName", ProfileName);
      //addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, result);
//            addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
//                    Intent.ShortcutIconResource.fromContext(getApplicationContext(), result));

      int size = (int) getReactApplicationContext().getResources().getDimension(android.R.dimen.app_icon_size);
      addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, Bitmap.createScaledBitmap(result, size, size, false));

      addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
      Log.d("run",addIntent.toString());
      getReactApplicationContext().sendBroadcast(addIntent);
      Toast.makeText(getReactApplicationContext(), "The profile is added ;)", Toast.LENGTH_SHORT).show();
    }
  }
}