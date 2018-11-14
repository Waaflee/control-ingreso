INSERT INTO users
	(id, userName, password, fullName, identification, emailAddress, phoneNumber, school, major, semester, dateLastPassword, active, userType, failedAttempts)
VALUES
	(0 ,'admin', '827ccb0eea8a706c4c34a16891f84e7b', 'Administrador Agustin', '12345678', 'admina@admins.co', '', 'Facultad de Ingenieria', 'Ingenieria de Sistemas', '7', NOW(), 'A', 'ADMIN', 0);

insert into parameter (id, parametertype, parametercode, descriptionparameter, textvalue, numbervalue, state) values (1, 's','1010','Valor primer contraseña', '#', 0, 'A');

insert into parameter (id, parametertype, parametercode, descriptionparameter, textvalue, numbervalue, state) values (2, 'i','1020','Vigencia contraseña', '', 30, 'A');
