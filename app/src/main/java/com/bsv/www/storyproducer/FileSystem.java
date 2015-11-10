package com.bsv.www.storyproducer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.*;
import java.util.*;

/**
 * Created by hannahbrown on 9/27/15.
 */
class FileSystem {
    String language = "English";

    FileSystem() {}
    //TODO Ability to change languages
    void changeLanguage(String language) {
        this.language = language;
    }

    String[] getVideos() {
        String path = getPath();
        System.out.println("Path: " + path);

        File f = new File(path);
        File file[] = f.listFiles();
        ArrayList<String> list = new ArrayList<>();

        for (int i=0; i < file.length; i++)
        {
            if(!file[i].getName().contains(".")) {
                list.add(file[i].getName());
            }
        }

        String[] temp = new String[list.size()];
        for(int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
        return list.toArray(temp);
    }

    private String getPath() {
        return Environment.getExternalStorageDirectory() + "/" + language;
    }

    private String getPath(String language) {
        return Environment.getExternalStorageDirectory() + "/" + language;
    }

    Bitmap getImage(String story, int number) {
        String path = getPath() + "/" + story;

        File f = new File(path);
        File file[] = f.listFiles();

        for (int i=0; i < file.length; i++)
        {
            if(file[i].getName().contains(number + ".jpg")) {
                return BitmapFactory.decodeFile(path + "/" + file[i].getName());
            }
        }

        return null;
    }
}
