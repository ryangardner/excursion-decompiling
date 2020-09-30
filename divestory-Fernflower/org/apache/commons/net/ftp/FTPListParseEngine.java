package org.apache.commons.net.ftp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import org.apache.commons.net.util.Charsets;

public class FTPListParseEngine {
   private ListIterator<String> _internalIterator;
   private List<String> entries;
   private final FTPFileEntryParser parser;
   private final boolean saveUnparseableEntries;

   public FTPListParseEngine(FTPFileEntryParser var1) {
      this(var1, (FTPClientConfig)null);
   }

   FTPListParseEngine(FTPFileEntryParser var1, FTPClientConfig var2) {
      LinkedList var3 = new LinkedList();
      this.entries = var3;
      this._internalIterator = var3.listIterator();
      this.parser = var1;
      if (var2 != null) {
         this.saveUnparseableEntries = var2.getUnparseableEntries();
      } else {
         this.saveUnparseableEntries = false;
      }

   }

   private void readStream(InputStream var1, String var2) throws IOException {
      BufferedReader var4 = new BufferedReader(new InputStreamReader(var1, Charsets.toCharset(var2)));

      for(String var3 = this.parser.readNextEntry(var4); var3 != null; var3 = this.parser.readNextEntry(var4)) {
         this.entries.add(var3);
      }

      var4.close();
   }

   public FTPFile[] getFiles() throws IOException {
      return this.getFiles(FTPFileFilters.NON_NULL);
   }

   public FTPFile[] getFiles(FTPFileFilter var1) throws IOException {
      ArrayList var2 = new ArrayList();
      Iterator var3 = this.entries.iterator();

      while(var3.hasNext()) {
         String var4 = (String)var3.next();
         FTPFile var5 = this.parser.parseFTPEntry(var4);
         FTPFile var6 = var5;
         if (var5 == null) {
            var6 = var5;
            if (this.saveUnparseableEntries) {
               var6 = new FTPFile(var4);
            }
         }

         if (var1.accept(var6)) {
            var2.add(var6);
         }
      }

      return (FTPFile[])var2.toArray(new FTPFile[var2.size()]);
   }

   public FTPFile[] getNext(int var1) {
      LinkedList var2;
      for(var2 = new LinkedList(); var1 > 0 && this._internalIterator.hasNext(); --var1) {
         String var3 = (String)this._internalIterator.next();
         FTPFile var4 = this.parser.parseFTPEntry(var3);
         FTPFile var5 = var4;
         if (var4 == null) {
            var5 = var4;
            if (this.saveUnparseableEntries) {
               var5 = new FTPFile(var3);
            }
         }

         var2.add(var5);
      }

      return (FTPFile[])var2.toArray(new FTPFile[var2.size()]);
   }

   public FTPFile[] getPrevious(int var1) {
      LinkedList var2;
      for(var2 = new LinkedList(); var1 > 0 && this._internalIterator.hasPrevious(); --var1) {
         String var3 = (String)this._internalIterator.previous();
         FTPFile var4 = this.parser.parseFTPEntry(var3);
         FTPFile var5 = var4;
         if (var4 == null) {
            var5 = var4;
            if (this.saveUnparseableEntries) {
               var5 = new FTPFile(var3);
            }
         }

         var2.add(0, var5);
      }

      return (FTPFile[])var2.toArray(new FTPFile[var2.size()]);
   }

   public boolean hasNext() {
      return this._internalIterator.hasNext();
   }

   public boolean hasPrevious() {
      return this._internalIterator.hasPrevious();
   }

   @Deprecated
   public void readServerList(InputStream var1) throws IOException {
      this.readServerList(var1, (String)null);
   }

   public void readServerList(InputStream var1, String var2) throws IOException {
      this.entries = new LinkedList();
      this.readStream(var1, var2);
      this.parser.preParse(this.entries);
      this.resetIterator();
   }

   public void resetIterator() {
      this._internalIterator = this.entries.listIterator();
   }
}
