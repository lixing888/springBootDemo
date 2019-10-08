package com.springboot.demo;

/**
 * @author lixing
 * java list 交集 并集 差集 去重复并集
 */

import com.springboot.demo.entity.Person;
import com.springboot.demo.vo.ContractData;
import com.springboot.demo.vo.CostDeptItemListRequest;
import com.springboot.demo.vo.Student;
import net.sf.json.JSONArray;
import org.apache.poi.ss.formula.functions.T;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class ListUtil {

    public static void main(String[] args) {
        List<String> list1 = new ArrayList();
        list1.add("1111");
        list1.add("2222");
        list1.add("3333");
        List<String> list2 = new ArrayList();
        list2.add("1111");
        list2.add("2222");
        // 交集
        List<String> intersection = list1.stream().filter(item -> list2.contains(item)).collect(toList());
        System.out.println("---得到交集 intersection---");
        intersection.parallelStream().forEach(System.out::println);
        // 差集 (list1 - list2)
        List<String> reduce1 = list1.stream().filter(item -> !list2.contains(item)).collect(toList());
        System.out.println("---得到差集 reduce1 (list1 - list2)---");
        reduce1.parallelStream().forEach(System.out::println);
        // 差集 (list2 - list1)
        List<String> reduce2 = list2.stream().filter(item -> !list1.contains(item)).collect(toList());
        System.out.println("---得到差集 reduce2 (list2 - list1)---");
        reduce2.parallelStream().forEach(System.out::println);
        // 并集
        List<String> listAll = list1.parallelStream().collect(toList());
        List<String> listAll2 = list2.parallelStream().collect(toList());
        listAll.addAll(listAll2);
        System.out.println("---得到并集 listAll---");
        listAll.parallelStream().forEach(System.out::println);
        // 去重并集
        List<String> listAllDistinct = listAll.stream().distinct().collect(toList());
        System.out.println("---得到去重并集 listAllDistinct---");
        listAllDistinct.parallelStream().forEach(System.out::println);
        System.out.println("---原来的List1---");
        list1.parallelStream().forEach(System.out::println);
        System.out.println("---原来的List2---");
        list2.parallelStream().forEach(System.out::println);         // 一般有filter 操作时，不用并行流parallelStream ,如果用的话可能会导致线程安全问题     }

        List list11 = new ArrayList();
        list11.add("1111");
        list11.add("2222");
        list11.add("3333");

        List list12 = new ArrayList();
        list12.add("3333");
        list12.add("4444");
        list12.add("5555");

        //并集
        //list1.addAll(list2);
        //交集
        //list1.retainAll(list2);
        Set<String> set1 = new HashSet<>();
        Set<String> set2 = new HashSet<>();

        set1.add("a");
        set1.add("b");
        boolean c = set1.add("c");
        set1.add("d");

        set2.add("c");
        set2.add("d");
        set2.add("e");

        //交集
        boolean result = set1.retainAll(set2);

        System.out.println("交集是 " + set1);


        //差集
        //list1.removeAll(list2);
        //无重复并集
        list2.removeAll(list1);
        list1.addAll(list2);

        Iterator<String> it = list1.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());

        }

        String deptId = "11";
        List<String> grdDeptNo = new ArrayList<>();
        List<String> deptLevelNoList = Arrays.asList("0@11", "0@11@2@3@4@5", "0@11@2@3@7@8", "0@11@2@3@7@8@10@13", "0@11@2@3@8@9");
        List<String> childIds = Arrays.asList("2", "3", "7");
        for (String deptLevelNo : deptLevelNoList) {
            String deptLevel = deptLevelNo.substring(deptLevelNo.lastIndexOf(deptId) + deptId.length());
            System.out.println("下属部门编码:" + deptLevel);

            if (!deptLevel.isEmpty()) {
                String[] split = deptLevel.split("@");
                for (int i = 0; i < split.length; i++) {
                    String deptNo = split[i];
                    if (!deptNo.isEmpty()) {
                        grdDeptNo.add(split[i]);
                    }
                }
            }

        }

        Set<String> grpIds = new HashSet<>(grdDeptNo);
        System.out.println("子部门个数：" + grpIds.size());
        for (String grpId : grpIds) {
            System.out.println("子部门:" + grpId);
        }
        grpIds.removeAll(childIds);
        //孙子辈的部门id
        System.out.println("孙子辈的部门个数：" + grpIds.size());
        for (String grpId : grpIds) {
            System.out.println("管辖部门：" + grpId);
        }
        //System.out.println("-----------------------------------\n");
        //printStr(list1);

        if (Objects.equals("2", "2")) {
            System.out.println("Objects判断两个对象是否相等。");
        }

        String str = DecimalFormat.getNumberInstance().format(1245600000);
        String currecy = NumberFormat.getCurrencyInstance().format(1245600000);
        System.out.println("转换成Currency格式：" + currecy);
        System.out.println("转换成带千分位的格式：" + str);

        BigDecimal a = new BigDecimal("233333333333333333333333333");
        //DecimalFormat df = new DecimalFormat(",###,##0");
        //没有小数
        DecimalFormat df = new DecimalFormat(",###,##0.00"); //保留2位小数
        String format = df.format(a);
        System.out.println(format);


        List<Student> list = new ArrayList<>();

        list.stream().map(e -> {
            Person person = new Person();
            person.setAge(Integer.valueOf(e.getAge()));
            person.setName(e.getName());
            return person;
        }).collect(Collectors.toList());

//        LambdaQueryWrapper<RuleEngineRuleSetTestCase> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(RuleEngineRuleSetTestCase::getRuleSetId, id.getId());
//        wrapper.eq(RuleEngineRuleSetTestCase::getDeleted, DeletedEnum.ENABLE.getStatus());
//        List<RuleEngineRuleSetTestCase> resuleList = ruleEngineRuleSetTestCaseManager.list(wrapper);
//        return resuleList.stream().map(m -> {
//            RuleEngineCaseListResponse listResponse = new RuleEngineCaseListResponse();
//            listResponse.setId(m.getId());
//            listResponse.setParameters(JSON.parseArray(m.getParamJson(), RuleEngineCaseListResponse.ParameterListBean.class));
//            listResponse.setExpectedResult(m.getExpectedResult());
//            listResponse.setActaulResult(m.getLastExecuteResult());
//            return listResponse;
//        }).collect(Collectors.toList());


        List<ContractData.CeoCheckData> costItems = new ArrayList<>();
        ContractData.CeoCheckData ceoCheckData1=new ContractData.CeoCheckData();
        ceoCheckData1.setCostTypeId("MDS090121");
        ceoCheckData1.setAmount(new BigDecimal("0"));
        ceoCheckData1.setCurrency("CNY");
        ceoCheckData1.setCnyAmount(new BigDecimal("10000"));

        ContractData.CeoCheckData ceoCheckData2=new ContractData.CeoCheckData();
        ceoCheckData2.setCostTypeId("MDS090123");
        ceoCheckData2.setAmount(new BigDecimal("0"));
        ceoCheckData2.setCurrency("CNY");
        ceoCheckData2.setCnyAmount(new BigDecimal("10000"));

        ContractData.CeoCheckData ceoCheckData3=new ContractData.CeoCheckData();
        ceoCheckData3.setCostTypeId("MDS090122");
        ceoCheckData3.setAmount(new BigDecimal("0"));
        ceoCheckData3.setCurrency("CNY");
        ceoCheckData3.setCnyAmount(new BigDecimal("10000"));

        ContractData.CeoCheckData ceoCheckData4=new ContractData.CeoCheckData();
        ceoCheckData4.setCostTypeId("MDS090671");
        ceoCheckData4.setAmount(new BigDecimal("0"));
        ceoCheckData4.setCurrency("CNY");
        ceoCheckData4.setCnyAmount(new BigDecimal("10000"));

        ContractData.CeoCheckData ceoCheckData5=new ContractData.CeoCheckData();
        ceoCheckData5.setCostTypeId("MDS090321");
        ceoCheckData5.setAmount(new BigDecimal("0"));
        ceoCheckData5.setCurrency("CNY");
        ceoCheckData5.setCnyAmount(new BigDecimal("10000"));

        costItems.add(ceoCheckData1);
        costItems.add(ceoCheckData2);
        costItems.add(ceoCheckData3);
        costItems.add(ceoCheckData4);
        costItems.add(ceoCheckData5);


        CostDeptItemListRequest costDeptItemListRequest = new CostDeptItemListRequest();
        List<CostDeptItemListRequest.CostDeptItem> costDeptItemList = new ArrayList<>();

        for (ContractData.CeoCheckData costItem : costItems) {
            CostDeptItemListRequest.CostDeptItem costDeptItem = new CostDeptItemListRequest.CostDeptItem();
            costDeptItem.setBudgetItemCode(costItem.getCostTypeId());
            costDeptItem.setAmount(costItem.getCnyAmount());
            costDeptItem.setCostDeptId("");
            costDeptItem.setCurrency("CNY");
            costDeptItemList.add(costDeptItem);
        }

        System.out.println("jsonListStr = " + JSONArray.fromObject(costDeptItemList));

    }

    public static void printStr(List list1) {
        for (int i = 0; i < list1.size(); i++) {
            System.out.println(list1.get(i));
        }

    }


    /**
     * for all jdk version
     * @param mList
     * @return
     */
    public static String listToString(List<T> mList) {
        String convertedListStr = "";
        if (null != mList && mList.size() > 0) {
            String[] mListArray = mList.toArray(new String[mList.size()]);
            for (int i = 0; i < mListArray.length; i++) {
                if (i < mListArray.length - 1) {
                    convertedListStr += mListArray[i] + ",";
                } else {
                    convertedListStr += mListArray[i];
                }
            }
            return convertedListStr;
        } else return "List is null!!!";
    }

    /**
     * for jdk <= java 7
     * @param mList
     * @return
     */
    // 采用Stringbuilder.append()的方式追加
    public static String listToString2(List<CostDeptItemListRequest.CostDeptItem> mList) {
        final String SEPARATOR = ",";
        // mList = Arrays.asList("AAA", "BBB", "CCC");
        StringBuilder sb = new StringBuilder();
        String convertedListStr = "";
        if (null != mList && mList.size() > 0) {
            for (CostDeptItemListRequest.CostDeptItem item : mList) {
                sb.append(item);
                sb.append(SEPARATOR);
            }
            convertedListStr = sb.toString();
            convertedListStr = convertedListStr.substring(0, convertedListStr.length()
                    - SEPARATOR.length());
            return convertedListStr;
        } else return "List is null!!!";
    }
}
