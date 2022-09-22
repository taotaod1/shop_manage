import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.junit.Test;

import java.io.File;

/**
 * @author wbt
 * @version v1.0
 * @project shop_manage
 * @data 2022/9/22 14:31
 **/
public class testtencent {
    @Test
    public void test() {
        // 1 初始化用户身份信息（secretId, secretKey）。
// SECRETID和SECRETKEY请登录访问管理控制台 https://console.cloud.tencent.com/cam/capi 进行查看和管理
        String secretId = "AKID0mIYYHjvqUrypPikW1FCsHPHtEH3YnlS";
        String secretKey = "RbVnohKfjW3IGxOXP0LPCixvc2A2dx0n";
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
// 2 设置 bucket 的地域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
// clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region("ap-beijing");
        ClientConfig clientConfig = new ClientConfig(region);
// 这里建议设置使用 https 协议
// 从 5.6.54 版本开始，默认使用了 https
        clientConfig.setHttpProtocol(HttpProtocol.https);
// 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);

//        PutObjectResult putObjectResult = cosClient.putObject("shop-manage-1300547616", "myfile1.jpg", new File("C:\\Users\\86155\\Pictures\\QQ图片20220501112312.jpg"));
        System.out.println(cosClient.getObjectUrl("shop-manage-1300547616","myfile1.jpg"));
    }
}
