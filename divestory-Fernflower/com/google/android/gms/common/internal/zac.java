package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;
import androidx.collection.SimpleArrayMap;
import androidx.core.os.ConfigurationCompat;
import com.google.android.gms.base.R;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.util.DeviceProperties;
import com.google.android.gms.common.wrappers.Wrappers;
import java.util.Locale;

public final class zac {
   private static final SimpleArrayMap<String, String> zaa = new SimpleArrayMap();
   private static Locale zab;

   public static String zaa(Context var0) {
      return var0.getResources().getString(R.string.common_google_play_services_notification_channel_name);
   }

   public static String zaa(Context var0, int var1) {
      Resources var2 = var0.getResources();
      switch(var1) {
      case 1:
         return var2.getString(R.string.common_google_play_services_install_title);
      case 2:
         return var2.getString(R.string.common_google_play_services_update_title);
      case 3:
         return var2.getString(R.string.common_google_play_services_enable_title);
      case 4:
      case 6:
      case 18:
         return null;
      case 5:
         Log.e("GoogleApiAvailability", "An invalid account was specified when connecting. Please provide a valid account.");
         return zaa(var0, "common_google_play_services_invalid_account_title");
      case 7:
         Log.e("GoogleApiAvailability", "Network error occurred. Please retry request later.");
         return zaa(var0, "common_google_play_services_network_error_title");
      case 8:
         Log.e("GoogleApiAvailability", "Internal error occurred. Please see logs for detailed information");
         return null;
      case 9:
         Log.e("GoogleApiAvailability", "Google Play services is invalid. Cannot recover.");
         return null;
      case 10:
         Log.e("GoogleApiAvailability", "Developer error occurred. Please see logs for detailed information");
         return null;
      case 11:
         Log.e("GoogleApiAvailability", "The application is not licensed to the user.");
         return null;
      case 12:
      case 13:
      case 14:
      case 15:
      case 19:
      default:
         StringBuilder var3 = new StringBuilder(33);
         var3.append("Unexpected error code ");
         var3.append(var1);
         Log.e("GoogleApiAvailability", var3.toString());
         return null;
      case 16:
         Log.e("GoogleApiAvailability", "One of the API components you attempted to connect to is not available.");
         return null;
      case 17:
         Log.e("GoogleApiAvailability", "The specified account could not be signed in.");
         return zaa(var0, "common_google_play_services_sign_in_failed_title");
      case 20:
         Log.e("GoogleApiAvailability", "The current user profile is restricted and could not use authenticated features.");
         return zaa(var0, "common_google_play_services_restricted_profile_title");
      }
   }

   private static String zaa(Context var0, String var1) {
      SimpleArrayMap var2 = zaa;
      synchronized(var2){}

      Throwable var10000;
      boolean var10001;
      label1631: {
         try {
            Locale var3 = ConfigurationCompat.getLocales(var0.getResources().getConfiguration()).get(0);
            if (!var3.equals(zab)) {
               zaa.clear();
               zab = var3;
            }
         } catch (Throwable var214) {
            var10000 = var214;
            var10001 = false;
            break label1631;
         }

         String var217;
         try {
            var217 = (String)zaa.get(var1);
         } catch (Throwable var213) {
            var10000 = var213;
            var10001 = false;
            break label1631;
         }

         if (var217 != null) {
            label1578:
            try {
               return var217;
            } catch (Throwable var202) {
               var10000 = var202;
               var10001 = false;
               break label1578;
            }
         } else {
            label1620: {
               Resources var215;
               try {
                  var215 = GooglePlayServicesUtil.getRemoteResource(var0);
               } catch (Throwable var212) {
                  var10000 = var212;
                  var10001 = false;
                  break label1620;
               }

               if (var215 == null) {
                  label1580:
                  try {
                     return null;
                  } catch (Throwable var203) {
                     var10000 = var203;
                     var10001 = false;
                     break label1580;
                  }
               } else {
                  label1616: {
                     int var4;
                     try {
                        var4 = var215.getIdentifier(var1, "string", "com.google.android.gms");
                     } catch (Throwable var211) {
                        var10000 = var211;
                        var10001 = false;
                        break label1616;
                     }

                     String var216;
                     if (var4 == 0) {
                        label1633: {
                           label1591: {
                              try {
                                 var216 = String.valueOf(var1);
                                 if (var216.length() != 0) {
                                    var216 = "Missing resource: ".concat(var216);
                                    break label1591;
                                 }
                              } catch (Throwable var206) {
                                 var10000 = var206;
                                 var10001 = false;
                                 break label1633;
                              }

                              try {
                                 var216 = new String("Missing resource: ");
                              } catch (Throwable var205) {
                                 var10000 = var205;
                                 var10001 = false;
                                 break label1633;
                              }
                           }

                           label1582:
                           try {
                              Log.w("GoogleApiAvailability", var216);
                              return null;
                           } catch (Throwable var204) {
                              var10000 = var204;
                              var10001 = false;
                              break label1582;
                           }
                        }
                     } else {
                        label1612: {
                           label1611: {
                              label1634: {
                                 try {
                                    var216 = var215.getString(var4);
                                    if (!TextUtils.isEmpty(var216)) {
                                       break label1634;
                                    }

                                    var216 = String.valueOf(var1);
                                    if (var216.length() != 0) {
                                       var216 = "Got empty resource: ".concat(var216);
                                       break label1611;
                                    }
                                 } catch (Throwable var210) {
                                    var10000 = var210;
                                    var10001 = false;
                                    break label1612;
                                 }

                                 try {
                                    var216 = new String("Got empty resource: ");
                                    break label1611;
                                 } catch (Throwable var209) {
                                    var10000 = var209;
                                    var10001 = false;
                                    break label1612;
                                 }
                              }

                              try {
                                 zaa.put(var1, var216);
                                 return var216;
                              } catch (Throwable var208) {
                                 var10000 = var208;
                                 var10001 = false;
                                 break label1612;
                              }
                           }

                           label1595:
                           try {
                              Log.w("GoogleApiAvailability", var216);
                              return null;
                           } catch (Throwable var207) {
                              var10000 = var207;
                              var10001 = false;
                              break label1595;
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      while(true) {
         Throwable var218 = var10000;

         try {
            throw var218;
         } catch (Throwable var201) {
            var10000 = var201;
            var10001 = false;
            continue;
         }
      }
   }

   private static String zaa(Context var0, String var1, String var2) {
      Resources var3 = var0.getResources();
      var1 = zaa(var0, var1);
      String var4 = var1;
      if (var1 == null) {
         var4 = var3.getString(com.google.android.gms.common.R.string.common_google_play_services_unknown_issue);
      }

      return String.format(var3.getConfiguration().locale, var4, var2);
   }

   private static String zab(Context var0) {
      String var1 = var0.getPackageName();

      try {
         String var2 = Wrappers.packageManager(var0).getApplicationLabel(var1).toString();
         return var2;
      } catch (NullPointerException | NameNotFoundException var3) {
         String var4 = var0.getApplicationInfo().name;
         return TextUtils.isEmpty(var4) ? var1 : var4;
      }
   }

   public static String zab(Context var0, int var1) {
      String var2;
      if (var1 == 6) {
         var2 = zaa(var0, "common_google_play_services_resolution_required_title");
      } else {
         var2 = zaa(var0, var1);
      }

      String var3 = var2;
      if (var2 == null) {
         var3 = var0.getResources().getString(R.string.common_google_play_services_notification_ticker);
      }

      return var3;
   }

   public static String zac(Context var0, int var1) {
      Resources var2 = var0.getResources();
      String var3 = zab(var0);
      if (var1 != 1) {
         if (var1 != 2) {
            if (var1 != 3) {
               if (var1 != 5) {
                  if (var1 != 7) {
                     if (var1 != 9) {
                        if (var1 != 20) {
                           switch(var1) {
                           case 16:
                              return zaa(var0, "common_google_play_services_api_unavailable_text", var3);
                           case 17:
                              return zaa(var0, "common_google_play_services_sign_in_failed_text", var3);
                           case 18:
                              return var2.getString(R.string.common_google_play_services_updating_text, new Object[]{var3});
                           default:
                              return var2.getString(com.google.android.gms.common.R.string.common_google_play_services_unknown_issue, new Object[]{var3});
                           }
                        } else {
                           return zaa(var0, "common_google_play_services_restricted_profile_text", var3);
                        }
                     } else {
                        return var2.getString(R.string.common_google_play_services_unsupported_text, new Object[]{var3});
                     }
                  } else {
                     return zaa(var0, "common_google_play_services_network_error_text", var3);
                  }
               } else {
                  return zaa(var0, "common_google_play_services_invalid_account_text", var3);
               }
            } else {
               return var2.getString(R.string.common_google_play_services_enable_text, new Object[]{var3});
            }
         } else {
            return DeviceProperties.isWearableWithoutPlayStore(var0) ? var2.getString(R.string.common_google_play_services_wear_update_text) : var2.getString(R.string.common_google_play_services_update_text, new Object[]{var3});
         }
      } else {
         return var2.getString(R.string.common_google_play_services_install_text, new Object[]{var3});
      }
   }

   public static String zad(Context var0, int var1) {
      return var1 != 6 && var1 != 19 ? zac(var0, var1) : zaa(var0, "common_google_play_services_resolution_required_text", zab(var0));
   }

   public static String zae(Context var0, int var1) {
      Resources var2 = var0.getResources();
      if (var1 != 1) {
         if (var1 != 2) {
            return var1 != 3 ? var2.getString(17039370) : var2.getString(R.string.common_google_play_services_enable_button);
         } else {
            return var2.getString(R.string.common_google_play_services_update_button);
         }
      } else {
         return var2.getString(R.string.common_google_play_services_install_button);
      }
   }
}
