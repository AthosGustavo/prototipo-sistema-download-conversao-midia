FROM jenkins/inbound-agent:latest-jdk17

USER root

# ===== Ferramentas =====
RUN apt-get update

RUN apt-get install -y --no-install-recommends \
    ca-certificates \
    curl \
    gnupg \
    lsb-release

RUN apt-get install -y --no-install-recommends \
    git \
    maven

RUN rm -rf /var/lib/apt/lists/*

# ===== Instalação do Docker =====
RUN install -m 0755 -d /etc/apt/keyrings
RUN curl -4 -fsSL https://download.docker.com/linux/debian/gpg | gpg --dearmor -o /etc/apt/keyrings/docker.gpg
RUN chmod a+r /etc/apt/keyrings/docker.gpg
RUN /bin/sh -c 'echo "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/debian $(. /etc/os-release && echo $VERSION_CODENAME) stable" > /etc/apt/sources.list.d/docker.list'

RUN apt-get update

RUN apt-get install -y --no-install-recommends \
    docker-ce-cli \
    docker-compose-plugin

RUN rm -rf /var/lib/apt/lists/*

# ===== Permissões para docker.sock =====
ARG DOCKER_GID=999

RUN /bin/sh -c 'if getent group docker >/dev/null; then groupdel docker; fi'
RUN groupadd -g "${DOCKER_GID}" docker
RUN usermod -aG docker jenkins

USER jenkins