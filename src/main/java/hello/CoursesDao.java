package hello;

import courses.Course;

import java.util.List;

/**
 * DAO definition for courses storage.
 */
public interface CoursesDao {

    List<Course> listCourses();
}
