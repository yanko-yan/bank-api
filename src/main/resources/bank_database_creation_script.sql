CREATE TABLE users
(
  id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  username varchar(30) UNIQUE NOT NULL,
  email varchar(255) UNIQUE NOT NULL,
  password varchar(512) NOT NULL,
  first_name varchar(60) NOT NULL,
  last_name varchar(60) NOT NULL,
  birth_date date NOT NULL,
  registration_date date NOT NULL,
  enabled boolean NOT NULL DEFAULT false,
  verification_code varchar(8),
  verification_code_expiration timestamptz,
  role varchar(30) NOT NULL
);

CREATE SEQUENCE account_numbers
START WITH 1000000000 
MAXVALUE 9999999999 
INCREMENT 1;

CREATE TABLE accounts
(
  number bigint DEFAULT(nextval('account_numbers')) PRIMARY KEY,
  balance decimal(14, 2) NOT NULL DEFAULT 0.00 CHECK(balance >= 0),
  status varchar(20) NOT NULL CHECK(status IN ('ACTIVE', 'BLOCKED', 'CLOSED')),
  opening_date date NOT NULL,
  user_id bigint REFERENCES users(id) NOT NULL
);

ALTER SEQUENCE account_numbers 
OWNED BY accounts.number;

CREATE TABLE transactions
(
  id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  from_account bigint REFERENCES accounts(number),
  to_account bigint REFERENCES accounts(number),
  amount decimal(14, 2) NOT NULL CHECK (amount > 0),
  type varchar(20) NOT NULL CHECK(type IN ('TRANSFER', 'DEPOSIT','WITHDRAWAL')),
  transaction_timestamp timestamptz NOT NULL,

  CHECK (
  	(type = 'TRANSFER' AND from_account IS NOT NULL AND to_account IS NOT NULL)
    OR
    (type = 'DEPOSIT' AND to_account IS NOT NULL)
    OR
    (type = 'WITHDRAWAL' AND from_account IS NOT NULL)
  )
);