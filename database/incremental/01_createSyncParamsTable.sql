CREATE TABLE "sync_params" (
  id     SERIAL PRIMARY KEY,
  bucket VARCHAR(50),
  rev    VARCHAR(50),
  date   TIMESTAMP
);

INSERT INTO sync_params (bucket, rev, date) VALUES ('r_animal', '1', NOW());
