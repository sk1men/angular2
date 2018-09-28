package courses;

import java.util.List;

/**
 * Represents db.json file structure.
 */
public class DbJson {

    private List<Course> courses;

    public DbJson(List<Course> course) {
        this.courses = course;
    }

    public List<Course> getCourses() {
        return courses;
    }
}
