package mycompany.com.nienluancoso.Category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import mycompany.com.nienluancoso.Data.AgriObject;
import mycompany.com.nienluancoso.R;

/**
 * Created by buimi on 3/13/2018.
 */

public class GridViewAdapter extends BaseAdapter {


    private Context mContext;
    private List<AgriObject> mAgriObjectList = new ArrayList<>();
    private LayoutInflater mLayoutInflater;

    public GridViewAdapter(Context mContext, List<AgriObject> mAgriObjectList) {
        this.mContext = mContext;
        this.mAgriObjectList = mAgriObjectList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mAgriObjectList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return mAgriObjectList.get(i).getID_AGRI();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (null == view) {
            view = mLayoutInflater.inflate(R.layout.item_agri, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.mImageView = (ImageView) view.findViewById(R.id.image_view);
            viewHolder.mNameAgri = (TextView) view.findViewById(R.id.tv_name_agri);
            viewHolder.mPrice = (TextView) view.findViewById(R.id.tv_price_agri);

            view.setTag(viewHolder);
        } else viewHolder = (ViewHolder) view.getTag();

        //Add data for View
       // viewHolder.mImageView.setClipToOutline(true);
        Glide.with(mContext).load("http://192.168.1.100:80/a/img/gao.jpg").into(viewHolder.mImageView);

        viewHolder.mNameAgri.setText(mAgriObjectList.get(i).getNAME_AGRI());
        viewHolder.mPrice.setText(mAgriObjectList.get(i).getPRICE_AGRI() + " VND");


        return view;
    }

    private class ViewHolder {
        private ImageView mImageView;
        private TextView mNameAgri;
        private TextView mPrice;
    }
}
