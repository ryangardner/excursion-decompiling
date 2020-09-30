/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.CloudShellCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.OAuth2Utils;
import com.google.api.client.googleapis.auth.oauth2.SystemEnvironmentProvider;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.util.ObjectParser;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.security.AccessControlException;
import java.util.Locale;

class DefaultCredentialProvider
extends SystemEnvironmentProvider {
    static final String APP_ENGINE_CREDENTIAL_CLASS = "com.google.api.client.googleapis.extensions.appengine.auth.oauth2.AppIdentityCredential$AppEngineCredentialWrapper";
    static final String CLOUDSDK_CONFIG_DIRECTORY = "gcloud";
    static final String CLOUD_SHELL_ENV_VAR = "DEVSHELL_CLIENT_PORT";
    static final String CREDENTIAL_ENV_VAR = "GOOGLE_APPLICATION_CREDENTIALS";
    static final String HELP_PERMALINK = "https://developers.google.com/accounts/docs/application-default-credentials";
    static final String WELL_KNOWN_CREDENTIALS_FILE = "application_default_credentials.json";
    private GoogleCredential cachedCredential = null;
    private Environment detectedEnvironment = null;

    DefaultCredentialProvider() {
    }

    private final Environment detectEnvironment(HttpTransport httpTransport) throws IOException {
        if (this.runningUsingEnvironmentVariable()) {
            return Environment.ENVIRONMENT_VARIABLE;
        }
        if (this.runningUsingWellKnownFile()) {
            return Environment.WELL_KNOWN_FILE;
        }
        if (this.useGAEStandardAPI()) {
            return Environment.APP_ENGINE;
        }
        if (this.runningOnCloudShell()) {
            return Environment.CLOUD_SHELL;
        }
        if (!OAuth2Utils.runningOnComputeEngine(httpTransport, this)) return Environment.UNKNOWN;
        return Environment.COMPUTE_ENGINE;
    }

    /*
     * WARNING - void declaration
     */
    private final GoogleCredential getAppEngineCredential(HttpTransport object, JsonFactory jsonFactory) throws IOException {
        void var1_7;
        try {
            return (GoogleCredential)this.forName(APP_ENGINE_CREDENTIAL_CLASS).getConstructor(HttpTransport.class, JsonFactory.class).newInstance(object, jsonFactory);
        }
        catch (InvocationTargetException invocationTargetException) {
            throw OAuth2Utils.exceptionWithCause(new IOException(String.format("Application Default Credentials failed to create the Google App Engine service account credentials class %s. Check that the component 'google-api-client-appengine' is deployed.", APP_ENGINE_CREDENTIAL_CLASS)), (Throwable)var1_7);
        }
        catch (IllegalAccessException illegalAccessException) {
            throw OAuth2Utils.exceptionWithCause(new IOException(String.format("Application Default Credentials failed to create the Google App Engine service account credentials class %s. Check that the component 'google-api-client-appengine' is deployed.", APP_ENGINE_CREDENTIAL_CLASS)), (Throwable)var1_7);
        }
        catch (InstantiationException instantiationException) {
            throw OAuth2Utils.exceptionWithCause(new IOException(String.format("Application Default Credentials failed to create the Google App Engine service account credentials class %s. Check that the component 'google-api-client-appengine' is deployed.", APP_ENGINE_CREDENTIAL_CLASS)), (Throwable)var1_7);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            throw OAuth2Utils.exceptionWithCause(new IOException(String.format("Application Default Credentials failed to create the Google App Engine service account credentials class %s. Check that the component 'google-api-client-appengine' is deployed.", APP_ENGINE_CREDENTIAL_CLASS)), (Throwable)var1_7);
        }
        catch (ClassNotFoundException classNotFoundException) {
            // empty catch block
        }
        throw OAuth2Utils.exceptionWithCause(new IOException(String.format("Application Default Credentials failed to create the Google App Engine service account credentials class %s. Check that the component 'google-api-client-appengine' is deployed.", APP_ENGINE_CREDENTIAL_CLASS)), (Throwable)var1_7);
    }

    private GoogleCredential getCloudShellCredential(JsonFactory jsonFactory) {
        return new CloudShellCredential(Integer.parseInt(this.getEnv(CLOUD_SHELL_ENV_VAR)), jsonFactory);
    }

    private final GoogleCredential getComputeCredential(HttpTransport httpTransport, JsonFactory jsonFactory) {
        return new ComputeGoogleCredential(httpTransport, jsonFactory);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * WARNING - void declaration
     * Enabled unnecessary exception pruning
     */
    private GoogleCredential getCredentialUsingEnvironmentVariable(HttpTransport object, JsonFactory jsonFactory) throws IOException {
        void var1_4;
        Object object2;
        block7 : {
            String string2;
            Object object3;
            block8 : {
                Object httpTransport;
                string2 = this.getEnv(CREDENTIAL_ENV_VAR);
                Object var4_10 = null;
                object2 = httpTransport = null;
                object2 = httpTransport;
                object3 = new FileInputStream(string2);
                try {
                    object = GoogleCredential.fromStream((InputStream)object3, (HttpTransport)object, jsonFactory);
                }
                catch (Throwable throwable) {
                    object2 = object3;
                    break block7;
                }
                catch (IOException iOException) {
                    object = object3;
                    break block8;
                }
                ((InputStream)object3).close();
                return object;
                catch (Throwable throwable) {
                    break block7;
                }
                catch (IOException iOException) {
                    object = var4_10;
                }
            }
            object2 = object;
            {
                void var2_8;
                object2 = object;
                object3 = new IOException(String.format("Error reading credential file from environment variable %s, value '%s': %s", CREDENTIAL_ENV_VAR, string2, var2_8.getMessage()));
                object2 = object;
                throw (IOException)OAuth2Utils.exceptionWithCause(object3, (Throwable)var2_8);
            }
        }
        if (object2 == null) throw var1_4;
        ((InputStream)object2).close();
        throw var1_4;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * WARNING - void declaration
     * Enabled unnecessary exception pruning
     */
    private GoogleCredential getCredentialUsingWellKnownFile(HttpTransport object, JsonFactory jsonFactory) throws IOException {
        void var1_4;
        Object object2;
        block7 : {
            File file;
            Object object3;
            block8 : {
                Object httpTransport;
                file = this.getWellKnownCredentialsFile();
                Object var4_10 = null;
                object2 = httpTransport = null;
                object2 = httpTransport;
                object3 = new FileInputStream(file);
                try {
                    object = GoogleCredential.fromStream((InputStream)object3, (HttpTransport)object, jsonFactory);
                }
                catch (Throwable throwable) {
                    object2 = object3;
                    break block7;
                }
                catch (IOException iOException) {
                    object = object3;
                    break block8;
                }
                ((InputStream)object3).close();
                return object;
                catch (Throwable throwable) {
                    break block7;
                }
                catch (IOException iOException) {
                    object = var4_10;
                }
            }
            object2 = object;
            {
                void var2_8;
                object2 = object;
                object3 = new IOException(String.format("Error reading credential file from location %s: %s", file, var2_8.getMessage()));
                object2 = object;
                throw object3;
            }
        }
        if (object2 == null) throw var1_4;
        ((InputStream)object2).close();
        throw var1_4;
    }

    private final GoogleCredential getDefaultCredentialUnsynchronized(HttpTransport httpTransport, JsonFactory jsonFactory) throws IOException {
        int n;
        if (this.detectedEnvironment == null) {
            this.detectedEnvironment = this.detectEnvironment(httpTransport);
        }
        if ((n = 1.$SwitchMap$com$google$api$client$googleapis$auth$oauth2$DefaultCredentialProvider$Environment[this.detectedEnvironment.ordinal()]) == 1) return this.getCredentialUsingEnvironmentVariable(httpTransport, jsonFactory);
        if (n == 2) return this.getCredentialUsingWellKnownFile(httpTransport, jsonFactory);
        if (n == 3) return this.getAppEngineCredential(httpTransport, jsonFactory);
        if (n == 4) return this.getCloudShellCredential(jsonFactory);
        if (n == 5) return this.getComputeCredential(httpTransport, jsonFactory);
        return null;
    }

    private final File getWellKnownCredentialsFile() {
        File file;
        if (this.getProperty("os.name", "").toLowerCase(Locale.US).indexOf("windows") >= 0) {
            file = new File(new File(this.getEnv("APPDATA")), CLOUDSDK_CONFIG_DIRECTORY);
            return new File(file, WELL_KNOWN_CREDENTIALS_FILE);
        }
        file = new File(new File(this.getProperty("user.home", ""), ".config"), CLOUDSDK_CONFIG_DIRECTORY);
        return new File(file, WELL_KNOWN_CREDENTIALS_FILE);
    }

    private boolean runningOnCloudShell() {
        if (this.getEnv(CLOUD_SHELL_ENV_VAR) == null) return false;
        return true;
    }

    private boolean runningUsingEnvironmentVariable() throws IOException {
        String string2 = this.getEnv(CREDENTIAL_ENV_VAR);
        if (string2 == null) return false;
        if (string2.length() == 0) {
            return false;
        }
        try {
            Serializable serializable = new File(string2);
            if (serializable.exists() && !serializable.isDirectory()) {
                return true;
            }
            serializable = new IOException(String.format("Error reading credential file from environment variable %s, value '%s': File does not exist.", CREDENTIAL_ENV_VAR, string2));
            throw serializable;
        }
        catch (AccessControlException accessControlException) {
            return false;
        }
    }

    private boolean runningUsingWellKnownFile() {
        File file = this.getWellKnownCredentialsFile();
        try {
            return this.fileExists(file);
        }
        catch (AccessControlException accessControlException) {
            return false;
        }
    }

    /*
     * WARNING - void declaration
     */
    private boolean useGAEStandardAPI() {
        Object object;
        void var2_9;
        boolean bl = false;
        try {
            object = this.forName("com.google.appengine.api.utils.SystemProperty");
        }
        catch (ClassNotFoundException classNotFoundException) {
            return false;
        }
        try {
            Field field = ((Class)object).getField("environment");
            object = field.get(null);
            object = field.getType().getMethod("value", new Class[0]).invoke(object, new Object[0]);
            if (object == null) return bl;
            return true;
        }
        catch (InvocationTargetException invocationTargetException) {
            throw OAuth2Utils.exceptionWithCause(new RuntimeException(String.format("Unexpcted error trying to determine if runnning on Google App Engine: %s", var2_9.getMessage())), (Throwable)var2_9);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            throw OAuth2Utils.exceptionWithCause(new RuntimeException(String.format("Unexpcted error trying to determine if runnning on Google App Engine: %s", var2_9.getMessage())), (Throwable)var2_9);
        }
        catch (IllegalAccessException illegalAccessException) {
            throw OAuth2Utils.exceptionWithCause(new RuntimeException(String.format("Unexpcted error trying to determine if runnning on Google App Engine: %s", var2_9.getMessage())), (Throwable)var2_9);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw OAuth2Utils.exceptionWithCause(new RuntimeException(String.format("Unexpcted error trying to determine if runnning on Google App Engine: %s", var2_9.getMessage())), (Throwable)var2_9);
        }
        catch (SecurityException securityException) {
            throw OAuth2Utils.exceptionWithCause(new RuntimeException(String.format("Unexpcted error trying to determine if runnning on Google App Engine: %s", var2_9.getMessage())), (Throwable)var2_9);
        }
        catch (NoSuchFieldException noSuchFieldException) {
            // empty catch block
        }
        throw OAuth2Utils.exceptionWithCause(new RuntimeException(String.format("Unexpcted error trying to determine if runnning on Google App Engine: %s", var2_9.getMessage())), (Throwable)var2_9);
    }

    boolean fileExists(File file) {
        if (!file.exists()) return false;
        if (file.isDirectory()) return false;
        return true;
    }

    Class<?> forName(String string2) throws ClassNotFoundException {
        return Class.forName(string2);
    }

    /*
     * Enabled unnecessary exception pruning
     */
    final GoogleCredential getDefaultCredential(HttpTransport object, JsonFactory jsonFactory) throws IOException {
        synchronized (this) {
            if (this.cachedCredential == null) {
                this.cachedCredential = this.getDefaultCredentialUnsynchronized((HttpTransport)object, jsonFactory);
            }
            if (this.cachedCredential != null) {
                return this.cachedCredential;
            }
            throw new IOException(String.format("The Application Default Credentials are not available. They are available if running on Google App Engine, Google Compute Engine, or Google Cloud Shell. Otherwise, the environment variable %s must be defined pointing to a file defining the credentials. See %s for more information.", CREDENTIAL_ENV_VAR, HELP_PERMALINK));
        }
    }

    String getProperty(String string2, String string3) {
        return System.getProperty(string2, string3);
    }

    private static class ComputeGoogleCredential
    extends GoogleCredential {
        private static final String TOKEN_SERVER_ENCODED_URL;

        static {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(OAuth2Utils.getMetadataServerUrl());
            stringBuilder.append("/computeMetadata/v1/instance/service-accounts/default/token");
            TOKEN_SERVER_ENCODED_URL = stringBuilder.toString();
        }

        ComputeGoogleCredential(HttpTransport httpTransport, JsonFactory jsonFactory) {
            super((GoogleCredential.Builder)((GoogleCredential.Builder)((GoogleCredential.Builder)new GoogleCredential.Builder().setTransport(httpTransport)).setJsonFactory(jsonFactory)).setTokenServerEncodedUrl(TOKEN_SERVER_ENCODED_URL));
        }

        @Override
        protected TokenResponse executeRefreshToken() throws IOException {
            Object object = new GenericUrl(this.getTokenServerEncodedUrl());
            Object object2 = this.getTransport().createRequestFactory().buildGetRequest((GenericUrl)object);
            object = new JsonObjectParser(this.getJsonFactory());
            ((HttpRequest)object2).setParser((ObjectParser)object);
            ((HttpRequest)object2).getHeaders().set("Metadata-Flavor", "Google");
            ((HttpRequest)object2).setThrowExceptionOnExecuteError(false);
            HttpResponse httpResponse = ((HttpRequest)object2).execute();
            int n = httpResponse.getStatusCode();
            if (n == 200) {
                object2 = httpResponse.getContent();
                if (object2 == null) throw new IOException("Empty content from metadata token server request.");
                return ((JsonObjectParser)object).parseAndClose((InputStream)object2, httpResponse.getContentCharset(), TokenResponse.class);
            }
            if (n != 404) throw new IOException(String.format("Unexpected Error code %s trying to get security access token from Compute Engine metadata for the default service account: %s", n, httpResponse.parseAsString()));
            throw new IOException(String.format("Error code %s trying to get security access token from Compute Engine metadata for the default service account. This may be because the virtual machine instance does not have permission scopes specified.", n));
        }
    }

    private static final class Environment
    extends Enum<Environment> {
        private static final /* synthetic */ Environment[] $VALUES;
        public static final /* enum */ Environment APP_ENGINE;
        public static final /* enum */ Environment CLOUD_SHELL;
        public static final /* enum */ Environment COMPUTE_ENGINE;
        public static final /* enum */ Environment ENVIRONMENT_VARIABLE;
        public static final /* enum */ Environment UNKNOWN;
        public static final /* enum */ Environment WELL_KNOWN_FILE;

        static {
            Environment environment;
            UNKNOWN = new Environment();
            ENVIRONMENT_VARIABLE = new Environment();
            WELL_KNOWN_FILE = new Environment();
            CLOUD_SHELL = new Environment();
            APP_ENGINE = new Environment();
            COMPUTE_ENGINE = environment = new Environment();
            $VALUES = new Environment[]{UNKNOWN, ENVIRONMENT_VARIABLE, WELL_KNOWN_FILE, CLOUD_SHELL, APP_ENGINE, environment};
        }

        public static Environment valueOf(String string2) {
            return Enum.valueOf(Environment.class, string2);
        }

        public static Environment[] values() {
            return (Environment[])$VALUES.clone();
        }
    }

}

