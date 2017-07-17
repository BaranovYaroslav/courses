CREATE TABLE IF NOT EXISTS `location` (
  id int(11) NOT NULL AUTO_INCREMENT,
  city varchar(225) NOT NULL,
  address varchar(225) NOT NULL,
  x double NOT NULL,
  y double NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS `user` (
  id int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(45) NOT NULL,
  `full_name` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(225) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS user_group (
  user_id int(11) NOT NULL,
  `group` varchar(45) NOT NULL,
  PRIMARY KEY (user_id),
  CONSTRAINT fk_user_group FOREIGN KEY (user_id) REFERENCES `user` (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS course (
  id int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  description varchar(45) NOT NULL,
  start_date varchar(45) NOT NULL,
  end_date varchar(45) NOT NULL,
  professor_id int(11) NOT NULL,
  price double NOT NULL,
  is_free tinyint(4) NOT NULL,
  students_number int(11) NOT NULL,
  `type` varchar(45) NOT NULL,
  location_id int(11) NOT NULL,
  PRIMARY KEY (id),
  KEY fk_course_professor_idx (professor_id),
  KEY fk_course_location_idx (location_id),
  CONSTRAINT fk_course_location FOREIGN KEY (location_id) REFERENCES location (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_course_professor FOREIGN KEY (professor_id) REFERENCES `user` (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS feedback (
  id int(11) NOT NULL AUTO_INCREMENT,
  score double NOT NULL,
  `comment` varchar(225) NOT NULL,
  course_id int(11) NOT NULL,
  student_id int(11) NOT NULL,
  PRIMARY KEY (id),
  KEY course_id_idx (course_id),
  KEY student_id_idx (student_id),
  CONSTRAINT fk_course_feedback FOREIGN KEY (course_id) REFERENCES course (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_student_feedback FOREIGN KEY (student_id) REFERENCES `user` (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);


CREATE TABLE IF NOT EXISTS student_course (
  user_id int(11) NOT NULL,
  course_id int(11) NOT NULL,
  PRIMARY KEY (user_id,course_id),
  KEY course_id_idx (course_id),
  CONSTRAINT fk_student_course_course_id FOREIGN KEY (user_id) REFERENCES `user` (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_student_course_student_id FOREIGN KEY (course_id) REFERENCES course (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);
