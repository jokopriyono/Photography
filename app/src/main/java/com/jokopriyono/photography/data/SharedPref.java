package com.jokopriyono.photography.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    private static final String KEY_DOWNLOAD = "sudahdownlaod";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public SharedPref(Context context) {
        int PRIVATE_MODE = 0;
        pref = context.getSharedPreferences("SharedPrefJoko", PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Set variable download true / false
     *
     * @param b boolean
     */
    public void setDownload(boolean b) {
        editor.putBoolean(KEY_DOWNLOAD, b);
        editor.commit();
    }

    /**
     * Get nilai variable download di dalam sharedpreference
     *
     * @return false jika null dan atau sudah diisi
     */
    public boolean isDownloaded() {
        return pref.getBoolean(KEY_DOWNLOAD, false);
    }
}
