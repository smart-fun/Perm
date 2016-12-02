package fr.arnaudguyon.perm;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by aguyon on 02.12.16.
 */

public class Perm {

    private Activity mActivity;
    private String mName;

    public class PermException extends RuntimeException {
        PermException(String message) {
            super(message);
        }
    }

//  examples of androidPermission Strings
//  Manifest.permission.CAMERA
//  Manifest.permission.ACCESS_FINE_LOCATION

    private Perm() {

    }

    public Perm(@NonNull Activity activity, @NonNull String androidPermission) {
        if (!androidPermission.startsWith("android.permission.")) {
            throw new PermException("Perm " + androidPermission + " is not an Android permission");
        }
        mActivity = activity;
        mName = androidPermission;
    }

    public boolean isGranted() {
        return (ContextCompat.checkSelfPermission(mActivity, mName) == PackageManager.PERMISSION_GRANTED);
    }

    public boolean isDenied() {
        return ((ContextCompat.checkSelfPermission(mActivity, mName) == PackageManager.PERMISSION_DENIED)
                && ActivityCompat.shouldShowRequestPermissionRationale(mActivity, mName));
    }

    public void askPermission(int requestCode) {
        ActivityCompat.requestPermissions(mActivity, new String[]{mName}, requestCode);
    }

}
