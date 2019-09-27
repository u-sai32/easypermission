package com.gamerMafia.easypermission;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class PermissionActivity extends AppCompatActivity {
    private SinglePermissionTon singlePermissionTon;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private String[] allPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Get instance of SinglePermissionTon class
        singlePermissionTon = SinglePermissionTon.getInstance();

        //Get Permission Information
        Intent intent = getIntent();
        Permission permission = (Permission) intent.getSerializableExtra("permission");
        allPermission = permission.getPermissions();

        // check permission in grant or not.
        if (checkAndRequestPermissions()) {
            callbackPermissionSuccess();
        }
    }
    // check permission function.
    public boolean checkAndRequestPermissions() {
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String perm : allPermission) {
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(perm);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {

            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), PERMISSION_REQUEST_CODE);
            return false;
        }
        return true;
    }


    // permission result handler.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PERMISSION_REQUEST_CODE) {
            HashMap<String, Integer> permissionResults = new HashMap<>();
            int deniedCount = 0;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    permissionResults.put(permissions[i], grantResults[i]);
                    deniedCount++;
                }
            }

            if (deniedCount == 0) {
                callbackPermissionSuccess();
            } else {
                for (Map.Entry<String, Integer> entry : permissionResults.entrySet()) {
                    String permName = entry.getKey();
                    int permResult = entry.getValue();

                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, permName)) {
                        showDialog("", "You need to allow access to the permissions.",
                                "OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialoglnterface, int i) {
                                        dialoglnterface.dismiss();
                                        checkAndRequestPermissions();
                                        //finish();
                                    }
                                }, "Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialoglnterface, int i) {
                                        dialoglnterface.dismiss();
                                        callbackPermissionCancelled();
                                        finish();
                                    }
                                }, false);

                    } else {
                        showDialog("", "You have denied some permissions. Allow all permissions at [Setting] > [Permissions]",
                                "Go to Settings",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface DialogInterface, int i) {
                                        DialogInterface.dismiss(); // Go to app settings
                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", getPackageName(), null));
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                },
                                "Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialoglnterface, int i) {
                                        dialoglnterface.dismiss();
                                        callbackPermissionCancelled();
                                        finish();
                                    }
                                }, false);
                        break;
                    }
                }
            }
        }
    }

    // show dialog
    public void showDialog(String title, String msg, String positiveLabel, DialogInterface.OnClickListener positiveOnClick, String negativeLabel, DialogInterface.OnClickListener negativeOnClick, boolean isCancelAble) {
        new AlertDialog.Builder(PermissionActivity.this, R.style.MyAlertDialogStyle)
                .setTitle(title)
                .setCancelable(isCancelAble)
                .setMessage(msg)
                .setPositiveButton(positiveLabel, positiveOnClick)
                .setNegativeButton(negativeLabel, negativeOnClick)
                .show();
    }

    private boolean isListenerRegistered() {
        return (SinglePermissionTon.getInstance().isListenerRegistered());
    }

    private void callbackPermissionSuccess() {
        if (isListenerRegistered()) {
            singlePermissionTon.getListener().onPermissionSuccess();
        }
        finish();
    }
    private void callbackPermissionCancelled() {
        if (isListenerRegistered()) {
            singlePermissionTon.getListener().onPermissionCancel();
        }
        finish();
    }


}
