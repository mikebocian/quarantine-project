### How to run?

Checkout the repo
```
git clone git@github.com:mikebocian/quarantine-project.git
cd quarantine-project
```



Start Kafka & Zookeper
```
docker-compose up -d
```

Initialize Kafka topics ( optional )
```
sh kafka-scripts.sh
```

Initialize Python venv
```
cd quarantine-project/python
python3 -m venv venv
source venv/bin/activate
```

Check audio interfaces
```
python sampler.py --topic audio listInterfaces
```

Choose the number of loopback interface for the default speaker. E.g. for `3: <Loopback Monitor of Dell AC511 USB SoundBar Digital Stereo (IEC958) (2 channels)>`
```
python sampler.py --topic audio --interfaceNo 3 stream
```

Import `flink` project to IntelliJ as a maven project, run StreamingJob class

Open new console, navigate to the `quarantine-project/python` 
```
source venv/bin/activate
python plotter.py
```
To kill it, press "CTRL + \"

## ENJOY!