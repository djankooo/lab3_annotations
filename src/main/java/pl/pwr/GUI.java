package pl.pwr;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;

public class GUI extends Application {

    private MyClassLoader mcl = null;
    private Class newClass = null;
    private Object newObject = null;
    private Method[] newMethods = null;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private User user = null;

    private ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private Validator validator = validatorFactory.getValidator();

    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText((String)null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    public void start(Stage primaryStage) {


        primaryStage.setTitle("Forms");
        GridPane gridPane = createPane();
        setUIControlls(primaryStage, gridPane);
        primaryStage.setScene(new Scene(gridPane, 1000, 500));
        primaryStage.show();
    }

    private GridPane createPane(){

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(40, 40, 40, 40));

        gridPane.setHgap(10);
        gridPane.setVgap(10);

        return gridPane;
    }

    private void setUIControlls(Stage primaryStage, GridPane gridPane){

        VBox vbox = new VBox();
        StackPane stackPane = new StackPane();

        HBox footerHBox = new HBox();
        HBox footer2HBox = new HBox();

        TextArea pathTextArea = new TextArea("C:\\Users\\djankooo\\Desktop\\PWr\\PWJJ\\test");

        Button findClassesButton = new Button("Find class");
        Button loadClassesButton = new Button("Load class");
        Button unloadClassesButton = new Button("Empty");
        Button createObjectButton = new Button("Create object");
        Button use = new Button("Use!");

        ListView classListView = new ListView();

        ArrayList <Node> nodesToRemove = new ArrayList<>();

        gridPane.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.5));
        gridPane.prefHeightProperty().bind(primaryStage.heightProperty().multiply(1.0));

        vbox.prefWidthProperty().bind(gridPane.widthProperty().multiply(0.50));
        vbox.prefHeightProperty().bind(gridPane.heightProperty().multiply(1.0));

        stackPane.prefWidthProperty().bind(gridPane.widthProperty().multiply(0.5));
        stackPane.prefHeightProperty().bind(gridPane.widthProperty().multiply(1.0));

        pathTextArea.prefWidthProperty().bind(vbox.widthProperty().multiply(1.0));
        pathTextArea.prefHeightProperty().bind(vbox.heightProperty().multiply(0.1));

        findClassesButton.prefWidthProperty().bind(vbox.widthProperty().multiply(0.2));
        findClassesButton.prefHeightProperty().bind(vbox.heightProperty().multiply(0.1));

        classListView.prefWidthProperty().bind(vbox.widthProperty().multiply(1.0));
        classListView.prefHeightProperty().bind(vbox.heightProperty().multiply(0.8));

        loadClassesButton.prefWidthProperty().bind(vbox.widthProperty().multiply(0.5));
        loadClassesButton.prefHeightProperty().bind(vbox.heightProperty().multiply(0.1));

        unloadClassesButton.prefWidthProperty().bind(vbox.widthProperty().multiply(0.5));
        unloadClassesButton.prefHeightProperty().bind(vbox.heightProperty().multiply(0.1));

        createObjectButton.prefWidthProperty().bind(vbox.widthProperty().multiply(0.5));
        createObjectButton.prefHeightProperty().bind(vbox.heightProperty().multiply(0.1));

        use.prefWidthProperty().bind(vbox.widthProperty().multiply(0.5));
        use.prefHeightProperty().bind(vbox.heightProperty().multiply(0.1));

        footerHBox.getChildren().addAll(loadClassesButton, createObjectButton);
        footer2HBox.getChildren().addAll(unloadClassesButton, use);

        vbox.getChildren().addAll(pathTextArea, classListView, footerHBox, footer2HBox);

        gridPane.add(vbox, 0, 0);
        gridPane.add(stackPane, 1, 0);

        loadClassesButton.setOnAction(event -> {
            mcl = new MyClassLoader(pathTextArea.getText());
            mcl.searchInDirectory();
            classListView.getItems().addAll(mcl.getClassNamesInDirectory());
            stackPane.getChildren().removeAll();
            loadClassesButton.setDisable(true);
        });

        use.setOnAction(event -> {
            if(newMethods!=null && newClass!=null){
                try {

                Method getLogin = newClass.getDeclaredMethod("getLoginTextLabel");
                getLogin.setAccessible(true);
                String login = (String) getLogin.invoke(newObject);
                System.out.println("Wywolane z refleksji : " + login);

                Method getName = newClass.getDeclaredMethod("getFirstNameTextLabel");
                getName.setAccessible(true);
                String name = (String) getName.invoke(newObject);
                System.out.println("Wywolane z refleksji : " + name);

                Method getLastName = newClass.getDeclaredMethod("getLastNameTextLabel");
                getLastName.setAccessible(true);
                String lastName = (String) getLastName.invoke(newObject);
                System.out.println("Wywolane z refleksji : " + lastName);

                Method getPassword = newClass.getDeclaredMethod("getPasswordPasswordField");
                getPassword.setAccessible(true);
                String password = (String) getPassword.invoke(newObject);
                System.out.println("Wywolane z refleksji : " + password);

                user = new User();

                user.setLogin(login);
                user.setName(name);
                user.setLastName(lastName);
                user.setPassword(password);

                Set<ConstraintViolation<User>> violations = validator.validate(user);

                for (ConstraintViolation<User> violation : violations) {
                    //log.error(violation.getMessage());

                    this.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Błąd!", violation.getMessage());
                }

                if(user!=null){
                    Writer writer = new FileWriter("C:\\Users\\djankooo\\Desktop\\PWr\\PWJJ\\JSON.txt");
                    gson.toJson(user, writer);
                    writer.flush();
                    writer.close();
                }

                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        createObjectButton.setOnAction(event -> {

            String s = classListView.getSelectionModel().getSelectedItems().toString();

            try {
                newClass = mcl.loadClass(String.valueOf(s.substring(1, s.length() - 1)));
                newObject = newClass.newInstance();
                newMethods = newClass.getDeclaredMethods();
//                for( Method m : newMethods){
//                    System.out.println(m);
//                }
                stackPane.getChildren().clear();
                stackPane.getChildren().add((Pane) newObject);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        });
    }
}

