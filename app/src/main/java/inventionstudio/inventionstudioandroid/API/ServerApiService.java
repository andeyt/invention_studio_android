package inventionstudio.inventionstudioandroid.API;

import java.util.List;

import inventionstudio.inventionstudioandroid.Model.GeneralFeedback;
import inventionstudio.inventionstudioandroid.Model.LoginFormObject;
import inventionstudio.inventionstudioandroid.Model.Machine;
import inventionstudio.inventionstudioandroid.Model.PIFeedback;
import inventionstudio.inventionstudioandroid.Model.QueueGroups;
import inventionstudio.inventionstudioandroid.Model.QueueMember;
import inventionstudio.inventionstudioandroid.Model.ToolBrokenFeedback;
import inventionstudio.inventionstudioandroid.Model.UserGroups;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Rishab K on 2/8/2018.
 */

public interface ServerApiService {

    @POST("feedback/general")
    Call<ResponseBody> sendGeneralFeedback(@Body GeneralFeedback generalFeedback, @Header("x-api-key") String xapikey, @Header("Authorization") String authorization);

    @POST("feedback/staff")
    Call<ResponseBody> sendPIFeedback(@Body PIFeedback generalFeedback, @Header("x-api-key") String xapikey, @Header("Authorization") String authorization);

    @POST("feedback/tool_broken")
    Call<ResponseBody> sendToolFeedback(@Body ToolBrokenFeedback generalFeedback, @Header("x-api-key") String xapikey, @Header("Authorization") String authorization);

    @POST("user/login")
    Call<ResponseBody> sendLoginRecord(@Body LoginFormObject login, @Header("x-api-key") String xapikey);

    @GET("server/app_status")
    Call<ResponseBody> getTimestamp(@Header("x-api-key") String xapikey);

}