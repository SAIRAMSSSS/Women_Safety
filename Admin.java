public class Admin extends Person {
    private String adminId;
    private String password;

    public Admin(String name, int age, String phoneNumber, String location, String adminId, String password) {
        super(name, age, phoneNumber, location);
        this.adminId = adminId;
        this.password = password;
    }

    public String getAdminId() {
        return adminId;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public void displayDetails() {
        System.out.println("Admin Name: " + name + " | ID: " + adminId);
    }
}
