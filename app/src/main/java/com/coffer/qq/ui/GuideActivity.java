package com.coffer.qq.ui;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;

import com.coffer.qq.R;
import com.framework.adapter.BasePageAdapter;
import com.framework.base.BaseUIActivity;
import com.framework.manager.MediaPlayerManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author：张宝全
 * @date：2020/6/19
 * @Description：引导页
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */

public class GuideActivity extends BaseUIActivity implements View.OnClickListener {

    private ImageView iv_music_switch;
    private TextView tv_guide_skip;
    private ImageView iv_guide_point_1;
    private ImageView iv_guide_point_2;
    private ImageView iv_guide_point_3;
    private ViewPager mViewPager;

    /**
     * 1.ViewPager : 适配器|帧动画播放
     * 2.小圆点的逻辑
     * 3.歌曲的播放
     * 4.属性动画旋转
     * 5.跳转
     */

    private View view1;
    private View view2;
    private View view3;

    private List<View> mPageList = new ArrayList<>();
    private BasePageAdapter mPageAdapter;

    private ImageView iv_guide_star;
    private ImageView iv_guide_night;
    private ImageView iv_guide_smile;

    private MediaPlayerManager mGuideMusic;

    private ObjectAnimator mAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        initView();
    }

    private void initView() {
        iv_music_switch = findViewById(R.id.iv_music_switch);
        tv_guide_skip = findViewById(R.id.tv_guide_skip);
        iv_guide_point_1 = findViewById(R.id.iv_guide_point_1);
        iv_guide_point_2 = findViewById(R.id.iv_guide_point_2);
        iv_guide_point_3 = findViewById(R.id.iv_guide_point_3);
        mViewPager = findViewById(R.id.mViewPager);

        iv_music_switch.setOnClickListener(this);
        tv_guide_skip.setOnClickListener(this);

        view1 = View.inflate(this, R.layout.layout_pager_guide_1, null);
        view2 = View.inflate(this, R.layout.layout_pager_guide_2, null);
        view3 = View.inflate(this, R.layout.layout_pager_guide_3, null);

        mPageList.add(view1);
        mPageList.add(view2);
        mPageList.add(view3);

        mPageAdapter = new BasePageAdapter(mPageList);
        mViewPager.setAdapter(mPageAdapter);

        //帧动画
        iv_guide_star = view1.findViewById(R.id.iv_guide_star);
        iv_guide_night = view2.findViewById(R.id.iv_guide_night);
        iv_guide_smile = view3.findViewById(R.id.iv_guide_smile);

        //播放帧动画
        AnimationDrawable animStar = (AnimationDrawable) iv_guide_star.getBackground();
        animStar.start();

        AnimationDrawable animNight = (AnimationDrawable) iv_guide_night.getBackground();
        animNight.start();

        AnimationDrawable animSmile= (AnimationDrawable) iv_guide_smile.getBackground();
        animSmile.start();

        // 小圆点的逻辑
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                seletePoint(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //歌曲的逻辑
        startMusic();
    }

    private void startMusic() {
        mGuideMusic = new MediaPlayerManager();
        mGuideMusic.setLooping(true);
        AssetFileDescriptor assetFileDescriptor = getResources().openRawResourceFd(R.raw.guide);
        mGuideMusic.startPlay(assetFileDescriptor);

        // 属性动画
        mAnim = ObjectAnimator.ofFloat(iv_music_switch,"rotation",0f,360f);
        mAnim.setDuration(2000);
        mAnim.setRepeatMode(ValueAnimator.RESTART);
        mAnim.setRepeatCount(ValueAnimator.INFINITE);
        mAnim.start();
    }

    /**
     * 动态选择小圆点
     * @param position 位置
     */
    private void seletePoint(int position) {
        switch (position){
            case 0:
                iv_guide_point_1.setImageResource(R.drawable.img_guide_point_p);
                iv_guide_point_2.setImageResource(R.drawable.img_guide_point);
                iv_guide_point_3.setImageResource(R.drawable.img_guide_point);
                break;
            case 1:
                iv_guide_point_1.setImageResource(R.drawable.img_guide_point);
                iv_guide_point_2.setImageResource(R.drawable.img_guide_point_p);
                iv_guide_point_3.setImageResource(R.drawable.img_guide_point);
                break;
            case 2:
                iv_guide_point_1.setImageResource(R.drawable.img_guide_point);
                iv_guide_point_2.setImageResource(R.drawable.img_guide_point);
                iv_guide_point_3.setImageResource(R.drawable.img_guide_point_p);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_music_switch:
                if(mGuideMusic.mCurrentStatus == MediaPlayerManager.MEDIA_STATUS_PAUSE){
                    mAnim.start();
                    mGuideMusic.continuePlay();
                    iv_music_switch.setImageResource(R.drawable.img_guide_music);
                }else if(mGuideMusic.mCurrentStatus == MediaPlayerManager.MEDIA_STATUS_PLAY){
                    mAnim.pause();
                    mGuideMusic.pausePlay();
                    iv_music_switch.setImageResource(R.drawable.img_guide_music_off);
                }
                break;
            case R.id.tv_guide_skip:
                startActivity(new Intent(this,LoginActivity.class));
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGuideMusic.stopPlay();
    }
}