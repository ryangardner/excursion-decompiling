package org.apache.commons.net.telnet;

public class WindowSizeOptionHandler extends TelnetOptionHandler {
   protected static final int WINDOW_SIZE = 31;
   private int m_nHeight = 24;
   private int m_nWidth = 80;

   public WindowSizeOptionHandler(int var1, int var2) {
      super(31, false, false, false, false);
      this.m_nWidth = var1;
      this.m_nHeight = var2;
   }

   public WindowSizeOptionHandler(int var1, int var2, boolean var3, boolean var4, boolean var5, boolean var6) {
      super(31, var3, var4, var5, var6);
      this.m_nWidth = var1;
      this.m_nHeight = var2;
   }

   public int[] startSubnegotiationLocal() {
      int var1 = this.m_nWidth;
      int var2 = this.m_nHeight;
      byte var3;
      if (var1 % 256 == 255) {
         var3 = 6;
      } else {
         var3 = 5;
      }

      int var4 = var3;
      if (this.m_nWidth / 256 == 255) {
         var4 = var3 + 1;
      }

      int var8 = var4;
      if (this.m_nHeight % 256 == 255) {
         var8 = var4 + 1;
      }

      int var5 = var8;
      if (this.m_nHeight / 256 == 255) {
         var5 = var8 + 1;
      }

      int[] var6 = new int[var5];
      var6[0] = 31;
      var8 = 24;

      for(var4 = 1; var4 < var5; var8 -= 8) {
         var6[var4] = (255 << var8 & 65536 * var1 + var2) >>> var8;
         int var7 = var4;
         if (var6[var4] == 255) {
            var7 = var4 + 1;
            var6[var7] = 255;
         }

         var4 = var7 + 1;
      }

      return var6;
   }
}
