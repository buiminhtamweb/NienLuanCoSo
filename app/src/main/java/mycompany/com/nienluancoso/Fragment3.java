package mycompany.com.nienluancoso;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by buimi on 1/29/2018.
 */

public class Fragment3 extends Fragment {



    public Fragment3() {
    }

    public static Fragment newIntance(){
        return new Fragment3();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3,container,false);

        return view;
    }

}
