package internet;

import internetmodel.register.RegisterResult;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AddLikeProduct {
    @FormUrlEncoded
    @POST("api/products/actions/addUserFavoriteProduct")
    Observable<RegisterResult> addLike(@Field("username") String username,
                                       @Field("productID") String productID);
}
