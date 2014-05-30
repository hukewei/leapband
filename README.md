How to import leapband project and play with leap motion
==================
you can go to the leapband blog to see the newest article :
http://leapband.fr/blog/2014/05/how-to-import-leapband-project-and-play-with-leap-motion/

* Preparation 

before getting started, you should prepare the following things:

Eclipse IDE

Leap Motion sensor

Leap Motion SDK V2.0, you can download from

 https://developer.leapmotion.com/

(beta version, need to sign in)

* Download leapband project from Github

You may download the latest beta version from github :

https://github.com/hukewei/leapband/releases 

after download finished, unzip it.

* import leapband project into Eclipse

Open your eclipse IDE, and choose import from existing project

* Change build path

**This step is for Windows user only, if you are Mac OS X user, you can go to step 4 directly.**

the project is pre-set for Mac OS X users, so, if you are a windows user, you must change the build path for the leapmotion dynamic file.

Firstly, open the buildpath of the leapband project, click the LeapJava.jar library and then double click on Native library location.

Second,select the right version of library file, if you are runing windows 32bit version, you should choose x86 directory, if you are runing windows with 64bit, you must choose x64.

Thirdly, make sure that the build path is correct, and click ok to confirm your choise:

* Run leap motion server and user application

Use the project explorer to find the fr.utc.leapband.main package, then, lauch ServerBoot.java to run the server service.

Then, lauch UserBoot to lauch a User service, the interface should appear now.

* Watch the demo

If you don't know how to play this game, you can watch a short demo from youtube: