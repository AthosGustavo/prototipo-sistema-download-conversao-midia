FROM rabbitmq:3-management-alpine

RUN rabbitmq-plugins enable --offline \
    rabbitmq_management

USER root
RUN mkdir -p /var/lib/rabbitmq /etc/rabbitmq
RUN chown -R rabbitmq /var/lib/rabbitmq /etc/rabbitmq
USER rabbitmq

HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
CMD rabbitmq-diagnostics -q ping || exit 1

EXPOSE 5672 15672

VOLUME /var/lib/rabbitmq

CMD ["rabbitmq-server"]