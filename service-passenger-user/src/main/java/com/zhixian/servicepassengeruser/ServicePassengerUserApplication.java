package com.zhixian.servicepassengeruser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ServicePassengerUserApplication {

    public static void main(String[] args) {

        SpringApplication.run(ServicePassengerUserApplication.class, args);
    }

}
