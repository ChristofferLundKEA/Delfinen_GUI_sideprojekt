package com.example.webeswimming;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Member implements Serializable {
    String firstName;
    String surName;
    String gender;
    boolean hasPaid = false;
    boolean isActiveMember = true;
    int team;
    int age;
    int memberID = MemberList.memberList.size() + 1;
    int phoneNumber;
    ArrayList<Result> personalResults;
    private static final long serialVersionUID = 1L;


    public Member() {
        this.personalResults = new ArrayList<>();// Initialize the personalResults list
        this.memberID = createUniqueMemberID();
    }

    public void addResult(Result result) {
        if (personalResults == null) {
            personalResults = new ArrayList<>(); // Double check initialization
        }
        personalResults.add(result);
    }

    public int createUniqueMemberID() {

            boolean isUnique;
            ArrayList<Member> memberList = FileController.readMemberListFromFile("members_data.ser");
            Random random = new Random();
            int randomInt;
            do {
                randomInt = random.nextInt(1001);
                isUnique = true;
                for (Member member : memberList) {
                    if (member.memberID == randomInt) {
                        isUnique = false;
                        break;  // Exit the for loop to generate a new ID
                    }
                }
            } while (!isUnique);
            return randomInt;

    }

    public String showPersonalResults(){
        StringBuilder newString = new StringBuilder();
        try{
            for (Result personalResult : personalResults) {
                newString.append(personalResult);
                newString.append("\n");
            }
        }catch(NullPointerException e){
            e.getMessage();
        }
        return newString + "";
    }

    @Override
    public String toString() {
        return
                "\nFirstName: " + firstName +
                "\nSurname: " + surName +
                "\nGender: " + gender +
                "\nThis person has paid: " + hasPaid +
                "\nThis person is active: " + isActiveMember +
                "\nTeam: " + team +
                "\nAge: " + age +
                "\nMemberID: " + memberID +
                "\nPhone: " + phoneNumber +
                "\nResults: " + showPersonalResults() + "\n";
    }
}
