USE toeiconline;

CREATE TABLE examination (
  examinationid BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  image VARCHAR(255) NOT NULL,
  createddate TIMESTAMP NULL,
  modifieddate TIMESTAMP NULL,
  PRIMARY KEY (examinationid),
  UNIQUE (name)
);

CREATE TABLE examinationquestion (
  examinationquestionid bigint NOT NULL AUTO_INCREMENT,
  image VARCHAR(255) NULL,
  audio VARCHAR(255) NULL,
  question TEXT NOT NULL,
  paragraph TEXT,
  option1 VARCHAR(300) NOT NULL,
  option2 VARCHAR(300) NOT NULL,
  option3 VARCHAR(300) NOT NULL,
  option4 VARCHAR(300) NOT NULL,
  correctanswer VARCHAR(10) NOT NULL,
  exexaminationid BIGINT NOT NULL,
  createddate TIMESTAMP NULL,
  modifieddate TIMESTAMP NULL,
  PRIMARY KEY (examinationquestionid),
  CONSTRAINT fk_examinationquestion_examination FOREIGN KEY (exexaminationid) REFERENCES examination (examinationid)
);

CREATE TABLE exercise (
  exerciseid BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  type VARCHAR(45) NOT NULL,
  createddate TIMESTAMP NULL,
  modifieddate TIMESTAMP NULL,
  PRIMARY KEY (exerciseid),
  UNIQUE (name)
);

CREATE TABLE exercisequestion (
  exercisequestionid BIGINT NOT NULL AUTO_INCREMENT,
  image VARCHAR(255) NULL,
  audio VARCHAR(255) NULL,
  question TEXT NOT NULL,
  option1 VARCHAR(300) NOT NULL,
  option2 VARCHAR(300) NOT NULL,
  option3 VARCHAR(300) NOT NULL,
  option4 VARCHAR(300) NOT NULL,
  correctanswer VARCHAR(10) NOT NULL,
  exerciseid BIGINT NOT NULL,
  createddate TIMESTAMP NULL,
  modifieddate TIMESTAMP NULL,
  PRIMARY KEY (exercisequestionid),
  CONSTRAINT fk_exercisequestion_exercise FOREIGN KEY (exerciseid) REFERENCES exercise (exerciseid)
);

CREATE TABLE result (
  resultid BIGINT NOT NULL AUTO_INCREMENT,
  listenscore INT(11) NOT NULL,
  readingscore INT(11) NOT NULL,
  examinationid BIGINT NOT NULL,
  userid BIGINT NOT NULL,
  createddate TIMESTAMP NULL,
  modifieddate TIMESTAMP NULL,
  PRIMARY KEY (resultid),
  CONSTRAINT fk_result_examination FOREIGN KEY (examinationid) REFERENCES examination (examinationid),
  CONSTRAINT fk_result_user FOREIGN KEY (userid) REFERENCES user (userid)
);

INSERT INTO `exercisetype` VALUES (1,'Bài tập phần nghe','listening','2017-11-23 19:28:10',NULL),(2,'Bài tập phần đọc','reading','2017-11-23 19:28:10',NULL);