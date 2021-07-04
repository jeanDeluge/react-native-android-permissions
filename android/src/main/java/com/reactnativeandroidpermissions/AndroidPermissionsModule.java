package com.reactnativeandroidpermissions;

import androidx.annotation.NonNull;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.os.Process;
import android.content.Context;
import android.content.pm.PackageManager;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.BaseActivityEventListener;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.permissions.PermissionsModule;

@ReactModule(name = AndroidPermissionsModule.NAME)
public class AndroidPermissionsModule extends ReactContextBaseJavaModule {
    public static final String NAME = "AndroidPermissions";
    
    private final ReactApplicationContext reactContext;
    private final int DRAW_OVER_PERMISSION_REQUEST_CODE = 123;
    private Promise mPromise;
    private final String error  = "Permission was not granted";


    private final ActivityEventListener mActivityEventListener = new BaseActivityEventListener(){
        @Override
        public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data){
            super.onActivityResult(activity, requestCode, resultCode, data);
            if(requestCode == DRAW_OVER_PERMISSION_REQUEST_CODE){
                if (Settings.canDrawOverlays(activity.getApplicationContext())) {
                    mPromise.resolve(true);
                }
                else {
                    mPromise.reject(new Throwable(error));
                }
            }else{
                mPromise.resolve(true);
            }
        }
    };
<<<<<<< HEAD
  
=======
   
>>>>>>> ab9e961 (fix: import android.content.pm.PackageManager)
    public AndroidPermissionsModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;

        this.reactContext.addActivityEventListener(mActivityEventListener);
    }

    private boolean permissionExist(final String permisson){

    }

    @Override
    @NonNull
    public String getName() {
        return NAME;
    }

    @ReactMethod
    public void checkPermission(final String permission, final Promise promise) {
        Context context = getReactApplicationContext().getBaseContext();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
          promise.resolve(
              context.checkPermission(permission, Process.myPid(), Process.myUid())
                  == PackageManager.PERMISSION_GRANTED);
          return;
        }
        promise.resolve(context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
    }
<<<<<<< HEAD
<<<<<<< HEAD
  
=======
>>>>>>> 98c204a (fix: requestPermission)
    @ReactMethod
    public void requestPermission(final String permission, final Promise promise) {
        Context context = getReactApplicationContext().getBaseContext();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
          promise.resolve(
              context.checkPermission(permission, Process.myPid(), Process.myUid())
                      == PackageManager.PERMISSION_GRANTED
                  ? GRANTED
                  : DENIED);
          return;
        }
        if (context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
          promise.resolve(GRANTED);
          return;
        }
    
        try {
          PermissionAwareActivity activity = getPermissionAwareActivity();
    
          mCallbacks.put(
              mRequestCode,
              new Callback() {
                @Override
                public void invoke(Object... args) {
                  int[] results = (int[]) args[0];
                  if (results.length > 0 && results[0] == PackageManager.PERMISSION_GRANTED) {
                    promise.resolve(GRANTED);
                  } else {
                    PermissionAwareActivity activity = (PermissionAwareActivity) args[1];
                    if (activity.shouldShowRequestPermissionRationale(permission)) {
                      promise.resolve(DENIED);
                    } else {
                      promise.resolve(NEVER_ASK_AGAIN);
                    }
                  }
                }
              });
    
          activity.requestPermissions(new String[] {permission}, mRequestCode, this);
          mRequestCode++;
        } catch (IllegalStateException e) {
          promise.reject(ERROR_INVALID_ACTIVITY, e);
        }
      }
<<<<<<< HEAD
=======
>>>>>>> ab9e961 (fix: import android.content.pm.PackageManager)
=======
>>>>>>> 98c204a (fix: requestPermission)

    @ReactMethod
    public void overlaypermission(Promise promise){
        mPromise = promise;
        if (!Settings.canDrawOverlays(this.reactContext)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + this.reactContext.getPackageName()));
            this.reactContext.startActivityForResult(intent, DRAW_OVER_PERMISSION_REQUEST_CODE, null);
        } else {
            promise.resolve(true);
        }
    }




}
