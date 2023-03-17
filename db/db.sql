CREATE DATABASE porto;
-- Remember, do not store stuff like this in repo :D
CREATE USER spring WITH PASSWORD 'porto';
GRANT ALL PRIVILEGES ON DATABASE porto to spring;
\connect porto
CREATE SCHEMA porto;

CREATE TABLE porto.accounts
(
    id             UUID PRIMARY KEY,
    vault_id       VARCHAR UNIQUE,
    iban           VARCHAR,
    public_key     VARCHAR,
    wallet_address VARCHAR
);

CREATE TABLE porto.sessions
(
    id         UUID PRIMARY KEY,
    account_id UUID,
    token      UUID,
    expiry     TIMESTAMPTZ
);

CREATE TABLE porto.projects
(
    id                     UUID PRIMARY KEY,
    name                   VARCHAR,
    logo                   VARCHAR,
    status                 VARCHAR,
    website_url            VARCHAR,
    twitter_url            VARCHAR,
    telegram_url           VARCHAR,
    medium_url             VARCHAR,
    token_contract_address VARCHAR
);

CREATE TABLE porto.tokens
(
    id            UUID PRIMARY KEY,
    project_id    UUID REFERENCES porto.projects (id),
    name          VARCHAR,
    token_address VARCHAR,
    symbol        VARCHAR,
    chain         VARCHAR
);

CREATE TABLE porto.transactions
(
    id           UUID PRIMARY KEY,
    price        DOUBLE PRECISION,
    date         TIMESTAMPTZ,
    token_amount DOUBLE PRECISION,
    token_id     UUID,
    account_id   UUID
);

-- Privileges
GRANT ALL PRIVILEGES ON SCHEMA porto TO spring;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA porto TO spring;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA porto To spring;