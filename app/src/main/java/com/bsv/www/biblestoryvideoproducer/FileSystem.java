package com.bsv.www.biblestoryvideoproducer;

import java.util.zip.*;
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
        try {
            ZipFile zipFile = new ZipFile("/sdcard/");//TODO find location
            ArrayList<String> list = new ArrayList<>();

            Enumeration<? extends ZipEntry> entries = zipFile.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                list.add(entry.getName());
            }

            return (String[])list.toArray();
        } catch (IOException e) {
            return null;
        }

    }

}
