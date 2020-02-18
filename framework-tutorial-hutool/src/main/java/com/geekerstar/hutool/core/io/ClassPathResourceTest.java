package com.geekerstar.hutool.core.io;

import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.lang.Console;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static cn.hutool.core.io.resource.ResourceUtil.getResource;

/**
 * @author geekerstar
 * date: 2019/11/30 15:43
 * description:
 */
public class ClassPathResourceTest {

    @Test
    public void test() throws IOException {
//        String path = "config.properties";
//        InputStream in = this.class.getResource(path).openStream();
        ClassPathResource resource = new ClassPathResource("example.properties");
        Properties properties = new Properties();
        properties.load(resource.getStream());
        Console.log("Properties: {}", properties);

    }
}
