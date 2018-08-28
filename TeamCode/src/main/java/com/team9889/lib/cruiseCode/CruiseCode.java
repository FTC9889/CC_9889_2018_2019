package com.team9889.lib.cruiseCode;

import com.team9889.ftc2019.auto.actions.Action;
import com.team9889.ftc2019.auto.actions.Drive;
import com.team9889.ftc2019.auto.actions.Telemetry;
import com.team9889.ftc2019.auto.actions.Turn;
import com.team9889.ftc2019.auto.actions.Wait;
import com.team9889.lib.android.FileReader;
import com.team9889.lib.android.FileWriter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by joshua9889 on 8/28/2018.
 */

public class CruiseCode {
    private FileReader fileReader;
    private FileWriter fileWriter;

    private ArrayList<Action> classes; // List of classes

    public static void main(String... args){
        ArrayList<Action> actions = new ArrayList<>();
        actions.add(new Drive());
        actions.add(new Turn());
        actions.add(new Wait());
        actions.add(new Telemetry());

        CruiseCode code = new CruiseCode("test", actions);
        code.run();
    }

    public CruiseCode(String fileName, ArrayList<Action> actions){
        fileReader = new FileReader(fileName + ".auto");
        fileWriter = new FileWriter(fileName+".log");
        classes = actions;
    }

    public void run(){
        String[] commandsFromFile = fileReader.lines();

        // Iterate over all lines
        for (int i = 0; i < commandsFromFile.length; i++) {
            // Remove unneeded characters
            commandsFromFile[i] = commandsFromFile[i].replace(" ", ""); // Remove spaces
            commandsFromFile[i] = commandsFromFile[i].replace("\t", ""); // Remove tabs
            commandsFromFile[i] = commandsFromFile[i].replace("\n", ""); // Remove newlines

            // Don't look for a valid command if the line is a comment
            if (!commandsFromFile[i].startsWith("#")) {
                // If Drive(10,0) is the command, splitting it will give us [Drive, 10,0)]
                String[] commands = commandsFromFile[i].split("\\(");

                // Command Name from filename.auto
                commands[0] = commands[0].toLowerCase();

                if(commands.length>=2){
                    // Check command list
                    for (int j = 0; j < classes.size(); j++) {

                        // Current Action we are checking
                        Action currentClass = classes.get(j); // Current class to check

                        // if commands[1] = 10,0), ["10", "0)"]
                        String[] parameters = commands[1].split(",");

                        // remove ")" from last parameter
                        parameters[parameters.length-1] = parameters[parameters.length-1].replace(")", "");

                        // Class name lower class
                        String actionNameLowerCase = currentClass.getClass().getSimpleName().toLowerCase();

                        if (commands[0].equals(actionNameLowerCase)) {
                            String argsToAction = parameters[0];
                            for (int k = 1; k < parameters.length; k++) {
                                argsToAction += "," + parameters[k];
                            }

                            fileWriter.write("Starting to Run " + currentClass.getClass().getSimpleName()
                                    + " at " + new SimpleDateFormat("hh:mm:ss:S MM.dd.yyyy").format(new Date()));
                            currentClass.setup(argsToAction);

                            runAction(currentClass);

                            fileWriter.write("Finished Running " + currentClass.getClass().getSimpleName()
                                    + " at " + new SimpleDateFormat("hh:mm:ss:S MM.dd.yyyy").format(new Date()));
                        }
                    }
                }
            }
        }

        fileWriter.close();
        fileReader.close();

    }

    private void runAction(Action action){
        action.start();

        while (!action.isFinished())
            action.update();

        action.done();
    }

    private void printArray(String[] array){
        for (String anArray : array) {
            System.out.println(anArray);
        }
    }

}
