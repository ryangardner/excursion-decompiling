package javax.activation;

import com.sun.activation.registries.LogSupport;
import com.sun.activation.registries.MailcapFile;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MailcapCommandMap extends CommandMap {
   private static final int PROG = 0;
   private static MailcapFile defDB;
   private MailcapFile[] DB;

   public MailcapCommandMap() {
      ArrayList var1 = new ArrayList(5);
      var1.add((Object)null);
      LogSupport.log("MailcapCommandMap: load HOME");

      MailcapFile var37;
      boolean var10001;
      label339: {
         String var2;
         try {
            var2 = System.getProperty("user.home");
         } catch (SecurityException var35) {
            var10001 = false;
            break label339;
         }

         if (var2 != null) {
            label343: {
               try {
                  StringBuilder var3 = new StringBuilder(String.valueOf(var2));
                  var3.append(File.separator);
                  var3.append(".mailcap");
                  var37 = this.loadFile(var3.toString());
               } catch (SecurityException var34) {
                  var10001 = false;
                  break label343;
               }

               if (var37 != null) {
                  try {
                     var1.add(var37);
                  } catch (SecurityException var33) {
                     var10001 = false;
                  }
               }
            }
         }
      }

      LogSupport.log("MailcapCommandMap: load SYS");

      label340: {
         try {
            StringBuilder var38 = new StringBuilder(String.valueOf(System.getProperty("java.home")));
            var38.append(File.separator);
            var38.append("lib");
            var38.append(File.separator);
            var38.append("mailcap");
            var37 = this.loadFile(var38.toString());
         } catch (SecurityException var32) {
            var10001 = false;
            break label340;
         }

         if (var37 != null) {
            try {
               var1.add(var37);
            } catch (SecurityException var31) {
               var10001 = false;
            }
         }
      }

      LogSupport.log("MailcapCommandMap: load JAR");
      this.loadAllResources(var1, "mailcap");
      LogSupport.log("MailcapCommandMap: load DEF");
      synchronized(MailcapCommandMap.class){}

      label341: {
         Throwable var10000;
         label307: {
            try {
               if (defDB == null) {
                  defDB = this.loadResource("mailcap.default");
               }
            } catch (Throwable var30) {
               var10000 = var30;
               var10001 = false;
               break label307;
            }

            label304:
            try {
               break label341;
            } catch (Throwable var29) {
               var10000 = var29;
               var10001 = false;
               break label304;
            }
         }

         while(true) {
            Throwable var36 = var10000;

            try {
               throw var36;
            } catch (Throwable var28) {
               var10000 = var28;
               var10001 = false;
               continue;
            }
         }
      }

      var37 = defDB;
      if (var37 != null) {
         var1.add(var37);
      }

      MailcapFile[] var39 = new MailcapFile[var1.size()];
      this.DB = var39;
      this.DB = (MailcapFile[])var1.toArray(var39);
   }

   public MailcapCommandMap(InputStream var1) {
      this();
      LogSupport.log("MailcapCommandMap: load PROG");
      MailcapFile[] var2 = this.DB;
      if (var2[0] == null) {
         MailcapFile var3;
         try {
            var3 = new MailcapFile(var1);
         } catch (IOException var4) {
            return;
         }

         var2[0] = var3;
      }

   }

   public MailcapCommandMap(String var1) throws IOException {
      this();
      if (LogSupport.isLoggable()) {
         StringBuilder var2 = new StringBuilder("MailcapCommandMap: load PROG from ");
         var2.append(var1);
         LogSupport.log(var2.toString());
      }

      MailcapFile[] var3 = this.DB;
      if (var3[0] == null) {
         var3[0] = new MailcapFile(var1);
      }

   }

   private void appendCmdsToList(Map var1, List var2) {
      Iterator var3 = var1.keySet().iterator();

      while(var3.hasNext()) {
         String var4 = (String)var3.next();
         Iterator var5 = ((List)var1.get(var4)).iterator();

         while(var5.hasNext()) {
            var2.add(new CommandInfo(var4, (String)var5.next()));
         }
      }

   }

   private void appendPrefCmdsToList(Map var1, List var2) {
      Iterator var3 = var1.keySet().iterator();

      while(var3.hasNext()) {
         String var4 = (String)var3.next();
         if (!this.checkForVerb(var2, var4)) {
            var2.add(new CommandInfo(var4, (String)((List)var1.get(var4)).get(0)));
         }
      }

   }

   private boolean checkForVerb(List var1, String var2) {
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         if (((CommandInfo)var3.next()).getCommandName().equals(var2)) {
            return true;
         }
      }

      return false;
   }

   private DataContentHandler getDataContentHandler(String param1) {
      // $FF: Couldn't be decompiled
   }

   private void loadAllResources(List param1, String param2) {
      // $FF: Couldn't be decompiled
   }

   private MailcapFile loadFile(String var1) {
      MailcapFile var2;
      MailcapFile var4;
      try {
         var2 = new MailcapFile(var1);
      } catch (IOException var3) {
         var4 = null;
         return var4;
      }

      var4 = var2;
      return var4;
   }

   private MailcapFile loadResource(String param1) {
      // $FF: Couldn't be decompiled
   }

   public void addMailcap(String var1) {
      synchronized(this){}

      Throwable var10000;
      label77: {
         boolean var10001;
         label76: {
            MailcapFile[] var2;
            MailcapFile var3;
            try {
               LogSupport.log("MailcapCommandMap: add to PROG");
               if (this.DB[0] != null) {
                  break label76;
               }

               var2 = this.DB;
               var3 = new MailcapFile();
            } catch (Throwable var9) {
               var10000 = var9;
               var10001 = false;
               break label77;
            }

            var2[0] = var3;
         }

         label70:
         try {
            this.DB[0].appendToMailcap(var1);
            return;
         } catch (Throwable var8) {
            var10000 = var8;
            var10001 = false;
            break label70;
         }
      }

      Throwable var10 = var10000;
      throw var10;
   }

   public DataContentHandler createDataContentHandler(String var1) {
      synchronized(this){}

      Throwable var10000;
      label1828: {
         boolean var10001;
         try {
            if (LogSupport.isLoggable()) {
               StringBuilder var2 = new StringBuilder("MailcapCommandMap: createDataContentHandler for ");
               var2.append(var1);
               LogSupport.log(var2.toString());
            }
         } catch (Throwable var214) {
            var10000 = var214;
            var10001 = false;
            break label1828;
         }

         String var218 = var1;
         if (var1 != null) {
            try {
               var218 = var1.toLowerCase(Locale.ENGLISH);
            } catch (Throwable var213) {
               var10000 = var213;
               var10001 = false;
               break label1828;
            }
         }

         int var3 = 0;

         StringBuilder var215;
         Map var216;
         List var217;
         DataContentHandler var219;
         while(true) {
            try {
               if (var3 >= this.DB.length) {
                  break;
               }
            } catch (Throwable var210) {
               var10000 = var210;
               var10001 = false;
               break label1828;
            }

            label1830: {
               try {
                  if (this.DB[var3] == null) {
                     break label1830;
                  }
               } catch (Throwable var212) {
                  var10000 = var212;
                  var10001 = false;
                  break label1828;
               }

               try {
                  if (LogSupport.isLoggable()) {
                     var215 = new StringBuilder("  search DB #");
                     var215.append(var3);
                     LogSupport.log(var215.toString());
                  }
               } catch (Throwable var211) {
                  var10000 = var211;
                  var10001 = false;
                  break label1828;
               }

               try {
                  var216 = this.DB[var3].getMailcapList(var218);
               } catch (Throwable var209) {
                  var10000 = var209;
                  var10001 = false;
                  break label1828;
               }

               if (var216 != null) {
                  try {
                     var217 = (List)var216.get("content-handler");
                  } catch (Throwable var208) {
                     var10000 = var208;
                     var10001 = false;
                     break label1828;
                  }

                  if (var217 != null) {
                     try {
                        var219 = this.getDataContentHandler((String)var217.get(0));
                     } catch (Throwable var207) {
                        var10000 = var207;
                        var10001 = false;
                        break label1828;
                     }

                     if (var219 != null) {
                        return var219;
                     }
                  }
               }
            }

            ++var3;
         }

         var3 = 0;

         while(true) {
            int var4;
            try {
               var4 = this.DB.length;
            } catch (Throwable var204) {
               var10000 = var204;
               var10001 = false;
               break;
            }

            if (var3 >= var4) {
               return null;
            }

            label1832: {
               try {
                  if (this.DB[var3] == null) {
                     break label1832;
                  }
               } catch (Throwable var206) {
                  var10000 = var206;
                  var10001 = false;
                  break;
               }

               try {
                  if (LogSupport.isLoggable()) {
                     var215 = new StringBuilder("  search fallback DB #");
                     var215.append(var3);
                     LogSupport.log(var215.toString());
                  }
               } catch (Throwable var205) {
                  var10000 = var205;
                  var10001 = false;
                  break;
               }

               try {
                  var216 = this.DB[var3].getMailcapFallbackList(var218);
               } catch (Throwable var203) {
                  var10000 = var203;
                  var10001 = false;
                  break;
               }

               if (var216 != null) {
                  try {
                     var217 = (List)var216.get("content-handler");
                  } catch (Throwable var202) {
                     var10000 = var202;
                     var10001 = false;
                     break;
                  }

                  if (var217 != null) {
                     try {
                        var219 = this.getDataContentHandler((String)var217.get(0));
                     } catch (Throwable var201) {
                        var10000 = var201;
                        var10001 = false;
                        break;
                     }

                     if (var219 != null) {
                        return var219;
                     }
                  }
               }
            }

            ++var3;
         }
      }

      Throwable var220 = var10000;
      throw var220;
   }

   public CommandInfo[] getAllCommands(String var1) {
      synchronized(this){}

      Throwable var10000;
      label922: {
         ArrayList var2;
         boolean var10001;
         try {
            var2 = new ArrayList();
         } catch (Throwable var115) {
            var10000 = var115;
            var10001 = false;
            break label922;
         }

         String var3 = var1;
         if (var1 != null) {
            try {
               var3 = var1.toLowerCase(Locale.ENGLISH);
            } catch (Throwable var114) {
               var10000 = var114;
               var10001 = false;
               break label922;
            }
         }

         byte var4 = 0;
         int var5 = 0;

         Map var116;
         while(true) {
            try {
               if (var5 >= this.DB.length) {
                  break;
               }
            } catch (Throwable var112) {
               var10000 = var112;
               var10001 = false;
               break label922;
            }

            label924: {
               try {
                  if (this.DB[var5] == null) {
                     break label924;
                  }
               } catch (Throwable var113) {
                  var10000 = var113;
                  var10001 = false;
                  break label922;
               }

               try {
                  var116 = this.DB[var5].getMailcapList(var3);
               } catch (Throwable var111) {
                  var10000 = var111;
                  var10001 = false;
                  break label922;
               }

               if (var116 != null) {
                  try {
                     this.appendCmdsToList(var116, var2);
                  } catch (Throwable var110) {
                     var10000 = var110;
                     var10001 = false;
                     break label922;
                  }
               }
            }

            ++var5;
         }

         var5 = var4;

         while(true) {
            try {
               if (var5 >= this.DB.length) {
                  CommandInfo[] var118 = (CommandInfo[])var2.toArray(new CommandInfo[var2.size()]);
                  return var118;
               }
            } catch (Throwable var108) {
               var10000 = var108;
               var10001 = false;
               break;
            }

            label926: {
               try {
                  if (this.DB[var5] == null) {
                     break label926;
                  }
               } catch (Throwable var109) {
                  var10000 = var109;
                  var10001 = false;
                  break;
               }

               try {
                  var116 = this.DB[var5].getMailcapFallbackList(var3);
               } catch (Throwable var107) {
                  var10000 = var107;
                  var10001 = false;
                  break;
               }

               if (var116 != null) {
                  try {
                     this.appendCmdsToList(var116, var2);
                  } catch (Throwable var106) {
                     var10000 = var106;
                     var10001 = false;
                     break;
                  }
               }
            }

            ++var5;
         }
      }

      Throwable var117 = var10000;
      throw var117;
   }

   public CommandInfo getCommand(String var1, String var2) {
      Throwable var10000;
      label1470: {
         synchronized(this){}
         String var3 = var1;
         boolean var10001;
         if (var1 != null) {
            try {
               var3 = var1.toLowerCase(Locale.ENGLISH);
            } catch (Throwable var187) {
               var10000 = var187;
               var10001 = false;
               break label1470;
            }
         }

         int var4 = 0;

         Map var188;
         List var189;
         CommandInfo var190;
         while(true) {
            try {
               if (var4 >= this.DB.length) {
                  break;
               }
            } catch (Throwable var185) {
               var10000 = var185;
               var10001 = false;
               break label1470;
            }

            label1472: {
               try {
                  if (this.DB[var4] == null) {
                     break label1472;
                  }
               } catch (Throwable var186) {
                  var10000 = var186;
                  var10001 = false;
                  break label1470;
               }

               try {
                  var188 = this.DB[var4].getMailcapList(var3);
               } catch (Throwable var184) {
                  var10000 = var184;
                  var10001 = false;
                  break label1470;
               }

               if (var188 != null) {
                  try {
                     var189 = (List)var188.get(var2);
                  } catch (Throwable var183) {
                     var10000 = var183;
                     var10001 = false;
                     break label1470;
                  }

                  if (var189 != null) {
                     try {
                        var1 = (String)var189.get(0);
                     } catch (Throwable var182) {
                        var10000 = var182;
                        var10001 = false;
                        break label1470;
                     }

                     if (var1 != null) {
                        try {
                           var190 = new CommandInfo(var2, var1);
                        } catch (Throwable var181) {
                           var10000 = var181;
                           var10001 = false;
                           break label1470;
                        }

                        return var190;
                     }
                  }
               }
            }

            ++var4;
         }

         var4 = 0;

         while(true) {
            int var5;
            try {
               var5 = this.DB.length;
            } catch (Throwable var179) {
               var10000 = var179;
               var10001 = false;
               break;
            }

            if (var4 >= var5) {
               return null;
            }

            label1474: {
               try {
                  if (this.DB[var4] == null) {
                     break label1474;
                  }
               } catch (Throwable var180) {
                  var10000 = var180;
                  var10001 = false;
                  break;
               }

               try {
                  var188 = this.DB[var4].getMailcapFallbackList(var3);
               } catch (Throwable var178) {
                  var10000 = var178;
                  var10001 = false;
                  break;
               }

               if (var188 != null) {
                  try {
                     var189 = (List)var188.get(var2);
                  } catch (Throwable var177) {
                     var10000 = var177;
                     var10001 = false;
                     break;
                  }

                  if (var189 != null) {
                     try {
                        var1 = (String)var189.get(0);
                     } catch (Throwable var176) {
                        var10000 = var176;
                        var10001 = false;
                        break;
                     }

                     if (var1 != null) {
                        try {
                           var190 = new CommandInfo(var2, var1);
                        } catch (Throwable var175) {
                           var10000 = var175;
                           var10001 = false;
                           break;
                        }

                        return var190;
                     }
                  }
               }
            }

            ++var4;
         }
      }

      Throwable var191 = var10000;
      throw var191;
   }

   public String[] getMimeTypes() {
      synchronized(this){}

      Throwable var10000;
      label440: {
         ArrayList var1;
         boolean var10001;
         try {
            var1 = new ArrayList();
         } catch (Throwable var46) {
            var10000 = var46;
            var10001 = false;
            break label440;
         }

         int var2 = 0;

         label431:
         while(true) {
            try {
               if (var2 >= this.DB.length) {
                  String[] var48 = (String[])var1.toArray(new String[var1.size()]);
                  return var48;
               }
            } catch (Throwable var42) {
               var10000 = var42;
               var10001 = false;
               break;
            }

            label442: {
               try {
                  if (this.DB[var2] == null) {
                     break label442;
                  }
               } catch (Throwable var45) {
                  var10000 = var45;
                  var10001 = false;
                  break;
               }

               String[] var3;
               try {
                  var3 = this.DB[var2].getMimeTypes();
               } catch (Throwable var41) {
                  var10000 = var41;
                  var10001 = false;
                  break;
               }

               if (var3 != null) {
                  int var4 = 0;

                  while(true) {
                     try {
                        if (var4 >= var3.length) {
                           break;
                        }
                     } catch (Throwable var43) {
                        var10000 = var43;
                        var10001 = false;
                        break label431;
                     }

                     try {
                        if (!var1.contains(var3[var4])) {
                           var1.add(var3[var4]);
                        }
                     } catch (Throwable var44) {
                        var10000 = var44;
                        var10001 = false;
                        break label431;
                     }

                     ++var4;
                  }
               }
            }

            ++var2;
         }
      }

      Throwable var47 = var10000;
      throw var47;
   }

   public String[] getNativeCommands(String var1) {
      synchronized(this){}

      Throwable var10000;
      label559: {
         ArrayList var2;
         boolean var10001;
         try {
            var2 = new ArrayList();
         } catch (Throwable var61) {
            var10000 = var61;
            var10001 = false;
            break label559;
         }

         String var3 = var1;
         if (var1 != null) {
            try {
               var3 = var1.toLowerCase(Locale.ENGLISH);
            } catch (Throwable var60) {
               var10000 = var60;
               var10001 = false;
               break label559;
            }
         }

         int var4 = 0;

         label546:
         while(true) {
            String[] var62;
            try {
               if (var4 >= this.DB.length) {
                  var62 = (String[])var2.toArray(new String[var2.size()]);
                  return var62;
               }
            } catch (Throwable var56) {
               var10000 = var56;
               var10001 = false;
               break;
            }

            label561: {
               try {
                  if (this.DB[var4] == null) {
                     break label561;
                  }
               } catch (Throwable var59) {
                  var10000 = var59;
                  var10001 = false;
                  break;
               }

               try {
                  var62 = this.DB[var4].getNativeCommands(var3);
               } catch (Throwable var55) {
                  var10000 = var55;
                  var10001 = false;
                  break;
               }

               if (var62 != null) {
                  int var5 = 0;

                  while(true) {
                     try {
                        if (var5 >= var62.length) {
                           break;
                        }
                     } catch (Throwable var57) {
                        var10000 = var57;
                        var10001 = false;
                        break label546;
                     }

                     try {
                        if (!var2.contains(var62[var5])) {
                           var2.add(var62[var5]);
                        }
                     } catch (Throwable var58) {
                        var10000 = var58;
                        var10001 = false;
                        break label546;
                     }

                     ++var5;
                  }
               }
            }

            ++var4;
         }
      }

      Throwable var63 = var10000;
      throw var63;
   }

   public CommandInfo[] getPreferredCommands(String var1) {
      synchronized(this){}

      Throwable var10000;
      label922: {
         ArrayList var2;
         boolean var10001;
         try {
            var2 = new ArrayList();
         } catch (Throwable var115) {
            var10000 = var115;
            var10001 = false;
            break label922;
         }

         String var3 = var1;
         if (var1 != null) {
            try {
               var3 = var1.toLowerCase(Locale.ENGLISH);
            } catch (Throwable var114) {
               var10000 = var114;
               var10001 = false;
               break label922;
            }
         }

         byte var4 = 0;
         int var5 = 0;

         Map var116;
         while(true) {
            try {
               if (var5 >= this.DB.length) {
                  break;
               }
            } catch (Throwable var112) {
               var10000 = var112;
               var10001 = false;
               break label922;
            }

            label924: {
               try {
                  if (this.DB[var5] == null) {
                     break label924;
                  }
               } catch (Throwable var113) {
                  var10000 = var113;
                  var10001 = false;
                  break label922;
               }

               try {
                  var116 = this.DB[var5].getMailcapList(var3);
               } catch (Throwable var111) {
                  var10000 = var111;
                  var10001 = false;
                  break label922;
               }

               if (var116 != null) {
                  try {
                     this.appendPrefCmdsToList(var116, var2);
                  } catch (Throwable var110) {
                     var10000 = var110;
                     var10001 = false;
                     break label922;
                  }
               }
            }

            ++var5;
         }

         var5 = var4;

         while(true) {
            try {
               if (var5 >= this.DB.length) {
                  CommandInfo[] var118 = (CommandInfo[])var2.toArray(new CommandInfo[var2.size()]);
                  return var118;
               }
            } catch (Throwable var108) {
               var10000 = var108;
               var10001 = false;
               break;
            }

            label926: {
               try {
                  if (this.DB[var5] == null) {
                     break label926;
                  }
               } catch (Throwable var109) {
                  var10000 = var109;
                  var10001 = false;
                  break;
               }

               try {
                  var116 = this.DB[var5].getMailcapFallbackList(var3);
               } catch (Throwable var107) {
                  var10000 = var107;
                  var10001 = false;
                  break;
               }

               if (var116 != null) {
                  try {
                     this.appendPrefCmdsToList(var116, var2);
                  } catch (Throwable var106) {
                     var10000 = var106;
                     var10001 = false;
                     break;
                  }
               }
            }

            ++var5;
         }
      }

      Throwable var117 = var10000;
      throw var117;
   }
}
