package inventionstudio.inventionstudioandroid.API;

import java.util.List;
import inventionstudio.inventionstudioandroid.Model.Machines;
import retrofit2.*;
import retrofit2.http.POST;

/**
 * Created by Rishab K on 2/8/2018.
 */

public interface SumsApiService {

    @POST("equipmentGroup_tools?DepartmentID=8")
    Call<List<Machines>> getMachineList();
}
