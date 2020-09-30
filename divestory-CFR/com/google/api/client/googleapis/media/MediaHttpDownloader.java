/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.media;

import com.google.api.client.googleapis.media.MediaHttpDownloaderProgressListener;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.util.Preconditions;
import com.google.common.base.MoreObjects;
import com.google.common.io.ByteStreams;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public final class MediaHttpDownloader {
    public static final int MAXIMUM_CHUNK_SIZE = 33554432;
    private long bytesDownloaded;
    private int chunkSize = 33554432;
    private boolean directDownloadEnabled = false;
    private DownloadState downloadState = DownloadState.NOT_STARTED;
    private long lastBytePos = -1L;
    private long mediaContentLength;
    private MediaHttpDownloaderProgressListener progressListener;
    private final HttpRequestFactory requestFactory;
    private final HttpTransport transport;

    public MediaHttpDownloader(HttpTransport object, HttpRequestInitializer httpRequestInitializer) {
        this.transport = Preconditions.checkNotNull(object);
        object = httpRequestInitializer == null ? ((HttpTransport)object).createRequestFactory() : ((HttpTransport)object).createRequestFactory(httpRequestInitializer);
        this.requestFactory = object;
    }

    private HttpResponse executeCurrentRequest(long l, GenericUrl object, HttpHeaders object2, OutputStream outputStream2) throws IOException {
        object = this.requestFactory.buildGetRequest((GenericUrl)object);
        if (object2 != null) {
            ((HttpRequest)object).getHeaders().putAll((Map<? extends String, ?>)object2);
        }
        if (this.bytesDownloaded != 0L || l != -1L) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("bytes=");
            ((StringBuilder)object2).append(this.bytesDownloaded);
            ((StringBuilder)object2).append("-");
            if (l != -1L) {
                ((StringBuilder)object2).append(l);
            }
            ((HttpRequest)object).getHeaders().setRange(((StringBuilder)object2).toString());
        }
        object = ((HttpRequest)object).execute();
        try {
            ByteStreams.copy(((HttpResponse)object).getContent(), outputStream2);
            return object;
        }
        finally {
            ((HttpResponse)object).disconnect();
        }
    }

    private long getNextByteIndex(String string2) {
        if (string2 != null) return Long.parseLong(string2.substring(string2.indexOf(45) + 1, string2.indexOf(47))) + 1L;
        return 0L;
    }

    private void setMediaContentLength(String string2) {
        if (string2 == null) {
            return;
        }
        if (this.mediaContentLength != 0L) return;
        this.mediaContentLength = Long.parseLong(string2.substring(string2.indexOf(47) + 1));
    }

    private void updateStateAndNotifyListener(DownloadState object) throws IOException {
        this.downloadState = object;
        object = this.progressListener;
        if (object == null) return;
        object.progressChanged(this);
    }

    public void download(GenericUrl genericUrl, HttpHeaders httpHeaders, OutputStream outputStream2) throws IOException {
        boolean bl = this.downloadState == DownloadState.NOT_STARTED;
        Preconditions.checkArgument(bl);
        genericUrl.put("alt", (Object)"media");
        if (this.directDownloadEnabled) {
            long l;
            this.updateStateAndNotifyListener(DownloadState.MEDIA_IN_PROGRESS);
            this.mediaContentLength = l = MoreObjects.firstNonNull(this.executeCurrentRequest(this.lastBytePos, genericUrl, httpHeaders, outputStream2).getHeaders().getContentLength(), this.mediaContentLength).longValue();
            this.bytesDownloaded = l;
            this.updateStateAndNotifyListener(DownloadState.MEDIA_COMPLETE);
            return;
        }
        do {
            long l = this.bytesDownloaded + (long)this.chunkSize - 1L;
            long l2 = this.lastBytePos;
            long l3 = l;
            if (l2 != -1L) {
                l3 = Math.min(l2, l);
            }
            String string2 = this.executeCurrentRequest(l3, genericUrl, httpHeaders, outputStream2).getHeaders().getContentRange();
            l3 = this.getNextByteIndex(string2);
            this.setMediaContentLength(string2);
            l = this.lastBytePos;
            if (l != -1L && l <= l3) {
                this.bytesDownloaded = l;
                this.updateStateAndNotifyListener(DownloadState.MEDIA_COMPLETE);
                return;
            }
            l = this.mediaContentLength;
            if (l <= l3) {
                this.bytesDownloaded = l;
                this.updateStateAndNotifyListener(DownloadState.MEDIA_COMPLETE);
                return;
            }
            this.bytesDownloaded = l3;
            this.updateStateAndNotifyListener(DownloadState.MEDIA_IN_PROGRESS);
        } while (true);
    }

    public void download(GenericUrl genericUrl, OutputStream outputStream2) throws IOException {
        this.download(genericUrl, null, outputStream2);
    }

    public int getChunkSize() {
        return this.chunkSize;
    }

    public DownloadState getDownloadState() {
        return this.downloadState;
    }

    public long getLastBytePosition() {
        return this.lastBytePos;
    }

    public long getNumBytesDownloaded() {
        return this.bytesDownloaded;
    }

    public double getProgress() {
        long l = this.mediaContentLength;
        if (l != 0L) return (double)this.bytesDownloaded / (double)l;
        return 0.0;
    }

    public MediaHttpDownloaderProgressListener getProgressListener() {
        return this.progressListener;
    }

    public HttpTransport getTransport() {
        return this.transport;
    }

    public boolean isDirectDownloadEnabled() {
        return this.directDownloadEnabled;
    }

    public MediaHttpDownloader setBytesDownloaded(long l) {
        boolean bl = l >= 0L;
        Preconditions.checkArgument(bl);
        this.bytesDownloaded = l;
        return this;
    }

    public MediaHttpDownloader setChunkSize(int n) {
        boolean bl = n > 0 && n <= 33554432;
        Preconditions.checkArgument(bl);
        this.chunkSize = n;
        return this;
    }

    @Deprecated
    public MediaHttpDownloader setContentRange(long l, int n) {
        return this.setContentRange(l, (long)n);
    }

    public MediaHttpDownloader setContentRange(long l, long l2) {
        boolean bl = l2 >= l;
        Preconditions.checkArgument(bl);
        this.setBytesDownloaded(l);
        this.lastBytePos = l2;
        return this;
    }

    public MediaHttpDownloader setDirectDownloadEnabled(boolean bl) {
        this.directDownloadEnabled = bl;
        return this;
    }

    public MediaHttpDownloader setProgressListener(MediaHttpDownloaderProgressListener mediaHttpDownloaderProgressListener) {
        this.progressListener = mediaHttpDownloaderProgressListener;
        return this;
    }

    public static final class DownloadState
    extends Enum<DownloadState> {
        private static final /* synthetic */ DownloadState[] $VALUES;
        public static final /* enum */ DownloadState MEDIA_COMPLETE;
        public static final /* enum */ DownloadState MEDIA_IN_PROGRESS;
        public static final /* enum */ DownloadState NOT_STARTED;

        static {
            DownloadState downloadState;
            NOT_STARTED = new DownloadState();
            MEDIA_IN_PROGRESS = new DownloadState();
            MEDIA_COMPLETE = downloadState = new DownloadState();
            $VALUES = new DownloadState[]{NOT_STARTED, MEDIA_IN_PROGRESS, downloadState};
        }

        public static DownloadState valueOf(String string2) {
            return Enum.valueOf(DownloadState.class, string2);
        }

        public static DownloadState[] values() {
            return (DownloadState[])$VALUES.clone();
        }
    }

}

