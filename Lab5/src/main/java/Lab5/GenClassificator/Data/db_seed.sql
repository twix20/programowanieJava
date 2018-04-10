BEGIN TRANSACTION;

INSERT INTO 'FLAGELLA' ('ALPHA', 'BETA', 'NUMBER') VALUES
  ('12', '43', '1'),
  ('33', '24', '3'),
  ('34', '52', '2'),
  ('32', '42', '2');

INSERT INTO 'TOUGHNESS' ('BETA', 'GAMMA', 'RANK') VALUES
  ('43', '23', 'd'),
  ('24', '43', 'b'),
  ('54', '12', 'b'),
  ('43', '43', 'a');

INSERT INTO 'EXAMINED' ('GENOTYPE', 'CLASS') VALUES
  ('328734', '1d'),
  ('653313', '3c'),
  ('239322', '1c'),
  ('853211', '2a');

COMMIT;