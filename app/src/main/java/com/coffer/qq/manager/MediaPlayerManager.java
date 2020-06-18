package com.coffer.qq.manager;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.utils.LogUtils;

import java.io.IOException;

/**
 * @author：张宝全
 * @date：2020/6/18
 * @Description： 媒体播放
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */

public class MediaPlayerManager {

    private static final String TAG = "MediaPlayerManager_TAG";

    /**
     * 播放状态码
     */
    public static final int MEDIA_STATUS_PLAY = 0;
    /**
     * 暂停状态码
     */
    public static final int MEDIA_STATUS_PAUSE = 1;
    /**
     * 停止状态码
     */
    public static final int MEDIA_STATUS_STOP = 2;

    /**
     * 当前状态码，默认是暂停
     */
    public int mCurrentStatus = MEDIA_STATUS_PAUSE;

    private MediaPlayer mMediaPlayer;

    public MediaPlayerManager(){
        mMediaPlayer = new MediaPlayer();
    }

    /**
     * 是否正在播放
     * @return 状态
     */
    public boolean isPlaying(){
        return mMediaPlayer.isPlaying();
    }

    /**
     * 播放
     * @param path 视频资源路径
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void startPlay(AssetFileDescriptor path){
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(path);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
            mCurrentStatus = MEDIA_STATUS_PLAY;
        } catch (IOException e) {
            LogUtils.e(TAG,e.toString());
            e.printStackTrace();
        }
    }

    /**
     * 暂停播放
     */
    public void pausePlay(){
        if (isPlaying()){
            mMediaPlayer.pause();
            mCurrentStatus = MEDIA_STATUS_PAUSE;
        }
    }

    /**
     * 继续播放
     */
    public void continuePlay(){
        mMediaPlayer.start();
        mCurrentStatus = MEDIA_STATUS_PLAY;
    }

    /**
     * 停止播放
     */
    public void stopPlay(){
        mMediaPlayer.stop();
        mCurrentStatus = MEDIA_STATUS_STOP;
    }

}
