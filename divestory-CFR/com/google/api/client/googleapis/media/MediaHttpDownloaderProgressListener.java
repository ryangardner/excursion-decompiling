/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.media;

import com.google.api.client.googleapis.media.MediaHttpDownloader;
import java.io.IOException;

public interface MediaHttpDownloaderProgressListener {
    public void progressChanged(MediaHttpDownloader var1) throws IOException;
}

