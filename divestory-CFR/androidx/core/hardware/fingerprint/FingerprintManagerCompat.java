/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.PackageManager
 *  android.hardware.fingerprint.FingerprintManager
 *  android.hardware.fingerprint.FingerprintManager$AuthenticationCallback
 *  android.hardware.fingerprint.FingerprintManager$AuthenticationResult
 *  android.hardware.fingerprint.FingerprintManager$CryptoObject
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.CancellationSignal
 *  android.os.Handler
 */
package androidx.core.hardware.fingerprint;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Handler;
import androidx.core.os.CancellationSignal;
import java.security.Signature;
import javax.crypto.Cipher;
import javax.crypto.Mac;

@Deprecated
public class FingerprintManagerCompat {
    private final Context mContext;

    private FingerprintManagerCompat(Context context) {
        this.mContext = context;
    }

    public static FingerprintManagerCompat from(Context context) {
        return new FingerprintManagerCompat(context);
    }

    private static FingerprintManager getFingerprintManagerOrNull(Context context) {
        if (Build.VERSION.SDK_INT == 23) {
            return (FingerprintManager)context.getSystemService(FingerprintManager.class);
        }
        if (Build.VERSION.SDK_INT <= 23) return null;
        if (!context.getPackageManager().hasSystemFeature("android.hardware.fingerprint")) return null;
        return (FingerprintManager)context.getSystemService(FingerprintManager.class);
    }

    static CryptoObject unwrapCryptoObject(FingerprintManager.CryptoObject cryptoObject) {
        CryptoObject cryptoObject2 = null;
        if (cryptoObject == null) {
            return null;
        }
        if (cryptoObject.getCipher() != null) {
            return new CryptoObject(cryptoObject.getCipher());
        }
        if (cryptoObject.getSignature() != null) {
            return new CryptoObject(cryptoObject.getSignature());
        }
        if (cryptoObject.getMac() == null) return cryptoObject2;
        return new CryptoObject(cryptoObject.getMac());
    }

    private static FingerprintManager.AuthenticationCallback wrapCallback(AuthenticationCallback authenticationCallback) {
        return new FingerprintManager.AuthenticationCallback(){

            public void onAuthenticationError(int n, CharSequence charSequence) {
                AuthenticationCallback.this.onAuthenticationError(n, charSequence);
            }

            public void onAuthenticationFailed() {
                AuthenticationCallback.this.onAuthenticationFailed();
            }

            public void onAuthenticationHelp(int n, CharSequence charSequence) {
                AuthenticationCallback.this.onAuthenticationHelp(n, charSequence);
            }

            public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult authenticationResult) {
                AuthenticationCallback.this.onAuthenticationSucceeded(new AuthenticationResult(FingerprintManagerCompat.unwrapCryptoObject(authenticationResult.getCryptoObject())));
            }
        };
    }

    private static FingerprintManager.CryptoObject wrapCryptoObject(CryptoObject cryptoObject) {
        FingerprintManager.CryptoObject cryptoObject2 = null;
        if (cryptoObject == null) {
            return null;
        }
        if (cryptoObject.getCipher() != null) {
            return new FingerprintManager.CryptoObject(cryptoObject.getCipher());
        }
        if (cryptoObject.getSignature() != null) {
            return new FingerprintManager.CryptoObject(cryptoObject.getSignature());
        }
        if (cryptoObject.getMac() == null) return cryptoObject2;
        return new FingerprintManager.CryptoObject(cryptoObject.getMac());
    }

    public void authenticate(CryptoObject cryptoObject, int n, CancellationSignal cancellationSignal, AuthenticationCallback authenticationCallback, Handler handler) {
        if (Build.VERSION.SDK_INT < 23) return;
        FingerprintManager fingerprintManager = FingerprintManagerCompat.getFingerprintManagerOrNull(this.mContext);
        if (fingerprintManager == null) return;
        cancellationSignal = cancellationSignal != null ? (android.os.CancellationSignal)cancellationSignal.getCancellationSignalObject() : null;
        fingerprintManager.authenticate(FingerprintManagerCompat.wrapCryptoObject(cryptoObject), (android.os.CancellationSignal)cancellationSignal, n, FingerprintManagerCompat.wrapCallback(authenticationCallback), handler);
    }

    public boolean hasEnrolledFingerprints() {
        boolean bl;
        int n = Build.VERSION.SDK_INT;
        boolean bl2 = bl = false;
        if (n < 23) return bl2;
        FingerprintManager fingerprintManager = FingerprintManagerCompat.getFingerprintManagerOrNull(this.mContext);
        bl2 = bl;
        if (fingerprintManager == null) return bl2;
        bl2 = bl;
        if (!fingerprintManager.hasEnrolledFingerprints()) return bl2;
        return true;
    }

    public boolean isHardwareDetected() {
        boolean bl;
        int n = Build.VERSION.SDK_INT;
        boolean bl2 = bl = false;
        if (n < 23) return bl2;
        FingerprintManager fingerprintManager = FingerprintManagerCompat.getFingerprintManagerOrNull(this.mContext);
        bl2 = bl;
        if (fingerprintManager == null) return bl2;
        bl2 = bl;
        if (!fingerprintManager.isHardwareDetected()) return bl2;
        return true;
    }

    public static abstract class AuthenticationCallback {
        public void onAuthenticationError(int n, CharSequence charSequence) {
        }

        public void onAuthenticationFailed() {
        }

        public void onAuthenticationHelp(int n, CharSequence charSequence) {
        }

        public void onAuthenticationSucceeded(AuthenticationResult authenticationResult) {
        }
    }

    public static final class AuthenticationResult {
        private final CryptoObject mCryptoObject;

        public AuthenticationResult(CryptoObject cryptoObject) {
            this.mCryptoObject = cryptoObject;
        }

        public CryptoObject getCryptoObject() {
            return this.mCryptoObject;
        }
    }

    public static class CryptoObject {
        private final Cipher mCipher;
        private final Mac mMac;
        private final Signature mSignature;

        public CryptoObject(Signature signature) {
            this.mSignature = signature;
            this.mCipher = null;
            this.mMac = null;
        }

        public CryptoObject(Cipher cipher) {
            this.mCipher = cipher;
            this.mSignature = null;
            this.mMac = null;
        }

        public CryptoObject(Mac mac) {
            this.mMac = mac;
            this.mCipher = null;
            this.mSignature = null;
        }

        public Cipher getCipher() {
            return this.mCipher;
        }

        public Mac getMac() {
            return this.mMac;
        }

        public Signature getSignature() {
            return this.mSignature;
        }
    }

}

