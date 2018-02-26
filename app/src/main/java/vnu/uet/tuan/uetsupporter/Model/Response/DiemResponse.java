package vnu.uet.tuan.uetsupporter.Model.Response;

import org.apache.commons.lang3.ArrayUtils;

import vnu.uet.tuan.uetsupporter.Model.Course;
import vnu.uet.tuan.uetsupporter.Model.Student;

/**
 * Created by Vu Minh Tuan on 2/24/2017.
 */

public class DiemResponse {


    private Course course;
    private Data[] data;


    public DiemResponse() {
    }

    public DiemResponse(Course course, Data[] data) {
        this.course = course;
        this.data = data;
    }

    public Student getStudent(int pos) {
        return this.data[pos].getStudent();
    }

    public String getEmailStudent(int pos) {
        return this.data[pos].getStudent().getCode()+"@vnu.edu.vn";
    }

    public double getMarkMiddle(int pos) {
        return this.data[pos].getMarkMiddle();
    }

    public double getMarkFinal(int pos) {
        return this.data[pos].getMarkFinal();
    }


    public Data[] getData() {
        return data;
    }

    public int getLengthData() {
        return this.getData().length;
    }

    public void setData(Data[] data) {
        this.data = data;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void removeDataItem(int pos) {
        this.data = ArrayUtils.removeElement(this.data, this.data[pos]);
    }



    private class Data {
        private Student student;
        private double markMiddle;
        private double markFinal;

        public Data(Student students, double markMiddle, double markFinal) {
            this.student = students;
            this.markMiddle = markMiddle;
            this.markFinal = markFinal;
        }

        public Student getStudent() {
            return student;
        }

        public void setStudent(Student students) {
            this.student = students;
        }

        public double getMarkMiddle() {
            return markMiddle;
        }

        public void setMarkMiddle(double markMiddle) {
            this.markMiddle = markMiddle;
        }

        public double getMarkFinal() {
            return markFinal;
        }

        public void setMarkFinal(double markFinal) {
            this.markFinal = markFinal;
        }
    }
}


