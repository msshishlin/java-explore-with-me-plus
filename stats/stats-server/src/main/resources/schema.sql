CREATE TABLE IF NOT EXISTS PUBLIC.HITS
(
  ID BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  APP VARCHAR(1000) NOT NULL,
  URI VARCHAR(1000) NOT NULL,
  IP VARCHAR(50) NOT NULL,
  TIMESTAMP TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  CONSTRAINT PK_HITS PRIMARY KEY (ID)
);
COMMENT ON TABLE PUBLIC.HITS IS 'Содержит информацию о запросах пользователей';
COMMENT ON COLUMN PUBLIC.HITS.ID IS 'Уникальный идентификатор';
COMMENT ON COLUMN PUBLIC.HITS.APP IS 'Идентификатор сервиса для которого записывается информация';
COMMENT ON COLUMN PUBLIC.HITS.URI IS 'URI для которого был осуществлен запрос';
COMMENT ON COLUMN PUBLIC.HITS.IP IS 'IP-адрес пользователя, осуществившего запрос';
COMMENT ON COLUMN PUBLIC.HITS.TIMESTAMP IS 'Дата и время, когда был совершен запрос к эндпоинту';