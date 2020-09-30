package com.google.common.hash;

import com.google.common.base.Preconditions;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Funnels {
   private Funnels() {
   }

   public static OutputStream asOutputStream(PrimitiveSink var0) {
      return new Funnels.SinkAsStream(var0);
   }

   public static Funnel<byte[]> byteArrayFunnel() {
      return Funnels.ByteArrayFunnel.INSTANCE;
   }

   public static Funnel<Integer> integerFunnel() {
      return Funnels.IntegerFunnel.INSTANCE;
   }

   public static Funnel<Long> longFunnel() {
      return Funnels.LongFunnel.INSTANCE;
   }

   public static <E> Funnel<Iterable<? extends E>> sequentialFunnel(Funnel<E> var0) {
      return new Funnels.SequentialFunnel(var0);
   }

   public static Funnel<CharSequence> stringFunnel(Charset var0) {
      return new Funnels.StringCharsetFunnel(var0);
   }

   public static Funnel<CharSequence> unencodedCharsFunnel() {
      return Funnels.UnencodedCharsFunnel.INSTANCE;
   }

   private static enum ByteArrayFunnel implements Funnel<byte[]> {
      INSTANCE;

      static {
         Funnels.ByteArrayFunnel var0 = new Funnels.ByteArrayFunnel("INSTANCE", 0);
         INSTANCE = var0;
      }

      public void funnel(byte[] var1, PrimitiveSink var2) {
         var2.putBytes(var1);
      }

      public String toString() {
         return "Funnels.byteArrayFunnel()";
      }
   }

   private static enum IntegerFunnel implements Funnel<Integer> {
      INSTANCE;

      static {
         Funnels.IntegerFunnel var0 = new Funnels.IntegerFunnel("INSTANCE", 0);
         INSTANCE = var0;
      }

      public void funnel(Integer var1, PrimitiveSink var2) {
         var2.putInt(var1);
      }

      public String toString() {
         return "Funnels.integerFunnel()";
      }
   }

   private static enum LongFunnel implements Funnel<Long> {
      INSTANCE;

      static {
         Funnels.LongFunnel var0 = new Funnels.LongFunnel("INSTANCE", 0);
         INSTANCE = var0;
      }

      public void funnel(Long var1, PrimitiveSink var2) {
         var2.putLong(var1);
      }

      public String toString() {
         return "Funnels.longFunnel()";
      }
   }

   private static class SequentialFunnel<E> implements Funnel<Iterable<? extends E>>, Serializable {
      private final Funnel<E> elementFunnel;

      SequentialFunnel(Funnel<E> var1) {
         this.elementFunnel = (Funnel)Preconditions.checkNotNull(var1);
      }

      public boolean equals(@NullableDecl Object var1) {
         if (var1 instanceof Funnels.SequentialFunnel) {
            Funnels.SequentialFunnel var2 = (Funnels.SequentialFunnel)var1;
            return this.elementFunnel.equals(var2.elementFunnel);
         } else {
            return false;
         }
      }

      public void funnel(Iterable<? extends E> var1, PrimitiveSink var2) {
         Iterator var4 = var1.iterator();

         while(var4.hasNext()) {
            Object var3 = var4.next();
            this.elementFunnel.funnel(var3, var2);
         }

      }

      public int hashCode() {
         return Funnels.SequentialFunnel.class.hashCode() ^ this.elementFunnel.hashCode();
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("Funnels.sequentialFunnel(");
         var1.append(this.elementFunnel);
         var1.append(")");
         return var1.toString();
      }
   }

   private static class SinkAsStream extends OutputStream {
      final PrimitiveSink sink;

      SinkAsStream(PrimitiveSink var1) {
         this.sink = (PrimitiveSink)Preconditions.checkNotNull(var1);
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("Funnels.asOutputStream(");
         var1.append(this.sink);
         var1.append(")");
         return var1.toString();
      }

      public void write(int var1) {
         this.sink.putByte((byte)var1);
      }

      public void write(byte[] var1) {
         this.sink.putBytes(var1);
      }

      public void write(byte[] var1, int var2, int var3) {
         this.sink.putBytes(var1, var2, var3);
      }
   }

   private static class StringCharsetFunnel implements Funnel<CharSequence>, Serializable {
      private final Charset charset;

      StringCharsetFunnel(Charset var1) {
         this.charset = (Charset)Preconditions.checkNotNull(var1);
      }

      public boolean equals(@NullableDecl Object var1) {
         if (var1 instanceof Funnels.StringCharsetFunnel) {
            Funnels.StringCharsetFunnel var2 = (Funnels.StringCharsetFunnel)var1;
            return this.charset.equals(var2.charset);
         } else {
            return false;
         }
      }

      public void funnel(CharSequence var1, PrimitiveSink var2) {
         var2.putString(var1, this.charset);
      }

      public int hashCode() {
         return Funnels.StringCharsetFunnel.class.hashCode() ^ this.charset.hashCode();
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("Funnels.stringFunnel(");
         var1.append(this.charset.name());
         var1.append(")");
         return var1.toString();
      }

      Object writeReplace() {
         return new Funnels.StringCharsetFunnel.SerializedForm(this.charset);
      }

      private static class SerializedForm implements Serializable {
         private static final long serialVersionUID = 0L;
         private final String charsetCanonicalName;

         SerializedForm(Charset var1) {
            this.charsetCanonicalName = var1.name();
         }

         private Object readResolve() {
            return Funnels.stringFunnel(Charset.forName(this.charsetCanonicalName));
         }
      }
   }

   private static enum UnencodedCharsFunnel implements Funnel<CharSequence> {
      INSTANCE;

      static {
         Funnels.UnencodedCharsFunnel var0 = new Funnels.UnencodedCharsFunnel("INSTANCE", 0);
         INSTANCE = var0;
      }

      public void funnel(CharSequence var1, PrimitiveSink var2) {
         var2.putUnencodedChars(var1);
      }

      public String toString() {
         return "Funnels.unencodedCharsFunnel()";
      }
   }
}
