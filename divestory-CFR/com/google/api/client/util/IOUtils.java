/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.util;

import com.google.api.client.util.ByteCountingOutputStream;
import com.google.api.client.util.ByteStreams;
import com.google.api.client.util.StreamingContent;
import com.google.api.client.util.Throwables;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class IOUtils {
    public static long computeLength(StreamingContent streamingContent) throws IOException {
        ByteCountingOutputStream byteCountingOutputStream = new ByteCountingOutputStream();
        try {
            streamingContent.writeTo(byteCountingOutputStream);
            return byteCountingOutputStream.count;
        }
        finally {
            byteCountingOutputStream.close();
        }
    }

    public static void copy(InputStream inputStream2, OutputStream outputStream2) throws IOException {
        IOUtils.copy(inputStream2, outputStream2, true);
    }

    public static void copy(InputStream inputStream2, OutputStream outputStream2, boolean bl) throws IOException {
        try {
            ByteStreams.copy(inputStream2, outputStream2);
            return;
        }
        finally {
            if (bl) {
                inputStream2.close();
            }
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    public static <S extends Serializable> S deserialize(InputStream inputStream2) throws IOException {
        Throwable throwable2222;
        Object object = new ObjectInputStream(inputStream2);
        object = (Serializable)((ObjectInputStream)object).readObject();
        inputStream2.close();
        return (S)object;
        {
            catch (Throwable throwable2222) {
            }
            catch (ClassNotFoundException classNotFoundException) {}
            {
                IOException iOException = new IOException("Failed to deserialize object");
                iOException.initCause(classNotFoundException);
                throw iOException;
            }
        }
        inputStream2.close();
        throw throwable2222;
    }

    public static <S extends Serializable> S deserialize(byte[] arrby) throws IOException {
        if (arrby != null) return IOUtils.deserialize(new ByteArrayInputStream(arrby));
        return null;
    }

    public static boolean isSymbolicLink(File file) throws IOException {
        try {
            Class<?> class_ = Class.forName("java.nio.file.Files");
            Class<?> class_2 = Class.forName("java.nio.file.Path");
            Object object = File.class.getMethod("toPath", new Class[0]).invoke(file, new Object[0]);
            return (Boolean)class_.getMethod("isSymbolicLink", class_2).invoke(null, object);
        }
        catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException exception) {
            if (File.separatorChar == '\\') {
                return false;
            }
            File file2 = file;
            if (file.getParent() == null) return file2.getCanonicalFile().equals(file2.getAbsoluteFile()) ^ true;
            file2 = new File(file.getParentFile().getCanonicalFile(), file.getName());
            return file2.getCanonicalFile().equals(file2.getAbsoluteFile()) ^ true;
        }
        catch (InvocationTargetException invocationTargetException) {
            Throwable throwable = invocationTargetException.getCause();
            Throwables.propagateIfPossible(throwable, IOException.class);
            throw new RuntimeException(throwable);
        }
    }

    public static void serialize(Object object, OutputStream outputStream2) throws IOException {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream2);
            objectOutputStream.writeObject(object);
            return;
        }
        finally {
            outputStream2.close();
        }
    }

    public static byte[] serialize(Object object) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        IOUtils.serialize(object, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}

