/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author rawan_ITI
 */
public class Notepad extends Application {

    Scene scene;
    MenuBar menu;
    TextArea editor;
    String extension = ".txt";
    int counter = 0;
    String Path = "D:\\ITI\\Core\\JavaFX\\DayTwo\\Notepad\\files\\";
    String FileName = null;
    String FilePath = null;
    FileChooser fileChooser = new FileChooser();

    private void saveFile(Stage primaryStage) {
        FileWriter myWriter;
        File file;
        if (FilePath == null) {
            saveAsFile(primaryStage);
            return;
        }
        try {
            file = new File(FilePath);
            file.createNewFile();
            myWriter = new FileWriter(file);
            myWriter.write(editor.getText());
            myWriter.close();
            FileName = file.getName();
            FilePath = file.getAbsolutePath();
        } catch (IOException ex) {
            Logger.getLogger(Notepad.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void newNotePad() {
        File file;
        FileWriter myWriter;
        FileName = "MyNotepad";
        TextInputDialog dialog = new TextInputDialog(FileName);
        dialog.setTitle("New File");
        dialog.setHeaderText("Enter File Name or Use Default");
        Optional<String> res = dialog.showAndWait();
        if (res.isPresent()) {
            FileName = res.get();
        } else {
            return;
        }
        try {
            if (counter == 0) {
                file = new File(Path + FileName + extension);
            } else {
                file = new File(Path + FileName + counter + extension);
            }
            file.createNewFile();
            myWriter = new FileWriter(file);
            myWriter.write(editor.getText());
            myWriter.close();
            System.out.println(file.getAbsolutePath());
            counter++;

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("File Saved Message");
            alert.setHeaderText("File Location");
            String fileLocation = file.getAbsolutePath();
            alert.setContentText(fileLocation);
            alert.show();
            FileName = null;
            FilePath = null;
        } catch (IOException ex) {
            Logger.getLogger(Notepad.class.getName()).log(Level.SEVERE, null, ex);
        }

        editor.clear();

    }

    private void saveAsFile(Stage primaryStage) {
        FileWriter myWriter;
        fileChooser.setInitialDirectory(new File(this.Path));
        File file = fileChooser.showSaveDialog(primaryStage);

        if (file != null) {
            System.out.println(file.getAbsolutePath()
                    + "  selected");
            try {
                file = new File(file.toString() + ".txt");
                file.createNewFile();
                myWriter = new FileWriter(file);
                myWriter.write(editor.getText());
                myWriter.close();
                FileName = file.getName();
                FilePath = file.getAbsolutePath();
            } catch (IOException ex) {
                Logger.getLogger(Notepad.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private void openFile(Stage primaryStage) {

        fileChooser.setInitialDirectory(new File(this.Path));
        File file = fileChooser.showOpenDialog(primaryStage);

        try {
            FileReader fileReader = new FileReader(file);
            //char newText = (char)(fileReader.read());

            int i;
            String newText = "";
            while ((i = fileReader.read()) != -1) {
                newText += String.valueOf((char) i);
            }
            editor.setText(newText);
            fileReader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Notepad.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Notepad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void exitNotepad(Stage primaryStage) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Exit Program");
        String s = "Do you want to save before exiting?";
        alert.setContentText(s);

        Optional<ButtonType> result = alert.showAndWait();

        if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
            saveFile(primaryStage);
        } else {
            Platform.exit();
        }
    }

    private void aboutDialog() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("MyNotepad Information");
        alert.setHeaderText("About Us");
        String s = "This program is created by Rawan Aboegila";
        alert.setContentText(s);
        alert.show();
    }

    private void menuEventHandler(Stage primaryStage) {

        MenuItem newItem = menu.getMenus().get(0).getItems().get(0);
        MenuItem openItem = menu.getMenus().get(0).getItems().get(1);
        MenuItem saveItem = menu.getMenus().get(0).getItems().get(2);
        MenuItem saveAsItem = menu.getMenus().get(0).getItems().get(3);
        MenuItem exitItem = menu.getMenus().get(0).getItems().get(4);

        newItem.setOnAction((ActionEvent event) -> {
            newNotePad();
        });

        openItem.setOnAction((ActionEvent event) -> {
            openFile(primaryStage);
        });

        saveItem.setOnAction((ActionEvent event) -> {
            System.out.println(saveItem.getAccelerator());
            saveFile(primaryStage);
        });

        saveAsItem.setOnAction((ActionEvent event) -> {
            saveAsFile(primaryStage);
        });

        exitItem.setOnAction((ActionEvent event) -> {
            exitNotepad(primaryStage);
        });

        MenuItem undoItem = menu.getMenus().get(1).getItems().get(0);
        MenuItem cutItem = menu.getMenus().get(1).getItems().get(1);
        MenuItem copyItem = menu.getMenus().get(1).getItems().get(2);
        MenuItem pasteItem = menu.getMenus().get(1).getItems().get(3);
        MenuItem deleteItem = menu.getMenus().get(1).getItems().get(4);
        MenuItem selectItem = menu.getMenus().get(1).getItems().get(5);

        undoItem.setOnAction((ActionEvent event) -> {
            editor.undo();
        });
        cutItem.setOnAction((ActionEvent event) -> {
            editor.cut();
        });
        copyItem.setOnAction((ActionEvent event) -> {
            editor.copy();
        });
        pasteItem.setOnAction((ActionEvent event) -> {
            editor.paste();
        });
        deleteItem.setOnAction((ActionEvent event) -> {
            editor.selectAll();
            editor.cut();
        });
        selectItem.setOnAction((ActionEvent event) -> {
            editor.selectAll();
        });

        MenuItem aboutItem = menu.getMenus().get(2).getItems().get(0);
        aboutItem.setOnAction((ActionEvent event) -> {
            aboutDialog();
        });

    }

    private void createMenu() {
        Menu fileMenu = new Menu("File");
        Menu editMenu = new Menu("Edit");
        Menu helpMenu = new Menu("Help");

        MenuItem newItem = new MenuItem("New");
        newItem.setAccelerator(KeyCombination.keyCombination("Ctrl+n"));

        MenuItem openItem = new MenuItem("Open");
        openItem.setAccelerator(KeyCombination.keyCombination("Ctrl+o"));

        MenuItem saveItem = new MenuItem("Save");
        saveItem.setAccelerator(KeyCombination.keyCombination("Ctrl+s"));

        MenuItem saveAsItem = new MenuItem("Save As");
        saveAsItem.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+s"));

        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setAccelerator(new KeyCodeCombination(KeyCode.ESCAPE));

        MenuItem undoItem = new MenuItem("Undo");
        undoItem.setAccelerator(KeyCombination.keyCombination("Ctrl+z"));

        MenuItem cutItem = new MenuItem("Cut");
        cutItem.setAccelerator(KeyCombination.keyCombination("Ctrl+x"));

        MenuItem copyItem = new MenuItem("Copy");
        copyItem.setAccelerator(KeyCombination.keyCombination("Ctrl+c"));

        MenuItem pasteItem = new MenuItem("Paste");
        pasteItem.setAccelerator(KeyCombination.keyCombination("Ctrl+v"));

        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setAccelerator(KeyCombination.keyCombination("Ctrl+d"));

        MenuItem selectItem = new MenuItem("Select All");
        selectItem.setAccelerator(KeyCombination.keyCombination("Ctrl+a"));

        MenuItem aboutItem = new MenuItem("About");
        aboutItem.setAccelerator(KeyCombination.keyCombination("Ctrl+h"));
        
        fileMenu.getItems().add(newItem);
        fileMenu.getItems().add(openItem);
        fileMenu.getItems().add(saveItem);
        fileMenu.getItems().add(saveAsItem);
        fileMenu.getItems().add(exitItem);

        editMenu.getItems().add(undoItem);
        editMenu.getItems().add(cutItem);
        editMenu.getItems().add(copyItem);
        editMenu.getItems().add(pasteItem);
        editMenu.getItems().add(deleteItem);
        editMenu.getItems().add(selectItem);

        helpMenu.getItems().add(aboutItem);

        menu = new MenuBar();
        menu.getMenus().add(fileMenu);
        menu.getMenus().add(editMenu);
        menu.getMenus().add(helpMenu);

    }

    @Override
    public void init() throws Exception {
        super.init();
        createMenu();
        BorderPane root = new BorderPane();
        root.setTop(menu);

        editor = new TextArea();
        editor.setWrapText(true);
        root.setCenter(editor);

        scene = new Scene(root, 600, 400);
    }

    @Override
    public void start(Stage primaryStage) {
        menuEventHandler(primaryStage);
        primaryStage.setTitle("My Notepad");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
