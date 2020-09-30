/*
 * Decompiled with CFR <Could not determine version>.
 */
package myjava.awt.datatransfer;

import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;
import java.io.Externalizable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import myjava.awt.datatransfer.MimeTypeProcessor;
import myjava.awt.datatransfer.Transferable;
import myjava.awt.datatransfer.UnsupportedFlavorException;
import org.apache.harmony.awt.datatransfer.DTK;
import org.apache.harmony.awt.internal.nls.Messages;

public class DataFlavor
implements Externalizable,
Cloneable {
    public static final DataFlavor javaFileListFlavor;
    public static final String javaJVMLocalObjectMimeType = "application/x-java-jvm-local-objectref";
    public static final String javaRemoteObjectMimeType = "application/x-java-remote-object";
    public static final String javaSerializedObjectMimeType = "application/x-java-serialized-object";
    @Deprecated
    public static final DataFlavor plainTextFlavor;
    private static DataFlavor plainUnicodeFlavor;
    private static final long serialVersionUID = 8367026044764648243L;
    private static final String[] sortedTextFlavors;
    public static final DataFlavor stringFlavor;
    private String humanPresentableName;
    private MimeTypeProcessor.MimeType mimeInfo;
    private Class<?> representationClass;

    static {
        plainTextFlavor = new DataFlavor("text/plain; charset=unicode; class=java.io.InputStream", "Plain Text");
        stringFlavor = new DataFlavor("application/x-java-serialized-object; class=java.lang.String", "Unicode String");
        javaFileListFlavor = new DataFlavor("application/x-java-file-list; class=java.util.List", "application/x-java-file-list");
        sortedTextFlavors = new String[]{"text/sgml", "text/xml", "text/html", "text/rtf", "text/enriched", "text/richtext", "text/uri-list", "text/tab-separated-values", "text/t140", "text/rfc822-headers", "text/parityfec", "text/directory", "text/css", "text/calendar", javaSerializedObjectMimeType, "text/plain"};
        plainUnicodeFlavor = null;
    }

    public DataFlavor() {
        this.mimeInfo = null;
        this.humanPresentableName = null;
        this.representationClass = null;
    }

    public DataFlavor(Class<?> class_, String string2) {
        this.mimeInfo = new MimeTypeProcessor.MimeType("application", "x-java-serialized-object");
        this.humanPresentableName = string2 != null ? string2 : javaSerializedObjectMimeType;
        this.mimeInfo.addParameter("class", class_.getName());
        this.representationClass = class_;
    }

    public DataFlavor(String string2) throws ClassNotFoundException {
        this.init(string2, null, null);
    }

    public DataFlavor(String string2, String string3) {
        try {
            this.init(string2, string3, null);
            return;
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new IllegalArgumentException(Messages.getString("awt.16C", this.mimeInfo.getParameter("class")), classNotFoundException);
        }
    }

    public DataFlavor(String string2, String string3, ClassLoader classLoader) throws ClassNotFoundException {
        this.init(string2, string3, classLoader);
    }

    private static List<DataFlavor> fetchTextFlavors(List<DataFlavor> linkedList, String string2) {
        LinkedList<DataFlavor> linkedList2 = new LinkedList<DataFlavor>();
        linkedList = linkedList.iterator();
        do {
            if (!linkedList.hasNext()) {
                linkedList = linkedList2;
                if (!linkedList2.isEmpty()) return linkedList;
                return null;
            }
            DataFlavor dataFlavor = (DataFlavor)linkedList.next();
            if (dataFlavor.isFlavorTextType()) {
                if (!dataFlavor.mimeInfo.getFullType().equals(string2)) continue;
                if (!linkedList2.contains(dataFlavor)) {
                    linkedList2.add(dataFlavor);
                }
                linkedList.remove();
                continue;
            }
            linkedList.remove();
        } while (true);
    }

    private String getCharset() {
        if (this.mimeInfo == null) return "";
        if (this.isCharsetRedundant()) {
            return "";
        }
        String string2 = this.mimeInfo.getParameter("charset");
        if (this.isCharsetRequired()) {
            if (string2 == null) return DTK.getDTK().getDefaultCharset();
            if (string2.length() == 0) {
                return DTK.getDTK().getDefaultCharset();
            }
        }
        if (string2 != null) return string2;
        return "";
    }

    private static List<DataFlavor> getFlavors(List<DataFlavor> list, Class<?> class_) {
        LinkedList<DataFlavor> linkedList = new LinkedList<DataFlavor>();
        Iterator<DataFlavor> iterator2 = list.iterator();
        do {
            if (!iterator2.hasNext()) {
                if (!linkedList.isEmpty()) return list;
                return null;
            }
            DataFlavor dataFlavor = iterator2.next();
            if (!dataFlavor.representationClass.equals(class_)) continue;
            linkedList.add(dataFlavor);
        } while (true);
    }

    /*
     * Unable to fully structure code
     */
    private static List<DataFlavor> getFlavors(List<DataFlavor> var0, String[] var1_1) {
        var2_2 = new LinkedList<DataFlavor>();
        var3_3 = var0.iterator();
        do {
            if (!var3_3.hasNext()) {
                if (var2_2.isEmpty() == false) return var0;
                return null;
            }
            var4_4 = var3_3.next();
            if (!DataFlavor.isCharsetSupported(var4_4.getCharset())) {
                var3_3.remove();
                continue;
            }
            var5_5 = var1_1.length;
            var6_6 = 0;
            do {
                if (var6_6 >= var5_5) ** break;
                if (Charset.forName(var1_1[var6_6]).equals(Charset.forName(var4_4.getCharset()))) {
                    var2_2.add(var4_4);
                }
                ++var6_6;
            } while (true);
            break;
        } while (true);
    }

    private String getKeyInfo() {
        CharSequence charSequence = new StringBuilder(String.valueOf(this.mimeInfo.getFullType()));
        ((StringBuilder)charSequence).append(";class=");
        ((StringBuilder)charSequence).append(this.representationClass.getName());
        String string2 = ((StringBuilder)charSequence).toString();
        charSequence = string2;
        if (!this.mimeInfo.getPrimaryType().equals("text")) return charSequence;
        if (this.isUnicodeFlavor()) {
            return string2;
        }
        charSequence = new StringBuilder(String.valueOf(string2));
        ((StringBuilder)charSequence).append(";charset=");
        ((StringBuilder)charSequence).append(this.getCharset().toLowerCase());
        return ((StringBuilder)charSequence).toString();
    }

    public static final DataFlavor getTextPlainUnicodeFlavor() {
        if (plainUnicodeFlavor != null) return plainUnicodeFlavor;
        StringBuilder stringBuilder = new StringBuilder("text/plain; charset=");
        stringBuilder.append(DTK.getDTK().getDefaultCharset());
        stringBuilder.append("; class=java.io.InputStream");
        plainUnicodeFlavor = new DataFlavor(stringBuilder.toString(), "Plain Text");
        return plainUnicodeFlavor;
    }

    private void init(String class_, String string2, ClassLoader classLoader) throws ClassNotFoundException {
        block4 : {
            block3 : {
                try {
                    this.mimeInfo = MimeTypeProcessor.parse((String)((Object)class_));
                    if (string2 == null) break block3;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw new IllegalArgumentException(Messages.getString("awt.16D", class_));
                }
                this.humanPresentableName = string2;
                break block4;
            }
            class_ = new StringBuilder(String.valueOf(this.mimeInfo.getPrimaryType()));
            ((StringBuilder)((Object)class_)).append('/');
            ((StringBuilder)((Object)class_)).append(this.mimeInfo.getSubType());
            this.humanPresentableName = ((StringBuilder)((Object)class_)).toString();
        }
        string2 = this.mimeInfo.getParameter("class");
        class_ = string2;
        if (string2 == null) {
            class_ = "java.io.InputStream";
            this.mimeInfo.addParameter("class", "java.io.InputStream");
        }
        class_ = classLoader == null ? Class.forName((String)((Object)class_)) : classLoader.loadClass((String)((Object)class_));
        this.representationClass = class_;
    }

    private boolean isByteCodeFlavor() {
        Class<?> class_ = this.representationClass;
        if (class_ == null) return false;
        if (class_.equals(InputStream.class)) return true;
        if (this.representationClass.equals(ByteBuffer.class)) return true;
        if (!this.representationClass.equals(byte[].class)) return false;
        return true;
    }

    private boolean isCharsetRedundant() {
        String string2 = this.mimeInfo.getFullType();
        if (string2.equals("text/rtf")) return true;
        if (string2.equals("text/tab-separated-values")) return true;
        if (string2.equals("text/t140")) return true;
        if (string2.equals("text/rfc822-headers")) return true;
        if (string2.equals("text/parityfec")) return true;
        return false;
    }

    private boolean isCharsetRequired() {
        String string2 = this.mimeInfo.getFullType();
        if (string2.equals("text/sgml")) return true;
        if (string2.equals("text/xml")) return true;
        if (string2.equals("text/html")) return true;
        if (string2.equals("text/enriched")) return true;
        if (string2.equals("text/richtext")) return true;
        if (string2.equals("text/uri-list")) return true;
        if (string2.equals("text/directory")) return true;
        if (string2.equals("text/css")) return true;
        if (string2.equals("text/calendar")) return true;
        if (string2.equals(javaSerializedObjectMimeType)) return true;
        if (string2.equals("text/plain")) return true;
        return false;
    }

    private static boolean isCharsetSupported(String string2) {
        try {
            return Charset.isSupported(string2);
        }
        catch (IllegalCharsetNameException illegalCharsetNameException) {
            return false;
        }
    }

    private boolean isUnicodeFlavor() {
        Class<?> class_ = this.representationClass;
        if (class_ == null) return false;
        if (class_.equals(Reader.class)) return true;
        if (this.representationClass.equals(String.class)) return true;
        if (this.representationClass.equals(CharBuffer.class)) return true;
        if (!this.representationClass.equals(char[].class)) return false;
        return true;
    }

    private static List<DataFlavor> selectBestByAlphabet(List<DataFlavor> object) {
        int n = object.size();
        String[] arrstring = new String[n];
        LinkedList<DataFlavor> linkedList = new LinkedList<DataFlavor>();
        int n2 = 0;
        do {
            if (n2 >= n) break;
            arrstring[n2] = object.get(n2).getCharset();
            ++n2;
        } while (true);
        Arrays.sort(arrstring, String.CASE_INSENSITIVE_ORDER);
        Iterator<DataFlavor> iterator2 = object.iterator();
        do {
            if (!iterator2.hasNext()) {
                object = linkedList;
                if (!linkedList.isEmpty()) return object;
                return null;
            }
            object = iterator2.next();
            if (!arrstring[0].equalsIgnoreCase(DataFlavor.super.getCharset())) continue;
            linkedList.add((DataFlavor)object);
        } while (true);
    }

    private static DataFlavor selectBestByCharset(List<DataFlavor> list) {
        List<DataFlavor> list2;
        List<DataFlavor> list3 = list2 = DataFlavor.getFlavors(list, new String[]{"UTF-16", "UTF-8", "UTF-16BE", "UTF-16LE"});
        if (list2 == null) {
            list3 = list2 = DataFlavor.getFlavors(list, new String[]{DTK.getDTK().getDefaultCharset()});
            if (list2 == null) {
                list3 = list2 = DataFlavor.getFlavors(list, new String[]{"US-ASCII"});
                if (list2 == null) {
                    list3 = DataFlavor.selectBestByAlphabet(list);
                }
            }
        }
        if (list3 == null) return null;
        if (list3.size() != 1) return DataFlavor.selectBestFlavorWOCharset(list3);
        return list3.get(0);
    }

    private static DataFlavor selectBestFlavorWCharset(List<DataFlavor> list) {
        List<DataFlavor> list2 = DataFlavor.getFlavors(list, Reader.class);
        if (list2 != null) {
            return list2.get(0);
        }
        list2 = DataFlavor.getFlavors(list, String.class);
        if (list2 != null) {
            return list2.get(0);
        }
        list2 = DataFlavor.getFlavors(list, CharBuffer.class);
        if (list2 != null) {
            return list2.get(0);
        }
        list2 = DataFlavor.getFlavors(list, char[].class);
        if (list2 == null) return DataFlavor.selectBestByCharset(list);
        return list2.get(0);
    }

    private static DataFlavor selectBestFlavorWOCharset(List<DataFlavor> list) {
        List<DataFlavor> list2 = DataFlavor.getFlavors(list, InputStream.class);
        if (list2 != null) {
            return list2.get(0);
        }
        list2 = DataFlavor.getFlavors(list, ByteBuffer.class);
        if (list2 != null) {
            return list2.get(0);
        }
        list2 = DataFlavor.getFlavors(list, byte[].class);
        if (list2 == null) return list.get(0);
        return list2.get(0);
    }

    public static final DataFlavor selectBestTextFlavor(DataFlavor[] object) {
        if (object == null) {
            return null;
        }
        if ((object = DataFlavor.sortTextFlavorsByType(new LinkedList<DataFlavor>(Arrays.asList(object)))).isEmpty()) {
            return null;
        }
        if ((object = (List)object.get(0)).size() == 1) {
            return (DataFlavor)object.get(0);
        }
        if (((DataFlavor)object.get(0)).getCharset().length() != 0) return DataFlavor.selectBestFlavorWCharset((List<DataFlavor>)object);
        return DataFlavor.selectBestFlavorWOCharset((List<DataFlavor>)object);
    }

    private static List<List<DataFlavor>> sortTextFlavorsByType(List<DataFlavor> list) {
        LinkedList<List<DataFlavor>> linkedList = new LinkedList<List<DataFlavor>>();
        String[] arrstring = sortedTextFlavors;
        int n = arrstring.length;
        int n2 = 0;
        do {
            if (n2 >= n) {
                if (list.isEmpty()) return linkedList;
                linkedList.addLast(list);
                return linkedList;
            }
            List<DataFlavor> list2 = DataFlavor.fetchTextFlavors(list, arrstring[n2]);
            if (list2 != null) {
                linkedList.addLast(list2);
            }
            ++n2;
        } while (true);
    }

    protected static final Class<?> tryToLoadClass(String string2, ClassLoader classLoader) throws ClassNotFoundException {
        try {
            return Class.forName(string2);
        }
        catch (ClassNotFoundException classNotFoundException) {
            try {
                return ClassLoader.getSystemClassLoader().loadClass(string2);
            }
            catch (ClassNotFoundException classNotFoundException2) {
                ClassLoader classLoader2 = Thread.currentThread().getContextClassLoader();
                if (classLoader2 == null) return classLoader.loadClass(string2);
                try {
                    return classLoader2.loadClass(string2);
                }
                catch (ClassNotFoundException classNotFoundException3) {
                    return classLoader.loadClass(string2);
                }
            }
        }
    }

    public Object clone() throws CloneNotSupportedException {
        DataFlavor dataFlavor = new DataFlavor();
        dataFlavor.humanPresentableName = this.humanPresentableName;
        dataFlavor.representationClass = this.representationClass;
        MimeTypeProcessor.MimeType mimeType = this.mimeInfo;
        mimeType = mimeType != null ? (MimeTypeProcessor.MimeType)mimeType.clone() : null;
        dataFlavor.mimeInfo = mimeType;
        return dataFlavor;
    }

    public boolean equals(Object object) {
        if (object == null) return false;
        if (object instanceof DataFlavor) return this.equals((DataFlavor)object);
        return false;
    }

    @Deprecated
    public boolean equals(String string2) {
        if (string2 != null) return this.isMimeTypeEqual(string2);
        return false;
    }

    public boolean equals(DataFlavor object) {
        if (object == this) {
            return true;
        }
        if (object == null) {
            return false;
        }
        Object object2 = this.mimeInfo;
        if (object2 == null) {
            if (((DataFlavor)object).mimeInfo != null) return false;
            return true;
        }
        if (!((MimeTypeProcessor.MimeType)object2).equals(((DataFlavor)object).mimeInfo)) return false;
        if (!this.representationClass.equals(((DataFlavor)object).representationClass)) {
            return false;
        }
        if (!this.mimeInfo.getPrimaryType().equals("text")) return true;
        if (this.isUnicodeFlavor()) {
            return true;
        }
        object2 = this.getCharset();
        object = ((DataFlavor)object).getCharset();
        if (!DataFlavor.isCharsetSupported((String)object2)) return ((String)object2).equalsIgnoreCase((String)object);
        if (DataFlavor.isCharsetSupported((String)object)) return Charset.forName((String)object2).equals(Charset.forName((String)object));
        return ((String)object2).equalsIgnoreCase((String)object);
    }

    public final Class<?> getDefaultRepresentationClass() {
        return InputStream.class;
    }

    public final String getDefaultRepresentationClassAsString() {
        return this.getDefaultRepresentationClass().getName();
    }

    public String getHumanPresentableName() {
        return this.humanPresentableName;
    }

    MimeTypeProcessor.MimeType getMimeInfo() {
        return this.mimeInfo;
    }

    public String getMimeType() {
        MimeTypeProcessor.MimeType mimeType = this.mimeInfo;
        if (mimeType == null) return null;
        return MimeTypeProcessor.assemble(mimeType);
    }

    public String getParameter(String object) {
        String string2 = ((String)object).toLowerCase();
        if (string2.equals("humanpresentablename")) {
            return this.humanPresentableName;
        }
        object = this.mimeInfo;
        if (object == null) return null;
        return ((MimeTypeProcessor.MimeType)object).getParameter(string2);
    }

    public String getPrimaryType() {
        MimeTypeProcessor.MimeType mimeType = this.mimeInfo;
        if (mimeType == null) return null;
        return mimeType.getPrimaryType();
    }

    public Reader getReaderForText(Transferable object) throws UnsupportedFlavorException, IOException {
        if ((object = object.getTransferData(this)) == null) throw new IllegalArgumentException(Messages.getString("awt.16E"));
        if (object instanceof Reader) {
            object = (Reader)object;
            ((Reader)object).reset();
            return object;
        }
        if (object instanceof String) {
            return new StringReader((String)object);
        }
        if (object instanceof CharBuffer) {
            return new CharArrayReader(((CharBuffer)object).array());
        }
        if (object instanceof char[]) {
            return new CharArrayReader((char[])object);
        }
        String string2 = this.getCharset();
        if (object instanceof InputStream) {
            object = (InputStream)object;
            ((InputStream)object).reset();
        } else if (object instanceof ByteBuffer) {
            object = new ByteArrayInputStream(((ByteBuffer)object).array());
        } else {
            if (!(object instanceof byte[])) throw new IllegalArgumentException(Messages.getString("awt.16F"));
            object = new ByteArrayInputStream((byte[])object);
        }
        if (string2.length() != 0) return new InputStreamReader((InputStream)object, string2);
        return new InputStreamReader((InputStream)object);
    }

    public Class<?> getRepresentationClass() {
        return this.representationClass;
    }

    public String getSubType() {
        MimeTypeProcessor.MimeType mimeType = this.mimeInfo;
        if (mimeType == null) return null;
        return mimeType.getSubType();
    }

    public int hashCode() {
        return this.getKeyInfo().hashCode();
    }

    public boolean isFlavorJavaFileListType() {
        if (!List.class.isAssignableFrom(this.representationClass)) return false;
        if (!this.isMimeTypeEqual(javaFileListFlavor)) return false;
        return true;
    }

    public boolean isFlavorRemoteObjectType() {
        if (!this.isMimeTypeEqual(javaRemoteObjectMimeType)) return false;
        if (!this.isRepresentationClassRemote()) return false;
        return true;
    }

    public boolean isFlavorSerializedObjectType() {
        if (!this.isMimeTypeSerializedObject()) return false;
        if (!this.isRepresentationClassSerializable()) return false;
        return true;
    }

    public boolean isFlavorTextType() {
        if (this.equals(stringFlavor)) return true;
        if (this.equals(plainTextFlavor)) {
            return true;
        }
        Object object = this.mimeInfo;
        if (object != null && !((MimeTypeProcessor.MimeType)object).getPrimaryType().equals("text")) {
            return false;
        }
        object = this.getCharset();
        if (!this.isByteCodeFlavor()) return this.isUnicodeFlavor();
        if (((String)object).length() == 0) return true;
        return DataFlavor.isCharsetSupported((String)object);
    }

    public boolean isMimeTypeEqual(String string2) {
        try {
            return this.mimeInfo.equals(MimeTypeProcessor.parse(string2));
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return false;
        }
    }

    public final boolean isMimeTypeEqual(DataFlavor dataFlavor) {
        MimeTypeProcessor.MimeType mimeType = this.mimeInfo;
        if (mimeType != null) {
            return mimeType.equals(dataFlavor.mimeInfo);
        }
        if (dataFlavor.mimeInfo != null) return false;
        return true;
    }

    public boolean isMimeTypeSerializedObject() {
        return this.isMimeTypeEqual(javaSerializedObjectMimeType);
    }

    public boolean isRepresentationClassByteBuffer() {
        return ByteBuffer.class.isAssignableFrom(this.representationClass);
    }

    public boolean isRepresentationClassCharBuffer() {
        return CharBuffer.class.isAssignableFrom(this.representationClass);
    }

    public boolean isRepresentationClassInputStream() {
        return InputStream.class.isAssignableFrom(this.representationClass);
    }

    public boolean isRepresentationClassReader() {
        return Reader.class.isAssignableFrom(this.representationClass);
    }

    public boolean isRepresentationClassRemote() {
        return false;
    }

    public boolean isRepresentationClassSerializable() {
        return Serializable.class.isAssignableFrom(this.representationClass);
    }

    public boolean match(DataFlavor dataFlavor) {
        return this.equals(dataFlavor);
    }

    @Deprecated
    protected String normalizeMimeType(String string2) {
        return string2;
    }

    @Deprecated
    protected String normalizeMimeTypeParameter(String string2, String string3) {
        return string3;
    }

    @Override
    public void readExternal(ObjectInput class_) throws IOException, ClassNotFoundException {
        synchronized (this) {
            this.humanPresentableName = (String)class_.readObject();
            class_ = (MimeTypeProcessor.MimeType)class_.readObject();
            this.mimeInfo = class_;
            class_ = class_ != null ? Class.forName(((MimeTypeProcessor.MimeType)((Object)class_)).getParameter("class")) : null;
            this.representationClass = class_;
            return;
        }
    }

    public void setHumanPresentableName(String string2) {
        this.humanPresentableName = string2;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(this.getClass().getName()));
        stringBuilder.append("[MimeType=(");
        stringBuilder.append(this.getMimeType());
        stringBuilder.append(");humanPresentableName=");
        stringBuilder.append(this.humanPresentableName);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeExternal(ObjectOutput objectOutput) throws IOException {
        synchronized (this) {
            objectOutput.writeObject(this.humanPresentableName);
            objectOutput.writeObject(this.mimeInfo);
            return;
        }
    }
}

