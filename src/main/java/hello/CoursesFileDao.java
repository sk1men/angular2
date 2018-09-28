package hello;

import com.google.gson.Gson;
import courses.Course;
import courses.DbJson;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Returns list of courses from json.db.
 */
@Component
public class CoursesFileDao implements CoursesDao {
    private static final String JSON_FILE_PATH = "../ angular2/db.json";

    public List<Course> listCourses() {
        Gson gson = new Gson();
        return gson.fromJson(readJsonFromFile(JSON_FILE_PATH), DbJson.class).getCourses();
    }

    private String readJsonFromFile(String filePath) {
        try {
            return FileUtils.readFileToString(new File(filePath), "UTF-8");
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
