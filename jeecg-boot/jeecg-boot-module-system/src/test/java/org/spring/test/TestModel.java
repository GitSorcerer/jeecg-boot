package org.spring.test;

import org.junit.Test;
import org.spring.config.Config;
import org.spring.model.OneModel;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author: GH
 * @Date: 2019/12/1 21:59
 * @Version 1.0
 */
public class TestModel {

    @Test
    public void test() {
        // git commit -m "翻译插件 id:44347d6cc1e05ecb 密钥:kz5tklhIprdOAjTBPhr1uZuRfyfwDUwf"
        //
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(Config.class);
//        context.register(Config.class);
        OneModel bean = context.getBean(OneModel.class);
        System.out.println(bean);
    }
}
