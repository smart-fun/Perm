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

import android.content.pm.PackageManager;

import static fr.arnaudguyon.perm.PermResult.Result.CANCELLED;
import static fr.arnaudguyon.perm.PermResult.Result.DENIED;
import static fr.arnaudguyon.perm.PermResult.Result.GRANTED;

/**
 * Helper to analyse teh result of a Permission request
 */

public class PermResult {

    enum Result {
        CANCELLED,
        GRANTED,
        DENIED
    }

    private Result mResult;

    private PermResult() {

    }

    /**
     * Constructor, to be called from the onRequestPermissionsResult method of the Activity
     * @param permissions list of permissions
     * @param grantResults list of results
     */
    public PermResult(String[] permissions, int[] grantResults) {
        if ((permissions == null) || (permissions.length == 0) || (grantResults == null) || (grantResults.length == 0)) {
            mResult = CANCELLED;
        } else {
            boolean granted = (grantResults[0] == PackageManager.PERMISSION_GRANTED);
            mResult = granted ? GRANTED : DENIED;
        }
    }

    /**
     *
     * @return true if the permission has just been granted
     */
    public boolean isGranted() {
        return (mResult == GRANTED);
    }

    /**
     *
     * @return true if the permission has just been denied
     */
    public boolean isDenied() {
        return (mResult == DENIED);
    }

    /**
     *
     * @return true if the permission process has been cancelled
     */
    public boolean isCancelled() {
        return (mResult == CANCELLED);
    }


}
