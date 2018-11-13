package entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "entryoutput", schema = "public", catalog = "ctrl-ingreso")
public class Entryoutput {
    private int id;
    private Timestamp dateentry;
    private Timestamp dateoutput;
    private String identification;
    private String dependency;
    private String personvisit;
    private int idqrcode;
    private String doorentry;
    private String dooroutput;
    private String comments;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entryoutput_id_seq")
    @SequenceGenerator(name="entryoutput_id_seq", sequenceName = "entryoutput_id_seq", allocationSize=1)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "dateentry", nullable = false)
    public Timestamp getDateentry() {
        return dateentry;
    }

    public void setDateentry(Timestamp dateentry) {
        this.dateentry = dateentry;
    }

    @Basic
    @Column(name = "dateoutput", nullable = true)
    public Timestamp getDateoutput() {
        return dateoutput;
    }

    public void setDateoutput(Timestamp dateoutput) {
        this.dateoutput = dateoutput;
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
    @Column(name = "dependency", nullable = false, length = 40)
    public String getDependency() {
        return dependency;
    }

    public void setDependency(String dependency) {
        this.dependency = dependency;
    }

    @Basic
    @Column(name = "personvisit", nullable = false, length = 40)
    public String getPersonvisit() {
        return personvisit;
    }

    public void setPersonvisit(String personvisit) {
        this.personvisit = personvisit;
    }

    @Basic
    @Column(name = "idqrcode", nullable = false)
    public int getIdqrcode() {
        return idqrcode;
    }

    public void setIdqrcode(int idqrcode) {
        this.idqrcode = idqrcode;
    }

    @Basic
    @Column(name = "doorentry", nullable = false, length = 30)
    public String getDoorentry() {
        return doorentry;
    }

    public void setDoorentry(String doorentry) {
        this.doorentry = doorentry;
    }

    @Basic
    @Column(name = "dooroutput", nullable = true, length = 30)
    public String getDooroutput() {
        return dooroutput;
    }

    public void setDooroutput(String dooroutput) {
        this.dooroutput = dooroutput;
    }

    @Basic
    @Column(name = "comments", nullable = true, length = 80)
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entryoutput that = (Entryoutput) o;
        return id == that.id &&
                idqrcode == that.idqrcode &&
                Objects.equals(dateentry, that.dateentry) &&
                Objects.equals(dateoutput, that.dateoutput) &&
                Objects.equals(identification, that.identification) &&
                Objects.equals(dependency, that.dependency) &&
                Objects.equals(personvisit, that.personvisit) &&
                Objects.equals(doorentry, that.doorentry) &&
                Objects.equals(dooroutput, that.dooroutput) &&
                Objects.equals(comments, that.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateentry, dateoutput, identification, dependency, personvisit, idqrcode, doorentry, dooroutput, comments);
    }
}
