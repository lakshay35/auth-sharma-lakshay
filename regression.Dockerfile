FROM node

COPY regression.json .

RUN npm i -g newman
EXPOSE 80

RUN newman run regression.json