package festivalplanner_guiModules.inputfields;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.PasswordField;

public class FPPasswordField extends PasswordField implements FPInputFields {

    public FPPasswordField() {

        defaultStyle();

        setAlignment(Pos.CENTER);

        setPromptText("Password");

        setMinSize(250, 48);
        setPrefSize(250, 48);
        setMaxSize(250, 48);

    }

    @Override
    public void defaultStyle() {

        setStyle("-fx-background-color: #FFFFFF; " +
                "-fx-text-fill: #B76F88; " +
                "-fx-background-radius: 50; " +
                "-fx-font-size: 15; " +
                "-fx-font-family: Helvetica; ");
    }

    @Override
    public void invalidInputStyle() {

        setStyle("-fx-background-color: #FFFFFF; " +
                "-fx-text-fill: red; " +
                "-fx-background-radius: 50; " +
                "-fx-font-size: 15; " +
                "-fx-font-family: Helvetica; " +
                "-fx-border-color: red; " +
                "-fx-border-width: 2px; " +
                "-fx-border-radius: 50;");

    }
}
