/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ParsingException;
import com.sun.mail.iap.Response;
import com.sun.mail.imap.protocol.ENVELOPE;
import com.sun.mail.imap.protocol.FetchResponse;
import com.sun.mail.imap.protocol.IMAPResponse;
import com.sun.mail.imap.protocol.Item;
import java.io.PrintStream;
import java.util.Vector;
import javax.mail.internet.ParameterList;

public class BODYSTRUCTURE
implements Item {
    private static int MULTI;
    private static int NESTED;
    private static int SINGLE;
    static final char[] name;
    private static boolean parseDebug;
    public String attachment;
    public BODYSTRUCTURE[] bodies;
    public ParameterList cParams;
    public ParameterList dParams;
    public String description;
    public String disposition;
    public String encoding;
    public ENVELOPE envelope;
    public String id;
    public String[] language;
    public int lines = -1;
    public String md5;
    public int msgno;
    private int processedType;
    public int size = -1;
    public String subtype;
    public String type;

    static {
        name = new char[]{'B', 'O', 'D', 'Y', 'S', 'T', 'R', 'U', 'C', 'T', 'U', 'R', 'E'};
        boolean bl = true;
        SINGLE = 1;
        MULTI = 2;
        NESTED = 3;
        parseDebug = false;
        try {
            String string2 = System.getProperty("mail.imap.parse.debug");
            if (string2 == null || !string2.equalsIgnoreCase("true")) {
                bl = false;
            }
            parseDebug = bl;
            return;
        }
        catch (SecurityException securityException) {
            return;
        }
    }

    public BODYSTRUCTURE(FetchResponse object) throws ParsingException {
        Object object2;
        Object object3;
        if (parseDebug) {
            System.out.println("DEBUG IMAP: parsing BODYSTRUCTURE");
        }
        this.msgno = ((IMAPResponse)object).getNumber();
        if (parseDebug) {
            object2 = System.out;
            object3 = new StringBuilder("DEBUG IMAP: msgno ");
            ((StringBuilder)object3).append(this.msgno);
            ((PrintStream)object2).println(((StringBuilder)object3).toString());
        }
        ((Response)object).skipSpaces();
        if (((Response)object).readByte() != 40) throw new ParsingException("BODYSTRUCTURE parse error: missing ``('' at start");
        if (((Response)object).peekByte() != 40) {
            if (parseDebug) {
                System.out.println("DEBUG IMAP: single part");
            }
            this.type = ((Response)object).readString();
            if (parseDebug) {
                object3 = System.out;
                object2 = new StringBuilder("DEBUG IMAP: type ");
                ((StringBuilder)object2).append(this.type);
                ((PrintStream)object3).println(((StringBuilder)object2).toString());
            }
            this.processedType = SINGLE;
            this.subtype = ((Response)object).readString();
            if (parseDebug) {
                object2 = System.out;
                object3 = new StringBuilder("DEBUG IMAP: subtype ");
                ((StringBuilder)object3).append(this.subtype);
                ((PrintStream)object2).println(((StringBuilder)object3).toString());
            }
            if (this.type == null) {
                this.type = "application";
                this.subtype = "octet-stream";
            }
            this.cParams = this.parseParameters((Response)object);
            if (parseDebug) {
                object3 = System.out;
                object2 = new StringBuilder("DEBUG IMAP: cParams ");
                ((StringBuilder)object2).append(this.cParams);
                ((PrintStream)object3).println(((StringBuilder)object2).toString());
            }
            this.id = ((Response)object).readString();
            if (parseDebug) {
                object3 = System.out;
                object2 = new StringBuilder("DEBUG IMAP: id ");
                ((StringBuilder)object2).append(this.id);
                ((PrintStream)object3).println(((StringBuilder)object2).toString());
            }
            this.description = ((Response)object).readString();
            if (parseDebug) {
                object2 = System.out;
                object3 = new StringBuilder("DEBUG IMAP: description ");
                ((StringBuilder)object3).append(this.description);
                ((PrintStream)object2).println(((StringBuilder)object3).toString());
            }
            this.encoding = ((Response)object).readString();
            if (parseDebug) {
                object3 = System.out;
                object2 = new StringBuilder("DEBUG IMAP: encoding ");
                ((StringBuilder)object2).append(this.encoding);
                ((PrintStream)object3).println(((StringBuilder)object2).toString());
            }
            this.size = ((Response)object).readNumber();
            if (parseDebug) {
                object2 = System.out;
                object3 = new StringBuilder("DEBUG IMAP: size ");
                ((StringBuilder)object3).append(this.size);
                ((PrintStream)object2).println(((StringBuilder)object3).toString());
            }
            if (this.size < 0) throw new ParsingException("BODYSTRUCTURE parse error: bad ``size'' element");
            if (this.type.equalsIgnoreCase("text")) {
                this.lines = ((Response)object).readNumber();
                if (parseDebug) {
                    object2 = System.out;
                    object3 = new StringBuilder("DEBUG IMAP: lines ");
                    ((StringBuilder)object3).append(this.lines);
                    ((PrintStream)object2).println(((StringBuilder)object3).toString());
                }
                if (this.lines < 0) throw new ParsingException("BODYSTRUCTURE parse error: bad ``lines'' element");
            } else if (this.type.equalsIgnoreCase("message") && this.subtype.equalsIgnoreCase("rfc822")) {
                this.processedType = NESTED;
                this.envelope = new ENVELOPE((FetchResponse)object);
                this.bodies = new BODYSTRUCTURE[]{new BODYSTRUCTURE((FetchResponse)object)};
                this.lines = ((Response)object).readNumber();
                if (parseDebug) {
                    object3 = System.out;
                    object2 = new StringBuilder("DEBUG IMAP: lines ");
                    ((StringBuilder)object2).append(this.lines);
                    ((PrintStream)object3).println(((StringBuilder)object2).toString());
                }
                if (this.lines < 0) throw new ParsingException("BODYSTRUCTURE parse error: bad ``lines'' element");
            } else {
                ((Response)object).skipSpaces();
                if (Character.isDigit((char)((Response)object).peekByte())) {
                    object = new StringBuilder("BODYSTRUCTURE parse error: server erroneously included ``lines'' element with type ");
                    ((StringBuilder)object).append(this.type);
                    ((StringBuilder)object).append("/");
                    ((StringBuilder)object).append(this.subtype);
                    throw new ParsingException(((StringBuilder)object).toString());
                }
            }
            if (((Response)object).peekByte() == 41) {
                ((Response)object).readByte();
                if (!parseDebug) return;
                System.out.println("DEBUG IMAP: parse DONE");
                return;
            }
            this.md5 = ((Response)object).readString();
            if (((Response)object).readByte() == 41) {
                if (!parseDebug) return;
                System.out.println("DEBUG IMAP: no MD5 DONE");
                return;
            }
            byte by = ((Response)object).readByte();
            if (by == 40) {
                this.disposition = ((Response)object).readString();
                if (parseDebug) {
                    object2 = System.out;
                    object3 = new StringBuilder("DEBUG IMAP: disposition ");
                    ((StringBuilder)object3).append(this.disposition);
                    ((PrintStream)object2).println(((StringBuilder)object3).toString());
                }
                this.dParams = this.parseParameters((Response)object);
                if (parseDebug) {
                    object3 = System.out;
                    object2 = new StringBuilder("DEBUG IMAP: dParams ");
                    ((StringBuilder)object2).append(this.dParams);
                    ((PrintStream)object3).println(((StringBuilder)object2).toString());
                }
                if (((Response)object).readByte() != 41) throw new ParsingException("BODYSTRUCTURE parse error: missing ``)'' at end of disposition");
            } else {
                if (by != 78 && by != 110) {
                    object = new StringBuilder("BODYSTRUCTURE parse error: ");
                    ((StringBuilder)object).append(this.type);
                    ((StringBuilder)object).append("/");
                    ((StringBuilder)object).append(this.subtype);
                    ((StringBuilder)object).append(": ");
                    ((StringBuilder)object).append("bad single part disposition, b ");
                    ((StringBuilder)object).append(by);
                    throw new ParsingException(((StringBuilder)object).toString());
                }
                if (parseDebug) {
                    System.out.println("DEBUG IMAP: disposition NIL");
                }
                ((Response)object).skip(2);
            }
            if (((Response)object).readByte() == 41) {
                if (!parseDebug) return;
                System.out.println("DEBUG IMAP: disposition DONE");
                return;
            }
            if (((Response)object).peekByte() == 40) {
                this.language = ((Response)object).readStringList();
                if (parseDebug) {
                    object3 = System.out;
                    object2 = new StringBuilder("DEBUG IMAP: language len ");
                    ((StringBuilder)object2).append(this.language.length);
                    ((PrintStream)object3).println(((StringBuilder)object2).toString());
                }
            } else {
                object3 = ((Response)object).readString();
                if (object3 != null) {
                    this.language = new String[]{object3};
                    if (parseDebug) {
                        PrintStream printStream = System.out;
                        object2 = new StringBuilder("DEBUG IMAP: language ");
                        ((StringBuilder)object2).append((String)object3);
                        printStream.println(((StringBuilder)object2).toString());
                    }
                }
            }
        } else {
            if (parseDebug) {
                System.out.println("DEBUG IMAP: parsing multipart");
            }
            this.type = "multipart";
            this.processedType = MULTI;
            object3 = new Vector(1);
            do {
                ((Vector)object3).addElement(new BODYSTRUCTURE((FetchResponse)object));
                ((Response)object).skipSpaces();
            } while (((Response)object).peekByte() == 40);
            object2 = new BODYSTRUCTURE[((Vector)object3).size()];
            this.bodies = object2;
            ((Vector)object3).copyInto((Object[])object2);
            this.subtype = ((Response)object).readString();
            if (parseDebug) {
                object2 = System.out;
                object3 = new StringBuilder("DEBUG IMAP: subtype ");
                ((StringBuilder)object3).append(this.subtype);
                ((PrintStream)object2).println(((StringBuilder)object3).toString());
            }
            if (((Response)object).readByte() == 41) {
                if (!parseDebug) return;
                System.out.println("DEBUG IMAP: parse DONE");
                return;
            }
            if (parseDebug) {
                System.out.println("DEBUG IMAP: parsing extension data");
            }
            this.cParams = this.parseParameters((Response)object);
            if (((Response)object).readByte() == 41) {
                if (!parseDebug) return;
                System.out.println("DEBUG IMAP: body parameters DONE");
                return;
            }
            byte by = ((Response)object).readByte();
            if (by == 40) {
                if (parseDebug) {
                    System.out.println("DEBUG IMAP: parse disposition");
                }
                this.disposition = ((Response)object).readString();
                if (parseDebug) {
                    object2 = System.out;
                    object3 = new StringBuilder("DEBUG IMAP: disposition ");
                    ((StringBuilder)object3).append(this.disposition);
                    ((PrintStream)object2).println(((StringBuilder)object3).toString());
                }
                this.dParams = this.parseParameters((Response)object);
                if (((Response)object).readByte() != 41) throw new ParsingException("BODYSTRUCTURE parse error: missing ``)'' at end of disposition in multipart");
                if (parseDebug) {
                    System.out.println("DEBUG IMAP: disposition DONE");
                }
            } else {
                if (by != 78 && by != 110) {
                    object = new StringBuilder("BODYSTRUCTURE parse error: ");
                    ((StringBuilder)object).append(this.type);
                    ((StringBuilder)object).append("/");
                    ((StringBuilder)object).append(this.subtype);
                    ((StringBuilder)object).append(": ");
                    ((StringBuilder)object).append("bad multipart disposition, b ");
                    ((StringBuilder)object).append(by);
                    throw new ParsingException(((StringBuilder)object).toString());
                }
                if (parseDebug) {
                    System.out.println("DEBUG IMAP: disposition NIL");
                }
                ((Response)object).skip(2);
            }
            if ((by = ((Response)object).readByte()) == 41) {
                if (!parseDebug) return;
                System.out.println("DEBUG IMAP: no body-fld-lang");
                return;
            }
            if (by != 32) throw new ParsingException("BODYSTRUCTURE parse error: missing space after disposition");
            if (((Response)object).peekByte() == 40) {
                this.language = ((Response)object).readStringList();
                if (parseDebug) {
                    object2 = System.out;
                    object3 = new StringBuilder("DEBUG IMAP: language len ");
                    ((StringBuilder)object3).append(this.language.length);
                    ((PrintStream)object2).println(((StringBuilder)object3).toString());
                }
            } else {
                object2 = ((Response)object).readString();
                if (object2 != null) {
                    this.language = new String[]{object2};
                    if (parseDebug) {
                        object3 = System.out;
                        StringBuilder stringBuilder = new StringBuilder("DEBUG IMAP: language ");
                        stringBuilder.append((String)object2);
                        ((PrintStream)object3).println(stringBuilder.toString());
                    }
                }
            }
            do {
                if (((Response)object).readByte() != 32) {
                    return;
                }
                this.parseBodyExtension((Response)object);
            } while (true);
        }
        do {
            if (((Response)object).readByte() != 32) {
                if (!parseDebug) return;
                System.out.println("DEBUG IMAP: all DONE");
                return;
            }
            this.parseBodyExtension((Response)object);
        } while (true);
    }

    private void parseBodyExtension(Response response) throws ParsingException {
        response.skipSpaces();
        byte by = response.peekByte();
        if (by == 40) {
            response.skip(1);
            do {
                this.parseBodyExtension(response);
            } while (response.readByte() != 41);
            return;
        }
        if (Character.isDigit((char)by)) {
            response.readNumber();
            return;
        }
        response.readString();
    }

    private ParameterList parseParameters(Response object) throws ParsingException {
        ((Response)object).skipSpaces();
        byte by = ((Response)object).readByte();
        ParameterList parameterList = null;
        if (by == 40) {
            parameterList = new ParameterList();
            do {
                PrintStream printStream;
                CharSequence charSequence;
                String string2 = ((Response)object).readString();
                if (parseDebug) {
                    printStream = System.out;
                    charSequence = new StringBuilder("DEBUG IMAP: parameter name ");
                    ((StringBuilder)charSequence).append(string2);
                    printStream.println(((StringBuilder)charSequence).toString());
                }
                if (string2 == null) {
                    object = new StringBuilder("BODYSTRUCTURE parse error: ");
                    ((StringBuilder)object).append(this.type);
                    ((StringBuilder)object).append("/");
                    ((StringBuilder)object).append(this.subtype);
                    ((StringBuilder)object).append(": ");
                    ((StringBuilder)object).append("null name in parameter list");
                    throw new ParsingException(((StringBuilder)object).toString());
                }
                charSequence = ((Response)object).readString();
                if (parseDebug) {
                    printStream = System.out;
                    StringBuilder stringBuilder = new StringBuilder("DEBUG IMAP: parameter value ");
                    stringBuilder.append((String)charSequence);
                    printStream.println(stringBuilder.toString());
                }
                parameterList.set(string2, (String)charSequence);
            } while (((Response)object).readByte() != 41);
            parameterList.set(null, "DONE");
            return parameterList;
        }
        if (by != 78) {
            if (by != 110) throw new ParsingException("Parameter list parse error");
        }
        if (parseDebug) {
            System.out.println("DEBUG IMAP: parameter list NIL");
        }
        ((Response)object).skip(2);
        return parameterList;
    }

    public boolean isMulti() {
        if (this.processedType != MULTI) return false;
        return true;
    }

    public boolean isNested() {
        if (this.processedType != NESTED) return false;
        return true;
    }

    public boolean isSingle() {
        if (this.processedType != SINGLE) return false;
        return true;
    }
}

