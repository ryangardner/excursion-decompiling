package com.google.android.gms.common.data;

import android.content.ContentValues;
import android.database.CharArrayBuffer;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.CursorWindow;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.google.android.gms.common.internal.Asserts;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.sqlite.CursorWrapper;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public final class DataHolder extends AbstractSafeParcelable implements Closeable {
   public static final Creator<DataHolder> CREATOR = new zac();
   private static final DataHolder.Builder zak = new zab(new String[0], (String)null);
   private final int zaa;
   private final String[] zab;
   private Bundle zac;
   private final CursorWindow[] zad;
   private final int zae;
   private final Bundle zaf;
   private int[] zag;
   private int zah;
   private boolean zai;
   private boolean zaj;

   DataHolder(int var1, String[] var2, CursorWindow[] var3, int var4, Bundle var5) {
      this.zai = false;
      this.zaj = true;
      this.zaa = var1;
      this.zab = var2;
      this.zad = var3;
      this.zae = var4;
      this.zaf = var5;
   }

   public DataHolder(Cursor var1, int var2, Bundle var3) {
      this(new CursorWrapper(var1), var2, var3);
   }

   private DataHolder(DataHolder.Builder var1, int var2, Bundle var3) {
      this(var1.zaa, zaa((DataHolder.Builder)var1, -1), var2, (Bundle)null);
   }

   private DataHolder(DataHolder.Builder var1, int var2, Bundle var3, int var4) {
      this(var1.zaa, zaa((DataHolder.Builder)var1, -1), var2, var3);
   }

   // $FF: synthetic method
   DataHolder(DataHolder.Builder var1, int var2, Bundle var3, int var4, zab var5) {
      this(var1, var2, var3, -1);
   }

   // $FF: synthetic method
   DataHolder(DataHolder.Builder var1, int var2, Bundle var3, zab var4) {
      this((DataHolder.Builder)var1, var2, (Bundle)null);
   }

   private DataHolder(CursorWrapper var1, int var2, Bundle var3) {
      this(var1.getColumnNames(), zaa(var1), var2, var3);
   }

   public DataHolder(String[] var1, CursorWindow[] var2, int var3, Bundle var4) {
      this.zai = false;
      this.zaj = true;
      this.zaa = 1;
      this.zab = (String[])Preconditions.checkNotNull(var1);
      this.zad = (CursorWindow[])Preconditions.checkNotNull(var2);
      this.zae = var3;
      this.zaf = var4;
      this.zaa();
   }

   public static DataHolder.Builder builder(String[] var0) {
      return new DataHolder.Builder(var0, (String)null, (zab)null);
   }

   public static DataHolder empty(int var0) {
      return new DataHolder(zak, var0, (Bundle)null);
   }

   private final void zaa(String var1, int var2) {
      Bundle var3 = this.zac;
      if (var3 != null && var3.containsKey(var1)) {
         if (!this.isClosed()) {
            if (var2 < 0 || var2 >= this.zah) {
               throw new CursorIndexOutOfBoundsException(var2, this.zah);
            }
         } else {
            throw new IllegalArgumentException("Buffer is closed.");
         }
      } else {
         var1 = String.valueOf(var1);
         if (var1.length() != 0) {
            var1 = "No such column: ".concat(var1);
         } else {
            var1 = new String("No such column: ");
         }

         throw new IllegalArgumentException(var1);
      }
   }

   private static CursorWindow[] zaa(DataHolder.Builder var0, int var1) {
      var1 = var0.zaa.length;
      byte var2 = 0;
      if (var1 == 0) {
         return new CursorWindow[0];
      } else {
         ArrayList var3 = var0.zab;
         int var4 = var3.size();
         CursorWindow var5 = new CursorWindow(false);
         ArrayList var6 = new ArrayList();
         var6.add(var5);
         var5.setNumColumns(var0.zaa.length);
         var1 = 0;

         for(boolean var7 = false; var1 < var4; ++var1) {
            RuntimeException var10000;
            int var38;
            label197: {
               boolean var8;
               boolean var10001;
               try {
                  var8 = var5.allocRow();
               } catch (RuntimeException var32) {
                  var10000 = var32;
                  var10001 = false;
                  break label197;
               }

               StringBuilder var37;
               if (!var8) {
                  CursorWindow var9;
                  try {
                     var37 = new StringBuilder(72);
                     var37.append("Allocating additional cursor window for large data set (row ");
                     var37.append(var1);
                     var37.append(")");
                     Log.d("DataHolder", var37.toString());
                     var9 = new CursorWindow(false);
                     var9.setStartPosition(var1);
                     var9.setNumColumns(var0.zaa.length);
                     var6.add(var9);
                  } catch (RuntimeException var31) {
                     var10000 = var31;
                     var10001 = false;
                     break label197;
                  }

                  var5 = var9;

                  try {
                     if (!var9.allocRow()) {
                        Log.e("DataHolder", "Unable to allocate row to hold data.");
                        var6.remove(var9);
                        return (CursorWindow[])var6.toArray(new CursorWindow[var6.size()]);
                     }
                  } catch (RuntimeException var30) {
                     var10000 = var30;
                     var10001 = false;
                     break label197;
                  }
               }

               Map var10;
               try {
                  var10 = (Map)var3.get(var1);
               } catch (RuntimeException var29) {
                  var10000 = var29;
                  var10001 = false;
                  break label197;
               }

               int var11 = 0;
               var8 = true;

               while(true) {
                  try {
                     if (var11 >= var0.zaa.length) {
                        break;
                     }
                  } catch (RuntimeException var28) {
                     var10000 = var28;
                     var10001 = false;
                     break label197;
                  }

                  if (!var8) {
                     break;
                  }

                  Object var12;
                  String var39;
                  try {
                     var39 = var0.zaa[var11];
                     var12 = var10.get(var39);
                  } catch (RuntimeException var20) {
                     var10000 = var20;
                     var10001 = false;
                     break label197;
                  }

                  if (var12 == null) {
                     try {
                        var8 = var5.putNull(var1, var11);
                     } catch (RuntimeException var19) {
                        var10000 = var19;
                        var10001 = false;
                        break label197;
                     }
                  } else {
                     label203: {
                        try {
                           if (var12 instanceof String) {
                              var8 = var5.putString((String)var12, var1, var11);
                              break label203;
                           }
                        } catch (RuntimeException var23) {
                           var10000 = var23;
                           var10001 = false;
                           break label197;
                        }

                        try {
                           if (var12 instanceof Long) {
                              var8 = var5.putLong((Long)var12, var1, var11);
                              break label203;
                           }
                        } catch (RuntimeException var27) {
                           var10000 = var27;
                           var10001 = false;
                           break label197;
                        }

                        try {
                           if (var12 instanceof Integer) {
                              var8 = var5.putLong((long)(Integer)var12, var1, var11);
                              break label203;
                           }
                        } catch (RuntimeException var22) {
                           var10000 = var22;
                           var10001 = false;
                           break label197;
                        }

                        long var13;
                        label201: {
                           label153: {
                              label152: {
                                 try {
                                    if (!(var12 instanceof Boolean)) {
                                       break label153;
                                    }

                                    if ((Boolean)var12) {
                                       break label152;
                                    }
                                 } catch (RuntimeException var26) {
                                    var10000 = var26;
                                    var10001 = false;
                                    break label197;
                                 }

                                 var13 = 0L;
                                 break label201;
                              }

                              var13 = 1L;
                              break label201;
                           }

                           try {
                              if (var12 instanceof byte[]) {
                                 var8 = var5.putBlob((byte[])var12, var1, var11);
                                 break label203;
                              }
                           } catch (RuntimeException var25) {
                              var10000 = var25;
                              var10001 = false;
                              break label197;
                           }

                           try {
                              if (var12 instanceof Double) {
                                 var8 = var5.putDouble((Double)var12, var1, var11);
                                 break label203;
                              }
                           } catch (RuntimeException var21) {
                              var10000 = var21;
                              var10001 = false;
                              break label197;
                           }

                           try {
                              if (var12 instanceof Float) {
                                 var8 = var5.putDouble((double)(Float)var12, var1, var11);
                                 break label203;
                              }
                           } catch (RuntimeException var24) {
                              var10000 = var24;
                              var10001 = false;
                              break label197;
                           }

                           try {
                              String var36 = String.valueOf(var12);
                              var38 = String.valueOf(var39).length();
                              var1 = String.valueOf(var36).length();
                              var37 = new StringBuilder(var38 + 32 + var1);
                              var37.append("Unsupported object for column ");
                              var37.append(var39);
                              var37.append(": ");
                              var37.append(var36);
                              IllegalArgumentException var33 = new IllegalArgumentException(var37.toString());
                              throw var33;
                           } catch (RuntimeException var15) {
                              var10000 = var15;
                              var10001 = false;
                              break label197;
                           }
                        }

                        try {
                           var8 = var5.putLong(var13, var1, var11);
                        } catch (RuntimeException var18) {
                           var10000 = var18;
                           var10001 = false;
                           break label197;
                        }
                     }
                  }

                  ++var11;
               }

               if (var8) {
                  var7 = false;
                  continue;
               }

               if (!var7) {
                  label115: {
                     try {
                        StringBuilder var40 = new StringBuilder(74);
                        var40.append("Couldn't populate window data for row ");
                        var40.append(var1);
                        var40.append(" - allocating new window.");
                        Log.d("DataHolder", var40.toString());
                        var5.freeLastRow();
                        var5 = new CursorWindow(false);
                        var5.setStartPosition(var1);
                        var5.setNumColumns(var0.zaa.length);
                        var6.add(var5);
                     } catch (RuntimeException var17) {
                        var10000 = var17;
                        var10001 = false;
                        break label115;
                     }

                     --var1;
                     var7 = true;
                     continue;
                  }
               } else {
                  try {
                     DataHolder.zaa var35 = new DataHolder.zaa("Could not add the value to a new CursorWindow. The size of value may be larger than what a CursorWindow can handle.");
                     throw var35;
                  } catch (RuntimeException var16) {
                     var10000 = var16;
                     var10001 = false;
                  }
               }
            }

            RuntimeException var34 = var10000;
            var38 = var6.size();

            for(var1 = var2; var1 < var38; ++var1) {
               ((CursorWindow)var6.get(var1)).close();
            }

            throw var34;
         }

         return (CursorWindow[])var6.toArray(new CursorWindow[var6.size()]);
      }
   }

   private static CursorWindow[] zaa(CursorWrapper var0) {
      ArrayList var1 = new ArrayList();

      label430: {
         Throwable var10000;
         label434: {
            int var2;
            CursorWindow var3;
            boolean var10001;
            try {
               var2 = var0.getCount();
               var3 = var0.getWindow();
            } catch (Throwable var47) {
               var10000 = var47;
               var10001 = false;
               break label434;
            }

            int var4;
            label425: {
               if (var3 != null) {
                  try {
                     if (var3.getStartPosition() == 0) {
                        var3.acquireReference();
                        var0.setWindow((CursorWindow)null);
                        var1.add(var3);
                        var4 = var3.getNumRows();
                        break label425;
                     }
                  } catch (Throwable var46) {
                     var10000 = var46;
                     var10001 = false;
                     break label434;
                  }
               }

               var4 = 0;
            }

            while(true) {
               if (var4 >= var2) {
                  break label430;
               }

               try {
                  if (!var0.moveToPosition(var4)) {
                     break label430;
                  }

                  var3 = var0.getWindow();
               } catch (Throwable var45) {
                  var10000 = var45;
                  var10001 = false;
                  break;
               }

               if (var3 != null) {
                  try {
                     var3.acquireReference();
                     var0.setWindow((CursorWindow)null);
                  } catch (Throwable var44) {
                     var10000 = var44;
                     var10001 = false;
                     break;
                  }
               } else {
                  try {
                     var3 = new CursorWindow(false);
                     var3.setStartPosition(var4);
                     var0.fillWindow(var4, var3);
                  } catch (Throwable var43) {
                     var10000 = var43;
                     var10001 = false;
                     break;
                  }
               }

               int var5;
               try {
                  if (var3.getNumRows() == 0) {
                     break label430;
                  }

                  var1.add(var3);
                  var5 = var3.getStartPosition();
                  var4 = var3.getNumRows();
               } catch (Throwable var42) {
                  var10000 = var42;
                  var10001 = false;
                  break;
               }

               var4 += var5;
            }
         }

         Throwable var48 = var10000;
         var0.close();
         throw var48;
      }

      var0.close();
      return (CursorWindow[])var1.toArray(new CursorWindow[var1.size()]);
   }

   public final void close() {
      synchronized(this){}

      Throwable var10000;
      boolean var10001;
      label217: {
         label221: {
            try {
               if (this.zai) {
                  break label221;
               }

               this.zai = true;
            } catch (Throwable var22) {
               var10000 = var22;
               var10001 = false;
               break label217;
            }

            int var1 = 0;

            while(true) {
               try {
                  if (var1 >= this.zad.length) {
                     break;
                  }

                  this.zad[var1].close();
               } catch (Throwable var21) {
                  var10000 = var21;
                  var10001 = false;
                  break label217;
               }

               ++var1;
            }
         }

         label204:
         try {
            return;
         } catch (Throwable var20) {
            var10000 = var20;
            var10001 = false;
            break label204;
         }
      }

      while(true) {
         Throwable var2 = var10000;

         try {
            throw var2;
         } catch (Throwable var19) {
            var10000 = var19;
            var10001 = false;
            continue;
         }
      }
   }

   protected final void finalize() throws Throwable {
      try {
         if (this.zaj && this.zad.length > 0 && !this.isClosed()) {
            this.close();
            String var1 = this.toString();
            int var2 = String.valueOf(var1).length();
            StringBuilder var3 = new StringBuilder(var2 + 178);
            var3.append("Internal data leak within a DataBuffer object detected!  Be sure to explicitly call release() on all DataBuffer extending objects when you are done with them. (internal object: ");
            var3.append(var1);
            var3.append(")");
            Log.e("DataBuffer", var3.toString());
         }
      } finally {
         super.finalize();
      }

   }

   public final boolean getBoolean(String var1, int var2, int var3) {
      this.zaa(var1, var2);
      return Long.valueOf(this.zad[var3].getLong(var2, this.zac.getInt(var1))) == 1L;
   }

   public final byte[] getByteArray(String var1, int var2, int var3) {
      this.zaa(var1, var2);
      return this.zad[var3].getBlob(var2, this.zac.getInt(var1));
   }

   public final int getCount() {
      return this.zah;
   }

   public final int getInteger(String var1, int var2, int var3) {
      this.zaa(var1, var2);
      return this.zad[var3].getInt(var2, this.zac.getInt(var1));
   }

   public final long getLong(String var1, int var2, int var3) {
      this.zaa(var1, var2);
      return this.zad[var3].getLong(var2, this.zac.getInt(var1));
   }

   public final Bundle getMetadata() {
      return this.zaf;
   }

   public final int getStatusCode() {
      return this.zae;
   }

   public final String getString(String var1, int var2, int var3) {
      this.zaa(var1, var2);
      return this.zad[var3].getString(var2, this.zac.getInt(var1));
   }

   public final int getWindowIndex(int var1) {
      int var2 = 0;
      boolean var3;
      if (var1 >= 0 && var1 < this.zah) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkState(var3);

      int var5;
      while(true) {
         int[] var4 = this.zag;
         var5 = var2;
         if (var2 >= var4.length) {
            break;
         }

         if (var1 < var4[var2]) {
            var5 = var2 - 1;
            break;
         }

         ++var2;
      }

      var1 = var5;
      if (var5 == this.zag.length) {
         var1 = var5 - 1;
      }

      return var1;
   }

   public final boolean hasColumn(String var1) {
      return this.zac.containsKey(var1);
   }

   public final boolean hasNull(String var1, int var2, int var3) {
      this.zaa(var1, var2);
      return this.zad[var3].isNull(var2, this.zac.getInt(var1));
   }

   public final boolean isClosed() {
      // $FF: Couldn't be decompiled
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeStringArray(var1, 1, this.zab, false);
      SafeParcelWriter.writeTypedArray(var1, 2, this.zad, var2, false);
      SafeParcelWriter.writeInt(var1, 3, this.getStatusCode());
      SafeParcelWriter.writeBundle(var1, 4, this.getMetadata(), false);
      SafeParcelWriter.writeInt(var1, 1000, this.zaa);
      SafeParcelWriter.finishObjectHeader(var1, var3);
      if ((var2 & 1) != 0) {
         this.close();
      }

   }

   public final float zaa(String var1, int var2, int var3) {
      this.zaa(var1, var2);
      return this.zad[var3].getFloat(var2, this.zac.getInt(var1));
   }

   public final void zaa() {
      this.zac = new Bundle();
      byte var1 = 0;
      int var2 = 0;

      while(true) {
         String[] var3 = this.zab;
         if (var2 >= var3.length) {
            this.zag = new int[this.zad.length];
            int var4 = 0;
            var2 = var1;

            while(true) {
               CursorWindow[] var6 = this.zad;
               if (var2 >= var6.length) {
                  this.zah = var4;
                  return;
               }

               this.zag[var2] = var4;
               int var5 = var6[var2].getStartPosition();
               var4 += this.zad[var2].getNumRows() - (var4 - var5);
               ++var2;
            }
         }

         this.zac.putInt(var3[var2], var2);
         ++var2;
      }
   }

   public final void zaa(String var1, int var2, int var3, CharArrayBuffer var4) {
      this.zaa(var1, var2);
      this.zad[var3].copyStringToBuffer(var2, this.zac.getInt(var1), var4);
   }

   public final double zab(String var1, int var2, int var3) {
      this.zaa(var1, var2);
      return this.zad[var3].getDouble(var2, this.zac.getInt(var1));
   }

   public static class Builder {
      private final String[] zaa;
      private final ArrayList<HashMap<String, Object>> zab;
      private final String zac;
      private final HashMap<Object, Integer> zad;
      private boolean zae;
      private String zaf;

      private Builder(String[] var1, String var2) {
         this.zaa = (String[])Preconditions.checkNotNull(var1);
         this.zab = new ArrayList();
         this.zac = null;
         this.zad = new HashMap();
         this.zae = false;
         this.zaf = null;
      }

      // $FF: synthetic method
      Builder(String[] var1, String var2, zab var3) {
         this(var1, (String)null);
      }

      public DataHolder build(int var1) {
         return new DataHolder(this, var1, (Bundle)null, (zab)null);
      }

      public DataHolder build(int var1, Bundle var2) {
         return new DataHolder(this, var1, var2, -1, (zab)null);
      }

      public DataHolder.Builder withRow(ContentValues var1) {
         Asserts.checkNotNull(var1);
         HashMap var2 = new HashMap(var1.size());
         Iterator var3 = var1.valueSet().iterator();

         while(var3.hasNext()) {
            Entry var4 = (Entry)var3.next();
            var2.put((String)var4.getKey(), var4.getValue());
         }

         return this.zaa(var2);
      }

      public DataHolder.Builder zaa(HashMap<String, Object> var1) {
         int var3;
         label22: {
            Asserts.checkNotNull(var1);
            String var2 = this.zac;
            if (var2 != null) {
               Object var5 = var1.get(var2);
               if (var5 != null) {
                  Integer var4 = (Integer)this.zad.get(var5);
                  if (var4 != null) {
                     var3 = var4;
                     break label22;
                  }

                  this.zad.put(var5, this.zab.size());
               }
            }

            var3 = -1;
         }

         if (var3 == -1) {
            this.zab.add(var1);
         } else {
            this.zab.remove(var3);
            this.zab.add(var3, var1);
         }

         this.zae = false;
         return this;
      }
   }

   public static final class zaa extends RuntimeException {
      public zaa(String var1) {
         super(var1);
      }
   }
}
