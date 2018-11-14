CREATE TABLE audit (
	id serial primary key,
	userId int NOT NULL,
	operationCrud varchar(1) NOT NULL,
	tableName varchar(30) NOT NULL,
	tableId int NOT NULL,
	createDate TIMESTAMP NOT NULL,
	addressIP varchar(10) NOT NULL
);

CREATE TABLE entryoutput (
	id serial primary key,
	dateEntry TIMESTAMP NOT NULL,
	dateOutput TIMESTAMP DEFAULT NULL,
	identification varchar(12) NOT NULL,
	dependency varchar(40) NOT NULL,
	personVisit varchar(40) NOT NULL,
	idQRCode int NOT NULL,
	doorEntry varchar(30) NOT NULL,
	doorOutput varchar(30) DEFAULT NULL,
	comments varchar(80) DEFAULT NULL
);

CREATE TABLE parameter (
	id serial primary key,
	parameterType varchar(1) NOT NULL,
	parameterCode varchar(10) NOT NULL,
	descriptionParameter varchar(60) NOT NULL,
	textValue varchar(10) NOT NULL,
	numberValue int NOT NULL,
	state varchar(1) NOT NULL
);

CREATE TABLE qrcode (
	id serial primary key,
	qrCode varchar(600) NOT NULL,
	dateStart TIMESTAMP NOT NULL,
	dateEnd TIMESTAMP NOT NULL
);

CREATE TABLE users (
	id serial primary key,
	userName varchar(8) DEFAULT NULL,
	password varchar(256) DEFAULT NULL,
	fullName varchar(60) NOT NULL,
	identification varchar(12) NOT NULL,
	emailAddress varchar(75) DEFAULT NULL,
	school VARCHAR(100) NOT NULL,
	major VARCHAR(100) NOT NULL,
	semester VARCHAR(10) NOT NULL,
	phoneNumber varchar(10) NOT NULL,
	dateLastPassword TIMESTAMP NOT NULL,
	active varchar(1) NOT NULL,
	userType varchar(12) NOT NULL,
	failedAttempts int NOT NULL DEFAULT 0
);
