package mycompany.com.nienluancoso.Data;

import java.util.List;

import mycompany.com.nienluancoso.Constant;
import mycompany.com.nienluancoso.DetailAgri.AgriObjectDetail;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by buimi on 3/13/2018.
 */

public interface Api {

    String BASE_URL = Constant.HOST;

    @GET("getKind.php")
    Call<List<KindObject>> getKind();

    @GET("getAllAgri.php")
    Call<List<AgriObjectItem>> getAllAgri();

    @GET("getHotAgri.php")
    Call<List<AgriObjectItem>> getHotAgri();

    @GET("getNewAgri.php")
    Call<List<AgriObjectItem>> getNewAgri();



    @GET("getAllAgri.php")
    Call<List<AgriObjectItem>> getDetailAgric();

    @POST("getArgiWithKind.php")
    @FormUrlEncoded
    Call<List<AgriObjectItem>> getArgiWithKind(@Field("ID_KIND") String kind);

    @POST("getDetailAgric.php")
    @FormUrlEncoded
    Call<AgriObjectDetail> getArgiDetail(@Field("ID_AGRI") String idAgric);

    @POST("signin_dangnhap.php")
    @FormUrlEncoded
    Call<String> login(@Field("USERNAME_CUS") String user,
                           @Field("PASSWORD_CUS") String pass);

    @POST("signin_checkUserName.php")
    @FormUrlEncoded
    Call<String> checkUserName(@Field("USERNAME_CUS") String userName);

    @POST("signin_dangky.php")
    @FormUrlEncoded
    Call<String> taoTaiKhoan_KH(@Field("USERNAME_CUS") String userName,
                                @Field("PASSWORD_CUS") String passwd,
                                @Field("FULLNAME_CUS") String fullName,
                                @Field("SEX") String sex,
                                @Field("BIRTHDAY") String birthDay,
                                @Field("IMG_URL_CUS") String imgUrl,
                                @Field("TEL_CUS") String tel,
                                @Field("ADDRESS_CUS") String address);

}