CREATE TABLE "user" (
  "id" serial NOT NULL,
  "username" character(64) NOT NULL,
  "password" character(128) NOT NULL
);

CREATE TABLE "events" (
  "id" serial NOT NULL,
  "name" character(128) NOT NULL
);

CREATE TABLE "register" (
  "id" serial NOT NULL,
  "eid" integer NOT NULL,
  "uid" integer NOT NULL
);