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
                    {"Student Name:", Utility.getFullName(messageBidInfo.getInitiatorId())},
                    {"Subject:", bid.getSubject().getName()},
                    {"Number of Sessions:", Integer.toString(messageBidInfo.getNumberOfSessions())},
                    {"Day & Time:", messageBidInfo.getDay() + " " + messageBidInfo.getTime()},
                    {"Duration (hours):", Integer.toString(messageBidInfo.getDuration())},
                    {"Rate (per session):", Integer.toString(messageBidInfo.getRate())},
                    {"Student's Message:", messageBidInfo.getContent()}
            };
            String[] col = {"", ""};
            JTable contractTable = new JTable(rec, col);
            contractTable.getColumnModel().getColumn(1).setCellRenderer(new WordWrapCellRenderer());
            return contractTable;
        }

        public static JTable buildStudentTable(MessageBidInfo messageBidInfo, Bid bid) {
            String freeLesson = messageBidInfo.isFreeLesson()? "Yes": "No";

            String[][] rec = {
                    {"Tutor Name:", Utility.getFullName(messageBidInfo.getInitiatorId())},
                    {"Subject:", bid.getSubject().getName()},
                    {"Number of Sessions:", Integer.toString(messageBidInfo.getNumberOfSessions())},
                    {"Day & Time:", messageBidInfo.getDay() + " " + messageBidInfo.getTime()},
                    {"Duration (hours):", Integer.toString(messageBidInfo.getDuration())},
                    {"Rate (per session):", Integer.toString(messageBidInfo.getRate())},
                    {"Free Lesson?", freeLesson},
                    {"Tutor's Message:", messageBidInfo.getContent()}

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
                    {"Number of Sessions:", Integer.toString(bidObject.getAdditionalInfo().getBidPreference().getPreferences().getNumberOfSessions())},
                    {"Day & Time:", bidObject.getAdditionalInfo().getBidPreference().getPreferences().getDay() + " " + bidObject.getAdditionalInfo().getBidPreference().getPreferences().getTime()},
                    {"Duration (hours):", Integer.toString(bidObject.getAdditionalInfo().getBidPreference().getPreferences().getDuration())},
                    {"Rate (per session):", Integer.toString(bidObject.getAdditionalInfo().getBidPreference().getPreferences().getRate())},
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
                    {"Number of Sessions:", Integer.toString(bidObject.getAdditionalInfo().getBidPreference().getPreferences().getNumberOfSessions())},
                    {"Day & Time:", bidObject.getAdditionalInfo().getBidPreference().getPreferences().getDay() + " " + bidObject.getAdditionalInfo().getBidPreference().getPreferences().getTime()},
                    {"Duration (hours):", Integer.toString(bidObject.getAdditionalInfo().getBidPreference().getPreferences().getDuration())},
                    {"Rate (per session):", Integer.toString(bidObject.getAdditionalInfo().getBidPreference().getPreferences().getRate())},
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
        return minLeft + " minutes, " +  secLeft +  " seconds left till expiry";
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
