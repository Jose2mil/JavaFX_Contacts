package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public ListView listOfContacts;
    public VBox menuContainer;
    public Label nameLabel;
    public TextField nameTextFiield;
    public Label emailLabel;
    public TextField emailTextField;
    public Label numberLabel;
    public TextField numberTextField;
    public Button addButton;
    public Button removeButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) 
    {
        String path = "contacts.txt";
        BufferedReader fileContactsRead;
        ArrayList<Contact> contacts = new ArrayList<>();
        
        if(! new File(path).exists())
        {
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setHeaderText(path + " not found");
            dialog.showAndWait();
        }
        
        else 
        {
            try 
            {
                fileContactsRead = new BufferedReader(new FileReader(path));
                String nameOfContact;
                do 
                {
                    nameOfContact = fileContactsRead.readLine();
                    if(nameOfContact != null && ! (nameOfContact.equals("")))
                    {
                        contacts.add(
                                new Contact(
                                        nameOfContact, 
                                        fileContactsRead.readLine(), //E-mail
                                        Integer.parseInt(
                                                fileContactsRead.readLine()))); //Phone Number
                        listOfContacts.getItems().add(nameOfContact);
                    }
                }
                while (nameOfContact != null && ! (nameOfContact.equals("")));
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
            
            listOfContacts.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>()
            {
                @Override
                public void changed(ObservableValue<? extends String> obs,
                                    String oldValue, String newValue)
                {
                    if(newValue == null)
                        return;

                    for(Contact c : contacts)
                    {
                        if(newValue.equals(c.getName()))
                        {
                            nameTextFiield.setText(c.name);
                            emailTextField.setText(c.email);

                            if(c.number != 0)
                                numberTextField.setText(c.number + "");
                            else
                                numberTextField.setText("");
                        }
                    }
                }
            });

            addButton.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent actionEvent)
                {
                    String nameAdded = nameTextFiield.getText();
                    if(!IOUtils.validateName(nameAdded))
                        nameAdded = "[Without name]";

                    boolean isRepeated = false;
                    int indexCopy = 1;
                    for(Contact c : contacts)
                    {
                        if(isRepeated && (nameAdded + " (" + indexCopy + ")").equals(c.name))
                            indexCopy++;
                        else if(nameAdded.equals(c.name))
                        {
                            isRepeated = true;
                        }
                    }
                    if(isRepeated)
                        nameAdded = nameAdded + " (" + indexCopy + ")";

                    String emailAdded = emailTextField.getText();
                    if(!IOUtils.validateEmail(emailAdded))
                        emailAdded = "";

                    int numberAdded = 0;
                    if(IOUtils.validateNumber(numberTextField.getText()))
                        numberAdded = Integer.parseInt(numberTextField.getText());

                    contacts.add(new Contact(
                            nameAdded,
                            emailAdded,
                            numberAdded)
                            );
                    Collections.sort(contacts);

                    refreshListOfContacts(contacts);
                    saveData(path, contacts);

                    Alert dialog = new Alert(Alert.AlertType.ERROR);
                    dialog.setHeaderText("Contact added!");
                    dialog.showAndWait();
                }
            });

            removeButton.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent actionEvent)
                {
                    if(listOfContacts.getSelectionModel().getSelectedItem() == null)
                    {
                        Alert dialog = new Alert(Alert.AlertType.ERROR);
                        dialog.setHeaderText("Contact not selected");
                        dialog.showAndWait();
                        return;
                    }

                    String nameRemove = listOfContacts.getSelectionModel().getSelectedItem().toString();
                    boolean found = false;
                    int pos = 0;

                    for(int i = 0; i < contacts.size(); i++)
                    {
                        if(nameRemove.equals(contacts.get(i).name))
                        {
                            found = true;
                            pos = i;
                        }
                    }

                    contacts.remove(pos);
                    nameTextFiield.setText("");
                    emailTextField.setText("");
                    numberTextField.setText("");

                    refreshListOfContacts(contacts);
                    saveData(path, contacts);

                    Alert dialog = new Alert(Alert.AlertType.ERROR);
                    dialog.setHeaderText("Contact removed!");
                    dialog.showAndWait();
                }
            });
        }
    }

    private void refreshListOfContacts(ArrayList<Contact> contacts)
    {
        listOfContacts.getItems().clear();

        for (Contact c : contacts)
            listOfContacts.getItems().add(c.getName());
    }

    private void saveData(String path, ArrayList<Contact> contacts)
    {
        try
        {
            FileWriter saveDataFile = new FileWriter(path);

            for (Contact c : contacts)
            {
                saveDataFile.write(c.name + "\n");
                saveDataFile.write(c.email + "\n");
                saveDataFile.write(c.number + "\n");
            }

            saveDataFile.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
