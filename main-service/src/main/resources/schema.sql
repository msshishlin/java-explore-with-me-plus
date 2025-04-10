CREATE TABLE IF NOT EXISTS PUBLIC.USERS
(
  ID BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  NAME VARCHAR(250) NOT NULL,
  EMAIL VARCHAR(254) UNIQUE NOT NULL
);
COMMENT ON TABLE PUBLIC.USERS IS 'Содержит информацию о пользователях';
COMMENT ON COLUMN PUBLIC.USERS.ID IS 'Уникальный идентификатор';
COMMENT ON COLUMN PUBLIC.USERS.NAME IS 'Имя пользователя';
COMMENT ON COLUMN PUBLIC.USERS.EMAIL IS 'Почта пользователя';

CREATE TABLE IF NOT EXISTS PUBLIC.CATEGORIES
(
  ID BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  NAME VARCHAR(50) NOT NULL,
  CONSTRAINT PK_CATEGORIES PRIMARY KEY (ID),
  CONSTRAINT UQ_CATEGORIES_NAME UNIQUE (NAME)
);
COMMENT ON TABLE PUBLIC.CATEGORIES IS 'Содержит информацию о категориях';
COMMENT ON COLUMN PUBLIC.CATEGORIES.ID IS 'Уникальный идентификатор';
COMMENT ON COLUMN PUBLIC.CATEGORIES.NAME IS 'Наименование категории';