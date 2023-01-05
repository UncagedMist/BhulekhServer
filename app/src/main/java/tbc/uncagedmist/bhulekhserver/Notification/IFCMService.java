package tbc.uncagedmist.bhulekhserver.Notification;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IFCMService {

    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAx8D3FeA:APA91bHjvwtXXMGbdBAbk1laAEmXr0-3d6krz1grpfCMkLQLPe3rMtVlPtSPe9J8E7pl0jfNeamth0uPXdHKJ6VwIw03wKsk1CCx3As5-xT4cLOyeQIW_h4-xshcd7QelvNtk-WPRcqR"
    })
    @POST("fcm/send")
    Call<MyResponse> sendNotification(
            @Body DataMessage body
    );
}
