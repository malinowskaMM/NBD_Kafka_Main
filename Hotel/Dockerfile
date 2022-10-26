FROM mongo
COPY --chown=999 --chmod=600 keyFile /etc/mongo/keyFile
RUN chmod 400 /etc/mongo/keyFile
COPY --chown=999 --chmod=600 etc/mongod.conf /etc/mongod.conf