FROM node:20-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install

COPY . .
RUN npm run build

FROM node:20-alpine
WORKDIR /app

COPY --from=build /app/dist ./dist
COPY --from=build /app/src/assets ./dist/loci-frontend/assets

# Copy the updated entrypoint
COPY docker/docker-entrypoint.sh /docker-entrypoint.sh
RUN chmod +x /docker-entrypoint.sh

RUN npm install -g http-server

EXPOSE 4200

ENTRYPOINT ["/docker-entrypoint.sh"]
CMD ["http-server", "-p", "4200", "-a", "0.0.0.0", "--proxy", "http://localhost:4200?"]
