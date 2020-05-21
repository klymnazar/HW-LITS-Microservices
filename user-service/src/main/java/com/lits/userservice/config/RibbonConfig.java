package com.lits.userservice.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;

@RibbonClient(name = "course-service")
public class RibbonConfig {

    @Bean
    public IRule loadBalancingRule() {
        return new RoundRobinRule();
    }

}
