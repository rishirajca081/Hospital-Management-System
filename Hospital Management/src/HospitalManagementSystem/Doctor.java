package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Doctor {
    private Connection con;
    private Scanner sc;
    public Doctor(Connection con,Scanner sc){
        this.con=con;
        this.sc=sc;
    }
    public void viewDoctor(){
        String q="Select *from doctors";
        try{
            PreparedStatement pstmt=con.prepareStatement(q);
            ResultSet resultSet=pstmt.executeQuery();
            System.out.println("DOCTOR DATA : ");
            System.out.println("+-------------+--------------+----------------+");
            System.out.println("| Doctor Id   |      Name    | Specialization |");
            while(resultSet.next()){
                int id= resultSet.getInt("id");
                String name =resultSet.getString("name");
                String specialization=resultSet.getString("specialization");
                System.out.printf("|%-13s|%-13s|%-14s|\n",id,name,specialization);
                System.out.println("+-------------+--------------+---------------+");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public boolean getdoctorById(int id){
        try {
            String q = "Select *from doctors WHERE id=?";
            PreparedStatement pstmt = con.prepareStatement(q);
            pstmt.setInt(1, id);
            ResultSet resultSet = pstmt.executeQuery();
            if(resultSet.next()){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
