FROM grafana/grafana

USER root
RUN mkdir -p /var/lib/grafana /var/log/grafana
RUN chown -R grafana /var/lib/grafana /var/log/grafana
USER grafana

VOLUME ["/var/lib/grafana","/var/log/grafana"]

EXPOSE 3000
