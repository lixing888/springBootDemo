package com.springboot.demo.util;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.cron.CronUtil;
import cn.hutool.extra.mail.MailUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import cn.hutool.extra.ssh.JschUtil;
import cn.hutool.extra.ssh.Sftp;
import cn.hutool.http.HttpUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.springboot.demo.vo.User;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.*;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class StreamsDate {

    private enum Status {
        OPEN, CLOSED
    };

    private static final Pattern NUM_PATTERN = Pattern.compile("[0-9]+");
    private static final class Task {
        private final Status status;
        private final Integer points;

        Task( final Status status, final Integer points ) {
            this.status = status;
            this.points = points;
        }

        public Integer getPoints() {
            return points;
        }

        public Status getStatus() {
            return status;
        }

        @Override
        public String toString() {
            return String.format( "[%s, %d]", status, points );
        }
    }

    /**
     * 利用正则表达式判断字符串是否是数字
     * @param str
     * @return
     **/
    public static boolean isNumeric(String str){
        Matcher isNum = NUM_PATTERN.matcher(str);
        if( !isNum.matches() ){
            return false;
        } return true;
    }

    public static void main(String[] args) {

        final Collection<Task> tasks = Arrays.asList(
                new Task(Status.OPEN,6 ),
                new Task(Status.OPEN,13 ),
                new Task(Status.CLOSED,18 ),
                new Task(Status.OPEN,12 ),
                new Task(Status.CLOSED,16 ),
                new Task(Status.OPEN,11 )
        );
        //首先看一个问题：在这个task集合中一共有多少个OPEN状态的点？在Java 8之前，要解决这个问题，则需要使用foreach循环遍历task集合；
        //但是在Java 8中可以利用steams解决：包括一系列元素的列表，并且支持顺序和并行处理。
        final long totalPointsOfOpenTasks = tasks
                .stream()
                //过滤
                .filter( task -> task.getStatus() == Status.OPEN )
                .mapToInt( Task::getPoints )
                .sum();

        System.out.println( "流程开启总数:" + totalPointsOfOpenTasks );

        //steam的另一个价值是创造性地支持并行处理（parallel processing）。对于上述的tasks集合，我们可以用下面的代码计算所有任务的点数之和：
        final double totalPoints = tasks
                .stream()
                //这里我们使用parallel方法并行处理所有的task，并使用reduce方法计算最终的结果。控制台输出如下：
                .parallel()
                // or map( Task::getPoints )
                .map( task -> task.getPoints() )
                .reduce( 0, Integer::sum );
        System.out.println("Total points (all tasks):"+totalPoints);

        //对于一个集合，经常需要根据某些条件对其中的元素分组。利用steam提供的API可以很快完成这类任务，代码如下：
        final Map< Status, List< Task >> map = tasks
                .stream()
                .collect( Collectors.groupingBy( Task::getStatus ) );
        System.out.println( map );

        //最后一个关于tasks集合的例子问题是：如何计算集合中每个任务的点数在集合中所占的比重，具体处理的代码如下：
        final Collection< String > result = tasks
                // Stream< String >
                .stream()
                // IntStream
                .mapToInt( Task::getPoints )
                // LongStream
                .asLongStream()
                // DoubleStream
                .mapToDouble( points -> points / totalPoints )
                // Stream< Double >
                .boxed()
                // LongStream
                .mapToLong( weigth -> ( long )( weigth * 100 ) )
                // Stream< String>
                .mapToObj( percentage -> percentage + "%" )
                // List< String >
                .collect( Collectors.toList() );

        System.out.println( result );
        //最后，正如之前所说，Steam API不仅可以作用于Java集合，传统的IO操作（从文件或者网络一行一行得读取数据）可以受益于steam处理，这里有一个小例子：
//        final Path path = new File( filename ).toPath();
//        try( Stream< String > lines = Files.lines( path, StandardCharsets.UTF_8 ) ) {
//            lines.onClose( () -> System.out.println("Done!") ).forEach( System.out::println );
//        }

        String lists=CollUtil.join(Arrays.asList(11,22,33,44),",");

        String str="100821李";
        System.out.println("判断是否是纯数字："+isNumeric(str));

        //求平方
        List<Integer> nums = Arrays.asList(1, 2, 3, 4);
        List<Integer> squareNums = nums.stream().
                map(n -> n * n).
                collect(Collectors.toList());
        System.out.println("平方:"+squareNums);
//        List<User> lists = new ArrayList<>();
//        lists.add(new User("张三",22));
//        lists.add(new User("张三",21));
//        lists.add(new User("李四",22));
//        lists.add(new User("张三",21));
//        List<User> collect = lists.stream().filter(user -> user.getAge() < 22).collect(Collectors.toList());
//        List<User> collect1 = lists.stream().filter(user -> user.getAge() > 50).collect(Collectors.toList());


        //JDK8里的时间
        final Clock clock = Clock.systemUTC();
        System.out.println(  Date.from(clock.instant()) );
        //毫秒
        System.out.println( clock.millis() );

        final LocalDate date = LocalDate.now();
        final LocalDate dateFromClock = LocalDate.now( clock );

        System.out.println( date );
        System.out.println( dateFromClock );

        // Get the local date and local time
        final LocalTime time = LocalTime.now();
        final LocalTime timeFromClock = LocalTime.now( clock );
        //文件转byte[]
        System.out.println(ByteFileUtils.getBytes("文件转byte[] :"+"G:\\filePath\\test.txt"));


        System.out.println( time );
        System.out.println( timeFromClock );
        //带时区的
        final ZonedDateTime zonedDatetime = ZonedDateTime.now();
        final ZonedDateTime zonedDatetimeFromClock = ZonedDateTime.now( clock );
        final ZonedDateTime zonedDatetimeFromZone = ZonedDateTime.now( ZoneId.of( "America/Los_Angeles" ) );

        System.out.println( zonedDatetime );
        System.out.println( zonedDatetimeFromClock );
        System.out.println( zonedDatetimeFromZone );
        System.out.println("纳秒:"+System.nanoTime()+"==毫秒："+System.currentTimeMillis());



        //最后看下Duration类，它持有的时间精确到秒和纳秒。这使得我们可以很容易得计算两个日期之间的不同，例子代码如下
        // Get duration between two dates
        final LocalDateTime from = LocalDateTime.of( 2014, Month.APRIL, 16, 0, 0, 0 );
        final LocalDateTime to = LocalDateTime.of( 2015, Month.APRIL, 16, 23, 59, 59 );

        final Duration duration = Duration.between( from, to );
        System.out.println( "Duration in days: " + duration.toDays() );
        System.out.println( "Duration in hours: " + duration.toHours() );


        //请求列表页 爬虫
        String listContent = HttpUtil.get("https://www.oschina.net/action/ajax/get_more_news_list?newsType=&p=2");
        //使用正则获取所有标题
        List<String> titles = ReUtil.findAll("<span class=\"text-ellipsis\">(.*?)</span>", listContent, 1);
        for (String title : titles) {
            //打印标题
            System.out.println(title);
           // log.info(title);
        }

        //定时器
//        CronUtil.schedule("*/2 * * * * *", new Task() {
//            public void execute() {
//                System.out.println("Task excuted.");
//            }
//        });
        //发送邮件(pass需要自主生成授权码)
        //MailUtil.send("85210279@qq.com", "测试", "邮件来自Hutool测试", false);

        //生成二维码
        QrCodeUtil.generate("https://www.baidu.com/", 300, 300, FileUtil.file("d:/qrcode.jpg"));


        QrConfig config = new QrConfig(300, 300);
        // 设置边距，既二维码和背景之间的边距
        config.setMargin(3);
        // 设置前景色，既二维码颜色（青色）
        config.setForeColor(Color.CYAN.getRGB());
        // 设置背景色（灰色）
        config.setBackColor(Color.GRAY.getRGB());
        // 生成二维码到文件，也可以到流
        QrCodeUtil.generate("http://hutool.cn/", config, FileUtil.file("d:/qrcodeNew.jpg"));

//        Sftp sftp= JschUtil.createSftp("172.0.0.1", 22, "root", "123456");
//        //进入远程目录
//        sftp.cd("/opt/upload");
//        //上传本地文件
//        sftp.put("e:/test.jpg", "/opt/upload");
//        //下载远程文件
//        sftp.get("/opt/upload/test.jpg", "e:/test2.jpg");
//        //关闭连接
//        try {
//            sftp.close();
//        }catch (Exception e){
//            log.info(e.toString());
//        }
        //execl文档读取poi
//        ExcelReader reader = ExcelUtil.getReader("d:/aaa.xlsx");
//        List<List<Object>> readAll = reader.read();
//        for(int i=0;i<readAll.size();i++){
//            System.out.println(readAll.get(i));
//        }

        //将行列对象写出到Excel
        List<String> row1 = CollUtil.newArrayList("aa", "bb", "cc", "dd");
        List<String> row2 = CollUtil.newArrayList("aa1", "bb1", "cc1", "dd1");
        List<String> row3 = CollUtil.newArrayList("aa2", "bb2", "cc2", "dd2");
        List<String> row4 = CollUtil.newArrayList("aa3", "bb3", "cc3", "dd3");
        List<String> row5 = CollUtil.newArrayList("aa4", "bb4", "cc4", "dd4");

        List<List<String>> rows = CollUtil.newArrayList(row1, row2, row3, row4, row5);

        //通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter("d:/writeTest.xlsx");
        //通过构造方法创建writer
        //ExcelWriter writer = new ExcelWriter("d:/writeTest.xls");

        //跳过当前行，既第一行，非必须，在此演示用
        writer.passCurrentRow();

        //合并单元格后的标题行，使用默认标题样式
        writer.merge(rows.size() - 1, "测试标题");
        //一次性写出内容
        writer.write(rows);
        //关闭writer，释放内存
        writer.close();

        //==============LineCaptcha 线段干扰的验证码==============
        //定义图形验证码的长和宽
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);

        //图形验证码写出，可以写出到文件，也可以写出到流
        lineCaptcha.write("d:/line.png");
        //输出code
        //log.info(lineCaptcha.getCode());
        //验证图形验证码的有效性，返回boolean值
        lineCaptcha.verify("1234");

        //重新生成验证码
        lineCaptcha.createCode();
        lineCaptcha.write("d:/line.png");
        //新的验证码
        //log.info(lineCaptcha.getCode());
        //验证图形验证码的有效性，返回boolean值
        lineCaptcha.verify("1234");

    }

}
