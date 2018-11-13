package entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "users", schema = "public", catalog = "ctrl-ingreso")
public class User {
    private int id;
    private String username;
    private String password;
    private String fullname;
    private String identification;
    private String emailaddress;
    private String school;
    private String major;
    private String semester;
    private String phonenumber;
    private Timestamp datelastpassword;
    private String active;
    private String usertype;
    private int failedattempts;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    @SequenceGenerator(name="users_id_seq", sequenceName = "users_id_seq", allocationSize=1)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "username", nullable = true, length = 8)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password", nullable = true, length = 256)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "fullname", nullable = false, length = 60)
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @Basic
    @Column(name = "identification", nullable = false, length = 12)
    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    @Basic
    @Column(name = "emailaddress", nullable = true, length = 75)
    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    @Basic
    @Column(name = "school", nullable = false, length = 100)
    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    @Basic
    @Column(name = "major", nullable = false, length = 100)
    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    @Basic
    @Column(name = "semester", nullable = false, length = 10)
    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    @Basic
    @Column(name = "phonenumber", nullable = false, length = 10)
    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    @Basic
    @Column(name = "datelastpassword", nullable = false)
    public Timestamp getDatelastpassword() {
        return datelastpassword;
    }

    public void setDatelastpassword(Timestamp datelastpassword) {
        this.datelastpassword = datelastpassword;
    }

    @Basic
    @Column(name = "active", nullable = false, length = 1)
    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    @Basic
    @Column(name = "usertype", nullable = false, length = 12)
    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    @Basic
    @Column(name = "failedattempts", nullable = false)
    public int getFailedattempts() {
        return failedattempts;
    }

    public void setFailedattempts(int failedattempts) {
        this.failedattempts = failedattempts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        return id == that.id &&
                failedattempts == that.failedattempts &&
                Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                Objects.equals(fullname, that.fullname) &&
                Objects.equals(identification, that.identification) &&
                Objects.equals(emailaddress, that.emailaddress) &&
                Objects.equals(school, that.school) &&
                Objects.equals(major, that.major) &&
                Objects.equals(semester, that.semester) &&
                Objects.equals(phonenumber, that.phonenumber) &&
                Objects.equals(datelastpassword, that.datelastpassword) &&
                Objects.equals(active, that.active) &&
                Objects.equals(usertype, that.usertype);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, fullname, identification, emailaddress, school, major, semester, phonenumber, datelastpassword, active, usertype, failedattempts);
    }
}
