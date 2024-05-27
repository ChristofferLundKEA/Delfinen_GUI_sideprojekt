package com.example.webeswimming;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import java.io.Serializable;
import java.util.ArrayList;

public class MemberList implements Serializable {

    private static final long serialVersionUID = 1L;

    static ArrayList<Member> memberList = new ArrayList<>();

    public MemberList() {
        memberList = new ArrayList<Member>();
    }

    public static void addMember(Member member) {
        memberList.add(member);
    }

    public static void lookUpMember(VBox mainContainerID) {
        mainContainerID.getChildren().clear();
        Label lookup = new Label("Name: ");
        TextField name = new TextField();
        name.setMaxWidth(250);

        Button search = new Button("Search");
        ArrayList<Member> webeloopin = FileController.readMemberListFromFile("members_data.ser");
        search.setOnAction(event -> {
            for (Member member : webeloopin) {
                if (name.getText().equalsIgnoreCase(member.firstName) || name.getText().equalsIgnoreCase(member.surName)){
                    Label memberLabel = new Label(member.toString());
                    mainContainerID.getChildren().clear();
                    mainContainerID.getChildren().add(memberLabel);
                }
            }
        });

        Label lookupup = new Label("ID: ");
        TextField ID = new TextField();
        ID.setMaxWidth(250);
        Button searchID = new Button("Search");
        ArrayList<Member> webeloopinn = FileController.readMemberListFromFile("members_data.ser");
        searchID.setOnAction(event -> {
            for (Member member : webeloopinn) {
                if (Integer.parseInt(ID.getText()) == member.memberID){
                    Label memberLabel = new Label(member.toString());
                    mainContainerID.getChildren().clear();
                    mainContainerID.getChildren().add(memberLabel);
                }
            }
        });

        mainContainerID.getChildren().addAll(lookup, name, search, lookupup,  ID, searchID);
    }
}
