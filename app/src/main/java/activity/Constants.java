package activity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Constants {
    private static final int MY_PERMISSIONS_REQUEST_ACCOUNTS = 1;
    public static boolean checkAndRequestPermissions(Activity _mCon) {
        List<String> listPermissionsNeeded = new ArrayList<>();
        int accountPermission = ContextCompat.checkSelfPermission(_mCon,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (accountPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        accountPermission = ContextCompat.checkSelfPermission(_mCon,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        if (accountPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        accountPermission = ContextCompat.checkSelfPermission(_mCon,
                Manifest.permission.INTERNET);
        if (accountPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.INTERNET);
        }
        accountPermission = ContextCompat.checkSelfPermission(_mCon,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if (accountPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        accountPermission = ContextCompat.checkSelfPermission(_mCon,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (accountPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        accountPermission = ContextCompat.checkSelfPermission(_mCon,
                Manifest.permission.CAMERA);
        if (accountPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(_mCon, listPermissionsNeeded.toArray(new String[0]), MY_PERMISSIONS_REQUEST_ACCOUNTS);
            return false;
        }
        return true;
    }
}
