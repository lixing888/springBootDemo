package com.springboot.demo.util;

public class EnumUtil {

    /**
     * 返回指定编码的'枚举'
     * @param code
     * @return SharedObjTypeEnum
     * @throws
     */
    public static <T extends CommonEnum> T getEnumBycode(Class<T> clazz, String code) {
        for(T _enum : clazz.getEnumConstants())
            if(code == _enum.code())
                return _enum;
        return null;
    }

    /**
     * 返回指定名称的'枚举'
     * @param name
     * @return SharedObjTypeEnum
     * @throws
     */
    public static <T extends CommonEnum> T getEnumByName(Class<T> clazz, String name) {
        for(T _enum : clazz.getEnumConstants())
            if(_enum.name().equals(name))
                return _enum;
        return null;
    }

    /**
     * 返回指定描述的'枚举'
     * @param
     * @return SharedObjTypeEnum
     * @throws
     */
//    public static <T extends CommonEnum> T getEnumByDesc(Class<T> clazz, String desc) {
//        for(T _enum : clazz.getEnumConstants())
//            if(_enum.getDesc().equals(desc))
//                return _enum;
//        return null;
//    }
    public static void main(String[] args) {
        StatusEnum statusEnum = EnumUtil.getEnumBycode(StatusEnum.class, "MDBS00000001");
        System.out.print(statusEnum.getName()+"  阀值："+statusEnum.getMaxNbr());

        //StatusEnum statusEnum1 = EnumUtil.getEnumByName(StatusEnum.class, "END");

        //StatusEnum statusEnum2 = EnumUtil.getEnumByDesc(StatusEnum.class, "开始");

    }
}
