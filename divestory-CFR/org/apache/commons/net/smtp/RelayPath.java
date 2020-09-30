/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.smtp;

import java.util.Enumeration;
import java.util.Vector;

public final class RelayPath {
    String _emailAddress;
    Vector<String> _path = new Vector();

    public RelayPath(String string2) {
        this._emailAddress = string2;
    }

    public void addRelay(String string2) {
        this._path.addElement(string2);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('<');
        Enumeration<String> enumeration = this._path.elements();
        if (enumeration.hasMoreElements()) {
            stringBuilder.append('@');
            stringBuilder.append(enumeration.nextElement());
            while (enumeration.hasMoreElements()) {
                stringBuilder.append(",@");
                stringBuilder.append(enumeration.nextElement());
            }
            stringBuilder.append(':');
        }
        stringBuilder.append(this._emailAddress);
        stringBuilder.append('>');
        return stringBuilder.toString();
    }
}

