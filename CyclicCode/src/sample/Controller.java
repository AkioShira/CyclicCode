package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.classes.CycleCode;
import sample.classes.GenPolynome;

import java.io.IOException;
import java.util.List;

public class Controller {
    private Stage mainStage;
    @FXML
    private TextField textErrorField;
    @FXML
    private TextField textGenField;
    @FXML
    private TextArea workArea;
    @FXML
    private Button correctBut;
    @FXML
    private Label resultLabel;
    @FXML
    private ListView<String> listPolynomes;
    static int cursor = 0;
    MyException me = new MyException();

    public void setStage(Stage stage) {
        mainStage = stage;
    }

    @FXML
    void infoClick(MouseEvent event) throws IOException {
        Stage info = new Stage();
        Pane root = FXMLLoader.load(getClass().getResource("infoSample.fxml"));
        Scene scene = new Scene(root);
        info.setTitle("Информация");
        info.setScene(scene);
        info.initStyle(StageStyle.UTILITY);
        info.setResizable(false);
        info.show();
    }

    @FXML
    void resultClick(MouseEvent event) {
        String errorPolynome = textErrorField.getText();
        String genPolynome = textGenField.getText();
        try {
            try {
                me.getException(errorPolynome);
                me.getException(genPolynome);
                CycleCode cl = new CycleCode();
                cl.driver(errorPolynome, genPolynome);
                workArea.setText(cl.getCorrectedString());
                resultLabel.setText(cl.getCorrectedPolynome());
            } catch (ErrorException ex) {
                showMessage(ex.getMessage());
            }
        }catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка при работе");
            alert.setContentText("Ошибка!");
            alert.showAndWait();
        }
    }

    void showMessage(String s)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(s);
        alert.setContentText("Ошибка!");
        alert.showAndWait();
    }

    @FXML
    void combClick(MouseEvent event) {
        listPolynomes.getItems().clear();
        try{
            String genPolynome = textGenField.getText();
            me.getExceptionPolynome(genPolynome);
            GenPolynome gp = new GenPolynome();
            gp.driver(genPolynome);
            List<String> polynomes = gp.getPolynomes();
            for (int i = 0; i < polynomes.size(); i++)
                listPolynomes.getItems().add(polynomes.get(i).toString());
        }catch(ErrorException ex)
        {
            showMessage(ex.getMessage());
        }
        cursor=0;
        correctBut.setDisable(true);
    }

    @FXML
    void listClick(MouseEvent event) {
        textErrorField.setText(listPolynomes.getSelectionModel().getSelectedItem());
        correctBut.setDisable(false);
    }

    public static String replaceCharAt(String s) {
        String ch="";
        String s1 = s.substring(0,cursor+1);
        if(s1=="0")ch = "1";
        else if(s1=="1") ch = "0";
        return s.substring(0,cursor) + ch + s.substring(cursor+1);

    }

    @FXML
    void leftClick(MouseEvent event) {
        textErrorField.setText(listPolynomes.getSelectionModel().getSelectedItem());
        StringBuilder stringBuilder = new StringBuilder();
        String s = textErrorField.getText();
        stringBuilder.append(s);
        if(stringBuilder.charAt(cursor)=='1') stringBuilder.setCharAt(cursor, '0');
        else if(stringBuilder.charAt(cursor)=='0') stringBuilder.setCharAt(cursor, '1');
        textErrorField.setText(stringBuilder.toString());
        if(cursor>0)cursor--;
    }

    @FXML
    void rightClick(MouseEvent event) {
        textErrorField.setText(listPolynomes.getSelectionModel().getSelectedItem());
        StringBuilder stringBuilder = new StringBuilder();
        String s = textErrorField.getText();
        stringBuilder.append(s);
        if(stringBuilder.charAt(cursor)=='1') stringBuilder.setCharAt(cursor, '0');
        else if(stringBuilder.charAt(cursor)=='0') stringBuilder.setCharAt(cursor, '1');
        textErrorField.setText(stringBuilder.toString());
        if(cursor<stringBuilder.length()-1)cursor++;
    }
}
