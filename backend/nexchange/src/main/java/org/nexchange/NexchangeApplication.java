package org.nexchange;

import com.github.jeffreyning.mybatisplus.conf.EnableMPP;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableMPP
public class NexchangeApplication {

    public static void main(String[] args) {
        SpringApplication.run(NexchangeApplication.class, args);
    }

}
