package edu.ib;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.mariuszgromada.math.mxparser.Function;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private LineChart<?, ?> graph;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private ChoiceBox<String> choiceBoxMethod;
    ObservableList<String> methods = FXCollections.observableArrayList();

    @FXML
    private TextField textFieldFunction;

    @FXML
    private Button btnCalculate;

    @FXML
    private Text textWrongFunction;

    @FXML
    private Text textRoot;

    @FXML
    private TextField textFieldRight;

    @FXML
    private TextField textFieldLeft;

    @FXML
    private Text textException;

    @FXML
    void calculate(ActionEvent event) {
        plot(event);
        String choiceBox = choiceBoxMethod.getValue();
        RootOfFunction r = new RootOfFunction();
        Function function = new Function("f(x)=" + textFieldFunction.getText());
        switch (choiceBox) {
            case "Bisection":
                textRoot.setText("Root of function " + r.bisection(0, 10, 0.001, (x) -> function.calculate(x)));
                break;
            case "False position":
                textRoot.setText("Root of function " + r.falsi(0, 10, 0.001, (x) -> function.calculate(x)));
                break;
            case "Fixed-point":
                textRoot.setText("Root of function " + r.fixedpoint(0, 0.001, (x) -> function.calculate(x)));
                break;
            case "Newton-Raphson":
                textRoot.setText("Root of function " + r.newtonraphson(0, 0.001, (x) -> function.calculate(x)));
                break;
            case "Secant":
                textRoot.setText("Root of function " + r.sieczna(0, 10, 0.001, (x) -> function.calculate(x)));
                break;
        }
    }

    @FXML
    void plot(ActionEvent event) throws IllegalArgumentException{
        Function function = new Function("f(x)=" + textFieldFunction.getText());
        if (function.checkSyntax()) {
            textException.setVisible(false);
            try {
                if(Integer.parseInt(textFieldLeft.getText())>Integer.parseInt(textFieldRight.getText())){
                    throw new IllegalArgumentException("left value < right value");
                }

                graph.getData().clear();
                XYChart.Series series = new XYChart.Series();
                double sample = (Integer.parseInt(textFieldRight.getText())-Integer.parseInt(textFieldLeft.getText()))/100;
                if(sample == 0) sample = 0.1;
                for (int i = 0; i <= (Integer.parseInt(textFieldRight.getText())-Integer.parseInt(textFieldLeft.getText()))/sample; i++) {
                    double x = Integer.parseInt(textFieldLeft.getText())+i*sample;
                    if (!Double.isNaN(function.calculate(x)) && !Double.isInfinite(function.calculate(x)))
                        series.getData().add(new XYChart.Data(x, function.calculate(x)));
                }
                graph.getData().add(series);
                textWrongFunction.setVisible(false);
            } catch (Exception e) {
                e.getStackTrace();
                textException.setVisible(true);
            }
        } else {
            textWrongFunction.setVisible(true);
        }
    }

    @FXML
    void initialize() {
        assert graph != null : "fx:id=\"graph\" was not injected: check your FXML file 'graph.fxml'.";
        assert xAxis != null : "fx:id=\"xAxis\" was not injected: check your FXML file 'graph.fxml'.";
        assert yAxis != null : "fx:id=\"yAxis\" was not injected: check your FXML file 'graph.fxml'.";
        assert choiceBoxMethod != null : "fx:id=\"choiceBoxMethod\" was not injected: check your FXML file 'graph.fxml'.";
        assert textFieldFunction != null : "fx:id=\"textFieldFunction\" was not injected: check your FXML file 'graph.fxml'.";
        assert btnCalculate != null : "fx:id=\"btnCalculate\" was not injected: check your FXML file 'graph.fxml'.";
        assert textWrongFunction != null : "fx:id=\"textWrongFunction\" was not injected: check your FXML file 'graph.fxml'.";
        assert textRoot != null : "fx:id=\"textRoot\" was not injected: check your FXML file 'graph.fxml'.";
        assert textFieldRight != null : "fx:id=\"textFieldRight\" was not injected: check your FXML file 'graph.fxml'.";
        assert textFieldLeft != null : "fx:id=\"textFieldLeft\" was not injected: check your FXML file 'graph.fxml'.";
        assert textException != null : "fx:id=\"textException\" was not injected: check your FXML file 'graph.fxml'.";
        String a = "Bisection";
        String b = "False position";
        String c = "Fixed-point";
        String d = "Newton-Raphson";
        String e = "Secant";
        methods.addAll(a, b, c, d, e);
        choiceBoxMethod.getItems().addAll(methods);
        choiceBoxMethod.setValue("Bisection");

    }
}
