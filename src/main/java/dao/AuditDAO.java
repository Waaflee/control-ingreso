package dao;

import java.util.List;

import entity.Audit;

public interface AuditDAO {

	public void insert(Audit newAudit);

	public List<Audit> getAudits();

	public Audit getAudit(int id);

	public List<Audit> getAuditsFiltered(String filter);

}
