package info.yywang.micro.dubbo.commands;

import info.yywang.micro.dubbo.lifecycles.ShutdownLatch;
import org.springframework.boot.CommandLineRunner;

/**
 * @author yanyan.wang
 * @date 2016-08-13 15:18
 */
public class DubboServiceLatchCommandLineRunner implements CommandLineRunner {

    private String domain = "info.yywang.micro";

    @Override
    public void run(String... strings) throws Exception {
        ShutdownLatch shutdownLatch = new ShutdownLatch(domain);
        shutdownLatch.await();
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
