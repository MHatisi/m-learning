CREATE TABLE "userData" (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`key`	TEXT NOT NULL,
	`valueText`	TEXT,
	`valueInt`	INTEGER
);
INSERT INTO `userData` (id,key,valueText,valueInt) VALUES (1,'username',NULL,NULL);
INSERT INTO `userData` (id,key,valueText,valueInt) VALUES (2,'score',NULL,NULL);
INSERT INTO `userData` (id,key,valueText,valueInt) VALUES (3,'favorites',NULL,NULL);
CREATE TABLE `questions` (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`articleId`	INTEGER NOT NULL,
	`content`	TEXT NOT NULL,
	`points`	INTEGER NOT NULL
);
CREATE TABLE `categories` (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`name`	VARCHAR NOT NULL
);
INSERT INTO `categories` (id,name) VALUES (1,'Math');
INSERT INTO `categories` (id,name) VALUES (2,'Info');
CREATE TABLE `articles` (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`categoryId`	INTEGER NOT NULL,
	`title`	INTEGER NOT NULL,
	`content`	TEXT
);
CREATE TABLE `answers` (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`questionId`	INTEGER NOT NULL,
	`content`	TEXT,
	`isCorrect`	INTEGER NOT NULL
);
