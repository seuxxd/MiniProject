package internet;

import internetmodel.register.RegisterResult;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RegisterService {
    @FormUrlEncoded
    @POST("api/users/actions/register")
    Observable<RegisterResult> firstLogin(@Field("username")  String username,
                                          @Field("password")  String password,
                                          @Field("nickname")  String nickname,
                                          @Field("sex")       String sex,
                                          @Field("birthday")  String birthday);
}
