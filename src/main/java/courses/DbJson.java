package courses;

import java.util.List;

/**
 * Represents db.json file structure.
 */
public class DbJson {

    private List<Course> courses;
    private List<Object> login;
    private List<Object> profiles;
    private Object status;

    public DbJson(List<Course> course) {
        this.courses = course;
    }

    public List<Course> getCourses() {
        return courses;
    }
}
