[![Codacy Badge](https://api.codacy.com/project/badge/Grade/d237ff087c6848209394562a50c8a62e)](https://www.codacy.com/app/gurupadmamadapur/Build-it-Bigger?utm_source=github.com&utm_medium=referral&utm_content=Protino/Build-it-Bigger&utm_campaign=badger)

BUILD IT BIGGER
===============
* [About](#about)
* [Checklist](#checklist)
* [What did I learn](#what-did-i-learn)
* [Screenshots](#screenshots)
* [Build Configuration](#build-configuration)
* [License](#licnese)

### About
This android application is part of the [Udacity Android Developer Nanodegree]. This project was all about managing projects with multiple modules and libraries.
It also included handling of different flavors and build variants. All these problems are easily handled by gradle as it allows to automate complex tasks, hence making life easier.

I also dabbled a bit more into Google App Engine and testing.

```
Project Overview
 In this project, you will create an app with multiple flavors that uses multiple libraries and Google Cloud Endpoints. The finished app will consist of four modules:

  1. A Java library that provides jokes.
  2. A Google Cloud Endpoints (GCE) project that serves those jokes.
  3. An Android Library containing an activity for displaying jokes.
  4. An Android app that fetches jokes from the GCE module and passes them to the Android Library for display.

Why this Project?
 As Android projects grow in complexity, it becomes necessary to customize the behavior of the Gradle build tool, allowing automation of repetitive tasks. Particularly, factoring functionality into
 libraries and creating product flavors allow for much bigger projects with minimal added complexity.

What Will I Learn?
 You will learn the role of Gradle in building Android Apps and how to use Gradle to manage apps of increasing complexity. You'll learn to:

 • Add free and paid flavors to an app, and set up your build to share code between them.
 • Factor reusable functionality into a Java library.
 • Factor reusable Android functionality into an Android library.
 • Configure a multi-project build to compile your libraries and app.
 • Use the Gradle App Engine plugin to deploy a backend.
 • Configure an integration test suite that runs against the local google app engine server.
```


### Checklist

 - [x] Create a java library to fetch jokes.
 - [x] Create an android library to display jokes.
 - [x] Create a GCE module.
 - [x] Add functional tests.
 - [x] Add a paid flavor.
 - [x] Add interstitial ad.
 - [x] Add loading indicator.
 - [x] Add gradle task that runs all tests by launching GCE.
 - [ ] Add cron job to GCE to updates jokes.

### What did I learn?

* It's not magic. It's gradle.
* Learnt new programming language groovy!
* More familiar with cloud endpoints and google appengine framework.
* Connected android tests and a bit of espresso (not used in the project).

### Screenshots

![Cover](/assets/cover.png?raw=true)

### Build Configuration

* <b>Google appengine configuration</b>

 You can configure by either setting property `systemProp.appengine.sdk.root = <PATH_TO_SDK_V1.9.34>` or by manually adding dependency in `/api/build.gradle` and setting `downloadSk = true` in `appengine` configuration block.

* <b>Firebase configuration</b>

 Add `google-services.json` file in `/app` directory. Make sure that it contains four sub apps with relevant package names show in the table below.

 <div align=center>
 <br/>

 | Variant       | Package name<sp>*</sp>  |
 |:------------- |:-------------|
 | Release        | udacity.calgen.com.builditbigger  (Don't append .release !)
 | Free          | udacity.calgen.com.builditbigger.free
 | Paid          | udacity.calgen.com.builditbigger.paid
 | Free-Debug    | udacity.calgen.com.builditbigger.free.debug
 | Paid-Release  | udacity.calgen.com.builditbigger.paid.release
 <i>*Default package name can be different, but the suffixes should be appropriate.</i>
 </div>

* <b>Testing</b>

 All debug variants will have endpoint address mapping to localhost:8080. If you want to test them make sure you first launch local endpoint server by running task `api:appengineRun`. Then set property `API_ENDPOINT_ADDRESS` appropriately as shown below.

    ```groovy
        buildTypes {
            release {
                ....
                buildConfigField "String", "API_ENDPOINT_ADDRESS", '"https://sunshine-149409.appspot.com/_ah/api/"'
                ....
            }
            debug {
                ....
                //The following address only works for android emulator. Change it to ipv4 address for real devices.
                buildConfigField "String", "API_ENDPOINT_ADDRESS", '"http://10.0.2.2:8080/_ah/api/"'
                ....
            }
        }
    ```

    To run all connected tests run task `connectedTests` and make sure it is the debug variant you're testing. To test release variants set `testBuildType = "release"` in `android` configuration block in `app/build.gradle`

### License
    Copyright 2016 Gurupad Mamadapur

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


  [Udacity Android Developer Nanodegree]:https://www.udacity.com/degrees/android-developer-nanodegree-by-google--nd801
  [Releases]:https://github.com/Protino/Build-it-Bigger/releases
