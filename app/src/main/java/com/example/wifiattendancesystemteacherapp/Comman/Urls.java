package com.example.wifiattendancesystemteacherapp.Comman;

public class Urls {

    public static final String OnlineAddress = "http://10.56.207.223:80/WIFIAttedanceSystem/TeacherApp/";
    public static final String OnlineImageAddress = "http://10.56.207.223:80/WIFIAttedanceSystem/Images/";

    public static String OnlineDocAddress="http://10.56.207.223:80/WIFIAttedanceSystem/uploaddoc/";

    public static final String urlLoginTeacher = OnlineAddress+"login_teacher.php";
    public static final String urlRegisterTeacher = OnlineAddress+"register_teacher.php";

    public static String urlAddTimeTablePDF=OnlineAddress+ "addTimeTablePDF.php";

    public static String urlTimeTable=OnlineAddress+ "addTimeTable.php";
    public static String urlGetAllStudent=OnlineAddress+ "get_all_student.php";
    public static String urlGetStudentAttendance=OnlineAddress+ "getStudentAttendance.php";

    public static final String urlGetPendingAttendance = OnlineAddress+"getPendingAttendance.php";

    public static final String urlGetAllPresentyCount = OnlineAddress+"getAllPresentyCount.php";
    public static final String urlDeleteAttendance = OnlineAddress+"deleteAttendance.php";
    public static final String urlAddAttendance = OnlineAddress+"addAttendance.php";


}
