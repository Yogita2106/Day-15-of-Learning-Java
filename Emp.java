package Employee;
import java.sql.*;
import java.util.Scanner;

public class Emp{
    private String name;
    private String mail;
    private String phn;
    private int salary;

    // Database credentials (update if needed)
    public static final String DB_URL = "jdbc.jdbc:mysql://localhost:3306/E";
    public static final String USER = "root";
    public static final String PASS = "root";

    public Emp(String name, String mail, String phn,int salary)
    {
        this.name = name;
        this.mail = mail;
        this.phn = phn;
        this.salary = salary;
    }

    // Getters and Setters
    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMail() {
        return mail;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPhn(String phn) {
        this.phn = phn;
    }

    public String getPhn() {
        return phn;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getSalary() {
        return salary;
    }

    // Insert Student
    public void ins(Emp emp) {
        String sql = "INSERT INTO e1 (name, mail, phn,salary) VALUES (?, ?, ?, ?)";
        try (Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, emp.getName());
            ps.setString(2, emp.getMail());
            ps.setString(3, emp.getPhn());
            ps.setInt(4, emp.getSalary());

            int i = ps.executeUpdate();
            System.out.println(i > 0 ? "Data inserted successfully." : "Insert failed.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete Student
    public void del(Emp emp) {
        String sql = "DELETE FROM e1 WHERE name=? AND mail=? AND salary=? AND phn=?";
        try (Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, emp.getName());
            ps.setString(2, emp.getMail());
            ps.setInt(3, emp.getSalary());
            ps.setString(4, emp.getPhn());

            int i = ps.executeUpdate();
            System.out.println(i > 0 ? "Data deleted successfully." : "Delete failed (employee not found).");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Fetch Student
    public void fetch(Emp emp) {
        String sql = "SELECT name, mail, salary, phn FROM e1 WHERE mail=? AND salary=? AND name=? AND phn=?";
        try (Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, emp.getMail());
            ps.setInt(2, emp.getSalary());
            ps.setString(3, emp.getName());
            ps.setString(4, emp.getPhn());
            ResultSet rs = ps.executeQuery();
            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.println("Name   : " + rs.getString("name"));
                System.out.println("Mail    : " + rs.getString("mail"));
                System.out.println("Salary    : " + rs.getInt("salary"));
                System.out.println("Phn : " + rs.getString("phn"));
                System.out.println("------------------------");
            }
            if (!found) {
                System.out.println("No matching employee found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update Student Course
    public void upd(Emp emp) {
        String sql = "UPDATE e1 SET phn=? WHERE mail=?";
        try (Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1,emp.getPhn());
            ps.setString(2, emp.getMail());

            int i = ps.executeUpdate();
            System.out.println(i > 0 ? "Data updated successfully." : "Update failed (employee not found).");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Main Method - Demo
    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Select an operation:\n1. Insert\n2. Fetch\n3. Update\n4. Delete");
        int choice = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Mail: ");
        String mail = scanner.nextLine();
        System.out.print("Enter Phn: ");
        String phn = scanner.nextLine();
        System.out.print("Enter Salary: ");
        int salary = scanner.nextInt();
        scanner.nextLine();

        Employee.Emp e = new Employee.Emp( name,mail, phn, salary);

        switch (choice) {
            case 1:
                e.ins(e);
                break;
            case 2:
                e.fetch(e);
                break;
            case 3:
                System.out.print("Enter new Phone number to update: ");
                String newPhn = scanner.nextLine();
                e.setPhn(newPhn);
                e.upd(e);
                break;
            case 4:
                e.del(e);
                break;
            default:
                System.out.println("Invalid choice.");
        }

        scanner.close();
    }
}