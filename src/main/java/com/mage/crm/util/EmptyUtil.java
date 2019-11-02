package com.mage.crm.util;

public class EmptyUtil {
    public static boolean isEmpty(String str){
        if(str==null||"".equals(str.trim())){
            return  true;
        }
        return false;
    }
}
