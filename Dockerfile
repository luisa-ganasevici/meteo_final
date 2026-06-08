FROM ubuntu:latest
LABEL authors="lgana"

ENTRYPOINT ["top", "-b"]