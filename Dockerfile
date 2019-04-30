FROM eclipse/stack-base:ubuntu
EXPOSE 4403 8000 8080 9876 22

LABEL che:server:8080:ref=tomcat8 che:server:8080:protocol=http che:server:8000:ref=tomcat8-debug che:server:8000:protocol=http che:server:9876:ref=codeserver che:server:9876:protocol=http

ENV MAVEN_VERSION=3.3.9 \
    JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-amd64 \
    TOMCAT_HOME=/home/user/tomcat8 \
    TERM=xterm
ENV M2_HOME=/home/user/apache-maven-$MAVEN_VERSION
ENV PATH=$JAVA_HOME/bin:$M2_HOME/bin:$PATH

RUN mkdir /home/user/tomcat8 /home/user/apache-maven-$MAVEN_VERSION && \
    wget -qO- "http://apache.ip-connect.vn.ua/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz" | tar -zx --strip-components=1 -C /home/user/apache-maven-$MAVEN_VERSION/ && \
    wget -qO- "http://archive.apache.org/dist/tomcat/tomcat-8/v8.0.24/bin/apache-tomcat-8.0.24.tar.gz" | tar -zx --strip-components=1 -C /home/user/tomcat8 && \
    rm -rf /home/user/tomcat8/webapps/* && \
    sudo mkdir -p /home/user/.m2 && \
    sudo mkdir -p /home/user/jdtls/data && \
    sudo chown -R 0 /home/user && \
    sudo chmod -R g+w /home/user

# Google Chrome

RUN wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | sudo apt-key add - \
    && echo "deb http://dl.google.com/linux/chrome/deb/ stable main" | sudo tee /etc/apt/sources.list.d/google-chrome.list > /dev/null \
	&& sudo apt-get update -qqy \
	&& sudo apt-get -qqy install google-chrome-stable \
	&& sudo rm /etc/apt/sources.list.d/google-chrome.list \
	&& sudo rm -rf /var/lib/apt/lists/* /var/cache/apt/* \
	&& sudo sed -i 's/"$HERE\/chrome"/"$HERE\/chrome" --no-sandbox/g' /opt/google/chrome/google-chrome

# ChromeDriver

ARG CHROME_DRIVER_VERSION=73.0.3683.68
RUN sudo wget --no-verbose -O /tmp/chromedriver_linux64.zip https://chromedriver.storage.googleapis.com/$CHROME_DRIVER_VERSION/chromedriver_linux64.zip \
	&& sudo rm -rf /opt/chromedriver \
	&& sudo unzip /tmp/chromedriver_linux64.zip -d /opt \
	&& sudo rm /tmp/chromedriver_linux64.zip \
	&& sudo mv /opt/chromedriver /opt/chromedriver-$CHROME_DRIVER_VERSION \
	&& sudo chmod 755 /opt/chromedriver-$CHROME_DRIVER_VERSION \
	&& sudo ln -fs /opt/chromedriver-$CHROME_DRIVER_VERSION /usr/bin/chromedriver
