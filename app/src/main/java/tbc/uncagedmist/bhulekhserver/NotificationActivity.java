package tbc.uncagedmist.bhulekhserver;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tbc.uncagedmist.bhulekhserver.Common.Common;
import tbc.uncagedmist.bhulekhserver.Model.State;
import tbc.uncagedmist.bhulekhserver.Model.Token;
import tbc.uncagedmist.bhulekhserver.Notification.DataMessage;
import tbc.uncagedmist.bhulekhserver.Notification.IFCMService;
import tbc.uncagedmist.bhulekhserver.Notification.MyResponse;
import tbc.uncagedmist.bhulekhserver.Remote.IMyAPI;

public class NotificationActivity extends AppCompatActivity {

    EditText edtTitle, edtDesc,edtImage;

    IFCMService ifcmService;

    IMyAPI myAPI;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        myAPI = Common.getAPI();
        ifcmService = Common.getNotificationAPI();

        edtTitle = findViewById(R.id.edtTitle);
        edtDesc = findViewById(R.id.edtDesc);
        edtImage = findViewById(R.id.edtImage);

        findViewById(R.id.btnSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = edtTitle.getText().toString().trim();
                String desc = edtDesc.getText().toString().trim();
                String image = edtImage.getText().toString().trim();

                if (TextUtils.isEmpty(title))   {
                    Toast.makeText(NotificationActivity.this, "enter title", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(desc))   {
                    Toast.makeText(NotificationActivity.this, "enter description", Toast.LENGTH_SHORT).show();
                }
                else {
                    sendNotification(title,desc,image);
                }
            }
        });



    }

    private void sendNotification(String title, String desc,String image) {

        final String[] id = {""};

        compositeDisposable.add(
                myAPI.getToken()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<List<Token>>() {
                                       @Override
                                       public void accept(List<Token> tokens) throws Exception {
                                           DataMessage dataMessage = new DataMessage();

                                           Map<String,String> dataSend = new HashMap<>();
                                           dataSend.put("title",title);
                                           dataSend.put("message",desc);
                                           dataSend.put("image",image);

                                           for (int i = 0; i < tokens.size(); i++) {
                                               id[0] = tokens.get(i).token;
                                           }

                                           dataMessage.to = id[0];
                                           dataMessage.setData(dataSend);



                                           ifcmService.sendNotification(dataMessage)
                                                   .enqueue(new Callback<MyResponse>() {
                                                       @Override
                                                       public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                                           if (response.body().success == 1)   {
                                                               Toast.makeText(NotificationActivity.this, "Notification Sent", Toast.LENGTH_SHORT).show();
                                                               finish();
                                                           }
                                                       }

                                                       @Override
                                                       public void onFailure(Call<MyResponse> call, Throwable t) {
                                                           Toast.makeText(NotificationActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                                       }
                                                   });
                                       }
                                   }
                        )
        );

    }

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        compositeDisposable.clear();
        super.onDestroy();
    }
}