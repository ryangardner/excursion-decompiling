/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3.internal.connection;

import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00060\u0001j\u0002`\u0002B\u000f\b\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0004R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001e\u0010\t\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0004@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0007\u00a8\u0006\u000e"}, d2={"Lokhttp3/internal/connection/RouteException;", "Ljava/lang/RuntimeException;", "Lkotlin/RuntimeException;", "firstConnectException", "Ljava/io/IOException;", "(Ljava/io/IOException;)V", "getFirstConnectException", "()Ljava/io/IOException;", "<set-?>", "lastConnectException", "getLastConnectException", "addConnectException", "", "e", "okhttp"}, k=1, mv={1, 1, 16})
public final class RouteException
extends RuntimeException {
    private final IOException firstConnectException;
    private IOException lastConnectException;

    public RouteException(IOException iOException) {
        Intrinsics.checkParameterIsNotNull(iOException, "firstConnectException");
        super(iOException);
        this.firstConnectException = iOException;
        this.lastConnectException = iOException;
    }

    public final void addConnectException(IOException iOException) {
        Intrinsics.checkParameterIsNotNull(iOException, "e");
        this.firstConnectException.addSuppressed(iOException);
        this.lastConnectException = iOException;
    }

    public final IOException getFirstConnectException() {
        return this.firstConnectException;
    }

    public final IOException getLastConnectException() {
        return this.lastConnectException;
    }
}

