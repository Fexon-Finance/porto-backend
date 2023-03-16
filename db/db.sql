CREATE DATABASE porto;
-- Remember, do not store stuff like this in repo :D
CREATE USER spring WITH PASSWORD 'porto';
GRANT ALL PRIVILEGES ON DATABASE porto to spring;
\connect porto
CREATE SCHEMA porto;

CREATE TABLE porto.accounts
(
    id             UUID PRIMARY KEY,
    email          VARCHAR,
    password       VARCHAR,
    name           VARCHAR,
    surname        VARCHAR,
    birthday       TIMESTAMPTZ,
    iban           VARCHAR,
    public_key     VARCHAR,
    wallet_address VARCHAR
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

-- Privileges
GRANT ALL PRIVILEGES ON SCHEMA porto TO spring;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA porto TO spring;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA porto To spring;