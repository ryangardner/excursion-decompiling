/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.iap;

import com.sun.mail.iap.ByteArray;
import com.sun.mail.iap.Protocol;
import com.sun.mail.iap.ProtocolException;
import com.sun.mail.iap.ResponseInputStream;
import com.sun.mail.util.ASCIIUtility;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Vector;

public class Response {
    public static final int BAD = 12;
    public static final int BYE = 16;
    public static final int CONTINUATION = 1;
    public static final int NO = 8;
    public static final int OK = 4;
    public static final int SYNTHETIC = 32;
    public static final int TAGGED = 2;
    public static final int TAG_MASK = 3;
    public static final int TYPE_MASK = 28;
    public static final int UNTAGGED = 3;
    private static final int increment = 100;
    protected byte[] buffer = null;
    protected int index;
    protected int pindex;
    protected int size;
    protected String tag = null;
    protected int type = 0;

    public Response(Protocol object) throws IOException, ProtocolException {
        ByteArray byteArray = ((Protocol)object).getResponseBuffer();
        object = ((Protocol)object).getInputStream().readResponse(byteArray);
        this.buffer = ((ByteArray)object).getBytes();
        this.size = ((ByteArray)object).getCount() - 2;
        this.parse();
    }

    public Response(Response response) {
        this.index = response.index;
        this.size = response.size;
        this.buffer = response.buffer;
        this.type = response.type;
        this.tag = response.tag;
    }

    public Response(String arrby) {
        arrby = ASCIIUtility.getBytes((String)arrby);
        this.buffer = arrby;
        this.size = arrby.length;
        this.parse();
    }

    public static Response byeResponse(Exception object) {
        StringBuilder stringBuilder = new StringBuilder("* BYE JavaMail Exception: ");
        stringBuilder.append(((Throwable)object).toString());
        object = new Response(stringBuilder.toString().replace('\r', ' ').replace('\n', ' '));
        ((Response)object).type |= 32;
        return object;
    }

    private void parse() {
        this.index = 0;
        Object object = this.buffer;
        if (object[0] == 43) {
            this.type |= 1;
            this.index = 0 + 1;
            return;
        }
        if (object[0] == 42) {
            this.type |= 3;
            this.index = 0 + 1;
        } else {
            this.type |= 2;
            this.tag = this.readAtom();
        }
        int n = this.index;
        String string2 = this.readAtom();
        object = string2;
        if (string2 == null) {
            object = "";
        }
        if (((String)object).equalsIgnoreCase("OK")) {
            this.type |= 4;
        } else if (((String)object).equalsIgnoreCase("NO")) {
            this.type |= 8;
        } else if (((String)object).equalsIgnoreCase("BAD")) {
            this.type |= 12;
        } else if (((String)object).equalsIgnoreCase("BYE")) {
            this.type |= 16;
        } else {
            this.index = n;
        }
        this.pindex = this.index;
    }

    private Object parseString(boolean bl, boolean bl2) {
        this.skipSpaces();
        Object object = this.buffer;
        int n = this.index;
        int n2 = object[n];
        if (n2 != 34) {
            if (n2 != 123) {
                if (bl) {
                    object = this.readAtom();
                    if (!bl2) return new ByteArray(this.buffer, n, this.index);
                    return object;
                }
                if (n2 != 78) {
                    if (n2 != 110) return null;
                }
                this.index += 3;
                return null;
            }
        } else {
            this.index = n2 = n + 1;
            n = n2;
            do {
                byte by;
                int n3;
                if ((by = (object = this.buffer)[n3 = this.index]) == 34) {
                    this.index = n3 + 1;
                    if (!bl2) return new ByteArray(this.buffer, n2, n - n2);
                    return ASCIIUtility.toString(object, n2, n);
                }
                if (by == 92) {
                    this.index = n3 + 1;
                }
                if ((n3 = this.index) != n) {
                    object = this.buffer;
                    object[n] = object[n3];
                }
                ++n;
                ++this.index;
            } while (true);
        }
        this.index = ++n;
        do {
            if ((object = this.buffer)[n2 = this.index] == 125) {
                int n4;
                try {
                    n = ASCIIUtility.parseInt(object, n, n2);
                    n4 = this.index + 3;
                }
                catch (NumberFormatException numberFormatException) {
                    return null;
                }
                this.index = n2 = n4 + n;
                if (!bl2) return new ByteArray(this.buffer, n4, n);
                return ASCIIUtility.toString(this.buffer, n4, n2);
            }
            this.index = n2 + 1;
        } while (true);
    }

    public String getRest() {
        this.skipSpaces();
        return ASCIIUtility.toString(this.buffer, this.index, this.size);
    }

    public String getTag() {
        return this.tag;
    }

    public int getType() {
        return this.type;
    }

    public boolean isBAD() {
        if ((this.type & 28) != 12) return false;
        return true;
    }

    public boolean isBYE() {
        if ((this.type & 28) != 16) return false;
        return true;
    }

    public boolean isContinuation() {
        if ((this.type & 3) != 1) return false;
        return true;
    }

    public boolean isNO() {
        if ((this.type & 28) != 8) return false;
        return true;
    }

    public boolean isOK() {
        if ((this.type & 28) != 4) return false;
        return true;
    }

    public boolean isSynthetic() {
        if ((this.type & 32) != 32) return false;
        return true;
    }

    public boolean isTagged() {
        if ((this.type & 3) != 2) return false;
        return true;
    }

    public boolean isUnTagged() {
        if ((this.type & 3) != 3) return false;
        return true;
    }

    public byte peekByte() {
        int n = this.index;
        if (n >= this.size) return 0;
        return this.buffer[n];
    }

    public String readAtom() {
        return this.readAtom('\u0000');
    }

    public String readAtom(char c) {
        int n;
        this.skipSpaces();
        int n2 = this.index;
        if (n2 >= this.size) {
            return null;
        }
        while ((n = this.index) < this.size) {
            if ((n = this.buffer[n]) <= 32) return ASCIIUtility.toString(this.buffer, n2, this.index);
            if (n == 40) return ASCIIUtility.toString(this.buffer, n2, this.index);
            if (n == 41) return ASCIIUtility.toString(this.buffer, n2, this.index);
            if (n == 37) return ASCIIUtility.toString(this.buffer, n2, this.index);
            if (n == 42) return ASCIIUtility.toString(this.buffer, n2, this.index);
            if (n == 34) return ASCIIUtility.toString(this.buffer, n2, this.index);
            if (n == 92) return ASCIIUtility.toString(this.buffer, n2, this.index);
            if (n == 127) return ASCIIUtility.toString(this.buffer, n2, this.index);
            if (c != '\u0000' && n == c) {
                return ASCIIUtility.toString(this.buffer, n2, this.index);
            }
            ++this.index;
        }
        return ASCIIUtility.toString(this.buffer, n2, this.index);
    }

    public String readAtomString() {
        return (String)this.parseString(true, true);
    }

    public byte readByte() {
        int n = this.index;
        if (n >= this.size) return 0;
        byte[] arrby = this.buffer;
        this.index = n + 1;
        return arrby[n];
    }

    public ByteArray readByteArray() {
        if (!this.isContinuation()) return (ByteArray)this.parseString(false, false);
        this.skipSpaces();
        byte[] arrby = this.buffer;
        int n = this.index;
        return new ByteArray(arrby, n, this.size - n);
    }

    public ByteArrayInputStream readBytes() {
        ByteArray byteArray = this.readByteArray();
        if (byteArray == null) return null;
        return byteArray.toByteArrayInputStream();
    }

    public long readLong() {
        int n;
        this.skipSpaces();
        int n2 = this.index;
        while ((n = this.index++) < this.size && Character.isDigit((char)this.buffer[n])) {
        }
        n = this.index;
        if (n <= n2) return -1L;
        try {
            return ASCIIUtility.parseLong(this.buffer, n2, n);
        }
        catch (NumberFormatException numberFormatException) {
            return -1L;
        }
    }

    public int readNumber() {
        int n;
        this.skipSpaces();
        int n2 = this.index;
        while ((n = this.index++) < this.size && Character.isDigit((char)this.buffer[n])) {
        }
        n = this.index;
        if (n <= n2) return -1;
        try {
            return ASCIIUtility.parseInt(this.buffer, n2, n);
        }
        catch (NumberFormatException numberFormatException) {
            return -1;
        }
    }

    public String readString() {
        return (String)this.parseString(false, true);
    }

    public String readString(char c) {
        int n;
        this.skipSpaces();
        int n2 = this.index;
        if (n2 >= this.size) {
            return null;
        }
        while ((n = this.index) < this.size) {
            if (this.buffer[n] == c) {
                return ASCIIUtility.toString(this.buffer, n2, this.index);
            }
            this.index = n + 1;
        }
        return ASCIIUtility.toString(this.buffer, n2, this.index);
    }

    public String[] readStringList() {
        Object[] arrobject;
        this.skipSpaces();
        Object object = this.buffer;
        int n = this.index;
        if (object[n] != 40) {
            return null;
        }
        this.index = n + 1;
        object = new Vector();
        do {
            ((Vector)object).addElement(this.readString());
            arrobject = this.buffer;
            n = this.index;
            this.index = n + 1;
        } while (arrobject[n] != 41);
        n = ((Vector)object).size();
        if (n <= 0) return null;
        arrobject = new String[n];
        ((Vector)object).copyInto(arrobject);
        return arrobject;
    }

    public void reset() {
        this.index = this.pindex;
    }

    public void skip(int n) {
        this.index += n;
    }

    public void skipSpaces() {
        int n;
        while ((n = this.index) < this.size) {
            if (this.buffer[n] != 32) {
                return;
            }
            this.index = n + 1;
        }
    }

    public void skipToken() {
        int n;
        while ((n = this.index) < this.size) {
            if (this.buffer[n] == 32) {
                return;
            }
            this.index = n + 1;
        }
    }

    public String toString() {
        return ASCIIUtility.toString(this.buffer, 0, this.size);
    }
}

