# Google Cloud Platform TTS use API-KEY on Android.
## How to use it?

### Step 1: Download file 
```
git clone https://github.com/changemyminds/Google-Cloud-TTS-Android.git
```
### Step 2: Set up API Key
Go to [here](https://github.com/changemyminds/Google-Cloud-TTS-Android/blob/master/app/src/main/java/darren/gcptts/model/gcp/Config.java) and change "YOUR_API_KEY" to your Google Cloud API Key. 
```
static final String API_KEY = "YOUR_API_KEY";
```

__Note__ <br>
If you don't know the Google API Key, please see [Google document](https://cloud.google.com/docs/authentication/api-keys). <br>
Don't forget your API-Key Application restrictions and API restrictions must select _None_ and _Don't restrict key_.<br>
![image](https://github.com/changemyminds/GCP_TTS_ByAPIKEY/blob/master/images/0.png)<br>
 

### Step 3: Run app<br>
See the following achievement.<br>
![image](https://github.com/changemyminds/GCP_TTS_ByAPIKEY/blob/master/images/1.png)<br>
![image](https://github.com/changemyminds/GCP_TTS_ByAPIKEY/blob/master/images/2.png)<br>

## Test language and voice
If you want to test voice or find support language, you can go [here](https://cloud.google.com/text-to-speech/) to test online.

## Reference
[Google Cloud Java Issue](https://github.com/googleapis/google-cloud-java/issues/3400)<br>
[Google Cloud Text-to-speech](https://cloud.google.com/text-to-speech/docs/)<br>
[OkHttp](http://square.github.io/okhttp/)<br>
[Gson](https://github.com/google/gson)

