package info.yywang.micro.dubbo.lifecycles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author yanyan.wang
 * @date 2016-08-13 15:04
 */
public class ShutdownLatch implements ShutdownLatchMBean {

    private AtomicBoolean running = new AtomicBoolean(false);

    private long checkIntervalInSeconds = 10;

    private String domain = "info.yywang.micro";

    private Logger logger = LoggerFactory.getLogger(ShutdownLatch.class);

    public ShutdownLatch(String domain) {
        this.domain = domain;
    }

    public ShutdownLatch() {

    }

    public void await() {
        if (running.compareAndSet(false, true)) {
            try {
                MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
                mBeanServer.registerMBean(this, new ObjectName(domain, "name", "ShutdownLatch"));
                while (running.get()) {
                    TimeUnit.SECONDS.sleep(checkIntervalInSeconds);
                }
            } catch (InstanceAlreadyExistsException e) {
                logger.error(e.getMessage(), e);
            } catch (MBeanRegistrationException e) {
                logger.error(e.getMessage(), e);
            } catch (NotCompliantMBeanException e) {
                logger.error(e.getMessage(), e);
            } catch (MalformedObjectNameException e) {
                logger.error(e.getMessage(), e);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public String shutdown() {
        if (running.compareAndSet(true, false)) {
            return "shutdown signal send,shutting down...";
        } else {
            return "shutdown signal had been sent, no need again and again...";
        }
    }

}
