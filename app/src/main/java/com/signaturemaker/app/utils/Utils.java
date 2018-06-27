/*
 __ _                   _                                 _
/ _(_) __ _ _ __   __ _| |_ _   _ _ __ ___    /\/\   __ _| | _____ _ __
\ \| |/ _` | '_ \ / _` | __| | | | '__/ _ \  /    \ / _` | |/ / _ \ '__|
_\ \ | (_| | | | | (_| | |_| |_| | | |  __/ / /\/\ \ (_| |   <  __/ |
\__/_|\__, |_| |_|\__,_|\__|\__,_|_|  \___| \/    \/\__,_|_|\_\___|_|
      |___/

Copyright (C) 2018  Raúl Rodríguez Concepción www.wepica.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.*/
package com.signaturemaker.app.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.signaturemaker.app.models.ItemFile;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public final class Utils {

    private Utils() {
    }
    
    public static int sortOrder = Constants.DEFAULT_SORT_ORDER;
    public static int maxStroke = Constants.DEFAULT_MAX_STROKE;
    public static int minStroke = Constants.DEFAULT_MINS_TROKE;
    public static int penColor = Constants.DEFAULT_PEN_COLOR;
    public static int wallpaper = Constants.DEFAULT_WALLPAPER;
    public static String path = Constants.DEFAULT_PATH;

    public static void defaultValues() {
        sortOrder = Constants.DEFAULT_SORT_ORDER;
        maxStroke = Constants.DEFAULT_MAX_STROKE;
        minStroke = Constants.DEFAULT_MINS_TROKE;
        penColor = Constants.DEFAULT_PEN_COLOR;
        wallpaper = Constants.DEFAULT_WALLPAPER;
        path = Constants.DEFAULT_PATH;
    }

    public static void defaultPath() {
        path = Constants.DEFAULT_PATH;
    }

    /**
     * Get last time compilation app
     *
     * @param context
     * @return
     */
    public static String getAppTimeStamp(Context context) {
        String timeStamp = "";
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
            String appFile = appInfo.sourceDir;
            long time = new File(appFile).lastModified();
            DateFormat formatter = DateFormat.getDateTimeInstance();
            timeStamp = formatter.format(time);
        } catch (Exception e) {
            Log.e(Constants.TAG, e.getMessage());
        }
        return timeStamp;
    }

    /**
     * Show toats
     *
     * @param mContext
     * @param msg
     * @param duration
     */
    public static void showToast(Context mContext, String msg, int duration) {
        Toast.makeText(mContext, msg, duration).show();
    }

    /**
     * Show toats, default short duration
     *
     * @param mContext
     * @param msg
     */
    public static void showToast(Context mContext, String msg) {
        showToast(mContext, msg, Toast.LENGTH_SHORT);
    }


    public static void sort(List<ItemFile> lista, final int type) {
        Collections.sort(lista, new Comparator<ItemFile>() {
            @Override
            public int compare(ItemFile lhs, ItemFile rhs) {

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String date1 = lhs.getDate();
                String date2 = rhs.getDate();
                Date ddate = null;
                Date ddate2 = null;
                try {
                    ddate = formatter.parse(date1);
                    ddate2 = formatter.parse(date2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return ddate.compareTo(ddate2) * type;
            }
        });
    }


    public static void savePreference(Context mContext, String key, String value) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();

    }

    public static void savePreference(Context mContext, String key, int value) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();

    }

    public static void savePreference(Context mContext, String key, Boolean value) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static String loadPreference(Context mContext, String key, String value) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        return sharedPreferences.getString(key, value);

    }

    public static int loadPreference(Context mContext, String key, int value) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        return sharedPreferences.getInt(key, value);

    }

    public static Boolean loadPreference(Context mContext, String key, Boolean value) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        return sharedPreferences.getBoolean(key, value);

    }
}