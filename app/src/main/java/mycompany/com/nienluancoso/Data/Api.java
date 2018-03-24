package mycompany.com.nienluancoso.Data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by buimi on 3/13/2018.
 */

public interface Api {

    String BASE_URL = "http://192.168.43.161:80/a/";

    @GET("getKind.php")
    Call<List<AgriObject>> getKind();

    @GET("getAllAgri.php")
    Call<List<AgriObject>> getAllAgri();

    @GET("getHotAgri.php")
    Call<List<AgriObject>> getHotAgri();

    @GET("getNewAgri.php")
    Call<List<AgriObject>> getNewAgri();

    @GET("getHotAgri.php")
    Call<List<AgriObject>> getArgiWithKind(@Query("kind") String kind);

    @POST("SignIn")
    @FormUrlEncoded
    Call<String> login(@Field("user") String user,
                           @Field("pass") String pass);

}