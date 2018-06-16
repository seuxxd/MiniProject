package internet;

import internetmodel.register.RegisterResult;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CheckHasUserService {
    @FormUrlEncoded
    @POST("api/users/actions/login")
    Observable<RegisterResult> getHasUserInfo(@Field("username") String username);
}
