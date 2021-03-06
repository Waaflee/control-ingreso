package beans;


import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import dao.ParameterDaoImpl;
import data.UniversityData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.context.RequestContext;
import util.Ip;
import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.primefaces.PrimeFaces;
import entity.Audit;
import entity.User;
import service.AuditService;
import service.QRService;
import service.UserService;
import util.Cifrado;
import util.DeactivateVisitors;
import util.Mail;

@ManagedBean
@SessionScoped
public class UserMB {
    private String qr;
    private Logger log = LogManager.getLogger();
    private User user;
    private User tempUser;
    private Audit audit = new Audit();
    private User loguser;
    private DataModel<Audit> listAudit;
    private DataModel<User> listUsers;
    private UserService userService;
    private AuditService auditService;
    private String[] majors;
    private String[] schools;
    private String[] semesters;
    private String table;
    private Date date1;
    private Date date2;

    public UserMB() {
        loguser = new User();
        DeactivateVisitors d = new DeactivateVisitors();
        d.deactivate();
        this.schools = UniversityData.getSchools();
        this.semesters = UniversityData.getSemesters();
    }

    public String updateUser() {
        userService = new UserService();
        User u = userService.getUserByUsername(user.getUsername());

        if (!u.getPassword().equals(Cifrado.getStringMessageDigest(user.getPassword(), Cifrado.MD5))) {
            java.sql.Timestamp d = new java.sql.Timestamp(System.currentTimeMillis());
            user.setDatelastpassword(d);
            user.setPassword(Cifrado.getStringMessageDigest(user.getPassword(), Cifrado.MD5));
        }
        if (user.getActive().equals("A")) {
            user.setFailedattempts(0);
        }

        this.qr = QRService.encode(user);

        userService.update(user);

        auditService = new AuditService();

        java.sql.Timestamp d = new java.sql.Timestamp(System.currentTimeMillis());
        audit.setCreatedate(d);
        audit.setAddressip(getLocalAddress());
        audit.setId(0);
        audit.setUserid(loguser.getId());
        audit.setTablename("user");
        audit.setTableid(user.getId());
        audit.setOperationcrud("U");
        auditService.insert(audit);

        return "principalAdmin.xhtml";
    }

    public String manageUser() {
        userService = new UserService();
        this.user.setFullname(this.tempUser.getFullname());
        this.user.setEmailaddress(this.tempUser.getEmailaddress());
        this.user.setMajor(this.tempUser.getMajor());
        this.user.setSchool(this.tempUser.getSchool());
        this.user.setSemester(this.tempUser.getSemester());
        userService.update(this.user);
        return "principalUser.xhtml";
    }

    public String prepareManageUser() {
        this.user = (User) listUsers.getRowData();
        this.tempUser = new User();
        return "gestionUsuario.xhtml";
    }

    public String addUser() {
        userService = new UserService();

        User u = userService.getUserByUsername(user.getUsername());

        FacesMessage m = null;
        boolean exists = false;

        if (u == null) {
            int length = 10;
            boolean useLetters = true;
            boolean useNumbers = false;
            ParameterDaoImpl paramDao = new ParameterDaoImpl();

            String password = RandomStringUtils.random(length, useLetters, useNumbers);
            String message = "Hola! " + user.getFullname()
                    + ",\nbienvenido al Sistema de Control de Ingreso de la Universidad El Bosque,"
                    + " su contraseña es: " + password + "\nDeberá cambiarla al momento de ingresar al sistema";
            String subject = "Bienvenido al Sistema de Control de Ingreso UEB";

            sendMail(user.getEmailaddress(), message, subject);
            user.setPassword("" + paramDao.getParameter(1).getTextvalue() + Cifrado.getStringMessageDigest(password, Cifrado.MD5));
            userService.insert(user);

            u = userService.getUserByUsername(user.getUsername());

            java.sql.Timestamp d = new java.sql.Timestamp(System.currentTimeMillis());
            audit.setCreatedate(d);
            audit.setAddressip(getLocalAddress());
            audit.setId(0);
            audit.setUserid(loguser.getId());
            audit.setTablename("user");
            audit.setTableid(u.getId());
            audit.setOperationcrud("C");
            auditService.insert(audit);
            exists = true;
        } else {
            m = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error en registro", "El usuario ya existe");
            FacesContext.getCurrentInstance().addMessage(null, m);
            PrimeFaces.current().ajax().addCallbackParam("exists", exists);
            return null;
        }

        return "principalAdmin.xhtml";
    }

    public String removeUser() {
        userService = new UserService();
        auditService = new AuditService();
        FacesMessage message = null;
        user = (User) listUsers.getRowData();
        if (user.getActive().equals("I")) {
            addMessage("Acción imposible", "El usuario ya se encuentra inactivo");
        } else {
            user.setActive("I");
            userService.update(user);
            addMessage("Acción exitosa", "El usuario ha sido desactivado");
            java.sql.Timestamp d = new java.sql.Timestamp(System.currentTimeMillis());
            audit.setCreatedate(d);
            audit.setAddressip(getLocalAddress());
            audit.setId(0);
            audit.setUserid(loguser.getId());
            audit.setTablename("user");
            audit.setTableid(user.getId());
            audit.setOperationcrud("U");
            auditService.insert(audit);
        }
        boolean show = false;
        PrimeFaces.current().ajax().addCallbackParam("show", show);
        return null;
    }

    public String prepareAddUser() {
        java.sql.Timestamp d = new java.sql.Timestamp(System.currentTimeMillis());
        user = new User();
        user.setActive("A");
        user.setId(0);
        audit.setUserid(loguser.getId());
        user.setDatelastpassword(d);
        user.setFailedattempts(0);

        return "newUser.xhtml";
    }

    public String prepareUpdateUser() {
        user = (User) listUsers.getRowData();
        return "updateUser.xhtml";
    }


    public DataModel<User> getListUsers() {
        userService = new UserService();
        List<User> list = userService.getUsers();
        listUsers = new ListDataModel<>(list);

        return listUsers;
    }

    public DataModel<Audit> getListAudit() {
    return this.listAudit;
    }

    public DataModel<Audit> getListofAudit() {
        auditService = new AuditService();
        List<Audit> list = auditService.getAudits();
        listAudit = new ListDataModel<>(list);

        return listAudit;
    }

    public DataModel<Audit> filterByDate() {
        auditService = new AuditService();
        if (date1 == null || date2 == null) {
            addMessage("ERROR", "Las fechas deben ser sucesivas.");
            return getListAudit();
        }
        Timestamp t1 = new Timestamp(date1.getTime());
        Timestamp t2 = new Timestamp(date2.getTime());
        log.info(t1);
        log.info(t2);
        if (t1.before(t2)) {
            addMessage("Procesando", "Las fechas son sucesivas.");
            List<Audit> list = auditService.getBetweenDates(t1 , t2);

            for (Audit a: list
                 ) {
               if (a.getCreatedate().before(t1) || a.getCreatedate().after(t2)) {
                   list.remove(a);
               }
            }
            listAudit = new ListDataModel<>(list);
//            RequestContext context = RequestContext.getCurrentInstance();
            FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("tbl");

//            context.update("tbl");
            return listAudit;
        } else  {
            addMessage("ERROR", "Las fechas deben ser sucesivas.");
            return getListAudit();
        }

    }

    public DataModel<Audit> getOperationC() {
        auditService = new AuditService();
        List<Audit> list = auditService.getByOperation("C");
        listAudit = new ListDataModel<>(list);
        FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("tbl");
        return listAudit;
    }
    public DataModel<Audit> getOperationU() {
        auditService = new AuditService();
        List<Audit> list = auditService.getByOperation("U");
        listAudit = new ListDataModel<>(list);
        FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("tbl");
        return listAudit;
    }
    public DataModel<Audit> getOperationI() {
        auditService = new AuditService();
        List<Audit> list = auditService.getByOperation("I");
        listAudit = new ListDataModel<>(list);
        FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("tbl");
        return listAudit;
    }
    public DataModel<Audit> getOperationS() {
        auditService = new AuditService();
        List<Audit> list = auditService.getByOperation("S");
        listAudit = new ListDataModel<>(list);
        FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("tbl");
        return listAudit;
    }


    public String loginUser() {
        userService = new UserService();
        User u = userService.getUserByDNI(Integer.parseInt(loguser.getIdentification()));
        boolean wrongPassword = true, wrongUsername = true;
        boolean loggedIn = false;

        if (u != null) {
            wrongUsername = false;

            if (u.getActive().equals("A") &&
                    u.getPassword().equals(
                            new ParameterDaoImpl().
                                    getParameter(1).
                                    getTextvalue()
                                    + Cifrado.getStringMessageDigest(
                                    loguser.getPassword(),
                                    Cifrado.MD5))) {
                log.info("First loging, redirecting to new password...");
                return forgotPassword();
            } else {
                if (u.getUsertype().equalsIgnoreCase("ADMIN") && u.getActive().equals("A")) {
                    if (u.getPassword().equals(Cifrado.getStringMessageDigest(loguser.getPassword(), Cifrado.MD5))) {
                        loguser = u;
                        auditService = new AuditService();
                        java.sql.Timestamp d = new java.sql.Timestamp(System.currentTimeMillis());
                        audit.setCreatedate(d);
                        audit.setAddressip(getLocalAddress());
                        audit.setId(0);
                        audit.setUserid(loguser.getId());
                        audit.setTablename("user");
                        audit.setTableid(loguser.getId());
                        audit.setOperationcrud("I");
                        auditService.insert(audit);
                        setTable("usersTable.xhtml");
                        wrongPassword = false;
                        loggedIn = true;
                        return "principalAdmin.xhtml";
                    }
                } else {
                    if (u.getActive().equals("A")
                            && u.getPassword().equals(Cifrado.getStringMessageDigest(loguser.getPassword(), Cifrado.MD5))) {
                        u.setFailedattempts(0);
                        this.qr = QRService.encode(u);
                        userService.update(u);
                        loguser = u;
                        this.user = u;
                        auditService = new AuditService();
                        java.sql.Timestamp d = new java.sql.Timestamp(System.currentTimeMillis());
                        audit.setCreatedate(d);
                        audit.setAddressip(getLocalAddress());
                        audit.setId(0);
                        audit.setUserid(loguser.getId());
                        audit.setTablename("user");
                        audit.setTableid(loguser.getId());
                        audit.setOperationcrud("I");
                        auditService.insert(audit);
                        if (Days.daysBetween(new DateTime(u.getDatelastpassword().getTime()), new DateTime(d.getTime())).getDays() >
                                new ParameterDaoImpl().getParameter(2).getNumbervalue()) {
                            log.info("La clave ha caducado");
                            boolean useLetters = true;
                            boolean useNumbers = false;
                            String password = RandomStringUtils.random(10, useLetters, useNumbers);
                            String mail = "Hola! " + user.getFullname()
                                    + "\n Su contraseña ha caducado."
                                    + ",\nSu nueva contraseña en el Sistema de Control de Ingreso de la Universidad El Bosque" + " es: "
                                    + password + "\nDeberá cambiarla al momento de ingresar al sistema";
                            String subject = "Cambio Contraseña";

                            sendMail(user.getEmailaddress(), mail, subject);
                            user.setDatelastpassword(d);
                            user.setPassword(new ParameterDaoImpl().getParameter(1).getTextvalue() + Cifrado.getStringMessageDigest(password, Cifrado.MD5));
                            userService.update(user);
                            auditService = new AuditService();
                            audit.setCreatedate(d);
                            audit.setAddressip(getLocalAddress());
                            audit.setId(0);
                            audit.setUserid(loguser.getId());
                            audit.setTablename("user");
                            audit.setTableid(user.getId());
                            audit.setOperationcrud("U");
                            auditService.insert(audit);
                        }
                        wrongPassword = false;
                        loggedIn = true;
                        this.setTable("/user/QR.xhtml");
                        return "/user/principalUser.xhtml";
                    } else {
                        u.setFailedattempts(u.getFailedattempts() + 1);
                        if (u.getFailedattempts() >= 3) {
                            u.setActive("I");
                        }
                        userService.update(u);
                    }
                }
            }
        }
        if (wrongUsername) {
            addMessage("Error en login", "Usuario no encontrado");
        } else if (u.getActive().equals("I")) {
            addMessage("Error en login", "Usuario Inactivo");
        } else if (wrongPassword) {
            addMessage("Error en login", "Contraseña incorrecta");
        }
        PrimeFaces.current().ajax().addCallbackParam("loggedIn", loggedIn);
        return null;
    }

    public String logout() throws IOException {
        auditService = new AuditService();
        java.sql.Timestamp d = new java.sql.Timestamp(System.currentTimeMillis());
        audit.setCreatedate(d);
        audit.setAddressip(getLocalAddress());
        audit.setId(0);
        audit.setUserid(loguser.getId());
        audit.setTablename("user");
        audit.setTableid(loguser.getId());
        audit.setOperationcrud("S");
        auditService.insert(audit);
        loguser = new User();
        table = "";
        FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
        return "login.xhtml";
    }


    public void sendMail(String email, String message, String subject) {
        Mail.sendMail(email, message, subject);
    }

    public void generateRandomPassword() {
        user = listUsers.getRowData();
        boolean useLetters = true;
        boolean useNumbers = false;
        String password = RandomStringUtils.random(10, useLetters, useNumbers);
        String message = "Hola! " + user.getFullname()
                + ",\nsu nueva contraseña en el Sistema de Control de Ingreso de la Universidad El Bosque" + " es: "
                + password + "\nDeberá cambiarla al momento de ingresar al sistema";
        String subject = "Cambio Contraseña";
        sendMail(user.getEmailaddress(), message, subject);

        java.sql.Timestamp d = new java.sql.Timestamp(System.currentTimeMillis());
        user.setDatelastpassword(d);
        user.setPassword(Cifrado.getStringMessageDigest(password, Cifrado.MD5));
        userService.update(user);

        auditService = new AuditService();
        audit.setCreatedate(d);
        audit.setAddressip(getLocalAddress());
        audit.setId(0);
        audit.setUserid(loguser.getId());
        audit.setTablename("user");
        audit.setTableid(user.getId());
        audit.setOperationcrud("U");
        auditService.insert(audit);
        log.info("Nueva contraseña generada exitosamente");
        addMessage("Éxito", "Nueva Contraseña Generada");

    }

    public String prepareForgotPassword() {
        user = new User();
        return "forgotPassword.xhtml";
    }

    public String generatePasswordLogin() {
        boolean validEmail = false;
        userService = new UserService();
        User u = userService.getUserByEmailAddress(user.getEmailaddress());

        if (u != null) {
            int length = 10;
            boolean useLetters = true;
            boolean useNumbers = false;
            String password = RandomStringUtils.random(length, useLetters, useNumbers);
            String msg = "Hola! " + user.getFullname()
                    + ",\nsu nueva contraseía en el Sistema de Control de Ingreso de la Universidad El Bosque" + " es: "
                    + password + "\nDeberá cambiarla al momento de ingresar al sistema";
            String subject = "Cambio Contraseña";

            sendMail(user.getEmailaddress(), msg, subject);

            java.sql.Timestamp d = new java.sql.Timestamp(System.currentTimeMillis());
            user.setDatelastpassword(d);
            user.setPassword(Cifrado.getStringMessageDigest(password, Cifrado.MD5));

            userService.update(user);

            auditService = new AuditService();

            audit.setCreatedate(d);
            audit.setAddressip(getLocalAddress());
            audit.setId(0);
            audit.setUserid(loguser.getId());
            audit.setTablename("user");
            audit.setTableid(user.getId());
            audit.setOperationcrud("U");
            auditService.insert(audit);
            validEmail = true;
            return "login.xhtml";
        } else {
            addMessage("Error", "Correo no encontrado");
            PrimeFaces.current().ajax().addCallbackParam("validEmail", validEmail);
            return null;
        }
    }



    public String forgotPassword() {
        userService = new UserService();
        this.user = userService.getUserByDNI(Integer.parseInt(loguser.getIdentification()));
        this.user.setPassword("");
        return "newPassword.xhtml";
    }

    public void check(ActionEvent e) throws IOException {
        addMessage("¡Felicidadades no eres un bot!", "");
        this.submitNewPassword();
    }

    public void submitNewPassword() throws IOException {
        java.sql.Timestamp d = new java.sql.Timestamp(System.currentTimeMillis());
        user.setDatelastpassword(d);
        user.setPassword(Cifrado.getStringMessageDigest(user.getPassword(), Cifrado.MD5));
        userService.update(this.user);
        FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
    }

    public String[] getMajors() {
        String school = user.getSchool();

        if (school != null) {
            if (school.equals(schools[0])) {
                majors = new String[6];
                majors[0] = "Biología";
                majors[1] = "Estadística";
                majors[2] = "Matemáticas";
                majors[3] = "Química Farmacéutica";
                majors[4] = "Maestría en Ciencias Básicas Biomédicas";
                majors[5] = "Doctorado en Ciencias Biomédicas";
            } else if (school.equals(schools[1])) {
                majors = new String[2];
                majors[0] = "Administración de Empresas";
                majors[1] = "Negocios Internacionales";
            } else if (school.equals(schools[2])) {
                majors = new String[2];
                majors[0] = "Ciencia Política";
                majors[1] = "Derecho";
            } else if (school.equals(schools[3])) {
                majors = new String[9];
                majors[0] = "Arquitectura";
                majors[1] = "Arte Dramático";
                majors[2] = "Artes Plásticas";
                majors[3] = "Diseño de Comunicación";
                majors[4] = "Diseño Industrial";
                majors[5] = "Formación Musical";
                majors[6] = "Maestría en Diseño para Industrias Creativas y Culturales";
                majors[7] = "Maestría en Músicas Colombianas";
                majors[8] = "Especialización en Ergonomía";
            } else if (school.equals(schools[4])) {
                majors = new String[4];
                majors[0] = "Licenciatura en Educación Infantil";
                majors[1] = "Licenciatura en Bilingüismo con Énfasis en la Enseñanza del Inglés";
                majors[2] = "Especialización en Docencia Universitaria";
                majors[3] = "Maestría en Docencia de la Educación Superior";
            } else if (school.equals(schools[5])) {
                majors = new String[6];
                majors[0] = "Enfermeria";
                majors[1] = "Especialización en Enfermería Neonatal";
                majors[2] = "Especialización en Seguridad del Paciente";
                majors[3] = "Maestría en Enfermería en Cuidados Paliativos";
                majors[4] = "Maestría en Salud Mental Comunitaria";
                majors[5] = "Maestría en Salud Sexual y Reproductiva";
            } else if (school.equals(schools[6])) {
                majors = new String[10];
                majors[0] = "Bioingeniería";
                majors[1] = "Ingeniería Ambiental";
                majors[2] = "Ingeniería Electrónica";
                majors[3] = "Ingeniería Industrial";
                majors[4] = "Ingeniería de Sistemas";
                majors[5] = "Especialización en Diseño de Redes Telemáticas";
                majors[6] = "Especialización en Gerencia de Producción y Productividad";
                majors[7] = "Especialización en Gerencia de Proyectos";
                majors[8] = "Especialización en Seguridad de Redes Telemáticas";
                majors[9] = "Maestráa en Gestión Empresarial Ambiental";
            } else if (school.equals(schools[7])) {
                return new String[]{"Instrumentación Quirúrgica", "Medicina", "Optometría",
                        "Especialización en Anestesia Cardiovascular y Torácica",
                        "Especialización en Anestesiología FSFB", "Especialización en Anestesiología HSB",
                        "Especialización en Cardiología Adultos", "Especialización en Cardiología Pediátrica",
                        "Especialización en Cirugía de Mano", "Especialización en Cirugía del Tórax",
                        "Especialización en Cirugía General",
                        "Especialización en Cirugía Plástica Reconstructiva y Estética",
                        "Especialización en Cirugía Vascular y Angiología", "Especialización en Dermatología",
                        "Especialización en Enfermería Neonatal", "Especialización en Gastroenterología Pediátrica",
                        "Especialización en Ginecología y Obstetricia", "Especialización en Infectología Pediátrica",
                        "Especialización en Medicina Crética y Cuidado Intensivo",
                        "Especialización en Medicina Crética y Cuidado Intensivo Pediátrico",
                        "Especialización en Medicina del Deporte",
                        "Especialización en Medicina del Dolor y Cuidados Paliativos",
                        "Especialización en Medicina Familiar", "Especialización en Medicina Física y Rehabilitación",
                        "Especialización en Medicina Interna FSFB", "Especialización en Medicina Interna HSC",
                        "Especialización en Medicina Materno-Fetal", "Especialización en Nefrología Pediátrica",
                        "Especialización en Neonatología", "Especialización en Neumología",
                        "Especialización en Neumología Pediátrica", "Especialización en NeuroCirugía",
                        "Especialización en Neurología", "Especialización en Oftalmología",
                        "Especialización en Oncología Clónica", "Especialización en Ortopedia y Traumatología",
                        "Especialización en Pediatría", "Especialización en Psiquiatría de Enlace e Interconsultas",
                        "Especialización en Psiquiatría Infantil y del Adolescente",
                        "Especialización en Radiología e Imágenes Diagósticas",
                        "Especialización en Reumatología Pediátrica", "Especialización en Salud Familiar y Comunitaria",
                        "Especialización en Urología", "Maestría en Epidemiología", "Maestría en Informática Biomédica",
                        "Maestría en Salud Pública", "Doctorado en Salud Pública"};
            } else if (school.equals(schools[8])) {
                return new String[]{"Odontología", "Especialización en Cirugía Oral y Maxilofacial",
                        "Especialización en Endodoncia",
                        "Especialización en Operatoria Dental Estética y Materiales Dentales",
                        "Especialización en Ortodoncia", "Especialización en Patología Oral y Medios Diagnósticos",
                        "Especialización en Periodoncia y Medicina Oral", "Especialización en Prostodoncia",
                        "Maestría en Ciencias Odontológicas"};

            } else if (school.equals(schools[9])) {
                return new String[]{"Psicología", "Especialización en Investigación de los Mercados y del Consumo",
                        "Especialización en Psicología Clónica y Autoeficacia Personal",
                        "Especialización en Psicología Clónica y Desarrollo Infantil",
                        "Especialización en Psicología del Deporte y el Ejercicio",
                        "Especialización en Psicología Médica y de la Salud",
                        "Especialización en Psicología Ocupacional y Organizacional",
                        "Especialización en Psicología Social, Cooperación y Gestión Comunitaria",
                        "Maestría en Psicología"};
            } else if (school.equals(schools[10])) {
                return new String[]{"Filosofía", "Especialización en Filosofía de la Ciencia",
                        "Maestría en Estudios Sociales y Culturales"};
            } else if (school.equals(schools[11])) {
                return new String[]{"Especialización en Bioética", "Maestría en Bioética", "Doctorado en Bioética",};
            } else if (school.equals(schools[12])) {
                return new String[]{"Administración de Empresas - Modalidad Virtual",
                        "Contaduría Pública - Modalidad Virtual", "Negocios Internacionales - Modalidad Virtual"};
            } else if (school.equals(schools[13])) {
                return new String[]{"No aplica"};
            }
        }
        return majors;
    }

    public String getQr() {
        return qr;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public User getloguser() {
        return loguser;
    }

    public void setloguser(User loguser) {
        this.loguser = loguser;
    }

    private static String getLocalAddress() {
        return Ip.getIp();
    }

    public String[] getSchools() {
        return schools;
    }

    public String[] getSemesters() {
        return semesters;
    }

    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    public Date getDate2() {
        return date2;
    }

    public void setDate2(Date date2) {
        this.date2 = date2;
    }

    private void addMessage(String summary, String detail) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(summary, detail));
    }
}
