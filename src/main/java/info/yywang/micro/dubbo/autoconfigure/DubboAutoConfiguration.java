package info.yywang.micro.dubbo.autoconfigure;

import info.yywang.micro.dubbo.commands.DubboServiceLatchCommandLineRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @author yanyan.wang
 * @date 2016-08-13 15:34
 */
@Configuration
@Order
public class DubboAutoConfiguration {

    protected Logger logger = LoggerFactory.getLogger(DubboAutoConfiguration.class);

    @Value("${shutdown.latch.domain.name:com.iydsj.sw}")
    private String shutdownLatchDomainName;

    @Bean
    @ConditionalOnClass(name = "com.alibaba.dubbo.rpc.Exporter")
    public DubboServiceLatchCommandLineRunner configureDubboxServiceLatchCommandLineRunner() {

        logger.debug("DubboAutoConfiguration enabled by adding DubboxServiceLatchCommandLineRunner.");

        DubboServiceLatchCommandLineRunner runner = new DubboServiceLatchCommandLineRunner();
        runner.setDomain(shutdownLatchDomainName);
        return runner;
    }

}
