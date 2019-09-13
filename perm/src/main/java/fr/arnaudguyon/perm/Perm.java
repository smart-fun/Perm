/*
    Copyright 2016-2018 Arnaud Guyon

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
import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

/**
 * Helper to Check or Request Android Permissions
 */

public class Perm {

    private Activity mActivity;
    private Fragment mFragment;
    private String[] mNames;

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
     *
     * @param activity          an Activity
     * @param androidPermission Android Permission like Manifest.permission.CAMERA or Manifest.permission.ACCESS_FINE_LOCATION
     */
    public Perm(@NonNull Activity activity, @NonNull String... androidPermission) {
        this(androidPermission);
        mActivity = activity;
    }

    /**
     * Constructor
     *
     * @param fragment          an androidx Fragment
     * @param androidPermission Android Permission like Manifest.permission.CAMERA or Manifest.permission.ACCESS_FINE_LOCATION
     */
    public Perm(@NonNull Fragment fragment, @NonNull String... androidPermission) {
        this(androidPermission);
        mFragment = fragment;
    }

    private Perm(@NonNull String... androidPermission) {
        if (androidPermission.length == 0) {
            throw new PermException("Provide at least 1 Android permission");
        } else {
            for (String permissionName : androidPermission) {
                checkPermissionName(permissionName);
            }
        }
        mNames = androidPermission;
    }

    /**
     * @return true if the all the Permissions are already Granted
     */
    public boolean areGranted() {
        Context context = (mActivity != null) ? mActivity : mFragment.getContext();
        for (String permission : mNames) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return true if the Permission permissionName is already Granted
     */
    public boolean isGranted(@NonNull String permissionName) {
        Context context = (mActivity != null) ? mActivity : mFragment.getContext();
        checkPermissionName(permissionName);
        return (ContextCompat.checkSelfPermission(context, permissionName) == PackageManager.PERMISSION_GRANTED);
    }

    /**
     * @return true if all the Permissions are already Denied
     */
    public boolean areDenied() {
        for (String permissionName : mNames) {
            if (!isDenied(permissionName)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return true if the Permission is already Denied
     */
    public boolean isDenied(@NonNull String permissionName) {
        checkPermissionName(permissionName);
        if (mActivity != null) {
            return ((ContextCompat.checkSelfPermission(mActivity, permissionName) == PackageManager.PERMISSION_DENIED)
                    && ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permissionName));
        } else {
            return ((ContextCompat.checkSelfPermission(mFragment.getContext(), permissionName) == PackageManager.PERMISSION_DENIED)
                    && mFragment.shouldShowRequestPermissionRationale(permissionName));
        }
    }

    /**
     * Requests an Android permissions. The response will be send to the onRequestPermissionsResult method of the mActivity
     *
     * @param requestCode user code that could be useful to differentiate several requests
     */
    public void askPermissions(int requestCode) {
        if (mActivity != null) {
            ActivityCompat.requestPermissions(mActivity, mNames, requestCode);
        } else {
            mFragment.requestPermissions(mNames, requestCode);
        }
    }

    private void checkPermissionName(String permissionName) {
        if (TextUtils.isEmpty(permissionName) || !permissionName.startsWith("android.permission.")) {
            throw new PermException("Perm " + permissionName + " is not an Android permission");
        }
    }

}
