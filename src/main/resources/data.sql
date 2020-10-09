insert into USERS (`NAME`, `USERNAME`, `PASSWORD`, `ACTIVE`)
VALUES ('ADMIN', 'admin', '$2a$10$B042kZ1aAdyef3r5ITWHN.yRT3l6YwuuO2PpupxuP/2ylbZmIUUnK', 1);
insert into USERS (`NAME`, `USERNAME`, `PASSWORD`, `ACTIVE`)
VALUES ('Рога и Копыта', 'roga', '$2a$10$B042kZ1aAdyef3r5ITWHN.yRT3l6YwuuO2PpupxuP/2ylbZmIUUnK', 1);
insert into USERS (`NAME`, `USERNAME`, `PASSWORD`, `ACTIVE`)
VALUES ('Кошкин дом', 'cat', '$2a$10$B042kZ1aAdyef3r5ITWHN.yRT3l6YwuuO2PpupxuP/2ylbZmIUUnK', 1);

INSERT INTO ROLE (`ROLE`) VALUES ('ADMIN');
INSERT INTO ROLE (`ROLE`) VALUES ('USER');

INSERT INTO USER_ROLE (`USER_ID`, `ROLE_ID`) VALUES (1,2);
INSERT INTO USER_ROLE (`USER_ID`, `ROLE_ID`) VALUES (2,1);
INSERT INTO USER_ROLE (`USER_ID`, `ROLE_ID`) VALUES (3,1);

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




