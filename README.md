
Place Search Dialog
==========
A place autocomplete search dialog which uses Google's places API for finding results.

![](preview/preview.png)

##Usage
```java
PlaceSearchDialog placeSearchDialog = new PlaceSearchDialog(this, new PlaceSearchDialog.LocationNameListener() {
            @Override
            public void locationName(String locationName) {
                //set textview or edittext
            }
        });
placeSearchDialog.show();
```

Add this in your applications AndroidManifest.xml
```xml
<meta-data android:name="com.google.android.geo.API_KEY"
            android:value="YOUR_API_KEY" />
```

##Adding to your project
Library is available in maven central.

###Gradle
Just use it as a dependency in your build.gradle file

```groovy
compile 'com.codemybrainsout.placesearchdialog:placesearch:1.0.0'
```

###Maven
Ensure you have android-maven-plugin version that support aar archives and add following dependency:

```xml
<dependency>
  <groupId>com.codemybrainsout.placesearchdialog</groupId>
  <artifactId>placesearch</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>
```

#License
```
Copyright (C) 2016 Code My Brains Out

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
