/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.media;

import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.http.HttpIOExceptionHandler;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpUnsuccessfulResponseHandler;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

class MediaUploadErrorHandler
implements HttpUnsuccessfulResponseHandler,
HttpIOExceptionHandler {
    static final Logger LOGGER = Logger.getLogger(MediaUploadErrorHandler.class.getName());
    private final HttpIOExceptionHandler originalIOExceptionHandler;
    private final HttpUnsuccessfulResponseHandler originalUnsuccessfulHandler;
    private final MediaHttpUploader uploader;

    public MediaUploadErrorHandler(MediaHttpUploader mediaHttpUploader, HttpRequest httpRequest) {
        this.uploader = Preconditions.checkNotNull(mediaHttpUploader);
        this.originalIOExceptionHandler = httpRequest.getIOExceptionHandler();
        this.originalUnsuccessfulHandler = httpRequest.getUnsuccessfulResponseHandler();
        httpRequest.setIOExceptionHandler(this);
        httpRequest.setUnsuccessfulResponseHandler(this);
    }

    @Override
    public boolean handleIOException(HttpRequest httpRequest, boolean bl) throws IOException {
        HttpIOExceptionHandler httpIOExceptionHandler = this.originalIOExceptionHandler;
        bl = httpIOExceptionHandler != null && httpIOExceptionHandler.handleIOException(httpRequest, bl);
        if (!bl) return bl;
        try {
            this.uploader.serverErrorCallback();
            return bl;
        }
        catch (IOException iOException) {
            LOGGER.log(Level.WARNING, "exception thrown while calling server callback", iOException);
        }
        return bl;
    }

    @Override
    public boolean handleResponse(HttpRequest httpRequest, HttpResponse httpResponse, boolean bl) throws IOException {
        HttpUnsuccessfulResponseHandler httpUnsuccessfulResponseHandler = this.originalUnsuccessfulHandler;
        boolean bl2 = httpUnsuccessfulResponseHandler != null && httpUnsuccessfulResponseHandler.handleResponse(httpRequest, httpResponse, bl);
        if (!bl2) return bl2;
        if (!bl) return bl2;
        if (httpResponse.getStatusCode() / 100 != 5) return bl2;
        try {
            this.uploader.serverErrorCallback();
            return bl2;
        }
        catch (IOException iOException) {
            LOGGER.log(Level.WARNING, "exception thrown while calling server callback", iOException);
        }
        return bl2;
    }
}

