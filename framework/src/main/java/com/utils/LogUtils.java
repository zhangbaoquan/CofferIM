package com.utils;

import android.util.Log;

import com.framework.BuildConfig;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.Constants.FILE_NAME;
import static com.Constants.LOG_FILE_NAME;

/**
 * @author：张宝全
 * @date：2020-02-02
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription： 打印日志、记录日志
 */
public class LogUtils {

    private static SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

    public static void i(String tag,String content){
        if (BuildConfig.DEBUG){
            Log.i(tag,content);
        }
    }

    public static void e(String tag,String content){
        if (BuildConfig.DEBUG){
            Log.e(tag,content);
        }
    }

    public static void writeToFile(String content){
        // 时间 + 内容
        String log = mSimpleDateFormat.format(new Date()) + " ， "+content+"\n";
        // 检测父路径
        File fileGroup = new File(FILE_NAME);
        if (!fileGroup.exists()){
            fileGroup.mkdirs();
        }
        // 写日志
        FileOutputStream fileOutputStream = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileOutputStream = new FileOutputStream(LOG_FILE_NAME,true);
            // 编码问题，需要正确的存储中文
            bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(fileOutputStream, Charset.forName("gbk")));
            bufferedWriter.write(log);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (bufferedWriter != null){
                try {
                    bufferedWriter.close();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
