DROP TABLE semesters;
DROP TABLE courses;
DROP TABLE tasks;
DROP TABLE sections;
DROP TABLE lectures;
DROP TABLE notes;
DROP TABLE noteReferences;
DROP TABLE attachments;

CREATE TABLE semesters
	(semesterID	INTEGER PRIMARY KEY,
	 semesterNr	INTEGER);

CREATE TABLE courses
	(courseID	INTEGER PRIMARY KEY,
	name		VARCHAR(128) NOT NULL,
	semesterID	INTEGER REFERENCES semesters ON DELETE CASCADE);

CREATE TABLE tasks
	(taskID		INTEGER PRIMARY KEY,
	deadline	INTEGER,
	content		VARCHAR(128) NOT NULL,
	relatedTo	INTEGER REFERENCES courses ON DELETE CASCADE);

CREATE TABLE sections
	(sectionID	INTEGER PRIMARY KEY,
	name	VARCHAR(64),
	containedBy	INTEGER REFERENCES courses ON DELETE CASCADE);

CREATE TABLE lectures
	(lectureID	INTEGER PRIMARY KEY,
	lectureNr	INTEGER,
	roomNr		VARCHAR(32),
	time		INTEGER,
	lecturer	VARCHAR(64),
	containedBy	INTEGER REFERENCES sections ON DELETE CASCADE);

CREATE TABLE note
	(noteID		INTEGER PRIMARY KEY,
	title 		VARCHAR(64),
	content		BYTEA,
	lectureID	INTEGER REFERENCES lecture ON DELETE CASCADE);


CREATE TABLE noteReferences
	(referenceID	INTEGER PRIMARY KEY,
	row		INTEGER,
	noteID		INTEGER REFERENCES note);


CREATE TABLE attachments
	(attachmentID	INTEGER PRIMARY KEY,
	content		BYTEA,
	position	INTEGER,
	lectureID	INTEGER REFERENCES lecture);
