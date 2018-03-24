package mycompany.com.nienluancoso.Home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import mycompany.com.nienluancoso.Data.AgriObject;
import mycompany.com.nienluancoso.R;

/**
 * Created by Admin on 3/21/2018.
 */

public class RecylerViewAdapter extends RecyclerView.Adapter<RecylerViewAdapter.ViewHolder> {


    private Context mContext;
    private List<AgriObject> mAgriObjectList = new ArrayList<>();

    public RecylerViewAdapter(Context mContext, List<AgriObject> mAgriObjectList) {
        this.mContext = mContext;
        this.mAgriObjectList = mAgriObjectList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_agri, parent, false);

        return new ViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(mContext).load("http://192.168.43.161:80/a/img/gao.jpg").into(holder.mImageView);

        holder.mNameAgri.setText(mAgriObjectList.get(position).getNAME_AGRI());
        holder.mPrice.setText(mAgriObjectList.get(position).getPRICE_AGRI() + " VND");

    }

    @Override
    public int getItemCount() {
        return mAgriObjectList.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public TextView mNameAgri;
        public TextView mPrice;

        public ViewHolder(View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.image_view);
            mNameAgri = (TextView) view.findViewById(R.id.tv_name_agri);
            mPrice = (TextView) view.findViewById(R.id.tv_price_agri);
        }
    }
}
