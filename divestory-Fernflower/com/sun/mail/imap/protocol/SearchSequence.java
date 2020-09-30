package com.sun.mail.imap.protocol;

import com.sun.mail.iap.Argument;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.search.AddressTerm;
import javax.mail.search.AndTerm;
import javax.mail.search.BodyTerm;
import javax.mail.search.DateTerm;
import javax.mail.search.FlagTerm;
import javax.mail.search.FromStringTerm;
import javax.mail.search.FromTerm;
import javax.mail.search.HeaderTerm;
import javax.mail.search.MessageIDTerm;
import javax.mail.search.NotTerm;
import javax.mail.search.OrTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.RecipientStringTerm;
import javax.mail.search.RecipientTerm;
import javax.mail.search.SearchException;
import javax.mail.search.SearchTerm;
import javax.mail.search.SentDateTerm;
import javax.mail.search.SizeTerm;
import javax.mail.search.StringTerm;
import javax.mail.search.SubjectTerm;

class SearchSequence {
   private static Calendar cal = new GregorianCalendar();
   private static String[] monthTable = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

   private static Argument and(AndTerm var0, String var1) throws SearchException, IOException {
      SearchTerm[] var4 = var0.getTerms();
      Argument var2 = generateSequence(var4[0], var1);

      for(int var3 = 1; var3 < var4.length; ++var3) {
         var2.append(generateSequence(var4[var3], var1));
      }

      return var2;
   }

   private static Argument body(BodyTerm var0, String var1) throws SearchException, IOException {
      Argument var2 = new Argument();
      var2.writeAtom("BODY");
      var2.writeString(var0.getPattern(), var1);
      return var2;
   }

   private static Argument flag(FlagTerm var0) throws SearchException {
      boolean var1 = var0.getTestSet();
      Argument var2 = new Argument();
      Flags var7 = var0.getFlags();
      Flags.Flag[] var3 = var7.getSystemFlags();
      String[] var4 = var7.getUserFlags();
      if (var3.length == 0 && var4.length == 0) {
         throw new SearchException("Invalid FlagTerm");
      } else {
         byte var5 = 0;

         int var6;
         String var8;
         for(var6 = 0; var6 < var3.length; ++var6) {
            if (var3[var6] == Flags.Flag.DELETED) {
               if (var1) {
                  var8 = "DELETED";
               } else {
                  var8 = "UNDELETED";
               }

               var2.writeAtom(var8);
            } else if (var3[var6] == Flags.Flag.ANSWERED) {
               if (var1) {
                  var8 = "ANSWERED";
               } else {
                  var8 = "UNANSWERED";
               }

               var2.writeAtom(var8);
            } else if (var3[var6] == Flags.Flag.DRAFT) {
               if (var1) {
                  var8 = "DRAFT";
               } else {
                  var8 = "UNDRAFT";
               }

               var2.writeAtom(var8);
            } else if (var3[var6] == Flags.Flag.FLAGGED) {
               if (var1) {
                  var8 = "FLAGGED";
               } else {
                  var8 = "UNFLAGGED";
               }

               var2.writeAtom(var8);
            } else if (var3[var6] == Flags.Flag.RECENT) {
               if (var1) {
                  var8 = "RECENT";
               } else {
                  var8 = "OLD";
               }

               var2.writeAtom(var8);
            } else if (var3[var6] == Flags.Flag.SEEN) {
               if (var1) {
                  var8 = "SEEN";
               } else {
                  var8 = "UNSEEN";
               }

               var2.writeAtom(var8);
            }
         }

         for(var6 = var5; var6 < var4.length; ++var6) {
            if (var1) {
               var8 = "KEYWORD";
            } else {
               var8 = "UNKEYWORD";
            }

            var2.writeAtom(var8);
            var2.writeAtom(var4[var6]);
         }

         return var2;
      }
   }

   private static Argument from(String var0, String var1) throws SearchException, IOException {
      Argument var2 = new Argument();
      var2.writeAtom("FROM");
      var2.writeString(var0, var1);
      return var2;
   }

   static Argument generateSequence(SearchTerm var0, String var1) throws SearchException, IOException {
      if (var0 instanceof AndTerm) {
         return and((AndTerm)var0, var1);
      } else if (var0 instanceof OrTerm) {
         return or((OrTerm)var0, var1);
      } else if (var0 instanceof NotTerm) {
         return not((NotTerm)var0, var1);
      } else if (var0 instanceof HeaderTerm) {
         return header((HeaderTerm)var0, var1);
      } else if (var0 instanceof FlagTerm) {
         return flag((FlagTerm)var0);
      } else if (var0 instanceof FromTerm) {
         return from(((FromTerm)var0).getAddress().toString(), var1);
      } else if (var0 instanceof FromStringTerm) {
         return from(((FromStringTerm)var0).getPattern(), var1);
      } else if (var0 instanceof RecipientTerm) {
         RecipientTerm var3 = (RecipientTerm)var0;
         return recipient(var3.getRecipientType(), var3.getAddress().toString(), var1);
      } else if (var0 instanceof RecipientStringTerm) {
         RecipientStringTerm var2 = (RecipientStringTerm)var0;
         return recipient(var2.getRecipientType(), var2.getPattern(), var1);
      } else if (var0 instanceof SubjectTerm) {
         return subject((SubjectTerm)var0, var1);
      } else if (var0 instanceof BodyTerm) {
         return body((BodyTerm)var0, var1);
      } else if (var0 instanceof SizeTerm) {
         return size((SizeTerm)var0);
      } else if (var0 instanceof SentDateTerm) {
         return sentdate((SentDateTerm)var0);
      } else if (var0 instanceof ReceivedDateTerm) {
         return receiveddate((ReceivedDateTerm)var0);
      } else if (var0 instanceof MessageIDTerm) {
         return messageid((MessageIDTerm)var0, var1);
      } else {
         throw new SearchException("Search too complex");
      }
   }

   private static Argument header(HeaderTerm var0, String var1) throws SearchException, IOException {
      Argument var2 = new Argument();
      var2.writeAtom("HEADER");
      var2.writeString(var0.getHeaderName());
      var2.writeString(var0.getPattern(), var1);
      return var2;
   }

   private static boolean isAscii(String var0) {
      int var1 = var0.length();

      for(int var2 = 0; var2 < var1; ++var2) {
         if (var0.charAt(var2) > 127) {
            return false;
         }
      }

      return true;
   }

   static boolean isAscii(SearchTerm var0) {
      boolean var1 = var0 instanceof AndTerm;
      if (!var1 && !(var0 instanceof OrTerm)) {
         if (var0 instanceof NotTerm) {
            return isAscii(((NotTerm)var0).getTerm());
         }

         if (var0 instanceof StringTerm) {
            return isAscii(((StringTerm)var0).getPattern());
         }

         if (var0 instanceof AddressTerm) {
            return isAscii(((AddressTerm)var0).getAddress().toString());
         }
      } else {
         SearchTerm[] var3;
         if (var1) {
            var3 = ((AndTerm)var0).getTerms();
         } else {
            var3 = ((OrTerm)var0).getTerms();
         }

         for(int var2 = 0; var2 < var3.length; ++var2) {
            if (!isAscii(var3[var2])) {
               return false;
            }
         }
      }

      return true;
   }

   private static Argument messageid(MessageIDTerm var0, String var1) throws SearchException, IOException {
      Argument var2 = new Argument();
      var2.writeAtom("HEADER");
      var2.writeString("Message-ID");
      var2.writeString(var0.getPattern(), var1);
      return var2;
   }

   private static Argument not(NotTerm var0, String var1) throws SearchException, IOException {
      Argument var2 = new Argument();
      var2.writeAtom("NOT");
      SearchTerm var3 = var0.getTerm();
      if (!(var3 instanceof AndTerm) && !(var3 instanceof FlagTerm)) {
         var2.append(generateSequence(var3, var1));
      } else {
         var2.writeArgument(generateSequence(var3, var1));
      }

      return var2;
   }

   private static Argument or(OrTerm var0, String var1) throws SearchException, IOException {
      SearchTerm[] var2 = var0.getTerms();
      SearchTerm[] var4 = var2;
      if (var2.length > 2) {
         Object var5 = var2[0];

         for(int var3 = 1; var3 < var2.length; ++var3) {
            var5 = new OrTerm((SearchTerm)var5, var2[var3]);
         }

         var4 = ((OrTerm)var5).getTerms();
      }

      Argument var6 = new Argument();
      if (var4.length > 1) {
         var6.writeAtom("OR");
      }

      if (!(var4[0] instanceof AndTerm) && !(var4[0] instanceof FlagTerm)) {
         var6.append(generateSequence(var4[0], var1));
      } else {
         var6.writeArgument(generateSequence(var4[0], var1));
      }

      if (var4.length > 1) {
         if (!(var4[1] instanceof AndTerm) && !(var4[1] instanceof FlagTerm)) {
            var6.append(generateSequence(var4[1], var1));
         } else {
            var6.writeArgument(generateSequence(var4[1], var1));
         }
      }

      return var6;
   }

   private static Argument receiveddate(DateTerm var0) throws SearchException {
      Argument var1 = new Argument();
      String var2 = toIMAPDate(var0.getDate());
      StringBuilder var3;
      switch(var0.getComparison()) {
      case 1:
         var3 = new StringBuilder("OR BEFORE ");
         var3.append(var2);
         var3.append(" ON ");
         var3.append(var2);
         var1.writeAtom(var3.toString());
         break;
      case 2:
         var3 = new StringBuilder("BEFORE ");
         var3.append(var2);
         var1.writeAtom(var3.toString());
         break;
      case 3:
         var3 = new StringBuilder("ON ");
         var3.append(var2);
         var1.writeAtom(var3.toString());
         break;
      case 4:
         var3 = new StringBuilder("NOT ON ");
         var3.append(var2);
         var1.writeAtom(var3.toString());
         break;
      case 5:
         var3 = new StringBuilder("SINCE ");
         var3.append(var2);
         var1.writeAtom(var3.toString());
         break;
      case 6:
         var3 = new StringBuilder("OR SINCE ");
         var3.append(var2);
         var3.append(" ON ");
         var3.append(var2);
         var1.writeAtom(var3.toString());
         break;
      default:
         throw new SearchException("Cannot handle Date Comparison");
      }

      return var1;
   }

   private static Argument recipient(Message.RecipientType var0, String var1, String var2) throws SearchException, IOException {
      Argument var3 = new Argument();
      if (var0 == Message.RecipientType.TO) {
         var3.writeAtom("TO");
      } else if (var0 == Message.RecipientType.CC) {
         var3.writeAtom("CC");
      } else {
         if (var0 != Message.RecipientType.BCC) {
            throw new SearchException("Illegal Recipient type");
         }

         var3.writeAtom("BCC");
      }

      var3.writeString(var1, var2);
      return var3;
   }

   private static Argument sentdate(DateTerm var0) throws SearchException {
      Argument var1 = new Argument();
      String var2 = toIMAPDate(var0.getDate());
      StringBuilder var3;
      switch(var0.getComparison()) {
      case 1:
         var3 = new StringBuilder("OR SENTBEFORE ");
         var3.append(var2);
         var3.append(" SENTON ");
         var3.append(var2);
         var1.writeAtom(var3.toString());
         break;
      case 2:
         var3 = new StringBuilder("SENTBEFORE ");
         var3.append(var2);
         var1.writeAtom(var3.toString());
         break;
      case 3:
         var3 = new StringBuilder("SENTON ");
         var3.append(var2);
         var1.writeAtom(var3.toString());
         break;
      case 4:
         var3 = new StringBuilder("NOT SENTON ");
         var3.append(var2);
         var1.writeAtom(var3.toString());
         break;
      case 5:
         var3 = new StringBuilder("SENTSINCE ");
         var3.append(var2);
         var1.writeAtom(var3.toString());
         break;
      case 6:
         var3 = new StringBuilder("OR SENTSINCE ");
         var3.append(var2);
         var3.append(" SENTON ");
         var3.append(var2);
         var1.writeAtom(var3.toString());
         break;
      default:
         throw new SearchException("Cannot handle Date Comparison");
      }

      return var1;
   }

   private static Argument size(SizeTerm var0) throws SearchException {
      Argument var1 = new Argument();
      int var2 = var0.getComparison();
      if (var2 != 2) {
         if (var2 != 5) {
            throw new SearchException("Cannot handle Comparison");
         }

         var1.writeAtom("LARGER");
      } else {
         var1.writeAtom("SMALLER");
      }

      var1.writeNumber(var0.getNumber());
      return var1;
   }

   private static Argument subject(SubjectTerm var0, String var1) throws SearchException, IOException {
      Argument var2 = new Argument();
      var2.writeAtom("SUBJECT");
      var2.writeString(var0.getPattern(), var1);
      return var2;
   }

   private static String toIMAPDate(Date var0) {
      StringBuffer var1 = new StringBuffer();
      cal.setTime(var0);
      var1.append(cal.get(5));
      var1.append("-");
      var1.append(monthTable[cal.get(2)]);
      var1.append('-');
      var1.append(cal.get(1));
      return var1.toString();
   }
}
