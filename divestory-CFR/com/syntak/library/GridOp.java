/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.BitmapFactory
 *  android.graphics.drawable.BitmapDrawable
 *  android.graphics.drawable.Drawable
 *  android.net.Uri
 *  android.os.AsyncTask
 *  android.provider.MediaStore
 *  android.provider.MediaStore$Images
 *  android.provider.MediaStore$Images$Thumbnails
 *  android.view.KeyEvent
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.View$OnKeyListener
 *  android.view.View$OnLongClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnPreDrawListener
 *  android.widget.BaseAdapter
 *  android.widget.CheckBox
 *  android.widget.GridView
 *  android.widget.ImageView
 *  android.widget.ListAdapter
 *  android.widget.RelativeLayout
 *  android.widget.ScrollView
 *  android.widget.TextView
 */
package com.syntak.library;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.syntak.library.FileOp;
import com.syntak.library.MediaOp;
import com.syntak.library.R;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GridOp {
    public static Drawable default_icon;
    final int CELL_WIDTH_BY_COLUMNS;
    final int CELL_WIDTH_BY_SET;
    float alpha_original;
    float alpha_selected = 0.8f;
    private ArrayList<AsyncTask> asyncTasks = null;
    int cell_background_color = -16777216;
    private int cell_frame_width = 0;
    final int cell_gap_factor;
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
    private GridAdapter gridAdapter;
    private ArrayList<GridCell> gridCells = null;
    private GridView gridView;
    private int grid_view_width = 0;
    private int icon_height = 100;
    private int icon_label_background = 2004318071;
    private int icon_label_text_color = 16777215;
    private int icon_label_text_size = 20;
    private float icon_percentage = 1.0f;
    private int icon_width = 100;
    private int min_cell_spacing_H = 0;
    private int number_of_columns = 0;
    int position_highlighted = -1;
    ArrayList<String> preSelectedPaths = null;
    private ScrollView scrollView;
    private int showed_number;
    private int size_checkbox = 0;
    private int size_delete_button = 0;

    public GridOp(Context context, GridView gridView, ArrayList<GridCell> arrayList) {
        this.CELL_WIDTH_BY_COLUMNS = 0;
        this.CELL_WIDTH_BY_SET = 1;
        this.cell_gap_factor = 15;
        this.context = context;
        this.gridView = gridView;
        this.contentResolver = context.getContentResolver();
        this.gridCells = arrayList == null ? new ArrayList() : arrayList;
        this.gridCells.size();
        this.gridAdapter = new GridAdapter();
        default_icon = this.context.getResources().getDrawable(R.drawable.icon);
        this.set_gridview_Dpad_listener();
        this.asyncTasks = new ArrayList();
    }

    private void calcuate_size() {
        int n;
        this.number_of_columns = this.gridView.getNumColumns();
        this.cell_width = this.gridView.getColumnWidth();
        this.cell_spacing_H = n = this.gridView.getHorizontalSpacing();
        this.gridView.setVerticalSpacing(n + this.extra_spacing_V);
        this.cell_spacing_V = this.gridView.getVerticalSpacing();
        float f = this.icon_percentage;
        int n2 = this.cell_width;
        this.icon_width = n = (int)(f * (float)n2);
        this.icon_height = n;
        this.cell_height = n2;
    }

    private boolean change_grid_para() {
        if (this.number_of_columns <= 0) {
            this.calcuate_size();
        }
        if (this.number_of_columns <= 0) return false;
        this.set_grid_height();
        return true;
    }

    private void check_grid_scroll(int n, int n2) {
        int n3 = this.getCellHeightPx();
        if (n < n3) {
            return;
        }
        int n4 = this.getRowsNumber(n2 + 1) * n3;
        n2 = this.scrollView.getScrollY();
        if (n4 >= n + n2 - n3) {
            this.scrollView.scrollTo(0, n4 - n + n3);
            return;
        }
        if (n4 > n2 + n3 + n3 / 2) return;
        this.scrollView.scrollTo(0, n4 - n3);
    }

    private void pre_draw_grid() {
        this.gridView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener(){

            public boolean onPreDraw() {
                if (!GridOp.this.change_grid_para()) return true;
                GridOp.this.gridView.getViewTreeObserver().removeOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)this);
                GridOp.this.gridView.setAdapter((ListAdapter)GridOp.this.gridAdapter);
                return true;
            }
        });
    }

    public static Bitmap prepare_image(Context context, GridCell object, int n, int n2) {
        int n3 = ((GridCell)object).source;
        String string2 = null;
        Object var6_6 = null;
        String string3 = null;
        switch (n3) {
            default: {
                object = var6_6;
                return MediaOp.getBitmapCenterCrop((Bitmap)object);
            }
            case 8: {
                object = ((BitmapDrawable)((GridCell)object).drawable).getBitmap();
                return MediaOp.getBitmapCenterCrop((Bitmap)object);
            }
            case 7: {
                object = ((GridCell)object).icon_path;
                if (FileOp.checkFileExist((String)object)) {
                    string3 = MediaOp.getThumbnailFromPath(context, (String)object, n, n2, 3);
                }
                object = string3;
                if (string3 != null) return MediaOp.getBitmapCenterCrop((Bitmap)object);
                object = ((BitmapDrawable)context.getResources().getDrawable(R.mipmap.folder_yellow)).getBitmap();
                return MediaOp.getBitmapCenterCrop((Bitmap)object);
            }
            case 6: {
                if (((GridCell)object).bitmap != null) {
                    object = ((GridCell)object).bitmap;
                    return MediaOp.getBitmapCenterCrop((Bitmap)object);
                }
                object = MediaOp.getThumbnailFromPath(context, ((GridCell)object).path, n, n2, 3);
                return MediaOp.getBitmapCenterCrop((Bitmap)object);
            }
            case 5: {
                string3 = string2 = ((GridCell)object).icon_path;
                if (string2 == null) {
                    string3 = ((GridCell)object).path;
                }
                if (FileOp.checkFileExist(string3)) {
                    object = MediaOp.getThumbnailFromPath(context, string3, n, n2, 3);
                    return MediaOp.getBitmapCenterCrop((Bitmap)object);
                }
                object = ((BitmapDrawable)default_icon).getBitmap();
                return MediaOp.getBitmapCenterCrop((Bitmap)object);
            }
            case 4: {
                object = MediaStore.Images.Thumbnails.getThumbnail((ContentResolver)context.getContentResolver(), (long)((GridCell)object).image_ID, (int)3, null);
                return MediaOp.getBitmapCenterCrop((Bitmap)object);
            }
            case 3: {
                object = ((BitmapDrawable)default_icon).getBitmap();
                return MediaOp.getBitmapCenterCrop((Bitmap)object);
            }
            case 2: {
                object = ((GridCell)object).path;
                string3 = string2;
                if (FileOp.checkFileExist((String)object)) {
                    string3 = MediaOp.getThumbnailFromPath(context, (String)object, n, n2, 3);
                }
                object = string3;
                if (string3 != null) return MediaOp.getBitmapCenterCrop((Bitmap)object);
                object = ((BitmapDrawable)default_icon).getBitmap();
                return MediaOp.getBitmapCenterCrop((Bitmap)object);
            }
            case 1: {
                object = ((GridCell)object).uri;
                if (FileOp.checkUriExist((Uri)object)) {
                    object = MediaOp.getSampledBitmapFromPath(FileOp.getPathFromURI(context, (Uri)object), n, n2);
                    return MediaOp.getBitmapCenterCrop((Bitmap)object);
                }
                object = ((BitmapDrawable)default_icon).getBitmap();
                return MediaOp.getBitmapCenterCrop((Bitmap)object);
            }
            case 0: 
        }
        object = ((BitmapDrawable)((GridCell)object).drawable).getBitmap();
        return MediaOp.getBitmapCenterCrop((Bitmap)object);
    }

    private void set_grid_height() {
        this.cell_height = this.icon_height + (this.cell_label_height_max + this.cell_label_height_min) / 2;
        ViewGroup.LayoutParams layoutParams = this.gridView.getLayoutParams();
        layoutParams.height = this.getRowsNumber(this.gridView.getCount()) * (this.cell_height + this.cell_spacing_V);
        if (this.cell_spacing_V == 0) {
            layoutParams.height += 10;
        }
        this.gridView.setLayoutParams(layoutParams);
    }

    private boolean set_gridview_Dpad_listener() {
        this.gridView.setOnKeyListener(new View.OnKeyListener(){

            public boolean onKey(View object, int n, KeyEvent keyEvent) {
                int n2 = GridOp.this.gridView.getSelectedItemPosition();
                if (n == 23) {
                    n = n2;
                    if (n2 == -1) {
                        if (GridOp.this.gridView.getCount() <= 0) return false;
                        GridOp.this.gridView.setSelection(0);
                        n = 0;
                    }
                    object = GridOp.this;
                    ((GridOp)object).onIconClicked(n, (GridCell)((GridOp)object).gridCells.get(n));
                    return false;
                }
                switch (n) {
                    default: {
                        return false;
                    }
                    case 19: 
                    case 20: {
                        object = GridOp.this;
                        ((GridOp)object).check_grid_scroll(((GridOp)object).display_area_height_px, n2);
                    }
                    case 21: 
                    case 22: 
                }
                object = GridOp.this;
                ((GridOp)object).onCellFocused(n2, (GridCell)((GridOp)object).gridCells.get(n2));
                return false;
            }
        });
        return true;
    }

    public boolean appendCell(int n, GridCell gridCell) {
        if (n > 0) return false;
        ArrayList<GridCell> arrayList = this.gridCells;
        arrayList.add(arrayList.size() + n, gridCell);
        this.onDataChanged();
        return true;
    }

    public boolean appendCell(GridCell gridCell) {
        this.gridCells.add(gridCell);
        this.onDataChanged();
        return true;
    }

    public void beforeDeleted(GridCell gridCell) {
    }

    public void clearCells() {
        Iterator<Object> iterator2 = this.asyncTasks.iterator();
        while (iterator2.hasNext()) {
            iterator2.next().cancel(true);
        }
        this.asyncTasks.clear();
        this.asyncTasks.trimToSize();
        iterator2 = this.gridCells.iterator();
        do {
            if (!iterator2.hasNext()) {
                this.gridCells.clear();
                this.gridCells.trimToSize();
                this.gridView.setAdapter((ListAdapter)this.gridAdapter);
                this.number_of_columns = 0;
                this.position_highlighted = -1;
                this.notifyDataSetChanged();
                return;
            }
            GridCell gridCell = (GridCell)iterator2.next();
        } while (true);
    }

    public void fillGridCellsFromAppsList(Context context, List<ApplicationInfo> object) {
        context = context.getPackageManager();
        object = object.iterator();
        do {
            if (!object.hasNext()) {
                this.notifyDataSetChanged();
                return;
            }
            Object object2 = (ApplicationInfo)object.next();
            String string2 = object2.packageName;
            if (context.getLaunchIntentForPackage(string2) == null) continue;
            Drawable drawable2 = context.getApplicationIcon(object2);
            object2 = context.getApplicationLabel(object2).toString();
            GridCell gridCell = new GridCell();
            gridCell.source = 8;
            gridCell.deletable = false;
            gridCell.checkable = false;
            gridCell.cell_label = object2;
            gridCell.drawable = drawable2;
            gridCell.obj = string2;
            gridCell.viewGroup = null;
            this.appendCell(gridCell);
        } while (true);
    }

    public boolean fillGridCellsFromPath(String arrfile) {
        if ((arrfile = new File((String)arrfile).listFiles()) == null) {
            return false;
        }
        int n = arrfile.length;
        int n2 = 0;
        do {
            if (n2 >= n) {
                this.notifyDataSetChanged();
                return true;
            }
            File file = arrfile[n2];
            GridCell gridCell = new GridCell();
            gridCell.path = file.getPath();
            gridCell.media_name = file.getName();
            gridCell.deletable = false;
            gridCell.checkable = false;
            gridCell.viewGroup = null;
            if (file.isDirectory()) {
                gridCell.icon_path = FileOp.getAnyFilePathInFolder(file.getPath());
                gridCell.source = 7;
                gridCell.icon_label = file.getName();
            } else {
                gridCell.source = 2;
                if (MediaOp.getMediaTypeFromPath(gridCell.path) != 1) {
                    gridCell.icon_label = file.getName();
                }
            }
            this.appendCell(gridCell);
            ++n2;
        } while (true);
    }

    public void fillGridCellsFromPathArray(String[] arrstring, boolean bl, boolean bl2, int n) {
        if (arrstring.length <= 0) return;
        int n2 = 0;
        do {
            if (n2 >= arrstring.length) {
                this.notifyDataSetChanged();
                return;
            }
            String string2 = arrstring[n2];
            Bitmap bitmap = MediaOp.getThumbnailFromPath(this.context, string2, this.icon_width, this.icon_height, 3);
            GridCell gridCell = new GridCell();
            gridCell.source = 6;
            gridCell.path = string2;
            gridCell.bitmap = bitmap;
            gridCell.checkable = bl2;
            gridCell.deletable = bl;
            gridCell.viewGroup = null;
            this.appendCell(n, gridCell);
            ++n2;
        } while (true);
    }

    public void fillGridCellsFromStringArray(String[] arrstring, String string2, String string3, boolean bl, boolean bl2, int n) {
        int n2 = arrstring.length;
        int n3 = 0;
        do {
            if (n3 >= n2) {
                this.notifyDataSetChanged();
                return;
            }
            String string4 = arrstring[n3];
            GridCell gridCell = new GridCell();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append(string4);
            gridCell.path = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append(string3);
            stringBuilder.append(string4);
            gridCell.icon_path = stringBuilder.toString();
            gridCell.source = 5;
            gridCell.media_name = string4;
            gridCell.deletable = bl;
            gridCell.checkable = bl2;
            gridCell.viewGroup = null;
            this.appendCell(n, gridCell);
            ++n3;
        } while (true);
    }

    public void fillGridCellsFromUris(Context context, ArrayList<Uri> arrayList, boolean bl, boolean bl2, int n) {
        if (arrayList.size() <= 0) return;
        int n2 = 0;
        do {
            if (n2 >= arrayList.size()) {
                this.notifyDataSetChanged();
                return;
            }
            Uri uri = arrayList.get(n2);
            String string2 = FileOp.getPathFromURI(context, uri);
            FileOp.getFilenameFromPath(string2);
            Bitmap bitmap = MediaOp.getThumbnailFromPath(context, string2, this.icon_width, this.icon_height, 3);
            GridCell gridCell = new GridCell();
            gridCell.source = 6;
            gridCell.uri = uri;
            gridCell.path = string2;
            gridCell.bitmap = bitmap;
            gridCell.checkable = bl2;
            gridCell.deletable = bl;
            gridCell.viewGroup = null;
            this.appendCell(n, gridCell);
            ++n2;
        } while (true);
    }

    public void fillGridCellsWithFolderInfo(ArrayList<MediaOp.MediaInfo> object, int n) {
        if (this.gridCells == null) return;
        if (object == null) {
            return;
        }
        Iterator<MediaOp.MediaInfo> iterator2 = ((ArrayList)object).iterator();
        do {
            if (!iterator2.hasNext()) {
                this.notifyDataSetChanged();
                return;
            }
            MediaOp.MediaInfo mediaInfo = iterator2.next();
            if (mediaInfo.type != 0 || n != 4 && !mediaInfo.mime_type.contains("image")) continue;
            object = new GridCell();
            ((GridCell)object).source = 4;
            ((GridCell)object).bitmap = mediaInfo.icon;
            if (mediaInfo.mime_type.contains("audio")) {
                ((GridCell)object).source = 6;
                ((GridCell)object).bitmap = BitmapFactory.decodeResource((Resources)this.context.getResources(), (int)R.mipmap.ic_audio);
            }
            if (mediaInfo.mime_type.contains("video")) {
                ((GridCell)object).source = 6;
                ((GridCell)object).bitmap = MediaOp.getThumbnailFromVideo(mediaInfo.media_path);
            }
            ((GridCell)object).path = mediaInfo.media_path;
            ((GridCell)object).image_ID = mediaInfo.id;
            ((GridCell)object).tag = mediaInfo.folder_path;
            ((GridCell)object).icon_label = mediaInfo.folder_name;
            ((GridCell)object).isChecked = false;
            ((GridCell)object).viewGroup = null;
            this.appendCell((GridCell)object);
        } while (true);
    }

    public void fillGridCellsWithImageInFolder(ArrayList<MediaOp.MediaInfo> object, String string2) {
        if (this.gridCells == null) return;
        if (object == null) {
            return;
        }
        object = ((ArrayList)object).iterator();
        do {
            if (!object.hasNext()) {
                this.notifyDataSetChanged();
                return;
            }
            MediaOp.MediaInfo mediaInfo = (MediaOp.MediaInfo)object.next();
            if (!mediaInfo.folder_path.equals(string2) || mediaInfo.type != 1) continue;
            GridCell gridCell = new GridCell();
            gridCell.source = 4;
            gridCell.bitmap = mediaInfo.icon;
            gridCell.path = mediaInfo.media_path;
            gridCell.image_ID = mediaInfo.id;
            gridCell.icon_label = mediaInfo.media_name;
            gridCell.isChecked = false;
            gridCell.viewGroup = null;
            this.appendCell(gridCell);
        } while (true);
    }

    public void fillGridCellsWithMediaInFolder(ArrayList<MediaOp.MediaInfo> object, String string2) {
        if (this.gridCells == null) return;
        if (object == null) {
            return;
        }
        Iterator<MediaOp.MediaInfo> iterator2 = ((ArrayList)object).iterator();
        do {
            if (!iterator2.hasNext()) {
                this.notifyDataSetChanged();
                return;
            }
            object = iterator2.next();
            if (!((MediaOp.MediaInfo)object).folder_path.equals(string2) || ((MediaOp.MediaInfo)object).type != 1 && ((MediaOp.MediaInfo)object).type != 2 && ((MediaOp.MediaInfo)object).type != 3) continue;
            GridCell gridCell = new GridCell();
            int n = ((MediaOp.MediaInfo)object).type;
            gridCell.source = n != 2 && n != 3 ? 4 : 2;
            gridCell.bitmap = ((MediaOp.MediaInfo)object).icon;
            gridCell.path = ((MediaOp.MediaInfo)object).media_path;
            gridCell.image_ID = ((MediaOp.MediaInfo)object).id;
            gridCell.icon_label = ((MediaOp.MediaInfo)object).media_name;
            gridCell.isChecked = false;
            gridCell.viewGroup = null;
            this.appendCell(gridCell);
        } while (true);
    }

    public GridCell getCell(int n) {
        return this.gridCells.get(n);
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

    public ArrayList<GridCell> getGridCells() {
        return this.gridCells;
    }

    public int getRowsNumber(int n) {
        int n2;
        int n3 = this.number_of_columns;
        int n4 = n2 = n / n3;
        if (n3 * n2 >= n) return n4;
        return n2 + 1;
    }

    public String getStringWithGridCellInfo(int n, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        int n2 = 0;
        while (n2 < this.gridCells.size()) {
            String string3 = null;
            if (n != 0) {
                if (n != 1) {
                    if (n == 2 && this.gridCells.get((int)n2).path != null) {
                        string3 = this.gridCells.get((int)n2).path;
                    }
                } else if (this.gridCells.get((int)n2).tag != null) {
                    string3 = this.gridCells.get((int)n2).tag.toString();
                }
            } else if (this.gridCells.get((int)n2).media_name != null) {
                string3 = this.gridCells.get((int)n2).media_name;
            }
            if (string3 != null) {
                if (n2 > 0) {
                    stringBuilder.append(string2);
                }
                stringBuilder.append(string3);
            }
            ++n2;
        }
        return stringBuilder.toString();
    }

    public void incrementCellsShowedNumber(int n) {
        this.showed_number = n = this.showed_number + n;
        if (n <= this.gridCells.size()) return;
        this.showed_number = this.gridCells.size();
    }

    public void notifyDataSetChanged() {
        this.gridAdapter.notifyDataSetChanged();
    }

    public void onCellFocused(int n, GridCell gridCell) {
    }

    public void onCheckedChanged(int n) {
    }

    public void onDataChanged() {
    }

    public void onIconClicked(int n, GridCell gridCell) {
    }

    public void onIconLongClicked(int n, GridCell gridCell) {
    }

    public void resetPositionHighlight() {
        this.position_highlighted = -1;
    }

    public void setCellAsyncLoading(boolean bl) {
        this.flag_cell_async_loading = bl;
    }

    public void setCellBackgroundColor(int n) {
        this.cell_background_color = n;
    }

    public void setCellFrameWidth(int n) {
        if (n <= 0) return;
        this.cell_frame_width = n;
    }

    public void setCellLabelBackground(int n) {
        this.flag_set_cell_label_background = true;
        this.cell_label_background = n;
    }

    public void setCellLabelTextColor(int n) {
        this.flag_set_cell_label_text_color = true;
        this.cell_label_text_color = n;
    }

    public void setCellLabelTextSize(int n) {
        this.flag_set_cell_label_text_size = true;
        this.cell_label_text_size = MediaOp.DpToPx(this.context, n);
    }

    public void setCellWidthDp(int n, float f, int n2, int n3) {
        this.cell_width_decider = 1;
        this.cell_width = MediaOp.DpToPx(this.context, n);
        this.min_cell_spacing_H = MediaOp.DpToPx(this.context, n2);
        if (f > 0.0f) {
            this.icon_percentage = f;
        }
        this.extra_spacing_V = n3;
        this.gridView.setColumnWidth(this.cell_width);
        this.gridView.setHorizontalSpacing(this.min_cell_spacing_H);
        this.pre_draw_grid();
    }

    public void setCellsShowedNumber(int n) {
        this.showed_number = n;
    }

    public void setCheckboxSize(int n) {
        this.size_checkbox = n;
    }

    public void setDefaultIcon(Drawable drawable2) {
        default_icon = drawable2;
    }

    public void setDeleteButton(int n) {
        this.size_delete_button = n;
    }

    public void setIconLabelBackground(int n) {
        this.flag_set_icon_label_background = true;
        this.icon_label_background = n;
    }

    public void setIconLabelTextColor(int n) {
        this.flag_set_icon_label_text_color = true;
        this.icon_label_text_color = n;
    }

    public void setIconLabelTextSize(int n) {
        this.flag_set_icon_label_text_size = true;
        this.icon_label_text_size = MediaOp.DpToPx(this.context, n);
    }

    public void setMultipleSelection(boolean bl) {
        this.flag_multiple_selection = bl;
    }

    public void setNumberOfColumns(int n, float f, int n2, int n3) {
        this.cell_width_decider = 0;
        if (n2 > 0) {
            this.cell_spacing_H = MediaOp.DpToPx(this.context, n2);
        }
        if (f > 0.0f) {
            this.icon_percentage = f;
        }
        this.extra_spacing_V = n3;
        this.gridView.setNumColumns(n);
        this.gridView.setHorizontalSpacing(this.cell_spacing_H);
        this.pre_draw_grid();
    }

    public void setPositionFocused(int n) {
        if (n < 0) return;
        this.gridView.setSelection(n);
    }

    public void setPositionHighlight(int n) {
        if (n < 0) return;
        int n2 = this.position_highlighted;
        if (n == n2) return;
        if (n2 >= 0) {
            this.gridCells.get((int)n2).viewGroup = null;
        }
        this.gridCells.get((int)n).viewGroup = null;
        this.position_highlighted = n;
        this.check_grid_scroll(this.display_area_height_px, n);
        this.notifyDataSetChanged();
    }

    public void setPreSelectedPaths(String string2, String arrstring) {
        if (string2 == null) return;
        arrstring = string2.split((String)arrstring);
        this.preSelectedPaths = new ArrayList();
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            string2 = arrstring[n2];
            this.preSelectedPaths.add(string2);
            ++n2;
        }
    }

    public void setScrollView(ScrollView scrollView, int n) {
        this.scrollView = scrollView;
        this.display_area_height_px = n;
    }

    public static class AsyncShowThumbnail
    extends AsyncTask<Void, Void, Bitmap> {
        public Context context;
        public GridCell gc;
        public int height;
        public Bitmap icon = null;
        public ImageView iv;
        public String path;
        public int width;

        public AsyncShowThumbnail(Context context, ImageView imageView, GridCell gridCell, int n, int n2) {
            this.context = context;
            this.iv = imageView;
            this.gc = gridCell;
            this.width = n;
            this.height = n2;
        }

        protected Bitmap doInBackground(Void ... bitmap) {
            if (this.isCancelled()) {
                return null;
            }
            this.icon = bitmap = GridOp.prepare_image(this.context, this.gc, this.width, this.height);
            return bitmap;
        }

        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap == null) {
                if (this.isCancelled()) return;
            }
            this.iv.setImageBitmap(bitmap);
        }
    }

    public class GridAdapter
    extends BaseAdapter {
        LayoutInflater inflater;

        public GridAdapter() {
            this.inflater = (LayoutInflater)GridOp.this.context.getSystemService("layout_inflater");
        }

        private void delete(View object) {
            object = (GridCell)object.getTag();
            GridOp.this.beforeDeleted((GridCell)object);
            GridOp.this.gridCells.remove(object);
            this.notifyDataSetChanged();
            GridOp.this.set_grid_height();
            GridOp.this.onDataChanged();
        }

        private void logChecked(View view) {
            GridCell gridCell = (GridCell)view.getTag();
            gridCell.isChecked = ((CheckBox)view).isChecked();
            if (gridCell.isChecked) {
                GridOp.this.onCheckedChanged(1);
                return;
            }
            GridOp.this.onCheckedChanged(-1);
        }

        private void show_cell_view(GridCell gridCell, final int n) {
            ImageView imageView = (ImageView)gridCell.viewGroup.findViewById(R.id.icon);
            ImageView imageView2 = (ImageView)gridCell.viewGroup.findViewById(R.id.deleteButton);
            CheckBox checkBox = (CheckBox)gridCell.viewGroup.findViewById(R.id.checkBox);
            TextView textView = (TextView)gridCell.viewGroup.findViewById(R.id.icon_label);
            final TextView textView2 = (TextView)gridCell.viewGroup.findViewById(R.id.cell_label);
            RelativeLayout relativeLayout = (RelativeLayout)gridCell.viewGroup.findViewById(R.id.cell);
            imageView.setBackgroundColor(GridOp.this.cell_background_color);
            relativeLayout.setVisibility(0);
            relativeLayout.setPadding(GridOp.this.cell_frame_width, GridOp.this.cell_frame_width, GridOp.this.cell_frame_width, GridOp.this.cell_frame_width);
            if (n == GridOp.this.position_highlighted) {
                relativeLayout.setBackgroundColor(-256);
            } else {
                relativeLayout.setBackgroundColor(0);
            }
            if (GridOp.this.number_of_columns <= 0 && GridOp.this.cell_width_decider == 0) {
                GridOp.this.calcuate_size();
            }
            MediaOp.setViewSizePx((View)imageView, GridOp.this.icon_width, GridOp.this.icon_height);
            if (GridOp.this.flag_cell_async_loading && gridCell.bitmap == null) {
                GridOp.this.asyncTasks.add(new AsyncShowThumbnail(GridOp.this.context, imageView, gridCell, GridOp.this.cell_width, GridOp.this.cell_height).execute((Object[])new Void[0]));
            } else {
                relativeLayout = gridCell.bitmap == null ? GridOp.prepare_image(GridOp.this.context, (GridCell)GridOp.this.gridCells.get(n), GridOp.this.cell_width, GridOp.this.cell_height) : gridCell.bitmap;
                if (relativeLayout != null) {
                    imageView.setImageBitmap((Bitmap)relativeLayout);
                } else if (gridCell.drawable != null) {
                    imageView.setImageDrawable(gridCell.drawable);
                }
            }
            imageView.setTag((Object)gridCell);
            if (gridCell.clickable) {
                imageView.setOnClickListener(new View.OnClickListener(){

                    public void onClick(View object) {
                        object = (GridCell)object.getTag();
                        GridOp.this.onIconClicked(n, (GridCell)object);
                    }
                });
                imageView.setOnLongClickListener(new View.OnLongClickListener(){

                    public boolean onLongClick(View object) {
                        object = (GridCell)object.getTag();
                        GridOp.this.onIconLongClicked(n, (GridCell)object);
                        return false;
                    }
                });
            }
            if (gridCell.icon_label != null) {
                textView.setTag((Object)gridCell);
                textView.setText((CharSequence)gridCell.icon_label);
                textView.setVisibility(0);
                textView.setWidth(GridOp.this.cell_width);
                if (GridOp.this.flag_set_icon_label_text_size) {
                    textView.setTextSize((float)GridOp.this.icon_label_text_size);
                }
                if (GridOp.this.flag_set_icon_label_text_color) {
                    textView.setTextColor(GridOp.this.icon_label_text_color);
                }
                if (GridOp.this.flag_set_icon_label_background) {
                    textView.setBackgroundColor(GridOp.this.icon_label_background);
                }
            } else {
                textView.setVisibility(8);
            }
            if (gridCell.cell_label != null) {
                GridOp.this.cell_label_height = textView2.getHeight();
                textView2.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener(){

                    public boolean onPreDraw() {
                        int n = textView2.getHeight();
                        if (GridOp.this.cell_label_height_min == 0 || n < GridOp.this.cell_label_height_min) {
                            GridOp.this.cell_label_height_min = n;
                        }
                        if (n > GridOp.this.cell_label_height_max) {
                            GridOp.this.cell_label_height_max = n;
                        }
                        GridOp.this.set_grid_height();
                        textView2.getViewTreeObserver().removeOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)this);
                        return true;
                    }
                });
                textView2.setTag((Object)gridCell);
                textView2.setText((CharSequence)gridCell.cell_label);
                textView2.setVisibility(0);
                textView2.setWidth(GridOp.this.cell_width);
                if (GridOp.this.flag_set_cell_label_text_size) {
                    textView2.setTextSize((float)GridOp.this.cell_label_text_size);
                }
                if (GridOp.this.flag_set_cell_label_text_color) {
                    textView2.setTextColor(GridOp.this.cell_label_text_color);
                }
                if (GridOp.this.flag_set_cell_label_background) {
                    textView2.setBackgroundColor(GridOp.this.cell_label_background);
                }
            } else {
                textView2.setVisibility(8);
            }
            if (gridCell.deletable) {
                if (GridOp.this.size_delete_button > 0) {
                    MediaOp.setViewSizePx((View)imageView2, GridOp.this.size_delete_button, GridOp.this.size_delete_button);
                } else {
                    MediaOp.setViewSizePx((View)imageView2, GridOp.this.icon_width / 3, GridOp.this.icon_width / 3);
                }
                imageView2.setVisibility(0);
                imageView2.setTag((Object)gridCell);
                if (GridOp.this.number_of_columns > 0) {
                    imageView2.setOnClickListener(new View.OnClickListener(){

                        public void onClick(View view) {
                            GridAdapter.this.delete(view);
                        }
                    });
                }
            } else {
                imageView2.setVisibility(8);
            }
            if (!GridOp.this.flag_multiple_selection) {
                checkBox.setVisibility(8);
                return;
            }
            relativeLayout = checkBox.getCompoundDrawables()[0];
            if (GridOp.this.size_checkbox > 0) {
                relativeLayout.setBounds(0, 0, GridOp.this.size_checkbox, GridOp.this.size_checkbox);
            } else {
                relativeLayout.setBounds(0, 0, GridOp.this.icon_width / 4, GridOp.this.icon_width / 4);
            }
            checkBox.setCompoundDrawables((Drawable)relativeLayout, null, null, null);
            if (GridOp.this.preSelectedPaths != null && GridOp.this.preSelectedPaths.contains(gridCell.path)) {
                checkBox.setChecked(true);
                checkBox.setEnabled(false);
            } else {
                checkBox.setChecked(false);
                checkBox.setEnabled(true);
                checkBox.setTag((Object)gridCell);
                if (GridOp.this.number_of_columns > 0) {
                    checkBox.setOnClickListener(new View.OnClickListener(){

                        public void onClick(View view) {
                            GridAdapter.this.logChecked(view);
                        }
                    });
                }
            }
            checkBox.setVisibility(0);
        }

        public int getCount() {
            return GridOp.this.gridCells.size();
        }

        public Object getItem(int n) {
            return GridOp.this.gridCells.get(n);
        }

        public long getItemId(int n) {
            return 0L;
        }

        public View getView(int n, View view, ViewGroup viewGroup) {
            GridCell gridCell = (GridCell)GridOp.this.gridCells.get(n);
            viewGroup = gridCell.viewGroup;
            view = viewGroup;
            if (viewGroup != null) return view;
            gridCell.viewGroup = viewGroup = (ViewGroup)this.inflater.inflate(R.layout.grid_cell, null);
            this.show_cell_view(gridCell, n);
            view = viewGroup;
            if (GridOp.this.number_of_columns <= 0) return view;
            GridOp.this.change_grid_para();
            return viewGroup;
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

