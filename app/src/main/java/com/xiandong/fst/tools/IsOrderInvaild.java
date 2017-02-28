package com.xiandong.fst.tools;

import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.bean.AbsBaseBean;
import com.xiandong.fst.model.bean.OrderDetailsMsgBean;
import com.xiandong.fst.presenter.OrderDetailsPresenterImpl;
import com.xiandong.fst.utils.GsonUtil;
import com.xiandong.fst.utils.StringUtil;
import com.xiandong.fst.view.OrderDetailsView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.IdentityHashMap;

/**
 * Created by dell on 2017/2/28.
 */
public class IsOrderInvaild{

    IsOrdering isOrdering;

    public void loadOrderMsg(String id , final IsOrdering isOrdering) {
        this.isOrdering = isOrdering;
        RequestParams params = new RequestParams(Constant.APIURL+"ordercontent");
        params.addBodyParameter("id", id);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                AbsBaseBean bean = GsonUtil.fromJson(result, AbsBaseBean.class);
                switch (bean.getResult()) {
                    case Constant.HTTPSUCCESS:
                        OrderDetailsMsgBean msgBean = GsonUtil.fromJson(result, OrderDetailsMsgBean.class);
                        if (StringUtil.isEquals("2",msgBean.getOrder().getAct())){
                            isOrdering.isOrdering(true);
                        }else {
                            isOrdering.isOrdering(false);
                        }
                        break;
                    default:
                        isOrdering.isOrdering(false);
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                isOrdering.isOrdering(false);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    public interface IsOrdering{
        void isOrdering(boolean is);
    }

}
