/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.googleapis.auth.oauth2.SystemEnvironmentProvider;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.util.GenericData;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OAuth2Utils {
    private static final int COMPUTE_PING_CONNECTION_TIMEOUT_MS = 500;
    private static final String DEFAULT_METADATA_SERVER_URL = "http://169.254.169.254";
    private static final Logger LOGGER;
    private static final int MAX_COMPUTE_PING_TRIES = 3;
    static final Charset UTF_8;

    static {
        UTF_8 = Charset.forName("UTF-8");
        LOGGER = Logger.getLogger(OAuth2Utils.class.getName());
    }

    static <T extends Throwable> T exceptionWithCause(T t, Throwable throwable) {
        ((Throwable)t).initCause(throwable);
        return t;
    }

    public static String getMetadataServerUrl() {
        return OAuth2Utils.getMetadataServerUrl(SystemEnvironmentProvider.INSTANCE);
    }

    static String getMetadataServerUrl(SystemEnvironmentProvider object) {
        String string2 = ((SystemEnvironmentProvider)object).getEnv("GCE_METADATA_HOST");
        if (string2 == null) return DEFAULT_METADATA_SERVER_URL;
        object = new StringBuilder();
        ((StringBuilder)object).append("http://");
        ((StringBuilder)object).append(string2);
        return ((StringBuilder)object).toString();
    }

    static boolean headersContainValue(HttpHeaders object, String object2, String string2) {
        if (!((object = ((GenericData)object).get(object2)) instanceof Collection)) return false;
        object2 = ((Collection)object).iterator();
        do {
            if (!object2.hasNext()) return false;
        } while (!((object = object2.next()) instanceof String) || !((String)object).equals(string2));
        return true;
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    static boolean runningOnComputeEngine(HttpTransport httpTransport, SystemEnvironmentProvider object) {
        if (Boolean.parseBoolean(((SystemEnvironmentProvider)object).getEnv("NO_GCE_CHECK"))) {
            return false;
        }
        object = new GenericUrl(OAuth2Utils.getMetadataServerUrl((SystemEnvironmentProvider)object));
        int n = 1;
        while (n <= 3) {
            Object object2 = httpTransport.createRequestFactory().buildGetRequest((GenericUrl)object);
            ((HttpRequest)object2).setConnectTimeout(500);
            ((HttpRequest)object2).getHeaders().set("Metadata-Flavor", "Google");
            object2 = ((HttpRequest)object2).execute();
            boolean bl = OAuth2Utils.headersContainValue(((HttpResponse)object2).getHeaders(), "Metadata-Flavor", "Google");
            {
                catch (Throwable throwable) {
                    ((HttpResponse)object2).disconnect();
                    throw throwable;
                }
            }
            try {
                ((HttpResponse)object2).disconnect();
                return bl;
            }
            catch (IOException iOException) {
                block8 : {
                    LOGGER.log(Level.WARNING, "Failed to detect whether we are running on Google Compute Engine.", iOException);
                    break block8;
                    catch (SocketTimeoutException socketTimeoutException) {}
                }
                ++n;
            }
        }
        return false;
    }
}

