package internet;

import internetmodel.diary.DiaryInfo;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetSkinState {
    @GET("api/diarys/actions/getHistoryDiarys")
    Observable<DiaryInfo[]> getSkinState(@Query("username") String username,
                                         @Query("days") String days);
}
