package pl.advanced;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;

@Configuration
@EnableMBeanExport
public class Main {
    @Bean
    public App jmxResource() {
        return new App();
    }

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        App resource = context.getBean(App.class);
        Thread.sleep(Long.MAX_VALUE);
    }
}
