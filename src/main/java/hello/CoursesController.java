package hello;

import courses.Course;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller, process GET request and returns list of courses.
 */
@RestController
public class CoursesController {

    private final List<Course> list;

    CoursesController(@Qualifier("coursesFileDao") CoursesDao coursesDao) {
        list = coursesDao.listCourses();
    }

    @RequestMapping("/courses")
    public List<Course> courses() {
        return list;
    }
}
