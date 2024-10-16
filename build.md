```sh
cd frontend
npm run build
```

```sh
cp -r ./frontend/dist/ ./backend/src/main/resources/static
```

```sh
cd backend
mvn package
```

```sh
docker build -t mahmoudfawzykhalil/java-coffee-beans:latest .
```

```sh
docker login
```

```sh
docker push mahmoudfawzykhalil/java-coffee-beans:latest 
```