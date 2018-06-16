package base.presenter;

/**
 * Created by 高岗 on 2017/8/19.
 */
public interface IPresenter<V extends IView> {

    void attachView(V view);

    void deAttachView();

    V getIView();
}
