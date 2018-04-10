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
import mycompany.com.nienluancoso.Data.AgriItemObject;
import mycompany.com.nienluancoso.R;

/**
 * Created by buimi on 3/13/2018.
 */

public class GridViewAdapter extends BaseAdapter {

    private List<AgriItemObject> mAgriItemObjectList;
    private LayoutInflater mLayoutInflater;

    public GridViewAdapter(Context mContext, List<AgriItemObject> mAgriItemObjectList) {
        this.mAgriItemObjectList = mAgriItemObjectList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mAgriItemObjectList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return mAgriItemObjectList.get(i).getID_AGRI();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (null == view) {
            view = mLayoutInflater.inflate(R.layout.item_agri, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.mImageView = (ImageView) view.findViewById(R.id.image_view);
            viewHolder.mNameAgri = (TextView) view.findViewById(R.id.tv_name_agri);
            viewHolder.mOldPrice = (TextView) view.findViewById(R.id.tv_old_price_agri);
            viewHolder.mPrice = (TextView) view.findViewById(R.id.tv_price_agri);

            view.setTag(viewHolder);
        } else viewHolder = (ViewHolder) view.getTag();

        //Add data for View
        // viewHolder.mImageView.setClipToOutline(true);
        Picasso.get().load(Constant.IMAGE_SOURCE + mAgriItemObjectList.get(i).getIMG_URL_AGRI()).into(viewHolder.mImageView);

        viewHolder.mNameAgri.setText(mAgriItemObjectList.get(i).getNAME_AGRI());

        //Hiện giá cũ lên nếu có giá cũ
        if (null != mAgriItemObjectList.get(i).getoLDPRICE()){
            String gachNgang = "<strike>"+mAgriItemObjectList.get(i).getoLDPRICE()+"  VND</strike>";
            viewHolder.mOldPrice.setText(android.text.Html.fromHtml(gachNgang));
        }else viewHolder.mOldPrice.setVisibility(View.GONE);

        viewHolder.mPrice.setText(mAgriItemObjectList.get(i).getPRICE_AGRI() + " VND");


        return view;
    }

    private class ViewHolder {
        private ImageView mImageView;
        private TextView mNameAgri;
        private TextView mPrice, mOldPrice;
    }
}
