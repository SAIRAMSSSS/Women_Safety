import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.FontFactory;

public class SafetyApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GUIApp();
        });
    }
}

class GUIApp extends JFrame {
    private JTextField nameField, dateField, timeField, locationField;
    private JTextArea descArea;
    private JCheckBox checkHarassment, checkStalking, checkCybercrime;
    private JRadioButton radioStandard, radioAnonymous;
    private JComboBox<String> ipcCombo;
    private JButton submitButton;
    private JLabel statusLabel;

    public GUIApp() {
        setTitle("SafeHer - Incident Report");
        setSize(700, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(255, 248, 252));

        JPanel mainContainer = new JPanel();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
        mainContainer.setBackground(new Color(255, 248, 252));
        mainContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        mainContainer.add(createHeaderPanel());
        mainContainer.add(Box.createRigidArea(new Dimension(0, 20)));

        mainContainer.add(createFormPanel());

        statusLabel = new JLabel(" ");
        statusLabel.setForeground(Color.RED);
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainContainer.add(Box.createRigidArea(new Dimension(0, 10)));
        mainContainer.add(statusLabel);

        add(mainContainer);
        setVisible(true);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(240, 220, 240), 1, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        int size = 48;
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(180, 80, 180));
        g2.fillRoundRect(0, 0, size, size, 15, 15);
        g2.setColor(Color.WHITE);
        g2.fillRect(14, 10, 20, 28);
        g2.setColor(new Color(255, 180, 0));
        g2.fillOval(20, 14, 8, 8);
        g2.setColor(Color.LIGHT_GRAY);
        g2.drawLine(18, 26, 30, 26);
        g2.drawLine(18, 30, 30, 30);
        g2.dispose();
        ImageIcon icon = new ImageIcon(image);

        JLabel titleLabel = new JLabel("Report an Incident");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 28));
        titleLabel.setForeground(new Color(150, 40, 90));
        titleLabel.setIcon(icon);
        titleLabel.setIconTextGap(15);

        JLabel subtitleLabel = new JLabel(
                "<html>Submit a confidential incident report.<br>All information is handled with care.</html>");
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subtitleLabel.setForeground(Color.GRAY);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);
        textPanel.add(titleLabel);
        textPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        textPanel.add(subtitleLabel);

        headerPanel.add(textPanel);
        return headerPanel;
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(250, 220, 230), 1, true),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.weightx = 1.0;

        Font labelFont = new Font("SansSerif", Font.BOLD, 13);
        Color labelColor = new Color(70, 70, 70);

        JLabel nameLabel = new JLabel("Full Name *");
        nameLabel.setFont(labelFont);
        nameLabel.setForeground(labelColor);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(nameLabel, gbc);

        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(400, 35));

        nameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isLetter(c) && !Character.isWhitespace(c) && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                }
            }
        });
        gbc.gridy = 1;
        formPanel.add(nameField, gbc);

        JLabel dateLabel = new JLabel("Date of Incident *");
        dateLabel.setFont(labelFont);
        dateLabel.setForeground(labelColor);
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        formPanel.add(dateLabel, gbc);

        JLabel timeLabel = new JLabel("Time of Incident");
        timeLabel.setFont(labelFont);
        timeLabel.setForeground(labelColor);
        gbc.gridx = 1;
        formPanel.add(timeLabel, gbc);

        dateField = new JTextField("dd-mm-yyyy");
        dateField.setPreferredSize(new Dimension(200, 35));
        dateField.setForeground(Color.GRAY);
        
        dateField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (dateField.getText().equals("dd-mm-yyyy")) {
                    dateField.setText("");
                    dateField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (dateField.getText().isEmpty()) {
                    dateField.setForeground(Color.GRAY);
                    dateField.setText("dd-mm-yyyy");
                }
            }
        });
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(dateField, gbc);

        timeField = new JTextField("--:--");
        timeField.setPreferredSize(new Dimension(200, 35));
        timeField.setForeground(Color.GRAY);
        
        timeField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (timeField.getText().equals("--:--")) {
                    timeField.setText("");
                    timeField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (timeField.getText().isEmpty()) {
                    timeField.setForeground(Color.GRAY);
                    timeField.setText("--:--");
                }
            }
        });

        gbc.gridx = 1;
        formPanel.add(timeField, gbc);

        JLabel locLabel = new JLabel("Location *");
        locLabel.setFont(labelFont);
        locLabel.setForeground(labelColor);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        formPanel.add(locLabel, gbc);

        locationField = new JTextField("Where did the incident occur?");
        locationField.setForeground(Color.GRAY);
        locationField.setPreferredSize(new Dimension(400, 35));

        locationField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (locationField.getText().equals("Where did the incident occur?")) {
                    locationField.setText("");
                    locationField.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent e) {
                if (locationField.getText().isEmpty()) {
                    locationField.setForeground(Color.GRAY);
                    locationField.setText("Where did the incident occur?");
                }
            }
        });
        gbc.gridy = 5;
        formPanel.add(locationField, gbc);

        JLabel idLabel = new JLabel("Reporting Disclosure");
        idLabel.setFont(labelFont);
        idLabel.setForeground(labelColor);
        gbc.gridy = 6;
        formPanel.add(idLabel, gbc);

        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        radioPanel.setBackground(Color.WHITE);
        radioStandard = new JRadioButton("Reveal Name", true);
        radioAnonymous = new JRadioButton("Anonymous");
        radioStandard.setBackground(Color.WHITE);
        radioAnonymous.setBackground(Color.WHITE);
        ButtonGroup bg = new ButtonGroup();
        bg.add(radioStandard);
        bg.add(radioAnonymous);
        radioPanel.add(radioStandard);
        radioPanel.add(radioAnonymous);
        gbc.gridy = 7;
        formPanel.add(radioPanel, gbc);

        JLabel typeLabel = new JLabel("Type of Incident(s)");
        typeLabel.setFont(labelFont);
        typeLabel.setForeground(labelColor);
        gbc.gridy = 8;
        formPanel.add(typeLabel, gbc);

        JPanel checkPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        checkPanel.setBackground(Color.WHITE);
        checkStalking = new JCheckBox("Stalking");
        checkHarassment = new JCheckBox("Harassment");
        checkCybercrime = new JCheckBox("Cybercrime");
        checkStalking.setBackground(Color.WHITE);
        checkHarassment.setBackground(Color.WHITE);
        checkCybercrime.setBackground(Color.WHITE);
        checkPanel.add(checkStalking);
        checkPanel.add(checkHarassment);
        checkPanel.add(checkCybercrime);
        gbc.gridy = 9;
        formPanel.add(checkPanel, gbc);

        JLabel ipcLabel = new JLabel("Applicable IPC Section");
        ipcLabel.setFont(labelFont);
        ipcLabel.setForeground(labelColor);
        gbc.gridy = 10;
        formPanel.add(ipcLabel, gbc);

        ipcCombo = new JComboBox<>();
        List<String> ipcList = FileManager.loadIPCSectionsList();
        for (String item : ipcList) {
            ipcCombo.addItem(item);
        }
        ipcCombo.setBackground(Color.WHITE);
        gbc.gridy = 11;
        formPanel.add(ipcCombo, gbc);

        JLabel descLabel = new JLabel("Description *");
        descLabel.setFont(labelFont);
        descLabel.setForeground(labelColor);
        gbc.gridy = 12;
        formPanel.add(descLabel, gbc);

        descArea = new JTextArea(6, 30);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(descArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 200, 220)));
        gbc.gridy = 13;
        gbc.ipady = 30;
        formPanel.add(scrollPane, gbc);
        gbc.ipady = 0;

        submitButton = new JButton("Submit Report");
        submitButton.setBackground(new Color(180, 70, 180));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("SansSerif", Font.BOLD, 15));
        submitButton.setFocusPainted(false);
        submitButton.setPreferredSize(new Dimension(170, 45));
        submitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        submitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                submitButton.setBackground(new Color(150, 40, 150));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                submitButton.setBackground(new Color(180, 70, 180));
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitComplaint();
            }
        });

        gbc.gridy = 14;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(20, 10, 10, 10);
        formPanel.add(submitButton, gbc);

        return formPanel;
    }

    private void submitComplaint() {
        String name = nameField.getText().trim();
        String date = dateField.getText().trim();
        String loc = locationField.getText().trim();
        String desc = descArea.getText().trim();

        if (name.isEmpty() || date.equals("dd-mm-yyyy") || loc.isEmpty() || loc.equals("Where did the incident occur?") || desc.isEmpty()) {
            statusLabel.setForeground(Color.RED);
            statusLabel.setText("Please fill all required (*) fields before submitting!");
            return;
        }

        List<String> types = new ArrayList<>();
        if (checkStalking.isSelected()) types.add("Stalking");
        if (checkHarassment.isSelected()) types.add("Harassment");
        if (checkCybercrime.isSelected()) types.add("Cybercrime");
        String typeCombined = String.join(" / ", types);
        if (typeCombined.isEmpty()) typeCombined = "Other Incident";

        String ipcSelection = (String) ipcCombo.getSelectedItem();
        if (ipcSelection == null || ipcSelection.startsWith("Select")) {
            ipcSelection = "Not Specified";
        }

        String userPhone = radioAnonymous.isSelected() ? "Anonymous Reporter" : "Registered User";
        String finalName = radioAnonymous.isSelected() ? "Anonymous" : name;

        String complaintId = "CMP" + System.currentTimeMillis();
        
        Complaint c = new Complaint(
                complaintId,
                "Incident Report: " + finalName,
                desc,
                date + " " + timeField.getText(),
                loc,
                typeCombined,
                "Pending",
                userPhone,
                ipcSelection);
        FileManager.saveComplaint(c);

        generatePDFReport(c);

        statusLabel.setForeground(new Color(40, 150, 40));
        statusLabel.setText("Report successfully submitted & PDF Downloaded!");

        nameField.setText("");
        descArea.setText("");
        locationField.setText("Where did the incident occur?");
        locationField.setForeground(Color.GRAY);
        dateField.setText("dd-mm-yyyy");
        dateField.setForeground(Color.GRAY);
        timeField.setText("--:--");
        timeField.setForeground(Color.GRAY);
        checkStalking.setSelected(false);
        checkHarassment.setSelected(false);
        checkCybercrime.setSelected(false);
        ipcCombo.setSelectedIndex(0);
    }

    private void generatePDFReport(Complaint c) {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Download Incident Report PDF");
            fileChooser.setSelectedFile(new java.io.File(c.getComplaintId() + "_Report.pdf"));
            
            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                java.io.File fileToSave = fileChooser.getSelectedFile();
                
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(fileToSave));
                document.open();
                
                document.add(new Paragraph("SafeHer - Official Incident Report", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
                document.add(new Paragraph("\n"));
                
                document.add(new Paragraph("Tracking ID: " + c.getComplaintId()));
                document.add(new Paragraph("Date & Time: " + c.getDateTime()));
                document.add(new Paragraph("Location: " + c.getLocation()));
                document.add(new Paragraph("Incident Type: " + c.getType()));
                document.add(new Paragraph("Relevant IPC Section: " + c.getIpcSection()));
                document.add(new Paragraph("Reported By: " + c.getUserPhone()));

                document.add(new Paragraph("\nDescription:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
                document.add(new Paragraph(c.getDescription()));

                document.close();
                JOptionPane.showMessageDialog(null, "PDF Report successfully downloaded to:\n" + fileToSave.getAbsolutePath(), "Download Complete", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error generating PDF: " + e.getMessage(), "Download Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}
