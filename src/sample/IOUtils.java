package sample;

import javafx.scene.control.Alert;

abstract public class IOUtils
{
    static public boolean validateNumber(String number)
    {
        try
        {
            Integer.parseInt(number);
        }
        catch(NumberFormatException e)
        {
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setHeaderText("Invalid number!");
            dialog.showAndWait();
            return false;
        }
        return true;
    }

    static public boolean validateEmail(String email)
    {
        int posSymbol1 = email.indexOf("@");
        int posSymbol2 = email.indexOf(".");

        if(posSymbol1 > 0 &&
                posSymbol2 > 0 &&
                posSymbol2 < email.length() - 1 &&
                posSymbol1 < posSymbol2 - 1)
            return true;

        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setHeaderText("Invalid e-mail!");
        dialog.showAndWait();
        return false;
    }

    static public boolean validateName(String name)
    {
        if(name.equals(""))
            return false;

        return true;
    }
}
