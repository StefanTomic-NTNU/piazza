INSERT INTO User (userID, name, email, password, instructor_privileges)
VALUES ('1', 'professor1', 'professor1@ntnu.no', 'profpass', b'1'),
       ('2', 'student2', 'student2@ntnu.no', 'studpass', b'0'),
       ('3', 'student3', 'student3@ntnu.no', 'studpass', b'0');

INSERT INTO Course
VALUES ('1', 'Databaser'),
       ('2', 'Objekt-orientert programmering');

INSERT INTO ActiveCourse
VALUES ('Vaar', '2021', b'1', '1'),
       ('Vaar', '2021', b'1', '2');

INSERT INTO CourseParticipation
VALUES ('2', '1'),
       ('2', '2'),
       ('3', '1');

INSERT INTO Folder
VALUES ('1', 'Exam'),
       ('2', 'Midtsemester'),
       ('3', 'Øvinger'),
       ('4', 'Øving 1');

INSERT INTO RootFolder
VALUES ('1', '1', 'Vaar', '2021'),
       ('2', '1', 'Vaar', '2021'),
       ('3', '2', 'Vaar', '2021');

INSERT INTO SubFolder
VALUES ('4', '3');

INSERT INTO Tags
VALUES ('1', 'Question'),
       ('2', 'Information')


