/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.auth.oauth2;

import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.CredentialRefreshListener;
import com.google.api.client.auth.oauth2.RefreshTokenRequest;
import com.google.api.client.auth.oauth2.TokenErrorResponse;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.HttpUnsuccessfulResponseHandler;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.Clock;
import com.google.api.client.util.Lists;
import com.google.api.client.util.Objects;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Credential
implements HttpExecuteInterceptor,
HttpRequestInitializer,
HttpUnsuccessfulResponseHandler {
    static final Logger LOGGER = Logger.getLogger(Credential.class.getName());
    private String accessToken;
    private final HttpExecuteInterceptor clientAuthentication;
    private final Clock clock;
    private Long expirationTimeMilliseconds;
    private final JsonFactory jsonFactory;
    private final Lock lock = new ReentrantLock();
    private final AccessMethod method;
    private final Collection<CredentialRefreshListener> refreshListeners;
    private String refreshToken;
    private final HttpRequestInitializer requestInitializer;
    private final String tokenServerEncodedUrl;
    private final HttpTransport transport;

    public Credential(AccessMethod accessMethod) {
        this(new Builder(accessMethod));
    }

    protected Credential(Builder builder) {
        this.method = Preconditions.checkNotNull(builder.method);
        this.transport = builder.transport;
        this.jsonFactory = builder.jsonFactory;
        String string2 = builder.tokenServerUrl == null ? null : builder.tokenServerUrl.build();
        this.tokenServerEncodedUrl = string2;
        this.clientAuthentication = builder.clientAuthentication;
        this.requestInitializer = builder.requestInitializer;
        this.refreshListeners = Collections.unmodifiableCollection(builder.refreshListeners);
        this.clock = Preconditions.checkNotNull(builder.clock);
    }

    protected TokenResponse executeRefreshToken() throws IOException {
        if (this.refreshToken != null) return new RefreshTokenRequest(this.transport, this.jsonFactory, new GenericUrl(this.tokenServerEncodedUrl), this.refreshToken).setClientAuthentication(this.clientAuthentication).setRequestInitializer(this.requestInitializer).execute();
        return null;
    }

    public final String getAccessToken() {
        this.lock.lock();
        try {
            String string2 = this.accessToken;
            return string2;
        }
        finally {
            this.lock.unlock();
        }
    }

    public final HttpExecuteInterceptor getClientAuthentication() {
        return this.clientAuthentication;
    }

    public final Clock getClock() {
        return this.clock;
    }

    public final Long getExpirationTimeMilliseconds() {
        this.lock.lock();
        try {
            Long l = this.expirationTimeMilliseconds;
            return l;
        }
        finally {
            this.lock.unlock();
        }
    }

    public final Long getExpiresInSeconds() {
        this.lock.lock();
        Long l = this.expirationTimeMilliseconds;
        if (l != null) return (this.expirationTimeMilliseconds - this.clock.currentTimeMillis()) / 1000L;
        return null;
    }

    public final JsonFactory getJsonFactory() {
        return this.jsonFactory;
    }

    public final AccessMethod getMethod() {
        return this.method;
    }

    public final Collection<CredentialRefreshListener> getRefreshListeners() {
        return this.refreshListeners;
    }

    public final String getRefreshToken() {
        this.lock.lock();
        try {
            String string2 = this.refreshToken;
            return string2;
        }
        finally {
            this.lock.unlock();
        }
    }

    public final HttpRequestInitializer getRequestInitializer() {
        return this.requestInitializer;
    }

    public final String getTokenServerEncodedUrl() {
        return this.tokenServerEncodedUrl;
    }

    public final HttpTransport getTransport() {
        return this.transport;
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    @Override
    public boolean handleResponse(HttpRequest httpRequest, HttpResponse httpResponse, boolean bl) {
        block9 : {
            boolean bl2;
            Object object = httpResponse.getHeaders().getAuthenticateAsList();
            boolean bl3 = true;
            if (object != null) {
                object = object.iterator();
                while (object.hasNext()) {
                    String string2 = (String)object.next();
                    if (!string2.startsWith("Bearer ")) continue;
                    bl = BearerToken.INVALID_TOKEN_ERROR.matcher(string2).find();
                    bl2 = true;
                    break;
                }
            } else {
                bl = false;
                bl2 = false;
            }
            if (!bl2) {
                bl = httpResponse.getStatusCode() == 401;
            }
            if (!bl) return false;
            this.lock.lock();
            bl = bl3;
            if (!Objects.equal(this.accessToken, this.method.getAccessTokenFromRequest(httpRequest))) break block9;
            bl = this.refreshToken();
            bl = bl ? bl3 : false;
            {
                catch (Throwable throwable) {
                    this.lock.unlock();
                    throw throwable;
                }
            }
        }
        try {
            this.lock.unlock();
            return bl;
        }
        catch (IOException iOException) {
            LOGGER.log(Level.SEVERE, "unable to refresh token", iOException);
        }
        return false;
    }

    @Override
    public void initialize(HttpRequest httpRequest) throws IOException {
        httpRequest.setInterceptor(this);
        httpRequest.setUnsuccessfulResponseHandler(this);
    }

    @Override
    public void intercept(HttpRequest httpRequest) throws IOException {
        this.lock.lock();
        Object object = this.getExpiresInSeconds();
        if (this.accessToken == null || object != null && (Long)object <= 60L) {
            this.refreshToken();
            object = this.accessToken;
            if (object == null) {
                this.lock.unlock();
                return;
            }
        }
        this.method.intercept(httpRequest, this.accessToken);
        return;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    public final boolean refreshToken() throws IOException {
        Throwable throwable2222;
        block11 : {
            this.lock.lock();
            boolean bl = true;
            TokenResponse tokenResponse = this.executeRefreshToken();
            if (tokenResponse != null) {
                this.setFromTokenResponse(tokenResponse);
                Iterator<CredentialRefreshListener> iterator2 = this.refreshListeners.iterator();
                do {
                    if (!iterator2.hasNext()) {
                        this.lock.unlock();
                        return true;
                    }
                    iterator2.next().onTokenResponse(this, tokenResponse);
                } while (true);
            }
            {
                catch (Throwable throwable2222) {
                    break block11;
                }
                catch (TokenResponseException tokenResponseException) {}
                {
                    if (400 > tokenResponseException.getStatusCode() || tokenResponseException.getStatusCode() >= 500) {
                        bl = false;
                    }
                    if (tokenResponseException.getDetails() != null && bl) {
                        this.setAccessToken(null);
                        this.setExpiresInSeconds(null);
                    }
                    Iterator<CredentialRefreshListener> iterator3 = this.refreshListeners.iterator();
                    while (iterator3.hasNext()) {
                        iterator3.next().onTokenErrorResponse(this, tokenResponseException.getDetails());
                    }
                    if (bl) throw tokenResponseException;
                }
            }
            this.lock.unlock();
            return false;
        }
        this.lock.unlock();
        throw throwable2222;
    }

    public Credential setAccessToken(String string2) {
        this.lock.lock();
        try {
            this.accessToken = string2;
            return this;
        }
        finally {
            this.lock.unlock();
        }
    }

    public Credential setExpirationTimeMilliseconds(Long l) {
        this.lock.lock();
        try {
            this.expirationTimeMilliseconds = l;
            return this;
        }
        finally {
            this.lock.unlock();
        }
    }

    public Credential setExpiresInSeconds(Long l) {
        if (l == null) {
            l = null;
            return this.setExpirationTimeMilliseconds(l);
        }
        l = this.clock.currentTimeMillis() + l * 1000L;
        return this.setExpirationTimeMilliseconds(l);
    }

    public Credential setFromTokenResponse(TokenResponse tokenResponse) {
        this.setAccessToken(tokenResponse.getAccessToken());
        if (tokenResponse.getRefreshToken() != null) {
            this.setRefreshToken(tokenResponse.getRefreshToken());
        }
        this.setExpiresInSeconds(tokenResponse.getExpiresInSeconds());
        return this;
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    public Credential setRefreshToken(String var1_1) {
        this.lock.lock();
        if (var1_1 == null) ** GOTO lbl6
        try {
            var2_3 = this.jsonFactory != null && this.transport != null && this.clientAuthentication != null && this.tokenServerEncodedUrl != null;
            Preconditions.checkArgument(var2_3, "Please use the Builder and call setJsonFactory, setTransport, setClientAuthentication and setTokenServerUrl/setTokenServerEncodedUrl");
lbl6: // 2 sources:
            this.refreshToken = var1_1;
            return this;
        }
        finally {
            this.lock.unlock();
        }
    }

    public static interface AccessMethod {
        public String getAccessTokenFromRequest(HttpRequest var1);

        public void intercept(HttpRequest var1, String var2) throws IOException;
    }

    public static class Builder {
        HttpExecuteInterceptor clientAuthentication;
        Clock clock = Clock.SYSTEM;
        JsonFactory jsonFactory;
        final AccessMethod method;
        Collection<CredentialRefreshListener> refreshListeners = Lists.newArrayList();
        HttpRequestInitializer requestInitializer;
        GenericUrl tokenServerUrl;
        HttpTransport transport;

        public Builder(AccessMethod accessMethod) {
            this.method = Preconditions.checkNotNull(accessMethod);
        }

        public Builder addRefreshListener(CredentialRefreshListener credentialRefreshListener) {
            this.refreshListeners.add(Preconditions.checkNotNull(credentialRefreshListener));
            return this;
        }

        public Credential build() {
            return new Credential(this);
        }

        public final HttpExecuteInterceptor getClientAuthentication() {
            return this.clientAuthentication;
        }

        public final Clock getClock() {
            return this.clock;
        }

        public final JsonFactory getJsonFactory() {
            return this.jsonFactory;
        }

        public final AccessMethod getMethod() {
            return this.method;
        }

        public final Collection<CredentialRefreshListener> getRefreshListeners() {
            return this.refreshListeners;
        }

        public final HttpRequestInitializer getRequestInitializer() {
            return this.requestInitializer;
        }

        public final GenericUrl getTokenServerUrl() {
            return this.tokenServerUrl;
        }

        public final HttpTransport getTransport() {
            return this.transport;
        }

        public Builder setClientAuthentication(HttpExecuteInterceptor httpExecuteInterceptor) {
            this.clientAuthentication = httpExecuteInterceptor;
            return this;
        }

        public Builder setClock(Clock clock) {
            this.clock = Preconditions.checkNotNull(clock);
            return this;
        }

        public Builder setJsonFactory(JsonFactory jsonFactory) {
            this.jsonFactory = jsonFactory;
            return this;
        }

        public Builder setRefreshListeners(Collection<CredentialRefreshListener> collection) {
            this.refreshListeners = Preconditions.checkNotNull(collection);
            return this;
        }

        public Builder setRequestInitializer(HttpRequestInitializer httpRequestInitializer) {
            this.requestInitializer = httpRequestInitializer;
            return this;
        }

        public Builder setTokenServerEncodedUrl(String object) {
            object = object == null ? null : new GenericUrl((String)object);
            this.tokenServerUrl = object;
            return this;
        }

        public Builder setTokenServerUrl(GenericUrl genericUrl) {
            this.tokenServerUrl = genericUrl;
            return this;
        }

        public Builder setTransport(HttpTransport httpTransport) {
            this.transport = httpTransport;
            return this;
        }
    }

}

