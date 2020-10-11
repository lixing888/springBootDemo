package com.springboot.demo.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.airec.model.v20181012.DescribeInstanceRequest;
import com.aliyuncs.airec.model.v20181012.DescribeInstanceResponse;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

/**
 * @program: springBootDemo
 * @description: 阿里云智能推荐系统
 * @author: lixing
 * @create: 2020-10-11 11:01
 **/
public class DescribeInstance {
    private static String accessKeyID="";
    private static String accessKeySecret="";
    public static void main(String args[]) {
        // 1.创建 Profile。
        // 生成 IClientProfile 的对象 profile，该对象存放 AccessKeyID 和 AccessKeySecret 和默认的地域信息，如示例中的 cn-hangzhou
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyID, accessKeySecret);
        // 2.设置 Endpoint。
        // 调用 DefaultProfile 的 addEndpoint 方法，传入 regionId、product 名称、服务接入地址。
        DefaultProfile.addEndpoint("cn-hangzhou", "Airec", "airec.cn-hangzhou.aliyuncs.com");
        // 3.创建 Client。
        // 从 IClientProfile 类中再生成 IAcsClient 的对象 client，后续获得 response 都需要从 IClientProfile 中获得
        DefaultAcsClient client = new DefaultAcsClient(profile);
        // 4.创建 Request。
        // 创建一个对应方法的 Request，类的命名规则一般为 API 的方法名加上 Request，如获取实例详情的 API 方法名为 DescribeInstance，
        // 那么对应的请求类名就是 DescribeInstanceRequest，直接使用构造函数生成一个默认的类 request。
        DescribeInstanceRequest request = new DescribeInstanceRequest();
        // 5.设置 Request的参数。
        // 设置 Request 的参数。请求类生成好之后需要通过 Request 类的 setXxx 方法设置必要的信息，即 API 参数中必须要提供的信息，
        // 如这里设置实例 ID，则设置对应的实例 ID。API 返回的数据为 JSON 格式，则这里需要设置 AcceptFormat 为 JSON。
        request.setInstanceId("airec-cn-xxx");
        request.setAcceptFormat(FormatType.JSON);
        try {
            // 6.使用 Client 对应的方法传入 Request，获得 Response。
            DescribeInstanceResponse response = client.getAcsResponse(request);
            // 7.在 Response 中获得返回的参数值。
            // 接着可以调用 response 中对应的 getXxx 方法获得返回的参数值了，如获得实例的名称，则调用 getName 方法。
            System.out.println(response.getResult().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
