package internet;



import internetmodel.product.RecommendProductList;
import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * 获取推荐商品
 */
public interface GetRecommendProducts {
    @GET("products/recommands")
    Observable<RecommendProductList> getProducts();
}
