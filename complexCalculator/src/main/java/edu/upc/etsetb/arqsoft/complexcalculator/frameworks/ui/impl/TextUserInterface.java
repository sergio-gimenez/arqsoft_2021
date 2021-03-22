/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.upc.etsetb.arqsoft.complexcalculator.frameworks.ui.impl;

import edu.upc.etsetb.arqsoft.complexcalculator.entities.CalculatorFactory;
import edu.upc.etsetb.arqsoft.complexcalculator.entities.ComplexCalculator;
import edu.upc.etsetb.arqsoft.complexcalculator.entities.ExpressionException;
import edu.upc.etsetb.arqsoft.complexcalculator.entities.MyNumber;
import edu.upc.etsetb.arqsoft.complexcalculator.entities.SimpleExpression;
import edu.upc.etsetb.arqsoft.complexcalculator.frameworks.ui.UIFactory;
import edu.upc.etsetb.arqsoft.complexcalculator.frameworks.ui.UserInterface;
import edu.upc.etsetb.arqsoft.complexcalculator.usecases.ExpressionGenerator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gerard
 */
public class TextUserInterface implements UserInterface {

    private UIFactory uiFactory;
    private CalculatorFactory calcFactory;
    private ComplexCalculator calculator;

    protected TextUserInterface(UIFactory uiFactory, CalculatorFactory calcFactory, ComplexCalculator calculator) {
        this.uiFactory = uiFactory;
        this.calcFactory = calcFactory;
        this.calculator = calculator;
    }

    public static UserInterface getInstance(UIFactory uiFactory, CalculatorFactory calcFactory, ComplexCalculator calculator) {
        return new TextUserInterface(uiFactory, calcFactory, calculator);
    }

    @Override
    public void proceed() {
        Scanner scan = new Scanner(System.in);
        String input = "";
        Boolean end = false;
        do {
            System.out.println("Enter something to compute or 'end' for finalizing the program");
            input = scan.nextLine();
            end = input.equalsIgnoreCase("END");
            if (!end) {
                try {
                    this.processLine(input);
                } catch (ExpressionException ex) {
                    Logger.getLogger(TextUserInterface.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } while (!end);

    }

    public void processLine(String line) throws ExpressionException {
        String expression = "";
        String variableName = "";
        if (line.contains("=")) {
            String[] lineGroup = line.split("=");
            expression = lineGroup[1].trim();
            variableName = lineGroup[0].replaceAll("\\s+", "");
        }else{
            expression = line;
        }
        ExpressionGenerator expressionGenerator = this.uiFactory.createExpressionGenerator(this.calculator.memory());
        SimpleExpression simpleExpression = expressionGenerator.getFromString(expression, calcFactory);
        MyNumber myNumber = simpleExpression.evaluate();
        if (!variableName.isEmpty()) {
            this.calculator.defineVariable(this.calcFactory.createVariable(variableName, myNumber));
        }

        System.out.println(myNumber.getValue());
    }

}
