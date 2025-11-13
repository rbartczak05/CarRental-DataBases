FROM mongo:8.0.1

COPY keyFile /etc/mongo/keyFile
COPY mongod.conf /etc/mongod.conf

RUN chown 999:999 /etc/mongo/keyFile && \
    chmod 400 /etc/mongo/keyFile && \
    chown 999:999 /etc/mongod.conf