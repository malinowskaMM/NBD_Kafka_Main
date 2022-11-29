FROM mongo:latest
COPY --chown=999 --chmod=603 keyFile /etc/mongo/keyFile
RUN chmod 400 /etc/mongo/keyFile
COPY --chown=999 --chmod=603 mongod.conf /etc/mongod.conf

FROM redislabs/redisearch:latest as redisearch
FROM redislabs/rejson:latest as rejson

ENV LD_LIBRARY_PATH /usr/lib/redis/modules

COPY --from=redisearch ${LD_LIBRARY_PATH}/redisearch.co ${LD_LIBRARY_PATH}/
COPY --from-rejson ${LD_LIBRARY_PATH}/*.so ${LD_LIBRARY_PATH}/