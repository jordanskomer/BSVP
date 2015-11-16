package com.bsv.www.storyproducer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.widget.Switch;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by hannahbrown on 9/27/15.
 */
class FileSystem {
    private String language = "English";

    FileSystem() {}
    //TODO Ability to change languages
    void changeLanguage(String language) {
        this.language = language;
    }

    public String[] getVideos() {
        String path = getPath();
        File f = new File(path);
        File file[] = f.listFiles();
        ArrayList<String> list = new ArrayList<>();
        for (int i=0; i < file.length; i++)
        {
            if(!file[i].isHidden()) {
                list.add(file[i].getName());
            }
        }
        String[] temp = new String[list.size()];
        return list.toArray(temp);
    }

    private String getPath() {
        if(isExternalStorageReadable()) {
            return System.getenv("SECONDARY_STORAGE") + "/BSVP/" + language;
        } else {
            return null;
        }
    }

    public String getPath(String lang) {
        if(isExternalStorageReadable()) {
            return System.getenv("SECONDARY_STORAGE") + "/BSVP/" + lang;
        } else {
            return null;
        }
    }

    public Bitmap getImage(String story, int number) {
        String path = getPath() + "/" + story;
        File f = new File(path);
        File file[] = f.listFiles();

        for (int i=0; i < file.length; i++) {
                if (file[i].getName().equals(number + ".jpg")) {
                    return BitmapFactory.decodeFile(path + "/" + file[i].getName());
                }
        }
        return null;
    }

    public int getImageAmount(String storyName){
        String path = getPath() + "/" + storyName;
        File f = new File(path);
        File file[] = f.listFiles();
        int count = 0;
        for(int i=0; i<file.length; i++) {
            if (!file[i].isHidden() && file[i].getName().contains(".jpg")) {
                count++;
            }
        }
        return count;
    }

    String[] content;
    public void loadSlideContent(String storyName, int slideNum){
        File file = new File((getPath() + "/" + storyName), (slideNum + ".txt"));
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
        }
        content = text.toString().split(Pattern.quote("~"));
    }

    public String getTitle(){
        return content[0];
    }
    public String getSubTitle(){
        return content[1];
    }
    public String getSlideTitle(){
        return content[2];
    }
    public String getSlideSubTitle(){
        return content[3];
    }
    public String getSlideContent(){
        return content[4];
    }


    private boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
}
