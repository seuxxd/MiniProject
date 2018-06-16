package internet;



import internetmodel.product.Product;
import internetmodel.product.ProductList;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 获取推荐商品
 */
public interface GetRecommendProducts {
    @GET("api/products/actions/getRecommendProducts")
    Observable<Product[]> getProducts(@Query("cbre") float cbre,
                                      @Query("cp") float cp,
                                      @Query("cs") float cs,
                                      @Query("username") String username,
                                      @Query("direction") String direction);
}
