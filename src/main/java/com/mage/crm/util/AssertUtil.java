package com.mage.crm.util;

import com.mage.crm.base.exceptions.ParamsException;

public class AssertUtil {
    private static Integer code=CrmConstant.OPS_FAILED_CODE;
    private static String msg;
    public static void isTrue(boolean flag,String msg){
        if(flag) {
            throw new ParamsException(code, msg);
        }
    }
    public static void isTrue(boolean flag,Integer code,String msg){
        if(flag) {
            throw new ParamsException(code, msg);
        }
    }
}
