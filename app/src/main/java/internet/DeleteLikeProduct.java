package internet;

import internetmodel.register.RegisterResult;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DeleteLikeProduct {
    @FormUrlEncoded
    @POST("api/products/actions/removeUserFavoriteProduct")
    Observable<RegisterResult> deleteProduct(@Field("username") String username,
                                             @Field("productID") String productId);
}
