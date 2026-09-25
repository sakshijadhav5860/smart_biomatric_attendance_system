package com.example.wifiattendancesystemteacherapp.AllStudent;

public class PojoClassGetAllStudent {
   String id,name,mobile_no,email_id,address,branch,sem,subject,enrollment_no;

    public PojoClassGetAllStudent(String id, String name, String mobile_no, String email_id, String address, String branch, String sem, String subject, String enrollment_no) {
        this.id = id;
        this.name = name;
        this.mobile_no = mobile_no;
        this.email_id = email_id;
        this.address = address;
        this.branch = branch;
        this.sem = sem;
        this.subject = subject;
        this.enrollment_no = enrollment_no;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getSem() {
        return sem;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEnrollment_no() {
        return enrollment_no;
    }

    public void setEnrollment_no(String enrollment_no) {
        this.enrollment_no = enrollment_no;
    }
}
