package com.google.android.gms.internal.drive;

import android.os.Bundle;
import android.util.SparseArray;
import androidx.collection.LongSparseArray;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.metadata.CustomPropertyKey;
import com.google.android.gms.drive.metadata.internal.AppVisibleCustomProperties;
import java.util.Arrays;

public class zzid extends com.google.android.gms.drive.metadata.internal.zzm<AppVisibleCustomProperties> {
   public static final com.google.android.gms.drive.metadata.internal.zzg zzlc = new zzie();

   public zzid(int var1) {
      super("customProperties", Arrays.asList("hasCustomProperties", "sqlId"), Arrays.asList("customPropertiesExtra", "customPropertiesExtraHolder"), 5000000);
   }

   private static void zzc(DataHolder var0) {
      Bundle var1 = var0.getMetadata();
      if (var1 != null) {
         synchronized(var0){}

         Throwable var10000;
         boolean var10001;
         label196: {
            DataHolder var2;
            try {
               var2 = (DataHolder)var1.getParcelable("customPropertiesExtraHolder");
            } catch (Throwable var22) {
               var10000 = var22;
               var10001 = false;
               break label196;
            }

            if (var2 != null) {
               try {
                  var2.close();
                  var1.remove("customPropertiesExtraHolder");
               } catch (Throwable var21) {
                  var10000 = var21;
                  var10001 = false;
                  break label196;
               }
            }

            label183:
            try {
               return;
            } catch (Throwable var20) {
               var10000 = var20;
               var10001 = false;
               break label183;
            }
         }

         while(true) {
            Throwable var23 = var10000;

            try {
               throw var23;
            } catch (Throwable var19) {
               var10000 = var19;
               var10001 = false;
               continue;
            }
         }
      }
   }

   // $FF: synthetic method
   static void zzd(DataHolder var0) {
      zzc(var0);
   }

   private static AppVisibleCustomProperties zzf(DataHolder var0, int var1, int var2) {
      Bundle var3 = var0.getMetadata();
      SparseArray var4 = var3.getSparseParcelableArray("customPropertiesExtra");
      SparseArray var5 = var4;
      if (var4 == null) {
         if (var3.getParcelable("customPropertiesExtraHolder") != null) {
            synchronized(var0){}

            label1747: {
               Throwable var205;
               Throwable var10000;
               boolean var10001;
               label1737: {
                  DataHolder var6;
                  try {
                     var6 = (DataHolder)var0.getMetadata().getParcelable("customPropertiesExtraHolder");
                  } catch (Throwable var199) {
                     var10000 = var199;
                     var10001 = false;
                     break label1737;
                  }

                  if (var6 == null) {
                     label1693:
                     try {
                        ;
                     } catch (Throwable var189) {
                        var10000 = var189;
                        var10001 = false;
                        break label1693;
                     }
                  } else {
                     label1733: {
                        label1748: {
                           label1749: {
                              String var7;
                              String var8;
                              String var9;
                              String var10;
                              LongSparseArray var11;
                              try {
                                 Bundle var200 = var6.getMetadata();
                                 var7 = var200.getString("entryIdColumn");
                                 var8 = var200.getString("keyColumn");
                                 var9 = var200.getString("visibilityColumn");
                                 var10 = var200.getString("valueColumn");
                                 var11 = new LongSparseArray();
                              } catch (Throwable var198) {
                                 var10000 = var198;
                                 var10001 = false;
                                 break label1749;
                              }

                              var2 = 0;

                              AppVisibleCustomProperties.zza var204;
                              while(true) {
                                 long var13;
                                 com.google.android.gms.drive.metadata.internal.zzc var17;
                                 try {
                                    if (var2 >= var6.getCount()) {
                                       break;
                                    }

                                    int var12 = var6.getWindowIndex(var2);
                                    var13 = var6.getLong(var7, var2, var12);
                                    String var15 = var6.getString(var8, var2, var12);
                                    int var16 = var6.getInteger(var9, var2, var12);
                                    String var201 = var6.getString(var10, var2, var12);
                                    CustomPropertyKey var203 = new CustomPropertyKey(var15, var16);
                                    var17 = new com.google.android.gms.drive.metadata.internal.zzc(var203, var201);
                                    var204 = (AppVisibleCustomProperties.zza)var11.get(var13);
                                 } catch (Throwable var197) {
                                    var10000 = var197;
                                    var10001 = false;
                                    break label1749;
                                 }

                                 AppVisibleCustomProperties.zza var202 = var204;
                                 if (var204 == null) {
                                    try {
                                       var202 = new AppVisibleCustomProperties.zza();
                                       var11.put(var13, var202);
                                    } catch (Throwable var196) {
                                       var10000 = var196;
                                       var10001 = false;
                                       break label1749;
                                    }
                                 }

                                 try {
                                    var202.zza(var17);
                                 } catch (Throwable var195) {
                                    var10000 = var195;
                                    var10001 = false;
                                    break label1749;
                                 }

                                 ++var2;
                              }

                              try {
                                 var4 = new SparseArray();
                              } catch (Throwable var194) {
                                 var10000 = var194;
                                 var10001 = false;
                                 break label1749;
                              }

                              var2 = 0;

                              while(true) {
                                 try {
                                    if (var2 >= var0.getCount()) {
                                       break;
                                    }

                                    var204 = (AppVisibleCustomProperties.zza)var11.get(var0.getLong("sqlId", var2, var0.getWindowIndex(var2)));
                                 } catch (Throwable var193) {
                                    var10000 = var193;
                                    var10001 = false;
                                    break label1749;
                                 }

                                 if (var204 != null) {
                                    try {
                                       var4.append(var2, var204.zzbb());
                                    } catch (Throwable var192) {
                                       var10000 = var192;
                                       var10001 = false;
                                       break label1749;
                                    }
                                 }

                                 ++var2;
                              }

                              label1699:
                              try {
                                 var0.getMetadata().putSparseParcelableArray("customPropertiesExtra", var4);
                                 break label1748;
                              } catch (Throwable var191) {
                                 var10000 = var191;
                                 var10001 = false;
                                 break label1699;
                              }
                           }

                           var205 = var10000;

                           try {
                              var6.close();
                              var0.getMetadata().remove("customPropertiesExtraHolder");
                              throw var205;
                           } catch (Throwable var188) {
                              var10000 = var188;
                              var10001 = false;
                              break label1733;
                           }
                        }

                        label1695:
                        try {
                           var6.close();
                           var0.getMetadata().remove("customPropertiesExtraHolder");
                        } catch (Throwable var190) {
                           var10000 = var190;
                           var10001 = false;
                           break label1695;
                        }
                     }
                  }
                  break label1747;
               }

               while(true) {
                  var205 = var10000;

                  try {
                     throw var205;
                  } catch (Throwable var187) {
                     var10000 = var187;
                     var10001 = false;
                     continue;
                  }
               }
            }

            var4 = var3.getSparseParcelableArray("customPropertiesExtra");
         }

         var5 = var4;
         if (var4 == null) {
            return AppVisibleCustomProperties.zzjb;
         }
      }

      return (AppVisibleCustomProperties)var5.get(var1, AppVisibleCustomProperties.zzjb);
   }

   // $FF: synthetic method
   protected final Object zzc(DataHolder var1, int var2, int var3) {
      return zzf(var1, var2, var3);
   }
}
