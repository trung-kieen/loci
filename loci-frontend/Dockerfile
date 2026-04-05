FROM node:20-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

FROM nginx:alpine

COPY nginx/default.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/dist/loci-frontend/browser /usr/share/nginx/html

# provide system env runtime file
COPY --from=build /app/src/assets /usr/share/nginx/html/assets


COPY docker/docker-entrypoint.sh /docker-entrypoint.sh

# inject variable
RUN chmod +x /docker-entrypoint.sh


EXPOSE 80
ENTRYPOINT ["/docker-entrypoint.sh"]
CMD ["nginx", "-g", "daemon off;"]
