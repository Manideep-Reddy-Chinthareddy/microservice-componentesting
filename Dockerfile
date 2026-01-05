FROM openjdk:17-jdk-slim
USER root
ENV APP_DIR /workspace
RUN mkdir -p /workspace



WORKDIR ${APP_DIR}
# Copy application and component test artifacts
COPY build/libs/*.jar ${APP_DIR}/
COPY src/componenttesting ${APP_DIR}/componenttesting
COPY componenttesting-entrypoint.sh ${APP_DIR}/componenttesting-entrypoint.sh
# Set the entrypoint script to trigger component testing
ENTRYPOINT ${APP_DIR}/componenttesting-entrypoint.sh