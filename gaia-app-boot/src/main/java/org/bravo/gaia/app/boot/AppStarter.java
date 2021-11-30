package org.bravo.gaia.app.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;

import java.util.concurrent.CountDownLatch;

/**
 * @author lijian
 * 应用启动器
 */
public class AppStarter {
	
	private static final Logger LOG = LoggerFactory.getLogger(AppStarter.class);

    /**
     * 调用spring boot启动方法启动spring容器
     * @param args 系统参数
     * @return spring容器对象
     */
	public static ApplicationContext start(String... args){
		ConfigurableApplicationContext context = SpringApplication.run(StandardSource.class, args);
		LOG.info("application server started!");
		return context;
	}

    /**
     * 调用spring boot启动方法启动spring容器；
     * 当webEnvironment为false时调用时调用，用于阻塞当前线程，让spring容器启动完成之后当前进程不结束
     * @param contextStartedCallback 当spring容器启动完成后的回调函数接口
     * @param args 系统参数
     * @throws InterruptedException
     */
	public static void blockingStart(ContextStartedCallback contextStartedCallback, String... args) throws InterruptedException {
		CountDownLatch cdl = new CountDownLatch(1);
		ConfigurableApplicationContext context = SpringApplication.run(StandardSource.class, args);
		if (contextStartedCallback != null) {
			contextStartedCallback.execute(context);
		}
		LOG.info("application server started!");
		cdl.await();
	}

    /**
     * 调用spring boot启动方法启动spring容器；
     * 当webEnvironment为false时调用时调用，用于阻塞当前线程，让spring容器启动完成之后当前进程不结束
     * @param args 系统参数
     * @throws InterruptedException
     */
	public static void blockingStart(String... args) throws InterruptedException{
        blockingStart(null, args);
	}

    /**
     * 使用spring boot builder构造启动器启动spring容器
     * @param builder spring boot builder
     * @param args 系统参数
     */
    public static void startByBuilder(SpringApplicationBuilder builder, String... args) {
        builder.sources(StandardSource.class).run(args);
    }

    /**
     * 调用spring boot启动方法启动spring容器
     * @param args 系统参数
     * @return spring容器对象
     */
    public static ApplicationContext startWithoutSecurity(String... args){
        ConfigurableApplicationContext context = SpringApplication.run(SimpleSource.class, args);
        LOG.info("application server started!");
        return context;
    }

    /**
     * 调用spring boot启动方法启动spring容器；
     * 当webEnvironment为false时调用时调用，用于阻塞当前线程，让spring容器启动完成之后当前进程不结束
     * @param contextStartedCallback 当spring容器启动完成后的回调函数接口
     * @param args 系统参数
     * @throws InterruptedException
     */
    public static void blockingStartWithoutSecurity(ContextStartedCallback contextStartedCallback, String... args) throws InterruptedException {
        CountDownLatch cdl = new CountDownLatch(1);
        ConfigurableApplicationContext context = SpringApplication.run(SimpleSource.class, args);
        if (contextStartedCallback != null) {
            contextStartedCallback.execute(context);
        }
        LOG.info("application server started!");
        cdl.await();
    }

    /**
     * 调用spring boot启动方法启动spring容器；
     * 当webEnvironment为false时调用时调用，用于阻塞当前线程，让spring容器启动完成之后当前进程不结束
     * @param args 系统参数
     * @throws InterruptedException
     */
    public static void blockingStartWithoutSecurity(String... args) throws InterruptedException{
        blockingStartWithoutSecurity(null, args);
    }

    /**
     * 使用spring boot builder构造启动器启动spring容器
     * @param builder spring boot builder
     * @param args 系统参数
     */
    public static void startByBuilderWithoutSecurity(SpringApplicationBuilder builder, String... args) {
        builder.sources(SimpleSource.class).run(args);
    }

    @EnableAutoConfiguration
    @ImportResource("classpath*:META-INF/spring/module-*.xml")
    public static class StandardSource {

    }

    @EnableAutoConfiguration(exclude={SecurityAutoConfiguration.class})
    @ImportResource("classpath*:META-INF/spring/module-*.xml")
    public static class SimpleSource {

    }

    /**
     * spring boot启动器执行完成之后的函数回调接口
     */
	@FunctionalInterface
	public interface ContextStartedCallback{
        /**
         * 执行
         * @param context   应用上下文
         */
		void execute(ConfigurableApplicationContext context);
	}
	
}
