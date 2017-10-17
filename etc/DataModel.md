mysql> describe meeting;
+----------------+--------------+------+-----+-------------------+----------------+
| Field          | Type         | Null | Key | Default           | Extra          |
+----------------+--------------+------+-----+-------------------+----------------+
| id             | bigint(20)   | NO   | PRI | NULL              | auto_increment |
| name           | varchar(255) | YES  |     | NULL              |                |
| event_id       | bigint(20)   | NO   | MUL | NULL              |                |
| num_asistentes | int(11)      | YES  |     | 0                 |                |
| type           | int(11)      | YES  |     | 0                 |                |
| smoke          | int(11)      | YES  |     | 0                 |                |
| alcohol        | int(11)      | YES  |     | 0                 |                |
| team           | varchar(255) | YES  |     | NULL              |                |
| description    | text         | YES  |     | NULL              |                |
| create_time    | timestamp    | YES  |     | CURRENT_TIMESTAMP |                |
| start_time     | timestamp    | YES  |     | NULL              |                |
| finish_time    | timestamp    | YES  |     | NULL              |                |
| tag            | varchar(255) | YES  |     | NULL              |                |
| latitude       | double       | YES  |     | 0                 |                |
| longitude      | double       | YES  |     | 0                 |                |
| active         | int(11)      | YES  |     | 1                 |                |
+----------------+--------------+------+-----+-------------------+----------------+

type es:

    const HOME = 0;
    const BAR = 1;
    const LIVE = 2;


mysql> describe user_has_meeting;
+----------------+
| id            | bigint(20) | NO   | PRI | NULL    | auto_increment |
| user_id       | bigint(20) | NO   | MUL | NULL    |                |
| meeting_id    | bigint(20) | NO   | MUL | NULL    |                |
| status        | int(11)    | YES  |     | 0       |                |
| accepted_time | timestamp  | YES  |     | NULL    |                |
+---------------+------------+------+-----+---------+----------------+

UserHasMeeting status:

    const NOT_JOIN = 0;
    const JOIN = 1;
    const ACCEPTED = 2;
    const NOT_ACCEPTED = 3;
    const OWNER = 4;
    const RATING = 5;
    const FINISH = 6;
    const OWNER_RATE = 7;


mysql> describe notification;
+--------------+---------------+------+-----+-------------------+----------------+
| Field        | Type          | Null | Key | Default           | Extra          |
+--------------+---------------+------+-----+-------------------+----------------+
| id           | bigint(20)    | NO   | PRI | NULL              | auto_increment |
| type         | int(11)       | YES  | MUL | 0                 |                |
| status       | int(11)       | YES  |     | 0                 |                |
| send_time    | timestamp     | YES  |     | NULL              |                |
| receive_time | timestamp     | YES  |     | NULL              |                |
| user_id      | bigint(20)    | YES  | MUL | NULL              |                |
| from_user_id | bigint(20)    | YES  | MUL | NULL              |                |
| description  | text          | YES  |     | NULL              |                |
| photoUrl     | varchar(1024) | YES  |     | NULL              |                |
| meeting_id   | bigint(20)    | YES  | MUL | NULL              |                |
| create_time  | timestamp     | YES  |     | CURRENT_TIMESTAMP |                |
| action       | int(11)       | YES  |     | 0                 |                |
+--------------+---------------+------+-----+-------------------+----------------+

mysql> describe address
    -> ;
+-------------+--------------+------+-----+---------+----------------+
| Field       | Type         | Null | Key | Default | Extra          |
+-------------+--------------+------+-----+---------+----------------+
| id          | int(11)      | NO   | PRI | NULL    | auto_increment |
| street      | varchar(255) | YES  |     | NULL    |                |
| city        | varchar(255) | YES  |     | NULL    |                |
| postal_code | varchar(255) | YES  |     | NULL    |                |
| country     | varchar(255) | YES  |     | NULL    |                |
| meeting_id  | bigint(20)   | YES  | MUL | NULL    |                |
+-------------+--------------+------+-----+---------+----------------+