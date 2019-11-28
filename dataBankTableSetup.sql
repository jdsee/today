DROP TABLE semesters;
DROP TABLE courses;
DROP TABLE tasks;
DROP TABLE sections;
DROP TABLE lectures;
DROP TABLE notes;
DROP TABLE noteReferences;
DROP TABLE attachments;

CREATE TABLE semesters
        (semesterID	INTEGER PRIMARY KEY ON DELETE CASCADE,
         semesterNr	INTEGER);

CREATE TABLE courses
        (courseID	INTEGER PRIMARY KEY ON DELETE CASCADE,
         name		VARCHAR(128)
	 semesterID	INTEGER REFERENCES semesters);

CREATE TABLE tasks
       (taskID		INTEGER PRIMARY KEY ON DELETE CASCADE,
        deadline	INTEGER,
	content		VARCHAR(128),
	relatedTo	INTEGER REFERENCES courses);

CREATE TABLE sections
	(sectionID	INTEGER PRIMARY KEY ON DELETE CASCADE,
	 name		VARCHAR(64),
 	 containedBy	INTEGER REFERENCES courses);

CREATE TABLE lectures
	(lectureID	INTEGER PRIMARY KEY ON DELETE CASCADE,
	 lectureNr	INTEGER,
 	 roomNr		VARCHAR(32),
 	 time		INTEGER,
 	 lecturer	VARCHAR(64),
 	 containedBy	INTEGER REFERENCES sections);

CREATE TABLE note
  	(noteID      	INTEGER PRIMARY KEY ON DELETE CASCADE,
   	 title		VARCHAR(64),
   	 content    	BLOB,
   	 lectureID    	INTEGER REFERENCES lecture);


CREATE TABLE noteReferences
  	(referenceID    INTEGER PRIMARY KEY,
   	 row      	INTEGER,
   	 noteID       	INTEGER REFERENCES note);


CREATE TABLE attachments
  	(attachmentID	INTEGER PRIMARY KEY,
   	 content    	BLOB,
   	 position    	INTEGER,
   	 lectureID   	INTEGER REFERENCES lecture);
