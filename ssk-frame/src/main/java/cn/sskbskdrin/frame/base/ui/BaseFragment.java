package cn.sskbskdrin.frame.base.ui;

import android.os.Bundle;
import android.os.Message;
import android.view.View;

import cn.sskbskdrin.base.IFragment;
import cn.sskbskdrin.frame.base.vm.LifeOwner;

/**
 * Created by keayuan on 2015/5/25.
 * Fragment 基类
 */
public abstract class BaseFragment extends IFragment implements LifeOwner {

    /**
     * @return 返回布局资源的layoutId
     */
    protected abstract int getLayoutId();

    /**
     * 在view被创建时调用{@link BaseFragment#onViewCreated(View, Bundle)}
     */
    protected abstract void initView();

    /**
     * 在{@link BaseFragment#onActivityCreated(Bundle)}被调用时调用
     */
    protected void initData() {
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

}
