package com.kuaqu.reader.module_common_ui.utils;

/**
 * 蓝宝石装饰品
 * 每颗攻击力+5
 * @author zhy
 *
 */
public class BlueGemDecorator implements IEquipDecorator
{
    /**
     * 每个装饰品维护一个装备
     */
    private IEquip equip;

    public BlueGemDecorator(IEquip equip)
    {
        this.equip = equip;
    }

    @Override
    public int caculateAttack()
    {
        return 5 + equip.caculateAttack();
    }

    @Override
    public String description()
    {
        return equip.description() + "+ 蓝宝石";
    }

}

