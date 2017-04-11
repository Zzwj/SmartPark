package com.xcoder.smartpark.util;


import android.graphics.Bitmap;

import com.xcoder.lib.app.AppPathManager;

import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by xcoder_xz on 2016/12/20 0020.
 * 分享时所用到的bitmap保存
 */

public class ShareImageUtils {

    /**
     * 保存图片
     *
     * @param bit     源图片
     * @param recycle 是否回收
     * @return {@code true}: 成功<br>{@code false}: 失败
     * @seefile 要保存到的文件
     * @seeformat格式
     */
    public static File save(Bitmap bit, boolean recycle) {
        File file =null;
        if (bit == null || bit.getWidth() == 0 || bit.getHeight() == 0)
            return null;
        File dir = new File(AppPathManager.getShareImagePath());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        file = new File(dir, AppPathManager.getImageName());
        OutputStream os = null;
        boolean ret = false;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file));
            ret = bit.compress(Bitmap.CompressFormat.JPEG,80, os);
            if (recycle && !bit.isRecycled()) bit.recycle();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeIO(os);
        }
        return file;
    }


    /**
     * 关闭IO
     *
     * @param closeables closeable
     */
    public static void closeIO(Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
