package com.zmy.exer;


/**
 *@Description
 *@author Sam  Email:superdouble@yeah.net
 *@create 2021-11-20 9:58
 *@version
 */
public class Student {
    private int FlowID;
    private int Type;
    private String IDCard;
    private String ExamCard;
    private String StudentName;
    private String Location;
    private int Grage;

    public Student() {
    }

    public Student(int flowId,
                   int type,
                   String IDCard,
                   String examCard,
                   String studentName,
                   String location,
                   int grage) {
        FlowID = flowId;
        Type = type;
        this.IDCard = IDCard;
        ExamCard = examCard;
        StudentName = studentName;
        Location = location;
        Grage = grage;
    }

    public int getFlowId() {
        return FlowID;
    }

    public void setFlowId(int flowId) {
        FlowID = flowId;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getIDCard() {
        return IDCard;
    }

    public void setIDCard(String IDCard) {
        this.IDCard = IDCard;
    }

    public String getExamCard() {
        return ExamCard;
    }

    public void setExamCard(String examCard) {
        ExamCard = examCard;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public int getGrage() {
        return Grage;
    }

    public void setGrage(int grage) {
        Grage = grage;
    }

    @Override
    public String toString() {
        return "流水号:" + FlowID +"\n"+
                "四级/六级" + Type +"\n"+
                "身份证号:" + IDCard + "\n" +
                "准考证号:" + ExamCard + "\n" +
                "学生姓名:" + StudentName + "\n" +
                "区域:" + Location + "\n" +
                "成绩:" + Grage ;
    }
}
