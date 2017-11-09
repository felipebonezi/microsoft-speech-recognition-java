# Microsoft @ Speech Recognition for Java API
With the cloud-based `Microsoft Speech Service`, you can develop applications using REST API to convert spoken audio to text.
To use speech recognition REST API, the steps are as follows:
1. Get a subscription key for using Speech API - [Subscribe here](https://azure.microsoft.com/try/cognitive-services/).
2. Configure it on `conf/microsoft-config.properties`.
3. Save your audio files at `/audios` folder (Check if this folder is configured as `resource folder` on your IDE).
4. Compile and Run!

The Speech REST API has some limitations:
* It only supports audio stream up to 15 seconds.
* REST API does not support intermediate results during recognition. Users receive only the final recognition result.

Use cases	REST | APIs
-------------- | -------------
Convert a short spoken audio, for example, commands (audio length < `15 s`) without interim results |	Yes
Convert a long audio (> `15 s`)	| No
Stream audio with interim results desired	| No
Understand the text converted from audio using LUIS	| No

# Collaborators
- Felipe Bonezi (felipebonezi@gmail.com)
- Lucas De Carli
