package inventionstudio.inventionstudioandroid.API;

import java.util.List;
import inventionstudio.inventionstudioandroid.Model.Machine;
import retrofit2.*;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Rishab K on 2/8/2018.
 */

public interface SumsApiService {

    @POST("equipmentGroup_tools")
    Call<List<Machine>> getMachineList(@Query("DepartmentID") int id);

}
