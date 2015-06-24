CREATE TABLE "user" (
  "id" serial NOT NULL,
  "username" character(64) NOT NULL,
  "password" character(128) NOT NULL
);

CREATE TABLE "events" (
  "id" serial NOT NULL,
  "name" character(128) NOT NULL,
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