/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.harmony.awt.datatransfer;

import java.awt.Image;
import java.awt.color.ColorSpace;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.SystemFlavorMap;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.DataBufferUShort;
import java.awt.image.DirectColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import org.apache.harmony.awt.datatransfer.DataProvider;
import org.apache.harmony.awt.datatransfer.RawBitmap;
import org.apache.harmony.awt.internal.nls.Messages;

public final class DataProxy
implements Transferable {
    public static final Class[] charsetTextClasses;
    public static final Class[] unicodeTextClasses;
    private final DataProvider data;
    private final SystemFlavorMap flavorMap;

    static {
        unicodeTextClasses = new Class[]{String.class, Reader.class, CharBuffer.class, char[].class};
        charsetTextClasses = new Class[]{byte[].class, ByteBuffer.class, InputStream.class};
    }

    public DataProxy(DataProvider dataProvider) {
        this.data = dataProvider;
        this.flavorMap = (SystemFlavorMap)SystemFlavorMap.getDefaultFlavorMap();
    }

    private BufferedImage createBufferedImage(RawBitmap object) {
        Object object2;
        if (object == null) return null;
        if (((RawBitmap)object).buffer == null) return null;
        if (((RawBitmap)object).width <= 0) return null;
        if (((RawBitmap)object).height <= 0) {
            return null;
        }
        if (((RawBitmap)object).bits == 32 && ((RawBitmap)object).buffer instanceof int[]) {
            if (!this.isRGB((RawBitmap)object) && !this.isBGR((RawBitmap)object)) {
                return null;
            }
            int n = ((RawBitmap)object).rMask;
            int n2 = ((RawBitmap)object).gMask;
            int n3 = ((RawBitmap)object).bMask;
            int[] arrn = (int[])((RawBitmap)object).buffer;
            object2 = new DirectColorModel(24, ((RawBitmap)object).rMask, ((RawBitmap)object).gMask, ((RawBitmap)object).bMask);
            object = Raster.createPackedRaster(new DataBufferInt(arrn, arrn.length), ((RawBitmap)object).width, ((RawBitmap)object).height, ((RawBitmap)object).stride, new int[]{n, n2, n3}, null);
        } else if (((RawBitmap)object).bits == 24 && ((RawBitmap)object).buffer instanceof byte[]) {
            if (this.isRGB((RawBitmap)object)) {
                object2 = new int[3];
                object2[1] = 1;
                object2[2] = 2;
            } else {
                if (!this.isBGR((RawBitmap)object)) return null;
                object2 = new int[3];
                object2[0] = 2;
                object2[1] = 1;
            }
            byte[] arrby = (byte[])((RawBitmap)object).buffer;
            ComponentColorModel componentColorModel = new ComponentColorModel(ColorSpace.getInstance(1000), new int[]{8, 8, 8}, false, false, 1, 0);
            object = Raster.createInterleavedRaster(new DataBufferByte(arrby, arrby.length), ((RawBitmap)object).width, ((RawBitmap)object).height, ((RawBitmap)object).stride, 3, object2, null);
            object2 = componentColorModel;
        } else if ((((RawBitmap)object).bits == 16 || ((RawBitmap)object).bits == 15) && ((RawBitmap)object).buffer instanceof short[]) {
            int n = ((RawBitmap)object).rMask;
            int n4 = ((RawBitmap)object).gMask;
            int n5 = ((RawBitmap)object).bMask;
            short[] arrs = (short[])((RawBitmap)object).buffer;
            object2 = new DirectColorModel(((RawBitmap)object).bits, ((RawBitmap)object).rMask, ((RawBitmap)object).gMask, ((RawBitmap)object).bMask);
            object = Raster.createPackedRaster(new DataBufferUShort(arrs, arrs.length), ((RawBitmap)object).width, ((RawBitmap)object).height, ((RawBitmap)object).stride, new int[]{n, n4, n5}, null);
        } else {
            object = null;
            object2 = object;
        }
        if (object2 == null) return null;
        if (object != null) return new BufferedImage((ColorModel)object2, (WritableRaster)object, false, null);
        return null;
    }

    private String getCharset(DataFlavor dataFlavor) {
        return dataFlavor.getParameter("charset");
    }

    private Object getFileList(DataFlavor arrstring) throws IOException, UnsupportedFlavorException {
        if (!this.data.isNativeFormatAvailable("application/x-java-file-list")) throw new UnsupportedFlavorException((DataFlavor)arrstring);
        arrstring = this.data.getFileList();
        if (arrstring == null) throw new IOException(Messages.getString("awt.4F"));
        return Arrays.asList(arrstring);
    }

    private Object getHTML(DataFlavor dataFlavor) throws IOException, UnsupportedFlavorException {
        if (!this.data.isNativeFormatAvailable("text/html")) throw new UnsupportedFlavorException(dataFlavor);
        String string2 = this.data.getHTML();
        if (string2 == null) throw new IOException(Messages.getString("awt.4F"));
        return this.getTextRepresentation(string2, dataFlavor);
    }

    private Image getImage(DataFlavor object) throws IOException, UnsupportedFlavorException {
        if (!this.data.isNativeFormatAvailable("image/x-java-image")) throw new UnsupportedFlavorException((DataFlavor)object);
        object = this.data.getRawBitmap();
        if (object == null) throw new IOException(Messages.getString("awt.4F"));
        return this.createBufferedImage((RawBitmap)object);
    }

    private Object getPlainText(DataFlavor dataFlavor) throws IOException, UnsupportedFlavorException {
        if (!this.data.isNativeFormatAvailable("text/plain")) throw new UnsupportedFlavorException(dataFlavor);
        String string2 = this.data.getText();
        if (string2 == null) throw new IOException(Messages.getString("awt.4F"));
        return this.getTextRepresentation(string2, dataFlavor);
    }

    private Object getSerializedObject(DataFlavor object) throws IOException, UnsupportedFlavorException {
        Object object2 = SystemFlavorMap.encodeDataFlavor((DataFlavor)object);
        if (object2 == null) throw new UnsupportedFlavorException((DataFlavor)object);
        if (!this.data.isNativeFormatAvailable((String)object2)) throw new UnsupportedFlavorException((DataFlavor)object);
        if ((object = this.data.getSerializedObject(object.getRepresentationClass())) == null) throw new IOException(Messages.getString("awt.4F"));
        object = new ByteArrayInputStream((byte[])object);
        try {
            object2 = new ObjectInputStream((InputStream)object);
            return ((ObjectInputStream)object2).readObject();
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new IOException(classNotFoundException.getMessage());
        }
    }

    private Object getTextRepresentation(String string2, DataFlavor arrc) throws UnsupportedFlavorException, IOException {
        if (arrc.getRepresentationClass() == String.class) {
            return string2;
        }
        if (arrc.isRepresentationClassReader()) {
            return new StringReader(string2);
        }
        if (arrc.isRepresentationClassCharBuffer()) {
            return CharBuffer.wrap(string2);
        }
        if (arrc.getRepresentationClass() == char[].class) {
            arrc = new char[string2.length()];
            string2.getChars(0, string2.length(), arrc, 0);
            return arrc;
        }
        String string3 = this.getCharset((DataFlavor)arrc);
        if (arrc.getRepresentationClass() == byte[].class) {
            return string2.getBytes(string3);
        }
        if (arrc.isRepresentationClassByteBuffer()) {
            return ByteBuffer.wrap(string2.getBytes(string3));
        }
        if (!arrc.isRepresentationClassInputStream()) throw new UnsupportedFlavorException((DataFlavor)arrc);
        return new ByteArrayInputStream(string2.getBytes(string3));
    }

    private Object getURL(DataFlavor dataFlavor) throws IOException, UnsupportedFlavorException {
        if (!this.data.isNativeFormatAvailable("application/x-java-url")) throw new UnsupportedFlavorException(dataFlavor);
        Object object = this.data.getURL();
        if (object == null) throw new IOException(Messages.getString("awt.4F"));
        object = new URL((String)object);
        if (dataFlavor.getRepresentationClass().isAssignableFrom(URL.class)) {
            return object;
        }
        if (!dataFlavor.isFlavorTextType()) throw new UnsupportedFlavorException(dataFlavor);
        return this.getTextRepresentation(((URL)object).toString(), dataFlavor);
    }

    private boolean isBGR(RawBitmap rawBitmap) {
        if (rawBitmap.rMask != 255) return false;
        if (rawBitmap.gMask != 65280) return false;
        if (rawBitmap.bMask != 16711680) return false;
        return true;
    }

    private boolean isRGB(RawBitmap rawBitmap) {
        if (rawBitmap.rMask != 16711680) return false;
        if (rawBitmap.gMask != 65280) return false;
        if (rawBitmap.bMask != 255) return false;
        return true;
    }

    public DataProvider getDataProvider() {
        return this.data;
    }

    @Override
    public Object getTransferData(DataFlavor dataFlavor) throws UnsupportedFlavorException, IOException {
        CharSequence charSequence = new StringBuilder(String.valueOf(dataFlavor.getPrimaryType()));
        ((StringBuilder)charSequence).append("/");
        ((StringBuilder)charSequence).append(dataFlavor.getSubType());
        charSequence = ((StringBuilder)charSequence).toString();
        if (dataFlavor.isFlavorTextType()) {
            if (((String)charSequence).equalsIgnoreCase("text/html")) {
                return this.getHTML(dataFlavor);
            }
            if (!((String)charSequence).equalsIgnoreCase("text/uri-list")) return this.getPlainText(dataFlavor);
            return this.getURL(dataFlavor);
        }
        if (dataFlavor.isFlavorJavaFileListType()) {
            return this.getFileList(dataFlavor);
        }
        if (dataFlavor.isFlavorSerializedObjectType()) {
            return this.getSerializedObject(dataFlavor);
        }
        if (dataFlavor.equals(DataProvider.urlFlavor)) {
            return this.getURL(dataFlavor);
        }
        if (!((String)charSequence).equalsIgnoreCase("image/x-java-image")) throw new UnsupportedFlavorException(dataFlavor);
        if (!Image.class.isAssignableFrom(dataFlavor.getRepresentationClass())) throw new UnsupportedFlavorException(dataFlavor);
        return this.getImage(dataFlavor);
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        ArrayList<DataFlavor> arrayList = new ArrayList<DataFlavor>();
        String[] arrstring = this.data.getNativeFormats();
        int n = 0;
        block0 : while (n < arrstring.length) {
            Iterator<DataFlavor> iterator2 = this.flavorMap.getFlavorsForNative(arrstring[n]).iterator();
            do {
                if (!iterator2.hasNext()) {
                    ++n;
                    continue block0;
                }
                DataFlavor dataFlavor = iterator2.next();
                if (arrayList.contains(dataFlavor)) continue;
                arrayList.add(dataFlavor);
            } while (true);
            break;
        }
        return arrayList.toArray(new DataFlavor[arrayList.size()]);
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor dataFlavor) {
        DataFlavor[] arrdataFlavor = this.getTransferDataFlavors();
        int n = 0;
        while (n < arrdataFlavor.length) {
            if (arrdataFlavor[n].equals(dataFlavor)) {
                return true;
            }
            ++n;
        }
        return false;
    }
}

