package view;

import entity.BidInfo;
import entity.Constants;
import entity.MessageBidInfo;
import entity.Utility;
import stream.Bid;
import stream.Contract;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ViewUtility {

    public static final int STUDENT_CODE = 0;
    public static final int TUTOR_CODE = 1;



    public static Integer[] competencies = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    public static Integer[] sessions = {1, 2, 3, 4, 5};
    public static String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    public static String[] times = {"8:00AM", "9:00AM", "10:00AM", "11:00AM", "12:00PM", "1:00PM", "2:00PM",
            "3:00PM", "4:00PM", "5:00PM", "6:00PM", "7:00PM"};
    public static Integer[] durations = {1, 2, 3, 4, 5};
    public static String[] freeLessons = {"Yes", "No"};


    public static String errorMessage = "This Bid has expired, please make a new one";
    public static String closeMessage = "This Bid has expired or closed down, please close and refresh main page";


    public static void resizeColumns(JTable table) {
        TableColumnModel columnModel = table.getColumnModel();
        int colCount = table.getColumnCount();
        int rowCount = table.getRowCount();
        for (int c = 0; c < colCount; c++) {
            int width = 20;
            for (int r = 0; r < rowCount; r++) {
                TableCellRenderer defaultRenderer = table.getCellRenderer(r, c);
                int defaultSize = table.prepareRenderer(defaultRenderer, r, c).getPreferredSize().width + 1;
                if (width < defaultSize){
                    width = defaultSize;
                }
            }
            if(width > 300)
                width=300;
            if(width < 200)
                width=200;
            columnModel.getColumn(c).setPreferredWidth(width);
        }
    }

    public static class ContractTable {
        public static JTable buildTutorTable(Contract contractObject, int contractNo) {
            String[][] rec = {
                    {"Contract Number", Integer.toString(contractNo)},
                    {"Contract End Date", contractObject.getExpiryDate().toString()},
                    {"Student Name", Utility.getFullName(contractObject.getFirstParty())},
                    {"Subject", contractObject.getSubject().getName()},
                    {"Number Of Sessions",  contractObject.getLessonInfo().getNumberOfSessions().toString()},
                    {"Day & Time", contractObject.getLessonInfo().getTime() + " " + contractObject.getLessonInfo().getDay()},
                    {"Duration", contractObject.getLessonInfo().getDuration().toString() + " hour(s)"},
                    {"Total Price", "$" + contractObject.getPaymentInfo().getTotalPrice()}};
            String[] col = {"", ""};
            return new JTable(rec, col);
        }

        public static JTable buildStudentTable(Contract contractObject, int contractNo) {
            String[][] rec = {
                    {"Contract Number", Integer.toString(contractNo)},
                    {"Contract End Date", contractObject.getExpiryDate().toString()},
                    {"Tutor Name", Utility.getFullName(contractObject.getSecondParty())},
                    {"Subject", contractObject.getSubject().getName()},
                    {"Number Of Sessions",  contractObject.getLessonInfo().getNumberOfSessions().toString()},
                    {"Day & Time", contractObject.getLessonInfo().getTime() + " " + contractObject.getLessonInfo().getDay()},
                    {"Duration", contractObject.getLessonInfo().getDuration().toString() + " hour(s)"},
                    {"Total Price", "$" + contractObject.getPaymentInfo().getTotalPrice()},
            };
            String[] col = {"", ""};
            return new JTable(rec, col);
        }
    }

    public static class MessageTable {
        public static JTable buildTutorTable(MessageBidInfo messageBidInfo, Bid bid) {
            String[][] rec = {
                    {"Tutor Name:", Utility.getFullName(messageBidInfo.getInitiatorId())},
                    {"Subject:", bid.getSubject().getName()},
                    {"Number of Sessions:", Integer.toString(messageBidInfo.getNumberOfSessions())},
                    {"Day & Time:", messageBidInfo.getDay() + " " + messageBidInfo.getTime()},
                    {"Duration (hours):", Integer.toString(messageBidInfo.getDuration())},
                    {"Rate (per session):", Integer.toString(messageBidInfo.getRate())},
                    {"Tutor's Message:", messageBidInfo.getContent()}
            };
            String[] col = {"", ""};
            JTable contractTable = new JTable(rec, col);
            contractTable.getColumnModel().getColumn(1).setCellRenderer(new WordWrapCellRenderer());
            return contractTable;
        }

        public static JTable buildStudentTable(MessageBidInfo messageBidInfo, Bid bid) {
            String freeLesson = messageBidInfo.isFreeLesson()? "Yes": "No";

            String[][] rec = {
                    {"Student Name:", Utility.getFullName(messageBidInfo.getInitiatorId())},
                    {"Subject:", bid.getSubject().getName()},
                    {"Number of Sessions:", Integer.toString(messageBidInfo.getNumberOfSessions())},
                    {"Day & Time:", messageBidInfo.getDay() + " " + messageBidInfo.getTime()},
                    {"Duration (hours):", Integer.toString(messageBidInfo.getDuration())},
                    {"Rate (per session):", Integer.toString(messageBidInfo.getRate())},
                    {"Free Lesson?", freeLesson},
                    {"Student's Message:", messageBidInfo.getContent()}

            };
            String[] col = {"", ""};
            JTable contractTable = new JTable(rec, col);
            contractTable.getColumnModel().getColumn(1).setCellRenderer(new WordWrapCellRenderer());
            return contractTable;
        }
    }

    public static class BidAndOfferTable {
        public static JTable buildTutorTable(Bid bidObject, int index) {
            String[][] rec = {
                    {"Bid Number:", Integer.toString(index)},
                    {"Bid Type", bidObject.getType()},
                    {"Student Name:", bidObject.getInitiator().getGivenName() + " " + bidObject.getInitiator().getFamilyName()},
                    {"Subject:", bidObject.getSubject().getName()},
                    {"Number of Sessions:", Integer.toString(bidObject.getAdditionalInfo().getPreference().getPreferences().getNumberOfSessions())},
                    {"Day & Time:", bidObject.getAdditionalInfo().getPreference().getPreferences().getDay() + " " + bidObject.getAdditionalInfo().getPreference().getPreferences().getTime()},
                    {"Duration (hours):", Integer.toString(bidObject.getAdditionalInfo().getPreference().getPreferences().getDuration())},
                    {"Rate (per session):", Integer.toString(bidObject.getAdditionalInfo().getPreference().getPreferences().getRate())},
            };
            String[] col = {"", ""};
            return new JTable(rec, col);
        }

        public static JTable buildStudentTable(BidInfo bidInfo, int bidNo, Bid bid) {
            String freeLesson = bidInfo.isFreeLesson()? "Yes": "No";
            String[][] rec = {
                    {"Offer Number: ", Integer.toString(bidNo)},
                    {"Tutor Name:", Utility.getFullName(bidInfo.getInitiatorId())},
                    {"Subject:", bid.getSubject().getName()},
                    {"Number of Sessions:", Integer.toString(bidInfo.getNumberOfSessions())},
                    {"Day & Time:", bidInfo.getDay() + " " + bidInfo.getTime()},
                    {"Duration (hours):", Integer.toString(bidInfo.getDuration())},
                    {"Rate (per session):", Integer.toString(bidInfo.getRate())},
                    {"Free Lesson?", freeLesson},
            };
            String[] col = {"", ""};
            return new JTable(rec, col);
        }
    }

    public static class OpenOffersTable {

        public static JTable buildTutorOpenOffer(BidInfo bidInfo, Bid bid) {
            String freeLesson = bidInfo.isFreeLesson()? "Yes": "No";
            String[][] rec = {
                    {"Tutor Name:", Utility.getFullName(bidInfo.getInitiatorId())},
                    {"Subject:", bid.getSubject().getName()},
                    {"Number of Sessions:", Integer.toString(bidInfo.getNumberOfSessions())},
                    {"Day & Time:", bidInfo.getDay() + " " + bidInfo.getTime()},
                    {"Duration (hours):", Integer.toString(bidInfo.getDuration())},
                    {"Rate (per session):", Integer.toString(bidInfo.getRate())},
                    {"Free Lesson?", freeLesson},
            };
            String[] col = {"", ""};
            return new JTable(rec, col);
        }

        public static JTable buildStudentRequest(Bid bidObject) {
            String[][] rec = {
                    {"Bid Type", "Open"},
                    {"Student Name:", Utility.getFullName(bidObject.getInitiator())},
                    {"Subject:", bidObject.getSubject().getName()},
                    {"Number of Sessions:", Integer.toString(bidObject.getAdditionalInfo().getPreference().getPreferences().getNumberOfSessions())},
                    {"Day & Time:", bidObject.getAdditionalInfo().getPreference().getPreferences().getDay() + " " + bidObject.getAdditionalInfo().getPreference().getPreferences().getTime()},
                    {"Duration (hours):", Integer.toString(bidObject.getAdditionalInfo().getPreference().getPreferences().getDuration())},
                    {"Rate (per session):", Integer.toString(bidObject.getAdditionalInfo().getPreference().getPreferences().getRate())},
            };
            String[] col = {"", ""};
            return new JTable(rec, col);
        }
    }


    public static String getOpenBidTimeLeft(Date then) {
        Date now = new Date();
        long difference = now.getTime() - then.getTime();
        long seconds = (Constants.OPEN_BID_MINS * 60) - TimeUnit.MILLISECONDS.toSeconds(difference);
        long minLeft = seconds / 60;
        long secLeft = seconds % 60;
        if (difference < 0){
            secLeft = 0;
            minLeft = 0;
        }
        return minLeft + " minutes, " +  secLeft +  " seconds left till expiry";
    }

    public static String getCloseBidTimeLeft(Date then) {
        Date now = new Date();
        long difference = now.getTime() - then.getTime();
        long minutes = (Constants.CLOSE_BID_DAYS * 24 * 60) - TimeUnit.MILLISECONDS.toMinutes(difference);
        // Ref: https://stackoverflow.com/questions/2751073/how-to-convert-minutes-to-days-hours-minutes
        long dayLeft = minutes / (24 * 60);
        System.out.println("this are the minutes");
        System.out.println(minutes);
        long hoursLeft = (minutes % (24 * 60)) / 60;
        long minsLeft = (minutes % (24 * 60)) % 60;
        if (difference < 0){
            hoursLeft = 0;
            minsLeft = 0;
            dayLeft = 0;
        }
        return dayLeft + " days, " +  hoursLeft + " hours, " + minsLeft + " mins left till expiry";
    }

    // TODO : got from stack overflow and not sure how to rewrite
    public static class WordWrapCellRenderer extends JTextArea implements TableCellRenderer {
        public WordWrapCellRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
        }
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value.toString());
            setSize(table.getColumnModel().getColumn(column).getWidth(), getPreferredSize().height);
            if (table.getRowHeight(row) != getPreferredSize().height) {
                table.setRowHeight(row, getPreferredSize().height);
            }
            return this;
        }
    }

}
