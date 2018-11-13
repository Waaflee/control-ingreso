package entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "qrcode", schema = "public", catalog = "ctrl-ingreso")
public class Qrcode {
    private int id;
    private String qrcode;
    private Timestamp datestart;
    private Timestamp dateend;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "qrcode_id_seq")
    @SequenceGenerator(name="qrcode_id_seq", sequenceName = "qrcode_id_seq", allocationSize=1)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "qrcode", nullable = false, length = 600)
    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    @Basic
    @Column(name = "datestart", nullable = false)
    public Timestamp getDatestart() {
        return datestart;
    }

    public void setDatestart(Timestamp datestart) {
        this.datestart = datestart;
    }

    @Basic
    @Column(name = "dateend", nullable = false)
    public Timestamp getDateend() {
        return dateend;
    }

    public void setDateend(Timestamp dateend) {
        this.dateend = dateend;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Qrcode that = (Qrcode) o;
        return id == that.id &&
                Objects.equals(qrcode, that.qrcode) &&
                Objects.equals(datestart, that.datestart) &&
                Objects.equals(dateend, that.dateend);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, qrcode, datestart, dateend);
    }
}
