package internet;

import internetmodel.mycomment.CommentResult;
import internetmodel.mycomment.UploadComment;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UploadCommentService {
    @FormUrlEncoded
    @POST("api/comments/actions/addProductComments")
    Observable<CommentResult> doUploadComment(@Field("username") String username,
                                              @Field("productID") String productId,
                                              @Field("recommendLevel") String level,
                                              @Field("recommendContent") String content);
}
