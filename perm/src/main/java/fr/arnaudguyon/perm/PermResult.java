package fr.arnaudguyon.perm;

import android.content.pm.PackageManager;

import static fr.arnaudguyon.perm.PermResult.Result.CANCELLED;
import static fr.arnaudguyon.perm.PermResult.Result.DENIED;
import static fr.arnaudguyon.perm.PermResult.Result.GRANTED;

/**
 * Created by aguyon on 02.12.16.
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

    public PermResult(String[] permissions, int[] grantResults) {
        if ((permissions == null) || (permissions.length == 0) || (grantResults == null) || (grantResults.length == 0)) {
            mResult = CANCELLED;
        } else {
            boolean granted = (grantResults[0] == PackageManager.PERMISSION_GRANTED);
            mResult = granted ? GRANTED : DENIED;
        }
    }

    public boolean isGranted() {
        return (mResult == GRANTED);
    }

    public boolean isDenied() {
        return (mResult == DENIED);
    }

    public boolean isCancelled() {
        return (mResult == CANCELLED);
    }


}
