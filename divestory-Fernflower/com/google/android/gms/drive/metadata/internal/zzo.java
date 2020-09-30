package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.SearchableCollectionMetadataField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public final class zzo extends zzl<DriveId> implements SearchableCollectionMetadataField<DriveId> {
   public static final zzg zzjk = new zzp();

   public zzo(int var1) {
      super("parents", Collections.emptySet(), Arrays.asList("parentsExtra", "dbInstanceId", "parentsExtraHolder"), 4100000);
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
               var2 = (DataHolder)var1.getParcelable("parentsExtraHolder");
            } catch (Throwable var22) {
               var10000 = var22;
               var10001 = false;
               break label196;
            }

            if (var2 != null) {
               try {
                  var2.close();
                  var1.remove("parentsExtraHolder");
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

   // $FF: synthetic method
   protected final Object zzb(Bundle var1) {
      return this.zzc(var1);
   }

   // $FF: synthetic method
   protected final Object zzc(DataHolder var1, int var2, int var3) {
      return this.zzd(var1, var2, var3);
   }

   protected final Collection<DriveId> zzc(Bundle var1) {
      Collection var2 = super.zzc(var1);
      return var2 == null ? null : new HashSet(var2);
   }

   protected final Collection<DriveId> zzd(DataHolder var1, int var2, int var3) {
      Bundle var4 = var1.getMetadata();
      ArrayList var5 = var4.getParcelableArrayList("parentsExtra");
      ArrayList var6 = var5;
      if (var5 == null) {
         if (var4.getParcelable("parentsExtraHolder") != null) {
            synchronized(var1){}

            label1306: {
               Throwable var10000;
               boolean var10001;
               label1295: {
                  DataHolder var129;
                  try {
                     var129 = (DataHolder)var1.getMetadata().getParcelable("parentsExtraHolder");
                  } catch (Throwable var127) {
                     var10000 = var127;
                     var10001 = false;
                     break label1295;
                  }

                  if (var129 == null) {
                     label1259:
                     try {
                        ;
                     } catch (Throwable var120) {
                        var10000 = var120;
                        var10001 = false;
                        break label1259;
                     }
                  } else {
                     label1291: {
                        label1307: {
                           label1308: {
                              HashMap var131;
                              int var7;
                              ArrayList var8;
                              try {
                                 var7 = var1.getCount();
                                 var8 = new ArrayList(var7);
                                 var131 = new HashMap(var7);
                              } catch (Throwable var126) {
                                 var10000 = var126;
                                 var10001 = false;
                                 break label1308;
                              }

                              byte var9 = 0;

                              for(var3 = 0; var3 < var7; ++var3) {
                                 try {
                                    int var10 = var1.getWindowIndex(var3);
                                    ParentDriveIdSet var11 = new ParentDriveIdSet();
                                    var8.add(var11);
                                    var131.put(var1.getLong("sqlId", var3, var10), var11);
                                 } catch (Throwable var125) {
                                    var10000 = var125;
                                    var10001 = false;
                                    break label1308;
                                 }
                              }

                              String var137;
                              String var138;
                              String var13;
                              try {
                                 Bundle var12 = var129.getMetadata();
                                 var13 = var12.getString("childSqlIdColumn");
                                 var137 = var12.getString("parentSqlIdColumn");
                                 var138 = var12.getString("parentResIdColumn");
                                 var7 = var129.getCount();
                              } catch (Throwable var124) {
                                 var10000 = var124;
                                 var10001 = false;
                                 break label1308;
                              }

                              for(var3 = var9; var3 < var7; ++var3) {
                                 try {
                                    int var136 = var129.getWindowIndex(var3);
                                    ParentDriveIdSet var14 = (ParentDriveIdSet)var131.get(var129.getLong(var13, var3, var136));
                                    zzq var15 = new zzq(var129.getString(var138, var3, var136), var129.getLong(var137, var3, var136), 1);
                                    var14.zzjj.add(var15);
                                 } catch (Throwable var123) {
                                    var10000 = var123;
                                    var10001 = false;
                                    break label1308;
                                 }
                              }

                              label1265:
                              try {
                                 var1.getMetadata().putParcelableArrayList("parentsExtra", var8);
                                 break label1307;
                              } catch (Throwable var122) {
                                 var10000 = var122;
                                 var10001 = false;
                                 break label1265;
                              }
                           }

                           Throwable var133 = var10000;

                           try {
                              var129.close();
                              var1.getMetadata().remove("parentsExtraHolder");
                              throw var133;
                           } catch (Throwable var119) {
                              var10000 = var119;
                              var10001 = false;
                              break label1291;
                           }
                        }

                        label1261:
                        try {
                           var129.close();
                           var1.getMetadata().remove("parentsExtraHolder");
                        } catch (Throwable var121) {
                           var10000 = var121;
                           var10001 = false;
                           break label1261;
                        }
                     }
                  }
                  break label1306;
               }

               while(true) {
                  Throwable var130 = var10000;

                  try {
                     throw var130;
                  } catch (Throwable var118) {
                     var10000 = var118;
                     var10001 = false;
                     continue;
                  }
               }
            }

            var5 = var4.getParcelableArrayList("parentsExtra");
         }

         var6 = var5;
         if (var5 == null) {
            return null;
         }
      }

      long var16 = var4.getLong("dbInstanceId");
      ParentDriveIdSet var132 = (ParentDriveIdSet)var6.get(var2);
      HashSet var128 = new HashSet();
      Iterator var134 = var132.zzjj.iterator();

      while(var134.hasNext()) {
         zzq var135 = (zzq)var134.next();
         var128.add(new DriveId(var135.zzad, var135.zzae, var16, var135.zzaf));
      }

      return var128;
   }
}
