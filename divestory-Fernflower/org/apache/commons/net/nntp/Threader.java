package org.apache.commons.net.nntp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Threader {
   private void buildContainer(Threadable var1, HashMap<String, ThreadContainer> var2) {
      String var3 = var1.messageThreadId();
      ThreadContainer var4 = (ThreadContainer)var2.get(var3);
      int var5 = 0;
      String var6 = var3;
      ThreadContainer var7 = var4;
      if (var4 != null) {
         if (var4.threadable != null) {
            StringBuilder var15 = new StringBuilder();
            var15.append("<Bogus-id:");
            var15.append(0);
            var15.append(">");
            var6 = var15.toString();
            var7 = null;
         } else {
            var4.threadable = var1;
            var7 = var4;
            var6 = var3;
         }
      }

      ThreadContainer var13 = var7;
      if (var7 == null) {
         var13 = new ThreadContainer();
         var13.threadable = var1;
         var2.put(var6, var13);
      }

      String[] var14 = var1.messageThreadReferences();
      int var8 = var14.length;

      ThreadContainer var10;
      ThreadContainer var16;
      for(var10 = null; var5 < var8; var10 = var7) {
         String var9 = var14[var5];
         var16 = (ThreadContainer)var2.get(var9);
         var7 = var16;
         if (var16 == null) {
            var7 = new ThreadContainer();
            var2.put(var9, var7);
         }

         if (var10 != null && var7.parent == null && var10 != var7 && !var7.findChild(var10)) {
            var7.parent = var10;
            var7.next = var10.child;
            var10.child = var7;
         }

         ++var5;
      }

      ThreadContainer var11 = var10;
      if (var10 != null) {
         label70: {
            if (var10 != var13) {
               var11 = var10;
               if (!var13.findChild(var10)) {
                  break label70;
               }
            }

            var11 = null;
         }
      }

      if (var13.parent != null) {
         var10 = var13.parent.child;

         for(var7 = null; var10 != null && var10 != var13; var10 = var16) {
            var16 = var10.next;
            var7 = var10;
         }

         if (var10 == null) {
            StringBuilder var12 = new StringBuilder();
            var12.append("Didnt find ");
            var12.append(var13);
            var12.append(" in parent");
            var12.append(var13.parent);
            throw new RuntimeException(var12.toString());
         }

         if (var7 == null) {
            var13.parent.child = var13.next;
         } else {
            var7.next = var13.next;
         }

         var13.next = null;
         var13.parent = null;
      }

      if (var11 != null) {
         var13.parent = var11;
         var13.next = var11.child;
         var11.child = var13;
      }

   }

   private ThreadContainer findRootSet(HashMap<String, ThreadContainer> var1) {
      ThreadContainer var2 = new ThreadContainer();
      Iterator var3 = var1.keySet().iterator();

      while(var3.hasNext()) {
         ThreadContainer var4 = (ThreadContainer)var1.get(var3.next());
         if (var4.parent == null) {
            if (var4.next != null) {
               StringBuilder var5 = new StringBuilder();
               var5.append("c.next is ");
               var5.append(var4.next.toString());
               throw new RuntimeException(var5.toString());
            }

            var4.next = var2.child;
            var2.child = var4;
         }
      }

      return var2;
   }

   private void gatherSubjects(ThreadContainer var1) {
      ThreadContainer var2 = var1.child;
      int var3 = 0;

      int var4;
      for(var4 = 0; var2 != null; var2 = var2.next) {
         ++var4;
      }

      HashMap var5 = new HashMap((int)((double)var4 * 1.2D), 0.9F);

      ThreadContainer var13;
      for(var2 = var1.child; var2 != null; var3 = var4) {
         Threadable var6 = var2.threadable;
         Threadable var7 = var6;
         if (var6 == null) {
            var7 = var2.child.threadable;
         }

         String var11 = var7.simplifiedSubject();
         var4 = var3;
         if (var11 != null) {
            if (var11.length() == 0) {
               var4 = var3;
            } else {
               label135: {
                  var13 = (ThreadContainer)var5.get(var11);
                  if (var13 != null && (var2.threadable != null || var13.threadable == null)) {
                     var4 = var3;
                     if (var13.threadable == null) {
                        break label135;
                     }

                     var4 = var3;
                     if (!var13.threadable.subjectIsReply()) {
                        break label135;
                     }

                     var4 = var3;
                     if (var2.threadable == null) {
                        break label135;
                     }

                     var4 = var3;
                     if (var2.threadable.subjectIsReply()) {
                        break label135;
                     }
                  }

                  var5.put(var11, var2);
                  var4 = var3 + 1;
               }
            }
         }

         var2 = var2.next;
      }

      if (var3 != 0) {
         var2 = var1.child;
         var13 = var2.next;

         ThreadContainer var16;
         for(ThreadContainer var12 = null; var2 != null; var13 = var16) {
            Threadable var8 = var2.threadable;
            Threadable var9 = var8;
            if (var8 == null) {
               var9 = var2.child.threadable;
            }

            label114: {
               String var15 = var9.simplifiedSubject();
               if (var15 != null && var15.length() != 0) {
                  ThreadContainer var14 = (ThreadContainer)var5.get(var15);
                  if (var14 != var2) {
                     if (var12 == null) {
                        var1.child = var2.next;
                     } else {
                        var12.next = var2.next;
                     }

                     var2.next = null;
                     if (var14.threadable == null && var2.threadable == null) {
                        for(var16 = var14.child; var16 != null && var16.next != null; var16 = var16.next) {
                        }

                        if (var16 != null) {
                           var16.next = var2.child;
                        }

                        for(var16 = var2.child; var16 != null; var16 = var16.next) {
                           var16.parent = var14;
                        }

                        var2.child = null;
                        break label114;
                     }

                     if (var14.threadable == null || var2.threadable != null && var2.threadable.subjectIsReply() && !var14.threadable.subjectIsReply()) {
                        var2.parent = var14;
                        var2.next = var14.child;
                        var14.child = var2;
                        break label114;
                     }

                     ThreadContainer var10 = new ThreadContainer();
                     var10.threadable = var14.threadable;
                     var10.child = var14.child;

                     for(var16 = var10.child; var16 != null; var16 = var16.next) {
                        var16.parent = var10;
                     }

                     var14.threadable = null;
                     var14.child = null;
                     var2.parent = var14;
                     var10.parent = var14;
                     var14.child = var2;
                     var2.next = var10;
                     break label114;
                  }
               }

               var12 = var2;
            }

            if (var13 == null) {
               var2 = null;
            } else {
               var2 = var13.next;
            }

            var16 = var2;
            var2 = var13;
         }

         var5.clear();
      }
   }

   private void pruneEmptyContainers(ThreadContainer var1) {
      ThreadContainer var2 = var1.child;
      ThreadContainer var3 = var2.next;
      ThreadContainer var4 = null;

      while(var2 != null) {
         ThreadContainer var5;
         if (var2.threadable == null && var2.child == null) {
            if (var4 == null) {
               var1.child = var2.next;
            } else {
               var4.next = var2.next;
            }
         } else if (var2.threadable == null && var2.child != null && (var2.parent != null || var2.child.next == null)) {
            var3 = var2.child;
            if (var4 == null) {
               var1.child = var3;
            } else {
               var4.next = var3;
            }

            for(var5 = var3; var5.next != null; var5 = var5.next) {
               var5.parent = var2.parent;
            }

            var5.parent = var2.parent;
            var5.next = var2.next;
         } else {
            if (var2.child != null) {
               this.pruneEmptyContainers(var2);
            }

            var4 = var2;
         }

         if (var3 == null) {
            var5 = null;
            var2 = var3;
            var3 = var5;
         } else {
            var5 = var3.next;
            var2 = var3;
            var3 = var5;
         }
      }

   }

   public Threadable thread(Iterable<? extends Threadable> var1) {
      Object var2 = null;
      if (var1 == null) {
         return null;
      } else {
         HashMap var3 = new HashMap();
         Iterator var4 = var1.iterator();

         Threadable var5;
         while(var4.hasNext()) {
            var5 = (Threadable)var4.next();
            if (!var5.isDummy()) {
               this.buildContainer(var5, var3);
            }
         }

         if (var3.isEmpty()) {
            return null;
         } else {
            ThreadContainer var8 = this.findRootSet(var3);
            var3.clear();
            this.pruneEmptyContainers(var8);
            var8.reverseChildren();
            this.gatherSubjects(var8);
            if (var8.next == null) {
               for(ThreadContainer var7 = var8.child; var7 != null; var7 = var7.next) {
                  if (var7.threadable == null) {
                     var7.threadable = var7.child.threadable.makeDummy();
                  }
               }

               if (var8.child == null) {
                  var5 = (Threadable)var2;
               } else {
                  var5 = var8.child.threadable;
               }

               var8.flush();
               return var5;
            } else {
               StringBuilder var6 = new StringBuilder();
               var6.append("root node has a next:");
               var6.append(var8);
               throw new RuntimeException(var6.toString());
            }
         }
      }
   }

   public Threadable thread(List<? extends Threadable> var1) {
      return this.thread((Iterable)var1);
   }

   @Deprecated
   public Threadable thread(Threadable[] var1) {
      return var1 == null ? null : this.thread(Arrays.asList(var1));
   }
}
