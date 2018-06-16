package personal.contact;


import base.presenter.IView;

/**
 * Created by mr.gao on 2018/6/13.
 * Package:    com.mrwho.skindetection.modules.personal.contact
 * Create Date:2018/6/13
 * Project Name:SkinDetection
 * Description:
 */

public interface LookView extends IView {

    void showDialog();

    void dissmisDialog();

    void showCenterPic(String path);


    void showHeaderView(String path);


    void showHowToUse();
}
