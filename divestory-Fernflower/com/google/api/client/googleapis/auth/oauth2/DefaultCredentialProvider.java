package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.security.AccessControlException;
import java.util.Locale;

class DefaultCredentialProvider extends SystemEnvironmentProvider {
   static final String APP_ENGINE_CREDENTIAL_CLASS = "com.google.api.client.googleapis.extensions.appengine.auth.oauth2.AppIdentityCredential$AppEngineCredentialWrapper";
   static final String CLOUDSDK_CONFIG_DIRECTORY = "gcloud";
   static final String CLOUD_SHELL_ENV_VAR = "DEVSHELL_CLIENT_PORT";
   static final String CREDENTIAL_ENV_VAR = "GOOGLE_APPLICATION_CREDENTIALS";
   static final String HELP_PERMALINK = "https://developers.google.com/accounts/docs/application-default-credentials";
   static final String WELL_KNOWN_CREDENTIALS_FILE = "application_default_credentials.json";
   private GoogleCredential cachedCredential = null;
   private DefaultCredentialProvider.Environment detectedEnvironment = null;

   private final DefaultCredentialProvider.Environment detectEnvironment(HttpTransport var1) throws IOException {
      if (this.runningUsingEnvironmentVariable()) {
         return DefaultCredentialProvider.Environment.ENVIRONMENT_VARIABLE;
      } else if (this.runningUsingWellKnownFile()) {
         return DefaultCredentialProvider.Environment.WELL_KNOWN_FILE;
      } else if (this.useGAEStandardAPI()) {
         return DefaultCredentialProvider.Environment.APP_ENGINE;
      } else if (this.runningOnCloudShell()) {
         return DefaultCredentialProvider.Environment.CLOUD_SHELL;
      } else {
         return OAuth2Utils.runningOnComputeEngine(var1, this) ? DefaultCredentialProvider.Environment.COMPUTE_ENGINE : DefaultCredentialProvider.Environment.UNKNOWN;
      }
   }

   private final GoogleCredential getAppEngineCredential(HttpTransport var1, JsonFactory var2) throws IOException {
      Object var8;
      try {
         GoogleCredential var9 = (GoogleCredential)this.forName("com.google.api.client.googleapis.extensions.appengine.auth.oauth2.AppIdentityCredential$AppEngineCredentialWrapper").getConstructor(HttpTransport.class, JsonFactory.class).newInstance(var1, var2);
         return var9;
      } catch (ClassNotFoundException var3) {
         var8 = var3;
      } catch (NoSuchMethodException var4) {
         var8 = var4;
      } catch (InstantiationException var5) {
         var8 = var5;
      } catch (IllegalAccessException var6) {
         var8 = var6;
      } catch (InvocationTargetException var7) {
         var8 = var7;
      }

      throw (IOException)OAuth2Utils.exceptionWithCause(new IOException(String.format("Application Default Credentials failed to create the Google App Engine service account credentials class %s. Check that the component 'google-api-client-appengine' is deployed.", "com.google.api.client.googleapis.extensions.appengine.auth.oauth2.AppIdentityCredential$AppEngineCredentialWrapper")), (Throwable)var8);
   }

   private GoogleCredential getCloudShellCredential(JsonFactory var1) {
      return new CloudShellCredential(Integer.parseInt(this.getEnv("DEVSHELL_CLIENT_PORT")), var1);
   }

   private final GoogleCredential getComputeCredential(HttpTransport var1, JsonFactory var2) {
      return new DefaultCredentialProvider.ComputeGoogleCredential(var1, var2);
   }

   private GoogleCredential getCredentialUsingEnvironmentVariable(HttpTransport param1, JsonFactory param2) throws IOException {
      // $FF: Couldn't be decompiled
   }

   private GoogleCredential getCredentialUsingWellKnownFile(HttpTransport param1, JsonFactory param2) throws IOException {
      // $FF: Couldn't be decompiled
   }

   private final GoogleCredential getDefaultCredentialUnsynchronized(HttpTransport var1, JsonFactory var2) throws IOException {
      if (this.detectedEnvironment == null) {
         this.detectedEnvironment = this.detectEnvironment(var1);
      }

      int var3 = null.$SwitchMap$com$google$api$client$googleapis$auth$oauth2$DefaultCredentialProvider$Environment[this.detectedEnvironment.ordinal()];
      if (var3 != 1) {
         if (var3 != 2) {
            if (var3 != 3) {
               if (var3 != 4) {
                  return var3 != 5 ? null : this.getComputeCredential(var1, var2);
               } else {
                  return this.getCloudShellCredential(var2);
               }
            } else {
               return this.getAppEngineCredential(var1, var2);
            }
         } else {
            return this.getCredentialUsingWellKnownFile(var1, var2);
         }
      } else {
         return this.getCredentialUsingEnvironmentVariable(var1, var2);
      }
   }

   private final File getWellKnownCredentialsFile() {
      File var1;
      if (this.getProperty("os.name", "").toLowerCase(Locale.US).indexOf("windows") >= 0) {
         var1 = new File(new File(this.getEnv("APPDATA")), "gcloud");
      } else {
         var1 = new File(new File(this.getProperty("user.home", ""), ".config"), "gcloud");
      }

      return new File(var1, "application_default_credentials.json");
   }

   private boolean runningOnCloudShell() {
      boolean var1;
      if (this.getEnv("DEVSHELL_CLIENT_PORT") != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private boolean runningUsingEnvironmentVariable() throws IOException {
      String var1 = this.getEnv("GOOGLE_APPLICATION_CREDENTIALS");
      if (var1 != null && var1.length() != 0) {
         try {
            File var2 = new File(var1);
            if (var2.exists() && !var2.isDirectory()) {
               return true;
            }

            IOException var4 = new IOException(String.format("Error reading credential file from environment variable %s, value '%s': File does not exist.", "GOOGLE_APPLICATION_CREDENTIALS", var1));
            throw var4;
         } catch (AccessControlException var3) {
         }
      }

      return false;
   }

   private boolean runningUsingWellKnownFile() {
      File var1 = this.getWellKnownCredentialsFile();

      try {
         boolean var2 = this.fileExists(var1);
         return var2;
      } catch (AccessControlException var3) {
         return false;
      }
   }

   private boolean useGAEStandardAPI() {
      boolean var1 = false;

      Class var2;
      try {
         var2 = this.forName("com.google.appengine.api.utils.SystemProperty");
      } catch (ClassNotFoundException var4) {
         return false;
      }

      Object var11;
      label32: {
         try {
            Field var3 = var2.getField("environment");
            var11 = var3.get((Object)null);
            var11 = var3.getType().getMethod("value").invoke(var11);
            break label32;
         } catch (NoSuchFieldException var5) {
            var11 = var5;
         } catch (SecurityException var6) {
            var11 = var6;
         } catch (IllegalArgumentException var7) {
            var11 = var7;
         } catch (IllegalAccessException var8) {
            var11 = var8;
         } catch (NoSuchMethodException var9) {
            var11 = var9;
         } catch (InvocationTargetException var10) {
            var11 = var10;
         }

         throw (RuntimeException)OAuth2Utils.exceptionWithCause(new RuntimeException(String.format("Unexpcted error trying to determine if runnning on Google App Engine: %s", ((Exception)var11).getMessage())), (Throwable)var11);
      }

      if (var11 != null) {
         var1 = true;
      }

      return var1;
   }

   boolean fileExists(File var1) {
      boolean var2;
      if (var1.exists() && !var1.isDirectory()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   Class<?> forName(String var1) throws ClassNotFoundException {
      return Class.forName(var1);
   }

   final GoogleCredential getDefaultCredential(HttpTransport var1, JsonFactory var2) throws IOException {
      synchronized(this){}

      Throwable var10000;
      boolean var10001;
      label206: {
         try {
            if (this.cachedCredential == null) {
               this.cachedCredential = this.getDefaultCredentialUnsynchronized(var1, var2);
            }
         } catch (Throwable var21) {
            var10000 = var21;
            var10001 = false;
            break label206;
         }

         try {
            if (this.cachedCredential != null) {
               GoogleCredential var24 = this.cachedCredential;
               return var24;
            }
         } catch (Throwable var22) {
            var10000 = var22;
            var10001 = false;
            break label206;
         }

         try {
            ;
         } catch (Throwable var20) {
            var10000 = var20;
            var10001 = false;
            break label206;
         }

         throw new IOException(String.format("The Application Default Credentials are not available. They are available if running on Google App Engine, Google Compute Engine, or Google Cloud Shell. Otherwise, the environment variable %s must be defined pointing to a file defining the credentials. See %s for more information.", "GOOGLE_APPLICATION_CREDENTIALS", "https://developers.google.com/accounts/docs/application-default-credentials"));
      }

      while(true) {
         Throwable var23 = var10000;

         try {
            throw var23;
         } catch (Throwable var19) {
            var10000 = var19;
            var10001 = false;
            continue;
         }
      }
   }

   String getProperty(String var1, String var2) {
      return System.getProperty(var1, var2);
   }

   private static class ComputeGoogleCredential extends GoogleCredential {
      private static final String TOKEN_SERVER_ENCODED_URL;

      static {
         StringBuilder var0 = new StringBuilder();
         var0.append(OAuth2Utils.getMetadataServerUrl());
         var0.append("/computeMetadata/v1/instance/service-accounts/default/token");
         TOKEN_SERVER_ENCODED_URL = var0.toString();
      }

      ComputeGoogleCredential(HttpTransport var1, JsonFactory var2) {
         super((new GoogleCredential.Builder()).setTransport(var1).setJsonFactory(var2).setTokenServerEncodedUrl(TOKEN_SERVER_ENCODED_URL));
      }

      protected TokenResponse executeRefreshToken() throws IOException {
         GenericUrl var1 = new GenericUrl(this.getTokenServerEncodedUrl());
         HttpRequest var2 = this.getTransport().createRequestFactory().buildGetRequest(var1);
         JsonObjectParser var5 = new JsonObjectParser(this.getJsonFactory());
         var2.setParser(var5);
         var2.getHeaders().set("Metadata-Flavor", "Google");
         var2.setThrowExceptionOnExecuteError(false);
         HttpResponse var3 = var2.execute();
         int var4 = var3.getStatusCode();
         if (var4 == 200) {
            InputStream var6 = var3.getContent();
            if (var6 != null) {
               return (TokenResponse)var5.parseAndClose(var6, var3.getContentCharset(), TokenResponse.class);
            } else {
               throw new IOException("Empty content from metadata token server request.");
            }
         } else if (var4 == 404) {
            throw new IOException(String.format("Error code %s trying to get security access token from Compute Engine metadata for the default service account. This may be because the virtual machine instance does not have permission scopes specified.", var4));
         } else {
            throw new IOException(String.format("Unexpected Error code %s trying to get security access token from Compute Engine metadata for the default service account: %s", var4, var3.parseAsString()));
         }
      }
   }

   private static enum Environment {
      APP_ENGINE,
      CLOUD_SHELL,
      COMPUTE_ENGINE,
      ENVIRONMENT_VARIABLE,
      UNKNOWN,
      WELL_KNOWN_FILE;

      static {
         DefaultCredentialProvider.Environment var0 = new DefaultCredentialProvider.Environment("COMPUTE_ENGINE", 5);
         COMPUTE_ENGINE = var0;
      }
   }
}
