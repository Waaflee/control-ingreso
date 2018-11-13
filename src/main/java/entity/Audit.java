package entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "audit", schema = "public", catalog = "ctrl-ingreso")
public class Audit {
    private int id;
    private int userid;
    private String operationcrud;
    private String tablename;
    private int tableid;
    private Timestamp createdate;
    private String addressip;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "audit_id_seq")
    @SequenceGenerator(name="audit_id_seq", sequenceName = "audit_id_seq", allocationSize=1)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "userid", nullable = false)
    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    @Basic
    @Column(name = "operationcrud", nullable = false, length = 1)
    public String getOperationcrud() {
        return operationcrud;
    }

    public void setOperationcrud(String operationcrud) {
        this.operationcrud = operationcrud;
    }

    @Basic
    @Column(name = "tablename", nullable = false, length = 30)
    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    @Basic
    @Column(name = "tableid", nullable = false)
    public int getTableid() {
        return tableid;
    }

    public void setTableid(int tableid) {
        this.tableid = tableid;
    }

    @Basic
    @Column(name = "createdate", nullable = false)
    public Timestamp getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Timestamp createdate) {
        this.createdate = createdate;
    }

    @Basic
    @Column(name = "addressip", nullable = false, length = 10)
    public String getAddressip() {
        return addressip;
    }

    public void setAddressip(String addressip) {
        this.addressip = addressip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Audit that = (Audit) o;
        return id == that.id &&
                userid == that.userid &&
                tableid == that.tableid &&
                Objects.equals(operationcrud, that.operationcrud) &&
                Objects.equals(tablename, that.tablename) &&
                Objects.equals(createdate, that.createdate) &&
                Objects.equals(addressip, that.addressip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userid, operationcrud, tablename, tableid, createdate, addressip);
    }
}
