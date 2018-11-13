package entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "parameter", schema = "public", catalog = "ctrl-ingreso")
public class Parameter {
    private int id;
    private String parametertype;
    private String parametercode;
    private String descriptionparameter;
    private String textvalue;
    private int numbervalue;
    private String state;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parameter_id_seq")
    @SequenceGenerator(name="parameter_id_seq", sequenceName = "parameter_id_seq", allocationSize=1)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "parametertype", nullable = false, length = 1)
    public String getParametertype() {
        return parametertype;
    }

    public void setParametertype(String parametertype) {
        this.parametertype = parametertype;
    }

    @Basic
    @Column(name = "parametercode", nullable = false, length = 10)
    public String getParametercode() {
        return parametercode;
    }

    public void setParametercode(String parametercode) {
        this.parametercode = parametercode;
    }

    @Basic
    @Column(name = "descriptionparameter", nullable = false, length = 60)
    public String getDescriptionparameter() {
        return descriptionparameter;
    }

    public void setDescriptionparameter(String descriptionparameter) {
        this.descriptionparameter = descriptionparameter;
    }

    @Basic
    @Column(name = "textvalue", nullable = false, length = 10)
    public String getTextvalue() {
        return textvalue;
    }

    public void setTextvalue(String textvalue) {
        this.textvalue = textvalue;
    }

    @Basic
    @Column(name = "numbervalue", nullable = false)
    public int getNumbervalue() {
        return numbervalue;
    }

    public void setNumbervalue(int numbervalue) {
        this.numbervalue = numbervalue;
    }

    @Basic
    @Column(name = "state", nullable = false, length = 1)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parameter that = (Parameter) o;
        return id == that.id &&
                numbervalue == that.numbervalue &&
                Objects.equals(parametertype, that.parametertype) &&
                Objects.equals(parametercode, that.parametercode) &&
                Objects.equals(descriptionparameter, that.descriptionparameter) &&
                Objects.equals(textvalue, that.textvalue) &&
                Objects.equals(state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, parametertype, parametercode, descriptionparameter, textvalue, numbervalue, state);
    }
}
