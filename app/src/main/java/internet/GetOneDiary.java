package internet;

import internetmodel.diary.DiaryInfo;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetOneDiary {
    @GET("api/diarys/actions/getUserDiaryByDate")
    Observable<DiaryInfo> getOneDiary(@Query("username") String username,
                                      @Query("date") String date);
}
