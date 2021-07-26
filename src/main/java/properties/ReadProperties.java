package properties;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * @Classname ReadProperties
 * @Description
 *  Different ways to read properties file
 * @Date 2021-07-20 12:05 PM
 * @Created by eenheni
 */
public class ReadProperties {

    public static void main(String[] args) throws IOException {

        Properties props = new Properties();
        // 1. ClassLoader to read Properties file
        //默认目录是"src/main/resources/", 所以直接写文件名即可
        InputStream is = ReadProperties.class.getClassLoader()
                .getResourceAsStream("default.properties");

        props.load(is);
        System.out.println(props.getProperty("use.host.logging"));

        // 2. 使用InPutStream流读取properties文件
        BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/default.properties"));
        props.load(bufferedReader);
        System.out.println(props.getProperty("use.host.logging"));


        //3. ResourceBundle to read properties file
        ResourceBundle resource = ResourceBundle.getBundle("default");
        System.out.println(resource.getString("use.host.logging"));

    }
}
