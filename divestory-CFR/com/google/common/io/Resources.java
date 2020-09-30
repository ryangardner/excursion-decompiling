/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.io;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.io.ByteSource;
import com.google.common.io.CharSource;
import com.google.common.io.LineProcessor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

public final class Resources {
    private Resources() {
    }

    public static ByteSource asByteSource(URL uRL) {
        return new UrlByteSource(uRL);
    }

    public static CharSource asCharSource(URL uRL, Charset charset) {
        return Resources.asByteSource(uRL).asCharSource(charset);
    }

    public static void copy(URL uRL, OutputStream outputStream2) throws IOException {
        Resources.asByteSource(uRL).copyTo(outputStream2);
    }

    public static URL getResource(Class<?> class_, String string2) {
        URL uRL = class_.getResource(string2);
        boolean bl = uRL != null;
        Preconditions.checkArgument(bl, "resource %s relative to %s not found.", (Object)string2, (Object)class_.getName());
        return uRL;
    }

    public static URL getResource(String string2) {
        URL uRL = MoreObjects.firstNonNull(Thread.currentThread().getContextClassLoader(), Resources.class.getClassLoader()).getResource(string2);
        boolean bl = uRL != null;
        Preconditions.checkArgument(bl, "resource %s not found.", (Object)string2);
        return uRL;
    }

    public static <T> T readLines(URL uRL, Charset charset, LineProcessor<T> lineProcessor) throws IOException {
        return Resources.asCharSource(uRL, charset).readLines(lineProcessor);
    }

    public static List<String> readLines(URL uRL, Charset charset) throws IOException {
        return Resources.readLines(uRL, charset, new LineProcessor<List<String>>(){
            final List<String> result = Lists.newArrayList();

            @Override
            public List<String> getResult() {
                return this.result;
            }

            @Override
            public boolean processLine(String string2) {
                this.result.add(string2);
                return true;
            }
        });
    }

    public static byte[] toByteArray(URL uRL) throws IOException {
        return Resources.asByteSource(uRL).read();
    }

    public static String toString(URL uRL, Charset charset) throws IOException {
        return Resources.asCharSource(uRL, charset).read();
    }

    private static final class UrlByteSource
    extends ByteSource {
        private final URL url;

        private UrlByteSource(URL uRL) {
            this.url = Preconditions.checkNotNull(uRL);
        }

        @Override
        public InputStream openStream() throws IOException {
            return this.url.openStream();
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Resources.asByteSource(");
            stringBuilder.append(this.url);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

}

