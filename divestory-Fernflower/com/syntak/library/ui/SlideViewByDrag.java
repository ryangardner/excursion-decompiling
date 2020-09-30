package com.syntak.library.ui;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import com.syntak.library.TimeOp;

public class SlideViewByDrag {
   private int THRESHOLD_CLICK_WITHIN_RADIUS;
   private int THRESHOLD_CLICK_WITH_TIME_ELAPSED;
   private float THRESHOLD_SWIPE_FRACTION_OF_VIEW;
   private float THRESHOLD_SWIPE_SPEED;
   private int animation_interval;
   public View container = null;
   boolean isAnimated;
   boolean isContainerFound = false;
   boolean isOverlapped = false;
   boolean isSettleFound;
   boolean isSizeFound_L = false;
   boolean isSizeFound_T = false;
   public View ref_L = null;
   public View ref_T = null;
   float settle_L;
   float settle_M;
   float settle_T;
   int size_L = 0;
   int size_T = 0;
   SlideViewByDrag.SLIDE_DIRECTION slide_direction;
   public View view_L = null;
   public View view_M = null;
   public View view_T = null;

   public SlideViewByDrag(View var1, View var2, View var3, View var4, View var5, View var6, SlideViewByDrag.SLIDE_DIRECTION var7) {
      this.slide_direction = SlideViewByDrag.SLIDE_DIRECTION.HORIZONTAL;
      this.THRESHOLD_CLICK_WITHIN_RADIUS = 10;
      this.THRESHOLD_CLICK_WITH_TIME_ELAPSED = 200;
      this.THRESHOLD_SWIPE_SPEED = 600.0F;
      this.THRESHOLD_SWIPE_FRACTION_OF_VIEW = 0.3F;
      this.isSettleFound = false;
      this.isAnimated = false;
      this.animation_interval = 300;
      this.isOverlapped = false;
      this.view_L = var1;
      this.view_M = var2;
      this.view_T = var3;
      if (var4 != null) {
         var1 = var4;
      }

      this.ref_L = var1;
      if (var5 != null) {
         var3 = var5;
      }

      this.ref_T = var3;
      this.container = var6;
      this.slide_direction = var7;
   }

   public SlideViewByDrag(View var1, View var2, View var3, SlideViewByDrag.SLIDE_DIRECTION var4) {
      this.slide_direction = SlideViewByDrag.SLIDE_DIRECTION.HORIZONTAL;
      this.THRESHOLD_CLICK_WITHIN_RADIUS = 10;
      this.THRESHOLD_CLICK_WITH_TIME_ELAPSED = 200;
      this.THRESHOLD_SWIPE_SPEED = 600.0F;
      this.THRESHOLD_SWIPE_FRACTION_OF_VIEW = 0.3F;
      this.isSettleFound = false;
      this.isAnimated = false;
      this.animation_interval = 300;
      this.isOverlapped = true;
      this.view_L = var1;
      this.view_M = var2;
      this.view_T = var3;
      this.ref_L = var1;
      this.ref_T = var3;
      this.container = var2;
      this.slide_direction = var4;
   }

   private void change_container() {
      MarginLayoutParams var1 = (MarginLayoutParams)this.container.getLayoutParams();
      if (this.slide_direction == SlideViewByDrag.SLIDE_DIRECTION.HORIZONTAL) {
         var1.leftMargin -= this.size_L;
         var1.rightMargin -= this.size_T;
      } else {
         var1.topMargin -= this.size_L;
         var1.bottomMargin -= this.size_T;
      }

      this.container.setLayoutParams(var1);
      float var2;
      if (this.slide_direction == SlideViewByDrag.SLIDE_DIRECTION.HORIZONTAL) {
         var2 = this.container.getX();
      } else {
         var2 = this.container.getY();
      }

      this.settle_M = var2;
   }

   private void check_parameters() {
      if (this.view_M == null) {
         Log.d("PrepareHori..SlideViews", "Middle view cannot be null!");
      }

      if (this.container == null) {
         Log.d("PrepareSlideViews", "Contianer view cannot be null!");
      }

      if (this.view_L == null && this.view_T == null) {
         Log.d("PrepareSlideViews", "Leading & Tailing views cannot be null at the same time!");
      }

      if (this.ref_L == null && this.ref_T == null) {
         Log.d("PrepareSlideViews", "Leading & Tailing reference views cannot be null at the same time!");
      }

   }

   private void move_container(float var1, boolean var2, long var3) {
      float var5;
      if (this.slide_direction == SlideViewByDrag.SLIDE_DIRECTION.HORIZONTAL) {
         var5 = this.container.getX();
      } else {
         var5 = this.container.getY();
      }

      var5 = var1 - var5;
      ObjectAnimator var6 = null;
      View var7;
      if (this.slide_direction == SlideViewByDrag.SLIDE_DIRECTION.HORIZONTAL) {
         if (var2) {
            var7 = this.container;
            var6 = ObjectAnimator.ofFloat(var7, "translationX", new float[]{var7.getTranslationX() + var5});
         } else {
            this.container.setX(var1);
         }
      } else if (var2) {
         var7 = this.container;
         var6 = ObjectAnimator.ofFloat(var7, "translationY", new float[]{var7.getTranslationY() + var5});
      } else {
         this.container.setY(var1);
      }

      if (var2) {
         var6.setDuration(var3);
         var6.setInterpolator(new TimeInterpolator() {
            public float getInterpolation(float var1) {
               return 2.0F * var1 - var1 * var1;
            }
         });
         var6.start();
      }

   }

   public void OnMidViewClicked(View var1, float var2, long var3) {
   }

   public void OnTouchDown() {
   }

   public void OnTouchUp() {
   }

   public void reset() {
      this.move_container(this.settle_M, false, 0L);
   }

   public SlideViewByDrag setAnimation(boolean var1) {
      this.isAnimated = var1;
      return this;
   }

   public SlideViewByDrag setAnimationInterval(int var1) {
      this.animation_interval = var1;
      return this;
   }

   public SlideViewByDrag setClickThresholds(int var1, int var2) {
      this.THRESHOLD_CLICK_WITHIN_RADIUS = var1;
      this.THRESHOLD_CLICK_WITH_TIME_ELAPSED = var2;
      return this;
   }

   public SlideViewByDrag setSwipeThresholds(int var1, float var2) {
      this.THRESHOLD_SWIPE_FRACTION_OF_VIEW = (float)var1;
      this.THRESHOLD_SWIPE_SPEED = var2;
      return this;
   }

   public void start() {
      this.check_parameters();
      View var1 = this.ref_L;
      ViewTreeObserver var2;
      if (var1 != null) {
         var2 = var1.getViewTreeObserver();
         if (var2.isAlive()) {
            var2.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
               public void onGlobalLayout() {
                  SlideViewByDrag.this.ref_L.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                  SlideViewByDrag var1 = SlideViewByDrag.this;
                  int var2;
                  if (var1.slide_direction == SlideViewByDrag.SLIDE_DIRECTION.HORIZONTAL) {
                     var2 = SlideViewByDrag.this.ref_L.getWidth();
                  } else {
                     var2 = SlideViewByDrag.this.ref_L.getHeight();
                  }

                  var1.size_L = var2;
                  SlideViewByDrag.this.isSizeFound_L = true;
                  if (!SlideViewByDrag.this.isOverlapped && SlideViewByDrag.this.isContainerFound && SlideViewByDrag.this.isSizeFound_T) {
                     SlideViewByDrag.this.change_container();
                  }

               }
            });
         }
      } else {
         this.isSizeFound_L = true;
      }

      var1 = this.ref_T;
      if (var1 != null) {
         var2 = var1.getViewTreeObserver();
         if (var2.isAlive()) {
            var2.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
               public void onGlobalLayout() {
                  SlideViewByDrag.this.ref_T.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                  SlideViewByDrag var1 = SlideViewByDrag.this;
                  int var2;
                  if (var1.slide_direction == SlideViewByDrag.SLIDE_DIRECTION.HORIZONTAL) {
                     var2 = SlideViewByDrag.this.ref_T.getWidth();
                  } else {
                     var2 = SlideViewByDrag.this.ref_T.getHeight();
                  }

                  var1.size_T = var2;
                  SlideViewByDrag.this.isSizeFound_T = true;
                  if (!SlideViewByDrag.this.isOverlapped && SlideViewByDrag.this.isContainerFound && SlideViewByDrag.this.isSizeFound_L) {
                     SlideViewByDrag.this.change_container();
                  }

               }
            });
         }
      } else {
         this.isSizeFound_T = true;
      }

      var2 = this.container.getViewTreeObserver();
      if (var2.isAlive()) {
         var2.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
               SlideViewByDrag.this.container.getViewTreeObserver().removeOnGlobalLayoutListener(this);
               SlideViewByDrag.this.isContainerFound = true;
               if (!SlideViewByDrag.this.isOverlapped && SlideViewByDrag.this.isSizeFound_L && SlideViewByDrag.this.isSizeFound_T) {
                  SlideViewByDrag.this.change_container();
               }

               SlideViewByDrag var1 = SlideViewByDrag.this;
               float var2;
               if (var1.slide_direction == SlideViewByDrag.SLIDE_DIRECTION.HORIZONTAL) {
                  var2 = SlideViewByDrag.this.container.getX();
               } else {
                  var2 = SlideViewByDrag.this.container.getY();
               }

               var1.settle_M = var2;
            }
         });
      }

      this.container.setOnTouchListener(new OnTouchListener() {
         float container_current;
         float container_moved;
         float contrainer_start;
         int lastAction;
         private VelocityTracker mVelocityTracker = null;
         long ms_down;
         long ms_up;
         float pointer_current;
         float pointer_moved = 0.0F;
         float pointer_start;
         float start_y;
         boolean toward_trail;
         float view_move_offset;
         float vx;
         float vy;

         public boolean onTouch(View var1, MotionEvent var2) {
            float var3;
            SlideViewByDrag var11;
            if (!SlideViewByDrag.this.isSettleFound) {
               var11 = SlideViewByDrag.this;
               if (var11.slide_direction == SlideViewByDrag.SLIDE_DIRECTION.HORIZONTAL) {
                  var3 = SlideViewByDrag.this.container.getX();
               } else {
                  var3 = SlideViewByDrag.this.container.getY();
               }

               var11.settle_M = var3;
               var11 = SlideViewByDrag.this;
               var11.settle_L = var11.settle_M + (float)SlideViewByDrag.this.size_L;
               var11 = SlideViewByDrag.this;
               var11.settle_T = var11.settle_M - (float)SlideViewByDrag.this.size_T;
               SlideViewByDrag.this.isSettleFound = true;
            }

            int var4 = var2.getActionMasked();
            boolean var5 = false;
            boolean var6 = false;
            float var9;
            if (var4 != 0) {
               if (var4 != 1) {
                  if (var4 == 2) {
                     this.mVelocityTracker.addMovement(var2);
                     this.mVelocityTracker.computeCurrentVelocity(1000);
                     this.vx = this.mVelocityTracker.getXVelocity();
                     this.vy = this.mVelocityTracker.getYVelocity();
                     if (SlideViewByDrag.this.slide_direction == SlideViewByDrag.SLIDE_DIRECTION.HORIZONTAL) {
                        var3 = var2.getX();
                     } else {
                        var3 = var2.getY();
                     }

                     this.pointer_current = var3;
                     this.pointer_moved = var3 - this.pointer_start;
                     if (SlideViewByDrag.this.slide_direction == SlideViewByDrag.SLIDE_DIRECTION.HORIZONTAL) {
                        var3 = SlideViewByDrag.this.container.getX();
                     } else {
                        var3 = SlideViewByDrag.this.container.getY();
                     }

                     this.container_current = var3;
                     this.container_moved = var3 - this.contrainer_start;
                     var3 = this.pointer_moved;
                     this.view_move_offset = var3;
                     if (var3 < 0.0F) {
                        var6 = true;
                     }

                     this.toward_trail = var6;
                     if (this.contrainer_start == SlideViewByDrag.this.settle_M) {
                        if (this.toward_trail) {
                           if (Math.abs(this.pointer_moved) >= (float)SlideViewByDrag.this.size_T) {
                              this.pointer_moved = SlideViewByDrag.this.settle_T - SlideViewByDrag.this.settle_M;
                           }
                        } else if (Math.abs(this.pointer_moved) > (float)SlideViewByDrag.this.size_L) {
                           this.pointer_moved = SlideViewByDrag.this.settle_L - SlideViewByDrag.this.settle_M;
                        }
                     } else if (this.contrainer_start == SlideViewByDrag.this.settle_L) {
                        if (this.toward_trail) {
                           if (Math.abs(this.pointer_moved) > (float)SlideViewByDrag.this.size_L) {
                              this.pointer_moved = SlideViewByDrag.this.settle_M - SlideViewByDrag.this.settle_L;
                           }
                        } else {
                           this.pointer_moved = 0.0F;
                        }
                     } else if (this.toward_trail) {
                        this.pointer_moved = 0.0F;
                     } else if (Math.abs(this.pointer_moved) > (float)SlideViewByDrag.this.size_T) {
                        this.pointer_moved = SlideViewByDrag.this.settle_M - SlideViewByDrag.this.settle_T;
                     }

                     if (SlideViewByDrag.this.slide_direction == SlideViewByDrag.SLIDE_DIRECTION.HORIZONTAL) {
                        SlideViewByDrag.this.container.setX(this.contrainer_start + this.pointer_moved);
                     } else {
                        SlideViewByDrag.this.container.setY(this.contrainer_start + this.pointer_moved);
                     }

                     this.lastAction = 2;
                     return true;
                  }

                  if (var4 != 3) {
                     return false;
                  }
               } else {
                  SlideViewByDrag.this.OnTouchUp();
               }

               if (SlideViewByDrag.this.slide_direction == SlideViewByDrag.SLIDE_DIRECTION.HORIZONTAL) {
                  var3 = SlideViewByDrag.this.container.getX();
               } else {
                  var3 = SlideViewByDrag.this.container.getY();
               }

               this.container_current = var3;
               this.container_moved = var3 - this.contrainer_start;
               long var7 = TimeOp.getNow();
               this.ms_up = var7;
               var7 -= this.ms_down;
               if (Math.abs(this.container_moved) < (float)SlideViewByDrag.this.THRESHOLD_CLICK_WITHIN_RADIUS && var7 < (long)SlideViewByDrag.this.THRESHOLD_CLICK_WITH_TIME_ELAPSED) {
                  var11 = SlideViewByDrag.this;
                  var11.OnMidViewClicked(var11.view_M, this.container_moved, var7);
               }

               if (SlideViewByDrag.this.slide_direction == SlideViewByDrag.SLIDE_DIRECTION.HORIZONTAL) {
                  var3 = this.vx;
               } else {
                  var3 = this.vy;
               }

               if (SlideViewByDrag.this.slide_direction == SlideViewByDrag.SLIDE_DIRECTION.HORIZONTAL) {
                  var9 = var2.getX();
               } else {
                  var9 = var2.getY();
               }

               this.pointer_current = var9;
               var9 -= this.pointer_start;
               this.pointer_moved = var9;
               var6 = var5;
               if (var9 < 0.0F) {
                  var6 = true;
               }

               this.toward_trail = var6;
               if (this.contrainer_start == SlideViewByDrag.this.settle_M) {
                  if (this.toward_trail) {
                     if (Math.abs(this.pointer_moved) <= (float)SlideViewByDrag.this.size_T * SlideViewByDrag.this.THRESHOLD_SWIPE_FRACTION_OF_VIEW && Math.abs(var3) <= SlideViewByDrag.this.THRESHOLD_SWIPE_SPEED) {
                        var3 = SlideViewByDrag.this.settle_M;
                     } else {
                        var3 = SlideViewByDrag.this.settle_T;
                     }
                  } else if (Math.abs(this.pointer_moved) <= (float)SlideViewByDrag.this.size_L * SlideViewByDrag.this.THRESHOLD_SWIPE_FRACTION_OF_VIEW && Math.abs(var3) <= SlideViewByDrag.this.THRESHOLD_SWIPE_SPEED) {
                     var3 = SlideViewByDrag.this.settle_M;
                  } else {
                     var3 = SlideViewByDrag.this.settle_L;
                  }
               } else if (this.contrainer_start == SlideViewByDrag.this.settle_L) {
                  if (this.toward_trail) {
                     if (Math.abs(this.pointer_moved) <= (float)SlideViewByDrag.this.size_L * SlideViewByDrag.this.THRESHOLD_SWIPE_FRACTION_OF_VIEW && Math.abs(var3) <= SlideViewByDrag.this.THRESHOLD_SWIPE_SPEED) {
                        var3 = SlideViewByDrag.this.settle_L;
                     } else {
                        var3 = SlideViewByDrag.this.settle_M;
                     }
                  } else {
                     var3 = SlideViewByDrag.this.settle_L;
                  }
               } else if (this.toward_trail) {
                  var3 = SlideViewByDrag.this.settle_T;
               } else if (Math.abs(this.pointer_moved) <= (float)SlideViewByDrag.this.size_T * SlideViewByDrag.this.THRESHOLD_SWIPE_FRACTION_OF_VIEW && Math.abs(var3) <= SlideViewByDrag.this.THRESHOLD_SWIPE_SPEED) {
                  var3 = SlideViewByDrag.this.settle_T;
               } else {
                  var3 = SlideViewByDrag.this.settle_M;
               }

               var11 = SlideViewByDrag.this;
               var11.move_container(var3, var11.isAnimated, (long)SlideViewByDrag.this.animation_interval);
            } else {
               SlideViewByDrag.this.OnTouchDown();
               this.ms_down = TimeOp.getNow();
               this.lastAction = 0;
               if (SlideViewByDrag.this.slide_direction == SlideViewByDrag.SLIDE_DIRECTION.HORIZONTAL) {
                  var3 = var2.getX();
               } else {
                  var3 = var2.getY();
               }

               this.pointer_start = var3;
               this.pointer_moved = 0.0F;
               VelocityTracker var13 = this.mVelocityTracker;
               if (var13 == null) {
                  this.mVelocityTracker = VelocityTracker.obtain();
               } else {
                  var13.clear();
               }

               this.mVelocityTracker.addMovement(var2);
               if (SlideViewByDrag.this.slide_direction == SlideViewByDrag.SLIDE_DIRECTION.HORIZONTAL) {
                  var3 = SlideViewByDrag.this.container.getX();
               } else {
                  var3 = SlideViewByDrag.this.container.getY();
               }

               this.container_current = var3;
               float[] var14 = new float[]{Math.abs(var3 - SlideViewByDrag.this.settle_L), Math.abs(this.container_current - SlideViewByDrag.this.settle_M), Math.abs(this.container_current - SlideViewByDrag.this.settle_T)};
               float[] var12 = new float[]{SlideViewByDrag.this.settle_L, SlideViewByDrag.this.settle_M, SlideViewByDrag.this.settle_T};
               float var10 = var14[0];
               var9 = var12[0];

               for(var4 = 1; var4 < 3; var10 = var3) {
                  var3 = var10;
                  if (var14[var4] < var10) {
                     var3 = var14[var4];
                     var9 = var12[var4];
                  }

                  ++var4;
               }

               this.contrainer_start = var9;
            }

            return true;
         }
      });
   }

   public void stop() {
   }

   public static enum SLIDE_DIRECTION {
      HORIZONTAL,
      VERTICAL;

      static {
         SlideViewByDrag.SLIDE_DIRECTION var0 = new SlideViewByDrag.SLIDE_DIRECTION("VERTICAL", 1);
         VERTICAL = var0;
      }
   }
}
