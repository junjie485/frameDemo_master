package com.kuaqu.reader.module_material_design;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

public abstract class LazyFragment extends Fragment {
    private boolean isPrepared;
    private boolean isLazyLoaded;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e("周期","setUserVisibleHint");
        lazyLoad();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared=true;
        lazyLoad();
        Log.e("周期","onActivityCreated");
    }

    private void lazyLoad() {
        if(isPrepared&&!isLazyLoaded&&getUserVisibleHint()){
            onLazyLoad();
            isLazyLoaded=true;
        }
    }


    public abstract void onLazyLoad();
}
