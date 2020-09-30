package androidx.versionedparcelable;

import android.os.BadParcelableException;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.NetworkOnMainThreadException;
import android.os.Parcelable;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseBooleanArray;
import androidx.collection.ArrayMap;
import androidx.collection.ArraySet;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public abstract class VersionedParcel {
   private static final int EX_BAD_PARCELABLE = -2;
   private static final int EX_ILLEGAL_ARGUMENT = -3;
   private static final int EX_ILLEGAL_STATE = -5;
   private static final int EX_NETWORK_MAIN_THREAD = -6;
   private static final int EX_NULL_POINTER = -4;
   private static final int EX_PARCELABLE = -9;
   private static final int EX_SECURITY = -1;
   private static final int EX_UNSUPPORTED_OPERATION = -7;
   private static final String TAG = "VersionedParcel";
   private static final int TYPE_BINDER = 5;
   private static final int TYPE_FLOAT = 8;
   private static final int TYPE_INTEGER = 7;
   private static final int TYPE_PARCELABLE = 2;
   private static final int TYPE_SERIALIZABLE = 3;
   private static final int TYPE_STRING = 4;
   private static final int TYPE_VERSIONED_PARCELABLE = 1;
   protected final ArrayMap<String, Class> mParcelizerCache;
   protected final ArrayMap<String, Method> mReadCache;
   protected final ArrayMap<String, Method> mWriteCache;

   public VersionedParcel(ArrayMap<String, Method> var1, ArrayMap<String, Method> var2, ArrayMap<String, Class> var3) {
      this.mReadCache = var1;
      this.mWriteCache = var2;
      this.mParcelizerCache = var3;
   }

   private Exception createException(int var1, String var2) {
      switch(var1) {
      case -9:
         return (Exception)this.readParcelable();
      case -8:
      default:
         StringBuilder var3 = new StringBuilder();
         var3.append("Unknown exception code: ");
         var3.append(var1);
         var3.append(" msg ");
         var3.append(var2);
         return new RuntimeException(var3.toString());
      case -7:
         return new UnsupportedOperationException(var2);
      case -6:
         return new NetworkOnMainThreadException();
      case -5:
         return new IllegalStateException(var2);
      case -4:
         return new NullPointerException(var2);
      case -3:
         return new IllegalArgumentException(var2);
      case -2:
         return new BadParcelableException(var2);
      case -1:
         return new SecurityException(var2);
      }
   }

   private Class findParcelClass(Class<? extends VersionedParcelable> var1) throws ClassNotFoundException {
      Class var2 = (Class)this.mParcelizerCache.get(var1.getName());
      Class var3 = var2;
      if (var2 == null) {
         var3 = Class.forName(String.format("%s.%sParcelizer", var1.getPackage().getName(), var1.getSimpleName()), false, var1.getClassLoader());
         this.mParcelizerCache.put(var1.getName(), var3);
      }

      return var3;
   }

   private Method getReadMethod(String var1) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException {
      Method var2 = (Method)this.mReadCache.get(var1);
      Method var3 = var2;
      if (var2 == null) {
         System.currentTimeMillis();
         var3 = Class.forName(var1, true, VersionedParcel.class.getClassLoader()).getDeclaredMethod("read", VersionedParcel.class);
         this.mReadCache.put(var1, var3);
      }

      return var3;
   }

   protected static Throwable getRootCause(Throwable var0) {
      while(var0.getCause() != null) {
         var0 = var0.getCause();
      }

      return var0;
   }

   private <T> int getType(T var1) {
      if (var1 instanceof String) {
         return 4;
      } else if (var1 instanceof Parcelable) {
         return 2;
      } else if (var1 instanceof VersionedParcelable) {
         return 1;
      } else if (var1 instanceof Serializable) {
         return 3;
      } else if (var1 instanceof IBinder) {
         return 5;
      } else if (var1 instanceof Integer) {
         return 7;
      } else if (var1 instanceof Float) {
         return 8;
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append(var1.getClass().getName());
         var2.append(" cannot be VersionedParcelled");
         throw new IllegalArgumentException(var2.toString());
      }
   }

   private Method getWriteMethod(Class var1) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException {
      Method var2 = (Method)this.mWriteCache.get(var1.getName());
      Method var3 = var2;
      if (var2 == null) {
         Class var4 = this.findParcelClass(var1);
         System.currentTimeMillis();
         var3 = var4.getDeclaredMethod("write", var1, VersionedParcel.class);
         this.mWriteCache.put(var1.getName(), var3);
      }

      return var3;
   }

   private <T, S extends Collection<T>> S readCollection(S var1) {
      int var2 = this.readInt();
      if (var2 < 0) {
         return null;
      } else {
         if (var2 != 0) {
            int var3 = this.readInt();
            if (var2 < 0) {
               return null;
            }

            int var4 = var2;
            if (var3 != 1) {
               var4 = var2;
               if (var3 == 2) {
                  while(var4 > 0) {
                     var1.add(this.readParcelable());
                     --var4;
                  }
               } else {
                  var4 = var2;
                  if (var3 == 3) {
                     while(var4 > 0) {
                        var1.add(this.readSerializable());
                        --var4;
                     }
                  } else {
                     var4 = var2;
                     if (var3 == 4) {
                        while(var4 > 0) {
                           var1.add(this.readString());
                           --var4;
                        }
                     } else if (var3 == 5) {
                        while(var2 > 0) {
                           var1.add(this.readStrongBinder());
                           --var2;
                        }
                     }
                  }
               }
            } else {
               while(var4 > 0) {
                  var1.add(this.readVersionedParcelable());
                  --var4;
               }
            }
         }

         return var1;
      }
   }

   private Exception readException(int var1, String var2) {
      return this.createException(var1, var2);
   }

   private int readExceptionCode() {
      return this.readInt();
   }

   private <T> void writeCollection(Collection<T> var1) {
      if (var1 == null) {
         this.writeInt(-1);
      } else {
         int var2 = var1.size();
         this.writeInt(var2);
         if (var2 > 0) {
            var2 = this.getType(var1.iterator().next());
            this.writeInt(var2);
            Iterator var3;
            switch(var2) {
            case 1:
               var3 = var1.iterator();

               while(var3.hasNext()) {
                  this.writeVersionedParcelable((VersionedParcelable)var3.next());
               }

               return;
            case 2:
               var3 = var1.iterator();

               while(var3.hasNext()) {
                  this.writeParcelable((Parcelable)var3.next());
               }

               return;
            case 3:
               var3 = var1.iterator();

               while(var3.hasNext()) {
                  this.writeSerializable((Serializable)var3.next());
               }

               return;
            case 4:
               var3 = var1.iterator();

               while(var3.hasNext()) {
                  this.writeString((String)var3.next());
               }

               return;
            case 5:
               var3 = var1.iterator();

               while(var3.hasNext()) {
                  this.writeStrongBinder((IBinder)var3.next());
               }
            case 6:
            default:
               break;
            case 7:
               var3 = var1.iterator();

               while(var3.hasNext()) {
                  this.writeInt((Integer)var3.next());
               }

               return;
            case 8:
               var3 = var1.iterator();

               while(var3.hasNext()) {
                  this.writeFloat((Float)var3.next());
               }
            }
         }

      }
   }

   private <T> void writeCollection(Collection<T> var1, int var2) {
      this.setOutputField(var2);
      this.writeCollection(var1);
   }

   private void writeSerializable(Serializable var1) {
      if (var1 == null) {
         this.writeString((String)null);
      } else {
         String var2 = var1.getClass().getName();
         this.writeString(var2);
         ByteArrayOutputStream var3 = new ByteArrayOutputStream();

         try {
            ObjectOutputStream var6 = new ObjectOutputStream(var3);
            var6.writeObject(var1);
            var6.close();
            this.writeByteArray(var3.toByteArray());
         } catch (IOException var5) {
            StringBuilder var4 = new StringBuilder();
            var4.append("VersionedParcelable encountered IOException writing serializable object (name = ");
            var4.append(var2);
            var4.append(")");
            throw new RuntimeException(var4.toString(), var5);
         }
      }
   }

   private void writeVersionedParcelableCreator(VersionedParcelable var1) {
      Class var2;
      try {
         var2 = this.findParcelClass(var1.getClass());
      } catch (ClassNotFoundException var4) {
         StringBuilder var3 = new StringBuilder();
         var3.append(var1.getClass().getSimpleName());
         var3.append(" does not have a Parcelizer");
         throw new RuntimeException(var3.toString(), var4);
      }

      this.writeString(var2.getName());
   }

   protected abstract void closeField();

   protected abstract VersionedParcel createSubParcel();

   public boolean isStream() {
      return false;
   }

   protected <T> T[] readArray(T[] var1) {
      int var2 = this.readInt();
      if (var2 < 0) {
         return null;
      } else {
         ArrayList var3 = new ArrayList(var2);
         if (var2 != 0) {
            int var4 = this.readInt();
            if (var2 < 0) {
               return null;
            }

            int var5 = var2;
            if (var4 != 1) {
               var5 = var2;
               if (var4 == 2) {
                  while(var5 > 0) {
                     var3.add(this.readParcelable());
                     --var5;
                  }
               } else {
                  var5 = var2;
                  if (var4 == 3) {
                     while(var5 > 0) {
                        var3.add(this.readSerializable());
                        --var5;
                     }
                  } else {
                     var5 = var2;
                     if (var4 == 4) {
                        while(var5 > 0) {
                           var3.add(this.readString());
                           --var5;
                        }
                     } else if (var4 == 5) {
                        while(var2 > 0) {
                           var3.add(this.readStrongBinder());
                           --var2;
                        }
                     }
                  }
               }
            } else {
               while(var5 > 0) {
                  var3.add(this.readVersionedParcelable());
                  --var5;
               }
            }
         }

         return var3.toArray(var1);
      }
   }

   public <T> T[] readArray(T[] var1, int var2) {
      return !this.readField(var2) ? var1 : this.readArray(var1);
   }

   protected abstract boolean readBoolean();

   public boolean readBoolean(boolean var1, int var2) {
      return !this.readField(var2) ? var1 : this.readBoolean();
   }

   protected boolean[] readBooleanArray() {
      int var1 = this.readInt();
      if (var1 < 0) {
         return null;
      } else {
         boolean[] var2 = new boolean[var1];

         for(int var3 = 0; var3 < var1; ++var3) {
            boolean var4;
            if (this.readInt() != 0) {
               var4 = true;
            } else {
               var4 = false;
            }

            var2[var3] = var4;
         }

         return var2;
      }
   }

   public boolean[] readBooleanArray(boolean[] var1, int var2) {
      return !this.readField(var2) ? var1 : this.readBooleanArray();
   }

   protected abstract Bundle readBundle();

   public Bundle readBundle(Bundle var1, int var2) {
      return !this.readField(var2) ? var1 : this.readBundle();
   }

   public byte readByte(byte var1, int var2) {
      return !this.readField(var2) ? var1 : (byte)(this.readInt() & 255);
   }

   protected abstract byte[] readByteArray();

   public byte[] readByteArray(byte[] var1, int var2) {
      return !this.readField(var2) ? var1 : this.readByteArray();
   }

   public char[] readCharArray(char[] var1, int var2) {
      if (!this.readField(var2)) {
         return var1;
      } else {
         int var3 = this.readInt();
         if (var3 < 0) {
            return null;
         } else {
            var1 = new char[var3];

            for(var2 = 0; var2 < var3; ++var2) {
               var1[var2] = (char)((char)this.readInt());
            }

            return var1;
         }
      }
   }

   protected abstract CharSequence readCharSequence();

   public CharSequence readCharSequence(CharSequence var1, int var2) {
      return !this.readField(var2) ? var1 : this.readCharSequence();
   }

   protected abstract double readDouble();

   public double readDouble(double var1, int var3) {
      return !this.readField(var3) ? var1 : this.readDouble();
   }

   protected double[] readDoubleArray() {
      int var1 = this.readInt();
      if (var1 < 0) {
         return null;
      } else {
         double[] var2 = new double[var1];

         for(int var3 = 0; var3 < var1; ++var3) {
            var2[var3] = this.readDouble();
         }

         return var2;
      }
   }

   public double[] readDoubleArray(double[] var1, int var2) {
      return !this.readField(var2) ? var1 : this.readDoubleArray();
   }

   public Exception readException(Exception var1, int var2) {
      if (!this.readField(var2)) {
         return var1;
      } else {
         var2 = this.readExceptionCode();
         if (var2 != 0) {
            var1 = this.readException(var2, this.readString());
         }

         return var1;
      }
   }

   protected abstract boolean readField(int var1);

   protected abstract float readFloat();

   public float readFloat(float var1, int var2) {
      return !this.readField(var2) ? var1 : this.readFloat();
   }

   protected float[] readFloatArray() {
      int var1 = this.readInt();
      if (var1 < 0) {
         return null;
      } else {
         float[] var2 = new float[var1];

         for(int var3 = 0; var3 < var1; ++var3) {
            var2[var3] = this.readFloat();
         }

         return var2;
      }
   }

   public float[] readFloatArray(float[] var1, int var2) {
      return !this.readField(var2) ? var1 : this.readFloatArray();
   }

   protected <T extends VersionedParcelable> T readFromParcel(String var1, VersionedParcel var2) {
      try {
         VersionedParcelable var7 = (VersionedParcelable)this.getReadMethod(var1).invoke((Object)null, var2);
         return var7;
      } catch (IllegalAccessException var3) {
         throw new RuntimeException("VersionedParcel encountered IllegalAccessException", var3);
      } catch (InvocationTargetException var4) {
         if (var4.getCause() instanceof RuntimeException) {
            throw (RuntimeException)var4.getCause();
         } else {
            throw new RuntimeException("VersionedParcel encountered InvocationTargetException", var4);
         }
      } catch (NoSuchMethodException var5) {
         throw new RuntimeException("VersionedParcel encountered NoSuchMethodException", var5);
      } catch (ClassNotFoundException var6) {
         throw new RuntimeException("VersionedParcel encountered ClassNotFoundException", var6);
      }
   }

   protected abstract int readInt();

   public int readInt(int var1, int var2) {
      return !this.readField(var2) ? var1 : this.readInt();
   }

   protected int[] readIntArray() {
      int var1 = this.readInt();
      if (var1 < 0) {
         return null;
      } else {
         int[] var2 = new int[var1];

         for(int var3 = 0; var3 < var1; ++var3) {
            var2[var3] = this.readInt();
         }

         return var2;
      }
   }

   public int[] readIntArray(int[] var1, int var2) {
      return !this.readField(var2) ? var1 : this.readIntArray();
   }

   public <T> List<T> readList(List<T> var1, int var2) {
      return !this.readField(var2) ? var1 : (List)this.readCollection(new ArrayList());
   }

   protected abstract long readLong();

   public long readLong(long var1, int var3) {
      return !this.readField(var3) ? var1 : this.readLong();
   }

   protected long[] readLongArray() {
      int var1 = this.readInt();
      if (var1 < 0) {
         return null;
      } else {
         long[] var2 = new long[var1];

         for(int var3 = 0; var3 < var1; ++var3) {
            var2[var3] = this.readLong();
         }

         return var2;
      }
   }

   public long[] readLongArray(long[] var1, int var2) {
      return !this.readField(var2) ? var1 : this.readLongArray();
   }

   public <K, V> Map<K, V> readMap(Map<K, V> var1, int var2) {
      if (!this.readField(var2)) {
         return var1;
      } else {
         int var3 = this.readInt();
         if (var3 < 0) {
            return null;
         } else {
            ArrayMap var4 = new ArrayMap();
            if (var3 == 0) {
               return var4;
            } else {
               ArrayList var5 = new ArrayList();
               ArrayList var6 = new ArrayList();
               this.readCollection(var5);
               this.readCollection(var6);

               for(var2 = 0; var2 < var3; ++var2) {
                  var4.put(var5.get(var2), var6.get(var2));
               }

               return var4;
            }
         }
      }
   }

   protected abstract <T extends Parcelable> T readParcelable();

   public <T extends Parcelable> T readParcelable(T var1, int var2) {
      return !this.readField(var2) ? var1 : this.readParcelable();
   }

   protected Serializable readSerializable() {
      String var1 = this.readString();
      if (var1 == null) {
         return null;
      } else {
         ByteArrayInputStream var2 = new ByteArrayInputStream(this.readByteArray());

         try {
            ObjectInputStream var8 = new ObjectInputStream(var2) {
               protected Class<?> resolveClass(ObjectStreamClass var1) throws IOException, ClassNotFoundException {
                  Class var2 = Class.forName(var1.getName(), false, this.getClass().getClassLoader());
                  return var2 != null ? var2 : super.resolveClass(var1);
               }
            };
            Serializable var7 = (Serializable)var8.readObject();
            return var7;
         } catch (IOException var4) {
            StringBuilder var6 = new StringBuilder();
            var6.append("VersionedParcelable encountered IOException reading a Serializable object (name = ");
            var6.append(var1);
            var6.append(")");
            throw new RuntimeException(var6.toString(), var4);
         } catch (ClassNotFoundException var5) {
            StringBuilder var3 = new StringBuilder();
            var3.append("VersionedParcelable encountered ClassNotFoundException reading a Serializable object (name = ");
            var3.append(var1);
            var3.append(")");
            throw new RuntimeException(var3.toString(), var5);
         }
      }
   }

   public <T> Set<T> readSet(Set<T> var1, int var2) {
      return !this.readField(var2) ? var1 : (Set)this.readCollection(new ArraySet());
   }

   public Size readSize(Size var1, int var2) {
      if (!this.readField(var2)) {
         return var1;
      } else {
         return this.readBoolean() ? new Size(this.readInt(), this.readInt()) : null;
      }
   }

   public SizeF readSizeF(SizeF var1, int var2) {
      if (!this.readField(var2)) {
         return var1;
      } else {
         return this.readBoolean() ? new SizeF(this.readFloat(), this.readFloat()) : null;
      }
   }

   public SparseBooleanArray readSparseBooleanArray(SparseBooleanArray var1, int var2) {
      if (!this.readField(var2)) {
         return var1;
      } else {
         int var3 = this.readInt();
         if (var3 < 0) {
            return null;
         } else {
            var1 = new SparseBooleanArray(var3);

            for(var2 = 0; var2 < var3; ++var2) {
               var1.put(this.readInt(), this.readBoolean());
            }

            return var1;
         }
      }
   }

   protected abstract String readString();

   public String readString(String var1, int var2) {
      return !this.readField(var2) ? var1 : this.readString();
   }

   protected abstract IBinder readStrongBinder();

   public IBinder readStrongBinder(IBinder var1, int var2) {
      return !this.readField(var2) ? var1 : this.readStrongBinder();
   }

   protected <T extends VersionedParcelable> T readVersionedParcelable() {
      String var1 = this.readString();
      return var1 == null ? null : this.readFromParcel(var1, this.createSubParcel());
   }

   public <T extends VersionedParcelable> T readVersionedParcelable(T var1, int var2) {
      return !this.readField(var2) ? var1 : this.readVersionedParcelable();
   }

   protected abstract void setOutputField(int var1);

   public void setSerializationFlags(boolean var1, boolean var2) {
   }

   protected <T> void writeArray(T[] var1) {
      if (var1 == null) {
         this.writeInt(-1);
      } else {
         int var2 = var1.length;
         this.writeInt(var2);
         if (var2 > 0) {
            byte var3 = 0;
            byte var4 = 0;
            byte var5 = 0;
            int var6 = 0;
            byte var7 = 0;
            int var8 = this.getType(var1[0]);
            this.writeInt(var8);
            if (var8 != 1) {
               var6 = var5;
               if (var8 == 2) {
                  while(var6 < var2) {
                     this.writeParcelable((Parcelable)var1[var6]);
                     ++var6;
                  }
               } else {
                  var6 = var4;
                  if (var8 == 3) {
                     while(var6 < var2) {
                        this.writeSerializable((Serializable)var1[var6]);
                        ++var6;
                     }
                  } else {
                     var6 = var3;
                     if (var8 == 4) {
                        while(var6 < var2) {
                           this.writeString((String)var1[var6]);
                           ++var6;
                        }
                     } else {
                        var6 = var7;
                        if (var8 == 5) {
                           while(var6 < var2) {
                              this.writeStrongBinder((IBinder)var1[var6]);
                              ++var6;
                           }
                        }
                     }
                  }
               }
            } else {
               while(var6 < var2) {
                  this.writeVersionedParcelable((VersionedParcelable)var1[var6]);
                  ++var6;
               }
            }
         }

      }
   }

   public <T> void writeArray(T[] var1, int var2) {
      this.setOutputField(var2);
      this.writeArray(var1);
   }

   protected abstract void writeBoolean(boolean var1);

   public void writeBoolean(boolean var1, int var2) {
      this.setOutputField(var2);
      this.writeBoolean(var1);
   }

   protected void writeBooleanArray(boolean[] var1) {
      if (var1 != null) {
         int var2 = var1.length;
         this.writeInt(var2);

         for(int var3 = 0; var3 < var2; ++var3) {
            this.writeInt(var1[var3]);
         }
      } else {
         this.writeInt(-1);
      }

   }

   public void writeBooleanArray(boolean[] var1, int var2) {
      this.setOutputField(var2);
      this.writeBooleanArray(var1);
   }

   protected abstract void writeBundle(Bundle var1);

   public void writeBundle(Bundle var1, int var2) {
      this.setOutputField(var2);
      this.writeBundle(var1);
   }

   public void writeByte(byte var1, int var2) {
      this.setOutputField(var2);
      this.writeInt(var1);
   }

   protected abstract void writeByteArray(byte[] var1);

   public void writeByteArray(byte[] var1, int var2) {
      this.setOutputField(var2);
      this.writeByteArray(var1);
   }

   protected abstract void writeByteArray(byte[] var1, int var2, int var3);

   public void writeByteArray(byte[] var1, int var2, int var3, int var4) {
      this.setOutputField(var4);
      this.writeByteArray(var1, var2, var3);
   }

   public void writeCharArray(char[] var1, int var2) {
      this.setOutputField(var2);
      if (var1 != null) {
         int var3 = var1.length;
         this.writeInt(var3);

         for(var2 = 0; var2 < var3; ++var2) {
            this.writeInt(var1[var2]);
         }
      } else {
         this.writeInt(-1);
      }

   }

   protected abstract void writeCharSequence(CharSequence var1);

   public void writeCharSequence(CharSequence var1, int var2) {
      this.setOutputField(var2);
      this.writeCharSequence(var1);
   }

   protected abstract void writeDouble(double var1);

   public void writeDouble(double var1, int var3) {
      this.setOutputField(var3);
      this.writeDouble(var1);
   }

   protected void writeDoubleArray(double[] var1) {
      if (var1 != null) {
         int var2 = var1.length;
         this.writeInt(var2);

         for(int var3 = 0; var3 < var2; ++var3) {
            this.writeDouble(var1[var3]);
         }
      } else {
         this.writeInt(-1);
      }

   }

   public void writeDoubleArray(double[] var1, int var2) {
      this.setOutputField(var2);
      this.writeDoubleArray(var1);
   }

   public void writeException(Exception var1, int var2) {
      this.setOutputField(var2);
      if (var1 == null) {
         this.writeNoException();
      } else {
         byte var3 = 0;
         if (var1 instanceof Parcelable && var1.getClass().getClassLoader() == Parcelable.class.getClassLoader()) {
            var3 = -9;
         } else if (var1 instanceof SecurityException) {
            var3 = -1;
         } else if (var1 instanceof BadParcelableException) {
            var3 = -2;
         } else if (var1 instanceof IllegalArgumentException) {
            var3 = -3;
         } else if (var1 instanceof NullPointerException) {
            var3 = -4;
         } else if (var1 instanceof IllegalStateException) {
            var3 = -5;
         } else if (var1 instanceof NetworkOnMainThreadException) {
            var3 = -6;
         } else if (var1 instanceof UnsupportedOperationException) {
            var3 = -7;
         }

         this.writeInt(var3);
         if (var3 == 0) {
            if (var1 instanceof RuntimeException) {
               throw (RuntimeException)var1;
            } else {
               throw new RuntimeException(var1);
            }
         } else {
            this.writeString(var1.getMessage());
            if (var3 == -9) {
               this.writeParcelable((Parcelable)var1);
            }

         }
      }
   }

   protected abstract void writeFloat(float var1);

   public void writeFloat(float var1, int var2) {
      this.setOutputField(var2);
      this.writeFloat(var1);
   }

   protected void writeFloatArray(float[] var1) {
      if (var1 != null) {
         int var2 = var1.length;
         this.writeInt(var2);

         for(int var3 = 0; var3 < var2; ++var3) {
            this.writeFloat(var1[var3]);
         }
      } else {
         this.writeInt(-1);
      }

   }

   public void writeFloatArray(float[] var1, int var2) {
      this.setOutputField(var2);
      this.writeFloatArray(var1);
   }

   protected abstract void writeInt(int var1);

   public void writeInt(int var1, int var2) {
      this.setOutputField(var2);
      this.writeInt(var1);
   }

   protected void writeIntArray(int[] var1) {
      if (var1 != null) {
         int var2 = var1.length;
         this.writeInt(var2);

         for(int var3 = 0; var3 < var2; ++var3) {
            this.writeInt(var1[var3]);
         }
      } else {
         this.writeInt(-1);
      }

   }

   public void writeIntArray(int[] var1, int var2) {
      this.setOutputField(var2);
      this.writeIntArray(var1);
   }

   public <T> void writeList(List<T> var1, int var2) {
      this.writeCollection(var1, var2);
   }

   protected abstract void writeLong(long var1);

   public void writeLong(long var1, int var3) {
      this.setOutputField(var3);
      this.writeLong(var1);
   }

   protected void writeLongArray(long[] var1) {
      if (var1 != null) {
         int var2 = var1.length;
         this.writeInt(var2);

         for(int var3 = 0; var3 < var2; ++var3) {
            this.writeLong(var1[var3]);
         }
      } else {
         this.writeInt(-1);
      }

   }

   public void writeLongArray(long[] var1, int var2) {
      this.setOutputField(var2);
      this.writeLongArray(var1);
   }

   public <K, V> void writeMap(Map<K, V> var1, int var2) {
      this.setOutputField(var2);
      if (var1 == null) {
         this.writeInt(-1);
      } else {
         var2 = var1.size();
         this.writeInt(var2);
         if (var2 != 0) {
            ArrayList var3 = new ArrayList();
            ArrayList var4 = new ArrayList();
            Iterator var5 = var1.entrySet().iterator();

            while(var5.hasNext()) {
               Entry var6 = (Entry)var5.next();
               var3.add(var6.getKey());
               var4.add(var6.getValue());
            }

            this.writeCollection(var3);
            this.writeCollection(var4);
         }
      }
   }

   protected void writeNoException() {
      this.writeInt(0);
   }

   protected abstract void writeParcelable(Parcelable var1);

   public void writeParcelable(Parcelable var1, int var2) {
      this.setOutputField(var2);
      this.writeParcelable(var1);
   }

   public void writeSerializable(Serializable var1, int var2) {
      this.setOutputField(var2);
      this.writeSerializable(var1);
   }

   public <T> void writeSet(Set<T> var1, int var2) {
      this.writeCollection(var1, var2);
   }

   public void writeSize(Size var1, int var2) {
      this.setOutputField(var2);
      boolean var3;
      if (var1 != null) {
         var3 = true;
      } else {
         var3 = false;
      }

      this.writeBoolean(var3);
      if (var1 != null) {
         this.writeInt(var1.getWidth());
         this.writeInt(var1.getHeight());
      }

   }

   public void writeSizeF(SizeF var1, int var2) {
      this.setOutputField(var2);
      boolean var3;
      if (var1 != null) {
         var3 = true;
      } else {
         var3 = false;
      }

      this.writeBoolean(var3);
      if (var1 != null) {
         this.writeFloat(var1.getWidth());
         this.writeFloat(var1.getHeight());
      }

   }

   public void writeSparseBooleanArray(SparseBooleanArray var1, int var2) {
      this.setOutputField(var2);
      if (var1 == null) {
         this.writeInt(-1);
      } else {
         int var3 = var1.size();
         this.writeInt(var3);

         for(var2 = 0; var2 < var3; ++var2) {
            this.writeInt(var1.keyAt(var2));
            this.writeBoolean(var1.valueAt(var2));
         }

      }
   }

   protected abstract void writeString(String var1);

   public void writeString(String var1, int var2) {
      this.setOutputField(var2);
      this.writeString(var1);
   }

   protected abstract void writeStrongBinder(IBinder var1);

   public void writeStrongBinder(IBinder var1, int var2) {
      this.setOutputField(var2);
      this.writeStrongBinder(var1);
   }

   protected abstract void writeStrongInterface(IInterface var1);

   public void writeStrongInterface(IInterface var1, int var2) {
      this.setOutputField(var2);
      this.writeStrongInterface(var1);
   }

   protected <T extends VersionedParcelable> void writeToParcel(T var1, VersionedParcel var2) {
      try {
         this.getWriteMethod(var1.getClass()).invoke((Object)null, var1, var2);
      } catch (IllegalAccessException var3) {
         throw new RuntimeException("VersionedParcel encountered IllegalAccessException", var3);
      } catch (InvocationTargetException var4) {
         if (var4.getCause() instanceof RuntimeException) {
            throw (RuntimeException)var4.getCause();
         } else {
            throw new RuntimeException("VersionedParcel encountered InvocationTargetException", var4);
         }
      } catch (NoSuchMethodException var5) {
         throw new RuntimeException("VersionedParcel encountered NoSuchMethodException", var5);
      } catch (ClassNotFoundException var6) {
         throw new RuntimeException("VersionedParcel encountered ClassNotFoundException", var6);
      }
   }

   protected void writeVersionedParcelable(VersionedParcelable var1) {
      if (var1 == null) {
         this.writeString((String)null);
      } else {
         this.writeVersionedParcelableCreator(var1);
         VersionedParcel var2 = this.createSubParcel();
         this.writeToParcel(var1, var2);
         var2.closeField();
      }
   }

   public void writeVersionedParcelable(VersionedParcelable var1, int var2) {
      this.setOutputField(var2);
      this.writeVersionedParcelable(var1);
   }

   public static class ParcelException extends RuntimeException {
      public ParcelException(Throwable var1) {
         super(var1);
      }
   }
}
