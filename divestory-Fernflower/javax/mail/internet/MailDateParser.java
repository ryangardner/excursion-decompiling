package javax.mail.internet;

class MailDateParser {
   int index = 0;
   char[] orig = null;

   public MailDateParser(char[] var1) {
      this.orig = var1;
   }

   int getIndex() {
      return this.index;
   }

   public int parseAlphaTimeZone() throws java.text.ParseException {
      char[] var1;
      short var17;
      int var2;
      boolean var4;
      char var15;
      label152: {
         boolean var10001;
         try {
            var1 = this.orig;
            var2 = this.index;
         } catch (ArrayIndexOutOfBoundsException var13) {
            var10001 = false;
            throw new java.text.ParseException("Bad Alpha TimeZone", this.index);
         }

         int var3 = var2 + 1;

         try {
            this.index = var3;
         } catch (ArrayIndexOutOfBoundsException var12) {
            var10001 = false;
            throw new java.text.ParseException("Bad Alpha TimeZone", this.index);
         }

         java.text.ParseException var14;
         label116: {
            label115: {
               var15 = var1[var2];
               var4 = false;
               char var16;
               switch(var15) {
               case 'C':
               case 'c':
                  var17 = 360;
                  break label115;
               case 'E':
               case 'e':
                  var17 = 300;
                  break label115;
               case 'G':
               case 'g':
                  try {
                     var1 = this.orig;
                     this.index = var3 + 1;
                  } catch (ArrayIndexOutOfBoundsException var10) {
                     var10001 = false;
                     throw new java.text.ParseException("Bad Alpha TimeZone", this.index);
                  }

                  var16 = var1[var3];
                  if (var16 != 'M' && var16 != 'm') {
                     break label116;
                  }

                  try {
                     var1 = this.orig;
                     var3 = this.index++;
                  } catch (ArrayIndexOutOfBoundsException var9) {
                     var10001 = false;
                     throw new java.text.ParseException("Bad Alpha TimeZone", this.index);
                  }

                  var16 = var1[var3];
                  if (var16 != 'T' && var16 != 't') {
                     break label116;
                  }
                  break;
               case 'M':
               case 'm':
                  var17 = 420;
                  break label115;
               case 'P':
               case 'p':
                  var17 = 480;
                  break label115;
               case 'U':
               case 'u':
                  try {
                     var1 = this.orig;
                     this.index = var3 + 1;
                  } catch (ArrayIndexOutOfBoundsException var11) {
                     var10001 = false;
                     throw new java.text.ParseException("Bad Alpha TimeZone", this.index);
                  }

                  var16 = var1[var3];
                  if (var16 != 'T' && var16 != 't') {
                     try {
                        var14 = new java.text.ParseException("Bad Alpha TimeZone", this.index);
                        throw var14;
                     } catch (ArrayIndexOutOfBoundsException var5) {
                        var10001 = false;
                        throw new java.text.ParseException("Bad Alpha TimeZone", this.index);
                     }
                  }
                  break;
               default:
                  try {
                     var14 = new java.text.ParseException;
                  } catch (ArrayIndexOutOfBoundsException var8) {
                     var10001 = false;
                     throw new java.text.ParseException("Bad Alpha TimeZone", this.index);
                  }

                  try {
                     var14.<init>("Bad Alpha TimeZone", this.index);
                     throw var14;
                  } catch (ArrayIndexOutOfBoundsException var7) {
                     var10001 = false;
                     throw new java.text.ParseException("Bad Alpha TimeZone", this.index);
                  }
               }

               var17 = 0;
               break label152;
            }

            var4 = true;
            break label152;
         }

         try {
            var14 = new java.text.ParseException("Bad Alpha TimeZone", this.index);
            throw var14;
         } catch (ArrayIndexOutOfBoundsException var6) {
            var10001 = false;
            throw new java.text.ParseException("Bad Alpha TimeZone", this.index);
         }
      }

      var2 = var17;
      if (var4) {
         var1 = this.orig;
         var2 = this.index++;
         char var18 = var1[var2];
         if (var18 != 'S' && var18 != 's') {
            if (var18 != 'D') {
               var2 = var17;
               if (var18 != 'd') {
                  return var2;
               }
            }

            var1 = this.orig;
            var2 = this.index++;
            var15 = var1[var2];
            if (var15 != 'T' && var15 == 't') {
               throw new java.text.ParseException("Bad Alpha TimeZone", this.index);
            }

            var2 = var17 - 60;
         } else {
            var1 = this.orig;
            var2 = this.index++;
            var18 = var1[var2];
            var2 = var17;
            if (var18 != 'T') {
               if (var18 != 't') {
                  throw new java.text.ParseException("Bad Alpha TimeZone", this.index);
               }

               var2 = var17;
            }
         }
      }

      return var2;
   }

   public int parseMonth() throws java.text.ParseException {
      char[] var1;
      int var2;
      char var22;
      label485: {
         boolean var10001;
         try {
            var1 = this.orig;
            var2 = this.index++;
         } catch (ArrayIndexOutOfBoundsException var21) {
            var10001 = false;
            throw new java.text.ParseException("Bad Month", this.index);
         }

         var22 = var1[var2];
         if (var22 != 'A') {
            label486: {
               if (var22 != 'D') {
                  label487: {
                     label488: {
                        label489: {
                           if (var22 != 'F') {
                              if (var22 == 'J') {
                                 break label488;
                              }

                              if (var22 == 'S') {
                                 break label489;
                              }

                              if (var22 == 'a') {
                                 break label486;
                              }

                              if (var22 == 'd') {
                                 break label487;
                              }

                              if (var22 != 'f') {
                                 if (var22 == 'j') {
                                    break label488;
                                 }

                                 if (var22 != 's') {
                                    label491: {
                                       label249:
                                       switch(var22) {
                                       case 'M':
                                          break;
                                       case 'N':
                                          break label491;
                                       default:
                                          switch(var22) {
                                          case 'm':
                                             break label249;
                                          case 'n':
                                             break label491;
                                          case 'o':
                                             break;
                                          default:
                                             throw new java.text.ParseException("Bad Month", this.index);
                                          }
                                       case 'O':
                                          try {
                                             var1 = this.orig;
                                             var2 = this.index++;
                                          } catch (ArrayIndexOutOfBoundsException var4) {
                                             var10001 = false;
                                             throw new java.text.ParseException("Bad Month", this.index);
                                          }

                                          var22 = var1[var2];
                                          if (var22 == 'C' || var22 == 'c') {
                                             try {
                                                var1 = this.orig;
                                                var2 = this.index++;
                                             } catch (ArrayIndexOutOfBoundsException var3) {
                                                var10001 = false;
                                                throw new java.text.ParseException("Bad Month", this.index);
                                             }

                                             var22 = var1[var2];
                                             if (var22 == 'T' || var22 == 't') {
                                                return 9;
                                             }
                                          }

                                          throw new java.text.ParseException("Bad Month", this.index);
                                       }

                                       try {
                                          var1 = this.orig;
                                          var2 = this.index++;
                                       } catch (ArrayIndexOutOfBoundsException var8) {
                                          var10001 = false;
                                          throw new java.text.ParseException("Bad Month", this.index);
                                       }

                                       var22 = var1[var2];
                                       if (var22 != 'A' && var22 != 'a') {
                                          throw new java.text.ParseException("Bad Month", this.index);
                                       }

                                       try {
                                          var1 = this.orig;
                                          var2 = this.index++;
                                       } catch (ArrayIndexOutOfBoundsException var7) {
                                          var10001 = false;
                                          throw new java.text.ParseException("Bad Month", this.index);
                                       }

                                       var22 = var1[var2];
                                       if (var22 == 'R' || var22 == 'r') {
                                          return 2;
                                       }

                                       if (var22 == 'Y' || var22 == 'y') {
                                          return 4;
                                       }

                                       throw new java.text.ParseException("Bad Month", this.index);
                                    }

                                    try {
                                       var1 = this.orig;
                                       var2 = this.index++;
                                    } catch (ArrayIndexOutOfBoundsException var6) {
                                       var10001 = false;
                                       throw new java.text.ParseException("Bad Month", this.index);
                                    }

                                    var22 = var1[var2];
                                    if (var22 == 'O' || var22 == 'o') {
                                       try {
                                          var1 = this.orig;
                                          var2 = this.index++;
                                       } catch (ArrayIndexOutOfBoundsException var5) {
                                          var10001 = false;
                                          throw new java.text.ParseException("Bad Month", this.index);
                                       }

                                       var22 = var1[var2];
                                       if (var22 == 'V' || var22 == 'v') {
                                          return 10;
                                       }
                                    }

                                    throw new java.text.ParseException("Bad Month", this.index);
                                 }
                                 break label489;
                              }
                           }

                           try {
                              var1 = this.orig;
                              var2 = this.index++;
                           } catch (ArrayIndexOutOfBoundsException var15) {
                              var10001 = false;
                              throw new java.text.ParseException("Bad Month", this.index);
                           }

                           var22 = var1[var2];
                           if (var22 != 'E' && var22 != 'e') {
                              throw new java.text.ParseException("Bad Month", this.index);
                           }

                           try {
                              var1 = this.orig;
                              var2 = this.index++;
                           } catch (ArrayIndexOutOfBoundsException var14) {
                              var10001 = false;
                              throw new java.text.ParseException("Bad Month", this.index);
                           }

                           var22 = var1[var2];
                           if (var22 == 'B' || var22 == 'b') {
                              return 1;
                           }

                           throw new java.text.ParseException("Bad Month", this.index);
                        }

                        try {
                           var1 = this.orig;
                           var2 = this.index++;
                        } catch (ArrayIndexOutOfBoundsException var10) {
                           var10001 = false;
                           throw new java.text.ParseException("Bad Month", this.index);
                        }

                        var22 = var1[var2];
                        if (var22 != 'E' && var22 != 'e') {
                           throw new java.text.ParseException("Bad Month", this.index);
                        }

                        try {
                           var1 = this.orig;
                           var2 = this.index++;
                        } catch (ArrayIndexOutOfBoundsException var9) {
                           var10001 = false;
                           throw new java.text.ParseException("Bad Month", this.index);
                        }

                        var22 = var1[var2];
                        if (var22 == 'P' || var22 == 'p') {
                           return 8;
                        }

                        throw new java.text.ParseException("Bad Month", this.index);
                     }

                     try {
                        var1 = this.orig;
                        var2 = this.index++;
                     } catch (ArrayIndexOutOfBoundsException var13) {
                        var10001 = false;
                        throw new java.text.ParseException("Bad Month", this.index);
                     }

                     label274: {
                        var22 = var1[var2];
                        if (var22 != 'A') {
                           if (var22 == 'U') {
                              break label274;
                           }

                           if (var22 != 'a') {
                              if (var22 != 'u') {
                                 throw new java.text.ParseException("Bad Month", this.index);
                              }
                              break label274;
                           }
                        }

                        try {
                           var1 = this.orig;
                           var2 = this.index++;
                           break label485;
                        } catch (ArrayIndexOutOfBoundsException var12) {
                           var10001 = false;
                           throw new java.text.ParseException("Bad Month", this.index);
                        }
                     }

                     try {
                        var1 = this.orig;
                        var2 = this.index++;
                     } catch (ArrayIndexOutOfBoundsException var11) {
                        var10001 = false;
                        throw new java.text.ParseException("Bad Month", this.index);
                     }

                     var22 = var1[var2];
                     if (var22 == 'N' || var22 == 'n') {
                        return 5;
                     }

                     if (var22 == 'L' || var22 == 'l') {
                        return 6;
                     }

                     throw new java.text.ParseException("Bad Month", this.index);
                  }
               }

               try {
                  var1 = this.orig;
                  var2 = this.index++;
               } catch (ArrayIndexOutOfBoundsException var17) {
                  var10001 = false;
                  throw new java.text.ParseException("Bad Month", this.index);
               }

               var22 = var1[var2];
               if (var22 != 'E' && var22 != 'e') {
                  throw new java.text.ParseException("Bad Month", this.index);
               }

               try {
                  var1 = this.orig;
                  var2 = this.index++;
               } catch (ArrayIndexOutOfBoundsException var16) {
                  var10001 = false;
                  throw new java.text.ParseException("Bad Month", this.index);
               }

               var22 = var1[var2];
               if (var22 == 'C' || var22 == 'c') {
                  return 11;
               }

               throw new java.text.ParseException("Bad Month", this.index);
            }
         }

         try {
            var1 = this.orig;
            var2 = this.index++;
         } catch (ArrayIndexOutOfBoundsException var20) {
            var10001 = false;
            throw new java.text.ParseException("Bad Month", this.index);
         }

         var22 = var1[var2];
         if (var22 != 'P' && var22 != 'p') {
            if (var22 == 'U' || var22 == 'u') {
               try {
                  var1 = this.orig;
                  var2 = this.index++;
               } catch (ArrayIndexOutOfBoundsException var18) {
                  var10001 = false;
                  throw new java.text.ParseException("Bad Month", this.index);
               }

               var22 = var1[var2];
               if (var22 == 'G' || var22 == 'g') {
                  return 7;
               }

               throw new java.text.ParseException("Bad Month", this.index);
            }
         } else {
            try {
               var1 = this.orig;
               var2 = this.index++;
            } catch (ArrayIndexOutOfBoundsException var19) {
               var10001 = false;
               throw new java.text.ParseException("Bad Month", this.index);
            }

            var22 = var1[var2];
            if (var22 == 'R' || var22 == 'r') {
               return 3;
            }
         }

         throw new java.text.ParseException("Bad Month", this.index);
      }

      var22 = var1[var2];
      if (var22 == 'N' || var22 == 'n') {
         return 0;
      } else {
         throw new java.text.ParseException("Bad Month", this.index);
      }
   }

   public int parseNumber() throws java.text.ParseException {
      int var1 = this.orig.length;
      boolean var2 = false;
      int var3 = 0;

      while(true) {
         int var4 = this.index;
         if (var4 >= var1) {
            if (var2) {
               return var3;
            } else {
               throw new java.text.ParseException("No Number found", this.index);
            }
         }

         switch(this.orig[var4]) {
         case '0':
            var3 *= 10;
            break;
         case '1':
            var3 = var3 * 10 + 1;
            break;
         case '2':
            var3 = var3 * 10 + 2;
            break;
         case '3':
            var3 = var3 * 10 + 3;
            break;
         case '4':
            var3 = var3 * 10 + 4;
            break;
         case '5':
            var3 = var3 * 10 + 5;
            break;
         case '6':
            var3 = var3 * 10 + 6;
            break;
         case '7':
            var3 = var3 * 10 + 7;
            break;
         case '8':
            var3 = var3 * 10 + 8;
            break;
         case '9':
            var3 = var3 * 10 + 9;
            break;
         default:
            if (var2) {
               return var3;
            }

            throw new java.text.ParseException("No Number found", this.index);
         }

         ++this.index;
         var2 = true;
      }
   }

   public int parseNumericTimeZone() throws java.text.ParseException {
      char[] var1 = this.orig;
      int var2 = this.index++;
      char var4 = var1[var2];
      boolean var5;
      if (var4 == '+') {
         var5 = true;
      } else {
         if (var4 != '-') {
            throw new java.text.ParseException("Bad Numeric TimeZone", this.index);
         }

         var5 = false;
      }

      int var3 = this.parseNumber();
      var3 = var3 / 100 * 60 + var3 % 100;
      return var5 ? -var3 : var3;
   }

   public int parseTimeZone() throws java.text.ParseException {
      int var1 = this.index;
      char[] var2 = this.orig;
      if (var1 < var2.length) {
         char var3 = var2[var1];
         return var3 != '+' && var3 != '-' ? this.parseAlphaTimeZone() : this.parseNumericTimeZone();
      } else {
         throw new java.text.ParseException("No more characters", this.index);
      }
   }

   public int peekChar() throws java.text.ParseException {
      int var1 = this.index;
      char[] var2 = this.orig;
      if (var1 < var2.length) {
         return var2[var1];
      } else {
         throw new java.text.ParseException("No more characters", this.index);
      }
   }

   public void skipChar(char var1) throws java.text.ParseException {
      int var2 = this.index;
      char[] var3 = this.orig;
      if (var2 < var3.length) {
         if (var3[var2] == var1) {
            this.index = var2 + 1;
         } else {
            throw new java.text.ParseException("Wrong char", this.index);
         }
      } else {
         throw new java.text.ParseException("No more characters", this.index);
      }
   }

   public boolean skipIfChar(char var1) throws java.text.ParseException {
      int var2 = this.index;
      char[] var3 = this.orig;
      if (var2 < var3.length) {
         if (var3[var2] == var1) {
            this.index = var2 + 1;
            return true;
         } else {
            return false;
         }
      } else {
         throw new java.text.ParseException("No more characters", this.index);
      }
   }

   public void skipUntilNumber() throws java.text.ParseException {
      while(true) {
         boolean var10001;
         try {
            switch(this.orig[this.index]) {
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
               return;
            }
         } catch (ArrayIndexOutOfBoundsException var5) {
            var10001 = false;
            break;
         }

         int var1;
         try {
            var1 = this.index;
         } catch (ArrayIndexOutOfBoundsException var4) {
            var10001 = false;
            break;
         }

         try {
            this.index = var1 + 1;
         } catch (ArrayIndexOutOfBoundsException var3) {
            var10001 = false;
            break;
         }
      }

      throw new java.text.ParseException("No Number Found", this.index);
   }

   public void skipWhiteSpace() {
      int var1 = this.orig.length;

      while(true) {
         int var2 = this.index;
         if (var2 >= var1) {
            return;
         }

         char var3 = this.orig[var2];
         if (var3 != '\t' && var3 != '\n' && var3 != '\r' && var3 != ' ') {
            return;
         }

         ++this.index;
      }
   }
}
