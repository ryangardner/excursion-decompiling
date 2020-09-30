/*
 * Decompiled with CFR <Could not determine version>.
 */
package myjava.awt.datatransfer;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

final class MimeTypeProcessor {
    private static MimeTypeProcessor instance;

    private MimeTypeProcessor() {
    }

    static String assemble(MimeType mimeType) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(mimeType.getFullType());
        Enumeration enumeration = mimeType.parameters.keys();
        while (enumeration.hasMoreElements()) {
            String string2 = (String)enumeration.nextElement();
            String string3 = (String)mimeType.parameters.get(string2);
            stringBuilder.append("; ");
            stringBuilder.append(string2);
            stringBuilder.append("=\"");
            stringBuilder.append(string3);
            stringBuilder.append('\"');
        }
        return stringBuilder.toString();
    }

    private static int getNextMeaningfulIndex(String string2, int n) {
        while (n < string2.length()) {
            if (MimeTypeProcessor.isMeaningfulChar(string2.charAt(n))) {
                return n;
            }
            ++n;
        }
        return n;
    }

    private static boolean isMeaningfulChar(char c) {
        if (c < '!') return false;
        if (c > '~') return false;
        return true;
    }

    private static boolean isTSpecialChar(char c) {
        if (c == '(') return true;
        if (c == ')') return true;
        if (c == '[') return true;
        if (c == ']') return true;
        if (c == '<') return true;
        if (c == '>') return true;
        if (c == '@') return true;
        if (c == ',') return true;
        if (c == ';') return true;
        if (c == ':') return true;
        if (c == '\\') return true;
        if (c == '\"') return true;
        if (c == '/') return true;
        if (c == '?') return true;
        if (c == '=') return true;
        return false;
    }

    static MimeType parse(String string2) {
        if (instance == null) {
            instance = new MimeTypeProcessor();
        }
        MimeType mimeType = new MimeType();
        if (string2 == null) return mimeType;
        StringPosition stringPosition = new StringPosition();
        MimeTypeProcessor.retrieveType(string2, mimeType, stringPosition);
        MimeTypeProcessor.retrieveParams(string2, mimeType, stringPosition);
        return mimeType;
    }

    private static void retrieveParam(String string2, MimeType mimeType, StringPosition stringPosition) {
        String string3 = MimeTypeProcessor.retrieveToken(string2, stringPosition).toLowerCase();
        stringPosition.i = MimeTypeProcessor.getNextMeaningfulIndex(string2, stringPosition.i);
        if (stringPosition.i >= string2.length()) throw new IllegalArgumentException();
        if (string2.charAt(stringPosition.i) != '=') throw new IllegalArgumentException();
        ++stringPosition.i;
        stringPosition.i = MimeTypeProcessor.getNextMeaningfulIndex(string2, stringPosition.i);
        if (stringPosition.i >= string2.length()) throw new IllegalArgumentException();
        string2 = string2.charAt(stringPosition.i) == '\"' ? MimeTypeProcessor.retrieveQuoted(string2, stringPosition) : MimeTypeProcessor.retrieveToken(string2, stringPosition);
        mimeType.parameters.put(string3, string2);
    }

    private static void retrieveParams(String string2, MimeType mimeType, StringPosition stringPosition) {
        MimeType.access$3(mimeType, new Hashtable());
        MimeType.access$4(mimeType, new Hashtable());
        do {
            stringPosition.i = MimeTypeProcessor.getNextMeaningfulIndex(string2, stringPosition.i);
            if (stringPosition.i >= string2.length()) {
                return;
            }
            if (string2.charAt(stringPosition.i) != ';') throw new IllegalArgumentException();
            ++stringPosition.i;
            MimeTypeProcessor.retrieveParam(string2, mimeType, stringPosition);
        } while (true);
    }

    private static String retrieveQuoted(String string2, StringPosition stringPosition) {
        StringBuilder stringBuilder = new StringBuilder();
        ++stringPosition.i;
        boolean bl = true;
        do {
            if (string2.charAt(stringPosition.i) == '\"' && bl) {
                ++stringPosition.i;
                return stringBuilder.toString();
            }
            int n = stringPosition.i;
            stringPosition.i = n + 1;
            char c = string2.charAt(n);
            if (!bl) {
                bl = true;
            } else if (c == '\\') {
                bl = false;
            }
            if (bl) {
                stringBuilder.append(c);
            }
            if (stringPosition.i == string2.length()) throw new IllegalArgumentException();
        } while (true);
    }

    private static String retrieveToken(String string2, StringPosition stringPosition) {
        StringBuilder stringBuilder = new StringBuilder();
        stringPosition.i = MimeTypeProcessor.getNextMeaningfulIndex(string2, stringPosition.i);
        if (stringPosition.i >= string2.length()) throw new IllegalArgumentException();
        if (MimeTypeProcessor.isTSpecialChar(string2.charAt(stringPosition.i))) throw new IllegalArgumentException();
        do {
            int n = stringPosition.i;
            stringPosition.i = n + 1;
            stringBuilder.append(string2.charAt(n));
            if (stringPosition.i >= string2.length()) return stringBuilder.toString();
            if (!MimeTypeProcessor.isMeaningfulChar(string2.charAt(stringPosition.i))) return stringBuilder.toString();
        } while (!MimeTypeProcessor.isTSpecialChar(string2.charAt(stringPosition.i)));
        return stringBuilder.toString();
    }

    private static void retrieveType(String string2, MimeType mimeType, StringPosition stringPosition) {
        MimeType.access$1(mimeType, MimeTypeProcessor.retrieveToken(string2, stringPosition).toLowerCase());
        stringPosition.i = MimeTypeProcessor.getNextMeaningfulIndex(string2, stringPosition.i);
        if (stringPosition.i >= string2.length()) throw new IllegalArgumentException();
        if (string2.charAt(stringPosition.i) != '/') throw new IllegalArgumentException();
        ++stringPosition.i;
        MimeType.access$2(mimeType, MimeTypeProcessor.retrieveToken(string2, stringPosition).toLowerCase());
    }

    static final class MimeType
    implements Cloneable,
    Serializable {
        private static final long serialVersionUID = -6693571907475992044L;
        private Hashtable<String, String> parameters;
        private String primaryType;
        private String subType;
        private Hashtable<String, Object> systemParameters;

        MimeType() {
            this.primaryType = null;
            this.subType = null;
            this.parameters = null;
            this.systemParameters = null;
        }

        MimeType(String string2, String string3) {
            this.primaryType = string2;
            this.subType = string3;
            this.parameters = new Hashtable();
            this.systemParameters = new Hashtable();
        }

        static /* synthetic */ void access$1(MimeType mimeType, String string2) {
            mimeType.primaryType = string2;
        }

        static /* synthetic */ void access$2(MimeType mimeType, String string2) {
            mimeType.subType = string2;
        }

        static /* synthetic */ void access$3(MimeType mimeType, Hashtable hashtable) {
            mimeType.parameters = hashtable;
        }

        static /* synthetic */ void access$4(MimeType mimeType, Hashtable hashtable) {
            mimeType.systemParameters = hashtable;
        }

        void addParameter(String string2, String string3) {
            if (string3 == null) {
                return;
            }
            String string4 = string3;
            if (string3.charAt(0) == '\"') {
                string4 = string3;
                if (string3.charAt(string3.length() - 1) == '\"') {
                    string4 = string3.substring(1, string3.length() - 2);
                }
            }
            if (string4.length() == 0) {
                return;
            }
            this.parameters.put(string2, string4);
        }

        void addSystemParameter(String string2, Object object) {
            this.systemParameters.put(string2, object);
        }

        public Object clone() {
            MimeType mimeType = new MimeType(this.primaryType, this.subType);
            mimeType.parameters = (Hashtable)this.parameters.clone();
            mimeType.systemParameters = (Hashtable)this.systemParameters.clone();
            return mimeType;
        }

        boolean equals(MimeType mimeType) {
            if (mimeType != null) return this.getFullType().equals(mimeType.getFullType());
            return false;
        }

        String getFullType() {
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(this.primaryType));
            stringBuilder.append("/");
            stringBuilder.append(this.subType);
            return stringBuilder.toString();
        }

        String getParameter(String string2) {
            return this.parameters.get(string2);
        }

        String getPrimaryType() {
            return this.primaryType;
        }

        String getSubType() {
            return this.subType;
        }

        Object getSystemParameter(String string2) {
            return this.systemParameters.get(string2);
        }

        void removeParameter(String string2) {
            this.parameters.remove(string2);
        }
    }

    private static final class StringPosition {
        int i = 0;

        private StringPosition() {
        }
    }

}

