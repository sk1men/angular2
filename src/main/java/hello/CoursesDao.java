package hello;

import courses.Course;

import java.util.List;

/**
 * DAO definition for courses storage.
 */
public interface CoursesDao {

    List<Course> listCourses();

    Course find(Long id);

    Course save(Course course);

    Course update(Long id, Course modifiedCourse);

    Course delete(Long id);
}
