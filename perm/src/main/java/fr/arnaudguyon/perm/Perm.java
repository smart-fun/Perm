/*
    Copyright 2016 Arnaud Guyon

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
 */
package fr.arnaudguyon.perm;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Helper to Check or Request Android Permissions
 */

public class Perm {

    private Activity mActivity;
    private String mName;

    /**
     * Exception eventually thrown
     */
    public class PermException extends RuntimeException {
        PermException(String message) {
            super(message);
        }
    }

    private Perm() {

    }

    /**
     * Constructor
     * @param activity an Activity
     * @param androidPermission Android Permission like Manifest.permission.CAMERA or Manifest.permission.ACCESS_FINE_LOCATION
     */
    public Perm(@NonNull Activity activity, @NonNull String androidPermission) {
        if (!androidPermission.startsWith("android.permission.")) {
            throw new PermException("Perm " + androidPermission + " is not an Android permission");
        }
        mActivity = activity;
        mName = androidPermission;
    }

    /**
     *
     * @return true if the Permission is already Granted
     */
    public boolean isGranted() {
        return (ContextCompat.checkSelfPermission(mActivity, mName) == PackageManager.PERMISSION_GRANTED);
    }

    /**
     *
     * @return true if the Permission is already Denied
     */
    public boolean isDenied() {
        return ((ContextCompat.checkSelfPermission(mActivity, mName) == PackageManager.PERMISSION_DENIED)
                && ActivityCompat.shouldShowRequestPermissionRationale(mActivity, mName));
    }

    /**
     * Requests an Android permission. The response will be send to the onRequestPermissionsResult method of the mActivity
     * @param requestCode user code that could be useful to differentiate several requests
     */
    public void askPermission(int requestCode) {
        ActivityCompat.requestPermissions(mActivity, new String[]{mName}, requestCode);
    }

}
