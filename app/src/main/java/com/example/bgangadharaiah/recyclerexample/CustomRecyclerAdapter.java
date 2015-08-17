package com.example.bgangadharaiah.recyclerexample;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Bgangadharaiah on 8/12/2015.
 */
public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.CustomViewHolder> {

    private List<ApplicationInfo> mAppInfo;
    private Context mContext;
    private PackageManager mPackageManager;

    public CustomRecyclerAdapter(Context context, List<ApplicationInfo> list){
        super();
        this.mAppInfo = list;
        this.mContext = context;
        mPackageManager = context.getPackageManager();
    }

    @Override
    public int getItemCount() {
        return mAppInfo.size();
    }

    @Override
    public CustomRecyclerAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        ApplicationInfo info = mAppInfo.get(position);
        holder.mTextViewAppName.setText(info.loadLabel(mPackageManager));
        holder.mTextViewPackageName.setText(info.packageName);
        holder.mImageViewAppIcon.setImageDrawable(info.loadIcon(mPackageManager));

    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView mImageViewAppIcon;
        protected TextView mTextViewAppName;
        protected TextView mTextViewPackageName;

        public CustomViewHolder(View view) {
            super(view);
            this.mImageViewAppIcon = (ImageView) view.findViewById(R.id.app_icon);
            this.mTextViewAppName = (TextView) view.findViewById(R.id.app_name);
            this.mTextViewPackageName = (TextView) view.findViewById(R.id.package_name);
        }
    }
}






