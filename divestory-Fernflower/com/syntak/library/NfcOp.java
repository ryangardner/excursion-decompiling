package com.syntak.library;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcF;
import android.os.Parcelable;
import android.util.Log;
import java.util.Arrays;

public class NfcOp {
   private static NfcAdapter nfcAdapter;
   private String[][] NfcTechLists;
   String TAG = "NfcOp";
   private String action;
   private Activity activity;
   private boolean flag_nfc_available;
   private IntentFilter[] intentFilters;
   int page_size = 4;
   private PendingIntent pendingIntent;
   private Tag tag;
   byte[] tagId;
   int tag_type;

   public NfcOp(Activity var1) {
      this.activity = var1;
      NfcAdapter var2 = NfcAdapter.getDefaultAdapter(var1);
      nfcAdapter = var2;
      if (var2 == null) {
         this.flag_nfc_available = false;
      } else {
         this.flag_nfc_available = true;
         this.pendingIntent = PendingIntent.getActivity(var1, 0, (new Intent(var1, var1.getClass())).addFlags(536870912), 0);
         IntentFilter var5 = new IntentFilter("android.nfc.action.NDEF_DISCOVERED");
         IntentFilter var4 = new IntentFilter("android.nfc.action.NDEF_DISCOVERED");

         try {
            var5.addDataType("*/*");
            var4.addDataScheme("http");
            this.intentFilters = new IntentFilter[]{var5, var4};
         } catch (Exception var3) {
            Log.e("TagDispatch", var3.toString());
         }

         this.NfcTechLists = new String[][]{{NfcF.class.getName()}};
      }

   }

   public String getAction() {
      return this.action;
   }

   public Tag getTag() {
      return this.tag;
   }

   public byte[] getTagId() {
      return this.tagId;
   }

   public void parseIntent(Intent var1) {
      this.action = var1.getAction();
      this.tag = (Tag)var1.getParcelableExtra("android.nfc.extra.TAG");
      this.tagId = var1.getByteArrayExtra("android.nfc.extra.ID");
      Parcelable[] var2 = var1.getParcelableArrayExtra("android.nfc.extra.NDEF_MESSAGES");
      if (var2 != null) {
         int var3 = 0;

         while(true) {
            label43: {
               Exception var10000;
               label49: {
                  NdefRecord[] var9;
                  boolean var10001;
                  try {
                     if (var3 >= var2.length) {
                        break;
                     }

                     var9 = ((NdefMessage)var2[var3]).getRecords();
                  } catch (Exception var8) {
                     var10000 = var8;
                     var10001 = false;
                     break label49;
                  }

                  int var4 = 0;

                  while(true) {
                     label36: {
                        byte[] var5;
                        try {
                           if (var4 >= var9.length) {
                              break label43;
                           }

                           if (var9[var4].getTnf() != 1 || !Arrays.equals(var9[var4].getType(), NdefRecord.RTD_TEXT)) {
                              break label36;
                           }

                           var5 = var9[var4].getPayload();
                        } catch (Exception var7) {
                           var10000 = var7;
                           var10001 = false;
                           break;
                        }

                        byte var6 = var5[0];
                        var6 = var5[0];
                     }

                     ++var4;
                  }
               }

               Exception var10 = var10000;
               Log.e("TagDispatch", var10.toString());
               break;
            }

            ++var3;
         }
      }

   }

   public void start() {
      NfcAdapter var1 = nfcAdapter;
      if (var1 != null) {
         var1.enableForegroundDispatch(this.activity, this.pendingIntent, this.intentFilters, this.NfcTechLists);
      }

   }

   public void stop() {
      NfcAdapter var1 = nfcAdapter;
      if (var1 != null) {
         var1.disableForegroundDispatch(this.activity);
      }

   }

   public boolean writeMifareUltralightPages(int param1, byte[] param2) {
      // $FF: Couldn't be decompiled
   }
}
