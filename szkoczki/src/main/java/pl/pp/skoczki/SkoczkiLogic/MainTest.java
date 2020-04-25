package pl.pp.skoczki.SkoczkiLogic;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.pp.skoczki.SkoczkiLogic.game.GameController;

/**
 *
 * @author Konrad
 */

@SpringBootApplication
public class MainTest {
    public static void main(String[] args){
        var applicationContext = SpringApplication.run(MainTest.class, args);
        GameController controller = applicationContext.getBean("gameController", GameController.class);
    }
}
