insert into semesters (semesterID, semesterNr) values (1, 1);
insert into semesters (semesterID, semesterNr) values (2, 2);
insert into semesters (semesterID, semesterNr) values (3, 3);

insert into courses (courseID, name, semesterID)
	values (11, 'English for Applied Computing', 1); 
insert into courses (courseID, name, semesterID)
	values (12, 'Theoretische Grundlagen der Informatik', 1);
insert into courses (courseID, name, semesterID)
	values (13, 'Programmieren 1', 1);
insert into courses (courseID, name, semesterID)
	values (14, 'Mathematik 1', 1);
insert into courses (courseID, name, semesterID)
	values (15, 'Gesellschaftliche Aspekte der Informatik', 1);
insert into courses (courseID, name, semesterID)
	values (16, 'Netzwerke', 1);

insert into tasks (taskID, deadline, content, relatedTo) 
	values (131, '20200420T173000Z', 'Abgabe Arraylisten', 13);
insert into tasks (taskID, deadline, content, relatedTo) 
	values (132, '20200427T173000Z', 'Abgabe Drei Gewinnt', 13);

insert into sections (sectionID, name, containedBy) values (1301, 'Übung', 13);
insert into sections (sectionID, name, containedBy) values (1302, 'Vorlesung', 13);

insert into lectures (lectureID, lectureNr, roomNr, startTime, endTime, containedBy) 
	values (130111, 1, 445, '20200325T133000Z', '20200325T153000Z', 1301);
insert into lectures (lectureID, lectureNr, roomNr, startTime, endTime, containedBy) 
	values (130112, 2, 445, '20200401T133000Z', '20200401T153000Z', 1301);
insert into lectures (lectureID, lectureNr, roomNr, startTime, endTime, containedBy) 
	values (130113, 3, 445, '20200408T133000Z', '20200408T153000Z', 1301);
insert into lectures (lectureID, lectureNr, roomNr, startTime, endTime, containedBy) 
	values (130114, 4, 445, '20200415T133000Z', '20200415T153000Z', 1301);

insert into lectures (lectureID, lectureNr, roomNr, startTime, endTime, containedBy) 
	values (130121, 1, 640, '20200326T133000Z', '20200326T153000Z', 1302);
insert into lectures (lectureID, lectureNr, roomNr, startTime, endTime, containedBy) 
	values (130122, 2, 640, '20200402T133000Z', '20200402T153000Z', 1302);
insert into lectures (lectureID, lectureNr, roomNr, startTime, endTime, containedBy) 
	values (130123, 3, 640, '20200409T133000Z', '20200409T153000Z', 1302);
insert into lectures (lectureID, lectureNr, roomNr, startTime, endTime, containedBy) 
	values (130124, 4, 640, '20200416T133000Z', '20200416T153000Z', 1302);

insert into notes (noteID, title, content, lectureID)
	values (1301211, "Einführungsveranstalltung", null, 130121);
insert into notes (noteID, title, content, lectureID)
	values (1301212, "For-Schleifen", null, 130122);
insert into notes (noteID, title, content, lectureID)
	values (1301213, "If-Statements, null, 130123);

insert into noteReferences (referenceID, row, noteID)
	values (01, 8, 1301211);
