package com.google.android.gms.common.server.response;

import com.google.android.gms.common.util.Base64Utils;
import com.google.android.gms.common.util.JsonUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class FastParser<T extends FastJsonResponse> {
   private static final char[] zaf = new char[]{'u', 'l', 'l'};
   private static final char[] zag = new char[]{'r', 'u', 'e'};
   private static final char[] zah = new char[]{'r', 'u', 'e', '"'};
   private static final char[] zai = new char[]{'a', 'l', 's', 'e'};
   private static final char[] zaj = new char[]{'a', 'l', 's', 'e', '"'};
   private static final char[] zak = new char[]{'\n'};
   private static final FastParser.zaa<Integer> zam = new com.google.android.gms.common.server.response.zaa();
   private static final FastParser.zaa<Long> zan = new zac();
   private static final FastParser.zaa<Float> zao = new zab();
   private static final FastParser.zaa<Double> zap = new zae();
   private static final FastParser.zaa<Boolean> zaq = new zad();
   private static final FastParser.zaa<String> zar = new zag();
   private static final FastParser.zaa<BigInteger> zas = new zaf();
   private static final FastParser.zaa<BigDecimal> zat = new zah();
   private final char[] zaa = new char[1];
   private final char[] zab = new char[32];
   private final char[] zac = new char[1024];
   private final StringBuilder zad = new StringBuilder(32);
   private final StringBuilder zae = new StringBuilder(1024);
   private final Stack<Integer> zal = new Stack();

   // $FF: synthetic method
   static int zaa(FastParser var0, BufferedReader var1) throws FastParser.ParseException, IOException {
      return var0.zad(var1);
   }

   private final int zaa(BufferedReader var1, char[] var2) throws FastParser.ParseException, IOException {
      char var3 = this.zaj(var1);
      if (var3 == 0) {
         throw new FastParser.ParseException("Unexpected EOF");
      } else if (var3 != ',') {
         if (var3 == 'n') {
            this.zab(var1, zaf);
            return 0;
         } else {
            var1.mark(1024);
            int var5;
            int var7;
            if (var3 == '"') {
               var7 = 0;
               boolean var4 = false;

               while(true) {
                  var5 = var7;
                  if (var7 >= var2.length) {
                     break;
                  }

                  var5 = var7;
                  if (var1.read(var2, var7, 1) == -1) {
                     break;
                  }

                  char var6 = var2[var7];
                  if (Character.isISOControl(var6)) {
                     throw new FastParser.ParseException("Unexpected control character while reading string");
                  }

                  if (var6 == '"' && !var4) {
                     var1.reset();
                     var1.skip((long)(var7 + 1));
                     return var7;
                  }

                  if (var6 == '\\') {
                     var4 ^= true;
                  } else {
                     var4 = false;
                  }

                  ++var7;
               }
            } else {
               var2[0] = (char)var3;
               var7 = 1;

               while(true) {
                  var5 = var7;
                  if (var7 >= var2.length) {
                     break;
                  }

                  var5 = var7;
                  if (var1.read(var2, var7, 1) == -1) {
                     break;
                  }

                  if (var2[var7] == '}' || var2[var7] == ',' || Character.isWhitespace(var2[var7]) || var2[var7] == ']') {
                     var1.reset();
                     var1.skip((long)(var7 - 1));
                     var2[var7] = (char)0;
                     return var7;
                  }

                  ++var7;
               }
            }

            if (var5 == var2.length) {
               throw new FastParser.ParseException("Absurdly long value");
            } else {
               throw new FastParser.ParseException("Unexpected EOF");
            }
         }
      } else {
         throw new FastParser.ParseException("Missing value");
      }
   }

   private final String zaa(BufferedReader var1) throws FastParser.ParseException, IOException {
      this.zal.push(2);
      char var2 = this.zaj(var1);
      if (var2 != '"') {
         if (var2 != ']') {
            if (var2 == '}') {
               this.zaa(2);
               return null;
            } else {
               StringBuilder var4 = new StringBuilder(19);
               var4.append("Unexpected token: ");
               var4.append(var2);
               throw new FastParser.ParseException(var4.toString());
            }
         } else {
            this.zaa(2);
            this.zaa(1);
            this.zaa(5);
            return null;
         }
      } else {
         this.zal.push(3);
         String var3 = zab(var1, this.zab, this.zad, (char[])null);
         this.zaa(3);
         if (this.zaj(var1) == ':') {
            return var3;
         } else {
            throw new FastParser.ParseException("Expected key/value separator");
         }
      }
   }

   private final String zaa(BufferedReader var1, char[] var2, StringBuilder var3, char[] var4) throws FastParser.ParseException, IOException {
      char var5 = this.zaj(var1);
      if (var5 != '"') {
         if (var5 == 'n') {
            this.zab(var1, zaf);
            return null;
         } else {
            throw new FastParser.ParseException("Expected string");
         }
      } else {
         return zab(var1, var2, var3, var4);
      }
   }

   private final <T extends FastJsonResponse> ArrayList<T> zaa(BufferedReader var1, FastJsonResponse.Field<?, ?> var2) throws FastParser.ParseException, IOException {
      ArrayList var3 = new ArrayList();
      char var4 = this.zaj(var1);
      if (var4 == ']') {
         this.zaa(5);
         return var3;
      } else if (var4 == 'n') {
         this.zab(var1, zaf);
         this.zaa(5);
         return null;
      } else {
         StringBuilder var8;
         if (var4 != '{') {
            var8 = new StringBuilder(19);
            var8.append("Unexpected token: ");
            var8.append(var4);
            throw new FastParser.ParseException(var8.toString());
         } else {
            this.zal.push(1);

            while(true) {
               try {
                  FastJsonResponse var5 = var2.zac();
                  if (!this.zaa(var1, var5)) {
                     return var3;
                  }

                  var3.add(var5);
               } catch (InstantiationException var6) {
                  throw new FastParser.ParseException("Error instantiating inner object", var6);
               } catch (IllegalAccessException var7) {
                  throw new FastParser.ParseException("Error instantiating inner object", var7);
               }

               var4 = this.zaj(var1);
               if (var4 != ',') {
                  if (var4 == ']') {
                     this.zaa(5);
                     return var3;
                  }

                  var8 = new StringBuilder(19);
                  var8.append("Unexpected token: ");
                  var8.append(var4);
                  throw new FastParser.ParseException(var8.toString());
               }

               if (this.zaj(var1) != '{') {
                  throw new FastParser.ParseException("Expected start of next object in array");
               }

               this.zal.push(1);
            }
         }
      }
   }

   private final <O> ArrayList<O> zaa(BufferedReader var1, FastParser.zaa<O> var2) throws FastParser.ParseException, IOException {
      char var3 = this.zaj(var1);
      if (var3 == 'n') {
         this.zab(var1, zaf);
         return null;
      } else if (var3 == '[') {
         this.zal.push(5);
         ArrayList var4 = new ArrayList();

         while(true) {
            var1.mark(1024);
            var3 = this.zaj(var1);
            if (var3 == 0) {
               throw new FastParser.ParseException("Unexpected EOF");
            }

            if (var3 != ',') {
               if (var3 == ']') {
                  this.zaa(5);
                  return var4;
               }

               var1.reset();
               var4.add(var2.zaa(this, var1));
            }
         }
      } else {
         throw new FastParser.ParseException("Expected start of array");
      }
   }

   private final void zaa(int var1) throws FastParser.ParseException {
      StringBuilder var3;
      if (!this.zal.isEmpty()) {
         int var2 = (Integer)this.zal.pop();
         if (var2 != var1) {
            var3 = new StringBuilder(46);
            var3.append("Expected state ");
            var3.append(var1);
            var3.append(" but had ");
            var3.append(var2);
            throw new FastParser.ParseException(var3.toString());
         }
      } else {
         var3 = new StringBuilder(46);
         var3.append("Expected state ");
         var3.append(var1);
         var3.append(" but had empty stack");
         throw new FastParser.ParseException(var3.toString());
      }
   }

   // $FF: synthetic method
   static boolean zaa(FastParser var0, BufferedReader var1, boolean var2) throws FastParser.ParseException, IOException {
      return var0.zaa(var1, false);
   }

   private final boolean zaa(BufferedReader var1, FastJsonResponse var2) throws FastParser.ParseException, IOException {
      Map var3 = var2.getFieldMappings();
      String var4 = this.zaa(var1);
      Integer var5 = 1;
      if (var4 == null) {
         this.zaa(1);
         return false;
      } else {
         while(true) {
            while(var4 != null) {
               FastJsonResponse.Field var6 = (FastJsonResponse.Field)var3.get(var4);
               if (var6 != null) {
                  this.zal.push(4);
                  char var7;
                  char var9;
                  StringBuilder var12;
                  switch(var6.zaa) {
                  case 0:
                     if (var6.zab) {
                        var2.zaa(var6, this.zaa(var1, zam));
                     } else {
                        var2.zaa(var6, this.zad(var1));
                     }
                     break;
                  case 1:
                     if (var6.zab) {
                        var2.zab(var6, this.zaa(var1, zas));
                     } else {
                        var2.zaa(var6, this.zaf(var1));
                     }
                     break;
                  case 2:
                     if (var6.zab) {
                        var2.zac(var6, this.zaa(var1, zan));
                     } else {
                        var2.zaa(var6, this.zae(var1));
                     }
                     break;
                  case 3:
                     if (var6.zab) {
                        var2.zad(var6, this.zaa(var1, zao));
                     } else {
                        var2.zaa(var6, this.zag(var1));
                     }
                     break;
                  case 4:
                     if (var6.zab) {
                        var2.zae(var6, this.zaa(var1, zap));
                     } else {
                        var2.zaa(var6, this.zah(var1));
                     }
                     break;
                  case 5:
                     if (var6.zab) {
                        var2.zaf(var6, this.zaa(var1, zat));
                     } else {
                        var2.zaa(var6, this.zai(var1));
                     }
                     break;
                  case 6:
                     if (var6.zab) {
                        var2.zag(var6, this.zaa(var1, zaq));
                     } else {
                        var2.zaa(var6, this.zaa(var1, false));
                     }
                     break;
                  case 7:
                     if (var6.zab) {
                        var2.zah(var6, this.zaa(var1, zar));
                     } else {
                        var2.zaa(var6, this.zac(var1));
                     }
                     break;
                  case 8:
                     var2.zaa(var6, Base64Utils.decode(this.zaa(var1, this.zac, this.zae, zak)));
                     break;
                  case 9:
                     var2.zaa(var6, Base64Utils.decodeUrlSafe(this.zaa(var1, this.zac, this.zae, zak)));
                     break;
                  case 10:
                     var7 = this.zaj(var1);
                     HashMap var15;
                     if (var7 == 'n') {
                        this.zab(var1, zaf);
                        var15 = null;
                     } else {
                        if (var7 != '{') {
                           throw new FastParser.ParseException("Expected start of a map object");
                        }

                        this.zal.push(var5);
                        var15 = new HashMap();

                        while(true) {
                           var7 = this.zaj(var1);
                           if (var7 == 0) {
                              throw new FastParser.ParseException("Unexpected EOF");
                           }

                           if (var7 != '"') {
                              if (var7 == '}') {
                                 this.zaa(1);
                                 break;
                              }
                           } else {
                              String var8 = zab(var1, this.zab, this.zad, (char[])null);
                              String var13;
                              if (this.zaj(var1) != ':') {
                                 var13 = String.valueOf(var8);
                                 if (var13.length() != 0) {
                                    var13 = "No map value found for key ".concat(var13);
                                 } else {
                                    var13 = new String("No map value found for key ");
                                 }

                                 throw new FastParser.ParseException(var13);
                              }

                              if (this.zaj(var1) != '"') {
                                 var13 = String.valueOf(var8);
                                 if (var13.length() != 0) {
                                    var13 = "Expected String value for key ".concat(var13);
                                 } else {
                                    var13 = new String("Expected String value for key ");
                                 }

                                 throw new FastParser.ParseException(var13);
                              }

                              var15.put(var8, zab(var1, this.zab, this.zad, (char[])null));
                              var9 = this.zaj(var1);
                              if (var9 != ',') {
                                 if (var9 != '}') {
                                    var12 = new StringBuilder(48);
                                    var12.append("Unexpected character while parsing string map: ");
                                    var12.append(var9);
                                    throw new FastParser.ParseException(var12.toString());
                                 }

                                 this.zaa(1);
                                 break;
                              }
                           }
                        }
                     }

                     var2.zaa(var6, (Map)var15);
                     break;
                  case 11:
                     if (var6.zab) {
                        var7 = this.zaj(var1);
                        if (var7 == 'n') {
                           this.zab(var1, zaf);
                           var2.addConcreteTypeArrayInternal(var6, var6.zae, (ArrayList)null);
                        } else {
                           this.zal.push(5);
                           if (var7 != '[') {
                              throw new FastParser.ParseException("Expected array start");
                           }

                           var2.addConcreteTypeArrayInternal(var6, var6.zae, this.zaa(var1, var6));
                        }
                     } else {
                        var7 = this.zaj(var1);
                        if (var7 == 'n') {
                           this.zab(var1, zaf);
                           var2.addConcreteTypeInternal(var6, var6.zae, (FastJsonResponse)null);
                        } else {
                           this.zal.push(var5);
                           if (var7 != '{') {
                              throw new FastParser.ParseException("Expected start of object");
                           }

                           try {
                              FastJsonResponse var14 = var6.zac();
                              this.zaa(var1, var14);
                              var2.addConcreteTypeInternal(var6, var6.zae, var14);
                           } catch (InstantiationException var10) {
                              throw new FastParser.ParseException("Error instantiating inner object", var10);
                           } catch (IllegalAccessException var11) {
                              throw new FastParser.ParseException("Error instantiating inner object", var11);
                           }
                        }
                     }
                     break;
                  default:
                     int var16 = var6.zaa;
                     var12 = new StringBuilder(30);
                     var12.append("Invalid field type ");
                     var12.append(var16);
                     throw new FastParser.ParseException(var12.toString());
                  }

                  this.zaa(4);
                  this.zaa(2);
                  var9 = this.zaj(var1);
                  if (var9 != ',') {
                     if (var9 != '}') {
                        var12 = new StringBuilder(55);
                        var12.append("Expected end of object or field separator, but found: ");
                        var12.append(var9);
                        throw new FastParser.ParseException(var12.toString());
                     }

                     var4 = null;
                  } else {
                     var4 = this.zaa(var1);
                  }
               } else {
                  var4 = this.zab(var1);
               }
            }

            this.zaa(1);
            return true;
         }
      }
   }

   private final boolean zaa(BufferedReader var1, boolean var2) throws FastParser.ParseException, IOException {
      while(true) {
         char var3 = this.zaj(var1);
         if (var3 != '"') {
            char[] var4;
            if (var3 != 'f') {
               if (var3 != 'n') {
                  if (var3 == 't') {
                     if (var2) {
                        var4 = zah;
                     } else {
                        var4 = zag;
                     }

                     this.zab(var1, var4);
                     return true;
                  }

                  StringBuilder var5 = new StringBuilder(19);
                  var5.append("Unexpected token: ");
                  var5.append(var3);
                  throw new FastParser.ParseException(var5.toString());
               }

               this.zab(var1, zaf);
               return false;
            }

            if (var2) {
               var4 = zaj;
            } else {
               var4 = zai;
            }

            this.zab(var1, var4);
            return false;
         }

         if (var2) {
            throw new FastParser.ParseException("No boolean value found in string");
         }

         var2 = true;
      }
   }

   // $FF: synthetic method
   static long zab(FastParser var0, BufferedReader var1) throws FastParser.ParseException, IOException {
      return var0.zae(var1);
   }

   private final String zab(BufferedReader var1) throws FastParser.ParseException, IOException {
      var1.mark(1024);
      char var2 = this.zaj(var1);
      char var4;
      StringBuilder var7;
      boolean var8;
      if (var2 == '"') {
         if (var1.read(this.zaa) == -1) {
            throw new FastParser.ParseException("Unexpected EOF while parsing string");
         }

         char var9 = this.zaa[0];

         for(var8 = false; var9 != '"' || var8; var9 = var4) {
            if (var9 == '\\') {
               var8 ^= true;
            } else {
               var8 = false;
            }

            if (var1.read(this.zaa) == -1) {
               throw new FastParser.ParseException("Unexpected EOF while parsing string");
            }

            var4 = this.zaa[0];
            if (Character.isISOControl(var4)) {
               throw new FastParser.ParseException("Unexpected control character while reading string");
            }
         }
      } else {
         if (var2 == ',') {
            throw new FastParser.ParseException("Missing value");
         }

         int var3 = 1;
         if (var2 != '[') {
            if (var2 != '{') {
               var1.reset();
               this.zaa(var1, this.zac);
            } else {
               this.zal.push(1);
               var1.mark(32);
               var4 = this.zaj(var1);
               if (var4 == '}') {
                  this.zaa(1);
               } else {
                  if (var4 != '"') {
                     var7 = new StringBuilder(18);
                     var7.append("Unexpected token ");
                     var7.append(var4);
                     throw new FastParser.ParseException(var7.toString());
                  }

                  var1.reset();
                  this.zaa(var1);

                  while(this.zab(var1) != null) {
                  }

                  this.zaa(1);
               }
            }
         } else {
            this.zal.push(5);
            var1.mark(32);
            if (this.zaj(var1) == ']') {
               this.zaa(5);
            } else {
               var1.reset();
               var8 = false;
               boolean var5 = false;

               while(true) {
                  if (var3 <= 0) {
                     this.zaa(5);
                     break;
                  }

                  var4 = this.zaj(var1);
                  if (var4 == 0) {
                     throw new FastParser.ParseException("Unexpected EOF while parsing array");
                  }

                  if (Character.isISOControl(var4)) {
                     throw new FastParser.ParseException("Unexpected control character while reading array");
                  }

                  boolean var6 = var5;
                  if (var4 == '"') {
                     var6 = var5;
                     if (!var8) {
                        var6 = var5 ^ true;
                     }
                  }

                  int var10 = var3;
                  if (var4 == '[') {
                     var10 = var3;
                     if (!var6) {
                        var10 = var3 + 1;
                     }
                  }

                  var3 = var10;
                  if (var4 == ']') {
                     var3 = var10;
                     if (!var6) {
                        var3 = var10 - 1;
                     }
                  }

                  if (var4 == '\\' && var6) {
                     var8 ^= true;
                     var5 = var6;
                  } else {
                     var8 = false;
                     var5 = var6;
                  }
               }
            }
         }
      }

      var4 = this.zaj(var1);
      if (var4 != ',') {
         if (var4 == '}') {
            this.zaa(2);
            return null;
         } else {
            var7 = new StringBuilder(18);
            var7.append("Unexpected token ");
            var7.append(var4);
            throw new FastParser.ParseException(var7.toString());
         }
      } else {
         this.zaa(2);
         return this.zaa(var1);
      }
   }

   private static String zab(BufferedReader var0, char[] var1, StringBuilder var2, char[] var3) throws FastParser.ParseException, IOException {
      var2.setLength(0);
      var0.mark(var1.length);
      boolean var4 = false;
      boolean var5 = false;

      while(true) {
         int var6 = var0.read(var1);
         if (var6 == -1) {
            throw new FastParser.ParseException("Unexpected EOF while parsing string");
         }

         boolean var10;
         for(int var7 = 0; var7 < var6; var5 = var10) {
            char var8 = var1[var7];
            if (Character.isISOControl(var8)) {
               label54: {
                  if (var3 != null) {
                     for(int var9 = 0; var9 < var3.length; ++var9) {
                        if (var3[var9] == var8) {
                           var10 = true;
                           break label54;
                        }
                     }
                  }

                  var10 = false;
               }

               if (!var10) {
                  throw new FastParser.ParseException("Unexpected control character while reading string");
               }
            }

            if (var8 == '"' && !var4) {
               var2.append(var1, 0, var7);
               var0.reset();
               var0.skip((long)(var7 + 1));
               if (var5) {
                  return JsonUtils.unescapeString(var2.toString());
               }

               return var2.toString();
            }

            if (var8 == '\\') {
               var5 = var4 ^ true;
               var10 = true;
            } else {
               var4 = false;
               var10 = var5;
               var5 = var4;
            }

            ++var7;
            var4 = var5;
         }

         var2.append(var1, 0, var6);
         var0.mark(var1.length);
      }
   }

   private final void zab(BufferedReader var1, char[] var2) throws FastParser.ParseException, IOException {
      int var4;
      for(int var3 = 0; var3 < var2.length; var3 += var4) {
         var4 = var1.read(this.zab, 0, var2.length - var3);
         if (var4 == -1) {
            throw new FastParser.ParseException("Unexpected EOF");
         }

         for(int var5 = 0; var5 < var4; ++var5) {
            if (var2[var5 + var3] != this.zab[var5]) {
               throw new FastParser.ParseException("Unexpected character");
            }
         }
      }

   }

   // $FF: synthetic method
   static float zac(FastParser var0, BufferedReader var1) throws FastParser.ParseException, IOException {
      return var0.zag(var1);
   }

   private final String zac(BufferedReader var1) throws FastParser.ParseException, IOException {
      return this.zaa(var1, this.zab, this.zad, (char[])null);
   }

   // $FF: synthetic method
   static double zad(FastParser var0, BufferedReader var1) throws FastParser.ParseException, IOException {
      return var0.zah(var1);
   }

   private final int zad(BufferedReader var1) throws FastParser.ParseException, IOException {
      int var2 = this.zaa(var1, this.zac);
      int var3 = 0;
      if (var2 == 0) {
         return 0;
      } else {
         char[] var8 = this.zac;
         if (var2 > 0) {
            int var4;
            byte var5;
            boolean var6;
            if (var8[0] == '-') {
               var4 = Integer.MIN_VALUE;
               var5 = 1;
               var6 = true;
            } else {
               var4 = -2147483647;
               var5 = 0;
               var6 = false;
            }

            int var7 = var5;
            if (var5 < var2) {
               var7 = Character.digit(var8[var5], 10);
               if (var7 < 0) {
                  throw new FastParser.ParseException("Unexpected non-digit character");
               }

               var3 = -var7;
               var7 = var5 + 1;
            }

            while(var7 < var2) {
               int var9 = Character.digit(var8[var7], 10);
               if (var9 < 0) {
                  throw new FastParser.ParseException("Unexpected non-digit character");
               }

               if (var3 < -214748364) {
                  throw new FastParser.ParseException("Number too large");
               }

               var3 *= 10;
               if (var3 < var4 + var9) {
                  throw new FastParser.ParseException("Number too large");
               }

               var3 -= var9;
               ++var7;
            }

            if (var6) {
               if (var7 > 1) {
                  return var3;
               } else {
                  throw new FastParser.ParseException("No digits to parse");
               }
            } else {
               return -var3;
            }
         } else {
            throw new FastParser.ParseException("No number to parse");
         }
      }
   }

   private final long zae(BufferedReader var1) throws FastParser.ParseException, IOException {
      int var2 = this.zaa(var1, this.zac);
      long var3 = 0L;
      if (var2 == 0) {
         return 0L;
      } else {
         char[] var12 = this.zac;
         if (var2 > 0) {
            byte var5 = 0;
            long var6;
            boolean var8;
            if (var12[0] == '-') {
               var6 = Long.MIN_VALUE;
               var5 = 1;
               var8 = true;
            } else {
               var6 = -9223372036854775807L;
               var8 = false;
            }

            int var9 = var5;
            if (var5 < var2) {
               var9 = Character.digit(var12[var5], 10);
               if (var9 < 0) {
                  throw new FastParser.ParseException("Unexpected non-digit character");
               }

               var3 = (long)(-var9);
               var9 = var5 + 1;
            }

            while(var9 < var2) {
               int var13 = Character.digit(var12[var9], 10);
               if (var13 < 0) {
                  throw new FastParser.ParseException("Unexpected non-digit character");
               }

               if (var3 < -922337203685477580L) {
                  throw new FastParser.ParseException("Number too large");
               }

               long var10 = var3 * 10L;
               var3 = (long)var13;
               if (var10 < var6 + var3) {
                  throw new FastParser.ParseException("Number too large");
               }

               var3 = var10 - var3;
               ++var9;
            }

            if (var8) {
               if (var9 > 1) {
                  return var3;
               } else {
                  throw new FastParser.ParseException("No digits to parse");
               }
            } else {
               return -var3;
            }
         } else {
            throw new FastParser.ParseException("No number to parse");
         }
      }
   }

   // $FF: synthetic method
   static String zae(FastParser var0, BufferedReader var1) throws FastParser.ParseException, IOException {
      return var0.zac(var1);
   }

   // $FF: synthetic method
   static BigInteger zaf(FastParser var0, BufferedReader var1) throws FastParser.ParseException, IOException {
      return var0.zaf(var1);
   }

   private final BigInteger zaf(BufferedReader var1) throws FastParser.ParseException, IOException {
      int var2 = this.zaa(var1, this.zac);
      return var2 == 0 ? null : new BigInteger(new String(this.zac, 0, var2));
   }

   private final float zag(BufferedReader var1) throws FastParser.ParseException, IOException {
      int var2 = this.zaa(var1, this.zac);
      return var2 == 0 ? 0.0F : Float.parseFloat(new String(this.zac, 0, var2));
   }

   // $FF: synthetic method
   static BigDecimal zag(FastParser var0, BufferedReader var1) throws FastParser.ParseException, IOException {
      return var0.zai(var1);
   }

   private final double zah(BufferedReader var1) throws FastParser.ParseException, IOException {
      int var2 = this.zaa(var1, this.zac);
      return var2 == 0 ? 0.0D : Double.parseDouble(new String(this.zac, 0, var2));
   }

   private final BigDecimal zai(BufferedReader var1) throws FastParser.ParseException, IOException {
      int var2 = this.zaa(var1, this.zac);
      return var2 == 0 ? null : new BigDecimal(new String(this.zac, 0, var2));
   }

   private final char zaj(BufferedReader var1) throws FastParser.ParseException, IOException {
      if (var1.read(this.zaa) == -1) {
         return '\u0000';
      } else {
         do {
            if (!Character.isWhitespace(this.zaa[0])) {
               return this.zaa[0];
            }
         } while(var1.read(this.zaa) != -1);

         return '\u0000';
      }
   }

   public void parse(InputStream param1, T param2) throws FastParser.ParseException {
      // $FF: Couldn't be decompiled
   }

   public static class ParseException extends Exception {
      public ParseException(String var1) {
         super(var1);
      }

      public ParseException(String var1, Throwable var2) {
         super(var1, var2);
      }

      public ParseException(Throwable var1) {
         super(var1);
      }
   }

   private interface zaa<O> {
      O zaa(FastParser var1, BufferedReader var2) throws FastParser.ParseException, IOException;
   }
}
