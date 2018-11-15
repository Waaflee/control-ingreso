package service;

import java.sql.Timestamp;
import java.util.List;

import dao.AuditDaoImpl;
import entity.Audit;

public class AuditService{

	private AuditDaoImpl auditDao = new AuditDaoImpl();

	public void insert(Audit newAudit) {
		auditDao.insert(newAudit);
	}

	public List<Audit> getAudits() {
		
		return auditDao.getAudits();
	}

	public Audit getAudit(int id) {
		return auditDao.getAudit(id);
	}

	public List<Audit> getAuditsFiltered(String filter) {
		return auditDao.getAuditsFiltered(filter);
	}

	public List<Audit> getBetweenDates(Timestamp inicio, Timestamp fin) {
		String filter = String.format("createdate >= %s AND createdate < %s", inicio, fin);
		return getAuditsFiltered(filter);
	}
	
	
}
