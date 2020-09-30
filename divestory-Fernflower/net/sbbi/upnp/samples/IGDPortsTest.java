package net.sbbi.upnp.samples;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import net.sbbi.upnp.impls.InternetGatewayDevice;
import net.sbbi.upnp.messages.ActionResponse;
import net.sbbi.upnp.messages.UPNPResponseException;

public class IGDPortsTest {
   public static final void main(String[] var0) {
      StringBuilder var31;
      IOException var36;
      label121: {
         UPNPResponseException var10000;
         label103: {
            boolean var10001;
            InternetGatewayDevice[] var24;
            try {
               System.out.println("Looking for Internet Gateway Device...");
               var24 = InternetGatewayDevice.getDevices(5000);
            } catch (IOException var22) {
               var36 = var22;
               var10001 = false;
               break label121;
            } catch (UPNPResponseException var23) {
               var10000 = var23;
               var10001 = false;
               break label103;
            }

            if (var24 != null) {
               int var1 = 0;

               label96:
               while(true) {
                  try {
                     if (var1 >= var24.length) {
                        System.out.println("\nDone.");
                        return;
                     }
                  } catch (IOException var16) {
                     var36 = var16;
                     var10001 = false;
                     break label121;
                  } catch (UPNPResponseException var17) {
                     var10000 = var17;
                     var10001 = false;
                     break;
                  }

                  InternetGatewayDevice var2 = var24[var1];

                  PrintStream var3;
                  StringBuilder var4;
                  StringBuilder var5;
                  Integer var30;
                  PrintStream var33;
                  try {
                     var3 = System.out;
                     var4 = new StringBuilder("\tFound device ");
                     var4.append(var2.getIGDRootDevice().getModelName());
                     var3.println(var4.toString());
                     var3 = System.out;
                     var4 = new StringBuilder("External IP address: ");
                     var4.append(var2.getExternalIPAddress());
                     var3.println(var4.toString());
                     var30 = var2.getNatTableSize();
                     var33 = System.out;
                     var5 = new StringBuilder("NAT table size is ");
                     var5.append(var30);
                     var33.println(var5.toString());
                  } catch (IOException var14) {
                     var36 = var14;
                     var10001 = false;
                     break label121;
                  } catch (UPNPResponseException var15) {
                     var10000 = var15;
                     var10001 = false;
                     break;
                  }

                  int var6 = 0;

                  label93: {
                     while(true) {
                        try {
                           if (var6 >= var30) {
                              var33 = System.out;
                              var31 = new StringBuilder("\nTrying to map dummy port ");
                              var31.append(9090);
                              var31.append("...");
                              var33.println(var31.toString());
                              String var32 = InetAddress.getLocalHost().getHostAddress();
                              if (!var2.addPortMapping("Some mapping description", (String)null, 9090, 9090, var32, 0, "TCP")) {
                                 break label93;
                              }

                              var33 = System.out;
                              var5 = new StringBuilder("Port ");
                              var5.append(9090);
                              var5.append(" mapped to ");
                              var5.append(var32);
                              var33.println(var5.toString());
                              var33 = System.out;
                              var31 = new StringBuilder("Current mappings count is ");
                              var31.append(var2.getNatMappingsCount());
                              var33.println(var31.toString());
                              if (var2.getSpecificPortMappingEntry((String)null, 9090, "TCP") != null) {
                                 var3 = System.out;
                                 var4 = new StringBuilder("Port ");
                                 var4.append(9090);
                                 var4.append(" mapping confirmation received from device");
                                 var3.println(var4.toString());
                              }
                              break;
                           }
                        } catch (IOException var18) {
                           var36 = var18;
                           var10001 = false;
                           break label121;
                        } catch (UPNPResponseException var19) {
                           var10000 = var19;
                           var10001 = false;
                           break label96;
                        }

                        PrintStream var7;
                        ActionResponse var34;
                        try {
                           var34 = var2.getGenericPortMappingEntry(var6);
                           var7 = System.out;
                           var5 = new StringBuilder();
                        } catch (IOException var12) {
                           var36 = var12;
                           var10001 = false;
                           break label121;
                        } catch (UPNPResponseException var13) {
                           var10000 = var13;
                           var10001 = false;
                           break label96;
                        }

                        ++var6;

                        try {
                           var5.append(var6);
                           var5.append(".\t");
                           var5.append(var34);
                           var7.println(var5.toString());
                        } catch (IOException var10) {
                           var36 = var10;
                           var10001 = false;
                           break label121;
                        } catch (UPNPResponseException var11) {
                           var10000 = var11;
                           var10001 = false;
                           break label96;
                        }
                     }

                     try {
                        System.out.println("Delete dummy port mapping...");
                        if (var2.deletePortMapping((String)null, 9090, "TCP")) {
                           var3 = System.out;
                           StringBuilder var27 = new StringBuilder("Port ");
                           var27.append(9090);
                           var27.append(" unmapped");
                           var3.println(var27.toString());
                        }
                     } catch (IOException var8) {
                        var36 = var8;
                        var10001 = false;
                        break label121;
                     } catch (UPNPResponseException var9) {
                        var10000 = var9;
                        var10001 = false;
                        break;
                     }
                  }

                  ++var1;
               }
            } else {
               try {
                  System.out.println("Unable to find IGD on your network");
                  return;
               } catch (IOException var20) {
                  var36 = var20;
                  var10001 = false;
                  break label121;
               } catch (UPNPResponseException var21) {
                  var10000 = var21;
                  var10001 = false;
               }
            }
         }

         UPNPResponseException var35 = var10000;
         PrintStream var28 = System.err;
         StringBuilder var25 = new StringBuilder("UPNP device unhappy ");
         var25.append(var35.getDetailErrorCode());
         var25.append(" ");
         var25.append(var35.getDetailErrorDescription());
         var28.println(var25.toString());
         return;
      }

      IOException var29 = var36;
      PrintStream var26 = System.err;
      var31 = new StringBuilder("IOException occured during discovery or ports mapping ");
      var31.append(var29.getMessage());
      var26.println(var31.toString());
   }
}
