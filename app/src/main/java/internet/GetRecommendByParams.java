package internet;

import internetmodel.product.Product;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by SEUXXD on 2018/6/17.
 */

public interface GetRecommendByParams {
    @GET("api/products/actions/getRecommendProducts")
    Observable<Product[]> getProductsByParams(@Query("cbre") String cbre,
                                              @Query("cp") String cp,
                                              @Query("cs") String cs,
                                              @Query("username") String username,
                                              @Query("direction") String direction,
                                              @Query("effect") String effect,
                                              @Query("category") String category);
}
