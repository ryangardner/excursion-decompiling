/*
 * Decompiled with CFR <Could not determine version>.
 */
package okio;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okio.AsyncTimeout;
import okio.Okio;

@Metadata(bv={1, 0, 3}, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0012\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\tH\u0014J\b\u0010\u000b\u001a\u00020\fH\u0014R\u0016\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2={"Lokio/SocketAsyncTimeout;", "Lokio/AsyncTimeout;", "socket", "Ljava/net/Socket;", "(Ljava/net/Socket;)V", "logger", "Ljava/util/logging/Logger;", "kotlin.jvm.PlatformType", "newTimeoutException", "Ljava/io/IOException;", "cause", "timedOut", "", "okio"}, k=1, mv={1, 1, 16})
final class SocketAsyncTimeout
extends AsyncTimeout {
    private final Logger logger;
    private final Socket socket;

    public SocketAsyncTimeout(Socket socket) {
        Intrinsics.checkParameterIsNotNull(socket, "socket");
        this.socket = socket;
        this.logger = Logger.getLogger("okio.Okio");
    }

    @Override
    protected IOException newTimeoutException(IOException iOException) {
        SocketTimeoutException socketTimeoutException = new SocketTimeoutException("timeout");
        if (iOException == null) return socketTimeoutException;
        socketTimeoutException.initCause(iOException);
        return socketTimeoutException;
    }

    @Override
    protected void timedOut() {
        try {
            this.socket.close();
            return;
        }
        catch (AssertionError assertionError) {
            if (!Okio.isAndroidGetsocknameError(assertionError)) throw (Throwable)((Object)assertionError);
            Logger logger = this.logger;
            Level level = Level.WARNING;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to close timed out socket ");
            stringBuilder.append(this.socket);
            logger.log(level, stringBuilder.toString(), (Throwable)((Object)assertionError));
            return;
        }
        catch (Exception exception) {
            Logger logger = this.logger;
            Level level = Level.WARNING;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to close timed out socket ");
            stringBuilder.append(this.socket);
            logger.log(level, stringBuilder.toString(), exception);
        }
    }
}

