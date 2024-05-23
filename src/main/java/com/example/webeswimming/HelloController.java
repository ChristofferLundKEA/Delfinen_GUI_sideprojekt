package com.example.webeswimming;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;


import java.util.ArrayList;
import java.util.Comparator;


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
    Button ACCOUNTANT_SEE_NONPAYERS = new Button("See non-payers");
    @FXML
    Button ADMIN_CREATE_MEMBER_BUTTON = new Button("Create new Member");
    @FXML
    Button ADMIN_EDIT_MEMBER_BUTTON = new Button("Edit Member");
    @FXML
    Button ADMIN_REMOVE_MEMBER_BUTTON = new Button("Remove Member");
    @FXML
    Button TRAINER_CREATE_RESULT_BUTTON = new Button("Create new Result");
    @FXML
    Button TRAINER_SEE_RESULT = new Button("See Result");
    @FXML
    Button TRAINER_SEE_TOP_FIVE = new Button("See top 5");
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
            ADMIN_EDIT_MEMBER_BUTTON.setOnAction(event -> editMember());
            ADMIN_REMOVE_MEMBER_BUTTON.setOnAction(event -> removeMember());
            mainContainerID.getChildren().addAll(ADMIN_CREATE_MEMBER_BUTTON, ADMIN_EDIT_MEMBER_BUTTON, ADMIN_REMOVE_MEMBER_BUTTON);
        }
        if (TRAINER_RB.isSelected() && pass) {
            mainContainerID.getChildren().clear();
            TRAINER_CREATE_RESULT_BUTTON.setOnAction(event -> createNewResult());
            TRAINER_SEE_RESULT.setOnAction(event -> seeMemberResult());
            TRAINER_SEE_TOP_FIVE.setOnAction(event -> seeTopFive());
            mainContainerID.getChildren().addAll(TRAINER_CREATE_RESULT_BUTTON, TRAINER_SEE_RESULT, TRAINER_SEE_TOP_FIVE);
        }
        if (ACCOUNTANT_RB.isSelected() && pass) {
            mainContainerID.getChildren().clear();
            ACCOUTNANT_EDIT_PAID_BUTTON.setOnAction(event -> editMemberHasPaidStatus());
            ACCOUNTANT_SEE_NONPAYERS.setOnAction(event -> seeNonPayers());
            mainContainerID.getChildren().addAll(ACCOUTNANT_EDIT_PAID_BUTTON, ACCOUNTANT_SEE_NONPAYERS);
        }
    }

    public void seeTopFive(){
        mainContainerID.getChildren().clear();
        Button seniorButton = new Button("Senior");
        Button juniorButton = new Button("Junior");

        seniorButton.setOnAction(event -> {
            mainContainerID.getChildren().clear();
            final ArrayList<Result>[] topFive = new ArrayList[]{new ArrayList<>()};
            TextField disciplineTextField = new TextField("Butterfly");
            disciplineTextField.setMaxWidth(250);
            Button submitButton = new Button("Submit");
            mainContainerID.getChildren().addAll(disciplineTextField, submitButton);

            submitButton.setOnAction(event1 -> {
                String discipline = disciplineTextField.getText();
                ArrayList<Member> members = FileCont.readMemberListFromFile("members_data.ser");
                for (Member member : members) {
                    if (member.age > 18){
                        for (Result memberResult : member.personalResults) {
                            if (memberResult.discipline.equalsIgnoreCase(discipline)) {
                                topFive[0].add(memberResult);
                            }
                        }
                    }
                }

                // Sort the results by resultInSeconds in ascending order
                topFive[0].sort(Comparator.comparingInt(result -> result.resultInSeconds));

                // Limit to top five results
                if (topFive[0].size() > 5) {
                    topFive[0] = new ArrayList<>(topFive[0].subList(0, 5));
                }

                // Display the top five results
                mainContainerID.getChildren().clear();
                for (Result result : topFive[0]) {
                    Label resultLabel = new Label(result.toString());
                    mainContainerID.getChildren().add(resultLabel);
                }
            });
        });

        juniorButton.setOnAction(event -> {
            mainContainerID.getChildren().clear();
            final ArrayList<Result>[] topFive = new ArrayList[]{new ArrayList<>()};
            TextField disciplineTextField = new TextField("Butterfly");
            disciplineTextField.setMaxWidth(250);
            Button submitButton = new Button("Submit");
            mainContainerID.getChildren().addAll(disciplineTextField, submitButton);

            submitButton.setOnAction(event1 -> {
                String discipline = disciplineTextField.getText();
                ArrayList<Member> members = FileCont.readMemberListFromFile("members_data.ser");
                for (Member member : members) {
                    if (member.age <= 18){
                        for (Result memberResult : member.personalResults) {
                            if (memberResult.discipline.equalsIgnoreCase(discipline)) {
                                topFive[0].add(memberResult);
                            }
                        }
                    }
                }

                // Sort the results by resultInSeconds in ascending order
                topFive[0].sort(Comparator.comparingInt(result -> result.resultInSeconds));

                // Limit to top five results
                if (topFive[0].size() > 5) {
                    topFive[0] = new ArrayList<>(topFive[0].subList(0, 5));
                }

                // Display the top five results
                mainContainerID.getChildren().clear();
                for (Result result : topFive[0]) {
                    Label resultLabel = new Label(result.toString());
                    mainContainerID.getChildren().add(resultLabel);
                }
            });
        });
        mainContainerID.getChildren().addAll(seniorButton, juniorButton);
    }

    public void removeMember(){
        mainContainerID.getChildren().clear();
        Label whatIDToRemoveLabel = new Label("What ID?");
        TextField whatIDToRemoveTextField = new TextField();
        whatIDToRemoveTextField.setMaxWidth(250);
        Button removeMemberButton = new Button("Remove");
        removeMemberButton.setOnAction(event -> {
            ArrayList<Member> looper = FileCont.readMemberListFromFile("members_data.ser");
            for (Member member : looper) {
                if (member.memberID == Integer.parseInt(whatIDToRemoveTextField.getText())) {
                    looper.remove(member);
                    FileCont.writeToFile("members_data.ser", looper);
                    Label removedLabel = new Label("Removed Member");
                    mainContainerID.getChildren().add(removedLabel);
                    break;
                }
            }
        });
        mainContainerID.getChildren().addAll(whatIDToRemoveLabel, whatIDToRemoveTextField, removeMemberButton);
    }

    public void seeMemberResult() {

        mainContainerID.getChildren().clear();
        Label whatIDLabel = new Label("What ID?");
        TextField whatIDField = new TextField();
        whatIDField.setMaxWidth(250);
        Button submitButton = new Button("Submit");

        submitButton.setOnAction(event -> {
            ArrayList<Member> looplings = FileCont.readMemberListFromFile("members_data.ser");
            Label membernameLabel = new Label("Member with ID: " + whatIDField.getText() + "'s results");
            VBoxForScrollPane.getChildren().add(membernameLabel);
            for (Member member : looplings) {

                if (member.memberID == Integer.parseInt(whatIDField.getText())) {
                    Label resultLabel = new Label(member.showPersonalResults());
                    VBoxForScrollPane.getChildren().add(resultLabel);
                }
            }
        });
        mainContainerID.getChildren().addAll(whatIDLabel, whatIDField, submitButton);
    }

    public void seeNonPayers() {
        VBoxForScrollPane.getChildren().clear();
        ArrayList<Member> looptiloop = FileCont.readMemberListFromFile("members_data.ser");
        for (Member member : looptiloop) {
            Label nonPayingMemberLabel = new Label();
            nonPayingMemberLabel.setText(member.toString());
            if(!member.hasPaid) VBoxForScrollPane.getChildren().add(nonPayingMemberLabel);
        }
    }

    public void editMember()  {
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

                            FileCont.writeToFile("members_data.ser", loopings);

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

                ArrayList<Member> loopdilopp = FileCont.readMemberListFromFile("members_data.ser");
                for (Member member : loopdilopp) {
                    if (member.memberID == Integer.parseInt(whatMemberTextField.getText())) {
                        mainContainerID.getChildren().clear();
                        Button toggle = new Button("Toggle");
                        toggle.setOnAction(event2 -> {
                            System.out.println("toggled");
                            member.hasPaid = !member.hasPaid;

                                FileCont.writeToFile("members_data.ser", loopdilopp);

                        });
                        mainContainerID.getChildren().addAll(new Label(member.toString(), toggle));

                    }
                }

        });
        mainContainerID.getChildren().addAll(whatMemberLabel, whatMemberTextField, submitButton);
    }

    public void CLEAR_BUTTONOnAction() {
        VBoxForScrollPane.getChildren().clear();
    }

    public void listMembers() {

            VBoxForScrollPane.getChildren().clear();
            ArrayList<Member> listToLoop = FileCont.readMemberListFromFile("members_data.ser");
            assert listToLoop != null : "listToLoop is null";
            for (Member member : listToLoop) {
                Label memberLabel = new Label();
                memberLabel.setText(member.toString());

                VBoxForScrollPane.getChildren().addAll(memberLabel);
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

                listFromFile = FileCont.readMemberListFromFile("members_data.ser");

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

                        Label membertestlabel = new Label(currentMember.toString());
                        VBoxForScrollPane.getChildren().clear();
                        VBoxForScrollPane.getChildren().addAll(membertestlabel);

                            FileCont.writeToFile("members_data.ser", finalListFromFile);


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

                ArrayList<Member> toAddNewMember = new ArrayList<>();

                    toAddNewMember = FileCont.readMemberListFromFile("members_data.ser");

                assert toAddNewMember != null : "toAddNewMember is null";
                toAddNewMember.add(member);

                FileCont.writeToFile("members_data.ser", toAddNewMember);

            Label memberLabel = new Label();
            memberLabel.setText("\nNew member added successfully" + member);
            VBoxForScrollPane.getChildren().addAll(memberLabel);
            System.out.println(member);
        });
        createMemberButton.setText("Create Member");
        mainContainerID.getChildren().add(createMemberButton);


    }
}
