package service;

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
		// TODO Auto-generated method stub
		return auditDao.getAudit(id);
	}

	public List<Audit> getAuditsFiltered(String filter) {
		// TODO Auto-generated method stub
		return auditDao.getAuditsFiltered(filter);
	}
	
	
}
