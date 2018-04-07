package mycompany.com.nienluancoso.Category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import mycompany.com.nienluancoso.Data.KindObject;
import mycompany.com.nienluancoso.R;

/**
 * Created by buimi on 3/13/2018.
 */

public class LvKindAdapter extends BaseAdapter {

    private List<KindObject> kindObjects;
    private LayoutInflater mLayoutInflater;

    public LvKindAdapter(Context mContext, List<KindObject> mAgriObjectList) {
        this.kindObjects = mAgriObjectList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return kindObjects.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return Long.parseLong(kindObjects.get(i).getIDKIND());
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (null == view) {
            view = mLayoutInflater.inflate(R.layout.item_kind_agric, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.mNameKind = (TextView) view.findViewById(R.id.tv_kind);
            view.setTag(viewHolder);
        } else viewHolder = (ViewHolder) view.getTag();

        //Add data for View
        viewHolder.mNameKind.setText(kindObjects.get(i).getNAMEKIND());

        return view;
    }

    private class ViewHolder {
        private TextView mNameKind;
    }
}
