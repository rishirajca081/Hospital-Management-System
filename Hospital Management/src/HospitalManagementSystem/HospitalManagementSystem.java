package HospitalManagementSystem;
import java.sql.*;
import java.util.*;
public class HospitalManagementSystem {
    private static final String url="jdbc:mysql://localhost:3306/hospital";
    private static final String username="root";
    private static final String pass="0209rishi@Pa";

    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection(url,username,pass);
            patient patient=new patient(con,sc);
            Doctor doctor=new Doctor(con,sc);
            while(true){
                System.out.println("HOSPITAL MANAGEMENT SYSTEM ");
                System.out.println("____________________________");
                System.out.println("1.  Add Patient");
                System.out.println("2.  View Patient");
                System.out.println("3.  View Doctor");
                System.out.println("4.  Book Appointment");
                System.out.println("5.  Exit");
                int choice=sc.nextInt();
                switch(choice){
                    case 1:
                        patient.addpatient();
                        System.out.println();
                        break;
                    case 2:
                        patient.viewpatient();
                        System.out.println();
                        break;
                    case 3:
                        doctor.viewDoctor();
                        System.out.println();
                        break;
                    case 4:
                        bookappointment(patient,doctor,con,sc);
                        System.out.println();
                        break;
                        case 5:
                        return;
                    default:
                        System.out.println("*** ALERT *** ENTER VALID NUMBER ***");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void bookappointment(patient patient,Doctor doctor,Connection con,Scanner sc){
        System.out.println("SUBMIT DETAILS");
        System.out.println("***************");
        System.out.println("Enter Patient Id : ");
        int patientid=sc.nextInt();
        System.out.println("Enter Doctor Id : ");
        int doctorid=sc.nextInt();
        System.out.println("Enter Appointment Date (YY-MM-DD) : ");
        String appointmentDate=sc.next();
        if(patient.getpatientById(patientid) && doctor.getdoctorById(doctorid)){
            if(checkavailability(doctorid,appointmentDate,con)){
              String q="insert into appointments(patients_id,doctors_id,appointment_date) values(?,?,?)";
              try{
                  PreparedStatement pstmt=con.prepareStatement(q);
                  pstmt.setInt(1,patientid);
                  pstmt.setInt(2,doctorid);
                  pstmt.setString(3,appointmentDate);
                  int rowsaffected=pstmt.executeUpdate();
                  if(rowsaffected>0){
                      System.out.println("Appointment Booked!!!");
                  }else{
                      System.out.println("Failed to Booked!!!");
                  }
              }catch(Exception e){
                  e.printStackTrace();
                }
            }else{
                System.out.println("*** DOCTOR NOT AVAILABLE ***");
            }
        }else{
            System.out.println("*** Either Doctor Or Patient Not Available ***");
        }
    }
    public static boolean checkavailability(int doctorid,String appointmentDate,Connection con){
        String q="select count(*) from appointments where doctors_id=? and appointment_date=?";
        try{
            PreparedStatement pstmt=con.prepareStatement(q);
            pstmt.setInt(1,doctorid);
            pstmt.setString(2,appointmentDate);
            ResultSet resultset=pstmt.executeQuery();
            if(resultset.next()){
                int count=resultset.getInt(1);
                if(count==0){
                    return true;
                }else{
                    return false;
                }
            }
        }catch(Exception e){
            //System.out.println("");
            e.printStackTrace();
        }
        return false;
    }
}
