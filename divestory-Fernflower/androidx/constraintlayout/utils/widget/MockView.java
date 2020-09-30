package androidx.constraintlayout.utils.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import androidx.constraintlayout.widget.R;

public class MockView extends View {
   private int mDiagonalsColor = Color.argb(255, 0, 0, 0);
   private boolean mDrawDiagonals = true;
   private boolean mDrawLabel = true;
   private int mMargin = 4;
   private Paint mPaintDiagonals = new Paint();
   private Paint mPaintText = new Paint();
   private Paint mPaintTextBackground = new Paint();
   protected String mText = null;
   private int mTextBackgroundColor = Color.argb(255, 50, 50, 50);
   private Rect mTextBounds = new Rect();
   private int mTextColor = Color.argb(255, 200, 200, 200);

   public MockView(Context var1) {
      super(var1);
      this.init(var1, (AttributeSet)null);
   }

   public MockView(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.init(var1, var2);
   }

   public MockView(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      this.init(var1, var2);
   }

   private void init(Context var1, AttributeSet var2) {
      if (var2 != null) {
         TypedArray var7 = var1.obtainStyledAttributes(var2, R.styleable.MockView);
         int var3 = var7.getIndexCount();

         for(int var4 = 0; var4 < var3; ++var4) {
            int var5 = var7.getIndex(var4);
            if (var5 == R.styleable.MockView_mock_label) {
               this.mText = var7.getString(var5);
            } else if (var5 == R.styleable.MockView_mock_showDiagonals) {
               this.mDrawDiagonals = var7.getBoolean(var5, this.mDrawDiagonals);
            } else if (var5 == R.styleable.MockView_mock_diagonalsColor) {
               this.mDiagonalsColor = var7.getColor(var5, this.mDiagonalsColor);
            } else if (var5 == R.styleable.MockView_mock_labelBackgroundColor) {
               this.mTextBackgroundColor = var7.getColor(var5, this.mTextBackgroundColor);
            } else if (var5 == R.styleable.MockView_mock_labelColor) {
               this.mTextColor = var7.getColor(var5, this.mTextColor);
            } else if (var5 == R.styleable.MockView_mock_showLabel) {
               this.mDrawLabel = var7.getBoolean(var5, this.mDrawLabel);
            }
         }
      }

      if (this.mText == null) {
         try {
            this.mText = var1.getResources().getResourceEntryName(this.getId());
         } catch (Exception var6) {
         }
      }

      this.mPaintDiagonals.setColor(this.mDiagonalsColor);
      this.mPaintDiagonals.setAntiAlias(true);
      this.mPaintText.setColor(this.mTextColor);
      this.mPaintText.setAntiAlias(true);
      this.mPaintTextBackground.setColor(this.mTextBackgroundColor);
      this.mMargin = Math.round((float)this.mMargin * (this.getResources().getDisplayMetrics().xdpi / 160.0F));
   }

   public void onDraw(Canvas var1) {
      super.onDraw(var1);
      int var2 = this.getWidth();
      int var3 = this.getHeight();
      int var4 = var2;
      int var5 = var3;
      float var6;
      float var7;
      if (this.mDrawDiagonals) {
         var4 = var2 - 1;
         var5 = var3 - 1;
         var6 = (float)var4;
         var7 = (float)var5;
         var1.drawLine(0.0F, 0.0F, var6, var7, this.mPaintDiagonals);
         var1.drawLine(0.0F, var7, var6, 0.0F, this.mPaintDiagonals);
         var1.drawLine(0.0F, 0.0F, var6, 0.0F, this.mPaintDiagonals);
         var1.drawLine(var6, 0.0F, var6, var7, this.mPaintDiagonals);
         var1.drawLine(var6, var7, 0.0F, var7, this.mPaintDiagonals);
         var1.drawLine(0.0F, var7, 0.0F, 0.0F, this.mPaintDiagonals);
      }

      String var8 = this.mText;
      if (var8 != null && this.mDrawLabel) {
         this.mPaintText.getTextBounds(var8, 0, var8.length(), this.mTextBounds);
         var6 = (float)(var4 - this.mTextBounds.width()) / 2.0F;
         var7 = (float)(var5 - this.mTextBounds.height()) / 2.0F + (float)this.mTextBounds.height();
         this.mTextBounds.offset((int)var6, (int)var7);
         Rect var9 = this.mTextBounds;
         var9.set(var9.left - this.mMargin, this.mTextBounds.top - this.mMargin, this.mTextBounds.right + this.mMargin, this.mTextBounds.bottom + this.mMargin);
         var1.drawRect(this.mTextBounds, this.mPaintTextBackground);
         var1.drawText(this.mText, var6, var7, this.mPaintText);
      }

   }
}
