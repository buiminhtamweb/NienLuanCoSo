package mycompany.com.nienluancoso.Category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import mycompany.com.nienluancoso.Constant;
import mycompany.com.nienluancoso.Data.AgriObjectItem;
import mycompany.com.nienluancoso.R;

/**
 * Created by buimi on 3/13/2018.
 */

public class GridViewAdapter extends BaseAdapter {

    private List<AgriObjectItem> mAgriObjectItemList;
    private LayoutInflater mLayoutInflater;

    public GridViewAdapter(Context mContext, List<AgriObjectItem> mAgriObjectItemList) {
        this.mAgriObjectItemList = mAgriObjectItemList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mAgriObjectItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return mAgriObjectItemList.get(i).getID_AGRI();
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
        Picasso.get().load(Constant.IMAGE_SOURCE + mAgriObjectItemList.get(i).getIMG_URL_AGRI()).into(viewHolder.mImageView);

        viewHolder.mNameAgri.setText(mAgriObjectItemList.get(i).getNAME_AGRI());
        viewHolder.mPrice.setText(mAgriObjectItemList.get(i).getPRICE_AGRI() + " VND");


        return view;
    }

    private class ViewHolder {
        private ImageView mImageView;
        private TextView mNameAgri;
        private TextView mPrice;
    }
}
