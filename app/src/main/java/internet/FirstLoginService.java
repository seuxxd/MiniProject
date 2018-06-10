package internet;

import internetmodel.firstlogin.LoginResult;
import internetmodel.firstlogin.User;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface FirstLoginService {
    @POST("users/actions/register")
    Observable<LoginResult> firstLogin(@Body User user);
}
