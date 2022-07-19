package com.tencent.common.utils;

import com.google.common.collect.Lists;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字符大小写转化
 */
public class CaseTransferUtils {

    /**
     * 将Map中所有key首字母转换为大写
     *
     * @param map Map对象
     * @return
     */
    public static Map<String, Object> upperKeyFirstLetterInMap(Map<String, Object> map) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        map.forEach((k, v) -> {
            String upperKey = StringUtils.upperFirst(k);
            if (v == null) {
                resultMap.put(upperKey, null);
            } else {
                if (v instanceof Map) {
                    resultMap.put(upperKey, upperKeyFirstLetterInMap((Map) v));
                } else if (v instanceof List) {
                    resultMap.put(upperKey, upperKeyFirstLetterInList((List) v));
                } else {
                    resultMap.put(upperKey, v);
                }
            }
        });
        return resultMap;
    }

    /**
     * 将List中所有Map对应的key首字母转换为大写
     *
     * @param list List对象
     * @return
     */
    private static List upperKeyFirstLetterInList(List list) {

        List resultList = Lists.newArrayList();
        list.forEach(m -> {
            if (m instanceof Map) {
                resultList.add(upperKeyFirstLetterInMap((Map) m));
            } else if (m instanceof List) {
                resultList.add(upperKeyFirstLetterInList((List) m));
            } else {
                resultList.add(m);
            }
        });
        return resultList;
    }


    /**
     * 将Map中所有key首字母转换为小写
     *
     * @param map Map对象
     * @return
     */
    public static Map<String, Object> lowerKeyFirstLetterInMap(Map<String, Object> map) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        map.forEach((k, v) -> {
            String lowerKey = StringUtils.lowerFirst(k);
            if (v == null) {
                resultMap.put(lowerKey, null);
            } else {
                if (v instanceof Map) {
                    resultMap.put(lowerKey, lowerKeyFirstLetterInMap((Map) v));
                } else if (v instanceof List) {
                    resultMap.put(lowerKey, lowerKeyFirstLetterInList((List) v));
                } else {
                    resultMap.put(lowerKey, v);
                }
            }
        });
        return resultMap;
    }

    /**
     * 将List中所有Map对应的key首字母转换为小写
     *
     * @param list List对象
     * @return
     */
    private static List lowerKeyFirstLetterInList(List list) {

        List resultList = Lists.newArrayList();
        list.forEach(m -> {
            if (m instanceof Map) {
                resultList.add(lowerKeyFirstLetterInMap((Map) m));
            } else if (m instanceof List) {
                resultList.add(lowerKeyFirstLetterInList((List) m));
            } else {
                resultList.add(m);
            }
        });
        return resultList;
    }

}
