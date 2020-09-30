/*
 * Decompiled with CFR <Could not determine version>.
 */
package myjava.awt.datatransfer;

import myjava.awt.datatransfer.DataFlavor;

public class UnsupportedFlavorException
extends Exception {
    private static final long serialVersionUID = 5383814944251665601L;

    public UnsupportedFlavorException(DataFlavor dataFlavor) {
        StringBuilder stringBuilder = new StringBuilder("flavor = ");
        stringBuilder.append(String.valueOf(dataFlavor));
        super(stringBuilder.toString());
    }
}

