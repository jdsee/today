insert into semesters (semesterID, semesterNr) values (1, 1);
insert into semesters (semesterID, semesterNr) values (2, 2);
insert into semesters (semesterID, semesterNr) values (3, 3);

insert into courses (courseID, name, relatedTo)
values (11, 'English for Applied Computing', 1);
insert into courses (courseID, name, relatedTo)
values (12, 'Theoretische Grundlagen der Informatik', 1);
insert into courses (courseID, name, relatedTo)
values (13, 'Programmieren 1', 1);
insert into courses (courseID, name, relatedTo)
values (14, 'Mathematik 1', 1);
insert into courses (courseID, name, relatedTo)
values (15, 'Gesellschaftliche Aspekte der Informatik', 1);
insert into courses (courseID, name, relatedTo)
values (16, 'Netzwerke', 1);

insert into tasks (taskID, deadline, content, relatedTo)
values (131, '20200420T173000Z', 'Abgabe Arraylisten', 13);
insert into tasks (taskID, deadline, content, relatedTo)
values (132, '20200427T173000Z', 'Abgabe Drei Gewinnt', 13);

insert into sections (sectionID, name, lecturer, relatedTo)
values (1301, 'Übung', 'Prof Schwotzer', 13);
insert into sections (sectionID, name, lecturer, relatedTo)
values (1302, 'Vorlesung', 'Prof Schwotzer', 13);

insert into lectures (lectureID, lectureNr, roomNr, startTime, endTime, relatedTo)
values (130111, 1, 445, '20200325T133000Z', '20200325T153000Z', 1301);
insert into lectures (lectureID, lectureNr, roomNr, startTime, endTime, relatedTo)
values (130112, 2, 445, '20200401T133000Z', '20200401T153000Z', 1301);
insert into lectures (lectureID, lectureNr, roomNr, startTime, endTime, relatedTo)
values (130113, 3, 445, '20200408T133000Z', '20200408T153000Z', 1301);
insert into lectures (lectureID, lectureNr, roomNr, startTime, endTime, relatedTo)
values (130114, 4, 445, '20200415T133000Z', '20200415T153000Z', 1301);

insert into lectures (lectureID, lectureNr, roomNr, startTime, endTime, relatedTo)
values (130121, 1, 640, '20200326T133000Z', '20200326T153000Z', 1302);
insert into lectures (lectureID, lectureNr, roomNr, startTime, endTime, relatedTo)
values (130122, 2, 640, '20200402T133000Z', '20200402T153000Z', 1302);
insert into lectures (lectureID, lectureNr, roomNr, startTime, endTime, relatedTo)
values (130123, 3, 640, '20200409T133000Z', '20200409T153000Z', 1302);
insert into lectures (lectureID, lectureNr, roomNr, startTime, endTime, relatedTo)
values (130124, 4, 640, '20200416T133000Z', '20200416T153000Z', 1302);

insert into notes (noteID, title, content, relatedTo)
values (1301211, 'Einführungsveranstalltung', 'heute hatte ich einen ersten schoenen Tag', 130121);
insert into notes (noteID, title, content, relatedTo)
values (1301212, 'For-Schleifen', 'For-schleifen sind so aufgebaut: for (int i=0; i<10; i++){}', 130122);
insert into notes (noteID, title, content, relatedTo)
values (1301213, 'If-Statements', 'heute brauch ich nichts auf zu schreiben', 130123);

insert into noteReferences (referenceID, row, noteID)
values (01, 8, 1301211);
