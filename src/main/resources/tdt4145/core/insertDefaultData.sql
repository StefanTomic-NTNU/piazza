INSERT INTO User (userID, name, email, password, salt, instructor_privileges)
VALUES ('1', 'professor', 'professor@ntnu.no',
        UNHEX('8510843722F5345181403D131FC9DCF04C83C7BB5E7B7E3732AB7C34FEB4B3EB'),
        UNHEX('070DF3A40AC0958C1EB63362C2BCD4CF'), b'1'),
       ('2', 'student', 'student@ntnu.no',
        UNHEX('D96B0D45AB62232D5AC7BAD77884605F81D275A9B0B299CF222E6654A486C07B'),
        UNHEX('9AA7C77496F60697187A7B2DC6DBE964'), b'0');

INSERT INTO Course
VALUES ('1', 'Databaser'),
       ('2', 'Objekt-orientert programmering');

INSERT INTO ActiveCourse
VALUES ('Vaar', '2021', b'1', '1'),
       ('Vaar', '2021', b'1', '2');

INSERT INTO CourseParticipation
VALUES ('2', '1'),
       ('2', '2');

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


