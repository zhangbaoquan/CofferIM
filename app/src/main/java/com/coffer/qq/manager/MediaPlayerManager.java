package com.coffer.qq.manager;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
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

    private static final int H_PROGRESS = 100;

    private MediaPlayer mMediaPlayer;

    private OnMusicProgressListener mProgressListener;

    private Handler mHandler;

    /**
     * 计算播放进度
     */
    public MediaPlayerManager(){
        mMediaPlayer = new MediaPlayer();
        mHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == H_PROGRESS && mProgressListener != null){
                    // 获取当前的播放进度
                    int progress = getCurrentPosition();
                    int pos = (int) (((float)progress) / ((float) getDuration()) * 100);
                    mProgressListener.onProgress(progress,pos);
                    // 每隔1s 向外抛进度
                    mHandler.sendEmptyMessageAtTime(H_PROGRESS,1000);
                }
            }
        };
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
            mHandler.sendEmptyMessage(H_PROGRESS);
        } catch (IOException e) {
            LogUtils.e(TAG,e.toString());
            e.printStackTrace();
        }
    }

    /**
     * 播放
     * @param path 视频资源路径
     */
    public void startPlay(String path){
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(path);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
            mCurrentStatus = MEDIA_STATUS_PLAY;
            mHandler.sendEmptyMessage(H_PROGRESS);
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
            mHandler.removeMessages(H_PROGRESS);
        }
    }

    /**
     * 继续播放
     */
    public void continuePlay(){
        mMediaPlayer.start();
        mCurrentStatus = MEDIA_STATUS_PLAY;
        mHandler.sendEmptyMessage(H_PROGRESS);
    }

    /**
     * 停止播放
     */
    public void stopPlay(){
        mMediaPlayer.stop();
        mCurrentStatus = MEDIA_STATUS_STOP;
        mHandler.removeMessages(H_PROGRESS);
    }

    /**
     * 设置是否循环
     */
    public void setLooping(boolean loop){
        mMediaPlayer.setLooping(loop);
    }

    /**
     * 设置跳转的位置
     * @param msec 时间点
     */
    public void seekTo(int msec){
        mMediaPlayer.seekTo(msec);
    }

    /**
     * 获取当前的位置
     */
    public int getCurrentPosition(){
        return mMediaPlayer.getCurrentPosition();
    }

    /**
     * 获取总时长
     */
    public int getDuration(){
        return mMediaPlayer.getDuration();
    }

    /**
     * 设置播放完成的监听
     */
    public void setOnCompleteListener(MediaPlayer.OnCompletionListener listener){
        mMediaPlayer.setOnCompletionListener(listener);
    }

    /**
     * 设置播放错误的监听
     */
    public void setOnErrorListener(MediaPlayer.OnErrorListener listener){
        mMediaPlayer.setOnErrorListener(listener);
    }

    /**
     * 获取播放进度
     * @param listener 监听
     */
    public void setProgressListener(OnMusicProgressListener listener){
        this.mProgressListener = listener;
    }

    public interface OnMusicProgressListener{
        void onProgress(int progress,int pos);
    }
}
