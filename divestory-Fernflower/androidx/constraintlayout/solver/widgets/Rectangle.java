package androidx.constraintlayout.solver.widgets;

public class Rectangle {
   public int height;
   public int width;
   public int x;
   public int y;

   public boolean contains(int var1, int var2) {
      int var3 = this.x;
      boolean var4;
      if (var1 >= var3 && var1 < var3 + this.width) {
         var1 = this.y;
         if (var2 >= var1 && var2 < var1 + this.height) {
            var4 = true;
            return var4;
         }
      }

      var4 = false;
      return var4;
   }

   public int getCenterX() {
      return (this.x + this.width) / 2;
   }

   public int getCenterY() {
      return (this.y + this.height) / 2;
   }

   void grow(int var1, int var2) {
      this.x -= var1;
      this.y -= var2;
      this.width += var1 * 2;
      this.height += var2 * 2;
   }

   boolean intersects(Rectangle var1) {
      int var2 = this.x;
      int var3 = var1.x;
      boolean var4;
      if (var2 >= var3 && var2 < var3 + var1.width) {
         var2 = this.y;
         var3 = var1.y;
         if (var2 >= var3 && var2 < var3 + var1.height) {
            var4 = true;
            return var4;
         }
      }

      var4 = false;
      return var4;
   }

   public void setBounds(int var1, int var2, int var3, int var4) {
      this.x = var1;
      this.y = var2;
      this.width = var3;
      this.height = var4;
   }
}
