CREATE DATABASE porto;
CREATE USER spring WITH PASSWORD 'porto';
GRANT ALL PRIVILEGES ON DATABASE porto to spring;
\connect porto
CREATE SCHEMA porto;

CREATE TABLE porto.accounts
(
    id          UUID PRIMARY KEY,
    name        VARCHAR,
    surname     VARCHAR,
    email       VARCHAR,
    password    VARCHAR,
    private_key VARCHAR

);

-- Privileges
GRANT ALL PRIVILEGES ON SCHEMA porto TO spring;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA porto TO spring;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA porto To spring;