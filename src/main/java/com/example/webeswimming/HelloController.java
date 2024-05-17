package com.example.webeswimming;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;


import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;

public class HelloController {

    @FXML
    Button SEARCH_FOR_MEMBER_BUTTON = new Button();
    @FXML
    GridPane grid = new GridPane();
    @FXML
    RadioButton ADMIN_RB = new RadioButton();
    @FXML
    RadioButton TRAINER_RB = new RadioButton();
    @FXML
    RadioButton ACCOUNTANT_RB = new RadioButton();
    @FXML
    VBox mainContainerID = new VBox();
    @FXML
    ScrollPane scrollPaneForData = new ScrollPane();
    @FXML
    VBox VBoxForScrollPane = new VBox();
    @FXML
    Button LOGIN_BUTTON = new Button();
    @FXML
    VBox VBOX_LOWERLEFT = new VBox();
    @FXML
    Button ACCOUTNANT_EDIT_PAID_BUTTON = new Button("Toggle members paid status");
    @FXML
    Button ADMIN_CREATE_MEMBER_BUTTON = new Button("Create new Member");
    @FXML
    Button ADMIN_EDIT_MEMBER_BUTTON = new Button("Edit Member");
    @FXML
    Button CREATE_RESULT_BUTTON = new Button("Create new Result");
    @FXML
    Button LIST_MEMBERS_BUTTON = new Button("List Members");
    @FXML
    boolean pass = true; // stand in for password function
    @FXML
    protected void initialize() {
        ToggleGroup radioGroup = new ToggleGroup();
        ADMIN_RB.setToggleGroup(radioGroup);
        TRAINER_RB.setToggleGroup(radioGroup);
        ACCOUNTANT_RB.setToggleGroup(radioGroup);

        // makes the vbox scrollable if enough data is inputted
        VBoxForScrollPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
        VBoxForScrollPane.setMinHeight(Region.USE_PREF_SIZE);
        VBOX_LOWERLEFT.getChildren().addAll(LIST_MEMBERS_BUTTON);

        //Button events
        LIST_MEMBERS_BUTTON.setOnAction(event -> listMembers());
        SEARCH_FOR_MEMBER_BUTTON.setOnAction(event -> MemberList.lookUpMember(mainContainerID));
    }
    @FXML
    protected void LOGIN_BUTTONOnAction() {
        if (ADMIN_RB.isSelected() && pass) {
            mainContainerID.getChildren().clear();
            ADMIN_CREATE_MEMBER_BUTTON.setOnAction(event -> setUpNewMemberForm());
            ADMIN_EDIT_MEMBER_BUTTON.setOnAction(event -> {
                try {
                    editMember();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
            mainContainerID.getChildren().addAll(ADMIN_CREATE_MEMBER_BUTTON, ADMIN_EDIT_MEMBER_BUTTON);
        }
        if (TRAINER_RB.isSelected() && pass) {
            mainContainerID.getChildren().clear();
            CREATE_RESULT_BUTTON.setOnAction(event -> createNewResult());
            mainContainerID.getChildren().addAll(CREATE_RESULT_BUTTON);
        }
        if (ACCOUNTANT_RB.isSelected() && pass) {
            mainContainerID.getChildren().clear();
            ACCOUTNANT_EDIT_PAID_BUTTON.setOnAction(event -> editMemberHasPaidStatus());
            mainContainerID.getChildren().addAll(ACCOUTNANT_EDIT_PAID_BUTTON);
        }
    }

    public void editMember() throws IOException, ClassNotFoundException {
        mainContainerID.getChildren().clear();
        Label whatID = new Label("What ID do you want to edit?");
        TextField whatIDTextfield = new TextField();
        Button SubmitButton = new Button("Submit");
        whatIDTextfield.setMaxWidth(250);
        mainContainerID.getChildren().addAll(whatID, whatIDTextfield, SubmitButton);
        ArrayList<Member> loopings = FileCont.readMemberListFromFile("members_data.ser");
        SubmitButton.setOnAction(event -> {
            for (Member m : loopings) {
                if (m.memberID == Integer.parseInt(whatIDTextfield.getText())) {
                    mainContainerID.getChildren().clear();

                    TextField firstNameField = new TextField(m.firstName);
                    firstNameField.setMaxWidth(250);

                    TextField surNameField = new TextField(m.surName);
                    surNameField.setMaxWidth(250);

                    TextField genderField = new TextField(m.gender);
                    genderField.setMaxWidth(250);

                    TextField ageField = new TextField(m.age + ""); //dodgy??
                    ageField.setMaxWidth(250);

                    TextField phoneField = new TextField(m.phoneNumber + ""); //dodgy??
                    phoneField.setMaxWidth(250);

                    Button saveChangesButton = new Button("Save Changes");
                    saveChangesButton.setOnAction(event2 -> {
                        m.firstName = firstNameField.getText();
                        m.surName = surNameField.getText();
                        m.gender = genderField.getText();
                        m.age = Integer.parseInt(ageField.getText());
                        m.phoneNumber = Integer.parseInt(phoneField.getText());
                        try {
                            FileCont.writeToFile("members_data.ser", loopings);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });

                    mainContainerID.getChildren().addAll(firstNameField,
                            surNameField,
                            genderField,
                            ageField,
                            phoneField,
                            saveChangesButton);

                }
            }
        });




    }

    public void editMemberHasPaidStatus(){
        mainContainerID.getChildren().clear();
        listMembers();
        Label whatMemberLabel = new Label("What member ID do you want to change payment status on?");
        TextField whatMemberTextField = new TextField();
        whatMemberTextField.setMaxWidth(250);
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> {
            try {
                ArrayList<Member> loopdilopp = FileCont.readMemberListFromFile("members_data.ser");
                for (Member member : loopdilopp) {
                    if (member.memberID == Integer.parseInt(whatMemberTextField.getText())) {
                        mainContainerID.getChildren().clear();
                        Button toggle = new Button("Toggle");
                        toggle.setOnAction(event2 -> {
                            System.out.println("toggled");
                            member.hasPaid = !member.hasPaid;
                            try {
                                FileCont.writeToFile("members_data.ser", loopdilopp);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                        mainContainerID.getChildren().addAll(new Label(member.toString(), toggle));

                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        mainContainerID.getChildren().addAll(whatMemberLabel, whatMemberTextField, submitButton);
    }

    public void CLEAR_BUTTONOnAction() {
        VBoxForScrollPane.getChildren().clear();
    }

    public void listMembers() {
        try{
            VBoxForScrollPane.getChildren().clear();
            ArrayList<Member> listToLoop = FileCont.readMemberListFromFile("members_data.ser");
            assert listToLoop != null : "listToLoop is null";
            for (Member member : listToLoop) {
                Label memberLabel = new Label();
                memberLabel.setText(member.toString());

                VBoxForScrollPane.getChildren().addAll(memberLabel);
            }
        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void createNewResult() {
        mainContainerID.getChildren().clear();
        Label CreateResult = new Label("What ID do you want to create result for?");
        TextField ID = new TextField();
        ID.setMaxWidth(250);

        Button SubmitButton = new Button("Submit");
        SubmitButton.setOnAction(event -> {
            boolean found = false;

            int inputId = Integer.parseInt(ID.getText());
            ArrayList<Member> listFromFile = new ArrayList<>();
            try {
                listFromFile = FileCont.readMemberListFromFile("members_data.ser");
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            for (int i = 0; i < listFromFile.size(); i++){
                if (listFromFile.get(i).memberID == inputId) {
                    found = true;
                    mainContainerID.getChildren().clear();

                    Member currentMember = listFromFile.get(i);

                    Label disciplineResult = new Label("Discipline");
                    TextField disciplineTextField = new TextField();
                    disciplineTextField.setMaxWidth(250);

                    Label CompetitionResult = new Label("Competition");
                    TextField CompetitionTextField = new TextField();
                    CompetitionTextField.setMaxWidth(250);

                    Label resultInSecondsResult = new Label("Result in Seconds");
                    TextField resultInSecondsTextField = new TextField();
                    resultInSecondsTextField.setMaxWidth(250);

                    Button CreateResultButton = new Button("Create Result");
                    ArrayList<Member> finalListFromFile = listFromFile;

                    CreateResultButton.setOnAction(anotherEvent -> {
                        Result newResult = new Result();
                        newResult.discipline = disciplineTextField.getText();
                        newResult.resultInSeconds = Integer.parseInt(resultInSecondsTextField.getText());
                        newResult.competitionName = CompetitionTextField.getText();

                        currentMember.addResult(newResult);
                        System.out.println(finalListFromFile.get(4));

                        Label membertestlabel = new Label(currentMember.toString());
                        VBoxForScrollPane.getChildren().clear();
                        VBoxForScrollPane.getChildren().addAll(membertestlabel);
                        try {
                            FileCont.writeToFile("members_data.ser", finalListFromFile);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    });
                    mainContainerID.getChildren().addAll(disciplineResult,
                            disciplineTextField,
                            CompetitionResult,
                            CompetitionTextField,
                            resultInSecondsResult,
                            resultInSecondsTextField,
                            CreateResultButton);
                    break;
                }
            }
            if (!found) {
                Label notFoundLabel = new Label("ID Not Found");
                mainContainerID.getChildren().add(notFoundLabel);
            }

        });
        mainContainerID.getChildren().addAll(CreateResult, ID, SubmitButton);
    }

    public void setUpNewMemberForm() {
        mainContainerID.getChildren().clear();
        Label firstNameLabel = new Label();
        firstNameLabel.setText("First Name");
        TextField firstNameField = new TextField();
        firstNameField.setMaxWidth(250);
        mainContainerID.getChildren().add(firstNameLabel);
        mainContainerID.getChildren().add(firstNameField);


        Label surNameLabel = new Label();
        surNameLabel.setText("Surname");
        TextField surNameField = new TextField();
        surNameField.setMaxWidth(250);
        mainContainerID.getChildren().add(surNameLabel);
        mainContainerID.getChildren().add(surNameField);


        Label genderLabel = new Label();
        genderLabel.setText("Gender");
        TextField genderField = new TextField();
        genderField.setMaxWidth(250);
        mainContainerID.getChildren().add(genderLabel);
        mainContainerID.getChildren().add(genderField);


        Label ageLabel = new Label();
        ageLabel.setText("Age");
        TextField ageField = new TextField();
        ageField.setMaxWidth(250);
        mainContainerID.getChildren().add(ageLabel);
        mainContainerID.getChildren().add(ageField);


        Label phoneNumberLabel = new Label();
        phoneNumberLabel.setText("Phone Number");
        TextField phoneNumberField = new TextField();
        phoneNumberField.setMaxWidth(250);
        mainContainerID.getChildren().add(phoneNumberLabel);
        mainContainerID.getChildren().add(phoneNumberField);


        Button createMemberButton = new Button();
        createMemberButton.setOnAction(event -> {
            Member member = new Member();
            member.firstName = firstNameField.getText();
            member.surName = surNameField.getText();
            member.gender = genderField.getText();
            member.age = Integer.parseInt(ageField.getText());
            member.phoneNumber = Integer.parseInt(phoneNumberField.getText());
            MemberList.memberList.add(member);
            try {
                ArrayList<Member> toAddNewMember = new ArrayList<>();
                try {
                    toAddNewMember = FileCont.readMemberListFromFile("members_data.ser");
                }catch (EOFException e) {
                    e.printStackTrace();
                    System.out.println("First member added");
                }
                assert toAddNewMember != null : "toAddNewMember is null";
                toAddNewMember.add(member);

                FileCont.writeToFile("members_data.ser", toAddNewMember);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            Label memberLabel = new Label();
            memberLabel.setText("\nNew member added successfully" + member);
            VBoxForScrollPane.getChildren().addAll(memberLabel);
            System.out.println(member);
        });
        createMemberButton.setText("Create Member");
        mainContainerID.getChildren().add(createMemberButton);


    }
}
