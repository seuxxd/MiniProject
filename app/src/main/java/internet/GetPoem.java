package internet;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetPoem {
    @GET("api/poems/{sex}")
    Observable<String> getPoem(@Path("sex") String sex);
}
