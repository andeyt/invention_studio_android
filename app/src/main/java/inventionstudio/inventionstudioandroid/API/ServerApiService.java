package inventionstudio.inventionstudioandroid.API;

import inventionstudio.inventionstudioandroid.Model.AppStatus;
import inventionstudio.inventionstudioandroid.Model.GeneralFeedback;
import inventionstudio.inventionstudioandroid.Model.LoginFormObject;
import inventionstudio.inventionstudioandroid.Model.StaffFeedback;
import inventionstudio.inventionstudioandroid.Model.ToolBrokenFeedback;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by Rishab K on 2/8/2018.
 */

/**
 * Full interface for communication with our personal server
 * Information on each call is documented online: https://is-apps.me.gatech.edu/api/
 */
public interface ServerApiService {


    /**
     * Send general feedback to server
     * @param generalFeedback feedback to send to server
     * @param xapikey key for android auth
     * @param authorization authorization of the request
     * @return Call object with response of request
     */
    @POST("feedback/general")
    Call<ResponseBody> sendGeneralFeedback(@Body GeneralFeedback generalFeedback, @Header("x-api-key") String xapikey, @Header("Authorization") String authorization);


    /**
     * Send staff feedback to server
     * @param staffFeedback feedback to send to server
     * @param xapikey key for android auth
     * @param authorization authorization of the request
     * @return Call object with response of request
     */
    @POST("feedback/staff")
    Call<ResponseBody> sendStaffFeedback(@Body StaffFeedback staffFeedback, @Header("x-api-key") String xapikey, @Header("Authorization") String authorization);


    /**
     * Send tool feedback to server
     * @param toolBrokenFeedback feedback to send to server
     * @param xapikey key for android auth
     * @param authorization authorization of the request
     * @return Call object with response of request
     */
    @POST("feedback/tool_broken")
    Call<ResponseBody> sendToolFeedback(@Body ToolBrokenFeedback toolBrokenFeedback, @Header("x-api-key") String xapikey, @Header("Authorization") String authorization);

    /**
     * Send login record to server
     * @param login login to send to server
     * @param xapikey key for android auth
     * @return Call object with response of request
     */
    @POST("user/login")
    Call<ResponseBody> sendLoginRecord(@Body LoginFormObject login, @Header("x-api-key") String xapikey);

    /**
     * Getting timestamp from server
     * @return Call response with timestamp in body
     */
    @GET("server/timestamp")
    Call<ResponseBody> getTimestamp();

    /**
     * Getting app status from server
     * @return Call response with app status in body
     */
    @GET("server/app_status")
    Call<AppStatus> getAppStatus(@Header("x-api-key") String xapikey);

}