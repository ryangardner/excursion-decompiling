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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.apache.harmony.awt.datatransfer.DTK;
import org.apache.harmony.awt.internal.nls.Messages;

public class DataFlavor implements Externalizable, Cloneable {
   public static final DataFlavor javaFileListFlavor = new DataFlavor("application/x-java-file-list; class=java.util.List", "application/x-java-file-list");
   public static final String javaJVMLocalObjectMimeType = "application/x-java-jvm-local-objectref";
   public static final String javaRemoteObjectMimeType = "application/x-java-remote-object";
   public static final String javaSerializedObjectMimeType = "application/x-java-serialized-object";
   @Deprecated
   public static final DataFlavor plainTextFlavor = new DataFlavor("text/plain; charset=unicode; class=java.io.InputStream", "Plain Text");
   private static DataFlavor plainUnicodeFlavor = null;
   private static final long serialVersionUID = 8367026044764648243L;
   private static final String[] sortedTextFlavors = new String[]{"text/sgml", "text/xml", "text/html", "text/rtf", "text/enriched", "text/richtext", "text/uri-list", "text/tab-separated-values", "text/t140", "text/rfc822-headers", "text/parityfec", "text/directory", "text/css", "text/calendar", "application/x-java-serialized-object", "text/plain"};
   public static final DataFlavor stringFlavor = new DataFlavor("application/x-java-serialized-object; class=java.lang.String", "Unicode String");
   private String humanPresentableName;
   private MimeTypeProcessor.MimeType mimeInfo;
   private Class<?> representationClass;

   public DataFlavor() {
      this.mimeInfo = null;
      this.humanPresentableName = null;
      this.representationClass = null;
   }

   public DataFlavor(Class<?> var1, String var2) {
      this.mimeInfo = new MimeTypeProcessor.MimeType("application", "x-java-serialized-object");
      if (var2 != null) {
         this.humanPresentableName = var2;
      } else {
         this.humanPresentableName = "application/x-java-serialized-object";
      }

      this.mimeInfo.addParameter("class", var1.getName());
      this.representationClass = var1;
   }

   public DataFlavor(String var1) throws ClassNotFoundException {
      this.init(var1, (String)null, (ClassLoader)null);
   }

   public DataFlavor(String var1, String var2) {
      try {
         this.init(var1, var2, (ClassLoader)null);
      } catch (ClassNotFoundException var3) {
         throw new IllegalArgumentException(Messages.getString("awt.16C", (Object)this.mimeInfo.getParameter("class")), var3);
      }
   }

   public DataFlavor(String var1, String var2, ClassLoader var3) throws ClassNotFoundException {
      this.init(var1, var2, var3);
   }

   private static List<DataFlavor> fetchTextFlavors(List<DataFlavor> var0, String var1) {
      LinkedList var2 = new LinkedList();
      Iterator var4 = var0.iterator();

      while(var4.hasNext()) {
         DataFlavor var3 = (DataFlavor)var4.next();
         if (var3.isFlavorTextType()) {
            if (var3.mimeInfo.getFullType().equals(var1)) {
               if (!var2.contains(var3)) {
                  var2.add(var3);
               }

               var4.remove();
            }
         } else {
            var4.remove();
         }
      }

      LinkedList var5 = var2;
      if (var2.isEmpty()) {
         var5 = null;
      }

      return var5;
   }

   private String getCharset() {
      if (this.mimeInfo != null && !this.isCharsetRedundant()) {
         String var1 = this.mimeInfo.getParameter("charset");
         if (this.isCharsetRequired() && (var1 == null || var1.length() == 0)) {
            return DTK.getDTK().getDefaultCharset();
         } else {
            return var1 == null ? "" : var1;
         }
      } else {
         return "";
      }
   }

   private static List<DataFlavor> getFlavors(List<DataFlavor> var0, Class<?> var1) {
      LinkedList var2 = new LinkedList();
      Iterator var3 = var0.iterator();

      while(var3.hasNext()) {
         DataFlavor var4 = (DataFlavor)var3.next();
         if (var4.representationClass.equals(var1)) {
            var2.add(var4);
         }
      }

      if (var2.isEmpty()) {
         var0 = null;
      }

      return var0;
   }

   private static List<DataFlavor> getFlavors(List<DataFlavor> var0, String[] var1) {
      LinkedList var2 = new LinkedList();
      Iterator var3 = var0.iterator();

      while(true) {
         while(var3.hasNext()) {
            DataFlavor var4 = (DataFlavor)var3.next();
            if (isCharsetSupported(var4.getCharset())) {
               int var5 = var1.length;

               for(int var6 = 0; var6 < var5; ++var6) {
                  if (Charset.forName(var1[var6]).equals(Charset.forName(var4.getCharset()))) {
                     var2.add(var4);
                  }
               }
            } else {
               var3.remove();
            }
         }

         if (var2.isEmpty()) {
            var0 = null;
         }

         return var0;
      }
   }

   private String getKeyInfo() {
      StringBuilder var1 = new StringBuilder(String.valueOf(this.mimeInfo.getFullType()));
      var1.append(";class=");
      var1.append(this.representationClass.getName());
      String var2 = var1.toString();
      String var3 = var2;
      if (this.mimeInfo.getPrimaryType().equals("text")) {
         if (this.isUnicodeFlavor()) {
            var3 = var2;
         } else {
            var1 = new StringBuilder(String.valueOf(var2));
            var1.append(";charset=");
            var1.append(this.getCharset().toLowerCase());
            var3 = var1.toString();
         }
      }

      return var3;
   }

   public static final DataFlavor getTextPlainUnicodeFlavor() {
      if (plainUnicodeFlavor == null) {
         StringBuilder var0 = new StringBuilder("text/plain; charset=");
         var0.append(DTK.getDTK().getDefaultCharset());
         var0.append("; class=java.io.InputStream");
         plainUnicodeFlavor = new DataFlavor(var0.toString(), "Plain Text");
      }

      return plainUnicodeFlavor;
   }

   private void init(String var1, String var2, ClassLoader var3) throws ClassNotFoundException {
      try {
         this.mimeInfo = MimeTypeProcessor.parse(var1);
      } catch (IllegalArgumentException var4) {
         throw new IllegalArgumentException(Messages.getString("awt.16D", (Object)var1));
      }

      if (var2 != null) {
         this.humanPresentableName = var2;
      } else {
         StringBuilder var5 = new StringBuilder(String.valueOf(this.mimeInfo.getPrimaryType()));
         var5.append('/');
         var5.append(this.mimeInfo.getSubType());
         this.humanPresentableName = var5.toString();
      }

      var2 = this.mimeInfo.getParameter("class");
      var1 = var2;
      if (var2 == null) {
         var1 = "java.io.InputStream";
         this.mimeInfo.addParameter("class", "java.io.InputStream");
      }

      Class var6;
      if (var3 == null) {
         var6 = Class.forName(var1);
      } else {
         var6 = var3.loadClass(var1);
      }

      this.representationClass = var6;
   }

   private boolean isByteCodeFlavor() {
      Class var1 = this.representationClass;
      return var1 != null && (var1.equals(InputStream.class) || this.representationClass.equals(ByteBuffer.class) || this.representationClass.equals(byte[].class));
   }

   private boolean isCharsetRedundant() {
      String var1 = this.mimeInfo.getFullType();
      return var1.equals("text/rtf") || var1.equals("text/tab-separated-values") || var1.equals("text/t140") || var1.equals("text/rfc822-headers") || var1.equals("text/parityfec");
   }

   private boolean isCharsetRequired() {
      String var1 = this.mimeInfo.getFullType();
      return var1.equals("text/sgml") || var1.equals("text/xml") || var1.equals("text/html") || var1.equals("text/enriched") || var1.equals("text/richtext") || var1.equals("text/uri-list") || var1.equals("text/directory") || var1.equals("text/css") || var1.equals("text/calendar") || var1.equals("application/x-java-serialized-object") || var1.equals("text/plain");
   }

   private static boolean isCharsetSupported(String var0) {
      try {
         boolean var1 = Charset.isSupported(var0);
         return var1;
      } catch (IllegalCharsetNameException var2) {
         return false;
      }
   }

   private boolean isUnicodeFlavor() {
      Class var1 = this.representationClass;
      return var1 != null && (var1.equals(Reader.class) || this.representationClass.equals(String.class) || this.representationClass.equals(CharBuffer.class) || this.representationClass.equals(char[].class));
   }

   private static List<DataFlavor> selectBestByAlphabet(List<DataFlavor> var0) {
      int var1 = var0.size();
      String[] var2 = new String[var1];
      LinkedList var3 = new LinkedList();

      for(int var4 = 0; var4 < var1; ++var4) {
         var2[var4] = ((DataFlavor)var0.get(var4)).getCharset();
      }

      Arrays.sort(var2, String.CASE_INSENSITIVE_ORDER);
      Iterator var5 = var0.iterator();

      while(var5.hasNext()) {
         DataFlavor var6 = (DataFlavor)var5.next();
         if (var2[0].equalsIgnoreCase(var6.getCharset())) {
            var3.add(var6);
         }
      }

      LinkedList var7 = var3;
      if (var3.isEmpty()) {
         var7 = null;
      }

      return var7;
   }

   private static DataFlavor selectBestByCharset(List<DataFlavor> var0) {
      List var1 = getFlavors(var0, new String[]{"UTF-16", "UTF-8", "UTF-16BE", "UTF-16LE"});
      List var2 = var1;
      if (var1 == null) {
         var1 = getFlavors(var0, new String[]{DTK.getDTK().getDefaultCharset()});
         var2 = var1;
         if (var1 == null) {
            var1 = getFlavors(var0, new String[]{"US-ASCII"});
            var2 = var1;
            if (var1 == null) {
               var2 = selectBestByAlphabet(var0);
            }
         }
      }

      if (var2 != null) {
         return var2.size() == 1 ? (DataFlavor)var2.get(0) : selectBestFlavorWOCharset(var2);
      } else {
         return null;
      }
   }

   private static DataFlavor selectBestFlavorWCharset(List<DataFlavor> var0) {
      List var1 = getFlavors(var0, Reader.class);
      if (var1 != null) {
         return (DataFlavor)var1.get(0);
      } else {
         var1 = getFlavors(var0, String.class);
         if (var1 != null) {
            return (DataFlavor)var1.get(0);
         } else {
            var1 = getFlavors(var0, CharBuffer.class);
            if (var1 != null) {
               return (DataFlavor)var1.get(0);
            } else {
               var1 = getFlavors(var0, char[].class);
               return var1 != null ? (DataFlavor)var1.get(0) : selectBestByCharset(var0);
            }
         }
      }
   }

   private static DataFlavor selectBestFlavorWOCharset(List<DataFlavor> var0) {
      List var1 = getFlavors(var0, InputStream.class);
      if (var1 != null) {
         return (DataFlavor)var1.get(0);
      } else {
         var1 = getFlavors(var0, ByteBuffer.class);
         if (var1 != null) {
            return (DataFlavor)var1.get(0);
         } else {
            var1 = getFlavors(var0, byte[].class);
            return var1 != null ? (DataFlavor)var1.get(0) : (DataFlavor)var0.get(0);
         }
      }
   }

   public static final DataFlavor selectBestTextFlavor(DataFlavor[] var0) {
      if (var0 == null) {
         return null;
      } else {
         List var1 = sortTextFlavorsByType(new LinkedList(Arrays.asList(var0)));
         if (var1.isEmpty()) {
            return null;
         } else {
            var1 = (List)var1.get(0);
            if (var1.size() == 1) {
               return (DataFlavor)var1.get(0);
            } else {
               return ((DataFlavor)var1.get(0)).getCharset().length() == 0 ? selectBestFlavorWOCharset(var1) : selectBestFlavorWCharset(var1);
            }
         }
      }
   }

   private static List<List<DataFlavor>> sortTextFlavorsByType(List<DataFlavor> var0) {
      LinkedList var1 = new LinkedList();
      String[] var2 = sortedTextFlavors;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         List var5 = fetchTextFlavors(var0, var2[var4]);
         if (var5 != null) {
            var1.addLast(var5);
         }
      }

      if (!var0.isEmpty()) {
         var1.addLast(var0);
      }

      return var1;
   }

   protected static final Class<?> tryToLoadClass(String var0, ClassLoader var1) throws ClassNotFoundException {
      Class var6;
      try {
         var6 = Class.forName(var0);
         return var6;
      } catch (ClassNotFoundException var5) {
         try {
            var6 = ClassLoader.getSystemClassLoader().loadClass(var0);
            return var6;
         } catch (ClassNotFoundException var4) {
            ClassLoader var2 = Thread.currentThread().getContextClassLoader();
            if (var2 != null) {
               try {
                  var6 = var2.loadClass(var0);
                  return var6;
               } catch (ClassNotFoundException var3) {
               }
            }

            return var1.loadClass(var0);
         }
      }
   }

   public Object clone() throws CloneNotSupportedException {
      DataFlavor var1 = new DataFlavor();
      var1.humanPresentableName = this.humanPresentableName;
      var1.representationClass = this.representationClass;
      MimeTypeProcessor.MimeType var2 = this.mimeInfo;
      if (var2 != null) {
         var2 = (MimeTypeProcessor.MimeType)var2.clone();
      } else {
         var2 = null;
      }

      var1.mimeInfo = var2;
      return var1;
   }

   public boolean equals(Object var1) {
      return var1 != null && var1 instanceof DataFlavor ? this.equals((DataFlavor)var1) : false;
   }

   @Deprecated
   public boolean equals(String var1) {
      return var1 == null ? false : this.isMimeTypeEqual(var1);
   }

   public boolean equals(DataFlavor var1) {
      if (var1 == this) {
         return true;
      } else if (var1 == null) {
         return false;
      } else {
         MimeTypeProcessor.MimeType var2 = this.mimeInfo;
         if (var2 == null) {
            return var1.mimeInfo == null;
         } else if (var2.equals(var1.mimeInfo) && this.representationClass.equals(var1.representationClass)) {
            if (this.mimeInfo.getPrimaryType().equals("text") && !this.isUnicodeFlavor()) {
               String var4 = this.getCharset();
               String var3 = var1.getCharset();
               return isCharsetSupported(var4) && isCharsetSupported(var3) ? Charset.forName(var4).equals(Charset.forName(var3)) : var4.equalsIgnoreCase(var3);
            } else {
               return true;
            }
         } else {
            return false;
         }
      }
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
      MimeTypeProcessor.MimeType var1 = this.mimeInfo;
      String var2;
      if (var1 != null) {
         var2 = MimeTypeProcessor.assemble(var1);
      } else {
         var2 = null;
      }

      return var2;
   }

   public String getParameter(String var1) {
      String var2 = var1.toLowerCase();
      if (var2.equals("humanpresentablename")) {
         return this.humanPresentableName;
      } else {
         MimeTypeProcessor.MimeType var3 = this.mimeInfo;
         if (var3 != null) {
            var1 = var3.getParameter(var2);
         } else {
            var1 = null;
         }

         return var1;
      }
   }

   public String getPrimaryType() {
      MimeTypeProcessor.MimeType var1 = this.mimeInfo;
      String var2;
      if (var1 != null) {
         var2 = var1.getPrimaryType();
      } else {
         var2 = null;
      }

      return var2;
   }

   public Reader getReaderForText(Transferable var1) throws UnsupportedFlavorException, IOException {
      Object var3 = var1.getTransferData(this);
      if (var3 != null) {
         if (var3 instanceof Reader) {
            Reader var4 = (Reader)var3;
            var4.reset();
            return var4;
         } else if (var3 instanceof String) {
            return new StringReader((String)var3);
         } else if (var3 instanceof CharBuffer) {
            return new CharArrayReader(((CharBuffer)var3).array());
         } else if (var3 instanceof char[]) {
            return new CharArrayReader((char[])var3);
         } else {
            String var2 = this.getCharset();
            if (var3 instanceof InputStream) {
               var3 = (InputStream)var3;
               ((InputStream)var3).reset();
            } else if (var3 instanceof ByteBuffer) {
               var3 = new ByteArrayInputStream(((ByteBuffer)var3).array());
            } else {
               if (!(var3 instanceof byte[])) {
                  throw new IllegalArgumentException(Messages.getString("awt.16F"));
               }

               var3 = new ByteArrayInputStream((byte[])var3);
            }

            return var2.length() == 0 ? new InputStreamReader((InputStream)var3) : new InputStreamReader((InputStream)var3, var2);
         }
      } else {
         throw new IllegalArgumentException(Messages.getString("awt.16E"));
      }
   }

   public Class<?> getRepresentationClass() {
      return this.representationClass;
   }

   public String getSubType() {
      MimeTypeProcessor.MimeType var1 = this.mimeInfo;
      String var2;
      if (var1 != null) {
         var2 = var1.getSubType();
      } else {
         var2 = null;
      }

      return var2;
   }

   public int hashCode() {
      return this.getKeyInfo().hashCode();
   }

   public boolean isFlavorJavaFileListType() {
      return List.class.isAssignableFrom(this.representationClass) && this.isMimeTypeEqual(javaFileListFlavor);
   }

   public boolean isFlavorRemoteObjectType() {
      return this.isMimeTypeEqual("application/x-java-remote-object") && this.isRepresentationClassRemote();
   }

   public boolean isFlavorSerializedObjectType() {
      return this.isMimeTypeSerializedObject() && this.isRepresentationClassSerializable();
   }

   public boolean isFlavorTextType() {
      if (!this.equals(stringFlavor) && !this.equals(plainTextFlavor)) {
         MimeTypeProcessor.MimeType var1 = this.mimeInfo;
         if (var1 != null && !var1.getPrimaryType().equals("text")) {
            return false;
         } else {
            String var2 = this.getCharset();
            if (this.isByteCodeFlavor()) {
               return var2.length() != 0 ? isCharsetSupported(var2) : true;
            } else {
               return this.isUnicodeFlavor();
            }
         }
      } else {
         return true;
      }
   }

   public boolean isMimeTypeEqual(String var1) {
      try {
         boolean var2 = this.mimeInfo.equals(MimeTypeProcessor.parse(var1));
         return var2;
      } catch (IllegalArgumentException var3) {
         return false;
      }
   }

   public final boolean isMimeTypeEqual(DataFlavor var1) {
      MimeTypeProcessor.MimeType var2 = this.mimeInfo;
      boolean var3;
      if (var2 != null) {
         var3 = var2.equals(var1.mimeInfo);
      } else if (var1.mimeInfo == null) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public boolean isMimeTypeSerializedObject() {
      return this.isMimeTypeEqual("application/x-java-serialized-object");
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

   public boolean match(DataFlavor var1) {
      return this.equals(var1);
   }

   @Deprecated
   protected String normalizeMimeType(String var1) {
      return var1;
   }

   @Deprecated
   protected String normalizeMimeTypeParameter(String var1, String var2) {
      return var2;
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      synchronized(this){}

      Throwable var10000;
      label128: {
         boolean var10001;
         MimeTypeProcessor.MimeType var14;
         try {
            this.humanPresentableName = (String)var1.readObject();
            var14 = (MimeTypeProcessor.MimeType)var1.readObject();
            this.mimeInfo = var14;
         } catch (Throwable var13) {
            var10000 = var13;
            var10001 = false;
            break label128;
         }

         Class var15;
         if (var14 != null) {
            try {
               var15 = Class.forName(var14.getParameter("class"));
            } catch (Throwable var12) {
               var10000 = var12;
               var10001 = false;
               break label128;
            }
         } else {
            var15 = null;
         }

         label115:
         try {
            this.representationClass = var15;
            return;
         } catch (Throwable var11) {
            var10000 = var11;
            var10001 = false;
            break label115;
         }
      }

      Throwable var16 = var10000;
      throw var16;
   }

   public void setHumanPresentableName(String var1) {
      this.humanPresentableName = var1;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder(String.valueOf(this.getClass().getName()));
      var1.append("[MimeType=(");
      var1.append(this.getMimeType());
      var1.append(");humanPresentableName=");
      var1.append(this.humanPresentableName);
      var1.append("]");
      return var1.toString();
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      synchronized(this){}

      try {
         var1.writeObject(this.humanPresentableName);
         var1.writeObject(this.mimeInfo);
      } finally {
         ;
      }

   }
}
