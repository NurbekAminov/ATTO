package dasturlash.uz;

import dasturlash.uz.config.ApplicationConfig;
import dasturlash.uz.controller.MainController;
import dasturlash.uz.repository.TableRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        TableRepository tableRepository = applicationContext.getBean("tableRepository", TableRepository.class);
        tableRepository.createTables();

        MainController mainController = applicationContext.getBean("mainController", MainController.class);
        mainController.start();
    }
}