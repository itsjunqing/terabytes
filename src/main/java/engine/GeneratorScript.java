package engine;

import entity.QualificationTitle;
import service.ApiService;
import stream.Competency;
import stream.Qualification;
import stream.User;

import java.util.Arrays;

public class GeneratorScript {

    /**
     * Example of usage: GeneratorScript.generatePlainStudent("Dummy", "Student 2", "dummystudent2");
     * This will generate a plain student (no qualifications + competency)
     */
    public static void generatePlainStudent(String fName, String lName, String username) {
        boolean userExist = ApiService.userApi().getAll().stream()
                                .anyMatch(u -> u.getUserName().equals(username));
        if (userExist) { // safety check
            return;
        }
        User student = new User(fName, lName, username, username, true, false);
        ApiService.userApi().add(student);
    }

    /**
     * Example of usage: GeneratorScript.generateProTutor("Dummy", "Tutor", "dummytutor", 10);
     * This will generate tutors of all qualifications + all competencies of level 10
     */
    public static void generateProTutor(String fName, String lName, String username, int level) {
        if (level < 0 || level > 10) {
            System.out.println("Please give level between 1 to 10 (inclusive)");
            return;
        }
        boolean userExist = ApiService.userApi().getAll().stream()
                .anyMatch(u -> u.getUserName().equals(username));
        if (userExist) { // safety check
            return;
        }
        User tutor = new User(fName, lName, username, username, false, true);
        User addedTutor = ApiService.userApi().add(tutor);
        String tutorId = addedTutor.getId();

        // add all qualifications
        Arrays.asList(QualificationTitle.values()).stream()
                .map(q -> new Qualification(q.toString(), q.toString() + " in every area", true, tutorId))
                .forEach(q -> ApiService.qualificationApi().add(q));

        // add all competency of max level
        ApiService.subjectApi().getAll().stream()
                .map(s -> new Competency(tutorId, s.getId(), level))
                .forEach(c -> ApiService.competencyApi().add(c));

    }
}
