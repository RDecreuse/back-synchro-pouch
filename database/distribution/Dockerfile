FROM postgres:9.6

ENV POSTGRES_USER user
ENV POSTGRES_PASSWORD s3cr3t
ENV POSTGRES_DB db

COPY docker-entrypoint-initdb.d/ /docker-entrypoint-initdb.d
