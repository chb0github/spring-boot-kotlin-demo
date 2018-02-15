-- SET REFERENTIAL_INTEGRITY FALSE;
--
-- DROP TABLE IF EXISTS authorities;
-- DROP TABLE IF EXISTS Roles;
-- DROP TABLE IF EXISTS users;
-- DROP TABLE IF EXISTS group_authorities;
-- DROP TABLE IF EXISTS group_members;
-- DROP TABLE IF EXISTS groups;
-- DROP TABLE IF EXISTS persistent_logins;
-- DROP TABLE IF EXISTS acl_object_identity;
-- DROP TABLE IF EXISTS acl_sid;
-- DROP TABLE IF EXISTS acl_class;
-- DROP TABLE IF EXISTS acl_entry;

CREATE SEQUENCE HIBERNATE_SEQUENCE START WITH 100 INCREMENT BY 1;

CREATE TABLE users (
  id       INT PRIMARY KEY NOT NULL IDENTITY,
  username VARCHAR(50) NOT NULL ,
  password VARCHAR(60) NOT NULL,
  enabled  BOOLEAN     NOT NULL,
  UNIQUE (username)
);
CREATE TABLE Roles (
  name VARCHAR(50) NOT NULL PRIMARY KEY
);

CREATE TABLE authorities (
  id       INT PRIMARY KEY NOT NULL IDENTITY,
  username VARCHAR(50)     NOT NULL,
  role     VARCHAR(50)     NOT NULL,
  FOREIGN KEY (username) REFERENCES users (username),
  FOREIGN KEY (role) REFERENCES Roles (name),
  UNIQUE (username, role)
);

CREATE TABLE groups (
  id   BIGINT PRIMARY KEY NOT NULL IDENTITY,
  name VARCHAR(50)        NOT NULL
);

CREATE TABLE group_authorities (
  group_id  BIGINT      NOT NULL,
  authority VARCHAR(50) NOT NULL,
  FOREIGN KEY (group_id) REFERENCES groups (id)
);

CREATE TABLE group_members (
  id       BIGINT PRIMARY KEY IDENTITY,
  username VARCHAR(50) NOT NULL,
  group_id BIGINT      NOT NULL,
  FOREIGN KEY (group_id) REFERENCES groups (id)
);

CREATE TABLE persistent_logins (
  username  VARCHAR(64) NOT NULL,
  series    VARCHAR(64) PRIMARY KEY,
  token     VARCHAR(64) NOT NULL,
  last_used TIMESTAMP   NOT NULL
);

CREATE TABLE acl_sid (
  id        BIGINT       NOT NULL PRIMARY KEY IDENTITY,
  principal BOOLEAN      NOT NULL,
  sid       VARCHAR(100) NOT NULL,
  UNIQUE (sid, principal)
);

CREATE TABLE acl_class (
  id    BIGINT       NOT NULL PRIMARY KEY IDENTITY,
  class VARCHAR(100) NOT NULL,
  UNIQUE (class)
);

CREATE TABLE acl_object_identity (
  id                 BIGINT PRIMARY KEY NOT NULL IDENTITY,
  object_id_class    BIGINT             NOT NULL,
  object_id_identity BIGINT             NOT NULL,
  parent_object      BIGINT,
  owner_sid          BIGINT             NOT NULL,
  entries_inheriting BOOLEAN            NOT NULL,
  UNIQUE (object_id_class, object_id_identity),
  FOREIGN KEY (parent_object) REFERENCES acl_object_identity (id),
  FOREIGN KEY (object_id_class) REFERENCES acl_class (id),
  FOREIGN KEY (owner_sid) REFERENCES acl_sid (id)
);

CREATE TABLE acl_entry (
  id                  BIGINT PRIMARY KEY NOT NULL IDENTITY,

  acl_object_identity BIGINT             NOT NULL,
  ace_order           INT                NOT NULL,
  sid                 BIGINT             NOT NULL,
  mask                INTEGER            NOT NULL,
  granting            BOOLEAN            NOT NULL,
  audit_success       BOOLEAN            NOT NULL,
  audit_failure       BOOLEAN            NOT NULL,
  UNIQUE (acl_object_identity, ace_order),
  FOREIGN KEY (acl_object_identity)
  REFERENCES acl_object_identity (id),
  FOREIGN KEY (sid) REFERENCES acl_sid (id)
);
-- SET REFERENTIAL_INTEGRITY TRUE ;