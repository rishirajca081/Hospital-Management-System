package HospitalManagementSystem;
import java.sql.*;
import java.util.*;
public class patient {
    private Connection con;
    private Scanner sc;
    public patient(Connection con,Scanner sc){
        this.con=con;
        this.sc=sc;
    }
    public void addpatient() {
        System.out.println("Enter Patient Name : ");
        String name = sc.next();
        System.out.println("Enter Patient Age : ");
        int age = sc.nextInt();
        System.out.println("Enter Patient Gender : ");
        String gender = sc.next();
        try {
            String q = "insert into patients(name,age,gender) values(?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(q);
            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.setString(3, gender);
            int affectedrow = pstmt.executeUpdate();
            if (affectedrow > 0) {
                System.out.println("Patient Added Successfully");
            } else {
                System.out.println("Failed to Add Patient");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        public void viewpatient(){
            String q="Select *from patients";
            try{
                PreparedStatement pstmt=con.prepareStatement(q);
                ResultSet resultSet=pstmt.executeQuery();
                System.out.println("PATIENT DATA : ");
                System.out.println("+-------------+--------------+------+--------+");
                System.out.println("| Patient Id  |      Name    |  Age | Gender |");
                while(resultSet.next()){
                    int id= resultSet.getInt("id");
                    String name =resultSet.getString("name");
                    int age=resultSet.getInt("age");
                    String gender=resultSet.getString("gender");
                    System.out.printf("|%-13s|%-13s|%-6s|%-8s\n",id,name,age,gender);
                    System.out.println("+-------------+--------------+------+--------+");
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        public boolean getpatientById(int id){
        try {
            String q = "Select *from patients WHERE id=?";
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


