/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.iap;

import com.sun.mail.iap.AString;
import com.sun.mail.iap.Atom;
import com.sun.mail.iap.Literal;
import com.sun.mail.iap.LiteralException;
import com.sun.mail.iap.Protocol;
import com.sun.mail.iap.ProtocolException;
import com.sun.mail.iap.Response;
import com.sun.mail.util.ASCIIUtility;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

public class Argument {
    protected Vector items = new Vector(1);

    private void astring(byte[] arrby, Protocol protocol) throws IOException, ProtocolException {
        DataOutputStream dataOutputStream;
        boolean bl;
        block14 : {
            int n;
            int n2;
            byte by;
            block13 : {
                dataOutputStream = (DataOutputStream)protocol.getOutputStream();
                n = arrby.length;
                if (n > 1024) {
                    this.literal(arrby, protocol);
                    return;
                }
                int n3 = 0;
                bl = n == 0;
                n2 = 0;
                by = 0;
                do {
                    byte by2;
                    block16 : {
                        byte by3;
                        block15 : {
                            int n4;
                            if (n2 >= n) {
                                if (bl) {
                                    dataOutputStream.write(34);
                                }
                                if (by != 0) {
                                    break block13;
                                }
                                dataOutputStream.write(arrby);
                                break block14;
                            }
                            by3 = arrby[n2];
                            if (by3 == 0 || by3 == 13 || by3 == 10 || (n4 = by3 & 255) > 127) break;
                            if (by3 == 42 || by3 == 37 || by3 == 40 || by3 == 41 || by3 == 123 || by3 == 34 || by3 == 92) break block15;
                            by2 = by;
                            if (n4 > 32) break block16;
                        }
                        if (by3 != 34 && by3 != 92) {
                            bl = true;
                            by2 = by;
                        } else {
                            bl = true;
                            by2 = 1;
                        }
                    }
                    ++n2;
                    by = by2;
                } while (true);
                this.literal(arrby, protocol);
                return;
            }
            for (n2 = n3; n2 < n; ++n2) {
                by = arrby[n2];
                if (by == 34 || by == 92) {
                    dataOutputStream.write(92);
                }
                dataOutputStream.write(by);
            }
        }
        if (!bl) return;
        dataOutputStream.write(34);
    }

    private void literal(Literal literal, Protocol protocol) throws IOException, ProtocolException {
        literal.writeTo(this.startLiteral(protocol, literal.size()));
    }

    private void literal(ByteArrayOutputStream byteArrayOutputStream, Protocol protocol) throws IOException, ProtocolException {
        byteArrayOutputStream.writeTo(this.startLiteral(protocol, byteArrayOutputStream.size()));
    }

    private void literal(byte[] arrby, Protocol protocol) throws IOException, ProtocolException {
        this.startLiteral(protocol, arrby.length).write(arrby);
    }

    private OutputStream startLiteral(Protocol protocol, int n) throws IOException, ProtocolException {
        DataOutputStream dataOutputStream = (DataOutputStream)protocol.getOutputStream();
        boolean bl = protocol.supportsNonSyncLiterals();
        dataOutputStream.write(123);
        dataOutputStream.writeBytes(Integer.toString(n));
        if (bl) {
            dataOutputStream.writeBytes("+}\r\n");
        } else {
            dataOutputStream.writeBytes("}\r\n");
        }
        dataOutputStream.flush();
        if (bl) return dataOutputStream;
        Response response;
        while (!(response = protocol.readResponse()).isContinuation()) {
            if (response.isTagged()) throw new LiteralException(response);
        }
        return dataOutputStream;
    }

    public void append(Argument argument) {
        Vector vector = this.items;
        vector.ensureCapacity(vector.size() + argument.items.size());
        int n = 0;
        while (n < argument.items.size()) {
            this.items.addElement(argument.items.elementAt(n));
            ++n;
        }
        return;
    }

    public void write(Protocol protocol) throws IOException, ProtocolException {
        Object object = this.items;
        int n = 0;
        int n2 = object != null ? ((Vector)object).size() : 0;
        object = (DataOutputStream)protocol.getOutputStream();
        while (n < n2) {
            Object e;
            if (n > 0) {
                ((DataOutputStream)object).write(32);
            }
            if ((e = this.items.elementAt(n)) instanceof Atom) {
                ((DataOutputStream)object).writeBytes(((Atom)e).string);
            } else if (e instanceof Number) {
                ((DataOutputStream)object).writeBytes(((Number)e).toString());
            } else if (e instanceof AString) {
                this.astring(((AString)e).bytes, protocol);
            } else if (e instanceof byte[]) {
                this.literal((byte[])e, protocol);
            } else if (e instanceof ByteArrayOutputStream) {
                this.literal((ByteArrayOutputStream)e, protocol);
            } else if (e instanceof Literal) {
                this.literal((Literal)e, protocol);
            } else if (e instanceof Argument) {
                ((DataOutputStream)object).write(40);
                ((Argument)e).write(protocol);
                ((DataOutputStream)object).write(41);
            }
            ++n;
        }
        return;
    }

    public void writeArgument(Argument argument) {
        this.items.addElement(argument);
    }

    public void writeAtom(String string2) {
        this.items.addElement(new Atom(string2));
    }

    public void writeBytes(Literal literal) {
        this.items.addElement(literal);
    }

    public void writeBytes(ByteArrayOutputStream byteArrayOutputStream) {
        this.items.addElement(byteArrayOutputStream);
    }

    public void writeBytes(byte[] arrby) {
        this.items.addElement(arrby);
    }

    public void writeNumber(int n) {
        this.items.addElement(new Integer(n));
    }

    public void writeNumber(long l) {
        this.items.addElement(new Long(l));
    }

    public void writeString(String string2) {
        this.items.addElement(new AString(ASCIIUtility.getBytes(string2)));
    }

    public void writeString(String string2, String string3) throws UnsupportedEncodingException {
        if (string3 == null) {
            this.writeString(string2);
            return;
        }
        this.items.addElement(new AString(string2.getBytes(string3)));
    }
}

