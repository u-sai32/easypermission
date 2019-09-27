package com.gamerMafia.easypermission;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.gamerMafia.easypermission.listener.PermissionStatusListener;


public final class EasyPermission {
    private Activity mActivity;
    private Permission mPermission;

    private EasyPermission(@NonNull final Activity mActivity, @NonNull Permission mPermission) {
        this.mActivity = mActivity;
        this.mPermission = mPermission;
    }

    public void startPayment() {
        Intent payIntent  = new Intent(mActivity, PermissionActivity.class);
        payIntent.putExtra("permission", mPermission);
        mActivity.startActivity(payIntent);
    }

    public static final class Builder {
        private Activity activity;
        private Permission permission;


        @NonNull
        public Builder with(@NonNull Activity activity) {
            this.activity = activity;
            permission = new Permission();
            return this;
        }

        @NonNull
        public Builder addPermissions(@NonNull String...mPermission) {
            if (mPermission.length==0) {
                throw new IllegalStateException("please add permission");
            }
            permission.setPermissions(mPermission);
            return this;
        }

        public Builder setPermissionCheckListener(@NonNull PermissionStatusListener mListener) {
            SinglePermissionTon singlePermissionTon = SinglePermissionTon.getInstance();
            singlePermissionTon.setListener(mListener);
            return this;
        }
        public Builder startPermission() {
            Intent payIntent  = new Intent(activity, PermissionActivity.class);
            payIntent.putExtra("permission", permission);
            activity.startActivity(payIntent);
            return this;
        }

        @NonNull
        public EasyPermission build() {
            if(activity == null) {
                throw new IllegalStateException("Activity must be specified using with() call begore build()");
            }

            if (permission == null) {
                throw new IllegalStateException("Permission must be initialized before build()");
            }

            if (permission.getPermissions() == null) {
                throw new IllegalStateException("Must call getPermissions() before build().");
            }
            return new EasyPermission(activity, permission);
        }
    }
}
