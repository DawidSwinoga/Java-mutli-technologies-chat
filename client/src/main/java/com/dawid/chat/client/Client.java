package com.dawid.chat.client;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(exclude = {SpringApplicationAdminJmxAutoConfiguration.class})
@Slf4j
public class Client extends Application {
    private ConfigurableApplicationContext springContext;
    private Parent rootNode;
    private FXMLLoader fxmlLoader;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        springContext = SpringApplication.run(Client.class);
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(springContext::getBean);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        fxmlLoader.setLocation(getClass().getResource("/fxml/sample.fxml"));
        rootNode = fxmlLoader.load();

        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(rootNode, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        springContext.getBean(SampleController.class).onClose();
        springContext.close();
        super.stop();
        Platform.exit();
        System.exit(0);
    }
}
