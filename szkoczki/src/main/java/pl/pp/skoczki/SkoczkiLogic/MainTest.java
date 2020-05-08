package pl.pp.skoczki.SkoczkiLogic;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import pl.pp.skoczki.SkoczkiLogic.game.GameController;

import java.awt.*;


/**
 *
 * @author Konrad
 */


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
