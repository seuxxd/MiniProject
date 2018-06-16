package internet;

import internetmodel.register.RegisterResult;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AddDiaryService {
    @FormUrlEncoded
    @POST("api/diarys/actions/addUserDiary")
    Observable<RegisterResult> addDiary(@Field("username") String username,
                                        @Field("food") String food,
                                        @Field("sport") String sport,
                                        @Field("others") String others,
                                        @Field("emotion") String emotion,
                                        @Field("feeling") String feeling,
                                        @Field("cbre") String cbre,
                                        @Field("cp") String cp,
                                        @Field("cs") String cs,
                                        @Field("skinState") String skinState,
                                        @Field("date") String date);

}
