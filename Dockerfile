#FROM eclipse/stack-base:ubuntu
FROM eclipse/selenium

# Google Chrome

RUN wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | sudo apt-key add - \
    && echo "deb http://dl.google.com/linux/chrome/deb/ stable main" | sudo tee /etc/apt/sources.list.d/google-chrome.list > /dev/null \
	&& sudo apt-get update -qqy \
	&& sudo apt-get -qqy install google-chrome-stable \
	&& sudo rm /etc/apt/sources.list.d/google-chrome.list \
	&& sudo rm -rf /var/lib/apt/lists/* /var/cache/apt/* \
	&& sudo sed -i 's/"$HERE\/chrome"/"$HERE\/chrome" --no-sandbox/g' /opt/google/chrome/google-chrome
