package net.sbbi.upnp.services;

import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.Set;
import net.sbbi.upnp.messages.StateVariableMessage;
import net.sbbi.upnp.messages.UPNPMessageFactory;
import net.sbbi.upnp.messages.UPNPResponseException;

public class ServiceStateVariable implements ServiceStateVariableTypes {
   protected Set allowedvalues;
   protected String dataType;
   protected String defaultValue;
   protected String maximumRangeValue;
   protected String minimumRangeValue;
   protected String name;
   protected UPNPService parent;
   protected boolean sendEvents;
   private StateVariableMessage stateVarMsg = null;
   protected String stepRangeValue;

   protected ServiceStateVariable() {
   }

   public static Object UPNPToJavaObject(String var0, String var1) throws Throwable {
      if (var1 != null) {
         if (var0 != null) {
            int var2 = var0.hashCode();
            if (var2 == UI1_INT) {
               return new Short(var1);
            } else if (var2 == UI2_INT) {
               return new Integer(var1);
            } else if (var2 == UI4_INT) {
               return new Long(var1);
            } else if (var2 == I1_INT) {
               return new Byte(var1);
            } else if (var2 == I2_INT) {
               return new Short(var1);
            } else if (var2 == I4_INT) {
               return new Integer(var1);
            } else if (var2 == INT_INT) {
               return new Integer(var1);
            } else if (var2 == R4_INT) {
               return new Float(var1);
            } else if (var2 == R8_INT) {
               return new Double(var1);
            } else if (var2 == NUMBER_INT) {
               return new Double(var1);
            } else if (var2 == FIXED_14_4_INT) {
               return new Double(var1);
            } else if (var2 == FLOAT_INT) {
               return new Float(var1);
            } else if (var2 == CHAR_INT) {
               return new Character(var1.charAt(0));
            } else if (var2 == STRING_INT) {
               return var1;
            } else if (var2 == DATE_INT) {
               return ISO8601Date.parse(var1);
            } else if (var2 == DATETIME_INT) {
               return ISO8601Date.parse(var1);
            } else if (var2 == DATETIME_TZ_INT) {
               return ISO8601Date.parse(var1);
            } else if (var2 == TIME_INT) {
               return ISO8601Date.parse(var1);
            } else if (var2 == TIME_TZ_INT) {
               return ISO8601Date.parse(var1);
            } else if (var2 == BOOLEAN_INT) {
               return !var1.equals("1") && !var1.equalsIgnoreCase("yes") && !var1.equals("true") ? Boolean.FALSE : Boolean.TRUE;
            } else if (var2 == BIN_BASE64_INT) {
               return var1;
            } else if (var2 == BIN_HEX_INT) {
               return var1;
            } else if (var2 == URI_INT) {
               return new URI(var1);
            } else if (var2 == UUID_INT) {
               return var1;
            } else {
               StringBuilder var3 = new StringBuilder("Unhandled data type ");
               var3.append(var0);
               throw new Exception(var3.toString());
            }
         } else {
            throw new Exception("null dataType");
         }
      } else {
         throw new Exception("null value");
      }
   }

   public static Class getDataTypeClassMapping(String var0) {
      int var1 = var0.hashCode();
      if (var1 == UI1_INT) {
         return Short.class;
      } else if (var1 == UI2_INT) {
         return Integer.class;
      } else if (var1 == UI4_INT) {
         return Long.class;
      } else if (var1 == I1_INT) {
         return Byte.class;
      } else if (var1 == I2_INT) {
         return Short.class;
      } else if (var1 == I4_INT) {
         return Integer.class;
      } else if (var1 == INT_INT) {
         return Integer.class;
      } else if (var1 == R4_INT) {
         return Float.class;
      } else if (var1 == R8_INT) {
         return Double.class;
      } else if (var1 == NUMBER_INT) {
         return Double.class;
      } else if (var1 == FIXED_14_4_INT) {
         return Double.class;
      } else if (var1 == FLOAT_INT) {
         return Float.class;
      } else if (var1 == CHAR_INT) {
         return Character.class;
      } else if (var1 == STRING_INT) {
         return String.class;
      } else if (var1 == DATE_INT) {
         return Date.class;
      } else if (var1 == DATETIME_INT) {
         return Date.class;
      } else if (var1 == DATETIME_TZ_INT) {
         return Date.class;
      } else if (var1 == TIME_INT) {
         return Date.class;
      } else if (var1 == TIME_TZ_INT) {
         return Date.class;
      } else if (var1 == BOOLEAN_INT) {
         return Boolean.class;
      } else if (var1 == BIN_BASE64_INT) {
         return String.class;
      } else if (var1 == BIN_HEX_INT) {
         return String.class;
      } else if (var1 == URI_INT) {
         return URI.class;
      } else {
         return var1 == UUID_INT ? String.class : null;
      }
   }

   public static String getUPNPDataTypeMapping(String var0) {
      if (!var0.equals(Short.class.getName()) && !var0.equals("short")) {
         if (!var0.equals(Byte.class.getName()) && !var0.equals("byte")) {
            if (!var0.equals(Integer.class.getName()) && !var0.equals("int")) {
               if (!var0.equals(Long.class.getName()) && !var0.equals("long")) {
                  if (!var0.equals(Float.class.getName()) && !var0.equals("float")) {
                     if (!var0.equals(Double.class.getName()) && !var0.equals("double")) {
                        boolean var1 = var0.equals(Character.class.getName());
                        String var2 = "char";
                        String var3 = var2;
                        if (!var1) {
                           if (var0.equals("char")) {
                              var3 = var2;
                           } else {
                              var1 = var0.equals(String.class.getName());
                              var2 = "string";
                              var3 = var2;
                              if (!var1) {
                                 if (var0.equals("string")) {
                                    var3 = var2;
                                 } else {
                                    if (var0.equals(Date.class.getName())) {
                                       return "dateTime";
                                    }

                                    var1 = var0.equals(Boolean.class.getName());
                                    var2 = "boolean";
                                    var3 = var2;
                                    if (!var1) {
                                       if (!var0.equals("boolean")) {
                                          if (var0.equals(URI.class.getName())) {
                                             return "uri";
                                          }

                                          return null;
                                       }

                                       var3 = var2;
                                    }
                                 }
                              }
                           }
                        }

                        return var3;
                     } else {
                        return "number";
                     }
                  } else {
                     return "float";
                  }
               } else {
                  return "ui4";
               }
            } else {
               return "int";
            }
         } else {
            return "i1";
         }
      } else {
         return "i2";
      }
   }

   public Set getAllowedvalues() {
      return this.allowedvalues;
   }

   public String getDataType() {
      return this.dataType;
   }

   public Class getDataTypeAsClass() {
      return getDataTypeClassMapping(this.dataType);
   }

   public String getDefaultValue() {
      return this.defaultValue;
   }

   public String getMaximumRangeValue() {
      return this.maximumRangeValue;
   }

   public String getMinimumRangeValue() {
      return this.minimumRangeValue;
   }

   public String getName() {
      return this.name;
   }

   public UPNPService getParent() {
      return this.parent;
   }

   public String getStepRangeValue() {
      return this.stepRangeValue;
   }

   public String getValue() throws UPNPResponseException, IOException {
      if (this.stateVarMsg == null) {
         synchronized(this){}

         Throwable var10000;
         boolean var10001;
         label144: {
            try {
               if (this.stateVarMsg == null) {
                  this.stateVarMsg = UPNPMessageFactory.getNewInstance(this.parent).getStateVariableMessage(this.name);
               }
            } catch (Throwable var13) {
               var10000 = var13;
               var10001 = false;
               break label144;
            }

            label141:
            try {
               return this.stateVarMsg.service().getStateVariableValue();
            } catch (Throwable var12) {
               var10000 = var12;
               var10001 = false;
               break label141;
            }
         }

         while(true) {
            Throwable var1 = var10000;

            try {
               throw var1;
            } catch (Throwable var11) {
               var10000 = var11;
               var10001 = false;
               continue;
            }
         }
      } else {
         return this.stateVarMsg.service().getStateVariableValue();
      }
   }

   public boolean isSendEvents() {
      return this.sendEvents;
   }
}
