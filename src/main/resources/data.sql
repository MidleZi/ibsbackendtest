insert into USERS (`NAME`)
VALUES ('Рога и Копыта');
insert into USERS (`NAME`)
VALUES ('Кошкин дом');

insert into DOCUMENT (`NAME`,`NUMBER`, `CREATOR_ID`)
VALUES ('Договор','РК/01', 1);
insert into DOCUMENT (`NAME`,`NUMBER`, `CREATOR_ID`)
VALUES ('Соглашение','РК/01-С1', 2);
insert into DOCUMENT (`NAME`,`NUMBER`, `CREATOR_ID`)
VALUES ('Спецификация','РК/01-01', 1);

insert into SIGNATURE (`USER_ID`,`DOCUMENT_ID`, `SIGNATURE`)
VALUES (1,1, TRUE);
insert into SIGNATURE (`USER_ID`,`DOCUMENT_ID`, `SIGNATURE`)
VALUES (2,1, TRUE);
insert into SIGNATURE (`USER_ID`,`DOCUMENT_ID`, `SIGNATURE`)
VALUES (1,2, TRUE);
insert into SIGNATURE (`USER_ID`,`DOCUMENT_ID`, `SIGNATURE`)
VALUES (2,2, FALSE);
insert into SIGNATURE (`USER_ID`,`DOCUMENT_ID`, `SIGNATURE`)
VALUES (1,3, FALSE);
insert into SIGNATURE (`USER_ID`,`DOCUMENT_ID`, `SIGNATURE`)
VALUES (2,3, FALSE);




