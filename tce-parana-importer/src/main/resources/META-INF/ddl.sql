drop table bidding_participant;
drop table bidding_winner;
drop table bidding;
drop table donation;
drop table candidate;
drop table corporate;
drop table individual;
drop table entity;
drop table city;
drop table changelog;

CREATE TABLE city
(
  name character varying(255) NOT NULL,
  state character varying(255) NOT NULL,
  CONSTRAINT city_pkey PRIMARY KEY (name, state)
);

CREATE TABLE bidding
(
  bidid character varying(255) NOT NULL,
  classification character varying(255),
  codeibge character varying(255),
  descbiddingmodel character varying(255),
  descexecution character varying(255),
  description character varying(2000),
  entityname character varying(255),
  evaluationcriteria character varying(255),
  start timestamp without time zone,
  total double precision,
  year character varying(255),
  city character varying(255) NOT NULL,
  state character varying(255) NOT NULL,
  CONSTRAINT bidding_pkey PRIMARY KEY (bidid),
  CONSTRAINT fk7jx1538pp3w3jed31okv3p8b8 FOREIGN KEY (city, state)
      REFERENCES city (name, state) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE entity
(
  documentnumber character varying(255) NOT NULL,
  name character varying(255),
  CONSTRAINT entity_pkey PRIMARY KEY (documentnumber)
);

CREATE TABLE bidding_participant
(
  documentnumber character varying(255) NOT NULL,
  bidid character varying(255) NOT NULL,
  CONSTRAINT bidding_participant_pkey PRIMARY KEY (bidid, documentnumber),
  CONSTRAINT fk2qpwfle7qfu1w8gyew7jfv2fy FOREIGN KEY (documentnumber)
      REFERENCES entity (documentnumber) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk8wocab5s9904l5v5s3om7hn2b FOREIGN KEY (bidid)
      REFERENCES bidding (bidid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE bidding_winner
(
  itemnumber character varying(255) NOT NULL,
  lotnumber character varying(255) NOT NULL,
  amount double precision,
  descitem character varying(255),
  itemprice double precision,
  documentnumber character varying(255) NOT NULL,
  bidid character varying(255) NOT NULL,
  CONSTRAINT bidding_winner_pkey PRIMARY KEY (bidid, documentnumber, itemnumber, lotnumber),
  CONSTRAINT fknh1vdsfl9t6whx9lmeg0p96a8 FOREIGN KEY (bidid)
      REFERENCES bidding (bidid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkod08v2wpfwgar4s2wjy0mp0ra FOREIGN KEY (documentnumber)
      REFERENCES entity (documentnumber) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE individual
(
  gender character varying(255),
  documentnumber character varying(255) NOT NULL,
  CONSTRAINT individual_pkey PRIMARY KEY (documentnumber),
  CONSTRAINT fk171m6ao945w897hko363108b7 FOREIGN KEY (documentnumber)
      REFERENCES entity (documentnumber) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE corporate
(
  openingdate timestamp without time zone,
  status character varying(255),
  title character varying(255),
  documentnumber character varying(255) NOT NULL,
  CONSTRAINT corporate_pkey PRIMARY KEY (documentnumber),
  CONSTRAINT fk2gqgyu53lonpcctmkn434ixy5 FOREIGN KEY (documentnumber)
      REFERENCES entity (documentnumber) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


CREATE TABLE candidate
(
  year bigint NOT NULL,
  candidatecode character varying(255),
  candidateseq character varying(255),
  city character varying(255),
  creationdatetime timestamp without time zone,
  electioncode character varying(255),
  electiondesc character varying(255),
  party character varying(255),
  role character varying(255),
  state character varying(255),
  individualdocumentnumber character varying(255) NOT NULL,
  corporatedocumentnumber character varying(255),
  vicedocumentnumber character varying(255),
  CONSTRAINT candidate_pkey PRIMARY KEY (individualdocumentnumber, year),
  CONSTRAINT fkbh9uuqaityn7xsany2je3m19u FOREIGN KEY (vicedocumentnumber)
      REFERENCES individual (documentnumber) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkh0uwgclcyiofogj7hxsn1p8hn FOREIGN KEY (individualdocumentnumber)
      REFERENCES individual (documentnumber) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkp9t3p25su5xweaunhximab2ta FOREIGN KEY (corporatedocumentnumber)
      REFERENCES corporate (documentnumber) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE donation
(
  id bigint NOT NULL,
  cnae character varying(255),
  donationdate timestamp without time zone,
  incomedesc character varying(255),
  incomeformat character varying(255),
  incomesource character varying(255),
  incometype character varying(255),
  incomevalue character varying(255),
  party character varying(255),
  state character varying(255),
  transactionnumber character varying(255),
  candidatedocumentnumber character varying(255),
  year bigint,
  donatordocumentnumber character varying(255),
  CONSTRAINT donation_pkey PRIMARY KEY (id),
  CONSTRAINT fkhsn4d2odwhneujd5n4g188hwt FOREIGN KEY (candidatedocumentnumber, year)
      REFERENCES candidate (individualdocumentnumber, year) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkq7s5w0sp7skclwvtkwpl3rxbe FOREIGN KEY (donatordocumentnumber)
      REFERENCES entity (documentnumber) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE changelog
(
  id bigint NOT NULL,
  file character varying(255),
  processeddate timestamp without time zone,
  locked boolean,
  CONSTRAINT changelog_pkey PRIMARY KEY (id)
);