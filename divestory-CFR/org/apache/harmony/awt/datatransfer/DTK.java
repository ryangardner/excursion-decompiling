/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.harmony.awt.datatransfer;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.SystemFlavorMap;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DropTargetContext;
import java.awt.dnd.peer.DragSourceContextPeer;
import java.awt.dnd.peer.DropTargetContextPeer;
import java.nio.charset.Charset;
import org.apache.harmony.awt.ContextStorage;
import org.apache.harmony.awt.datatransfer.DataProvider;
import org.apache.harmony.awt.datatransfer.DataTransferThread;
import org.apache.harmony.awt.datatransfer.NativeClipboard;
import org.apache.harmony.awt.datatransfer.TextFlavor;
import org.apache.harmony.awt.internal.nls.Messages;
import org.apache.harmony.misc.SystemUtils;

public abstract class DTK {
    protected final DataTransferThread dataTransferThread;
    private NativeClipboard nativeClipboard = null;
    private NativeClipboard nativeSelection = null;
    protected SystemFlavorMap systemFlavorMap;

    protected DTK() {
        DataTransferThread dataTransferThread;
        this.dataTransferThread = dataTransferThread = new DataTransferThread(this);
        dataTransferThread.start();
    }

    private static DTK createDTK() {
        Object object;
        int n = SystemUtils.getOS();
        if (n != 1) {
            if (n != 2) throw new RuntimeException(Messages.getString("awt.4E"));
            object = "org.apache.harmony.awt.datatransfer.linux.LinuxDTK";
        } else {
            object = "org.apache.harmony.awt.datatransfer.windows.WinDTK";
        }
        try {
            return (DTK)Class.forName((String)object).newInstance();
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public static DTK getDTK() {
        Object object = ContextStorage.getContextLock();
        synchronized (object) {
            DTK dTK;
            if (ContextStorage.shutdownPending()) {
                return null;
            }
            DTK dTK2 = dTK = ContextStorage.getDTK();
            if (dTK != null) return dTK2;
            dTK2 = DTK.createDTK();
            ContextStorage.setDTK(dTK2);
            return dTK2;
        }
    }

    protected void appendSystemFlavorMap(SystemFlavorMap systemFlavorMap, DataFlavor dataFlavor, String string2) {
        systemFlavorMap.addFlavorForUnencodedNative(string2, dataFlavor);
        systemFlavorMap.addUnencodedNativeForFlavor(dataFlavor, string2);
    }

    protected void appendSystemFlavorMap(SystemFlavorMap systemFlavorMap, String[] arrstring, String string2, String string3) {
        TextFlavor.addUnicodeClasses(systemFlavorMap, string3, string2);
        int n = 0;
        while (n < arrstring.length) {
            if (arrstring[n] != null && Charset.isSupported(arrstring[n])) {
                TextFlavor.addCharsetClasses(systemFlavorMap, string3, string2, arrstring[n]);
            }
            ++n;
        }
        return;
    }

    public abstract DragSourceContextPeer createDragSourceContextPeer(DragGestureEvent var1);

    public abstract DropTargetContextPeer createDropTargetContextPeer(DropTargetContext var1);

    protected String[] getCharsets() {
        return new String[]{"UTF-16", "UTF-8", "unicode", "ISO-8859-1", "US-ASCII"};
    }

    public String getDefaultCharset() {
        return "unicode";
    }

    public NativeClipboard getNativeClipboard() {
        if (this.nativeClipboard != null) return this.nativeClipboard;
        this.nativeClipboard = this.newNativeClipboard();
        return this.nativeClipboard;
    }

    public NativeClipboard getNativeSelection() {
        if (this.nativeSelection != null) return this.nativeSelection;
        this.nativeSelection = this.newNativeSelection();
        return this.nativeSelection;
    }

    public SystemFlavorMap getSystemFlavorMap() {
        synchronized (this) {
            return this.systemFlavorMap;
        }
    }

    public abstract void initDragAndDrop();

    public void initSystemFlavorMap(SystemFlavorMap systemFlavorMap) {
        String[] arrstring = this.getCharsets();
        this.appendSystemFlavorMap(systemFlavorMap, DataFlavor.stringFlavor, "text/plain");
        this.appendSystemFlavorMap(systemFlavorMap, arrstring, "plain", "text/plain");
        this.appendSystemFlavorMap(systemFlavorMap, arrstring, "html", "text/html");
        this.appendSystemFlavorMap(systemFlavorMap, DataProvider.urlFlavor, "application/x-java-url");
        this.appendSystemFlavorMap(systemFlavorMap, arrstring, "uri-list", "application/x-java-url");
        this.appendSystemFlavorMap(systemFlavorMap, DataFlavor.javaFileListFlavor, "application/x-java-file-list");
        this.appendSystemFlavorMap(systemFlavorMap, DataFlavor.imageFlavor, "image/x-java-image");
    }

    protected abstract NativeClipboard newNativeClipboard();

    protected abstract NativeClipboard newNativeSelection();

    public abstract void runEventLoop();

    public void setSystemFlavorMap(SystemFlavorMap systemFlavorMap) {
        synchronized (this) {
            this.systemFlavorMap = systemFlavorMap;
            return;
        }
    }
}

