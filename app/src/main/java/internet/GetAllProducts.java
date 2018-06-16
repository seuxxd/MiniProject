package internet;

import internetmodel.product.ProductList;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface GetAllProducts {
    @GET("api/products")
    Observable<ProductList> getAllProducts();
}
