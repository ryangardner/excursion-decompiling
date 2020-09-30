/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.media;

import com.google.api.client.googleapis.MethodOverride;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.client.googleapis.media.MediaUploadErrorHandler;
import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.EmptyContent;
import com.google.api.client.http.GZipEncoding;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpEncoding;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.http.MultipartContent;
import com.google.api.client.util.ByteStreams;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Sleeper;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public final class MediaHttpUploader {
    public static final String CONTENT_LENGTH_HEADER = "X-Upload-Content-Length";
    public static final String CONTENT_TYPE_HEADER = "X-Upload-Content-Type";
    public static final int DEFAULT_CHUNK_SIZE = 10485760;
    private static final int KB = 1024;
    static final int MB = 1048576;
    public static final int MINIMUM_CHUNK_SIZE = 262144;
    private Byte cachedByte;
    private int chunkSize = 10485760;
    private InputStream contentInputStream;
    private int currentChunkLength;
    private HttpRequest currentRequest;
    private byte[] currentRequestContentBuffer;
    private boolean directUploadEnabled;
    private boolean disableGZipContent;
    private HttpHeaders initiationHeaders = new HttpHeaders();
    private String initiationRequestMethod = "POST";
    private boolean isMediaContentLengthCalculated;
    private final AbstractInputStreamContent mediaContent;
    private long mediaContentLength;
    String mediaContentLengthStr = "*";
    private HttpContent metadata;
    private MediaHttpUploaderProgressListener progressListener;
    private final HttpRequestFactory requestFactory;
    Sleeper sleeper = Sleeper.DEFAULT;
    private long totalBytesClientSent;
    private long totalBytesServerReceived;
    private final HttpTransport transport;
    private UploadState uploadState = UploadState.NOT_STARTED;

    public MediaHttpUploader(AbstractInputStreamContent object, HttpTransport httpTransport, HttpRequestInitializer httpRequestInitializer) {
        this.mediaContent = Preconditions.checkNotNull(object);
        this.transport = Preconditions.checkNotNull(httpTransport);
        object = httpRequestInitializer == null ? httpTransport.createRequestFactory() : httpTransport.createRequestFactory(httpRequestInitializer);
        this.requestFactory = object;
    }

    private ContentChunk buildContentChunk() throws IOException {
        Object object;
        Object object2;
        int n = this.isMediaLengthKnown() ? (int)Math.min((long)this.chunkSize, this.getMediaContentLength() - this.totalBytesServerReceived) : this.chunkSize;
        if (this.isMediaLengthKnown()) {
            this.contentInputStream.mark(n);
            object = this.contentInputStream;
            long l = n;
            object = ByteStreams.limit((InputStream)object, l);
            object = new InputStreamContent(this.mediaContent.getType(), (InputStream)object).setRetrySupported(true).setLength(l).setCloseInputStream(false);
            this.mediaContentLengthStr = String.valueOf(this.getMediaContentLength());
        } else {
            int n2;
            int n3;
            int n4;
            object = this.currentRequestContentBuffer;
            if (object == null) {
                n3 = this.cachedByte == null ? n + 1 : n;
                object = new byte[n + 1];
                this.currentRequestContentBuffer = object;
                object2 = this.cachedByte;
                if (object2 != null) {
                    object[0] = (Byte)object2;
                }
                n2 = 0;
                n4 = n3;
                n3 = n2;
            } else {
                n3 = (int)(this.totalBytesClientSent - this.totalBytesServerReceived);
                System.arraycopy(object, this.currentChunkLength - n3, object, 0, n3);
                object = this.cachedByte;
                if (object != null) {
                    this.currentRequestContentBuffer[n3] = (Byte)object;
                }
                n4 = n - n3;
            }
            n2 = ByteStreams.read(this.contentInputStream, this.currentRequestContentBuffer, n + 1 - n4, n4);
            if (n2 < n4) {
                n = n3 += Math.max(0, n2);
                if (this.cachedByte != null) {
                    n = n3 + 1;
                    this.cachedByte = null;
                }
                if (this.mediaContentLengthStr.equals("*")) {
                    this.mediaContentLengthStr = String.valueOf(this.totalBytesServerReceived + (long)n);
                }
            } else {
                this.cachedByte = this.currentRequestContentBuffer[n];
            }
            object = new ByteArrayContent(this.mediaContent.getType(), this.currentRequestContentBuffer, 0, n);
            this.totalBytesClientSent = this.totalBytesServerReceived + (long)n;
        }
        this.currentChunkLength = n;
        if (n == 0) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("bytes */");
            ((StringBuilder)object2).append(this.mediaContentLengthStr);
            object2 = ((StringBuilder)object2).toString();
            return new ContentChunk((AbstractInputStreamContent)object, (String)object2);
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("bytes ");
        ((StringBuilder)object2).append(this.totalBytesServerReceived);
        ((StringBuilder)object2).append("-");
        ((StringBuilder)object2).append(this.totalBytesServerReceived + (long)n - 1L);
        ((StringBuilder)object2).append("/");
        ((StringBuilder)object2).append(this.mediaContentLengthStr);
        object2 = ((StringBuilder)object2).toString();
        return new ContentChunk((AbstractInputStreamContent)object, (String)object2);
    }

    private HttpResponse directUpload(GenericUrl object) throws IOException {
        this.updateStateAndNotifyListener(UploadState.MEDIA_IN_PROGRESS);
        HttpContent httpContent = this.mediaContent;
        if (this.metadata != null) {
            httpContent = new MultipartContent().setContentParts(Arrays.asList(this.metadata, this.mediaContent));
            ((GenericData)object).put("uploadType", (Object)"multipart");
        } else {
            ((GenericData)object).put("uploadType", (Object)"media");
        }
        object = this.requestFactory.buildRequest(this.initiationRequestMethod, (GenericUrl)object, httpContent);
        ((HttpRequest)object).getHeaders().putAll(this.initiationHeaders);
        object = this.executeCurrentRequest((HttpRequest)object);
        try {
            if (this.isMediaLengthKnown()) {
                this.totalBytesServerReceived = this.getMediaContentLength();
            }
            this.updateStateAndNotifyListener(UploadState.MEDIA_COMPLETE);
            return object;
        }
        catch (Throwable throwable) {
            ((HttpResponse)object).disconnect();
            throw throwable;
        }
    }

    private HttpResponse executeCurrentRequest(HttpRequest httpRequest) throws IOException {
        if (this.disableGZipContent) return this.executeCurrentRequestWithoutGZip(httpRequest);
        if (httpRequest.getContent() instanceof EmptyContent) return this.executeCurrentRequestWithoutGZip(httpRequest);
        httpRequest.setEncoding(new GZipEncoding());
        return this.executeCurrentRequestWithoutGZip(httpRequest);
    }

    private HttpResponse executeCurrentRequestWithoutGZip(HttpRequest httpRequest) throws IOException {
        new MethodOverride().intercept(httpRequest);
        httpRequest.setThrowExceptionOnExecuteError(false);
        return httpRequest.execute();
    }

    private HttpResponse executeUploadInitiation(GenericUrl object) throws IOException {
        HttpContent httpContent;
        this.updateStateAndNotifyListener(UploadState.INITIATION_STARTED);
        ((GenericData)object).put("uploadType", (Object)"resumable");
        HttpContent httpContent2 = httpContent = this.metadata;
        if (httpContent == null) {
            httpContent2 = new EmptyContent();
        }
        object = this.requestFactory.buildRequest(this.initiationRequestMethod, (GenericUrl)object, httpContent2);
        this.initiationHeaders.set(CONTENT_TYPE_HEADER, this.mediaContent.getType());
        if (this.isMediaLengthKnown()) {
            this.initiationHeaders.set(CONTENT_LENGTH_HEADER, this.getMediaContentLength());
        }
        ((HttpRequest)object).getHeaders().putAll(this.initiationHeaders);
        object = this.executeCurrentRequest((HttpRequest)object);
        try {
            this.updateStateAndNotifyListener(UploadState.INITIATION_COMPLETE);
            return object;
        }
        catch (Throwable throwable) {
            ((HttpResponse)object).disconnect();
            throw throwable;
        }
    }

    private long getMediaContentLength() throws IOException {
        if (this.isMediaContentLengthCalculated) return this.mediaContentLength;
        this.mediaContentLength = this.mediaContent.getLength();
        this.isMediaContentLengthCalculated = true;
        return this.mediaContentLength;
    }

    private long getNextByteIndex(String string2) {
        if (string2 != null) return Long.parseLong(string2.substring(string2.indexOf(45) + 1)) + 1L;
        return 0L;
    }

    private boolean isMediaLengthKnown() throws IOException {
        if (this.getMediaContentLength() < 0L) return false;
        return true;
    }

    private HttpResponse resumableUpload(GenericUrl object) throws IOException {
        Object object2;
        Object object3;
        if (!((HttpResponse)(object = this.executeUploadInitiation((GenericUrl)object))).isSuccessStatusCode()) {
            return object;
        }
        try {
            object3 = new GenericUrl(((HttpResponse)object).getHeaders().getLocation());
            object2 = this.mediaContent.getInputStream();
            this.contentInputStream = object2;
            object = object3;
            if (!((InputStream)object2).markSupported()) {
                object = object3;
                if (this.isMediaLengthKnown()) {
                    this.contentInputStream = new BufferedInputStream(this.contentInputStream);
                    object = object3;
                }
            }
        }
        finally {
            ((HttpResponse)object).disconnect();
        }
        do {
            object3 = this.buildContentChunk();
            this.currentRequest = object2 = this.requestFactory.buildPutRequest((GenericUrl)object, null);
            ((HttpRequest)object2).setContent(((ContentChunk)object3).getContent());
            this.currentRequest.getHeaders().setContentRange(((ContentChunk)object3).getContentRange());
            new MediaUploadErrorHandler(this, this.currentRequest);
            object3 = this.isMediaLengthKnown() ? this.executeCurrentRequestWithoutGZip(this.currentRequest) : this.executeCurrentRequest(this.currentRequest);
            try {
                if (((HttpResponse)object3).isSuccessStatusCode()) {
                    this.totalBytesServerReceived = this.getMediaContentLength();
                    if (this.mediaContent.getCloseInputStream()) {
                        this.contentInputStream.close();
                    }
                    this.updateStateAndNotifyListener(UploadState.MEDIA_COMPLETE);
                    return object3;
                }
                if (((HttpResponse)object3).getStatusCode() != 308) {
                    if (!this.mediaContent.getCloseInputStream()) return object3;
                    this.contentInputStream.close();
                    return object3;
                }
                object2 = ((HttpResponse)object3).getHeaders().getLocation();
                if (object2 != null) {
                    object = new GenericUrl((String)object2);
                }
                long l = this.getNextByteIndex(((HttpResponse)object3).getHeaders().getRange());
                long l2 = l - this.totalBytesServerReceived;
                boolean bl = true;
                boolean bl2 = l2 >= 0L && l2 <= (long)this.currentChunkLength;
                Preconditions.checkState(bl2);
                long l3 = (long)this.currentChunkLength - l2;
                if (this.isMediaLengthKnown()) {
                    if (l3 > 0L) {
                        this.contentInputStream.reset();
                        bl2 = l2 == this.contentInputStream.skip(l2) ? bl : false;
                        Preconditions.checkState(bl2);
                    }
                } else if (l3 == 0L) {
                    this.currentRequestContentBuffer = null;
                }
                this.totalBytesServerReceived = l;
                this.updateStateAndNotifyListener(UploadState.MEDIA_IN_PROGRESS);
                continue;
            }
            finally {
                ((HttpResponse)object3).disconnect();
                continue;
            }
            break;
        } while (true);
    }

    private void updateStateAndNotifyListener(UploadState object) throws IOException {
        this.uploadState = object;
        object = this.progressListener;
        if (object == null) return;
        object.progressChanged(this);
    }

    public int getChunkSize() {
        return this.chunkSize;
    }

    public boolean getDisableGZipContent() {
        return this.disableGZipContent;
    }

    public HttpHeaders getInitiationHeaders() {
        return this.initiationHeaders;
    }

    public String getInitiationRequestMethod() {
        return this.initiationRequestMethod;
    }

    public HttpContent getMediaContent() {
        return this.mediaContent;
    }

    public HttpContent getMetadata() {
        return this.metadata;
    }

    public long getNumBytesUploaded() {
        return this.totalBytesServerReceived;
    }

    public double getProgress() throws IOException {
        Preconditions.checkArgument(this.isMediaLengthKnown(), "Cannot call getProgress() if the specified AbstractInputStreamContent has no content length. Use  getNumBytesUploaded() to denote progress instead.");
        if (this.getMediaContentLength() != 0L) return (double)this.totalBytesServerReceived / (double)this.getMediaContentLength();
        return 0.0;
    }

    public MediaHttpUploaderProgressListener getProgressListener() {
        return this.progressListener;
    }

    public Sleeper getSleeper() {
        return this.sleeper;
    }

    public HttpTransport getTransport() {
        return this.transport;
    }

    public UploadState getUploadState() {
        return this.uploadState;
    }

    public boolean isDirectUploadEnabled() {
        return this.directUploadEnabled;
    }

    void serverErrorCallback() throws IOException {
        Preconditions.checkNotNull(this.currentRequest, "The current request should not be null");
        this.currentRequest.setContent(new EmptyContent());
        HttpHeaders httpHeaders = this.currentRequest.getHeaders();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("bytes */");
        stringBuilder.append(this.mediaContentLengthStr);
        httpHeaders.setContentRange(stringBuilder.toString());
    }

    public MediaHttpUploader setChunkSize(int n) {
        boolean bl = n > 0 && n % 262144 == 0;
        Preconditions.checkArgument(bl, "chunkSize must be a positive multiple of 262144.");
        this.chunkSize = n;
        return this;
    }

    public MediaHttpUploader setDirectUploadEnabled(boolean bl) {
        this.directUploadEnabled = bl;
        return this;
    }

    public MediaHttpUploader setDisableGZipContent(boolean bl) {
        this.disableGZipContent = bl;
        return this;
    }

    public MediaHttpUploader setInitiationHeaders(HttpHeaders httpHeaders) {
        this.initiationHeaders = httpHeaders;
        return this;
    }

    public MediaHttpUploader setInitiationRequestMethod(String string2) {
        boolean bl = string2.equals("POST") || string2.equals("PUT") || string2.equals("PATCH");
        Preconditions.checkArgument(bl);
        this.initiationRequestMethod = string2;
        return this;
    }

    public MediaHttpUploader setMetadata(HttpContent httpContent) {
        this.metadata = httpContent;
        return this;
    }

    public MediaHttpUploader setProgressListener(MediaHttpUploaderProgressListener mediaHttpUploaderProgressListener) {
        this.progressListener = mediaHttpUploaderProgressListener;
        return this;
    }

    public MediaHttpUploader setSleeper(Sleeper sleeper) {
        this.sleeper = sleeper;
        return this;
    }

    public HttpResponse upload(GenericUrl genericUrl) throws IOException {
        boolean bl = this.uploadState == UploadState.NOT_STARTED;
        Preconditions.checkArgument(bl);
        if (!this.directUploadEnabled) return this.resumableUpload(genericUrl);
        return this.directUpload(genericUrl);
    }

    private static class ContentChunk {
        private final AbstractInputStreamContent content;
        private final String contentRange;

        ContentChunk(AbstractInputStreamContent abstractInputStreamContent, String string2) {
            this.content = abstractInputStreamContent;
            this.contentRange = string2;
        }

        AbstractInputStreamContent getContent() {
            return this.content;
        }

        String getContentRange() {
            return this.contentRange;
        }
    }

    public static final class UploadState
    extends Enum<UploadState> {
        private static final /* synthetic */ UploadState[] $VALUES;
        public static final /* enum */ UploadState INITIATION_COMPLETE;
        public static final /* enum */ UploadState INITIATION_STARTED;
        public static final /* enum */ UploadState MEDIA_COMPLETE;
        public static final /* enum */ UploadState MEDIA_IN_PROGRESS;
        public static final /* enum */ UploadState NOT_STARTED;

        static {
            UploadState uploadState;
            NOT_STARTED = new UploadState();
            INITIATION_STARTED = new UploadState();
            INITIATION_COMPLETE = new UploadState();
            MEDIA_IN_PROGRESS = new UploadState();
            MEDIA_COMPLETE = uploadState = new UploadState();
            $VALUES = new UploadState[]{NOT_STARTED, INITIATION_STARTED, INITIATION_COMPLETE, MEDIA_IN_PROGRESS, uploadState};
        }

        public static UploadState valueOf(String string2) {
            return Enum.valueOf(UploadState.class, string2);
        }

        public static UploadState[] values() {
            return (UploadState[])$VALUES.clone();
        }
    }

}

