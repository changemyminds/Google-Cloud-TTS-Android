# GCP TTS use API-KEY on Android

## How to use it?
### Step
1. Download file 
```
git clone https://github.com/changemyminds/GCP_TTS_APIKEY_Android.git
```
2. Go to [here](https://github.com/changemyminds/GCP_TTS_ByAPIKEY/blob/master/app/src/main/java/darren/gcptts/tts/gcp/Config.java) and change "YOUR_API_KEY" to your "API_KEY". If you don't know the API_KEY, please see [Google document](https://cloud.google.com/docs/authentication/api-keys).
```
static final String API_KEY = "YOUR_API_KEY";
```
3. Run the app and you can use it.<br>
4. see the following achievement.<br>
![image](https://github.com/changemyminds/GCP_TTS_ByAPIKEY/blob/master/images/1.png)<br>
![image](https://github.com/changemyminds/GCP_TTS_ByAPIKEY/blob/master/images/2.png)<br>

## Test language and voice
If you want to test voice or find support language, you can go [here](https://cloud.google.com/text-to-speech/) to test online.

## Reference
[Google Cloud Text-to-speech](https://cloud.google.com/text-to-speech/docs/)<br>
[OkHttp](http://square.github.io/okhttp/)<br>
[Gson](https://github.com/google/gson)

