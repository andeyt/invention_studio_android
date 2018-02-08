package inventionstudio.inventionstudioandroid.API;

import java.util.List;

import inventionstudio.inventionstudioandroid.Model.Machine;
import inventionstudio.inventionstudioandroid.Model.QueueMember;
import retrofit2.*;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Rishab K on 2/8/2018.
 */

public interface SumsApiService {

    @POST("equipmentGroup_tools?DepartmentID=8")
    Call<List<Machine>> getMachineList();

    @POST("equipmentGroup_queues")
    Call<List<QueueMember>> getQueueLists(@Query("DepartmentID") int id, @Query("userName") String userName);
}