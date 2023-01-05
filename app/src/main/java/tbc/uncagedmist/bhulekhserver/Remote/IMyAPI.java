package tbc.uncagedmist.bhulekhserver.Remote;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import tbc.uncagedmist.bhulekhserver.Model.State;
import tbc.uncagedmist.bhulekhserver.Model.Token;


public interface IMyAPI {

    @GET("getStates.php")
    Observable<List<State>> getStates();

    @FormUrlEncoded
    @POST("updateData.php")
    Call<String> updateData(
            @Field("url") String url,
            @Field("id") String id
    );

    @GET("getToken.php")
    Observable<List<Token>> getToken();

}
