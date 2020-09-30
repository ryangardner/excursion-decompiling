/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.common.hash.Funnel;
import com.google.common.hash.PrimitiveSink;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Funnels {
    private Funnels() {
    }

    public static OutputStream asOutputStream(PrimitiveSink primitiveSink) {
        return new SinkAsStream(primitiveSink);
    }

    public static Funnel<byte[]> byteArrayFunnel() {
        return ByteArrayFunnel.INSTANCE;
    }

    public static Funnel<Integer> integerFunnel() {
        return IntegerFunnel.INSTANCE;
    }

    public static Funnel<Long> longFunnel() {
        return LongFunnel.INSTANCE;
    }

    public static <E> Funnel<Iterable<? extends E>> sequentialFunnel(Funnel<E> funnel) {
        return new SequentialFunnel<E>(funnel);
    }

    public static Funnel<CharSequence> stringFunnel(Charset charset) {
        return new StringCharsetFunnel(charset);
    }

    public static Funnel<CharSequence> unencodedCharsFunnel() {
        return UnencodedCharsFunnel.INSTANCE;
    }

    private static final class ByteArrayFunnel
    extends Enum<ByteArrayFunnel>
    implements Funnel<byte[]> {
        private static final /* synthetic */ ByteArrayFunnel[] $VALUES;
        public static final /* enum */ ByteArrayFunnel INSTANCE;

        static {
            ByteArrayFunnel byteArrayFunnel;
            INSTANCE = byteArrayFunnel = new ByteArrayFunnel();
            $VALUES = new ByteArrayFunnel[]{byteArrayFunnel};
        }

        public static ByteArrayFunnel valueOf(String string2) {
            return Enum.valueOf(ByteArrayFunnel.class, string2);
        }

        public static ByteArrayFunnel[] values() {
            return (ByteArrayFunnel[])$VALUES.clone();
        }

        @Override
        public void funnel(byte[] arrby, PrimitiveSink primitiveSink) {
            primitiveSink.putBytes(arrby);
        }

        public String toString() {
            return "Funnels.byteArrayFunnel()";
        }
    }

    private static final class IntegerFunnel
    extends Enum<IntegerFunnel>
    implements Funnel<Integer> {
        private static final /* synthetic */ IntegerFunnel[] $VALUES;
        public static final /* enum */ IntegerFunnel INSTANCE;

        static {
            IntegerFunnel integerFunnel;
            INSTANCE = integerFunnel = new IntegerFunnel();
            $VALUES = new IntegerFunnel[]{integerFunnel};
        }

        public static IntegerFunnel valueOf(String string2) {
            return Enum.valueOf(IntegerFunnel.class, string2);
        }

        public static IntegerFunnel[] values() {
            return (IntegerFunnel[])$VALUES.clone();
        }

        @Override
        public void funnel(Integer n, PrimitiveSink primitiveSink) {
            primitiveSink.putInt(n);
        }

        public String toString() {
            return "Funnels.integerFunnel()";
        }
    }

    private static final class LongFunnel
    extends Enum<LongFunnel>
    implements Funnel<Long> {
        private static final /* synthetic */ LongFunnel[] $VALUES;
        public static final /* enum */ LongFunnel INSTANCE;

        static {
            LongFunnel longFunnel;
            INSTANCE = longFunnel = new LongFunnel();
            $VALUES = new LongFunnel[]{longFunnel};
        }

        public static LongFunnel valueOf(String string2) {
            return Enum.valueOf(LongFunnel.class, string2);
        }

        public static LongFunnel[] values() {
            return (LongFunnel[])$VALUES.clone();
        }

        @Override
        public void funnel(Long l, PrimitiveSink primitiveSink) {
            primitiveSink.putLong(l);
        }

        public String toString() {
            return "Funnels.longFunnel()";
        }
    }

    private static class SequentialFunnel<E>
    implements Funnel<Iterable<? extends E>>,
    Serializable {
        private final Funnel<E> elementFunnel;

        SequentialFunnel(Funnel<E> funnel) {
            this.elementFunnel = Preconditions.checkNotNull(funnel);
        }

        public boolean equals(@NullableDecl Object object) {
            if (!(object instanceof SequentialFunnel)) return false;
            object = (SequentialFunnel)object;
            return this.elementFunnel.equals(((SequentialFunnel)object).elementFunnel);
        }

        @Override
        public void funnel(Iterable<? extends E> object, PrimitiveSink primitiveSink) {
            object = object.iterator();
            while (object.hasNext()) {
                Object e = object.next();
                this.elementFunnel.funnel(e, primitiveSink);
            }
        }

        public int hashCode() {
            return SequentialFunnel.class.hashCode() ^ this.elementFunnel.hashCode();
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Funnels.sequentialFunnel(");
            stringBuilder.append(this.elementFunnel);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    private static class SinkAsStream
    extends OutputStream {
        final PrimitiveSink sink;

        SinkAsStream(PrimitiveSink primitiveSink) {
            this.sink = Preconditions.checkNotNull(primitiveSink);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Funnels.asOutputStream(");
            stringBuilder.append(this.sink);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }

        @Override
        public void write(int n) {
            this.sink.putByte((byte)n);
        }

        @Override
        public void write(byte[] arrby) {
            this.sink.putBytes(arrby);
        }

        @Override
        public void write(byte[] arrby, int n, int n2) {
            this.sink.putBytes(arrby, n, n2);
        }
    }

    private static class StringCharsetFunnel
    implements Funnel<CharSequence>,
    Serializable {
        private final Charset charset;

        StringCharsetFunnel(Charset charset) {
            this.charset = Preconditions.checkNotNull(charset);
        }

        public boolean equals(@NullableDecl Object object) {
            if (!(object instanceof StringCharsetFunnel)) return false;
            object = (StringCharsetFunnel)object;
            return this.charset.equals(((StringCharsetFunnel)object).charset);
        }

        @Override
        public void funnel(CharSequence charSequence, PrimitiveSink primitiveSink) {
            primitiveSink.putString(charSequence, this.charset);
        }

        public int hashCode() {
            return StringCharsetFunnel.class.hashCode() ^ this.charset.hashCode();
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Funnels.stringFunnel(");
            stringBuilder.append(this.charset.name());
            stringBuilder.append(")");
            return stringBuilder.toString();
        }

        Object writeReplace() {
            return new SerializedForm(this.charset);
        }

        private static class SerializedForm
        implements Serializable {
            private static final long serialVersionUID = 0L;
            private final String charsetCanonicalName;

            SerializedForm(Charset charset) {
                this.charsetCanonicalName = charset.name();
            }

            private Object readResolve() {
                return Funnels.stringFunnel(Charset.forName(this.charsetCanonicalName));
            }
        }

    }

    private static final class UnencodedCharsFunnel
    extends Enum<UnencodedCharsFunnel>
    implements Funnel<CharSequence> {
        private static final /* synthetic */ UnencodedCharsFunnel[] $VALUES;
        public static final /* enum */ UnencodedCharsFunnel INSTANCE;

        static {
            UnencodedCharsFunnel unencodedCharsFunnel;
            INSTANCE = unencodedCharsFunnel = new UnencodedCharsFunnel();
            $VALUES = new UnencodedCharsFunnel[]{unencodedCharsFunnel};
        }

        public static UnencodedCharsFunnel valueOf(String string2) {
            return Enum.valueOf(UnencodedCharsFunnel.class, string2);
        }

        public static UnencodedCharsFunnel[] values() {
            return (UnencodedCharsFunnel[])$VALUES.clone();
        }

        @Override
        public void funnel(CharSequence charSequence, PrimitiveSink primitiveSink) {
            primitiveSink.putUnencodedChars(charSequence);
        }

        public String toString() {
            return "Funnels.unencodedCharsFunnel()";
        }
    }

}

