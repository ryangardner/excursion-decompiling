/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.notifications;

import com.google.api.client.googleapis.notifications.StoredChannel;
import com.google.api.client.googleapis.notifications.TypedNotification;
import com.google.api.client.googleapis.notifications.UnparsedNotification;
import com.google.api.client.googleapis.notifications.UnparsedNotificationCallback;
import com.google.api.client.http.HttpMediaType;
import com.google.api.client.util.ObjectParser;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public abstract class TypedNotificationCallback<T>
implements UnparsedNotificationCallback {
    private static final long serialVersionUID = 1L;

    protected abstract Class<T> getDataClass() throws IOException;

    protected abstract ObjectParser getObjectParser() throws IOException;

    protected abstract void onNotification(StoredChannel var1, TypedNotification<T> var2) throws IOException;

    @Override
    public final void onNotification(StoredChannel storedChannel, UnparsedNotification unparsedNotification) throws IOException {
        TypedNotification<T> typedNotification = new TypedNotification<T>(unparsedNotification);
        Object object = unparsedNotification.getContentType();
        if (object != null) {
            object = new HttpMediaType((String)object).getCharsetParameter();
            Class<T> class_ = Preconditions.checkNotNull(this.getDataClass());
            typedNotification.setContent(this.getObjectParser().parseAndClose(unparsedNotification.getContentStream(), (Charset)object, class_));
        }
        this.onNotification(storedChannel, typedNotification);
    }
}

