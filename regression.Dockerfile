FROM node

COPY regression.json .

RUN npm i -g newman

RUN newman run regression.json