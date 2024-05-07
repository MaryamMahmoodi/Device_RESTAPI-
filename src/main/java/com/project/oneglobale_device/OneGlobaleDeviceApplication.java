package com.project.oneglobale_device;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.project"})
public class OneGlobaleDeviceApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(OneGlobaleDeviceApplication.class, args);
    }

}
