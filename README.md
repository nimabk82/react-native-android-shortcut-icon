# react-native-android-shortcut-icon

## Install
     npm i --save nimamyscreen/react-native-android-shortcut-icon
     react-native link
     
   add this line to app/build.gradle
   ```compile project(':react-native-android-shortcut-icon')```
   and also add this to MainApplication
   ```
   import com.nima.rnShortcutIcon.ShortcutIconPackage;
    @Override
    protected List<ReactPackage> getPackages() {
      return Arrays.<ReactPackage>asList(
          new MainReactPackage(),
          new ShortcutIconPackage()  <--- This Line
      );
    }
   ````
   add following changes to manifest
```       
       <manifest xmlns:android="http://schemas.android.com/apk/res/android"
            package="com.reacttestapp"
            android:versionCode="1"
            android:versionName="1.0">

            <uses-permission android:name="android.permission.INTERNET" />
            <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"/>
            <uses-permission android:name="com.android.launcher.permission.INSTALL_SHORTCUT" /> <--- ADD This
            
            <uses-sdk
                android:minSdkVersion="16"
                android:targetSdkVersion="22" />

            <application
              android:name=".MainApplication"
              android:allowBackup="true"
              android:label="@string/app_name"
              android:icon="@mipmap/ic_launcher"
              android:theme="@style/AppTheme">
              <activity
                android:name=".MainActivity"
                android:label="@string/app_name"
                android:configChanges="keyboard|keyboardHidden|orientation|screenSize"
                android:windowSoftInputMode="adjustResize">
                
                <intent-filter>                                                <--- ADD The whole intent-filter
                    <action android:name="android.intent.action.MAIN" />
                    <category android:name="android.intent.category.LAUNCHER" />
                    <action android:name="android.intent.action.CREATE_SHORTCUT" />
                    <category android:name="android.intent.category.DEFAULT" />
                </intent-filter>
              </activity>
              <activity android:name="com.facebook.react.devsupport.DevSettingsActivity" />
            </application>
        </manifest>
  ```

