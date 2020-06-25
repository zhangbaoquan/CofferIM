package com.coffer.qq.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coffer.qq.R;
import com.coffer.qq.model.StarModel;
import com.helper.GlideHelper;
import com.moxun.tagcloudlib.view.TagsAdapter;

import java.util.List;

/**
 * FileName: CloudTagAdapter
 * Founder: LiuGuiLin
 * Profile: 3D星球适配器
 */
public class CloudTagAdapter extends TagsAdapter {

    private Context mContext;
    private List<StarModel> mList;
    private LayoutInflater inflater;

    public CloudTagAdapter(Context mContext, List<StarModel> mList) {
        this.mContext = mContext;
        this.mList = mList;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public View getView(Context context, int position, ViewGroup parent) {
        StarModel model = mList.get(position);
        View mView = null;
        ViewHolder viewHolder = null;
        viewHolder = new ViewHolder();
        //初始化View
        mView = inflater.inflate(com.liuguilin.framework.R.layout.layout_star_view_item, null);
        //初始化控件
        viewHolder.iv_star_icon = mView.findViewById(com.liuguilin.framework.R.id.iv_star_icon);
        viewHolder.tv_star_name = mView.findViewById(com.liuguilin.framework.R.id.tv_star_name);
        mView.setTag(viewHolder);
        if (!TextUtils.isEmpty(model.getPhotoUrl())) {
            GlideHelper.loadSmollUrl(mContext, model.getPhotoUrl(), 30, 30, viewHolder.iv_star_icon);
        } else {
            viewHolder.iv_star_icon.setImageResource(R.drawable.img_star_icon_3);
        }
        viewHolder.tv_star_name.setText(model.getNickName());
        return mView;
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getPopularity(int position) {
        return 7;
    }

    @Override
    public void onThemeColorChanged(View view, int themeColor) {

    }

    class ViewHolder {
        private ImageView iv_star_icon;
        private TextView tv_star_name;
    }
}
