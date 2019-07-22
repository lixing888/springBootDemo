package com.springboot.demo.util;

import com.springboot.demo.vo.Demo;
import com.springboot.demo.vo.Student;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * @author lixing
 * jdk8 List
 */
@Slf4j
public class ListSum {
    public static void main(String[] args) {
        /**
         * 读取execl文件
         */
//        ExcelReader reader = ExcelUtil.getReader("D://excel/商业化文件盖章申请.xlsx");
//        List<Map<String, Object>> maps = reader.readAll();
//        List<Student> ruleRoleUsers=new ArrayList<>();
//        /*List<RuleRoleUser> ruleRoleUsers = reader.readAll(RuleRoleUser.class);*/
//        maps.forEach(map->{
//            Date date = new Date();
//            Student ruleRoleUser = new Student();
//            map.forEach((k,v)->{
//                //读取标签
//                if(k.equals("userId")){
//                    Integer employeeId = Integer.valueOf(v.toString());
//                    ruleRoleUser.setUserId(employeeId);
//                    //并查询出用户邮箱
//
//                }else if(k.equals("userName")){
//                    ruleRoleUser.setUserName(v.toString());
//                }else if(k.equals("roleCode")){
//                    ruleRoleUser.setRoleCode(v.toString());
//                    //并把roleId查询出来
//
//                    ruleRoleUser.setRoleId(9001);
//                }else if(k.equals("roleName")){
//                    ruleRoleUser.setRoleName(v.toString());
//                }
//                ruleRoleUser.setUpdateTime(date);
//                ruleRoleUser.setCreateTime(date);
//                ruleRoleUser.setDeleted(0);
//            });
//            ruleRoleUsers.add(ruleRoleUser);
//        });
        //log.info("批量插入数据{}",ruleRoleUsers);
        //boolean boo = ruleRoleUserManager.saveOrUpdateBatch(ruleRoleUsers);
        log.info("执行状态{}", true);

        Integer[] myArray = {1, 2, 3};
        List integerList = Arrays.asList(myArray);
        System.out.println(integerList.size());

        int[] intArray = {5, 10, 21};
        List intList = Arrays.asList(myArray);
        System.out.println(intList.size());
        //Java 8 新引入的 Stream 操作
        List myList = Arrays.stream(intArray).boxed().collect(Collectors.toList());
        System.out.println(myList.size());

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list.add(createMap("小溪塔", "A", 9.0, 5.0));
        list.add(createMap("小溪塔", "B", 7.0, 4.0));
        list.add(createMap("小溪塔", "C", 4.574, 2.52));
        list.add(createMap("小溪塔", "D", 8.435, 3.53));
        list.add(createMap("太平溪", "A", 7.246, 4));
        list.add(createMap("太平溪", "B", 5, 3));
        list.add(createMap("雾渡河镇", "A", 7.0, 2.5));
        list.add(createMap("雾渡河镇", "B", 5.0, 3.5));
        List<Map<String, Object>> xlist = groupCount(list);
        for (Map<String, Object> map : xlist) {
            System.out.println("===" + map);
        }

        //==============List取交集并集合集=============
        List<String> list1 = new ArrayList<String>();
        list1.add("1");
        list1.add("2");
        list1.add("3");
        list1.add("5");
        list1.add("6");

        List<String> list2 = new ArrayList<String>();
        list2.add("2");
        list2.add("3");
        list2.add("7");
        list2.add("8");

        // 交集
        List<String> intersection = list1.stream().filter(item -> list2.contains(item)).collect(toList());
        System.out.println("---交集 intersection---");
        intersection.parallelStream().forEach(System.out::println);

        // 差集 (list1 - list2)
        List<String> reduce1 = list1.stream().filter(item -> !list2.contains(item)).collect(toList());
        System.out.println("---差集 reduce1 (list1 - list2)---");
        reduce1.parallelStream().forEach(System.out::println);

        // 差集 (list2 - list1)
        List<String> reduce2 = list2.stream().filter(item -> !list1.contains(item)).collect(toList());
        System.out.println("---差集 reduce2 (list2 - list1)---");
        reduce2.parallelStream().forEach(System.out::println);

        // 并集
        List<String> listAll = list1.parallelStream().collect(toList());
        List<String> listAll2 = list2.parallelStream().collect(toList());
        listAll.addAll(listAll2);
        System.out.println("---并集 listAll---");
        listAll.parallelStream().forEachOrdered(System.out::println);

        // 去重并集
        List<String> listAllDistinct = listAll.stream().distinct().collect(toList());
        System.out.println("---得到去重并集 listAllDistinct---");
        listAllDistinct.parallelStream().forEachOrdered(System.out::println);

        System.out.println("---原来的List1---");
        list1.parallelStream().forEachOrdered(System.out::println);
        System.out.println("---原来的List2---");
        list2.parallelStream().forEachOrdered(System.out::println);


        List<Student> studentList = new ArrayList<>();
        Student student1 = new Student();
        student1.setAge("12");
        student1.setSex(0);
        Student student2 = new Student();
        student2.setAge("13");
        student2.setSex(2);
        Student student3 = new Student();
        student3.setAge("11");
        student3.setSex(1);
        Student student4 = new Student();
        student4.setAge("18");
        student4.setSex(1);
        Student student5 = new Student();
        student5.setAge("18");
        student5.setSex(0);
        Student student6 = new Student();
        student6.setAge("18");
        student6.setSex(2);
        Student student7 = new Student();
        student7.setAge("18");
        student7.setSex(2);
        studentList.add(student1);
        studentList.add(student2);
        studentList.add(student3);
        studentList.add(student4);
        studentList.add(student5);
        studentList.add(student6);
        studentList.add(student7);
        List<Demo> demos = new ArrayList<Demo>();

        studentList.stream().sorted(Comparator.comparing(Student::getAge));
        //倒序
        studentList.stream().sorted(Comparator.comparing(Student::getAge).reversed());

        //原始数据
        System.out.println("原始数据 组装list<demo>*******************");
        demos = studentList.stream().map(student -> new Demo(student.getAge(), student.getSex())).collect(Collectors.toList());
        demos.forEach(demo -> {
            System.out.println("年龄 " + demo.getAge() + "  性别 " + demo.getSex() + ",");
        });
        //只取sex为0
        System.out.println("只取sex为0****************");
        List<Demo> demorm = demos.stream().filter(demo -> demo.getSex() == 0).distinct().collect(Collectors.toList());
        demorm.forEach(demo -> {
            System.out.println("年龄 " + demo.getAge() + "  性别 " + demo.getSex() + ",");
        });
        //筛选年龄大于12岁
        System.out.println("筛选年龄大于12岁的*************");
        List<Demo> demoFilter = demos.stream().filter(demo -> Integer.valueOf(demo.getAge()) > 12).collect(Collectors.toList());
        demoFilter.forEach(demo -> {
            System.out.println("年龄 " + demo.getAge() + "  性别 " + demo.getSex() + ",");
        });
        //排序
        System.out.println("排序******************");
        List<Demo> demoSort = demos.stream().sorted((s1, s2) -> s1.getAge().compareTo(s2.getAge())).collect(Collectors.toList());
        demoSort.forEach(demo -> {
            System.out.println("年龄 " + demo.getAge() + "  性别 " + demo.getSex() + ",");
        });
        //倒序
        System.out.println("倒序****************");
        ArrayList<Demo> demoArray = (ArrayList<Demo>) demos;
        Comparator<Demo> comparator = (h1, h2) -> h1.getAge().compareTo(h2.getAge());
        demos.sort(comparator.reversed());
        demos.forEach(demo -> {
            System.out.println("年龄 " + demo.getAge() + "  性别 " + demo.getSex() + ",");
        });
        //多条件正序
        System.out.println("多条件排序正序****************");
        demoArray.sort(Comparator.comparing(Demo::getSex).thenComparing(Demo::getAge));
        demoArray.forEach(demo -> {
            System.out.println("年龄 " + demo.getAge() + "  性别 " + demo.getSex() + ",");
        });
        //多条件倒序
        System.out.println("多条件排序倒序 sex  倒序****************");
        demoArray.sort(Comparator.comparing(Demo::getSex).reversed().thenComparing(Demo::getAge));
        demoArray.forEach(demo -> {
            System.out.println("年龄 " + demo.getAge() + "  性别 " + demo.getSex() + ",");
        });
        //按照年龄分组
        System.out.println("根据age分组结果为Map****************");
        Map<String, List<Demo>> demoOder = demos.stream().collect(Collectors.groupingBy(Demo::getAge));
        System.out.println(demoOder);

        String[] myArrays = {"Apple", "Banana", "Orange"};
        List<String> myLists = new ArrayList<>();
        for (String str : myArrays) {
            myLists.add(str);
            //比如说需要把数组的每个元素向 List 中添加两次
            // myLists.add(str);
        }
        System.out.println(myLists.size());

        List<Integer> ruleData = new ArrayList<Integer>();
        ruleData.add(1);
        ruleData.add(1);
        ruleData.add(0);
        ruleData.add(0);
        List<Integer> areas = ruleData.stream().distinct().map(record -> record.intValue()).collect(Collectors.toList());

        List<String> list11 = new ArrayList<>();
        list11.add("zzz");
        list11.add("aaa");
        list11.add("bbb");
        list11.add("bbb");
        list11.add("zzz");
        Set<String> set = new HashSet<>(list11);
        boolean result = list11.size() != set.size() ? true : false;
        System.out.println("List中是否存在重复数据:" + result);
        Map<String, String> map = new HashMap<>();
        map.put("name", "lixing");
        map.put("age", "24");
        map.put("sex", "男");
        // lxj，map中存在name,获得name对应的value
        String name = map.getOrDefault("name", "test");
        System.out.println(name);
        // 北京，map中不存在address,使用默认值“北京”
        String address = map.getOrDefault("address", "北京");
        System.out.println(address);

        Map<String, List<String>> maps = new HashMap<>();
        maps.getOrDefault("list1", new ArrayList<>()).add("A");

        //按照人员工号排序
        //return employeeInfos.stream().sorted(Comparator.comparing(DeptmentInfoVo.EmployeeInfo::getLeaderId)).collect(Collectors.toList());
        //RuleCostInfo costInfo = costInfoMap.getOrDefault(record.getCostTypeId(), new RuleCostInfo());
        //JAVA list按照另外一个给定list排序 多余的自动排在后面
        String[] regulation = {"诸葛亮", "鲁班", "xzcx", "貂蝉", "吕布"};
        final List<String> regulationOrder = Arrays.asList(regulation);
        String[] ordered = {"诸葛亮", "nice", "貂蝉", "诸葛亮", "xzcx", "吕布", "貂蝉", "鲁班", "诸葛亮", "貂蝉", "鲁班", "诸葛亮", "hahahahah", "adsad"};
        List<String> orderedList = Arrays.asList(ordered);
        Collections.sort(orderedList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int io1 = regulationOrder.indexOf(o1);
                int io2 = regulationOrder.indexOf(o2);
                if (io1 == io2) {
                    return 0;
                }
                if (io1 == -1) {
                    return 1;
                }
                if (io2 == -1) {
                    return -1;
                }
                return io1 - io2;
            }
        });

        System.out.println(orderedList.stream().distinct().collect(Collectors.toList()));


    }


    //================================
    public static List<Map<String, Object>> groupCount(List<Map<String, Object>> list) {
        List<Map<String, Object>> xList = new ArrayList<Map<String, Object>>();
        if (list != null && list.size() > 0) {
            double zhj1 = 0, gdzhj1 = 0;
            xList.add(list.get(0));
            for (int i = 1; i < list.size(); i++) {
                int size = xList.size() - 1;
                Map<String, Object> map = list.get(i);
                Map<String, Object> map1 = xList.get(size);
                String xz = object2String(map.get("乡镇"));
                String xz1 = object2String(map1.get("乡镇"));
                double zj = object2Double(map.get("总计"));
                double gdzj = object2Double(map.get("耕地"));
                zhj1 = add(zj, zhj1);
                gdzhj1 = add(gdzj, gdzhj1);
                if (xz.equals(xz1)) {
                    double zj1 = object2Double(map1.get("总合计"));
                    double gdzj1 = object2Double(map1.get("耕地"));
                    double zhj = add(zj, zj1);
                    double gdzhj = add(gdzj, gdzj1);
                    if (!Objects.isNull(gdzhj)) {
                        System.out.println("非空判断");
                    }
                    Map<String, Object> map2 = new HashMap<String, Object>();
                    map2.put("乡镇总合计", xz + "总合计");
                    map2.put("村", null);
                    map2.put("总计", zhj);
                    map2.put("耕地", gdzhj);
                    xList.add(map);
                    xList.add(map2);
                } else {
                    xList.add(map);
                }
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("所有总计", "所有总计");
            map.put("总计", zhj1);
            map.put("耕地", gdzhj1);
            xList.add(map);
        }

        return xList;
    }

    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    public static String object2String(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    public static double object2Double(Object obj) {
        String value = object2String(obj);
        return "".equals(value) ? 0 : Double.valueOf(value);
    }

    public static Map<String, Object> createMap(String town, String village, double zjmj, double gdmj) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("乡镇", town);
        map.put("村", village);
        map.put("总计", zjmj);
        map.put("耕地", gdmj);
        return map;
    }
}
