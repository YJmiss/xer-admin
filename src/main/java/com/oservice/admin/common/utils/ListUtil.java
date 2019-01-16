package com.oservice.admin.common.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @ClassName: ListUtil
 * @Description: TODO(对list的一些操作)
 */
public class ListUtil {

    /**
     * @param list          需要转换的list
     * @param idName        list结构中单个节点的id名
     * @param pidName       list中单个节点的父节点关联名称
     * @param innerListName 返回后内嵌list的名称
     *                      ListUtil.listToTreeList(categoryList, "cat_id", "parent_id", "data_list")
     * @throws
     * @Title: listToTreeList
     * @Description: TODO(把平行的list转换为树状list集合)
     * @Return List<Map   <   String   ,   Object>>
     */
    public static List<Map<String, Object>> listToTreeList(List<Map<String, Object>> list, String idName, String pidName, String innerListName) {
        try {
            Map<String, Map<String, Object>> allData = new TreeMap<String, Map<String, Object>>();
            List<String> keyList = new ArrayList<String>();
            for (Map<String, Object> data : list) {
                allData.put(data.get(idName).toString(), data);
                keyList.add(data.get(idName).toString());
            }
            List<String> record = new ArrayList<String>();
            for (String key : keyList) {
                String pId = allData.get(key).get(pidName).toString();
                // 是子节点
                if (allData.get(pId) != null) {
                    Map<String, Object> pMap = allData.get(pId);
                    //已经创建了list子集合
                    if (pMap.get(innerListName) != null) {
                        @SuppressWarnings("unchecked")
                        List<Map<String, Object>> dataList = (List<Map<String, Object>>) pMap.get(innerListName);
                        dataList.add(allData.get(key));
                    } else {
                        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
                        dataList.add(allData.get(key));
                        pMap.put(innerListName, dataList);
                    }
                    record.add(key);
                }
            }
            List<Map<String, Object>> score = new ArrayList<Map<String, Object>>();
            for (String key : keyList) {
                if (!record.contains(key)) {
                    score.add(allData.get(key));
                }
            }
            return score;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("转化出错，请检查参数名及参数是否正确");
        }
        return null;
    }

    /**
     * @param list          需要转换的list
     * @param idName        list结构中单个节点的id名
     * @param pidName       list中单个节点的父节点关联名称
     * @param innerListName 返回后内嵌list的名称
     * @throws
     * @Title: listToTreeList2
     * @Description: TODO(把平行的list转换为树状list集合)
     * @Return List<Map   <   String   ,   Object>>
     */
    public static List<Map<String, Object>> listToTreeList2(List<LinkedHashMap<String, Object>> list, String idName, String pidName, String innerListName) {
        try {
            Map<String, Map<String, Object>> allData = new TreeMap<String, Map<String, Object>>();
            List<String> keyList = new ArrayList<String>();
            for (LinkedHashMap<String, Object> data : list) {
                allData.put(data.get(idName).toString(), data);
                keyList.add(data.get(idName).toString());
            }
            List<String> record = new ArrayList<String>();
            for (String key : keyList) {
                String pId = allData.get(key).get(pidName).toString();
                if (allData.get(pId) != null) {//是子节点
                    Map<String, Object> pMap = allData.get(pId);
                    if (pMap.get(innerListName) != null) {//已经创建了list子集合
                        @SuppressWarnings("unchecked")
                        List<Map<String, Object>> dataList = (List<Map<String, Object>>) pMap.get(innerListName);
                        dataList.add(allData.get(key));
                    } else {
                        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
                        dataList.add(allData.get(key));
                        pMap.put(innerListName, dataList);
                    }
                    record.add(key);
                }
            }
            List<Map<String, Object>> score = new ArrayList<Map<String, Object>>();
            for (String key : keyList) {
                if (!record.contains(key)) {
                    score.add(allData.get(key));
                }
            }
            return score;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("转化出错，请检查参数名及参数是否正确");
        }
        return null;
    }

}
