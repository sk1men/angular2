package hello;

import com.google.common.flogger.FluentLogger;
import com.google.gson.Gson;
import courses.Course;
import courses.DbJson;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Provides access to courses in json.db.
 */
@Component
public class CoursesFileDao implements CoursesDao {
    private static final String JSON_FILE_PATH = "../angular2/db.json";
    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    private static final Gson GSON = new Gson();

    public List<Course> listCourses() {
        return GSON.fromJson(readJsonFromFile(), DbJson.class).getCourses();
    }

    @Override
    public Course find(Long id) {
        DbJson dbJson = GSON.fromJson(readJsonFromFile(), DbJson.class);
        List<Course> coursesList = dbJson.getCourses();
        Optional<Course> foundedCourse = coursesList.stream().filter(course -> course.getId() == id).findFirst();
        if (foundedCourse.isPresent()) {
            logger.atInfo().log("Found Course #%d", id);
            return foundedCourse.get();
        } else {
            logger.atWarning().log("Course #%d was not found", id);
            return null;
        }
    }

    @Override
    public Course save(Course course) {
        DbJson dbJson = GSON.fromJson(readJsonFromFile(), DbJson.class);
        List<Course> courses = dbJson.getCourses();
        Course savedCourse = new Course(courses.size() + 1,
                course.getName(),
                course.getDescription(),
                course.getType(),
                course.getDate(),
                course.getDurationInSeconds(),
                course.isTopRated()
        );
        courses.add(savedCourse);

        writeIntoFile(dbJson);
        logger.atInfo().log("Saved new Course #%d", savedCourse.getId());
        return savedCourse;
    }

    @Override
    public Course delete(Long id) {
        Gson gson = new Gson();
        DbJson dbJson = gson.fromJson(readJsonFromFile(), DbJson.class);
        List<Course> coursesList = dbJson.getCourses();
        Iterator<Course> courseIterator = coursesList.iterator();
        while (courseIterator.hasNext()) {
            Course deletedCourse = courseIterator.next();
            if (deletedCourse.getId() == id) {
                courseIterator.remove();
                writeIntoFile(dbJson);
                logger.atInfo().log("Deleted course #%d", id);
                return deletedCourse;
            }
        }
        logger.atWarning().log("The course #%d was not found in db.json file", id);
        return null;
    }

    @Override
    public Course update(Long id, Course modifiedCourse) {
        DbJson dbJson = GSON.fromJson(readJsonFromFile(), DbJson.class);
        List<Course> coursesList = dbJson.getCourses();

        Course updatedCourse = null;
        for (int i = 0; i < coursesList.size(); i++) {
            Course course = coursesList.get(i);
            if (course.getId() == id) {
                updatedCourse = new Course(id,
                        modifiedCourse.getName(),
                        modifiedCourse.getDescription(),
                        modifiedCourse.getType(),
                        modifiedCourse.getDate(),
                        modifiedCourse.getDurationInSeconds(),
                        modifiedCourse.isTopRated());
                coursesList.set(i, updatedCourse);
                break;
            }
        }
        if (updatedCourse == null) {
            logger.atWarning().log("The course #%d was not found in db.json file", id);
        } else {
            writeIntoFile(dbJson);
            logger.atInfo().log("Saved changes to Course #%d", id);
        }
        return updatedCourse;
    }

    private String readJsonFromFile() {
        try {
            return FileUtils.readFileToString(new File(JSON_FILE_PATH), "UTF-8");
        } catch (IOException e) {
            logger.atWarning().withCause(e).log("Exception reading from %s file", JSON_FILE_PATH);
            throw new IllegalStateException(e);
        }
    }

    private void writeIntoFile(DbJson dbJson) {
        try {
            FileUtils.writeStringToFile(new File(JSON_FILE_PATH), GSON.toJson(dbJson));
        } catch (IOException e) {
            logger.atWarning().withCause(e).log("Exception writing to %s file", JSON_FILE_PATH);
            throw new IllegalStateException(e);
        }
    }
}
