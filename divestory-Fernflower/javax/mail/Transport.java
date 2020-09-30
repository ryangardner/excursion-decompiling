package javax.mail;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;

public abstract class Transport extends Service {
   private Vector transportListeners = null;

   public Transport(Session var1, URLName var2) {
      super(var1, var2);
   }

   public static void send(Message var0) throws MessagingException {
      var0.saveChanges();
      send0(var0, var0.getAllRecipients());
   }

   public static void send(Message var0, Address[] var1) throws MessagingException {
      var0.saveChanges();
      send0(var0, var1);
   }

   private static void send0(Message var0, Address[] var1) throws MessagingException {
      if (var1 != null && var1.length != 0) {
         Hashtable var2 = new Hashtable();
         Vector var3 = new Vector();
         Vector var4 = new Vector();
         Vector var5 = new Vector();

         int var6;
         for(var6 = 0; var6 < var1.length; ++var6) {
            if (var2.containsKey(var1[var6].getType())) {
               ((Vector)var2.get(var1[var6].getType())).addElement(var1[var6]);
            } else {
               Vector var7 = new Vector();
               var7.addElement(var1[var6]);
               var2.put(var1[var6].getType(), var7);
            }
         }

         var6 = var2.size();
         if (var6 == 0) {
            throw new SendFailedException("No recipient addresses");
         } else {
            Session var231;
            if (var0.session != null) {
               var231 = var0.session;
            } else {
               var231 = Session.getDefaultInstance(System.getProperties(), (Authenticator)null);
            }

            if (var6 == 1) {
               Transport var229 = var231.getTransport(var1[0]);

               try {
                  var229.connect();
                  var229.sendMessage(var0, var1);
               } finally {
                  var229.close();
               }

            } else {
               Enumeration var8 = var2.elements();
               Object var225 = null;
               boolean var230 = false;

               while(true) {
                  Address[] var226;
                  while(var8.hasMoreElements()) {
                     Vector var9 = (Vector)var8.nextElement();
                     int var10 = var9.size();
                     var226 = new Address[var10];
                     var9.copyInto(var226);
                     Transport var233 = var231.getTransport(var226[0]);
                     if (var233 == null) {
                        for(int var11 = 0; var11 < var10; ++var11) {
                           var3.addElement(var226[var11]);
                        }
                     } else {
                        label2823: {
                           Throwable var10000;
                           label2822: {
                              Object var227;
                              label2821: {
                                 boolean var10001;
                                 label2859: {
                                    SendFailedException var228;
                                    try {
                                       try {
                                          var233.connect();
                                          var233.sendMessage(var0, var226);
                                          break label2823;
                                       } catch (SendFailedException var220) {
                                          var228 = var220;
                                       } catch (MessagingException var221) {
                                          var227 = var221;
                                          break label2859;
                                       }
                                    } catch (Throwable var222) {
                                       var10000 = var222;
                                       var10001 = false;
                                       break label2822;
                                    }

                                    if (var225 == null) {
                                       var225 = var228;
                                    } else {
                                       try {
                                          ((MessagingException)var225).setNextException(var228);
                                       } catch (Throwable var216) {
                                          var10000 = var216;
                                          var10001 = false;
                                          break label2822;
                                       }
                                    }

                                    Address[] var12;
                                    try {
                                       var12 = var228.getInvalidAddresses();
                                    } catch (Throwable var215) {
                                       var10000 = var215;
                                       var10001 = false;
                                       break label2822;
                                    }

                                    if (var12 != null) {
                                       var6 = 0;

                                       while(true) {
                                          try {
                                             if (var6 >= var12.length) {
                                                break;
                                             }
                                          } catch (Throwable var217) {
                                             var10000 = var217;
                                             var10001 = false;
                                             break label2822;
                                          }

                                          try {
                                             var3.addElement(var12[var6]);
                                          } catch (Throwable var214) {
                                             var10000 = var214;
                                             var10001 = false;
                                             break label2822;
                                          }

                                          ++var6;
                                       }
                                    }

                                    try {
                                       var12 = var228.getValidSentAddresses();
                                    } catch (Throwable var213) {
                                       var10000 = var213;
                                       var10001 = false;
                                       break label2822;
                                    }

                                    if (var12 != null) {
                                       var6 = 0;

                                       while(true) {
                                          try {
                                             if (var6 >= var12.length) {
                                                break;
                                             }
                                          } catch (Throwable var219) {
                                             var10000 = var219;
                                             var10001 = false;
                                             break label2822;
                                          }

                                          try {
                                             var4.addElement(var12[var6]);
                                          } catch (Throwable var212) {
                                             var10000 = var212;
                                             var10001 = false;
                                             break label2822;
                                          }

                                          ++var6;
                                       }
                                    }

                                    try {
                                       var12 = var228.getValidUnsentAddresses();
                                    } catch (Throwable var211) {
                                       var10000 = var211;
                                       var10001 = false;
                                       break label2822;
                                    }

                                    var227 = var225;
                                    if (var12 != null) {
                                       var6 = 0;

                                       while(true) {
                                          try {
                                             if (var6 >= var12.length) {
                                                break;
                                             }
                                          } catch (Throwable var218) {
                                             var10000 = var218;
                                             var10001 = false;
                                             break label2822;
                                          }

                                          try {
                                             var5.addElement(var12[var6]);
                                          } catch (Throwable var210) {
                                             var10000 = var210;
                                             var10001 = false;
                                             break label2822;
                                          }

                                          ++var6;
                                       }

                                       var227 = var225;
                                    }
                                    break label2821;
                                 }

                                 if (var225 != null) {
                                    try {
                                       ((MessagingException)var225).setNextException((Exception)var227);
                                    } catch (Throwable var209) {
                                       var10000 = var209;
                                       var10001 = false;
                                       break label2822;
                                    }

                                    var227 = var225;
                                 }
                              }

                              var233.close();
                              var230 = true;
                              var225 = var227;
                              continue;
                           }

                           Throwable var223 = var10000;
                           var233.close();
                           throw var223;
                        }

                        var233.close();
                     }
                  }

                  if (!var230 && var3.size() == 0 && var5.size() == 0) {
                     return;
                  }

                  Address[] var224 = (Address[])null;
                  if (var4.size() > 0) {
                     var226 = new Address[var4.size()];
                     var4.copyInto(var226);
                  } else {
                     var226 = var224;
                  }

                  Address[] var232;
                  if (var5.size() > 0) {
                     var232 = new Address[var5.size()];
                     var5.copyInto(var232);
                  } else {
                     var232 = var224;
                  }

                  if (var3.size() > 0) {
                     var224 = new Address[var3.size()];
                     var3.copyInto(var224);
                  }

                  throw new SendFailedException("Sending failed", (Exception)var225, var226, var232, var224);
               }
            }
         }
      } else {
         throw new SendFailedException("No recipient addresses");
      }
   }

   public void addTransportListener(TransportListener var1) {
      synchronized(this){}

      try {
         if (this.transportListeners == null) {
            Vector var2 = new Vector();
            this.transportListeners = var2;
         }

         this.transportListeners.addElement(var1);
      } finally {
         ;
      }

   }

   protected void notifyTransportListeners(int var1, Address[] var2, Address[] var3, Address[] var4, Message var5) {
      if (this.transportListeners != null) {
         this.queueEvent(new TransportEvent(this, var1, var2, var3, var4, var5), this.transportListeners);
      }
   }

   public void removeTransportListener(TransportListener var1) {
      synchronized(this){}

      try {
         if (this.transportListeners != null) {
            this.transportListeners.removeElement(var1);
         }
      } finally {
         ;
      }

   }

   public abstract void sendMessage(Message var1, Address[] var2) throws MessagingException;
}
