# Usar una imagen base de Node.js
FROM node:18-alpine AS build

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar solo el package.json y package-lock.json para instalar dependencias
COPY package*.json ./

# Instalar las dependencias
RUN npm install --frozen-lockfile

# Copiar el resto de la aplicación
COPY . .

# Construir la aplicación Angular
RUN npm run build -- --configuration production

# Usar una imagen ligera para servir la aplicación
FROM nginx:alpine

# Copiar los archivos construidos a Nginx
COPY --from=build /app/dist/proyecto-microservicios/browser /usr/share/nginx/html
COPY ./nginx.conf /etc/nginx/conf.d/default.conf

# Exponer el puerto
EXPOSE 80

# Comando para ejecutar Nginx
CMD ["nginx", "-g", "daemon off;"]


