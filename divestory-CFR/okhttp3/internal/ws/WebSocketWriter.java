/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3.internal.ws;

import java.io.Closeable;
import java.io.IOException;
import java.util.Random;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.ws.MessageDeflater;
import okhttp3.internal.ws.WebSocketProtocol;
import okio.Buffer;
import okio.BufferedSink;
import okio.ByteString;

@Metadata(bv={1, 0, 3}, d1={"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\fJ\b\u0010\u001b\u001a\u00020\u001cH\u0016J\u0018\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010!J\u0018\u0010\"\u001a\u00020\u001c2\u0006\u0010#\u001a\u00020\u001f2\u0006\u0010$\u001a\u00020!H\u0002J\u0016\u0010%\u001a\u00020\u001c2\u0006\u0010&\u001a\u00020\u001f2\u0006\u0010'\u001a\u00020!J\u000e\u0010(\u001a\u00020\u001c2\u0006\u0010$\u001a\u00020!J\u000e\u0010)\u001a\u00020\u001c2\u0006\u0010$\u001a\u00020!R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u000e\u0010\u0019\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006*"}, d2={"Lokhttp3/internal/ws/WebSocketWriter;", "Ljava/io/Closeable;", "isClient", "", "sink", "Lokio/BufferedSink;", "random", "Ljava/util/Random;", "perMessageDeflate", "noContextTakeover", "minimumDeflateSize", "", "(ZLokio/BufferedSink;Ljava/util/Random;ZZJ)V", "maskCursor", "Lokio/Buffer$UnsafeCursor;", "maskKey", "", "messageBuffer", "Lokio/Buffer;", "messageDeflater", "Lokhttp3/internal/ws/MessageDeflater;", "getRandom", "()Ljava/util/Random;", "getSink", "()Lokio/BufferedSink;", "sinkBuffer", "writerClosed", "close", "", "writeClose", "code", "", "reason", "Lokio/ByteString;", "writeControlFrame", "opcode", "payload", "writeMessageFrame", "formatOpcode", "data", "writePing", "writePong", "okhttp"}, k=1, mv={1, 1, 16})
public final class WebSocketWriter
implements Closeable {
    private final boolean isClient;
    private final Buffer.UnsafeCursor maskCursor;
    private final byte[] maskKey;
    private final Buffer messageBuffer;
    private MessageDeflater messageDeflater;
    private final long minimumDeflateSize;
    private final boolean noContextTakeover;
    private final boolean perMessageDeflate;
    private final Random random;
    private final BufferedSink sink;
    private final Buffer sinkBuffer;
    private boolean writerClosed;

    public WebSocketWriter(boolean bl, BufferedSink object, Random random, boolean bl2, boolean bl3, long l) {
        Intrinsics.checkParameterIsNotNull(object, "sink");
        Intrinsics.checkParameterIsNotNull(random, "random");
        this.isClient = bl;
        this.sink = object;
        this.random = random;
        this.perMessageDeflate = bl2;
        this.noContextTakeover = bl3;
        this.minimumDeflateSize = l;
        this.messageBuffer = new Buffer();
        this.sinkBuffer = this.sink.getBuffer();
        bl = this.isClient;
        random = null;
        object = bl ? new byte[4] : null;
        this.maskKey = object;
        object = random;
        if (this.isClient) {
            object = new Buffer.UnsafeCursor();
        }
        this.maskCursor = object;
    }

    private final void writeControlFrame(int n, ByteString object) throws IOException {
        if (this.writerClosed) throw (Throwable)new IOException("closed");
        int n2 = ((ByteString)object).size();
        boolean bl = (long)n2 <= 125L;
        if (!bl) throw (Throwable)new IllegalArgumentException("Payload size must be less than or equal to 125".toString());
        this.sinkBuffer.writeByte(n | 128);
        if (this.isClient) {
            this.sinkBuffer.writeByte(n2 | 128);
            Object object2 = this.random;
            byte[] arrby = this.maskKey;
            if (arrby == null) {
                Intrinsics.throwNpe();
            }
            ((Random)object2).nextBytes(arrby);
            this.sinkBuffer.write(this.maskKey);
            if (n2 > 0) {
                long l = this.sinkBuffer.size();
                this.sinkBuffer.write((ByteString)object);
                object = this.sinkBuffer;
                object2 = this.maskCursor;
                if (object2 == null) {
                    Intrinsics.throwNpe();
                }
                ((Buffer)object).readAndWriteUnsafe((Buffer.UnsafeCursor)object2);
                this.maskCursor.seek(l);
                WebSocketProtocol.INSTANCE.toggleMask(this.maskCursor, this.maskKey);
                this.maskCursor.close();
            }
        } else {
            this.sinkBuffer.writeByte(n2);
            this.sinkBuffer.write((ByteString)object);
        }
        this.sink.flush();
    }

    @Override
    public void close() {
        MessageDeflater messageDeflater = this.messageDeflater;
        if (messageDeflater == null) return;
        messageDeflater.close();
    }

    public final Random getRandom() {
        return this.random;
    }

    public final BufferedSink getSink() {
        return this.sink;
    }

    public final void writeClose(int n, ByteString byteString) throws IOException {
        Object object = ByteString.EMPTY;
        if (n != 0 || byteString != null) {
            if (n != 0) {
                WebSocketProtocol.INSTANCE.validateCloseCode(n);
            }
            object = new Buffer();
            ((Buffer)object).writeShort(n);
            if (byteString != null) {
                ((Buffer)object).write(byteString);
            }
            object = ((Buffer)object).readByteString();
        }
        try {
            this.writeControlFrame(8, (ByteString)object);
            return;
        }
        finally {
            this.writerClosed = true;
        }
    }

    public final void writeMessageFrame(int n, ByteString object) throws IOException {
        int n2;
        Intrinsics.checkParameterIsNotNull(object, "data");
        if (this.writerClosed) throw (Throwable)new IOException("closed");
        this.messageBuffer.write((ByteString)object);
        int n3 = 128;
        n = n2 = n | 128;
        if (this.perMessageDeflate) {
            n = n2;
            if ((long)((ByteString)object).size() >= this.minimumDeflateSize) {
                object = this.messageDeflater;
                if (object == null) {
                    this.messageDeflater = object = new MessageDeflater(this.noContextTakeover);
                }
                ((MessageDeflater)object).deflate(this.messageBuffer);
                n = n2 | 64;
            }
        }
        long l = this.messageBuffer.size();
        this.sinkBuffer.writeByte(n);
        n = this.isClient ? n3 : 0;
        if (l <= 125L) {
            n3 = (int)l;
            this.sinkBuffer.writeByte(n3 | n);
        } else if (l <= 65535L) {
            this.sinkBuffer.writeByte(n | 126);
            this.sinkBuffer.writeShort((int)l);
        } else {
            this.sinkBuffer.writeByte(n | 127);
            this.sinkBuffer.writeLong(l);
        }
        if (this.isClient) {
            object = this.random;
            Object object2 = this.maskKey;
            if (object2 == null) {
                Intrinsics.throwNpe();
            }
            ((Random)object).nextBytes((byte[])object2);
            this.sinkBuffer.write(this.maskKey);
            if (l > 0L) {
                object = this.messageBuffer;
                object2 = this.maskCursor;
                if (object2 == null) {
                    Intrinsics.throwNpe();
                }
                ((Buffer)object).readAndWriteUnsafe((Buffer.UnsafeCursor)object2);
                this.maskCursor.seek(0L);
                WebSocketProtocol.INSTANCE.toggleMask(this.maskCursor, this.maskKey);
                this.maskCursor.close();
            }
        }
        this.sinkBuffer.write(this.messageBuffer, l);
        this.sink.emit();
    }

    public final void writePing(ByteString byteString) throws IOException {
        Intrinsics.checkParameterIsNotNull(byteString, "payload");
        this.writeControlFrame(9, byteString);
    }

    public final void writePong(ByteString byteString) throws IOException {
        Intrinsics.checkParameterIsNotNull(byteString, "payload");
        this.writeControlFrame(10, byteString);
    }
}

