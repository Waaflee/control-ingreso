INSERT INTO users
	(userName, password, fullName, identification, emailAddress, phoneNumber, school, major, semester, dateLastPassword, active, userType, failedAttempts)
VALUES
	('admin', '827ccb0eea8a706c4c34a16891f84e7b', 'Administrador Agustin', '', 'admina@admins.co', '', 'Facultad de Ingenieria', 'Ingenieria de Sistemas', '7', NOW(), 'A', 'ADMIN', 0);

insert into parameter (parametertype, parametercode, descriptionparameter, textvalue, numbervalue, state) values ('s','1010','Valor primer contraseña', '#', 0, 'A');

insert into parameter (parametertype, parametercode, descriptionparameter, textvalue, numbervalue, state) values ('i','1020','Vigencia contraseña', '', 30, 'A');
