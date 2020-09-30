package org.apache.commons.net.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public final class DotTerminatedMessageReader extends BufferedReader {
   private static final char CR = '\r';
   private static final int DOT = 46;
   private static final char LF = '\n';
   private boolean atBeginning = true;
   private boolean eof = false;
   private boolean seenCR;

   public DotTerminatedMessageReader(Reader var1) {
      super(var1);
   }

   public void close() throws IOException {
      Object var1 = this.lock;
      synchronized(var1){}

      Throwable var10000;
      boolean var10001;
      label183: {
         label182: {
            try {
               if (this.eof) {
                  break label182;
               }
            } catch (Throwable var22) {
               var10000 = var22;
               var10001 = false;
               break label183;
            }

            while(true) {
               try {
                  if (this.read() == -1) {
                     break;
                  }
               } catch (Throwable var21) {
                  var10000 = var21;
                  var10001 = false;
                  break label183;
               }
            }
         }

         label173:
         try {
            this.eof = true;
            this.atBeginning = false;
            return;
         } catch (Throwable var20) {
            var10000 = var20;
            var10001 = false;
            break label173;
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

   public int read() throws IOException {
      Object var1 = this.lock;
      synchronized(var1){}

      Throwable var10000;
      boolean var10001;
      label2063: {
         try {
            if (this.eof) {
               return -1;
            }
         } catch (Throwable var275) {
            var10000 = var275;
            var10001 = false;
            break label2063;
         }

         int var2;
         try {
            var2 = super.read();
         } catch (Throwable var274) {
            var10000 = var274;
            var10001 = false;
            break label2063;
         }

         if (var2 == -1) {
            label2003:
            try {
               this.eof = true;
               return -1;
            } catch (Throwable var261) {
               var10000 = var261;
               var10001 = false;
               break label2003;
            }
         } else {
            label2064: {
               label2048: {
                  try {
                     if (!this.atBeginning) {
                        break label2048;
                     }

                     this.atBeginning = false;
                  } catch (Throwable var273) {
                     var10000 = var273;
                     var10001 = false;
                     break label2064;
                  }

                  if (var2 == 46) {
                     try {
                        this.mark(2);
                        var2 = super.read();
                     } catch (Throwable var268) {
                        var10000 = var268;
                        var10001 = false;
                        break label2064;
                     }

                     if (var2 == -1) {
                        try {
                           this.eof = true;
                           return 46;
                        } catch (Throwable var262) {
                           var10000 = var262;
                           var10001 = false;
                           break label2064;
                        }
                     } else if (var2 == 46) {
                        try {
                           return var2;
                        } catch (Throwable var263) {
                           var10000 = var263;
                           var10001 = false;
                           break label2064;
                        }
                     } else {
                        if (var2 == 13) {
                           try {
                              var2 = super.read();
                           } catch (Throwable var267) {
                              var10000 = var267;
                              var10001 = false;
                              break label2064;
                           }

                           if (var2 == -1) {
                              try {
                                 this.reset();
                                 return 46;
                              } catch (Throwable var264) {
                                 var10000 = var264;
                                 var10001 = false;
                                 break label2064;
                              }
                           }

                           if (var2 == 10) {
                              try {
                                 this.atBeginning = true;
                                 this.eof = true;
                                 return -1;
                              } catch (Throwable var265) {
                                 var10000 = var265;
                                 var10001 = false;
                                 break label2064;
                              }
                           }
                        }

                        try {
                           this.reset();
                           return 46;
                        } catch (Throwable var266) {
                           var10000 = var266;
                           var10001 = false;
                           break label2064;
                        }
                     }
                  }
               }

               label2040: {
                  try {
                     if (!this.seenCR) {
                        break label2040;
                     }

                     this.seenCR = false;
                  } catch (Throwable var272) {
                     var10000 = var272;
                     var10001 = false;
                     break label2064;
                  }

                  if (var2 == 10) {
                     try {
                        this.atBeginning = true;
                     } catch (Throwable var271) {
                        var10000 = var271;
                        var10001 = false;
                        break label2064;
                     }
                  }
               }

               if (var2 == 13) {
                  try {
                     this.seenCR = true;
                  } catch (Throwable var270) {
                     var10000 = var270;
                     var10001 = false;
                     break label2064;
                  }
               }

               label2028:
               try {
                  return var2;
               } catch (Throwable var269) {
                  var10000 = var269;
                  var10001 = false;
                  break label2028;
               }
            }
         }
      }

      while(true) {
         Throwable var3 = var10000;

         try {
            throw var3;
         } catch (Throwable var260) {
            var10000 = var260;
            var10001 = false;
            continue;
         }
      }
   }

   public int read(char[] var1) throws IOException {
      return this.read(var1, 0, var1.length);
   }

   public int read(char[] var1, int var2, int var3) throws IOException {
      if (var3 < 1) {
         return 0;
      } else {
         Object var4 = this.lock;
         synchronized(var4){}

         Throwable var10000;
         boolean var10001;
         label343: {
            int var5;
            try {
               var5 = this.read();
            } catch (Throwable var37) {
               var10000 = var37;
               var10001 = false;
               break label343;
            }

            if (var5 == -1) {
               label325:
               try {
                  return -1;
               } catch (Throwable var34) {
                  var10000 = var34;
                  var10001 = false;
                  break label325;
               }
            } else {
               label349: {
                  int var6 = var2;
                  int var7 = var3;
                  var3 = var5;

                  while(true) {
                     var5 = var6 + 1;
                     var1[var6] = (char)((char)var3);
                     --var7;
                     if (var7 <= 0) {
                        break;
                     }

                     try {
                        var3 = this.read();
                     } catch (Throwable var36) {
                        var10000 = var36;
                        var10001 = false;
                        break label349;
                     }

                     if (var3 == -1) {
                        break;
                     }

                     var6 = var5;
                  }

                  try {
                     ;
                  } catch (Throwable var35) {
                     var10000 = var35;
                     var10001 = false;
                     break label349;
                  }

                  return var5 - var2;
               }
            }
         }

         while(true) {
            Throwable var38 = var10000;

            try {
               throw var38;
            } catch (Throwable var33) {
               var10000 = var33;
               var10001 = false;
               continue;
            }
         }
      }
   }

   public String readLine() throws IOException {
      StringBuilder var1 = new StringBuilder();
      Object var2 = this.lock;
      synchronized(var2){}

      String var34;
      label318: {
         Throwable var10000;
         boolean var10001;
         while(true) {
            int var3;
            try {
               var3 = this.read();
            } catch (Throwable var33) {
               var10000 = var33;
               var10001 = false;
               break;
            }

            if (var3 != -1) {
               if (var3 == 10) {
                  try {
                     if (this.atBeginning) {
                        var34 = var1.substring(0, var1.length() - 1);
                        return var34;
                     }
                  } catch (Throwable var31) {
                     var10000 = var31;
                     var10001 = false;
                     break;
                  }
               }

               try {
                  var1.append((char)var3);
               } catch (Throwable var30) {
                  var10000 = var30;
                  var10001 = false;
                  break;
               }
            } else {
               try {
                  break label318;
               } catch (Throwable var32) {
                  var10000 = var32;
                  var10001 = false;
                  break;
               }
            }
         }

         while(true) {
            Throwable var35 = var10000;

            try {
               throw var35;
            } catch (Throwable var29) {
               var10000 = var29;
               var10001 = false;
               continue;
            }
         }
      }

      var34 = var1.toString();
      String var36 = var34;
      if (var34.length() == 0) {
         var36 = null;
      }

      return var36;
   }
}
