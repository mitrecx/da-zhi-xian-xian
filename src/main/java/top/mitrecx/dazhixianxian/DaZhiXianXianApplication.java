package top.mitrecx.dazhixianxian;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = {"top.mitrecx.dazhixianxian"})
@MapperScan(basePackages = {"top.mitrecx.dazhixianxian.mapper"})
public class DaZhiXianXianApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(DaZhiXianXianApplication.class, args);
    }

}
