/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.harmony.awt.datatransfer;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.SystemFlavorMap;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.awt.image.ImageObserver;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.harmony.awt.datatransfer.DataProvider;
import org.apache.harmony.awt.datatransfer.RawBitmap;

public class DataSource
implements DataProvider {
    protected final Transferable contents;
    private DataFlavor[] flavors;
    private List<String> nativeFormats;

    public DataSource(Transferable transferable) {
        this.contents = transferable;
    }

    private RawBitmap getImageBitmap(Image image) {
        Object object;
        if (image instanceof BufferedImage && ((BufferedImage)(object = (BufferedImage)image)).getType() == 1) {
            return this.getImageBitmap32((BufferedImage)object);
        }
        int n = image.getWidth(null);
        int n2 = image.getHeight(null);
        if (n <= 0) return null;
        if (n2 <= 0) {
            return null;
        }
        BufferedImage bufferedImage = new BufferedImage(n, n2, 1);
        object = bufferedImage.getGraphics();
        ((Graphics)object).drawImage(image, 0, 0, null);
        ((Graphics)object).dispose();
        return this.getImageBitmap32(bufferedImage);
    }

    private RawBitmap getImageBitmap32(BufferedImage bufferedImage) {
        int[] arrn = new int[bufferedImage.getWidth() * bufferedImage.getHeight()];
        DataBufferInt dataBufferInt = (DataBufferInt)bufferedImage.getRaster().getDataBuffer();
        int n = dataBufferInt.getNumBanks();
        int[] arrn2 = dataBufferInt.getOffsets();
        int n2 = 0;
        int n3 = 0;
        while (n2 < n) {
            int[] arrn3 = dataBufferInt.getData(n2);
            System.arraycopy(arrn3, arrn2[n2], arrn, n3, arrn3.length - arrn2[n2]);
            n3 += arrn3.length - arrn2[n2];
            ++n2;
        }
        return new RawBitmap(bufferedImage.getWidth(), bufferedImage.getHeight(), bufferedImage.getWidth(), 32, 16711680, 65280, 255, arrn);
    }

    private static List<String> getNativesForFlavors(DataFlavor[] arrdataFlavor) {
        ArrayList<String> arrayList = new ArrayList<String>();
        SystemFlavorMap systemFlavorMap = (SystemFlavorMap)SystemFlavorMap.getDefaultFlavorMap();
        int n = 0;
        block0 : while (n < arrdataFlavor.length) {
            Iterator<String> iterator2 = systemFlavorMap.getNativesForFlavor(arrdataFlavor[n]).iterator();
            do {
                if (!iterator2.hasNext()) {
                    ++n;
                    continue block0;
                }
                String string2 = iterator2.next();
                if (arrayList.contains(string2)) continue;
                arrayList.add(string2);
            } while (true);
            break;
        }
        return arrayList;
    }

    private String getText(boolean bl) {
        DataFlavor[] arrdataFlavor = this.contents.getTransferDataFlavors();
        int n = 0;
        while (n < arrdataFlavor.length) {
            DataFlavor dataFlavor = arrdataFlavor[n];
            if (dataFlavor.isFlavorTextType() && (!bl || this.isHtmlFlavor(dataFlavor))) {
                try {
                    if (!String.class.isAssignableFrom(dataFlavor.getRepresentationClass())) return this.getTextFromReader(dataFlavor.getReaderForText(this.contents));
                    return (String)this.contents.getTransferData(dataFlavor);
                }
                catch (Exception exception) {}
            }
            ++n;
        }
        return null;
    }

    private String getTextFromReader(Reader reader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        char[] arrc = new char[1024];
        int n;
        while ((n = reader.read(arrc)) > 0) {
            stringBuilder.append(arrc, 0, n);
        }
        return stringBuilder.toString();
    }

    private boolean isHtmlFlavor(DataFlavor dataFlavor) {
        return "html".equalsIgnoreCase(dataFlavor.getSubType());
    }

    protected DataFlavor[] getDataFlavors() {
        if (this.flavors != null) return this.flavors;
        this.flavors = this.contents.getTransferDataFlavors();
        return this.flavors;
    }

    @Override
    public String[] getFileList() {
        try {
            String[] arrstring = (String[])this.contents.getTransferData(DataFlavor.javaFileListFlavor);
            return arrstring.toArray(new String[arrstring.size()]);
        }
        catch (Exception exception) {
            return null;
        }
    }

    @Override
    public String getHTML() {
        return this.getText(true);
    }

    @Override
    public String[] getNativeFormats() {
        return this.getNativeFormatsList().toArray(new String[0]);
    }

    public List<String> getNativeFormatsList() {
        if (this.nativeFormats != null) return this.nativeFormats;
        this.nativeFormats = DataSource.getNativesForFlavors(this.getDataFlavors());
        return this.nativeFormats;
    }

    @Override
    public RawBitmap getRawBitmap() {
        DataFlavor[] arrdataFlavor = this.contents.getTransferDataFlavors();
        int n = 0;
        while (n < arrdataFlavor.length) {
            DataFlavor dataFlavor = arrdataFlavor[n];
            Class<?> class_ = dataFlavor.getRepresentationClass();
            if (class_ != null && Image.class.isAssignableFrom(class_) && (dataFlavor.isMimeTypeEqual(DataFlavor.imageFlavor) || dataFlavor.isFlavorSerializedObjectType())) {
                try {
                    return this.getImageBitmap((Image)this.contents.getTransferData(dataFlavor));
                }
                catch (Throwable throwable) {}
            }
            ++n;
        }
        return null;
    }

    @Override
    public byte[] getSerializedObject(Class<?> arrby) {
        try {
            Serializable serializable = new DataFlavor((Class<?>)arrby, null);
            serializable = (Serializable)this.contents.getTransferData((DataFlavor)serializable);
            arrby = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream((OutputStream)arrby);
            objectOutputStream.writeObject(serializable);
            return arrby.toByteArray();
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    @Override
    public String getText() {
        return this.getText(false);
    }

    @Override
    public String getURL() {
        try {
            return ((URL)this.contents.getTransferData(urlFlavor)).toString();
        }
        catch (Exception exception) {
            try {
                return ((URL)this.contents.getTransferData(uriFlavor)).toString();
            }
            catch (Exception exception2) {
                try {
                    Object object = new URL(this.getText());
                    return ((URL)object).toString();
                }
                catch (Exception exception3) {
                    return null;
                }
            }
        }
    }

    @Override
    public boolean isNativeFormatAvailable(String string2) {
        return this.getNativeFormatsList().contains(string2);
    }
}

