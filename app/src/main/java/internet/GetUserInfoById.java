package internet;

import internetmodel.login.LoginResult;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetUserInfoById {
    @GET("api/users/me")
    Observable<LoginResult> getInfo(@Query("username") String username);
}
