package com.gamerMafia.easypermission;

import androidx.annotation.NonNull;

import com.gamerMafia.easypermission.listener.PermissionStatusListener;

public final class SinglePermissionTon {
    private static SinglePermissionTon instance = null;

    private PermissionStatusListener listener;

    public static SinglePermissionTon getInstance() {
        if(instance == null) {
            instance = new SinglePermissionTon();
        }
        return instance;
    }

    @NonNull
    public PermissionStatusListener getListener() {
        return instance.listener;
    }

    void setListener(@NonNull PermissionStatusListener listener) {
        instance.listener = listener;
    }

    public void detachListener() {
        instance.listener = null;
    }

    public boolean isListenerRegistered() {
        return (instance.listener != null);
    }
}
