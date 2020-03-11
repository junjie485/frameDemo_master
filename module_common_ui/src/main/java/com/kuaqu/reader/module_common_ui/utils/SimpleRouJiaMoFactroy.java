package com.kuaqu.reader.module_common_ui.utils;

import com.kuaqu.reader.module_common_ui.response.LaRouJiaMo;
import com.kuaqu.reader.module_common_ui.response.RouJiaMo;
import com.kuaqu.reader.module_common_ui.response.SuanRouJiaMo;
import com.kuaqu.reader.module_common_ui.response.TianRouJiaMo;

/*
* 工厂模式：不同的对象进行同一个请求，需求均可以满足
* 工厂模式不单单是在new 对象的基础上添加了一层工厂辅助类
* 例子：不同城市的肉夹馍店对象，同一个请求：卖肉夹馍。但不同城市店铺，卖的肉夹馍类型不同。
* 比如：工厂生产一批模具，模具的用途（方法）相同，但是模具的样式是不定的。
* */
public class SimpleRouJiaMoFactroy {
    public RouJiaMo createRouJiaMo(String type)
    {
        RouJiaMo rouJiaMo = null;
        if (type.equals("Suan"))
        {
            rouJiaMo = new SuanRouJiaMo();
        } else if (type.equals("Tian"))
        {
            rouJiaMo = new TianRouJiaMo();
        } else if (type.equals("La"))
        {
            rouJiaMo = new LaRouJiaMo();
        }
        return rouJiaMo;
    }

}
