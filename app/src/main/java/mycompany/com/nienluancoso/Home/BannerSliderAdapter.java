package mycompany.com.nienluancoso.Home;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import mycompany.com.nienluancoso.R;

public class BannerSliderAdapter extends PagerAdapter {

    Context context;
    private int[] GalImages = new int[]{
            R.drawable.backround,
            R.drawable.backround,
            R.drawable.backround
    };


    BannerSliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return GalImages.length;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
//        int padding = 8;
//        imageView.setPadding(padding, padding, padding, padding);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setImageResource(GalImages[position]);
        ((ViewPager) container).addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }
}
