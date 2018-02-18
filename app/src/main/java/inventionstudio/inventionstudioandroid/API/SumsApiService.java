package inventionstudio.inventionstudioandroid.API;

import java.util.List;

import inventionstudio.inventionstudioandroid.Model.Machine;
import inventionstudio.inventionstudioandroid.Model.QueueGroups;
import inventionstudio.inventionstudioandroid.Model.QueueMember;
import inventionstudio.inventionstudioandroid.Model.UserGroups;
import retrofit2.*;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Rishab K on 2/8/2018.
 */

public interface SumsApiService {

    @POST("equipmentGroup_tools")
    Call<List<Machine>> getMachineList(@Query("DepartmentID") int id,  @Query("userName") String userName, @Header("authorization") String otp);

    @POST("equipmentGroup_queueUsers")
    Call<List<QueueMember>> getQueueMembers(@Query("DepartmentID") int id, @Query("userName") String userName, @Header("authorization") String otp);

    @POST("user_info")
    Call<List<UserGroups>> getUserGroups(@Query("userName") String userName, @Header("authorization") String otp);

    @POST("equipmentGroup_queueGroups")
    Call<List<QueueGroups>> getQueueGroups(@Query("DepartmentID") int id, @Query("userName") String userName, @Header("authorization") String otp);
}