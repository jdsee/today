DROP TABLE semesters;
DROP TABLE courses;
DROP TABLE tasks;
DROP TABLE sections;
DROP TABLE lectures;
DROP TABLE noteReferences;
DROP TABLE notes;
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
	deadline	VARCHAR(16),
	content		VARCHAR(128) NOT NULL,
	relatedTo	INTEGER REFERENCES courses ON DELETE CASCADE);

CREATE TABLE sections
	(sectionID	INTEGER PRIMARY KEY,
	name		VARCHAR(64),
	lecturer	VARCHAR(64),
	containedBy	INTEGER REFERENCES courses ON DELETE CASCADE);

CREATE TABLE lectures
	(lectureID	INTEGER PRIMARY KEY,
	roomNr		VARCHAR(32),
	startTime	VARCHAR(16),
	endTime		VARCHAR(16),
	lectureNr	INTEGER,
	containedBy	INTEGER REFERENCES sections ON DELETE CASCADE);

CREATE TABLE notes
	(noteID		INTEGER PRIMARY KEY,
	title 		VARCHAR(64),
	content		BYTEA,
	lectureID	INTEGER REFERENCES lectures ON DELETE CASCADE);


CREATE TABLE noteReferences
	(referenceID	INTEGER PRIMARY KEY,
	row		INTEGER,
	noteID		INTEGER REFERENCES notes);


CREATE TABLE attachments
	(attachmentID	INTEGER PRIMARY KEY,
	content		BYTEA,
	lectureID	INTEGER REFERENCES lectures);
