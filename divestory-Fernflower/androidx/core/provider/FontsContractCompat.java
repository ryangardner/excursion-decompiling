package androidx.core.provider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.Signature;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Build.VERSION;
import android.provider.BaseColumns;
import androidx.collection.LruCache;
import androidx.collection.SimpleArrayMap;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.TypefaceCompat;
import androidx.core.graphics.TypefaceCompatUtil;
import androidx.core.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class FontsContractCompat {
   private static final int BACKGROUND_THREAD_KEEP_ALIVE_DURATION_MS = 10000;
   public static final String PARCEL_FONT_RESULTS = "font_results";
   static final int RESULT_CODE_PROVIDER_NOT_FOUND = -1;
   static final int RESULT_CODE_WRONG_CERTIFICATES = -2;
   private static final SelfDestructiveThread sBackgroundThread = new SelfDestructiveThread("fonts", 10, 10000);
   private static final Comparator<byte[]> sByteArrayComparator = new Comparator<byte[]>() {
      public int compare(byte[] var1, byte[] var2) {
         int var3;
         int var4;
         if (var1.length != var2.length) {
            var3 = var1.length;
            var4 = var2.length;
         } else {
            var4 = 0;

            while(true) {
               if (var4 >= var1.length) {
                  return 0;
               }

               if (var1[var4] != var2[var4]) {
                  var3 = var1[var4];
                  var4 = var2[var4];
                  break;
               }

               ++var4;
            }
         }

         return var3 - var4;
      }
   };
   static final Object sLock = new Object();
   static final SimpleArrayMap<String, ArrayList<SelfDestructiveThread.ReplyCallback<FontsContractCompat.TypefaceResult>>> sPendingReplies = new SimpleArrayMap();
   static final LruCache<String, Typeface> sTypefaceCache = new LruCache(16);

   private FontsContractCompat() {
   }

   public static Typeface buildTypeface(Context var0, CancellationSignal var1, FontsContractCompat.FontInfo[] var2) {
      return TypefaceCompat.createFromFontInfo(var0, var1, var2, 0);
   }

   private static List<byte[]> convertToByteArrayList(Signature[] var0) {
      ArrayList var1 = new ArrayList();

      for(int var2 = 0; var2 < var0.length; ++var2) {
         var1.add(var0[var2].toByteArray());
      }

      return var1;
   }

   private static boolean equalsByteArrayList(List<byte[]> var0, List<byte[]> var1) {
      if (var0.size() != var1.size()) {
         return false;
      } else {
         for(int var2 = 0; var2 < var0.size(); ++var2) {
            if (!Arrays.equals((byte[])var0.get(var2), (byte[])var1.get(var2))) {
               return false;
            }
         }

         return true;
      }
   }

   public static FontsContractCompat.FontFamilyResult fetchFonts(Context var0, CancellationSignal var1, FontRequest var2) throws NameNotFoundException {
      ProviderInfo var3 = getProvider(var0.getPackageManager(), var2, var0.getResources());
      return var3 == null ? new FontsContractCompat.FontFamilyResult(1, (FontsContractCompat.FontInfo[])null) : new FontsContractCompat.FontFamilyResult(0, getFontFromProvider(var0, var2, var3.authority, var1));
   }

   private static List<List<byte[]>> getCertificates(FontRequest var0, Resources var1) {
      return var0.getCertificates() != null ? var0.getCertificates() : FontResourcesParserCompat.readCerts(var1, var0.getCertificatesArrayResId());
   }

   static FontsContractCompat.FontInfo[] getFontFromProvider(Context var0, FontRequest var1, String var2, CancellationSignal var3) {
      ArrayList var4 = new ArrayList();
      Uri var5 = (new Builder()).scheme("content").authority(var2).build();
      Uri var6 = (new Builder()).scheme("content").authority(var2).appendPath("file").build();
      Object var7 = null;
      Cursor var725 = (Cursor)var7;

      Cursor var721;
      ArrayList var724;
      label5938: {
         ArrayList var727;
         label5942: {
            Throwable var10000;
            label5943: {
               boolean var10001;
               label5944: {
                  ContentResolver var720;
                  String var722;
                  label5945: {
                     try {
                        if (VERSION.SDK_INT <= 16) {
                           break label5945;
                        }
                     } catch (Throwable var719) {
                        var10000 = var719;
                        var10001 = false;
                        break label5943;
                     }

                     var725 = (Cursor)var7;

                     try {
                        var720 = var0.getContentResolver();
                     } catch (Throwable var718) {
                        var10000 = var718;
                        var10001 = false;
                        break label5943;
                     }

                     var725 = (Cursor)var7;

                     try {
                        var722 = var1.getQuery();
                     } catch (Throwable var717) {
                        var10000 = var717;
                        var10001 = false;
                        break label5943;
                     }

                     var725 = (Cursor)var7;

                     try {
                        var721 = var720.query(var5, new String[]{"_id", "file_id", "font_ttc_index", "font_variation_settings", "font_weight", "font_italic", "result_code"}, "query = ?", new String[]{var722}, (String)null, var3);
                        break label5944;
                     } catch (Throwable var716) {
                        var10000 = var716;
                        var10001 = false;
                        break label5943;
                     }
                  }

                  var725 = (Cursor)var7;

                  try {
                     var720 = var0.getContentResolver();
                  } catch (Throwable var715) {
                     var10000 = var715;
                     var10001 = false;
                     break label5943;
                  }

                  var725 = (Cursor)var7;

                  try {
                     var722 = var1.getQuery();
                  } catch (Throwable var714) {
                     var10000 = var714;
                     var10001 = false;
                     break label5943;
                  }

                  var725 = (Cursor)var7;

                  try {
                     var721 = var720.query(var5, new String[]{"_id", "file_id", "font_ttc_index", "font_variation_settings", "font_weight", "font_italic", "result_code"}, "query = ?", new String[]{var722}, (String)null);
                  } catch (Throwable var713) {
                     var10000 = var713;
                     var10001 = false;
                     break label5943;
                  }
               }

               var724 = var4;
               if (var721 == null) {
                  break label5938;
               }

               var724 = var4;
               var725 = var721;

               try {
                  if (var721.getCount() <= 0) {
                     break label5938;
                  }
               } catch (Throwable var712) {
                  var10000 = var712;
                  var10001 = false;
                  break label5943;
               }

               var725 = var721;

               int var8;
               try {
                  var8 = var721.getColumnIndex("result_code");
               } catch (Throwable var711) {
                  var10000 = var711;
                  var10001 = false;
                  break label5943;
               }

               var725 = var721;

               try {
                  var727 = new ArrayList;
               } catch (Throwable var710) {
                  var10000 = var710;
                  var10001 = false;
                  break label5943;
               }

               var725 = var721;

               try {
                  var727.<init>();
               } catch (Throwable var709) {
                  var10000 = var709;
                  var10001 = false;
                  break label5943;
               }

               var725 = var721;

               int var9;
               try {
                  var9 = var721.getColumnIndex("_id");
               } catch (Throwable var708) {
                  var10000 = var708;
                  var10001 = false;
                  break label5943;
               }

               var725 = var721;

               int var10;
               try {
                  var10 = var721.getColumnIndex("file_id");
               } catch (Throwable var707) {
                  var10000 = var707;
                  var10001 = false;
                  break label5943;
               }

               var725 = var721;

               int var11;
               try {
                  var11 = var721.getColumnIndex("font_ttc_index");
               } catch (Throwable var706) {
                  var10000 = var706;
                  var10001 = false;
                  break label5943;
               }

               var725 = var721;

               int var12;
               try {
                  var12 = var721.getColumnIndex("font_weight");
               } catch (Throwable var705) {
                  var10000 = var705;
                  var10001 = false;
                  break label5943;
               }

               var725 = var721;

               int var13;
               try {
                  var13 = var721.getColumnIndex("font_italic");
               } catch (Throwable var704) {
                  var10000 = var704;
                  var10001 = false;
                  break label5943;
               }

               while(true) {
                  var725 = var721;

                  try {
                     if (!var721.moveToNext()) {
                        break label5942;
                     }
                  } catch (Throwable var702) {
                     var10000 = var702;
                     var10001 = false;
                     break;
                  }

                  int var14;
                  if (var8 != -1) {
                     var725 = var721;

                     try {
                        var14 = var721.getInt(var8);
                     } catch (Throwable var701) {
                        var10000 = var701;
                        var10001 = false;
                        break;
                     }
                  } else {
                     var14 = 0;
                  }

                  int var15;
                  if (var11 != -1) {
                     var725 = var721;

                     try {
                        var15 = var721.getInt(var11);
                     } catch (Throwable var700) {
                        var10000 = var700;
                        var10001 = false;
                        break;
                     }
                  } else {
                     var15 = 0;
                  }

                  Uri var726;
                  if (var10 == -1) {
                     var725 = var721;

                     try {
                        var726 = ContentUris.withAppendedId(var5, var721.getLong(var9));
                     } catch (Throwable var699) {
                        var10000 = var699;
                        var10001 = false;
                        break;
                     }
                  } else {
                     var725 = var721;

                     try {
                        var726 = ContentUris.withAppendedId(var6, var721.getLong(var10));
                     } catch (Throwable var698) {
                        var10000 = var698;
                        var10001 = false;
                        break;
                     }
                  }

                  int var16;
                  if (var12 != -1) {
                     var725 = var721;

                     try {
                        var16 = var721.getInt(var12);
                     } catch (Throwable var697) {
                        var10000 = var697;
                        var10001 = false;
                        break;
                     }
                  } else {
                     var16 = 400;
                  }

                  boolean var17;
                  label5873: {
                     label5872: {
                        if (var13 != -1) {
                           var725 = var721;

                           try {
                              if (var721.getInt(var13) == 1) {
                                 break label5872;
                              }
                           } catch (Throwable var703) {
                              var10000 = var703;
                              var10001 = false;
                              break;
                           }
                        }

                        var17 = false;
                        break label5873;
                     }

                     var17 = true;
                  }

                  var725 = var721;

                  FontsContractCompat.FontInfo var728;
                  try {
                     var728 = new FontsContractCompat.FontInfo;
                  } catch (Throwable var696) {
                     var10000 = var696;
                     var10001 = false;
                     break;
                  }

                  var725 = var721;

                  try {
                     var728.<init>(var726, var15, var16, var17, var14);
                  } catch (Throwable var695) {
                     var10000 = var695;
                     var10001 = false;
                     break;
                  }

                  var725 = var721;

                  try {
                     var727.add(var728);
                  } catch (Throwable var694) {
                     var10000 = var694;
                     var10001 = false;
                     break;
                  }
               }
            }

            Throwable var723 = var10000;
            if (var725 != null) {
               var725.close();
            }

            throw var723;
         }

         var724 = var727;
      }

      if (var721 != null) {
         var721.close();
      }

      return (FontsContractCompat.FontInfo[])var724.toArray(new FontsContractCompat.FontInfo[0]);
   }

   static FontsContractCompat.TypefaceResult getFontInternal(Context var0, FontRequest var1, int var2) {
      FontsContractCompat.FontFamilyResult var7;
      try {
         var7 = fetchFonts(var0, (CancellationSignal)null, var1);
      } catch (NameNotFoundException var5) {
         return new FontsContractCompat.TypefaceResult((Typeface)null, -1);
      }

      int var3 = var7.getStatusCode();
      byte var4 = -3;
      if (var3 == 0) {
         Typeface var6 = TypefaceCompat.createFromFontInfo(var0, (CancellationSignal)null, var7.getFonts(), var2);
         if (var6 != null) {
            var4 = 0;
         }

         return new FontsContractCompat.TypefaceResult(var6, var4);
      } else {
         if (var7.getStatusCode() == 1) {
            var4 = -2;
         }

         return new FontsContractCompat.TypefaceResult((Typeface)null, var4);
      }
   }

   public static Typeface getFontSync(final Context var0, final FontRequest var1, final ResourcesCompat.FontCallback var2, final Handler var3, boolean var4, int var5, final int var6) {
      StringBuilder var7 = new StringBuilder();
      var7.append(var1.getIdentifier());
      var7.append("-");
      var7.append(var6);
      final String var66 = var7.toString();
      Typeface var8 = (Typeface)sTypefaceCache.get(var66);
      if (var8 != null) {
         if (var2 != null) {
            var2.onFontRetrieved(var8);
         }

         return var8;
      } else if (var4 && var5 == -1) {
         FontsContractCompat.TypefaceResult var64 = getFontInternal(var0, var1, var6);
         if (var2 != null) {
            if (var64.mResult == 0) {
               var2.callbackSuccessAsync(var64.mTypeface, var3);
            } else {
               var2.callbackFailAsync(var64.mResult, var3);
            }
         }

         return var64.mTypeface;
      } else {
         Callable var60 = new Callable<FontsContractCompat.TypefaceResult>() {
            public FontsContractCompat.TypefaceResult call() throws Exception {
               FontsContractCompat.TypefaceResult var1x = FontsContractCompat.getFontInternal(var0, var1, var6);
               if (var1x.mTypeface != null) {
                  FontsContractCompat.sTypefaceCache.put(var66, var1x.mTypeface);
               }

               return var1x;
            }
         };
         Typeface var58 = null;
         if (var4) {
            Typeface var61;
            try {
               var61 = ((FontsContractCompat.TypefaceResult)sBackgroundThread.postAndWait(var60, var5)).mTypeface;
            } catch (InterruptedException var52) {
               return var58;
            }

            var58 = var61;
            return var58;
         } else {
            SelfDestructiveThread.ReplyCallback var59;
            if (var2 == null) {
               var59 = null;
            } else {
               var59 = new SelfDestructiveThread.ReplyCallback<FontsContractCompat.TypefaceResult>() {
                  public void onReply(FontsContractCompat.TypefaceResult var1) {
                     if (var1 == null) {
                        var2.callbackFailAsync(1, var3);
                     } else if (var1.mResult == 0) {
                        var2.callbackSuccessAsync(var1.mTypeface, var3);
                     } else {
                        var2.callbackFailAsync(var1.mResult, var3);
                     }

                  }
               };
            }

            Object var63 = sLock;
            synchronized(var63){}

            Throwable var10000;
            boolean var10001;
            label601: {
               ArrayList var65;
               try {
                  var65 = (ArrayList)sPendingReplies.get(var66);
               } catch (Throwable var57) {
                  var10000 = var57;
                  var10001 = false;
                  break label601;
               }

               if (var65 != null) {
                  label590: {
                     if (var59 != null) {
                        try {
                           var65.add(var59);
                        } catch (Throwable var54) {
                           var10000 = var54;
                           var10001 = false;
                           break label590;
                        }
                     }

                     label586:
                     try {
                        return null;
                     } catch (Throwable var53) {
                        var10000 = var53;
                        var10001 = false;
                        break label586;
                     }
                  }
               } else {
                  label611: {
                     if (var59 != null) {
                        try {
                           var65 = new ArrayList();
                           var65.add(var59);
                           sPendingReplies.put(var66, var65);
                        } catch (Throwable var56) {
                           var10000 = var56;
                           var10001 = false;
                           break label611;
                        }
                     }

                     try {
                        ;
                     } catch (Throwable var55) {
                        var10000 = var55;
                        var10001 = false;
                        break label611;
                     }

                     sBackgroundThread.postAndReply(var60, new SelfDestructiveThread.ReplyCallback<FontsContractCompat.TypefaceResult>() {
                        public void onReply(FontsContractCompat.TypefaceResult var1) {
                           Object var2 = FontsContractCompat.sLock;
                           synchronized(var2){}

                           Throwable var10000;
                           boolean var10001;
                           label222: {
                              ArrayList var3;
                              try {
                                 var3 = (ArrayList)FontsContractCompat.sPendingReplies.get(var66);
                              } catch (Throwable var24) {
                                 var10000 = var24;
                                 var10001 = false;
                                 break label222;
                              }

                              if (var3 == null) {
                                 label215:
                                 try {
                                    return;
                                 } catch (Throwable var22) {
                                    var10000 = var22;
                                    var10001 = false;
                                    break label215;
                                 }
                              } else {
                                 label226: {
                                    try {
                                       FontsContractCompat.sPendingReplies.remove(var66);
                                    } catch (Throwable var23) {
                                       var10000 = var23;
                                       var10001 = false;
                                       break label226;
                                    }

                                    for(int var4 = 0; var4 < var3.size(); ++var4) {
                                       ((SelfDestructiveThread.ReplyCallback)var3.get(var4)).onReply(var1);
                                    }

                                    return;
                                 }
                              }
                           }

                           while(true) {
                              Throwable var25 = var10000;

                              try {
                                 throw var25;
                              } catch (Throwable var21) {
                                 var10000 = var21;
                                 var10001 = false;
                                 continue;
                              }
                           }
                        }
                     });
                     return null;
                  }
               }
            }

            while(true) {
               Throwable var62 = var10000;

               try {
                  throw var62;
               } catch (Throwable var51) {
                  var10000 = var51;
                  var10001 = false;
                  continue;
               }
            }
         }
      }
   }

   public static ProviderInfo getProvider(PackageManager var0, FontRequest var1, Resources var2) throws NameNotFoundException {
      String var3 = var1.getProviderAuthority();
      int var4 = 0;
      ProviderInfo var5 = var0.resolveContentProvider(var3, 0);
      StringBuilder var6;
      if (var5 != null) {
         if (var5.packageName.equals(var1.getProviderPackage())) {
            List var7 = convertToByteArrayList(var0.getPackageInfo(var5.packageName, 64).signatures);
            Collections.sort(var7, sByteArrayComparator);

            for(List var9 = getCertificates(var1, var2); var4 < var9.size(); ++var4) {
               ArrayList var8 = new ArrayList((Collection)var9.get(var4));
               Collections.sort(var8, sByteArrayComparator);
               if (equalsByteArrayList(var7, var8)) {
                  return var5;
               }
            }

            return null;
         } else {
            var6 = new StringBuilder();
            var6.append("Found content provider ");
            var6.append(var3);
            var6.append(", but package was not ");
            var6.append(var1.getProviderPackage());
            throw new NameNotFoundException(var6.toString());
         }
      } else {
         var6 = new StringBuilder();
         var6.append("No package found for authority: ");
         var6.append(var3);
         throw new NameNotFoundException(var6.toString());
      }
   }

   public static Map<Uri, ByteBuffer> prepareFontData(Context var0, FontsContractCompat.FontInfo[] var1, CancellationSignal var2) {
      HashMap var3 = new HashMap();
      int var4 = var1.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         FontsContractCompat.FontInfo var6 = var1[var5];
         if (var6.getResultCode() == 0) {
            Uri var7 = var6.getUri();
            if (!var3.containsKey(var7)) {
               var3.put(var7, TypefaceCompatUtil.mmap(var0, var2, var7));
            }
         }
      }

      return Collections.unmodifiableMap(var3);
   }

   public static void requestFont(Context var0, FontRequest var1, FontsContractCompat.FontRequestCallback var2, Handler var3) {
      requestFontInternal(var0.getApplicationContext(), var1, var2, var3);
   }

   private static void requestFontInternal(final Context var0, final FontRequest var1, final FontsContractCompat.FontRequestCallback var2, Handler var3) {
      var3.post(new Runnable(new Handler()) {
         // $FF: synthetic field
         final Handler val$callerThreadHandler;

         {
            this.val$callerThreadHandler = var3;
         }

         public void run() {
            FontsContractCompat.FontFamilyResult var1x;
            try {
               var1x = FontsContractCompat.fetchFonts(var0, (CancellationSignal)null, var1);
            } catch (NameNotFoundException var5) {
               this.val$callerThreadHandler.post(new Runnable() {
                  public void run() {
                     var2.onTypefaceRequestFailed(-1);
                  }
               });
               return;
            }

            final int var2x;
            if (var1x.getStatusCode() != 0) {
               var2x = var1x.getStatusCode();
               if (var2x != 1) {
                  if (var2x != 2) {
                     this.val$callerThreadHandler.post(new Runnable() {
                        public void run() {
                           var2.onTypefaceRequestFailed(-3);
                        }
                     });
                  } else {
                     this.val$callerThreadHandler.post(new Runnable() {
                        public void run() {
                           var2.onTypefaceRequestFailed(-3);
                        }
                     });
                  }
               } else {
                  this.val$callerThreadHandler.post(new Runnable() {
                     public void run() {
                        var2.onTypefaceRequestFailed(-2);
                     }
                  });
               }
            } else {
               FontsContractCompat.FontInfo[] var6 = var1x.getFonts();
               if (var6 != null && var6.length != 0) {
                  int var3 = var6.length;

                  for(var2x = 0; var2x < var3; ++var2x) {
                     FontsContractCompat.FontInfo var4 = var6[var2x];
                     if (var4.getResultCode() != 0) {
                        var2x = var4.getResultCode();
                        if (var2x < 0) {
                           this.val$callerThreadHandler.post(new Runnable() {
                              public void run() {
                                 var2.onTypefaceRequestFailed(-3);
                              }
                           });
                        } else {
                           this.val$callerThreadHandler.post(new Runnable() {
                              public void run() {
                                 var2.onTypefaceRequestFailed(var2x);
                              }
                           });
                        }

                        return;
                     }
                  }

                  final Typeface var7 = FontsContractCompat.buildTypeface(var0, (CancellationSignal)null, var6);
                  if (var7 == null) {
                     this.val$callerThreadHandler.post(new Runnable() {
                        public void run() {
                           var2.onTypefaceRequestFailed(-3);
                        }
                     });
                  } else {
                     this.val$callerThreadHandler.post(new Runnable() {
                        public void run() {
                           var2.onTypefaceRetrieved(var7);
                        }
                     });
                  }
               } else {
                  this.val$callerThreadHandler.post(new Runnable() {
                     public void run() {
                        var2.onTypefaceRequestFailed(1);
                     }
                  });
               }
            }
         }
      });
   }

   public static void resetCache() {
      sTypefaceCache.evictAll();
   }

   public static final class Columns implements BaseColumns {
      public static final String FILE_ID = "file_id";
      public static final String ITALIC = "font_italic";
      public static final String RESULT_CODE = "result_code";
      public static final int RESULT_CODE_FONT_NOT_FOUND = 1;
      public static final int RESULT_CODE_FONT_UNAVAILABLE = 2;
      public static final int RESULT_CODE_MALFORMED_QUERY = 3;
      public static final int RESULT_CODE_OK = 0;
      public static final String TTC_INDEX = "font_ttc_index";
      public static final String VARIATION_SETTINGS = "font_variation_settings";
      public static final String WEIGHT = "font_weight";
   }

   public static class FontFamilyResult {
      public static final int STATUS_OK = 0;
      public static final int STATUS_UNEXPECTED_DATA_PROVIDED = 2;
      public static final int STATUS_WRONG_CERTIFICATES = 1;
      private final FontsContractCompat.FontInfo[] mFonts;
      private final int mStatusCode;

      public FontFamilyResult(int var1, FontsContractCompat.FontInfo[] var2) {
         this.mStatusCode = var1;
         this.mFonts = var2;
      }

      public FontsContractCompat.FontInfo[] getFonts() {
         return this.mFonts;
      }

      public int getStatusCode() {
         return this.mStatusCode;
      }
   }

   public static class FontInfo {
      private final boolean mItalic;
      private final int mResultCode;
      private final int mTtcIndex;
      private final Uri mUri;
      private final int mWeight;

      public FontInfo(Uri var1, int var2, int var3, boolean var4, int var5) {
         this.mUri = (Uri)Preconditions.checkNotNull(var1);
         this.mTtcIndex = var2;
         this.mWeight = var3;
         this.mItalic = var4;
         this.mResultCode = var5;
      }

      public int getResultCode() {
         return this.mResultCode;
      }

      public int getTtcIndex() {
         return this.mTtcIndex;
      }

      public Uri getUri() {
         return this.mUri;
      }

      public int getWeight() {
         return this.mWeight;
      }

      public boolean isItalic() {
         return this.mItalic;
      }
   }

   public static class FontRequestCallback {
      public static final int FAIL_REASON_FONT_LOAD_ERROR = -3;
      public static final int FAIL_REASON_FONT_NOT_FOUND = 1;
      public static final int FAIL_REASON_FONT_UNAVAILABLE = 2;
      public static final int FAIL_REASON_MALFORMED_QUERY = 3;
      public static final int FAIL_REASON_PROVIDER_NOT_FOUND = -1;
      public static final int FAIL_REASON_SECURITY_VIOLATION = -4;
      public static final int FAIL_REASON_WRONG_CERTIFICATES = -2;
      public static final int RESULT_OK = 0;

      public void onTypefaceRequestFailed(int var1) {
      }

      public void onTypefaceRetrieved(Typeface var1) {
      }

      @Retention(RetentionPolicy.SOURCE)
      public @interface FontRequestFailReason {
      }
   }

   private static final class TypefaceResult {
      final int mResult;
      final Typeface mTypeface;

      TypefaceResult(Typeface var1, int var2) {
         this.mTypeface = var1;
         this.mResult = var2;
      }
   }
}
