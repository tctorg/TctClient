DROP TABLE "user";
DROP TABLE "comment";
DROP TABLE "events";
DROP TABLE "register";
DROP TABLE "topic";
DROP TABLE "avatar";

CREATE TABLE "user" (
  "id" serial NOT NULL,
  "username" character varying(64) NOT NULL,
  "password" character(128) NOT NULL,
  "aid" integer NOT NULL DEFAULT '0'
);

CREATE TABLE "events" (
  "id" serial NOT NULL,
  "name" character varying(128) NOT NULL,
  "topic" character varying(256) NOT NULL,
  "content" character varying(1024) NOT NULL,
  "seat_max" smallint NOT NULL DEFAULT '0'
);

CREATE TABLE "register" (
  "id" serial NOT NULL,
  "eid" integer NOT NULL,
  "uid" integer NOT NULL
);

CREATE TABLE "topic" (
  "id" serial NOT NULL,
  "uid" integer NOT NULL,
  "time" timestamp NOT NULL,
  "subject" character varying(512) NOT NULL,
  "content" character varying(5120) NOT NULL
);

CREATE TABLE "comment" (
  "id" serial NOT NULL,
  "tid" integer NOT NULL,
  "uid" integer NOT NULL,
  "time" timestamp NOT NULL,
  "comment" character varying(5120) NOT NULL
);

CREATE TABLE "avatar" (
  "id" serial NOT NULL,
  "uid" integer NOT NULL,
  "path" character varying(256) NOT NULL
);