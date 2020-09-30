/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.entity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import org.apache.http.entity.AbstractHttpEntity;

public class SerializableEntity
extends AbstractHttpEntity {
    private Serializable objRef;
    private byte[] objSer;

    public SerializableEntity(Serializable serializable, boolean bl) throws IOException {
        if (serializable == null) throw new IllegalArgumentException("Source object may not be null");
        if (bl) {
            this.createBytes(serializable);
            return;
        }
        this.objRef = serializable;
    }

    private void createBytes(Serializable serializable) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(serializable);
        objectOutputStream.flush();
        this.objSer = byteArrayOutputStream.toByteArray();
    }

    @Override
    public InputStream getContent() throws IOException, IllegalStateException {
        if (this.objSer != null) return new ByteArrayInputStream(this.objSer);
        this.createBytes(this.objRef);
        return new ByteArrayInputStream(this.objSer);
    }

    @Override
    public long getContentLength() {
        byte[] arrby = this.objSer;
        if (arrby != null) return arrby.length;
        return -1L;
    }

    @Override
    public boolean isRepeatable() {
        return true;
    }

    @Override
    public boolean isStreaming() {
        if (this.objSer != null) return false;
        return true;
    }

    @Override
    public void writeTo(OutputStream outputStream2) throws IOException {
        if (outputStream2 == null) throw new IllegalArgumentException("Output stream may not be null");
        byte[] arrby = this.objSer;
        if (arrby == null) {
            outputStream2 = new ObjectOutputStream(outputStream2);
            ((ObjectOutputStream)outputStream2).writeObject(this.objRef);
            ((ObjectOutputStream)outputStream2).flush();
            return;
        }
        outputStream2.write(arrby);
        outputStream2.flush();
    }
}

