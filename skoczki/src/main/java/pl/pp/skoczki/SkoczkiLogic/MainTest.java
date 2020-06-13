package pl.pp.skoczki.SkoczkiLogic;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;




@SpringBootApplication
public class MainTest extends Application {

    private static ApplicationContext context;

    public static void main(String[] args) {
        context = new SpringApplicationBuilder(MainTest.class)
                .web(WebApplicationType.NONE).run(args);
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setScene(context.getBean("chessBoard", Scene.class));
        stage.show();
    }
}
