package inventionstudio.inventionstudioandroid.API;

import java.util.List;

import inventionstudio.inventionstudioandroid.Model.Equipment;
import inventionstudio.inventionstudioandroid.Model.QueueGroups;
import inventionstudio.inventionstudioandroid.Model.QueueMember;
import inventionstudio.inventionstudioandroid.Model.StudioDescription;
import inventionstudio.inventionstudioandroid.Model.UserGroups;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Rishab K on 2/8/2018.
 */

/**
 * Full Interface for communication with the SUMS API
 * Information on each call can be found online: https://sums.gatech.edu/SUMSAPI/rest/API/
 */
public interface SumsApiService {


    /**
     * Get list of equipment from SUMS
     * @param id department id
     * @param userName username of the user
     * @param otp one time password of the user
     * @return Call response with list of equipment
     */
    @POST("equipmentGroup_tools")
    Call<List<Equipment>> getEquipmentList(@Query("DepartmentID") int id, @Query("userName") String userName, @Header("authorization") String otp);

    /**
     * Get list of queue members from SUMS
     * @param id department id
     * @param userName username of the user
     * @param otp one time password of the user
     * @return Call response with list of queue members
     */
    @POST("equipmentGroup_queueUsers")
    Call<List<QueueMember>> getQueueMembers(@Query("DepartmentID") int id, @Query("userName") String userName, @Header("authorization") String otp);


    /**
     * Get list of user groups of a user from SUMS
     * @param userName username of the user
     * @param otp one time password of the user
     * @return Call response with list of user groups
     */
    @POST("user_info")
    Call<List<UserGroups>> getUserGroups(@Query("userName") String userName, @Header("authorization") String otp);


    /**
     * Get list of queue groups from SUMS
     * @param id department id
     * @param userName username of the user
     * @param otp one time password of the user
     * @return Call response with list of queue groups
     */
    @POST("equipmentGroup_queueGroups")
    Call<List<QueueGroups>> getQueueGroups(@Query("DepartmentID") int id, @Query("userName") String userName, @Header("authorization") String otp);


    /**
     * Get description of department
     * @param id department id
     * @return Call response with description of studio
     */
    @POST("equipmentGroup_info")
    Call<StudioDescription> getStudioDescription(@Query("equipmentGroupId") int id);
}