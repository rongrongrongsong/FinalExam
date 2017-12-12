package pkgApp.controller;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import pkgApp.RetirementApp;
import pkgCore.Retirement;

public class RetirementController implements Initializable {


    private RetirementApp mainApp = null;

    @FXML
    private TextField txtYearsToWork;
    @FXML
    private TextField txtAnnualReturn;
    @FXML
    private TextField txtYearsRetired;
    @FXML
    private TextField txtRetiredReturn;
    @FXML
    private TextField txtRequiredIncome;
    @FXML
    private TextField txtMonthlySSI;
    @FXML
    private Label txtYearsToWorkLabel;
    @FXML
    private Label amountToSaveByMonth;
    @FXML
    private Label totalAmountSaved;

    @FXML
    public RetirementApp getMainApp() {
        return mainApp;
    }

    public void setMainApp(RetirementApp mainApp) {
        this.mainApp = mainApp;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void btnClear(ActionEvent event) {
        //	TODO: Clear all the text inputs
        System.out.println("Clear pressed");
        txtYearsToWork.setText("");
        txtAnnualReturn.setText("");
        txtYearsRetired.setText("");
        txtRetiredReturn.setText("");
        txtRequiredIncome.setText("$");
        txtMonthlySSI.setText("$");
        txtRequiredIncome.setStyle("");
        txtMonthlySSI.setStyle("");
        amountToSaveByMonth.setText("$");
        totalAmountSaved.setText("$");
        txtYearsToWorkLabel.setText("");
    }

    @FXML
    public void btnCalculate(ActionEvent event) {

        txtYearsToWorkLabel.setText("");
       
        String txtYearsToWorkContent = txtYearsToWork.getText();
        String txtAnnualReturnContent = txtAnnualReturn.getText();
        String txtYearsRetiredContent = txtYearsRetired.getText();
        String txtRetiredReturnContent = txtRetiredReturn.getText();
        String txtRequiredIncomeContent = txtRequiredIncome.getText();
        String txtMonthlySSIContent = txtMonthlySSI.getText();

        String errorCotent = "";
        if (txtYearsToWorkContent == null || !txtYearsToWorkContent.matches("^[0-9]+$")) {
            errorCotent = "yearsToWork";
        }
        if (txtAnnualReturnContent == null || !txtAnnualReturnContent.matches("^[0-9.]%")) {
            errorCotent = errorCotent.length() > 0 ? (errorCotent + ",yourAnnualReturn") : "yourAnnualReturn";
        }
        if (txtYearsRetiredContent == null || !txtYearsRetiredContent.matches("^[0-9]+$")) {
            errorCotent = errorCotent.length() > 0 ? (errorCotent + ",YearsyouRetired") : "YearsyouRetired";
        }
        if (txtRetiredReturnContent == null || !txtRetiredReturnContent.matches("^[0-9.]%+$")) {
            errorCotent = errorCotent.length() > 0 ? (errorCotent + ",yourAnnualReturn") : "youAnnualReturn";
        }
        if (txtRequiredIncomeContent == null || !txtRequiredIncomeContent.matches("^[$][0-9,.]+$")) {
            errorCotent = errorCotent.length() > 0 ? (errorCotent + ",yourRequiredIncome") : "yourRequiredIncome";
        }
        if (txtMonthlySSIContent == null || !txtMonthlySSIContent.matches("^[$][0-9,.]+$")) {
            errorCotent = errorCotent.length() > 0 ? (errorCotent + ",yourMonthlySSIContent") : "yourMonthlySSIContent";
        }
        if (errorCotent.length() > 0) {
            txtYearsToWorkLabel.setText("format error:"+errorCotent);
        } else {
            int yearToWork = Integer.parseInt(txtYearsToWorkContent);
            double annualReturn = Double.parseDouble(txtAnnualReturnContent.substring(0, txtAnnualReturnContent.length() - 1)) * 0.01;

            int yearsRetired = Integer.parseInt(txtYearsRetiredContent);
            double retiredAnnualReturn = Double.parseDouble(txtRetiredReturnContent.substring(0, txtRetiredReturnContent.length() - 1)) * 0.01;
            double requiredIncome = Double.parseDouble(txtRequiredIncomeContent.replace(",", "").replace("$", ""));
            double monthlySSI = Double.parseDouble(txtMonthlySSIContent.replace(",", "").replace("$", ""));

            Retirement retirement = new Retirement(yearToWork, annualReturn, yearsRetired, retiredAnnualReturn, requiredIncome, monthlySSI);

            DecimalFormat df = new DecimalFormat("#,###.00");

            double  doubleTotalAmountSaved = retirement.TotalAmountSaved();
            double doubleAmountToSaveByMonth = retirement.AmountToSave(doubleTotalAmountSaved);

            String strTotalAmountSaved= df.format(-doubleTotalAmountSaved);
            String strAmountToSaveByMonth = df.format(doubleAmountToSaveByMonth);
            System.out.println("amountToSaveByMonth="+strTotalAmountSaved+",totalAmountSaved="+strAmountToSaveByMonth);
       
            amountToSaveByMonth.setText("$     "+strAmountToSaveByMonth);
            totalAmountSaved.setText("$     "+strTotalAmountSaved);
        }
    }

    @FXML
    public void checkYearsToWork(KeyEvent event) {
        String txtYearsToWorkContent = txtYearsToWork.getText();
        System.out.println("checkYearsToWork:txtYearsToWorkContent=" + txtYearsToWorkContent);
        if (txtYearsToWorkContent.length() > 0 && !txtYearsToWorkContent.matches("^[0-9]+")) {
            txtYearsToWork.setStyle("-fx-text-fill: #ff0000;");
        } else {
            txtYearsToWork.setStyle("");
        }
    }

    @FXML
    public void checkAnnualReturn(KeyEvent event) {
        String txtAnnualReturnContent = txtAnnualReturn.getText();
        if (txtAnnualReturnContent != null && !txtAnnualReturnContent.matches("^[0-9.%]+")) {
            if (txtAnnualReturnContent.length() > 0 && !"%".equals(txtAnnualReturnContent.substring(txtAnnualReturnContent.length() - 1))) {
                txtAnnualReturnContent = txtAnnualReturnContent + "%";
                txtAnnualReturn.setText(txtAnnualReturnContent);
            }
            txtAnnualReturn.setStyle("-fx-text-fill: #ff0000;");
        } else {
            if (txtAnnualReturnContent != null && txtAnnualReturnContent.length() > 0 && !"%".equals(txtAnnualReturnContent.substring(txtAnnualReturnContent.length() - 1))) {
                txtAnnualReturnContent = txtAnnualReturnContent + "%";
                txtAnnualReturn.setText(txtAnnualReturnContent);
            }
            txtAnnualReturn.setStyle("");
            if (txtAnnualReturnContent != null && txtAnnualReturnContent.length() > 0 && txtAnnualReturnContent.indexOf("%") < (txtAnnualReturnContent.length() - 1)) {
                txtAnnualReturn.setStyle("-fx-text-fill: #ff0000;");
            }

        }
    }

    @FXML
    public void checkYearsRetired(KeyEvent event) {

        String txtYearsRetiredContent = txtYearsRetired.getText();
        if (txtYearsRetiredContent.length() > 0 && !txtYearsRetiredContent.matches("^[0-9]+$")) {
            txtYearsRetired.setStyle("-fx-text-fill: #ff0000;");
        } else {
            txtYearsRetired.setStyle("");
        }
    }

    @FXML
    public void checkMonthlySSI(KeyEvent event) {
        String txtMonthlySSIContent = txtMonthlySSI.getText();
        if (txtMonthlySSIContent.length() > 0 && !txtMonthlySSIContent.matches("^[0-9.,$]+$")) {
            if (!"$".equals(txtMonthlySSIContent.substring(0, 1))) {
                txtMonthlySSI.setText("$" + txtMonthlySSIContent);
            }
            txtMonthlySSI.setStyle("-fx-text-fill: #ff0000;");
        } else {
            if (txtMonthlySSIContent != null && txtMonthlySSIContent.length() > 0 && !"$".equals(txtMonthlySSIContent.substring(0, 1))) {
                txtMonthlySSI.setText("$" + txtMonthlySSIContent);
            }
            txtMonthlySSI.setStyle("");
        }
        txtMonthlySSI.end();
    }
    @FXML
    public void checkRequiredIncome(KeyEvent event) {
        String txtRequiredIncomeContent = txtRequiredIncome.getText();
        if (txtRequiredIncomeContent.length() > 0 && !txtRequiredIncomeContent.matches("^[0-9.,$]+$")) {
            if (!"$".equals(txtRequiredIncomeContent.substring(0, 1))) {
                txtRequiredIncome.setText("$" + txtRequiredIncomeContent);
            }
            txtRequiredIncome.setStyle("-fx-text-fill: #ff0000;");

        } else {
            if (txtRequiredIncomeContent != null && txtRequiredIncomeContent.length() > 0 && !("$".equals(txtRequiredIncomeContent.substring(0, 1)))) {
                txtRequiredIncome.setText("$" + txtRequiredIncomeContent);
            }
            txtRequiredIncome.setStyle("");
        }
        txtRequiredIncome.end();
    }


    @FXML
    public void checkRetiredReturn(KeyEvent event) {

        String txtRetiredReturnContent = txtRetiredReturn.getText();
        {
            if (txtRetiredReturnContent != null && !txtRetiredReturnContent.matches("^[0-9.%]+")) {
                if (txtRetiredReturnContent.length() > 0 && !"%".equals(txtRetiredReturnContent.substring(txtRetiredReturnContent.length() - 1))) {
                    txtRetiredReturnContent = txtRetiredReturnContent + "%";
                    txtRetiredReturn.setText(txtRetiredReturnContent);
                }
                txtRetiredReturn.setStyle("-fx-text-fill: #ff0000;");
            } else {
                if (txtRetiredReturnContent != null && txtRetiredReturnContent.length() > 0 && !"%".equals(txtRetiredReturnContent.substring(txtRetiredReturnContent.length() - 1))) {
                    txtRetiredReturnContent = txtRetiredReturnContent + "%";
                    txtRetiredReturn.setText(txtRetiredReturnContent);
                }
                txtRetiredReturn.setStyle("");
                if (txtRetiredReturnContent != null && txtRetiredReturnContent.length() > 0 && txtRetiredReturnContent.indexOf("%") < (txtRetiredReturnContent.length() - 1)) {
                    txtRetiredReturn.setStyle("-fx-text-fill: #ff0000;");
                }

            }
        }
    }

}
