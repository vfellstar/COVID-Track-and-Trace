import java.time.LocalDate;
import java.time.Period;

import static java.lang.String.format;

public class User {
    private String name;
    private String dob;
    private String email;
    private String phoneNumber;

    private int birthYear;
    private int birthMonth;
    private int birthDay;

    int Age = 0;

    // Constructors
    public User(){
    }
    public User(String name, String dob, String email, String phoneNumber){
        setName(name);
        setDob(dob);
        setEmail(email);
        setPhoneNumber(phoneNumber);
        String[] dateFormat = dob.split("/");
        setName(name);
        setBirthYear(Integer.parseInt(dateFormat[2]));
        setBirthMonth(Integer.parseInt(dateFormat[1]));
        setBirthDay(Integer.parseInt(dateFormat[0]));
        calculateAge();
    }
    public User(String name, int birthYear, int birthMonth, int birthDay, String email, String phoneNumber) {
        setName(name);
        setBirthYear(birthYear);
        setBirthMonth(birthMonth);
        setBirthDay(birthDay);
        setEmail(email);
        setPhoneNumber(phoneNumber);
        this.dob = birthDay + "/" + birthMonth + "/" + birthYear ;
        calculateAge();
    }

    // setters
    public void setName(String name) {
        this.name = name;
    }
    public void setDob(String dob) {
        this.dob = dob;
        String[] s = dob.split("/");
        this.setBirthDay(Integer.parseInt(s[0]));
        this.setBirthMonth(Integer.parseInt(s[1]));
        this.setBirthYear(Integer.parseInt(s[2]));
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }
    public void setBirthMonth(int birthMonth) {
        this.birthMonth = birthMonth;
    }
    public void setBirthDay(int birthDay) {
        this.birthDay = birthDay;
    }

    // getters
    public int getBirthYear() {
        return birthYear;
    }
    public int getBirthMonth() {
        return birthMonth;
    }
    public int getBirthDay() {
        return birthDay;
    }
    public String getName() {
        return name;
    }
    public String getDOB() {
        return dob;
    }
    public String getEmail() {
        return email;
    }
    public String getPhone() {
        return phoneNumber;
    }

    // Return Methods

    public String nameAndNumber() {
        return name + "  |  " + phoneNumber;
    }

    /*Age Calculator
    Example of how to calculate age found here: https://www.youtube.com/watch?v=dOvYkzKfsdg&t=282s
    Original Author: Alex Lee
    Editing Author: Vincent Taylor
    This is a method that calculates, sets and returns the age of the user
    using the day, month and year of birth of the User object.*/
    private int calculateAge() {
        LocalDate DateBirth = LocalDate.of(birthYear, birthMonth, birthDay);
        this.Age = Period.between(DateBirth, LocalDate.now()).getYears();
        return Age;
    }

    String returnUser() {
        return format("    Name %s\n     Date of Birth %s\n     " +
                        "Email %s\n     Contact Number %s\n     Age %s\n",
                this.getName(),
                this.getDOB(),
                this.getEmail(),
                this.getPhone(),
                this.calculateAge());

    }

}


