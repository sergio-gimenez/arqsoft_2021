/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.upc.etsetb.arqsoft.complexcalculator.frameworks.ui;

import edu.upc.etsetb.arqsoft.complexcalculator.entities.CalculatorFactory;
import edu.upc.etsetb.arqsoft.complexcalculator.entities.ComplexCalculator;
import edu.upc.etsetb.arqsoft.complexcalculator.entities.UnknownCalculatorFactoryException;
import edu.upc.etsetb.arqsoft.complexcalculator.entities.impl.DefaultCalculatorFactory;
import edu.upc.etsetb.arqsoft.complexcalculator.frameworks.ui.impl.DefaultUIFactory;
import edu.upc.etsetb.arqsoft.complexcalculator.frameworks.ui.UIFactory;
import java.util.logging.Level;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 *
 * @author osboxes
 */
public class Main {

    public static void main(String[] args) {
        try {
            CalculatorFactory defaultCalculatorFactory = DefaultCalculatorFactory.getInstance("DEFAULT");
            UIFactory defaultUIFactory = DefaultUIFactory.getInstance("DEFAULT");
            ComplexCalculator complexCalculator = defaultUIFactory.createComplexCalculator();

            Scanner scan = new Scanner(System.in);
            System.out.println("Select type of User Interface:\n\n  TEXTUAL: T\n  Graphic: G");
            String uiTyped = scan.nextLine();

            UserInterface userInterface = defaultUIFactory.createUserInterface(uiTyped, defaultCalculatorFactory, complexCalculator);
            userInterface.proceed();

        } catch (UnknownUserInterfaceException | UnknownCalculatorFactoryException | UnknownUIFactoryException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }

    }

}
