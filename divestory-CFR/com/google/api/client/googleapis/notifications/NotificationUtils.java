/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.notifications;

import java.util.UUID;

public final class NotificationUtils {
    private NotificationUtils() {
    }

    public static String randomUuidString() {
        return UUID.randomUUID().toString();
    }
}

