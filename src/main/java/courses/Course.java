package courses;

import java.util.Objects;

/** A Course parameters. */
public class Course {

    private final long id;
    private final String name;
    private final String description;
    private final String type;
    private final String date;
    private final int durationInSeconds;
    private final boolean topRated;

    public Course(long id, String name, String description, String type, String date, int durationInSeconds, boolean topRated) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.date = date;
        this.durationInSeconds = durationInSeconds;
        this.topRated = topRated;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public int getDurationInSeconds() {
        return durationInSeconds;
    }

    public boolean isTopRated() {
        return topRated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id &&
                durationInSeconds == course.durationInSeconds &&
                topRated == course.topRated &&
                Objects.equals(name, course.name) &&
                Objects.equals(description, course.description) &&
                Objects.equals(type, course.type) &&
                Objects.equals(date, course.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, type, date, durationInSeconds, topRated);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", date='" + date + '\'' +
                ", durationInSeconds=" + durationInSeconds +
                ", topRated=" + topRated +
                '}';
    }
}
