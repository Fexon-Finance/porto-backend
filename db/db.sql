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
    description            VARCHAR,
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
    account_id   UUID,
    symbol       VARCHAR,
    logo         VARCHAR,
    project_name VARCHAR
);

INSERT INTO porto.projects (id, name, logo, description, status, website_url, twitter_url, telegram_url, medium_url, token_contract_address) VALUES ('05a07ff7-6bf8-48fb-ad4e-e6c3d140389f', 'Toucan', 'https://app.toucan.earth/logos/toucan/icon-light.svg', 'Super desc', 'VERIFIED', 'https://toucan.earth', 'https://twitter.com/ToucanProtocol', '', 'https://medium.com/@ToucanProtocol', '0x2f800db0fdb5223b3c3f354886d907a671414a7f');
INSERT INTO porto.projects (id, name, logo, description, status, website_url, twitter_url, telegram_url, medium_url, token_contract_address) VALUES ('0580407e-52f2-4715-9667-13d93f6f1688', 'Vita DAO', 'https://www.vitadao.com/', 'Super desc', 'IN_PROGRESS', 'https://www.vitadao.com/', 'https://twitter.com/vlta_dao', '', 'https://vitadao.medium.com/', '0x81f8f0bb1cb2a06649e51913a151f0e7ef6fa321');

INSERT INTO porto.tokens (id, project_id, name, token_address, symbol, chain, logo) VALUES ('5823ce3a-36c6-471b-9be1-0b5b8136e502', '05a07ff7-6bf8-48fb-ad4e-e6c3d140389f', 'Base Carbon Tonne', '0x2f800db0fdb5223b3c3f354886d907a671414a7f', 'BCT', 'MATIC', 'https://assets.coingecko.com/coins/images/19240/small/bct_exchange_enhanced.png?1641441498');
INSERT INTO porto.tokens (id, project_id, name, token_address, symbol, chain, logo) VALUES ('e5bca8dd-89ed-4075-be83-334096a869c5', '0580407e-52f2-4715-9667-13d93f6f1688', 'VitaDAO', '0x81f8f0bb1cb2a06649e51913a151f0e7ef6fa321', 'VITA', 'ETH', 'https://assets.coingecko.com/coins/images/16580/sm…SfQTuWM3zCTghSHN7G6ohQaar7Ht6WANUp.png?1624506420');

-- Privileges
GRANT ALL PRIVILEGES ON SCHEMA porto TO spring;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA porto TO spring;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA porto To spring;