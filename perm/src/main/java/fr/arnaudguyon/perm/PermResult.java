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

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import java.util.HashMap;

import static fr.arnaudguyon.perm.PermResult.ResultValue.CANCELLED;
import static fr.arnaudguyon.perm.PermResult.ResultValue.DENIED;
import static fr.arnaudguyon.perm.PermResult.ResultValue.GRANTED;

/**
 * Helper to analyse teh result of a Permission request
 */

public class PermResult {

    public enum ResultValue {
        CANCELLED,
        GRANTED,
        DENIED
    }

    private class Result {
        private String mPermissionName;
        private int mRawResult;
        private ResultValue mResultValue;
        Result(String permissionName, int rawResult) {
            mPermissionName = permissionName;
            mRawResult = rawResult;
        }
        private boolean isGranted() {
            return (mResultValue == GRANTED);
        }
        private boolean isDenied() {
            return (mResultValue == DENIED);
        }
        private void setResultValue(@NonNull ResultValue resultValue) {
            mResultValue = resultValue;
        }
    }

    private HashMap<String, Result> mResults;

    private PermResult() {

    }

    /**
     * Constructor, to be called from the onRequestPermissionsResult method of the Activity
     * @param permissions list of permissions
     * @param grantResults list of results
     */
    public PermResult(String[] permissions, int[] grantResults) {
        if ((permissions != null) && (grantResults != null) && (permissions.length > 0) && (grantResults.length == permissions.length)) {
            mResults = new HashMap<>(permissions.length);
            for(int i=0; i<permissions.length; ++i) {
                String permissionName = permissions[i];
                int rawResult = grantResults[i];
                Result result = new Result(permissionName, rawResult);
                if (rawResult == PackageManager.PERMISSION_GRANTED) {
                    result.setResultValue(GRANTED);
                } else {
                    result.setResultValue(DENIED);
                }
            }
        }
    }

    /**
     *
     * @return true if the permissions have just been granted
     */
    public boolean areGranted() {
        if (mResults == null) {
            return false;
        }
        for(Result result : mResults.values()) {
            if (!result.isGranted()) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @return true if the permission has just been granted
     */
    public boolean isGranted(@NonNull String permissionName) {
        Result result = mResults.get(permissionName);
        return ((result != null) && result.isGranted());
    }

    /**
     *
     * @return true if the permissions have just been denied
     */
    public boolean areDenied() {
        if (mResults == null) {
            return false;
        }
        for(Result result : mResults.values()) {
            if (!result.isDenied()) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @return true if the permission has just been granted
     */
    public boolean isDenied(@NonNull String permissionName) {
        Result result = mResults.get(permissionName);
        return ((result != null) && result.isDenied());
    }

    /**
     *
     * @return true if the permission process has been cancelled
     */
    public boolean areCancelled() {
        return (mResults == null);
    }


}
