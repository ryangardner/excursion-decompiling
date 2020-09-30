/*
 * Decompiled with CFR <Could not determine version>.
 */
package net.sbbi.upnp.xpath;

import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.InputSource;

public class JXPathFilterSource
extends InputSource {
    private static final Logger log = Logger.getLogger(JXPathFilterSource.class.getName());
    private final char buggyChar = (char)(false ? 1 : 0);

    public JXPathFilterSource() {
    }

    public JXPathFilterSource(InputStream object) {
        super((InputStream)object);
        Object object2;
        if (object == null) {
            return;
        }
        Object object3 = new StringBuffer();
        try {
            object2 = new byte[512];
            do {
                int n;
                if ((n = ((InputStream)object).read((byte[])object2)) == -1) {
                    object2 = ((StringBuffer)object3).toString();
                    object3 = log;
                    object = new StringBuilder("Readen raw xml doc:\n");
                    ((StringBuilder)object).append((String)object2);
                    break;
                }
                String string2 = new String((byte[])object2, 0, n);
                ((StringBuffer)object3).append(string2);
            } while (true);
        }
        catch (IOException iOException) {
            log.log(Level.SEVERE, "IOException occured during XML reception", iOException);
            return;
        }
        ((Logger)object3).fine(((StringBuilder)object).toString());
        object = object2;
        if (((String)object2).indexOf(0) != -1) {
            object = ((String)object2).replace('\u0000', ' ');
        }
        this.setByteStream(new ByteArrayInputStream(((String)object).getBytes()));
    }

    public JXPathFilterSource(Reader object) {
        super((Reader)object);
        CharSequence charSequence;
        if (object == null) {
            return;
        }
        CharSequence charSequence2 = new StringBuffer();
        try {
            char[] arrc = new char[512];
            do {
                int n;
                if ((n = ((Reader)object).read(arrc)) == -1) {
                    charSequence2 = ((StringBuffer)charSequence2).toString();
                    object = log;
                    charSequence = new StringBuilder("Readen raw xml doc:\n");
                    ((StringBuilder)charSequence).append((String)charSequence2);
                    break;
                }
                charSequence = new String(arrc, 0, n);
                ((StringBuffer)charSequence2).append((String)charSequence);
            } while (true);
        }
        catch (IOException iOException) {
            log.log(Level.SEVERE, "IOException occured during XML reception", iOException);
            return;
        }
        ((Logger)object).fine(((StringBuilder)charSequence).toString());
        object = charSequence2;
        if (((String)charSequence2).indexOf(0) != -1) {
            object = ((String)charSequence2).replace('\u0000', ' ');
        }
        this.setCharacterStream(new CharArrayReader(((String)object).toCharArray()));
    }

    public JXPathFilterSource(String string2) {
        super(string2);
    }
}

