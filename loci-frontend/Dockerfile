FROM node:20-alpine AS build
WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .
RUN npm run build

FROM node:20-alpine
WORKDIR /app

RUN npm install -g http-server

COPY --from=build /app/dist/loci-frontend/browser .


EXPOSE 4200

CMD ["http-server", "-p", "4200", "-a", "0.0.0.0", "--proxy", "http://localhost:4200?"]
