CREATE TABLE IF NOT EXISTS COLONISTS (
	ID               BIGSERIAL PRIMARY KEY CONSTRAINT no_null NOT NULL,
	PROFILE_NAME     TEXT                  CONSTRAINT no_null NOT NULL,
	INTERGALACTIC_ID TEXT                  CONSTRAINT no_null NOT NULL,
	PASSWORD_HASH    TEXT                  CONSTRAINT no_null NOT NULL
);

CREATE UNIQUE INDEX COLONIST_INTERGALACTIC_ID_IDX ON COLONISTS (INTERGALACTIC_ID);


CREATE TABLE IF NOT EXISTS ROLES (
	ID               BIGSERIAL PRIMARY KEY CONSTRAINT no_null NOT NULL,
	INTERGALACTIC_ID TEXT                  CONSTRAINT no_null NOT NULL REFERENCES COLONISTS (INTERGALACTIC_ID),
	ROLE             TEXT                  CONSTRAINT no_null NOT NULL
);

ALTER TABLE ROLES ADD CONSTRAINT ROLE_CHK CHECK (
	ROLE IN ('ADMIN', 'USER')
);


CREATE TABLE IF NOT EXISTS UNITS (
	ID                  BIGSERIAL PRIMARY KEY CONSTRAINT no_null NOT NULL,
	IMAGE               TEXT                  CONSTRAINT no_null NOT NULL,
	TITLE               TEXT                  CONSTRAINT no_null NOT NULL,
	REGION              TEXT                  CONSTRAINT no_null NOT NULL,
	DESCRIPTION         TEXT                  CONSTRAINT no_null NOT NULL,
	CANCELLATION_POLICY TEXT                  CONSTRAINT no_null NOT NULL,
	PRICE_AMOUNT        NUMERIC(12, 2)        CONSTRAINT no_null NOT NULL DEFAULT 0.00,
	PRICE_CCY           VARCHAR(3)            CONSTRAINT no_null NOT NULL DEFAULT 'IGD', -- Intergalactic dollars.
	SCORE               INTEGER               CONSTRAINT no_null NOT NULL DEFAULT 0
);

ALTER TABLE UNITS ADD CONSTRAINT PRICE_CCY_CHK CHECK (
	PRICE_CCY IN ('IGD')
);


ALTER TABLE UNITS ADD CONSTRAINT SCORE_RANGE_CHK CHECK (
		SCORE >= 0
	AND SCORE <= 5
);

CREATE INDEX UNIT_TITLE_IDX  ON UNITS (TITLE);
CREATE INDEX UNIT_REGION_IDX ON UNITS (REGION);


CREATE TABLE IF NOT EXISTS UNIT_VIEWERS (
	ID             BIGSERIAL PRIMARY KEY CONSTRAINT no_null NOT NULL,
	UNIT_ID        BIGINT                CONSTRAINT no_null NOT NULL,
	ACTIVE_VIEWERS BIGINT                CONSTRAINT no_null NOT NULL DEFAULT 0
);

CREATE UNIQUE INDEX UNIT_VIEWER_UNIT_ID_IDX ON UNIT_VIEWERS (UNIT_ID);
