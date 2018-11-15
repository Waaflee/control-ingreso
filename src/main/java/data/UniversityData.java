package data;

public class UniversityData {

    private static String[] schools = {"Facultad de Ciencias", "Facultad de Ciencias Económicas y Administrativas",
            "Facultad de Ciencias Jurídicas y Políticas", "Facultad de Creación y Comunicación",
            "Facultad de Educación", "Facultad de Enfermería", "Facultad de Ingeniería", "Facultad de Medicina",
            "Facultad de Odontología", "Facultad de Psicología", "Departamento de Humanidades",
            "Departamento de Bioética", "División de Educación Virtual y a Distancia", "No aplica"};

    private static String[] semesters = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "No aplica"};

    public static String[] getSchools() {
        return schools;
    }

    public static void setSchools(String[] schools) {
        schools = schools;
    }

    public static String[] getSemesters() {
        return semesters;
    }

    public static void setSemesters(String[] semesters) {
        semesters = semesters;
    }
}
