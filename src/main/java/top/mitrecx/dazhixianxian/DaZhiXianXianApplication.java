package top.mitrecx.dazhixianxian;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication(scanBasePackages = {"top.mitrecx.dazhixianxian"})
@MapperScan(basePackages = {"top.mitrecx.dazhixianxian.dal.dao"})
public class DaZhiXianXianApplication {

    public static void main(String[] args) {
        SpringApplication.run(DaZhiXianXianApplication.class, args);
    }

}
