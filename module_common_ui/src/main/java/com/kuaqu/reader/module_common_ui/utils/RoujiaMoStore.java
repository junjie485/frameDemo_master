package com.kuaqu.reader.module_common_ui.utils;

import com.kuaqu.reader.module_common_ui.response.RouJiaMo;

public class RoujiaMoStore {
    private SimpleRouJiaMoFactroy factroy;

    public RoujiaMoStore(SimpleRouJiaMoFactroy factroy) {
        this.factroy = factroy;
    }
    public RouJiaMo sellRouJiaMo(String type)
    {
        RouJiaMo rouJiaMo = factroy.createRouJiaMo(type);
        rouJiaMo.prepare();
        rouJiaMo.fire();
        rouJiaMo.pack();
        return rouJiaMo;
    }


}
