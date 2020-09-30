/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3.internal.ws;

import java.io.Closeable;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.Util;
import okhttp3.internal.ws.MessageInflater;
import okhttp3.internal.ws.WebSocketProtocol;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Timeout;

@Metadata(bv={1, 0, 3}, d1={"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001:\u0001&B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\nJ\b\u0010\u001e\u001a\u00020\u001fH\u0016J\u0006\u0010 \u001a\u00020\u001fJ\b\u0010!\u001a\u00020\u001fH\u0002J\b\u0010\"\u001a\u00020\u001fH\u0002J\b\u0010#\u001a\u00020\u001fH\u0002J\b\u0010$\u001a\u00020\u001fH\u0002J\b\u0010%\u001a\u00020\u001fH\u0002R\u000e\u0010\u000b\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001d\u00a8\u0006'"}, d2={"Lokhttp3/internal/ws/WebSocketReader;", "Ljava/io/Closeable;", "isClient", "", "source", "Lokio/BufferedSource;", "frameCallback", "Lokhttp3/internal/ws/WebSocketReader$FrameCallback;", "perMessageDeflate", "noContextTakeover", "(ZLokio/BufferedSource;Lokhttp3/internal/ws/WebSocketReader$FrameCallback;ZZ)V", "closed", "controlFrameBuffer", "Lokio/Buffer;", "frameLength", "", "isControlFrame", "isFinalFrame", "maskCursor", "Lokio/Buffer$UnsafeCursor;", "maskKey", "", "messageFrameBuffer", "messageInflater", "Lokhttp3/internal/ws/MessageInflater;", "opcode", "", "readingCompressedMessage", "getSource", "()Lokio/BufferedSource;", "close", "", "processNextFrame", "readControlFrame", "readHeader", "readMessage", "readMessageFrame", "readUntilNonControlFrame", "FrameCallback", "okhttp"}, k=1, mv={1, 1, 16})
public final class WebSocketReader
implements Closeable {
    private boolean closed;
    private final Buffer controlFrameBuffer;
    private final FrameCallback frameCallback;
    private long frameLength;
    private final boolean isClient;
    private boolean isControlFrame;
    private boolean isFinalFrame;
    private final Buffer.UnsafeCursor maskCursor;
    private final byte[] maskKey;
    private final Buffer messageFrameBuffer;
    private MessageInflater messageInflater;
    private final boolean noContextTakeover;
    private int opcode;
    private final boolean perMessageDeflate;
    private boolean readingCompressedMessage;
    private final BufferedSource source;

    public WebSocketReader(boolean bl, BufferedSource object, FrameCallback frameCallback, boolean bl2, boolean bl3) {
        Intrinsics.checkParameterIsNotNull(object, "source");
        Intrinsics.checkParameterIsNotNull(frameCallback, "frameCallback");
        this.isClient = bl;
        this.source = object;
        this.frameCallback = frameCallback;
        this.perMessageDeflate = bl2;
        this.noContextTakeover = bl3;
        this.controlFrameBuffer = new Buffer();
        this.messageFrameBuffer = new Buffer();
        bl = this.isClient;
        frameCallback = null;
        object = bl ? null : new byte[4];
        this.maskKey = object;
        object = this.isClient ? frameCallback : new Buffer.UnsafeCursor();
        this.maskCursor = object;
    }

    private final void readControlFrame() throws IOException {
        Object object;
        Object object2;
        long l = this.frameLength;
        if (l > 0L) {
            this.source.readFully(this.controlFrameBuffer, l);
            if (!this.isClient) {
                object2 = this.controlFrameBuffer;
                object = this.maskCursor;
                if (object == null) {
                    Intrinsics.throwNpe();
                }
                ((Buffer)object2).readAndWriteUnsafe((Buffer.UnsafeCursor)object);
                this.maskCursor.seek(0L);
                object = WebSocketProtocol.INSTANCE;
                object2 = this.maskCursor;
                byte[] arrby = this.maskKey;
                if (arrby == null) {
                    Intrinsics.throwNpe();
                }
                ((WebSocketProtocol)object).toggleMask((Buffer.UnsafeCursor)object2, arrby);
                this.maskCursor.close();
            }
        }
        switch (this.opcode) {
            default: {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Unknown control opcode: ");
                ((StringBuilder)object2).append(Util.toHexString(this.opcode));
                throw (Throwable)new ProtocolException(((StringBuilder)object2).toString());
            }
            case 10: {
                this.frameCallback.onReadPong(this.controlFrameBuffer.readByteString());
                return;
            }
            case 9: {
                this.frameCallback.onReadPing(this.controlFrameBuffer.readByteString());
                return;
            }
            case 8: 
        }
        int n = 1005;
        l = this.controlFrameBuffer.size();
        if (l == 1L) throw (Throwable)new ProtocolException("Malformed close payload length of 1.");
        if (l != 0L) {
            n = this.controlFrameBuffer.readShort();
            object2 = this.controlFrameBuffer.readUtf8();
            object = WebSocketProtocol.INSTANCE.closeCodeExceptionMessage(n);
            if (object != null) throw (Throwable)new ProtocolException((String)object);
        } else {
            object2 = "";
        }
        this.frameCallback.onReadClose(n, (String)object2);
        this.closed = true;
    }

    private final void readHeader() throws IOException, ProtocolException {
        boolean bl;
        boolean bl2;
        int n;
        int n2;
        long l;
        block16 : {
            block15 : {
                if (this.closed) throw (Throwable)new IOException("closed");
                l = this.source.timeout().timeoutNanos();
                this.source.timeout().clearTimeout();
                n = Util.and(this.source.readByte(), 255);
                this.opcode = n & 15;
                bl = false;
                bl2 = (n & 128) != 0;
                this.isFinalFrame = bl2;
                bl2 = (n & 8) != 0;
                this.isControlFrame = bl2;
                if (bl2) {
                    if (!this.isFinalFrame) throw (Throwable)new ProtocolException("Control frames must be final.");
                }
                n2 = (n & 64) != 0 ? 1 : 0;
                int n3 = this.opcode;
                if (n3 == 1 || n3 == 2) break block15;
                if (n2 != 0) throw (Throwable)new ProtocolException("Unexpected rsv1 flag");
                break block16;
            }
            if (n2 != 0) {
                if (!this.perMessageDeflate) throw (Throwable)new ProtocolException("Unexpected rsv1 flag");
                this.readingCompressedMessage = true;
            } else {
                this.readingCompressedMessage = false;
            }
        }
        n2 = (n & 32) != 0 ? 1 : 0;
        if (n2 != 0) throw (Throwable)new ProtocolException("Unexpected rsv2 flag");
        n2 = (n & 16) != 0 ? 1 : 0;
        if (n2 != 0) throw (Throwable)new ProtocolException("Unexpected rsv3 flag");
        n2 = Util.and(this.source.readByte(), 255);
        bl2 = bl;
        if ((n2 & 128) != 0) {
            bl2 = true;
        }
        if (bl2 == this.isClient) {
            String string2;
            if (this.isClient) {
                string2 = "Server-sent frames must not be masked.";
                throw (Throwable)new ProtocolException(string2);
            }
            string2 = "Client-sent frames must be masked.";
            throw (Throwable)new ProtocolException(string2);
        }
        this.frameLength = l = (long)(n2 & 127);
        if (l == (long)126) {
            this.frameLength = Util.and(this.source.readShort(), 65535);
        } else if (l == (long)127) {
            this.frameLength = l = this.source.readLong();
            if (l < 0L) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Frame length 0x");
                stringBuilder.append(Util.toHexString(this.frameLength));
                stringBuilder.append(" > 0x7FFFFFFFFFFFFFFF");
                throw (Throwable)new ProtocolException(stringBuilder.toString());
            }
        }
        if (this.isControlFrame) {
            if (this.frameLength > 125L) throw (Throwable)new ProtocolException("Control frame must be less than 125B.");
        }
        if (!bl2) return;
        BufferedSource bufferedSource = this.source;
        byte[] arrby = this.maskKey;
        if (arrby == null) {
            Intrinsics.throwNpe();
        }
        bufferedSource.readFully(arrby);
        return;
        finally {
            this.source.timeout().timeout(l, TimeUnit.NANOSECONDS);
        }
    }

    private final void readMessage() throws IOException {
        Object object;
        do {
            if (this.closed) throw (Throwable)new IOException("closed");
            long l = this.frameLength;
            if (l > 0L) {
                this.source.readFully(this.messageFrameBuffer, l);
                if (!this.isClient) {
                    object = this.messageFrameBuffer;
                    Buffer.UnsafeCursor unsafeCursor = this.maskCursor;
                    if (unsafeCursor == null) {
                        Intrinsics.throwNpe();
                    }
                    ((Buffer)object).readAndWriteUnsafe(unsafeCursor);
                    this.maskCursor.seek(this.messageFrameBuffer.size() - this.frameLength);
                    object = WebSocketProtocol.INSTANCE;
                    unsafeCursor = this.maskCursor;
                    byte[] arrby = this.maskKey;
                    if (arrby == null) {
                        Intrinsics.throwNpe();
                    }
                    ((WebSocketProtocol)object).toggleMask(unsafeCursor, arrby);
                    this.maskCursor.close();
                }
            }
            if (this.isFinalFrame) {
                return;
            }
            this.readUntilNonControlFrame();
        } while (this.opcode == 0);
        object = new StringBuilder();
        ((StringBuilder)object).append("Expected continuation opcode. Got: ");
        ((StringBuilder)object).append(Util.toHexString(this.opcode));
        throw (Throwable)new ProtocolException(((StringBuilder)object).toString());
    }

    private final void readMessageFrame() throws IOException {
        int n = this.opcode;
        if (n != 1 && n != 2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown opcode: ");
            stringBuilder.append(Util.toHexString(n));
            throw (Throwable)new ProtocolException(stringBuilder.toString());
        }
        this.readMessage();
        if (this.readingCompressedMessage) {
            MessageInflater messageInflater = this.messageInflater;
            if (messageInflater == null) {
                this.messageInflater = messageInflater = new MessageInflater(this.noContextTakeover);
            }
            messageInflater.inflate(this.messageFrameBuffer);
        }
        if (n == 1) {
            this.frameCallback.onReadMessage(this.messageFrameBuffer.readUtf8());
            return;
        }
        this.frameCallback.onReadMessage(this.messageFrameBuffer.readByteString());
    }

    private final void readUntilNonControlFrame() throws IOException {
        while (!this.closed) {
            this.readHeader();
            if (!this.isControlFrame) {
                return;
            }
            this.readControlFrame();
        }
    }

    @Override
    public void close() throws IOException {
        MessageInflater messageInflater = this.messageInflater;
        if (messageInflater == null) return;
        messageInflater.close();
    }

    public final BufferedSource getSource() {
        return this.source;
    }

    public final void processNextFrame() throws IOException {
        this.readHeader();
        if (this.isControlFrame) {
            this.readControlFrame();
            return;
        }
        this.readMessageFrame();
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0010\u0010\b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u0007H&J\u0010\u0010\b\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH&J\u0010\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000bH&J\u0010\u0010\u000e\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000bH&\u00a8\u0006\u000f"}, d2={"Lokhttp3/internal/ws/WebSocketReader$FrameCallback;", "", "onReadClose", "", "code", "", "reason", "", "onReadMessage", "text", "bytes", "Lokio/ByteString;", "onReadPing", "payload", "onReadPong", "okhttp"}, k=1, mv={1, 1, 16})
    public static interface FrameCallback {
        public void onReadClose(int var1, String var2);

        public void onReadMessage(String var1) throws IOException;

        public void onReadMessage(ByteString var1) throws IOException;

        public void onReadPing(ByteString var1);

        public void onReadPong(ByteString var1);
    }

}

