package service;

import dao.ParameterDaoImpl;
import dao.QRDaoImpl;
import entity.Qrcode;
import entity.User;

import java.sql.Timestamp;

public class QRService {

    private static void generate(User u) {
        Qrcode qrcode = new Qrcode();
        qrcode.setDatestart(new Timestamp(System.currentTimeMillis()));
        // dias de vigencia parametrizados convertidos a milisegundos.
        qrcode.setDateend(new Timestamp(System.currentTimeMillis() + ((long) 86400000 * (long) new ParameterDaoImpl().getParameter(2).getNumbervalue())));
        qrcode.setQrcode("Nombre:" + u.getFullname() + "\n" +
                "Email:" + u.getEmailaddress() + "\n" +
                "Facultad:" + u.getSchool() + "\n" +
                "Carrera:" + u.getMajor() + "\n" +
                "Phone:" + u.getPhonenumber() + "\n" +
                "Identification:" + u.getIdentification());
        qrcode.setId(u.getId());
        QRDaoImpl.put(qrcode);
    }

    private static Qrcode retrieve(User u) {
        return QRDaoImpl.getQrcode(u.getId());
    }

    public static String refresh(User u) {
        generate(u);
        return retrieve(u).getQrcode();
    }
	
	
}
