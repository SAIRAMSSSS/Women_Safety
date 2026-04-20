public class Complaint {
    private String complaintId;
    private String title;
    private String description;
    private String dateTime;
    private String location;
    private String type;
    private String status;
    private String userPhone;
    private String ipcSection;

    public Complaint(String complaintId, String title, String description,
            String dateTime, String location, String type, String status, String userPhone, String ipcSection) {
        this.complaintId = complaintId;
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
        this.location = location;
        this.type = type;
        this.status = status;
        this.userPhone = userPhone;
        this.ipcSection = (ipcSection != null) ? ipcSection : "None";
    }

    public String getComplaintId() {
        return complaintId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getIpcSection() {
        return ipcSection;
    }

    public String toCSV() {
        return complaintId + "," + title.replace(",", " ") + "," + 
                description.replace(",", " ") + "," + dateTime.replace(",", " ") + "," + 
                location.replace(",", " ") + "," + type.replace(",", " ") + "," + 
                status + "," + userPhone + "," + ipcSection.replace(",", " "); 
    }

    @Override
    public String toString() {
        return "Complaint ID: " + complaintId + " | Title: " + title + " | Status: " + status + "\n" + 
                "Type: " + type + " | IPC Section: " + ipcSection + " | Date/Time: " + dateTime + " | Location: " + location + "\n" + 
                "Description: " + description + "\n" + 
                "Reported by (Phone): " + userPhone + "\n" + 
                "---------------------------------------------------"; 
    }
}
