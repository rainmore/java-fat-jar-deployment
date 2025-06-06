#Java Fat Jar Deployment

Java Fat Jar Deployment is to allow the spring boot to be deployed as Linux service. 

Run the application

```
./gradlew run
```


## Deploy as a service

### Environments

Add the following environments in the `/etc/environment`
```
APP_LOGGING_PATH=/var/log/app
```

Then, load the environment variables by calling

```
source /etc/environment
```

### Update the jar

```
sudo mkdir -p /opt/java-fat-jar/config
cp java-fat-jar-deployment-${version}.jar /opt/java-fat-jar
cp java-fat-jar-environment.txt /opt/java-fat-jar/config/environment
cp java-fat-jar.service /opt/java-fat-jar/config/java-fat-jar.service
sudo chown root:root /opt/java-fat-jar/java-fat-jar-deployment-${version}.jar
sudo chmod 500 /opt/java-fat-jar/java-fat-jar-deployment-${version}.jar

```

### Setup Logrotate

```
sudo mkdir -p /var/log/java-fat-jar/
cp java-fat-jar.logrotate.txt /etc/logrotate.d/java-fat-jar
sudo logrotate /etc/logrotate.conf --debug
```

### Setup the service

```bash
ln -s /opt/java-fat-jar/config/java-fat-jar.service /etc/systemd/system/java-fat-jar.service

sudo systemctl enable java-fat-jar.service
# output will Created symlink /etc/systemd/system/multi-user.target.wants/java-fat-jar.service → /opt/java-fat-jar/java-fat-jar.service.

sudo systemctl daemon-reload

sudo systemctl status java-fat-jar.service
sudo systemctl start java-fat-jar.service
sudo systemctl restart java-fat-jar.service
sudo systemctl stop java-fat-jar.service
 
 
sudo journalctl -u java-fat-jar.service
```
