package internet;

import internetmodel.firstlogin.LoginResult;
import io.reactivex.Observable;
import retrofit2.http.POST;

/**
 * Created by SEUXXD on 2018/6/8.
 */

public interface LoginService {
    @POST()
    Observable<LoginResult> doLogin();
}
