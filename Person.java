public abstract class Person {
    protected String name;
    protected int age;
    protected String phoneNumber;
    protected String location;

    public Person(String name, int age, String phoneNumber, String location) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.location = location;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public abstract void displayDetails();
}
