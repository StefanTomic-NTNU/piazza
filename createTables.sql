CREATE TABLE `User` (
  `userID` int PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(64),
  `email` varchar(64),
  `password` BINARY(64),
  `salt` binary(16),
  `logged_in` bit DEFAULT 0,
  `instructor_privileges` bit DEFAULT 0
);

CREATE TABLE `Course` (
  `courseID` int PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255)
);

CREATE TABLE `ActiveCourse` (
  `term` varchar(16),
  `year` int,
  `allow_anonymous` bit,
  `courseID` int,
  PRIMARY KEY (`term`, `year`, `courseID`),
  CONSTRAINT `ActiveCourse_fk` FOREIGN KEY (`courseID`) REFERENCES `Course` (`courseID`) ON UPDATE CASCADE
);

CREATE TABLE `CourseParticipation` (
  `userID` int,
  `courseID` int,
  PRIMARY KEY (`userID`, `courseID`),
  CONSTRAINT `CourseParticipation_pk1` FOREIGN KEY (`userID`) REFERENCES `User` (`userID`) ON UPDATE CASCADE,
  CONSTRAINT `CourseParticipation_pk2` FOREIGN KEY (`courseID`) REFERENCES `Course` (`courseID`)ON UPDATE CASCADE
);

CREATE TABLE `Folder` (
  `folderID` int PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(32)
);

CREATE TABLE `RootFolder` (
  `folderID` int PRIMARY KEY,
  `courseID` int,
  `term` varchar(16),
  `year` int,
  CONSTRAINT `RootFolder_pk` FOREIGN KEY (`folderID`) REFERENCES `Folder` (`folderID`) ON UPDATE CASCADE,
  CONSTRAINT `RootFolder_fk1` FOREIGN KEY (`courseID`) REFERENCES `ActiveCourse` (`courseID`) ON UPDATE CASCADE ON DELETE SET NULL,
  CONSTRAINT `RootFolder_fk2` FOREIGN KEY (`term`, `year`) REFERENCES `ActiveCourse` (`term`, `year`) ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE `SubFolder` (
  `folderID` int PRIMARY KEY,
  `parent_folder` int,
  CONSTRAINT `SubFolder_pk` FOREIGN KEY (`folderID`) REFERENCES `Folder` (`folderID`) ON UPDATE CASCADE,
  CONSTRAINT `SubFolder_fk` FOREIGN KEY (`parent_folder`) REFERENCES `Folder` (`folderID`)
);

CREATE TABLE `Thread` (
  `threadID` int PRIMARY KEY AUTO_INCREMENT,
  `text` varchar(500),
  `anonymous` bit,
  `userID` int,
  CONSTRAINT `Thread_fk` FOREIGN KEY (`userID`) REFERENCES `User` (`userID`) ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE `Post` (
  `threadID` int PRIMARY KEY,
  `title` varchar(128),
  `colour` int,
  `folderID` int,
  CONSTRAINT `Post_pk` FOREIGN KEY (`threadID`) REFERENCES `Thread` (`threadID`) ON UPDATE CASCADE,
  CONSTRAINT `Post_fk` FOREIGN KEY (`folderID`) REFERENCES `Folder` (`folderID`) ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE `Comment` (
  `threadID` int PRIMARY KEY,
  `parentID` int,
  CONSTRAINT `Comment_pk` FOREIGN KEY (`threadID`) REFERENCES `Thread` (`threadID`) ON UPDATE CASCADE,
  CONSTRAINT `Comment_fk` FOREIGN KEY (`parentID`) REFERENCES `Thread` (`threadID`) ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE `ViewedPosts` (
  `userID` int,
  `threadID` int,
  `times_viewed` int DEFAULT 0,
  PRIMARY KEY (`userID`, `threadID`),
  CONSTRAINT `ViewedPosts_fk1` FOREIGN KEY (`userID`) REFERENCES `User` (`userID`) ON UPDATE CASCADE,
  CONSTRAINT `ViewedPosts_fk2` FOREIGN KEY (`threadID`) REFERENCES `Post` (`threadID`) ON UPDATE CASCADE
);

CREATE TABLE `Tags` (
  `tagID` int PRIMARY KEY AUTO_INCREMENT,
  `label` varchar(255)
);

CREATE TABLE `PostTags` (
  `threadID` int,
  `tagID` int,
  PRIMARY KEY (`threadID`, `tagID`),
  CONSTRAINT `PostTags_fk1` FOREIGN KEY (`threadID`) REFERENCES `Post` (`threadID`) ON UPDATE CASCADE,
  CONSTRAINT `PostTags_fk2` FOREIGN KEY (`tagID`) REFERENCES `Tags` (`tagID`) ON UPDATE CASCADE
);

CREATE TABLE `likes` (
  `userID` int,
  `threadID` int,
  PRIMARY KEY (`userID`, `threadID`),
  CONSTRAINT `likes_fk1` FOREIGN KEY (`userID`) REFERENCES `User` (`userID`) ON UPDATE CASCADE,
  CONSTRAINT `likes_fk2` FOREIGN KEY (`threadID`) REFERENCES `Thread` (`threadID`) ON UPDATE CASCADE
);