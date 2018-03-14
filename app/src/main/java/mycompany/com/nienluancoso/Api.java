package mycompany.com.nienluancoso;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by buimi on 3/13/2018.
 */

public interface Api {
    String BASE_URL = "http://169.254.62.221:8080/a/";

    @GET("getdata.php")
    Call<List<AgriObject>> getData();
}
