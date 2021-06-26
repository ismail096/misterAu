package com.project.misterauto.Util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.project.misterauto.model.users;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Cache {

    private static final String CACHE_NAME = "MisterAuto" ;
    private static final String APP_NAME = "MisterAuto";
    private static String _permanentDir;

    protected static String getPermanentDir() {
        return _permanentDir;
    }

    protected static String getPermanentFileForKey(String key) {
        return getPermanentDir()+"/"+APP_NAME+"."+key;
    }

    public static void initCache(Context c){
        File f = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (android.os.Build.VERSION.SDK_INT < 8) {
                f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/"+APP_NAME);
                f.mkdirs();
            } else f = c.getExternalCacheDir();
        } else f = c.getCacheDir();
        if (f == null) {
            f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/"+APP_NAME);
            f.mkdirs();
        }
        _permanentDir = f.getAbsolutePath()+"/"+CACHE_NAME;
        try {
            File permanent = new File(getPermanentDir());
            if (!permanent.isDirectory()) permanent.delete();
            if (!permanent.exists()) permanent.mkdirs();
        } catch (Exception e) {
        }
    }



    public static Object getPermanentObject(String key) {
        try {
            String file = getPermanentFileForKey(key);
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            Object object = in.readObject();
            in.close();
            return object;

        } catch (Exception e) {
           // Log.e("Cache","getPermanentObject("+key+")",e);
        }
        return null;
    }

    public static void putPermanentObject(Serializable object, String key) {
        try {
            removePermanentForKey(key);
            String file = getPermanentFileForKey(key);
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(object);
            out.close();
            //Log.i("Cache","Permanent object ["+file+"] cached for key ["+key+"]");
        } catch (Exception e) {
            //Log.e("Cache","putPermanentObject("+key+")",e);
        }
    }


    public static void removePermanentForKey(String key) {
        try {
            File ff = new File(getPermanentFileForKey(key));
            if (ff.exists()) ff.delete();
            //Log.i("Cache","Permanent object removed for key ["+key+"]");
        } catch (Exception e) {
            //Log.e("Cache","removePermanentForKey("+key+")",e);
        }
    }


}
