package hello;

import com.google.common.flogger.FluentLogger;
import courses.Course;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller to process REST requests for courses resource.
 */
@RestController
public class CoursesController {
    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    private final CoursesDao coursesDao;

    CoursesController(@Qualifier("coursesFileDao") CoursesDao coursesDao) {
        this.coursesDao = coursesDao;
    }

    /**
     * Lists available courses.
     */
    @GetMapping("/courses")
    List<Course> listCourses() {
        logger.atFine().log("Reading available courses");
        return coursesDao.listCourses();
    }

    /**
     * Returns the course by id.
     */
    @GetMapping("/courses/{id}")
    Course getCourse(@PathVariable long id) {
        logger.atFine().log("Reading course by id %d", id);
        return coursesDao.find(id);
    }

    /**
     * Creates the course.
     */
    @PostMapping("/courses")
    Course postCourse(@RequestBody Course course) {
        logger.atFine().log("Saving the course %s", course.getName());
        return coursesDao.save(course);
    }

    /**
     * Updates the course.
     */
    @PutMapping("/courses/{id}")
    Course updateCourse(@PathVariable long id, @RequestBody Course modifiedCourse) {
        logger.atFine().log("Saving changes to the course %d", id);
        return coursesDao.update(id, modifiedCourse);
    }

    /**
     * Deletes the course.
     */
    @DeleteMapping("/courses/{id}")
    Course deleteCourse(@PathVariable long id) {
        logger.atFine().log("Deleting course by id %d", id);
        return coursesDao.delete(id);
    }
}
