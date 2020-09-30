/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.notifications;

import com.google.api.client.googleapis.notifications.StoredChannel;
import com.google.api.client.googleapis.notifications.UnparsedNotification;
import java.io.IOException;
import java.io.Serializable;

public interface UnparsedNotificationCallback
extends Serializable {
    public void onNotification(StoredChannel var1, UnparsedNotification var2) throws IOException;
}

