import java.io.*;
import java.util.*;

public class FileManager {
    private static final String USERS_FILE = "users.csv";
    private static final String COMPLAINTS_FILE = "complaints.csv";
    private static final String IPC_FILE = "ipc_sections.csv";

    public static void saveUser(User user) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
            bw.write(user.toCSV());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error saving user: " + e.getMessage());
        }
    }

    public static List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        File file = new File(USERS_FILE);
        if (!file.exists())
            return users;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    users.add(new User(parts[0], Integer.parseInt(parts[1]), parts[2], parts[3], parts[4])); 
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading users: " + e.getMessage());
        }
        return users;
    }

    public static void saveComplaint(Complaint complaint) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(COMPLAINTS_FILE, true))) {
            bw.write(complaint.toCSV());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error saving complaint: " + e.getMessage());
        }
    }

    public static List<Complaint> loadComplaints() {
        List<Complaint> complaints = new ArrayList<>();
        File file = new File(COMPLAINTS_FILE);
        if (!file.exists())
            return complaints;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 8) {
                    String ipc = parts.length >= 9 ? parts[8] : "None"; 
                    complaints.add(new Complaint(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7], ipc)); 
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading complaints: " + e.getMessage());
        }
        return complaints;
    }

    public static void updateComplaintsFile(List<Complaint> complaints) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(COMPLAINTS_FILE, false))) {
            for (Complaint c : complaints) {
                bw.write(c.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error updating complaints file: " + e.getMessage());
        }
    }

    public static List<String> loadIPCSectionsList() {
        List<String> sections = new ArrayList<>();
        sections.add("Select Relevant IPC Section (Case/Issue)");
        File file = new File(IPC_FILE);
        if (!file.exists()) return sections;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;
            StringBuilder currentBlock = new StringBuilder();
            boolean insideQuotes = false;

            while ((line = br.readLine()) != null) {
                currentBlock.append(line).append("\n");
                for (char c : line.toCharArray()) {
                    if (c == '"') insideQuotes = !insideQuotes;
                }

                if (!insideQuotes) {
                    String record = currentBlock.toString().trim();
                    currentBlock.setLength(0);
                    if (firstLine) {
                        firstLine = false;
                        continue;
                    }

                    List<String> fields = new ArrayList<>();
                    StringBuilder currentField = new StringBuilder();
                    boolean inQ = false;
                    for (char c : record.toCharArray()) {
                        if (c == '"') {
                            inQ = !inQ;
                        } else if (c == ',' && !inQ) {
                            fields.add(currentField.toString().trim());
                            currentField.setLength(0);
                        } else {
                            currentField.append(c);
                        }
                    }
                    fields.add(currentField.toString().trim());

                    if (fields.size() >= 4) {
                        String section = fields.get(3).trim();
                        String offense = fields.get(1).trim();
                        if (!section.isEmpty() && !offense.isEmpty()) {
                            offense = offense.replace("\n", " ").replace("\r", " ").replace("\"", ""); 
                            String display = section + " - " + (offense.length() > 60 ? offense.substring(0, 60) + "..." : offense); 
                            sections.add(display);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading IPC sections: " + e.getMessage());
        }
        return sections;
    }
}
