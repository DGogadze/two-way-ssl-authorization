# Self Sign Certificate Creation Guide

##### Requirements: java keytool -> included in JDK

#### First, you need to create client & server side trust store (jks,p12 e.t.c)

##### keytool command:

> keytool -genkey -keyalg RSA -alias server -keystore server.jks -storepass server -keypass server -ext san=dns:localhost,ip:127.0.0.1 -validity 360 -keysize 2048

> keytool -genkey -keyalg RSA -alias client -keystore client.jks -storepass client -keypass client -ext san=dns:localhost,ip:127.0.0.1 -validity 360 -keysize 2048

#### After you create trust store, you need to create security certificate (crt) for both trust stores

##### keytool command:

> keytool -export -alias server -storepass server -file server.crt -keystore server.jks

> keytool -export -alias client -storepass client -file client.crt -keystore client.jks

#### certificates created, put it into project, create SSL Context & make request!
