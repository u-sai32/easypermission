package com.gamerMafia.easypermission;

import java.io.Serializable;

public class Permission implements Serializable {
    String mPermission[];


    public String[] getPermissions() {
        return mPermission;
    }

    public void setPermissions(String mPermission[]) {
        this.mPermission = mPermission;
    }

}
