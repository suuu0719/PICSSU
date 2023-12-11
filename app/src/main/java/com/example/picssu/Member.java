package com.example.picssu;

public class Member {
    private String studentNum;
    private String department;
    private String name;
    private String major;

    // 생성자
    public Member(String studentNum, String department, String name, String major) {
        this.studentNum = studentNum;
        this.department = department;
        this.name = name;
        this.major = major;
    }

    // Getter 메서드들
    public String getStudentNum() {
        return studentNum;
    }

    public String getDepartment() {
        return department;
    }

    public String getName() {
        return name;
    }

    public String getMajor() {
        return major;
    }
}

