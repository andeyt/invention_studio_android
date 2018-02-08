
package inventionstudio.inventionstudioandroid.API;

/**
 * Created by Maxwell on 2/5/2018.
 */

public class APIController {

    private static final APIController ourInstance = new APIController();

    static APIController getInstance() {
        return ourInstance;
    }

    private APIController() {}

}

