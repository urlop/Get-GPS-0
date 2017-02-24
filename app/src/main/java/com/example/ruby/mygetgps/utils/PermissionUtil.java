package com.example.ruby.mygetgps.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.example.ruby.mygetgps.R;

/**
 * Utility class that wraps access to the runtime permissions API in M and provides basic helper
 * methods.
 */
public class PermissionUtil {

    /**
     * Checks that all given permissions have been granted by verifying that each entry in the
     * given array is of the value {@link PackageManager#PERMISSION_GRANTED}
     *
     * @param grantResults array resulting of asking permission to user
     * @return yes if each required permission has been granted, otherwise return false.
     */
    public static boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if (grantResults.length < 1) {
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * Shows explanation of permission if necessary and return true if app needs to ask the user for permissions;
     * if not, return false and proceed to execute the corresponding method. Ex. startTripService();
     *
     * @param activity            Activity where methos was called
     * @param container           view which will contain the explanation to the user
     * @param permissionRationale permission/s
     * @return yes if permission is missing and no if not
     */
    public static boolean isAskingForPermissionNecessary(final Activity activity, View container, String permissionRationale) {
        if (Build.VERSION.SDK_INT < 23) {
            return false;
        } else if (isPermissionMissing(activity)) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissionRationale)) {

                // Show an explanation to the user
                Snackbar.make(container, activity.getString(R.string.permissions_why),
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.permissions_ok, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ActivityCompat.requestPermissions(activity, Constants.PERMISSIONS_LOCATION,
                                        Constants.MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .show();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(activity, Constants.PERMISSIONS_LOCATION,
                        Constants.MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return true;
        }
        return false;
    }


    /**
     * Checks all location permissions
     *
     * @param context Environment data where method was called
     * @return yes if all permissions are granted
     * @see android.Manifest.permission#ACCESS_FINE_LOCATION
     * @see android.Manifest.permission#ACCESS_COARSE_LOCATION
     */
    public static boolean checkLocationPermission(Context context) {
        return isPermissionGranted(context, Constants.PERMISSIONS_LOCATION);
    }

    /**
     * Checks ACCESS_FINE_LOCATION permission
     *
     * @param context Environment data where method was called
     * @return yes if permission is granted
     * @see android.Manifest.permission#ACCESS_FINE_LOCATION
     */
    public static boolean checkFineLocationPermission(Context context) {
        return isPermissionGranted(context, new String[]{Constants.PERMISSIONS_LOCATION[0]});
    }

    /**
     * Tells if at least one permission of the array was granted by the user.
     * <p/>
     * Generalized version for:
     * </br>{@code return (ActivityCompat.checkSelfPermission(context,Constants.PERMISSIONS_LOCATION[0])==PackageManager.PERMISSION_GRANTED
     * || ActivityCompat.checkSelfPermission(context,Constants.PERMISSIONS_LOCATION[1])==PackageManager.PERMISSION_GRANTED);}
     *
     * @param context     Environment data where method was called
     * @param permissions all permissions to be checked
     * @return yes if at least one permission was granted
     * @see PermissionUtil#checkLocationPermission(Context)
     */
    private static boolean isPermissionGranted(Context context, String[] permissions) {
        if (Build.VERSION.SDK_INT >= 23) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
                    return true;
                }
            }
            return false;
        } else {
            return true;
        }
    }

    /**
     * Tells if one permission of the array needs to be granted.
     * <p/>
     * Generalized version for:
     * </br>{@code return (ActivityCompat.checkSelfPermission(context,Constants.PERMISSIONS_LOCATION[0])!=PackageManager.PERMISSION_GRANTED
     * || ActivityCompat.checkSelfPermission(context,Constants.PERMISSIONS_LOCATION[1])!=PackageManager.PERMISSION_GRANTED);}
     *
     * @param context     Environment data where method was called
     * @return yes if at least one permission was denied
     * @see PermissionUtil#isAskingForPermissionNecessary(Activity, View, String)
     */
    private static boolean isPermissionMissing(Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            for (String permission : Constants.PERMISSIONS_LOCATION) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return true;
                }
            }
        }
        return false;
    }
}
