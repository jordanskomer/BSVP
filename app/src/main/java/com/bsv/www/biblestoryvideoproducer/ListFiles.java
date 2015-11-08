package com.bsv.www.biblestoryvideoproducer;

import android.graphics.Bitmap;

/**
 * Created by hannahbrown on 11/3/15.
 */
public class ListFiles {
    public Bitmap icon;
    public String title;

    public ListFiles(){
        super();
    }

    public ListFiles(Bitmap icon, String title) {
        super();
        this.icon = icon;
        this.title = title;
    }
}
