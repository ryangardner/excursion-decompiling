/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3.internal.connection;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ProtocolException;
import java.net.UnknownServiceException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.List;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocket;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.ConnectionSpec;

@Metadata(bv={1, 0, 3}, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\u0002\u0010\u0005J\u000e\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\rJ\u000e\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u0010J\u0010\u0010\b\u001a\u00020\u00072\u0006\u0010\u0011\u001a\u00020\rH\u0002R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2={"Lokhttp3/internal/connection/ConnectionSpecSelector;", "", "connectionSpecs", "", "Lokhttp3/ConnectionSpec;", "(Ljava/util/List;)V", "isFallback", "", "isFallbackPossible", "nextModeIndex", "", "configureSecureSocket", "sslSocket", "Ljavax/net/ssl/SSLSocket;", "connectionFailed", "e", "Ljava/io/IOException;", "socket", "okhttp"}, k=1, mv={1, 1, 16})
public final class ConnectionSpecSelector {
    private final List<ConnectionSpec> connectionSpecs;
    private boolean isFallback;
    private boolean isFallbackPossible;
    private int nextModeIndex;

    public ConnectionSpecSelector(List<ConnectionSpec> list) {
        Intrinsics.checkParameterIsNotNull(list, "connectionSpecs");
        this.connectionSpecs = list;
    }

    private final boolean isFallbackPossible(SSLSocket sSLSocket) {
        int n = this.nextModeIndex;
        int n2 = this.connectionSpecs.size();
        while (n < n2) {
            if (this.connectionSpecs.get(n).isCompatible(sSLSocket)) {
                return true;
            }
            ++n;
        }
        return false;
    }

    public final ConnectionSpec configureSecureSocket(SSLSocket object) throws IOException {
        Object object2;
        Intrinsics.checkParameterIsNotNull(object, "sslSocket");
        ConnectionSpec connectionSpec = null;
        int n = this.nextModeIndex;
        int n2 = this.connectionSpecs.size();
        do {
            object2 = connectionSpec;
            if (n >= n2) break;
            object2 = this.connectionSpecs.get(n);
            if (((ConnectionSpec)object2).isCompatible((SSLSocket)object)) {
                this.nextModeIndex = n + 1;
                break;
            }
            ++n;
        } while (true);
        if (object2 != null) {
            this.isFallbackPossible = this.isFallbackPossible((SSLSocket)object);
            ((ConnectionSpec)object2).apply$okhttp((SSLSocket)object, this.isFallback);
            return object2;
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("Unable to find acceptable protocols. isFallback=");
        ((StringBuilder)object2).append(this.isFallback);
        ((StringBuilder)object2).append(',');
        ((StringBuilder)object2).append(" modes=");
        ((StringBuilder)object2).append(this.connectionSpecs);
        ((StringBuilder)object2).append(',');
        ((StringBuilder)object2).append(" supported protocols=");
        object = object.getEnabledProtocols();
        if (object == null) {
            Intrinsics.throwNpe();
        }
        object = Arrays.toString(object);
        Intrinsics.checkExpressionValueIsNotNull(object, "java.util.Arrays.toString(this)");
        ((StringBuilder)object2).append((String)object);
        throw (Throwable)new UnknownServiceException(((StringBuilder)object2).toString());
    }

    public final boolean connectionFailed(IOException iOException) {
        Intrinsics.checkParameterIsNotNull(iOException, "e");
        boolean bl = true;
        this.isFallback = true;
        if (!this.isFallbackPossible) return false;
        if (iOException instanceof ProtocolException || iOException instanceof InterruptedIOException || iOException instanceof SSLHandshakeException && iOException.getCause() instanceof CertificateException || iOException instanceof SSLPeerUnverifiedException) return false;
        if (iOException instanceof SSLException) return bl;
        return false;
    }
}

