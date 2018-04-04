package com.jessechiu.android.ontepad;

import java.io.Serializable;

// 存储的数据单元类
// 这里要继承序列化接口，因为 list 数据不能直接通过 intent 传递
public class CostBean implements Serializable{
    public String costTitle;
    public String costDate;
    public String costMoney;
}
