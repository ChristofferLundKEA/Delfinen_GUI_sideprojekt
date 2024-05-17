package com.example.webeswimming;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
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

    public static void removeMember(Member member) {}

    public static void lookUpMember(VBox mainContainerID) {
        mainContainerID.getChildren().clear();
        Label lookup = new Label("Name: ");
        TextField name = new TextField();
        name.setMaxWidth(250);

        try{
        Button search = new Button("Search");
        ArrayList<Member> webeloopin = FileCont.readMemberListFromFile("members_data.ser");
        search.setOnAction(event -> {
            for (Member member : webeloopin) {
                if (name.getText().equalsIgnoreCase(member.firstName) || name.getText().equalsIgnoreCase(member.surName)){
                    Label memberLabel = new Label(member.toString());
                    mainContainerID.getChildren().clear();
                    mainContainerID.getChildren().add(memberLabel);
                }
            }
        });

        mainContainerID.getChildren().addAll(lookup, name, search);

        } catch (IOException | ClassNotFoundException | NumberFormatException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
//        InputManager.takeScannerInt();
//
//        if (lookup == 1){
//            String name = InputManager.takeScannerString("What name are you looking for?");
//            for (Member member : memberList){
//                if (member.firstName.equals(name) || member.surName.equals(name)){
//                    System.out.println(member);
//                }
//            }
//        }
//        if (lookup == 2){
//            int ID = InputManager.takeScannerInt("Which ID do you want to look up?");
//            for (Member member : memberList){
//                if (member.memberID == ID){
//                    System.out.println(member);
//                }
//            }
//        }
    }

}
