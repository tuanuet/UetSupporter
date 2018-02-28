package vnu.uet.tuan.uetsupporter.Model.Response;

import java.util.ArrayList;

import vnu.uet.tuan.uetsupporter.Model.Course;
import vnu.uet.tuan.uetsupporter.Model.Student;

/**
 * Created by vu.minh.tuan on 28/02/2018.
 */

public class CourseInformation {
    private Course course = new Course();
    private ArrayList<Student> students = new ArrayList<>();

    public CourseInformation(Course course, ArrayList<Student> students) {
        this.course = course;
        this.students = students;
    }

    public CourseInformation() {
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }
}
