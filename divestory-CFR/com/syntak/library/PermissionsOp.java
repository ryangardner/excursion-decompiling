/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.pm.PackageManager
 */
package com.syntak.library;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class PermissionsOp {
    public static final int ASK_ALL_PERMISSIONS = 9999;
    Activity activity;
    public HashMap<String, Boolean> permissions;
    private int permissions_to_request = 0;

    public PermissionsOp(Activity activity) {
        this.activity = activity;
        this.permissions = new HashMap();
    }

    public PermissionsOp(Activity object, List<String> object2) {
        this.activity = object;
        this.permissions = new HashMap();
        object = object2.iterator();
        while (object.hasNext()) {
            object2 = (String)object.next();
            this.permissions.put((String)object2, false);
        }
    }

    public static boolean check_permission(Activity activity, String string2) {
        if (ContextCompat.checkSelfPermission((Context)activity, string2) == -1) return false;
        return true;
    }

    private void check_permissions(Activity activity) {
        this.permissions_to_request = 0;
        Iterator<String> iterator2 = this.permissions.keySet().iterator();
        while (iterator2.hasNext()) {
            String string2 = iterator2.next();
            if (ContextCompat.checkSelfPermission((Context)activity, string2) == -1) {
                this.permissions.put(string2, false);
                ++this.permissions_to_request;
                continue;
            }
            this.permissions.put(string2, true);
        }
    }

    public static boolean isCameraAvailable(Context context) {
        return context.getPackageManager().hasSystemFeature("android.hardware.camera");
    }

    public static boolean isTelephonyAvailable(Context context) {
        return context.getPackageManager().hasSystemFeature("android.hardware.telephony");
    }

    public static void permissions_init() {
    }

    public static void request_permission(Activity activity, String string2) {
        ActivityCompat.requestPermissions(activity, new String[]{string2}, 9999);
    }

    private void request_permissions(Activity activity) {
        String[] arrstring = new String[this.permissions_to_request];
        Iterator<String> iterator2 = this.permissions.keySet().iterator();
        int n = 0;
        do {
            if (!iterator2.hasNext()) {
                ActivityCompat.requestPermissions(activity, arrstring, 9999);
                return;
            }
            String string2 = iterator2.next();
            if (this.permissions.get(string2).booleanValue()) continue;
            arrstring[n] = string2;
            ++n;
        } while (true);
    }

    public boolean get_permission(String string2) {
        return this.permissions.get(string2);
    }

    public void put_permission(String string2, boolean bl) {
        this.permissions.put(string2, bl);
    }

    public boolean start() {
        this.check_permissions(this.activity);
        if (this.permissions_to_request <= 0) return true;
        this.request_permissions(this.activity);
        return false;
    }
}

