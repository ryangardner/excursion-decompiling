/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3.internal.cache;

import java.io.IOException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import okio.Buffer;
import okio.ForwardingSink;
import okio.Sink;

@Metadata(bv={1, 0, 3}, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\b\u0010\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005\u00a2\u0006\u0002\u0010\bJ\b\u0010\r\u001a\u00020\u0007H\u0016J\b\u0010\u000e\u001a\u00020\u0007H\u0016J\u0018\u0010\u000f\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u0014"}, d2={"Lokhttp3/internal/cache/FaultHidingSink;", "Lokio/ForwardingSink;", "delegate", "Lokio/Sink;", "onException", "Lkotlin/Function1;", "Ljava/io/IOException;", "", "(Lokio/Sink;Lkotlin/jvm/functions/Function1;)V", "hasErrors", "", "getOnException", "()Lkotlin/jvm/functions/Function1;", "close", "flush", "write", "source", "Lokio/Buffer;", "byteCount", "", "okhttp"}, k=1, mv={1, 1, 16})
public class FaultHidingSink
extends ForwardingSink {
    private boolean hasErrors;
    private final Function1<IOException, Unit> onException;

    public FaultHidingSink(Sink sink2, Function1<? super IOException, Unit> function1) {
        Intrinsics.checkParameterIsNotNull(sink2, "delegate");
        Intrinsics.checkParameterIsNotNull(function1, "onException");
        super(sink2);
        this.onException = function1;
    }

    @Override
    public void close() {
        if (this.hasErrors) {
            return;
        }
        try {
            super.close();
            return;
        }
        catch (IOException iOException) {
            this.hasErrors = true;
            this.onException.invoke(iOException);
        }
    }

    @Override
    public void flush() {
        if (this.hasErrors) {
            return;
        }
        try {
            super.flush();
            return;
        }
        catch (IOException iOException) {
            this.hasErrors = true;
            this.onException.invoke(iOException);
        }
    }

    public final Function1<IOException, Unit> getOnException() {
        return this.onException;
    }

    @Override
    public void write(Buffer buffer, long l) {
        Intrinsics.checkParameterIsNotNull(buffer, "source");
        if (this.hasErrors) {
            buffer.skip(l);
            return;
        }
        try {
            super.write(buffer, l);
            return;
        }
        catch (IOException iOException) {
            this.hasErrors = true;
            this.onException.invoke(iOException);
        }
    }
}

