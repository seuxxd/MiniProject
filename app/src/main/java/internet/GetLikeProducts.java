package internet;

import internetmodel.product.Product;
import internetmodel.product.UserLikeProduct;
import internetmodel.product.UserLikeProducts;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetLikeProducts {
    @GET("api/products/actions/getUserFavoriteProducts")
    Observable<Product[]> getLikeProducts(@Query("username") String username);
}
