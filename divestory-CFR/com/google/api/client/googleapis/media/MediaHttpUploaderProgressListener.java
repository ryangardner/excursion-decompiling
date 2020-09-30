/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.media;

import com.google.api.client.googleapis.media.MediaHttpUploader;
import java.io.IOException;

public interface MediaHttpUploaderProgressListener {
    public void progressChanged(MediaHttpUploader var1) throws IOException;
}

