package example.hs.haihai.base;


/**
 * Created by Administrator on 2018/3/14.
 */
public interface BaseContract {
    interface BasePresenter<T> {
        void attachView(T view);
        void detachView();
    }



//    abstract class BasePresenter<T> {
//        protected T view;
//
//        public void attachView(T view) {
//            this.view = view;
//        }
//
//        public void detachView() {
//            view = null;
//        }
//    }

    interface BaseView {
        /**
         * c错误返回标记
         *
         * @param code 错误码
         * @param info 错误信息描述
         */
        void showError(int code, String info);

        /**
         * 完成回调，用得可能不多
         */
        void complete(int code, String info);

        /**
         * 登录失效回调
         * 后台返回 403
         */
        //  void unlogin();

    }
}
