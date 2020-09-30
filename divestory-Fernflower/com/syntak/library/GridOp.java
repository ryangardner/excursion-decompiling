package com.syntak.library;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore.Images.Thumbnails;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GridOp {
   public static Drawable default_icon;
   final int CELL_WIDTH_BY_COLUMNS = 0;
   final int CELL_WIDTH_BY_SET = 1;
   float alpha_original;
   float alpha_selected = 0.8F;
   private ArrayList<AsyncTask> asyncTasks = null;
   int cell_background_color = -16777216;
   private int cell_frame_width = 0;
   final int cell_gap_factor = 15;
   private int cell_height = 100;
   private int cell_label_background = 0;
   private int cell_label_height = 0;
   private int cell_label_height_max = 0;
   private int cell_label_height_min = 0;
   private int cell_label_text_color = 16777215;
   private int cell_label_text_size = 20;
   private int cell_spacing_H = 0;
   private int cell_spacing_V = 0;
   private int cell_width = 100;
   private int cell_width_decider = 0;
   private ContentResolver contentResolver = null;
   private Context context;
   private int display_area_height_px = 0;
   private int extra_spacing_V = 0;
   private boolean flag_cell_async_loading = true;
   boolean flag_multiple_selection = false;
   private boolean flag_set_cell_label_background = false;
   private boolean flag_set_cell_label_text_color = false;
   private boolean flag_set_cell_label_text_size = false;
   private boolean flag_set_icon_label_background = false;
   private boolean flag_set_icon_label_text_color = false;
   private boolean flag_set_icon_label_text_size = false;
   private boolean flag_size_calculated = false;
   private GridOp.GridAdapter gridAdapter;
   private ArrayList<GridOp.GridCell> gridCells = null;
   private GridView gridView;
   private int grid_view_width = 0;
   private int icon_height = 100;
   private int icon_label_background = 2004318071;
   private int icon_label_text_color = 16777215;
   private int icon_label_text_size = 20;
   private float icon_percentage = 1.0F;
   private int icon_width = 100;
   private int min_cell_spacing_H = 0;
   private int number_of_columns = 0;
   int position_highlighted = -1;
   ArrayList<String> preSelectedPaths = null;
   private ScrollView scrollView;
   private int showed_number;
   private int size_checkbox = 0;
   private int size_delete_button = 0;

   public GridOp(Context var1, GridView var2, ArrayList<GridOp.GridCell> var3) {
      this.context = var1;
      this.gridView = var2;
      this.contentResolver = var1.getContentResolver();
      if (var3 == null) {
         this.gridCells = new ArrayList();
      } else {
         this.gridCells = var3;
      }

      this.gridCells.size();
      this.gridAdapter = new GridOp.GridAdapter();
      default_icon = this.context.getResources().getDrawable(R.drawable.icon);
      this.set_gridview_Dpad_listener();
      this.asyncTasks = new ArrayList();
   }

   private void calcuate_size() {
      this.number_of_columns = this.gridView.getNumColumns();
      this.cell_width = this.gridView.getColumnWidth();
      int var1 = this.gridView.getHorizontalSpacing();
      this.cell_spacing_H = var1;
      this.gridView.setVerticalSpacing(var1 + this.extra_spacing_V);
      this.cell_spacing_V = this.gridView.getVerticalSpacing();
      float var2 = this.icon_percentage;
      int var3 = this.cell_width;
      var1 = (int)(var2 * (float)var3);
      this.icon_width = var1;
      this.icon_height = var1;
      this.cell_height = var3;
   }

   private boolean change_grid_para() {
      if (this.number_of_columns <= 0) {
         this.calcuate_size();
      }

      if (this.number_of_columns > 0) {
         this.set_grid_height();
         return true;
      } else {
         return false;
      }
   }

   private void check_grid_scroll(int var1, int var2) {
      int var3 = this.getCellHeightPx();
      if (var1 >= var3) {
         int var4 = this.getRowsNumber(var2 + 1) * var3;
         var2 = this.scrollView.getScrollY();
         if (var4 >= var1 + var2 - var3) {
            this.scrollView.scrollTo(0, var4 - var1 + var3);
         } else if (var4 <= var2 + var3 + var3 / 2) {
            this.scrollView.scrollTo(0, var4 - var3);
         }

      }
   }

   private void pre_draw_grid() {
      this.gridView.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
         public boolean onPreDraw() {
            if (GridOp.this.change_grid_para()) {
               GridOp.this.gridView.getViewTreeObserver().removeOnPreDrawListener(this);
               GridOp.this.gridView.setAdapter(GridOp.this.gridAdapter);
            }

            return true;
         }
      });
   }

   public static Bitmap prepare_image(Context var0, GridOp.GridCell var1, int var2, int var3) {
      int var4 = var1.source;
      String var5 = null;
      Object var6 = null;
      Bitmap var7 = null;
      Bitmap var8;
      String var9;
      switch(var4) {
      case 0:
         var8 = ((BitmapDrawable)var1.drawable).getBitmap();
         break;
      case 1:
         Uri var10 = var1.uri;
         if (FileOp.checkUriExist(var10)) {
            var8 = MediaOp.getSampledBitmapFromPath(FileOp.getPathFromURI(var0, var10), var2, var3);
         } else {
            var8 = ((BitmapDrawable)default_icon).getBitmap();
         }
         break;
      case 2:
         var9 = var1.path;
         var7 = var5;
         if (FileOp.checkFileExist(var9)) {
            var7 = MediaOp.getThumbnailFromPath(var0, var9, var2, var3, 3);
         }

         var8 = var7;
         if (var7 == null) {
            var8 = ((BitmapDrawable)default_icon).getBitmap();
         }
         break;
      case 3:
         var8 = ((BitmapDrawable)default_icon).getBitmap();
         break;
      case 4:
         var8 = Thumbnails.getThumbnail(var0.getContentResolver(), var1.image_ID, 3, (Options)null);
         break;
      case 5:
         var5 = var1.icon_path;
         String var11 = var5;
         if (var5 == null) {
            var11 = var1.path;
         }

         if (FileOp.checkFileExist(var11)) {
            var8 = MediaOp.getThumbnailFromPath(var0, var11, var2, var3, 3);
         } else {
            var8 = ((BitmapDrawable)default_icon).getBitmap();
         }
         break;
      case 6:
         if (var1.bitmap != null) {
            var8 = var1.bitmap;
         } else {
            var8 = MediaOp.getThumbnailFromPath(var0, var1.path, var2, var3, 3);
         }
         break;
      case 7:
         var9 = var1.icon_path;
         if (FileOp.checkFileExist(var9)) {
            var7 = MediaOp.getThumbnailFromPath(var0, var9, var2, var3, 3);
         }

         var8 = var7;
         if (var7 == null) {
            var8 = ((BitmapDrawable)var0.getResources().getDrawable(R.mipmap.folder_yellow)).getBitmap();
         }
         break;
      case 8:
         var8 = ((BitmapDrawable)var1.drawable).getBitmap();
         break;
      default:
         var8 = (Bitmap)var6;
      }

      return MediaOp.getBitmapCenterCrop(var8);
   }

   private void set_grid_height() {
      this.cell_height = this.icon_height + (this.cell_label_height_max + this.cell_label_height_min) / 2;
      LayoutParams var1 = this.gridView.getLayoutParams();
      var1.height = this.getRowsNumber(this.gridView.getCount()) * (this.cell_height + this.cell_spacing_V);
      if (this.cell_spacing_V == 0) {
         var1.height += 10;
      }

      this.gridView.setLayoutParams(var1);
   }

   private boolean set_gridview_Dpad_listener() {
      this.gridView.setOnKeyListener(new OnKeyListener() {
         public boolean onKey(View var1, int var2, KeyEvent var3) {
            int var4 = GridOp.this.gridView.getSelectedItemPosition();
            GridOp var5;
            if (var2 == 23) {
               var2 = var4;
               if (var4 == -1) {
                  if (GridOp.this.gridView.getCount() <= 0) {
                     return false;
                  }

                  GridOp.this.gridView.setSelection(0);
                  var2 = 0;
               }

               var5 = GridOp.this;
               var5.onIconClicked(var2, (GridOp.GridCell)var5.gridCells.get(var2));
            } else {
               switch(var2) {
               case 19:
               case 20:
                  var5 = GridOp.this;
                  var5.check_grid_scroll(var5.display_area_height_px, var4);
               case 21:
               case 22:
                  var5 = GridOp.this;
                  var5.onCellFocused(var4, (GridOp.GridCell)var5.gridCells.get(var4));
               }
            }

            return false;
         }
      });
      return true;
   }

   public boolean appendCell(int var1, GridOp.GridCell var2) {
      boolean var4;
      if (var1 <= 0) {
         ArrayList var3 = this.gridCells;
         var3.add(var3.size() + var1, var2);
         this.onDataChanged();
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   public boolean appendCell(GridOp.GridCell var1) {
      this.gridCells.add(var1);
      this.onDataChanged();
      return true;
   }

   public void beforeDeleted(GridOp.GridCell var1) {
   }

   public void clearCells() {
      Iterator var1 = this.asyncTasks.iterator();

      while(var1.hasNext()) {
         ((AsyncTask)var1.next()).cancel(true);
      }

      this.asyncTasks.clear();
      this.asyncTasks.trimToSize();

      GridOp.GridCell var2;
      for(var1 = this.gridCells.iterator(); var1.hasNext(); var2 = (GridOp.GridCell)var1.next()) {
      }

      this.gridCells.clear();
      this.gridCells.trimToSize();
      this.gridView.setAdapter(this.gridAdapter);
      this.number_of_columns = 0;
      this.position_highlighted = -1;
      this.notifyDataSetChanged();
   }

   public void fillGridCellsFromAppsList(Context var1, List<ApplicationInfo> var2) {
      PackageManager var7 = var1.getPackageManager();
      Iterator var8 = var2.iterator();

      while(var8.hasNext()) {
         ApplicationInfo var3 = (ApplicationInfo)var8.next();
         String var4 = var3.packageName;
         if (var7.getLaunchIntentForPackage(var4) != null) {
            Drawable var5 = var7.getApplicationIcon(var3);
            String var9 = var7.getApplicationLabel(var3).toString();
            GridOp.GridCell var6 = new GridOp.GridCell();
            var6.source = 8;
            var6.deletable = false;
            var6.checkable = false;
            var6.cell_label = var9;
            var6.drawable = var5;
            var6.obj = var4;
            var6.viewGroup = null;
            this.appendCell(var6);
         }
      }

      this.notifyDataSetChanged();
   }

   public boolean fillGridCellsFromPath(String var1) {
      File[] var6 = (new File(var1)).listFiles();
      if (var6 == null) {
         return false;
      } else {
         int var2 = var6.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            File var4 = var6[var3];
            GridOp.GridCell var5 = new GridOp.GridCell();
            var5.path = var4.getPath();
            var5.media_name = var4.getName();
            var5.deletable = false;
            var5.checkable = false;
            var5.viewGroup = null;
            if (var4.isDirectory()) {
               var5.icon_path = FileOp.getAnyFilePathInFolder(var4.getPath());
               var5.source = 7;
               var5.icon_label = var4.getName();
            } else {
               var5.source = 2;
               if (MediaOp.getMediaTypeFromPath(var5.path) != 1) {
                  var5.icon_label = var4.getName();
               }
            }

            this.appendCell(var5);
         }

         this.notifyDataSetChanged();
         return true;
      }
   }

   public void fillGridCellsFromPathArray(String[] var1, boolean var2, boolean var3, int var4) {
      if (var1.length > 0) {
         for(int var5 = 0; var5 < var1.length; ++var5) {
            String var6 = var1[var5];
            Bitmap var7 = MediaOp.getThumbnailFromPath(this.context, var6, this.icon_width, this.icon_height, 3);
            GridOp.GridCell var8 = new GridOp.GridCell();
            var8.source = 6;
            var8.path = var6;
            var8.bitmap = var7;
            var8.checkable = var3;
            var8.deletable = var2;
            var8.viewGroup = null;
            this.appendCell(var4, var8);
         }

         this.notifyDataSetChanged();
      }

   }

   public void fillGridCellsFromStringArray(String[] var1, String var2, String var3, boolean var4, boolean var5, int var6) {
      int var7 = var1.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         String var9 = var1[var8];
         GridOp.GridCell var10 = new GridOp.GridCell();
         StringBuilder var11 = new StringBuilder();
         var11.append(var2);
         var11.append(var9);
         var10.path = var11.toString();
         var11 = new StringBuilder();
         var11.append(var3);
         var11.append(var9);
         var10.icon_path = var11.toString();
         var10.source = 5;
         var10.media_name = var9;
         var10.deletable = var4;
         var10.checkable = var5;
         var10.viewGroup = null;
         this.appendCell(var6, var10);
      }

      this.notifyDataSetChanged();
   }

   public void fillGridCellsFromUris(Context var1, ArrayList<Uri> var2, boolean var3, boolean var4, int var5) {
      if (var2.size() > 0) {
         for(int var6 = 0; var6 < var2.size(); ++var6) {
            Uri var7 = (Uri)var2.get(var6);
            String var8 = FileOp.getPathFromURI(var1, var7);
            FileOp.getFilenameFromPath(var8);
            Bitmap var9 = MediaOp.getThumbnailFromPath(var1, var8, this.icon_width, this.icon_height, 3);
            GridOp.GridCell var10 = new GridOp.GridCell();
            var10.source = 6;
            var10.uri = var7;
            var10.path = var8;
            var10.bitmap = var9;
            var10.checkable = var4;
            var10.deletable = var3;
            var10.viewGroup = null;
            this.appendCell(var5, var10);
         }

         this.notifyDataSetChanged();
      }

   }

   public void fillGridCellsWithFolderInfo(ArrayList<MediaOp.MediaInfo> var1, int var2) {
      if (this.gridCells != null && var1 != null) {
         Iterator var3 = var1.iterator();

         while(true) {
            MediaOp.MediaInfo var4;
            do {
               do {
                  if (!var3.hasNext()) {
                     this.notifyDataSetChanged();
                     return;
                  }

                  var4 = (MediaOp.MediaInfo)var3.next();
               } while(var4.type != 0);
            } while(var2 != 4 && !var4.mime_type.contains("image"));

            GridOp.GridCell var5 = new GridOp.GridCell();
            var5.source = 4;
            var5.bitmap = var4.icon;
            if (var4.mime_type.contains("audio")) {
               var5.source = 6;
               var5.bitmap = BitmapFactory.decodeResource(this.context.getResources(), R.mipmap.ic_audio);
            }

            if (var4.mime_type.contains("video")) {
               var5.source = 6;
               var5.bitmap = MediaOp.getThumbnailFromVideo(var4.media_path);
            }

            var5.path = var4.media_path;
            var5.image_ID = (long)var4.id;
            var5.tag = var4.folder_path;
            var5.icon_label = var4.folder_name;
            var5.isChecked = false;
            var5.viewGroup = null;
            this.appendCell(var5);
         }
      }
   }

   public void fillGridCellsWithImageInFolder(ArrayList<MediaOp.MediaInfo> var1, String var2) {
      if (this.gridCells != null && var1 != null) {
         Iterator var5 = var1.iterator();

         while(var5.hasNext()) {
            MediaOp.MediaInfo var3 = (MediaOp.MediaInfo)var5.next();
            if (var3.folder_path.equals(var2) && var3.type == 1) {
               GridOp.GridCell var4 = new GridOp.GridCell();
               var4.source = 4;
               var4.bitmap = var3.icon;
               var4.path = var3.media_path;
               var4.image_ID = (long)var3.id;
               var4.icon_label = var3.media_name;
               var4.isChecked = false;
               var4.viewGroup = null;
               this.appendCell(var4);
            }
         }

         this.notifyDataSetChanged();
      }

   }

   public void fillGridCellsWithMediaInFolder(ArrayList<MediaOp.MediaInfo> var1, String var2) {
      if (this.gridCells != null && var1 != null) {
         Iterator var3 = var1.iterator();

         while(true) {
            MediaOp.MediaInfo var6;
            do {
               do {
                  if (!var3.hasNext()) {
                     this.notifyDataSetChanged();
                     return;
                  }

                  var6 = (MediaOp.MediaInfo)var3.next();
               } while(!var6.folder_path.equals(var2));
            } while(var6.type != 1 && var6.type != 2 && var6.type != 3);

            GridOp.GridCell var4 = new GridOp.GridCell();
            int var5 = var6.type;
            if (var5 != 2 && var5 != 3) {
               var4.source = 4;
            } else {
               var4.source = 2;
            }

            var4.bitmap = var6.icon;
            var4.path = var6.media_path;
            var4.image_ID = (long)var6.id;
            var4.icon_label = var6.media_name;
            var4.isChecked = false;
            var4.viewGroup = null;
            this.appendCell(var4);
         }
      }
   }

   public GridOp.GridCell getCell(int var1) {
      return (GridOp.GridCell)this.gridCells.get(var1);
   }

   public int getCellHeightPx() {
      return this.cell_height;
   }

   public int getCellsSize() {
      return this.gridCells.size();
   }

   public int getColumnNumbers() {
      return this.number_of_columns;
   }

   public ArrayList<GridOp.GridCell> getGridCells() {
      return this.gridCells;
   }

   public int getRowsNumber(int var1) {
      int var2 = this.number_of_columns;
      int var3 = var1 / var2;
      int var4 = var3;
      if (var2 * var3 < var1) {
         var4 = var3 + 1;
      }

      return var4;
   }

   public String getStringWithGridCellInfo(int var1, String var2) {
      StringBuilder var3 = new StringBuilder();

      for(int var4 = 0; var4 < this.gridCells.size(); ++var4) {
         String var5 = null;
         if (var1 != 0) {
            if (var1 != 1) {
               if (var1 == 2 && ((GridOp.GridCell)this.gridCells.get(var4)).path != null) {
                  var5 = ((GridOp.GridCell)this.gridCells.get(var4)).path;
               }
            } else if (((GridOp.GridCell)this.gridCells.get(var4)).tag != null) {
               var5 = ((GridOp.GridCell)this.gridCells.get(var4)).tag.toString();
            }
         } else if (((GridOp.GridCell)this.gridCells.get(var4)).media_name != null) {
            var5 = ((GridOp.GridCell)this.gridCells.get(var4)).media_name;
         }

         if (var5 != null) {
            if (var4 > 0) {
               var3.append(var2);
            }

            var3.append(var5);
         }
      }

      return var3.toString();
   }

   public void incrementCellsShowedNumber(int var1) {
      var1 += this.showed_number;
      this.showed_number = var1;
      if (var1 > this.gridCells.size()) {
         this.showed_number = this.gridCells.size();
      }

   }

   public void notifyDataSetChanged() {
      this.gridAdapter.notifyDataSetChanged();
   }

   public void onCellFocused(int var1, GridOp.GridCell var2) {
   }

   public void onCheckedChanged(int var1) {
   }

   public void onDataChanged() {
   }

   public void onIconClicked(int var1, GridOp.GridCell var2) {
   }

   public void onIconLongClicked(int var1, GridOp.GridCell var2) {
   }

   public void resetPositionHighlight() {
      this.position_highlighted = -1;
   }

   public void setCellAsyncLoading(boolean var1) {
      this.flag_cell_async_loading = var1;
   }

   public void setCellBackgroundColor(int var1) {
      this.cell_background_color = var1;
   }

   public void setCellFrameWidth(int var1) {
      if (var1 > 0) {
         this.cell_frame_width = var1;
      }

   }

   public void setCellLabelBackground(int var1) {
      this.flag_set_cell_label_background = true;
      this.cell_label_background = var1;
   }

   public void setCellLabelTextColor(int var1) {
      this.flag_set_cell_label_text_color = true;
      this.cell_label_text_color = var1;
   }

   public void setCellLabelTextSize(int var1) {
      this.flag_set_cell_label_text_size = true;
      this.cell_label_text_size = MediaOp.DpToPx(this.context, var1);
   }

   public void setCellWidthDp(int var1, float var2, int var3, int var4) {
      this.cell_width_decider = 1;
      this.cell_width = MediaOp.DpToPx(this.context, var1);
      this.min_cell_spacing_H = MediaOp.DpToPx(this.context, var3);
      if (var2 > 0.0F) {
         this.icon_percentage = var2;
      }

      this.extra_spacing_V = var4;
      this.gridView.setColumnWidth(this.cell_width);
      this.gridView.setHorizontalSpacing(this.min_cell_spacing_H);
      this.pre_draw_grid();
   }

   public void setCellsShowedNumber(int var1) {
      this.showed_number = var1;
   }

   public void setCheckboxSize(int var1) {
      this.size_checkbox = var1;
   }

   public void setDefaultIcon(Drawable var1) {
      default_icon = var1;
   }

   public void setDeleteButton(int var1) {
      this.size_delete_button = var1;
   }

   public void setIconLabelBackground(int var1) {
      this.flag_set_icon_label_background = true;
      this.icon_label_background = var1;
   }

   public void setIconLabelTextColor(int var1) {
      this.flag_set_icon_label_text_color = true;
      this.icon_label_text_color = var1;
   }

   public void setIconLabelTextSize(int var1) {
      this.flag_set_icon_label_text_size = true;
      this.icon_label_text_size = MediaOp.DpToPx(this.context, var1);
   }

   public void setMultipleSelection(boolean var1) {
      this.flag_multiple_selection = var1;
   }

   public void setNumberOfColumns(int var1, float var2, int var3, int var4) {
      this.cell_width_decider = 0;
      if (var3 > 0) {
         this.cell_spacing_H = MediaOp.DpToPx(this.context, var3);
      }

      if (var2 > 0.0F) {
         this.icon_percentage = var2;
      }

      this.extra_spacing_V = var4;
      this.gridView.setNumColumns(var1);
      this.gridView.setHorizontalSpacing(this.cell_spacing_H);
      this.pre_draw_grid();
   }

   public void setPositionFocused(int var1) {
      if (var1 >= 0) {
         this.gridView.setSelection(var1);
      }

   }

   public void setPositionHighlight(int var1) {
      if (var1 >= 0) {
         int var2 = this.position_highlighted;
         if (var1 != var2) {
            if (var2 >= 0) {
               ((GridOp.GridCell)this.gridCells.get(var2)).viewGroup = null;
            }

            ((GridOp.GridCell)this.gridCells.get(var1)).viewGroup = null;
            this.position_highlighted = var1;
            this.check_grid_scroll(this.display_area_height_px, var1);
            this.notifyDataSetChanged();
         }
      }

   }

   public void setPreSelectedPaths(String var1, String var2) {
      if (var1 != null) {
         String[] var5 = var1.split(var2);
         this.preSelectedPaths = new ArrayList();
         int var3 = var5.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            var1 = var5[var4];
            this.preSelectedPaths.add(var1);
         }
      }

   }

   public void setScrollView(ScrollView var1, int var2) {
      this.scrollView = var1;
      this.display_area_height_px = var2;
   }

   public static class AsyncShowThumbnail extends AsyncTask<Void, Void, Bitmap> {
      public Context context;
      public GridOp.GridCell gc;
      public int height;
      public Bitmap icon = null;
      public ImageView iv;
      public String path;
      public int width;

      public AsyncShowThumbnail(Context var1, ImageView var2, GridOp.GridCell var3, int var4, int var5) {
         this.context = var1;
         this.iv = var2;
         this.gc = var3;
         this.width = var4;
         this.height = var5;
      }

      protected Bitmap doInBackground(Void... var1) {
         if (this.isCancelled()) {
            return null;
         } else {
            Bitmap var2 = GridOp.prepare_image(this.context, this.gc, this.width, this.height);
            this.icon = var2;
            return var2;
         }
      }

      protected void onPostExecute(Bitmap var1) {
         if (var1 != null || !this.isCancelled()) {
            this.iv.setImageBitmap(var1);
         }

      }
   }

   public class GridAdapter extends BaseAdapter {
      LayoutInflater inflater;

      public GridAdapter() {
         this.inflater = (LayoutInflater)GridOp.this.context.getSystemService("layout_inflater");
      }

      private void delete(View var1) {
         GridOp.GridCell var2 = (GridOp.GridCell)var1.getTag();
         GridOp.this.beforeDeleted(var2);
         GridOp.this.gridCells.remove(var2);
         this.notifyDataSetChanged();
         GridOp.this.set_grid_height();
         GridOp.this.onDataChanged();
      }

      private void logChecked(View var1) {
         GridOp.GridCell var2 = (GridOp.GridCell)var1.getTag();
         var2.isChecked = ((CheckBox)var1).isChecked();
         if (var2.isChecked) {
            GridOp.this.onCheckedChanged(1);
         } else {
            GridOp.this.onCheckedChanged(-1);
         }

      }

      private void show_cell_view(GridOp.GridCell var1, final int var2) {
         ImageView var3 = (ImageView)var1.viewGroup.findViewById(R.id.icon);
         ImageView var4 = (ImageView)var1.viewGroup.findViewById(R.id.deleteButton);
         CheckBox var5 = (CheckBox)var1.viewGroup.findViewById(R.id.checkBox);
         TextView var6 = (TextView)var1.viewGroup.findViewById(R.id.icon_label);
         final TextView var7 = (TextView)var1.viewGroup.findViewById(R.id.cell_label);
         RelativeLayout var8 = (RelativeLayout)var1.viewGroup.findViewById(R.id.cell);
         var3.setBackgroundColor(GridOp.this.cell_background_color);
         var8.setVisibility(0);
         var8.setPadding(GridOp.this.cell_frame_width, GridOp.this.cell_frame_width, GridOp.this.cell_frame_width, GridOp.this.cell_frame_width);
         if (var2 == GridOp.this.position_highlighted) {
            var8.setBackgroundColor(-256);
         } else {
            var8.setBackgroundColor(0);
         }

         if (GridOp.this.number_of_columns <= 0 && GridOp.this.cell_width_decider == 0) {
            GridOp.this.calcuate_size();
         }

         MediaOp.setViewSizePx(var3, GridOp.this.icon_width, GridOp.this.icon_height);
         if (GridOp.this.flag_cell_async_loading && var1.bitmap == null) {
            GridOp.this.asyncTasks.add((new GridOp.AsyncShowThumbnail(GridOp.this.context, var3, var1, GridOp.this.cell_width, GridOp.this.cell_height)).execute(new Void[0]));
         } else {
            Bitmap var9;
            if (var1.bitmap == null) {
               var9 = GridOp.prepare_image(GridOp.this.context, (GridOp.GridCell)GridOp.this.gridCells.get(var2), GridOp.this.cell_width, GridOp.this.cell_height);
            } else {
               var9 = var1.bitmap;
            }

            if (var9 != null) {
               var3.setImageBitmap(var9);
            } else if (var1.drawable != null) {
               var3.setImageDrawable(var1.drawable);
            }
         }

         var3.setTag(var1);
         if (var1.clickable) {
            var3.setOnClickListener(new OnClickListener() {
               public void onClick(View var1) {
                  GridOp.GridCell var2x = (GridOp.GridCell)var1.getTag();
                  GridOp.this.onIconClicked(var2, var2x);
               }
            });
            var3.setOnLongClickListener(new OnLongClickListener() {
               public boolean onLongClick(View var1) {
                  GridOp.GridCell var2x = (GridOp.GridCell)var1.getTag();
                  GridOp.this.onIconLongClicked(var2, var2x);
                  return false;
               }
            });
         }

         if (var1.icon_label != null) {
            var6.setTag(var1);
            var6.setText(var1.icon_label);
            var6.setVisibility(0);
            var6.setWidth(GridOp.this.cell_width);
            if (GridOp.this.flag_set_icon_label_text_size) {
               var6.setTextSize((float)GridOp.this.icon_label_text_size);
            }

            if (GridOp.this.flag_set_icon_label_text_color) {
               var6.setTextColor(GridOp.this.icon_label_text_color);
            }

            if (GridOp.this.flag_set_icon_label_background) {
               var6.setBackgroundColor(GridOp.this.icon_label_background);
            }
         } else {
            var6.setVisibility(8);
         }

         if (var1.cell_label != null) {
            GridOp.this.cell_label_height = var7.getHeight();
            var7.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
               public boolean onPreDraw() {
                  int var1 = var7.getHeight();
                  if (GridOp.this.cell_label_height_min == 0 || var1 < GridOp.this.cell_label_height_min) {
                     GridOp.this.cell_label_height_min = var1;
                  }

                  if (var1 > GridOp.this.cell_label_height_max) {
                     GridOp.this.cell_label_height_max = var1;
                  }

                  GridOp.this.set_grid_height();
                  var7.getViewTreeObserver().removeOnPreDrawListener(this);
                  return true;
               }
            });
            var7.setTag(var1);
            var7.setText(var1.cell_label);
            var7.setVisibility(0);
            var7.setWidth(GridOp.this.cell_width);
            if (GridOp.this.flag_set_cell_label_text_size) {
               var7.setTextSize((float)GridOp.this.cell_label_text_size);
            }

            if (GridOp.this.flag_set_cell_label_text_color) {
               var7.setTextColor(GridOp.this.cell_label_text_color);
            }

            if (GridOp.this.flag_set_cell_label_background) {
               var7.setBackgroundColor(GridOp.this.cell_label_background);
            }
         } else {
            var7.setVisibility(8);
         }

         if (var1.deletable) {
            if (GridOp.this.size_delete_button > 0) {
               MediaOp.setViewSizePx(var4, GridOp.this.size_delete_button, GridOp.this.size_delete_button);
            } else {
               MediaOp.setViewSizePx(var4, GridOp.this.icon_width / 3, GridOp.this.icon_width / 3);
            }

            var4.setVisibility(0);
            var4.setTag(var1);
            if (GridOp.this.number_of_columns > 0) {
               var4.setOnClickListener(new OnClickListener() {
                  public void onClick(View var1) {
                     GridAdapter.this.delete(var1);
                  }
               });
            }
         } else {
            var4.setVisibility(8);
         }

         if (GridOp.this.flag_multiple_selection) {
            Drawable var10 = var5.getCompoundDrawables()[0];
            if (GridOp.this.size_checkbox > 0) {
               var10.setBounds(0, 0, GridOp.this.size_checkbox, GridOp.this.size_checkbox);
            } else {
               var10.setBounds(0, 0, GridOp.this.icon_width / 4, GridOp.this.icon_width / 4);
            }

            var5.setCompoundDrawables(var10, (Drawable)null, (Drawable)null, (Drawable)null);
            if (GridOp.this.preSelectedPaths != null && GridOp.this.preSelectedPaths.contains(var1.path)) {
               var5.setChecked(true);
               var5.setEnabled(false);
            } else {
               var5.setChecked(false);
               var5.setEnabled(true);
               var5.setTag(var1);
               if (GridOp.this.number_of_columns > 0) {
                  var5.setOnClickListener(new OnClickListener() {
                     public void onClick(View var1) {
                        GridAdapter.this.logChecked(var1);
                     }
                  });
               }
            }

            var5.setVisibility(0);
         } else {
            var5.setVisibility(8);
         }

      }

      public int getCount() {
         return GridOp.this.gridCells.size();
      }

      public Object getItem(int var1) {
         return GridOp.this.gridCells.get(var1);
      }

      public long getItemId(int var1) {
         return 0L;
      }

      public View getView(int var1, View var2, ViewGroup var3) {
         GridOp.GridCell var4 = (GridOp.GridCell)GridOp.this.gridCells.get(var1);
         var3 = var4.viewGroup;
         ViewGroup var5 = var3;
         if (var3 == null) {
            var3 = (ViewGroup)this.inflater.inflate(R.layout.grid_cell, (ViewGroup)null);
            var4.viewGroup = var3;
            this.show_cell_view(var4, var1);
            var5 = var3;
            if (GridOp.this.number_of_columns > 0) {
               GridOp.this.change_grid_para();
               var5 = var3;
            }
         }

         return var5;
      }
   }

   public static class GridCell {
      public static final int COLLECT_MEDIA_NAMES = 0;
      public static final int COLLECT_PATHS = 2;
      public static final int COLLECT_TAGS = 1;
      public static final int SOURCE_APP_LIST = 8;
      public static final int SOURCE_BITMAP = 6;
      public static final int SOURCE_DRAWABLE = 0;
      public static final int SOURCE_FILE = 3;
      public static final int SOURCE_FOLDER = 7;
      public static final int SOURCE_ICON_PATH = 5;
      public static final int SOURCE_IMAGE_ID = 4;
      public static final int SOURCE_PATH = 2;
      public static final int SOURCE_URI = 1;
      public static final int TYPE_AUDIO = 2;
      public static final int TYPE_IMAGE = 0;
      public static final int TYPE_VIDEO = 1;
      public Bitmap bitmap = null;
      public String cell_label = null;
      public boolean checkable = false;
      public boolean clickable = true;
      public boolean deletable = false;
      public Drawable drawable = null;
      public String function = null;
      public String icon_label = null;
      public String icon_path = null;
      public long image_ID = -1L;
      public boolean isChecked = false;
      public String media_name = null;
      public int media_type = 0;
      public Object obj = null;
      public String path = null;
      public int position = -1;
      public int source = 1;
      public Object tag = null;
      public Uri uri = null;
      public ViewGroup viewGroup = null;
   }
}
