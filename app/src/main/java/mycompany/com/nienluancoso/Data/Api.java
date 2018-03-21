package mycompany.com.nienluancoso.Data;

import java.util.List;

import mycompany.com.nienluancoso.Home.AgriObject;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by buimi on 3/13/2018.
 */

public interface Api {

    String BASE_URL = "http://192.168.1.100:80/a/";

    @GET("getKind.php")
    Call<List<AgriObject>> getKind();

    @GET("getAllAgri.php")
    Call<List<AgriObject>> getAllAgri();

    @GET("getHotAgri.php")
    Call<List<AgriObject>> getHotAgri();

    @GET("getNewAgri.php")
    Call<List<AgriObject>> getNewAgri();


}
