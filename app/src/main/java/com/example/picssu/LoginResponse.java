package com.example.picssu;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("success")
    boolean success;

    @SerializedName("code")
    String code;

    @SerializedName("message")
    String message;

    @SerializedName("data")
    StudentData studentData;

    static class StudentData {
        @SerializedName("dept")
        DeptData deptData;
        @SerializedName("parentDept")
        ParentDeptData parentDeptData;

        //학번
        @SerializedName("memberNo")
        String memberNo;

        //이름
        @SerializedName("name")
        String name;
    }

    static class DeptData {
        @SerializedName("name")
        String name;
    }

    static class ParentDeptData {
        @SerializedName("id")
        int id;
        @SerializedName("code")
        String code;
        @SerializedName("name")
        String name;
    }

    public StudentData getStudentData() {
        if (studentData != null) {
            return studentData;
        } else {
            return new StudentData(); // 또는 null 대신 적절한 기본값을 반환할 수 있는 객체를 생성
        }
    }

        public String getDept() {
            if (studentData != null && studentData.deptData != null) {
                return studentData.deptData.name;
            } else {
                return ""; // 또는 적절한 디폴트 값 또는 에러 처리를 수행할 수 있는 값
            }
        }

    public String getParentDept() {
        if (studentData != null && studentData.parentDeptData != null) {
            return studentData.parentDeptData.name;
        } else {
            return ""; // 또는 적절한 디폴트 값 또는 에러 처리를 수행할 수 있는 값
        }
    }

    public String getName() {
        StudentData data = getStudentData();
        if (data != null) {
            return data.name;
        } else {
            return ""; // 또는 적절한 디폴트 값 또는 에러 처리를 수행할 수 있는 값
        }
    }

    public String getStudentNum() {
        return studentData.memberNo;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


}