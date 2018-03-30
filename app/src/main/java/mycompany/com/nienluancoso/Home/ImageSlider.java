package mycompany.com.nienluancoso.Home;

import android.support.annotation.DrawableRes;

public class ImageSlider {

    private String name;

    //optional @DrawableRes
    @DrawableRes
    private int resId;

    public ImageSlider(String name, int resId) {
        this.name = name;
        this.resId = resId;
    }

    @Override
    public String toString() {
        return name;
    }

    //getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
