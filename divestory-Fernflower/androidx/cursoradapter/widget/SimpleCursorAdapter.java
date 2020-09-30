package androidx.cursoradapter.widget;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SimpleCursorAdapter extends ResourceCursorAdapter {
   private SimpleCursorAdapter.CursorToStringConverter mCursorToStringConverter;
   protected int[] mFrom;
   String[] mOriginalFrom;
   private int mStringConversionColumn = -1;
   protected int[] mTo;
   private SimpleCursorAdapter.ViewBinder mViewBinder;

   @Deprecated
   public SimpleCursorAdapter(Context var1, int var2, Cursor var3, String[] var4, int[] var5) {
      super(var1, var2, var3);
      this.mTo = var5;
      this.mOriginalFrom = var4;
      this.findColumns(var3, var4);
   }

   public SimpleCursorAdapter(Context var1, int var2, Cursor var3, String[] var4, int[] var5, int var6) {
      super(var1, var2, var3, var6);
      this.mTo = var5;
      this.mOriginalFrom = var4;
      this.findColumns(var3, var4);
   }

   private void findColumns(Cursor var1, String[] var2) {
      if (var1 != null) {
         int var3 = var2.length;
         int[] var4 = this.mFrom;
         if (var4 == null || var4.length != var3) {
            this.mFrom = new int[var3];
         }

         for(int var5 = 0; var5 < var3; ++var5) {
            this.mFrom[var5] = var1.getColumnIndexOrThrow(var2[var5]);
         }
      } else {
         this.mFrom = null;
      }

   }

   public void bindView(View var1, Context var2, Cursor var3) {
      SimpleCursorAdapter.ViewBinder var4 = this.mViewBinder;
      int[] var5 = this.mTo;
      int var6 = var5.length;
      int[] var7 = this.mFrom;

      for(int var8 = 0; var8 < var6; ++var8) {
         View var9 = var1.findViewById(var5[var8]);
         if (var9 != null) {
            boolean var10;
            if (var4 != null) {
               var10 = var4.setViewValue(var9, var3, var7[var8]);
            } else {
               var10 = false;
            }

            if (!var10) {
               String var11 = var3.getString(var7[var8]);
               String var13 = var11;
               if (var11 == null) {
                  var13 = "";
               }

               if (var9 instanceof TextView) {
                  this.setViewText((TextView)var9, var13);
               } else {
                  if (!(var9 instanceof ImageView)) {
                     StringBuilder var12 = new StringBuilder();
                     var12.append(var9.getClass().getName());
                     var12.append(" is not a ");
                     var12.append(" view that can be bounds by this SimpleCursorAdapter");
                     throw new IllegalStateException(var12.toString());
                  }

                  this.setViewImage((ImageView)var9, var13);
               }
            }
         }
      }

   }

   public void changeCursorAndColumns(Cursor var1, String[] var2, int[] var3) {
      this.mOriginalFrom = var2;
      this.mTo = var3;
      this.findColumns(var1, var2);
      super.changeCursor(var1);
   }

   public CharSequence convertToString(Cursor var1) {
      SimpleCursorAdapter.CursorToStringConverter var2 = this.mCursorToStringConverter;
      if (var2 != null) {
         return var2.convertToString(var1);
      } else {
         int var3 = this.mStringConversionColumn;
         return (CharSequence)(var3 > -1 ? var1.getString(var3) : super.convertToString(var1));
      }
   }

   public SimpleCursorAdapter.CursorToStringConverter getCursorToStringConverter() {
      return this.mCursorToStringConverter;
   }

   public int getStringConversionColumn() {
      return this.mStringConversionColumn;
   }

   public SimpleCursorAdapter.ViewBinder getViewBinder() {
      return this.mViewBinder;
   }

   public void setCursorToStringConverter(SimpleCursorAdapter.CursorToStringConverter var1) {
      this.mCursorToStringConverter = var1;
   }

   public void setStringConversionColumn(int var1) {
      this.mStringConversionColumn = var1;
   }

   public void setViewBinder(SimpleCursorAdapter.ViewBinder var1) {
      this.mViewBinder = var1;
   }

   public void setViewImage(ImageView var1, String var2) {
      try {
         var1.setImageResource(Integer.parseInt(var2));
      } catch (NumberFormatException var4) {
         var1.setImageURI(Uri.parse(var2));
      }

   }

   public void setViewText(TextView var1, String var2) {
      var1.setText(var2);
   }

   public Cursor swapCursor(Cursor var1) {
      this.findColumns(var1, this.mOriginalFrom);
      return super.swapCursor(var1);
   }

   public interface CursorToStringConverter {
      CharSequence convertToString(Cursor var1);
   }

   public interface ViewBinder {
      boolean setViewValue(View var1, Cursor var2, int var3);
   }
}
