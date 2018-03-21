package mycompany.com.nienluancoso.Category;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import mycompany.com.nienluancoso.R;

/**
 * Created by buimi on 1/29/2018.
 */

public class Fragment2 extends Fragment {


    public Fragment2() {
    }

    public static Fragment newIntance(){
        return new Fragment2();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2,container,false);
        WebView webView = (WebView) view.findViewById(R.id.webview);
        webView.loadUrl("172.22.37.227:8080");
        return view;
    }

}
