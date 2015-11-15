package com.bsv.www.storyproducer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by hannahbrown on 11/3/15.
 */
public class CustomAdapter extends ArrayAdapter<ListFiles> {
    Context context;
    int layoutResourceId;
    ListFiles data[] = null;

    public CustomAdapter(Context context, int layoutResourceId, ListFiles[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        FileHolder holder;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new FileHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.story_list_image);
            holder.txtTitle = (TextView)row.findViewById(R.id.story_list_title);
            holder.txtSubtitle = (TextView)row.findViewById(R.id.story_list_subtitle);

            row.setTag(holder);
        }
        else
        {
            holder = (FileHolder)row.getTag();
        }

        ListFiles listFiles = data[position];
        holder.txtTitle.setText(listFiles.title);
//        holder.imgIcon.setImageBitmap(listFiles.icon);
        holder.imgIcon.setImageBitmap(getRoundedShape(listFiles.icon));
        holder.txtSubtitle.setText(listFiles.subtitle);

        return row;
    }

    static class FileHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
        TextView txtSubtitle;
    }

    public static Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
        int targetWidth = Math.min(scaleBitmapImage.getWidth(), scaleBitmapImage.getHeight());
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth, targetWidth,Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(((float) targetWidth - 1) / 2, ((float) targetWidth - 1) / 2,
                (Math.min(((float) targetWidth), ((float) targetWidth)) / 2), Path.Direction.CCW);
        canvas.clipPath(path);
        Bitmap sourceBitmap = scaleBitmapImage;
        canvas.drawBitmap(sourceBitmap,
                new Rect(0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight()),
                new Rect(0, 0, targetWidth, targetWidth), null);
        return targetBitmap;
    }
}

class ListFiles {
    public Bitmap icon;
    public String title;
    public String subtitle;

    public ListFiles(){ super(); }

    public ListFiles(Bitmap icon, String title, String subtitle) {
        super();
        this.icon = icon;
        this.title = title;
        this.subtitle = subtitle;
    }
}
