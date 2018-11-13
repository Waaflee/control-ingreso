package util;

import entity.Audit;
import entity.User;
import service.AuditService;
import service.UserService;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class DeactivateVisitors {

	public void deactivate() {
		TimerTask repeatedTask = new TimerTask() {
			public void run() {
				UserService service = new UserService();

				List<User> l = service.getUsersFiltered("userType='visitante'");

				Iterator<User> it = l.iterator();

				while (it.hasNext()) {
					User u = it.next();
					AuditService auditService = new AuditService();
					List<Audit> list = auditService
							.getAuditsFiltered("tableId =" + u.getId() + " and operationCrud='C'");
					List<Audit> list2 = auditService
							.getAuditsFiltered("tableId =" + u.getId() + " and operationCrud='U'");
					Date d = new Date();
					int diff1 = service.getDateDifference(list.get(0).getCreatedate(), d);
					if (!list2.isEmpty()) {
						int diff2 = service.getDateDifference(list2.get(list.size() - 1).getCreatedate(), d);
						if (u.getActive().equals("A") && diff1 > 1 && diff2 > 1) {
							u.setActive("I");
							service.update(u);
						}
					} else {
						if (u.getActive().equals("A") && diff1 > 1) {
							u.setActive("I");
							service.update(u);
						}
					}
				}
			}
		};

		Timer timer = new Timer("Timer");

		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 1);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);

		timer.scheduleAtFixedRate(repeatedTask, today.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
	}

}
