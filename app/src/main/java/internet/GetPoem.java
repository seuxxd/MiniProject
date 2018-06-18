package internet;

import internetmodel.poem.Poem;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetPoem {
    @GET("api/poems")
    Observable<Poem> getPoem(@Query("sex") String sex);
}
