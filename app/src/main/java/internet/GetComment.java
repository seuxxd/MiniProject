package internet;

import internetmodel.mycomment.UploadComment;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetComment {
    @GET("/api/comments/actions/getProductComments")
    Observable<UploadComment[]> getComments(@Query("productID") String id);
}
