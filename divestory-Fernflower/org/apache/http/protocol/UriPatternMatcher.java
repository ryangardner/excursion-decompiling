package org.apache.http.protocol;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UriPatternMatcher {
   private final Map map = new HashMap();

   public Object lookup(String var1) {
      synchronized(this){}
      Throwable var10000;
      boolean var10001;
      if (var1 != null) {
         label717: {
            int var2;
            try {
               var2 = var1.indexOf("?");
            } catch (Throwable var77) {
               var10000 = var77;
               var10001 = false;
               break label717;
            }

            String var3 = var1;
            if (var2 != -1) {
               try {
                  var3 = var1.substring(0, var2);
               } catch (Throwable var76) {
                  var10000 = var76;
                  var10001 = false;
                  break label717;
               }
            }

            Object var79;
            try {
               var79 = this.map.get(var3);
            } catch (Throwable var75) {
               var10000 = var75;
               var10001 = false;
               break label717;
            }

            Object var4 = var79;
            if (var79 == null) {
               String var5 = null;

               Iterator var6;
               try {
                  var6 = this.map.keySet().iterator();
               } catch (Throwable var72) {
                  var10000 = var72;
                  var10001 = false;
                  break label717;
               }

               while(true) {
                  String var82;
                  while(true) {
                     while(true) {
                        var4 = var79;

                        try {
                           if (!var6.hasNext()) {
                              return var4;
                           }

                           var82 = (String)var6.next();
                           if (this.matchUriRequestPattern(var82, var3)) {
                              break;
                           }
                        } catch (Throwable var73) {
                           var10000 = var73;
                           var10001 = false;
                           break label717;
                        }
                     }

                     if (var5 == null) {
                        break;
                     }

                     try {
                        if (var5.length() < var82.length() || var5.length() == var82.length() && var82.endsWith("*")) {
                           break;
                        }
                     } catch (Throwable var74) {
                        var10000 = var74;
                        var10001 = false;
                        break label717;
                     }
                  }

                  try {
                     var79 = this.map.get(var82);
                  } catch (Throwable var71) {
                     var10000 = var71;
                     var10001 = false;
                     break label717;
                  }

                  var5 = var82;
               }
            }

            return var4;
         }
      } else {
         label714:
         try {
            IllegalArgumentException var81 = new IllegalArgumentException("Request URI may not be null");
            throw var81;
         } catch (Throwable var78) {
            var10000 = var78;
            var10001 = false;
            break label714;
         }
      }

      Throwable var80 = var10000;
      throw var80;
   }

   protected boolean matchUriRequestPattern(String var1, String var2) {
      boolean var3 = var1.equals("*");
      boolean var4 = true;
      if (var3) {
         return true;
      } else {
         if (var1.endsWith("*")) {
            var3 = var4;
            if (var2.startsWith(var1.substring(0, var1.length() - 1))) {
               return var3;
            }
         }

         if (var1.startsWith("*") && var2.endsWith(var1.substring(1, var1.length()))) {
            var3 = var4;
         } else {
            var3 = false;
         }

         return var3;
      }
   }

   public void register(String var1, Object var2) {
      synchronized(this){}
      Throwable var10000;
      boolean var10001;
      if (var1 != null) {
         label51: {
            try {
               this.map.put(var1, var2);
            } catch (Throwable var7) {
               var10000 = var7;
               var10001 = false;
               break label51;
            }

            return;
         }
      } else {
         label53:
         try {
            IllegalArgumentException var10 = new IllegalArgumentException("URI request pattern may not be null");
            throw var10;
         } catch (Throwable var8) {
            var10000 = var8;
            var10001 = false;
            break label53;
         }
      }

      Throwable var9 = var10000;
      throw var9;
   }

   public void setHandlers(Map var1) {
      synchronized(this){}
      Throwable var10000;
      boolean var10001;
      if (var1 != null) {
         label51: {
            try {
               this.map.clear();
               this.map.putAll(var1);
            } catch (Throwable var6) {
               var10000 = var6;
               var10001 = false;
               break label51;
            }

            return;
         }
      } else {
         label53:
         try {
            IllegalArgumentException var9 = new IllegalArgumentException("Map of handlers may not be null");
            throw var9;
         } catch (Throwable var7) {
            var10000 = var7;
            var10001 = false;
            break label53;
         }
      }

      Throwable var8 = var10000;
      throw var8;
   }

   public void setObjects(Map var1) {
      synchronized(this){}
      Throwable var10000;
      boolean var10001;
      if (var1 != null) {
         label51: {
            try {
               this.map.clear();
               this.map.putAll(var1);
            } catch (Throwable var6) {
               var10000 = var6;
               var10001 = false;
               break label51;
            }

            return;
         }
      } else {
         label53:
         try {
            IllegalArgumentException var9 = new IllegalArgumentException("Map of handlers may not be null");
            throw var9;
         } catch (Throwable var7) {
            var10000 = var7;
            var10001 = false;
            break label53;
         }
      }

      Throwable var8 = var10000;
      throw var8;
   }

   public void unregister(String var1) {
      synchronized(this){}
      if (var1 != null) {
         try {
            this.map.remove(var1);
         } finally {
            ;
         }

      }
   }
}
