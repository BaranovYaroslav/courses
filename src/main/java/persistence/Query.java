package persistence;

/**
 * Holder of query constants.
 *
 * @author Yaroslav Baranov
 */
public interface Query {

    int NO_INDEX_CREATED_VALUE = -1;
    String INSERT_COURSE_QUERY = "BEGIN; " +
                                 "INSERT INTO `location` (`city`, `address`, `x`, `y`) " +
                                 "VALUES (?, ?, ?, ?); " +
                                 "INSERT INTO `course` (`name`, `description`, `start_date`, `end_date`, `professor_id`, " +
                                 "`students_number`, `price`, `is_free`, `type`, `location_id`) " +
                                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, LAST_INSERT_ID());" +
                                 "COMMIT;";
    String DELETE_COURSE_QUERY = "DELETE FROM `course` WHERE id=?;";
    String UPDATE_COURSE_QUERY = "UPDATE `course` SET `name`=?, `description`=?, `start_date`=?, `end_date`=?, " +
                                 "`professor_id`=?, `students_number`=?, `price`=?, `is_free`=?, `type`=? " +
                                 "WHERE id=?;";
    String FIND_COURSE_QUERY = "SELECT c.id, c.name, c.description, c.start_date, c.end_date, c.professor_id, " +
                               "c.students_number, c.price, c.is_free, type, c.location_id, location.city, " +
                               "location.address, location.x, location.y, user.id, user.full_name, user.email, " +
                               "user.login, user.password, user_group.group " +
                               "FROM course c " +
                               "INNER JOIN location ON location.id = c.location_id " +
                               "INNER JOIN user ON user.id = c.professor_id " +
                               "INNER JOIN user_group ON user_group.user_id = c.professor_id " +
                               "WHERE c.id=?;";
    String FIND_ALL_COURSES_QUERY = "SELECT c.id, c.name, c.description, c.start_date, c.end_date, c.professor_id, " +
                                    "c.students_number, c.price, c.is_free, type, c.location_id, location.city, " +
                                    "location.address, location.x, location.y, user.id, user.full_name, user.email, " +
                                    "user.login, user.password, user_group.group " +
                                    "FROM course c " +
                                    "INNER JOIN location ON location.id = c.location_id " +
                                    "INNER JOIN user ON user.id = c.professor_id " +
                                    "INNER JOIN user_group ON user_group.user_id = c.professor_id;";
    String REGISTER_STUDENT_QUERY = "INSERT INTO `student_course` (`student_id`, `course_id`) VALUES (?, ?);";
    String UNREGISTER_STUDENT_QUERY = "DELETE FROM `student_course` WHERE `student_id`=? AND `course_id`=?;";
    String GET_STUDENTS_FOR_COURSE_QUERY = "SELECT `id`, `login`, `full_name`, `email`, `password`, `user_group`.`group` " +
                                           "FROM `user` " +
                                           "JOIN `user_group` ON `id`=`user_group`.`user_id`" +
                                           "JOIN `student_course` ON `student_course`.`user_id`=`id`" +
                                           "WHERE `student_course`.`course_id`=?;";
    String INSERT_LOCATION_QUERY = "INSERT INTO `location` (`city`, `address`, `x`, `y`) VALUES (?, ?, ?, ?);";
    String DELETE_LOCATION_QUERY = "DELETE FROM `location` WHERE id=?;";
    String UPDATE_LOCATION_QUERY = "UPDATE `location` SET `city`=?, `address`=?, `x`=?, `y`=? WHERE `id`=?;";
    String FIND_LOCATION_QUERY = "SELECT * FROM `location` WHERE `id`=?;";
    String FIND_ALL_LOCATIONS_QUERY = "SELECT * FROM `location`;";
    String INSERT_FEEDBACK_QUERY = "INSERT INTO `feedback` (`score`, `comment`, `course_id`, `user_id`) VALUES(?, ?, ?, ?);";
    String DELETE_FEEDBACK_QUERY = "DELETE FROM `feedback` where id=?;";
    String UPDATE_FEEDBACK_QUERY = "UPDATE `feedback` SET `score`=?, `comment`=? WHERE id=?;";
    String FIND_FEEDBACK_QUERY = "SELECT * FROM `feedback` WHERE `id`=?";
    String FIND_ALL_FEEDBACKS_QUERY = "SELECT * FROM `feedback`;";
    String GET_FEEDBACKS_FOR_COURSE_QUERY = "SELECT * FROM `feedback` WHERE `course_id`=?";
    String GET_FEEDBACKS_FOR_STUDENT_QUERY = "SELECT * FROM `feedback` WHERE `user_id`=?;";
    String DELETE_FEEDBACKS_BY_COURSE_QUERY = "DELETE FROM `feedback` WHERE `course_id`=?;";
    String DELETE_FEEDBACK_BY_COURSE_AND_STUDENT_ID_QUERY = "DELETE FROM `feedback` WHERE `course_id`=? AND `user_id`=?;";
    String INSERT_USER_QUERY = "INSERT INTO `user` (`login`, `full_name`, `email`, `password`) " +
                               "VALUES (?, ?, ?, ?);";
    String DELETE_USER_QUERY = "DELETE FROM `user` WHERE `id`=?;";
    String UPDATE_USER_QUERY = "UPDATE `user` SET `login`=?, `full_name`=?, `email`=?, `password`=? WHERE `id`=?;";
    String FIND_USER_QUERY = "SELECT `id`, `login`, `full_name`, `email`, `password`, `user_group`.`group` " +
                             "FROM `user` " +
                             "JOIN `user_group` ON `id`=`user_group`.`user_id` " +
                             "WHERE `id`=?;";
    String FIND_ALL_USERS_QUERY = "SELECT `id`, `login`, `full_name`, `email`, `password`, `user_group`.`group` " +
                                  "FROM `user` " +
                                  "JOIN `user_group` ON `id`=`user_group`.`user_id`;";
    String FIND_USER_BY_LOGIN_QUERY = "SELECT `id`, `login`, `full_name`, `email`, `password`, `user_group`.`group` " +
                                      "FROM `user` " +
                                      "JOIN `user_group` ON `id`=`user_group`.`user_id` " +
                                      "WHERE `login`=?;";
    String GET_USER_ROLE_QUERY = "SELECT * FROM `user_group` WHERE user_id=?;";
    String INSERT_USER_ROLE_QUERY = "INSERT INTO `user_group` (`user_id`, `group`) VALUES (?, ?);";
    String GET_USER_ROLE_BY_LOGIN_QUERY = "SELECT * FROM `user_group` WHERE user_id=?;";
    String UNREGISTER_ALL_STUDENTS_FROM_COURSE_QUERY = "DELETE FROM `student_course` WHERE `course_id`=?;";



}
