# SafeHer (SafetyApp) - Project Documentation
This document provides a comprehensive overview of the SafeHer (SafetyApp) Java mini-project, designed to help you understand the architecture, codebase, and features thoroughly—perfect for reading and explaining during a viva.

## 1. Project Overview
**SafeHer** is a desktop-based incident reporting application built using Java Swing. It provides a secure, locally-hosted interface for individuals to report incidents (like Stalking, Harassment, and Cybercrime). 

**Core Functionalities:**
*   **Incident Logging:** A user-friendly form to capture complex incident details.
*   **Data Persistence:** Uses local `.csv` files to store User configurations, Complaint records, and legal datasets.
*   **Legal Mapping:** Reads from an `ipc_sections.csv` database to allow users to link specific Indian Penal Code (IPC) sections to their reports.
*   **Anonymity:** Provides a feature to report incidents anonymously.
*   **PDF Generation:** Generates an official PDF report using the `iText` library once a complaint is filed, which can be shared or saved.

## 2. Technology Stack & Requirements
*   **Language:** Java.
*   **GUI Framework:** Java Swing (`javax.swing.*`) and AWT (`java.awt.*`).
*   **Data Storage:** Standard File I/O for reading/writing CSV files (`java.io.*`).
*   **PDF Export:** iText PDF Library (`itextpdf.jar`).

## 3. Class Architecture Breakdown

### 3.1 Object-Oriented Fundamentals
The project utilizes Object-Oriented principles like **Inheritance** and **Encapsulation** through a foundational user hierarchy.

*   **`Person.java` (Abstract Class):**
    *   **Purpose:** The base class for all human entities in the system.
    *   **Properties:** `name`, `age`, `phoneNumber`, `location`.
    *   **Features:** Contains getters/setters and an abstract method `displayDetails()`. Being abstract, it cannot be instantiated directly.
*   **`User.java` (Inherits `Person`):**
    *   **Purpose:** Represents a standard user who can log complaints. 
    *   **Additions:** Adds a `password` field and a `toCSV()` method to format properties into a comma-separated string for local file persistence.
*   **`Admin.java` (Inherits `Person`):**
    *   **Purpose:** Intended to represent system administrators.
    *   **Additions:** Adds `adminId` and `password`. Overrides `displayDetails()` to specifically highlight Admin properties.

### 3.2 The Core Data Model: `Complaint.java`
*   **Purpose:** Acts as the data transfer object (DTO) that holds the comprehensive state of a single incident report.
*   **Properties:** `complaintId` (unique string generated via timestamp), `title`, `description`, `dateTime`, `location`, `type` (e.g., Cybercrime), `status` (defaults to "Pending"), `userPhone` (links to user identity or Anonymous tag), and `ipcSection` (relevant law mapping).
*   **Key Methods:**
    *   `toCSV()`: Cleans up internal structures (removing commas from strings) and returns the data in a CSV-compliant format. 
    *   `toString()`: A nicely formatted string representation useful for console debugging.

### 3.3 Data Access Layer: `FileManager.java`
*   **Purpose:** This static utility class completely abstracts away the File I/O complexity from the main application logic.
*   **Key Operations:**
    *   **`saveUser(User)` / `loadUsers()`:** Appends to and reads from `users.csv`.
    *   **`saveComplaint(Complaint)` / `loadComplaints()`:** Appends a new complaint record to `complaints.csv` and reads historical data.
    *   **`loadIPCSectionsList()`:** Complex CSV parser designed specifically to safely read `ipc_sections.csv`. It actively manages multiline sequences and quotation-bound strings to extract a clean list of IPC legal sections.

### 3.4 The GUI & Application Driver: `SafetyApp.java`
This file serves as the heart of the application, encompassing the `SafetyApp` wrapper and the `GUIApp` logic payload.

*   **`main(String[] args)`:** Starts the application safely on the Swing Event Dispatch Thread (`SwingUtilities.invokeLater`).
*   **`GUIApp` Class (Extends `JFrame`):**
    *   **Layouts Used:** Uses a combination of `BoxLayout` (for top-level vertical stacking) and `GridBagLayout` (for exact precision in the form matrix).
    *   **Custom Graphics:** Replaces external image files with a procedurally generated custom vector badge icon drawn directly via `BufferedImage` and `Graphics2D`.
    *   **Components:**
        *   Text Inputs (`JTextField`, `JTextArea`) for Name, Date, Time, Location, and Description.
        *   Toggles (`JRadioButton`, `ButtonGroup`) for Standard vs Anonymous reporting.
        *   Checkboxes (`JCheckBox`) for incident tagging (Harassment, Stalking, etc.).
        *   Dynamic Tooling (`JComboBox`) populated via the `FileManager`.
    *   **`submitComplaint()` Engine:**
        1. Evaluates all inputs to stop incomplete submissions.
        2. Aggregates data into a new `Complaint` instance.
        3. Invokes `FileManager.saveComplaint(c)` to write the record to `complaints.csv`.
        4. Invokes `generatePDFReport(c)`.
        5. Wipes the interface fields clean.
    *   **`generatePDFReport(Complaint)`:** Utilizes iText classes (`Document`, `PdfWriter`, `Paragraph`, `FontFactory`) alongside Swing's `JFileChooser` to allow the user to save a perfectly formatted PDF rendering of their official report locally on their drive.

## 4. Key Talking Points for Your Viva
If asked about specific computer science or software engineering principles inside this codebase, rely on these points:

1.  **"How do you handle data?"**
    *   *We avoided complex relational databases to minimize dependencies. Instead, it relies on static File I/O manipulation via `FileManager.java`. The system cleanly serializes Java objects into structured CSV bounds `(.toCSV())` and deserializes them back into memory using `BufferedReader` and line splitting logic.*
2.  **"How does the GUI stay responsive?"**
    *   *The GUI is invoked onto the Event Dispatch Thread (EDT) utilizing `SwingUtilities.invokeLater`. This ensures that Swing's non-thread-safe components are constructed efficiently without causing UI freezing/locking.*
3.  **"How does the IPC dataset binding work?"**
    *   *The `FileManager` parses a complex open-source CSV dataset. It contains logic to filter out newlines formatting bugs within quoted strings. The extracted sections are parsed and passed into a Java `List<String>`, which dynamically populates the `JComboBox` UI component.*
4.  **"What design patterns did you employ?"**
    *   *We mapped out an Inheritance schema via the `Person` abstract class. We also used utility pattern design with `FileManager` holding static methods, ensuring memory safety as objects don't need to be instantiated repeatedly to save a file.*

## 5. Potential Improvements (To mention if asked)
*   **Security:** Passwords inside `User.java` are saved in plain text. Implementing password hashing (e.g., using BCrypt) before saving to CSV would drastically improve security.
*   **UI/UX:** Shifting to JavaFX instead of Java Swing for more modern CSS-based view styling capabilities.
*   **Extensibility:** Introducing a small SQLite database instead of plain `.csv` text files would make it substantially easier to perform data updates and searches.
