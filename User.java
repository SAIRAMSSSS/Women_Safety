public class User extends Person {
    private String password;

    public User(String name, int age, String phoneNumber, String location, String password) {
        super(name, age, phoneNumber, location);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void displayDetails() {
        System.out.println("User Name: " + name + " | Phone: " + phoneNumber + " | Location: " + location);
    }

    public String toCSV() {
        return name + "," + age + "," + phoneNumber + "," + location + "," + password;
    }
}
