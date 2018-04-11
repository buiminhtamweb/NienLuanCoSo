package mycompany.com.nienluancoso.Data;
import java.util.List;

import mycompany.com.nienluancoso.Constant;
import mycompany.com.nienluancoso.Data.Local.AgricLiteObject;
import mycompany.com.nienluancoso.DetailAgri.AgriDetailObject;
import mycompany.com.nienluancoso.Order.BillObject;
import mycompany.com.nienluancoso.Order.OrderObject;
import mycompany.com.nienluancoso.User.UserCusObject;
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
    Call<List<AgriItemObject>> getAllAgri();

    @GET("getHotAgri.php")
    Call<List<AgriItemObject>> getHotAgri();

    @GET("getCheapAgri.php")
    Call<List<AgriItemObject>> getCheapAgri();

    @GET("getSaleAgri.php")
    Call<List<AgriItemObject>> getSaleAgri();


    @POST("getArgiWithKind.php")
    @FormUrlEncoded
    Call<List<AgriItemObject>> getArgiWithKind(@Field("ID_KIND") String kind);

    @POST("getDetailAgric.php")
    @FormUrlEncoded
    Call<AgriDetailObject> getArgiDetail(@Field("ID_AGRI") String idAgric);

    @POST("getAgricLite.php")
    @FormUrlEncoded
    Call<AgricLiteObject> getArgiLite(@Field("ID_AGRI") String idAgric);

    //Đăng nhập
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


    //Thông tin người dùng
    @POST("user_getInfo.php")
    @FormUrlEncoded
    Call<UserCusObject> getInfoUser(@Field("USERNAME_CUS") String userName);

    @POST("user_changePassWd.php")
    @FormUrlEncoded
    Call<String> getChangePassUser(@Field("USERNAME_CUS") String userName,
                                      @Field("OLD_PASSWORD") String oldPass,
                                      @Field("NEW_PASSWORD") String newPassWd);

    @POST("user_updateUser.php")
    @FormUrlEncoded
    Call<String> updateUser(@Field("USERNAME_CUS") String userName,
                                          @Field("TYPE") int type,
                                          @Field("DATA") String data);


    //Đặt hàng
    @POST("order_viewBillList.php")
    @FormUrlEncoded
    Call<List<BillObject>> getBillList(@Field("USERNAME_CUS") String user);

    @POST("order_viewOrderList.php")
    @FormUrlEncoded
    Call<List<OrderObject>> getOrderProcessing(@Field("USERNAME_CUS") String user);

    @POST("order_addNewOrder.php")
    @FormUrlEncoded
    Call<String> uploadOrder(@Field("DON_HANG") String json);

}