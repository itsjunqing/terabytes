package deserialization;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data @AllArgsConstructor
public class Contract {
    private String id;
    @SerializedName("firstParty")
    private User student;
    @SerializedName("secondParty")
    private User tutor;
    private Subject subject;
    private Date dateCreated;
    private Date dateSigned;
    private Payment paymentInfo;
    private Lesson lessonInfo;
    private Extra additionalInfo;

    // TODO: Inner-classes below to be modified
    @Data
    private class Payment {
        private String info;
    }
    @Data
    private class Lesson {
        private String info;
    }
    @Data
    private class Extra {
        private String info;
    }
}
