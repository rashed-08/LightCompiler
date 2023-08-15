# Light Compiler [![Build Status](https://travis-ci.com/rashed-08/LightCompiler.svg?branch=master)](https://travis-ci.com/rashed-08/LightCompiler)
This is very basic compiler inspired by [Ideone](https://ideone.com/) \
Now supported language: **C/C++**, **Java**

# Installation:
**Step-1:** Install git \
**Step-2:** Install docker for your desktop environment \
**Step-3:** ``git clone https://github.com/rashed-08/LightCompiler.git`` \
**Step-4:** ``cd LightCompiler`` \
**Step-5:** **Go to `src/main/resources/application.properties` and change `prod` to `dev`** \
**Step-6:** ```docker build -t rashed08/light-compiler .``` \
**Step-7:** `docker run -p 8080:8080 rashed08/light-compiler` 

# Technologies:
  - **Java**
  - **Git**
  - **Docker**
  - **Travis CI**
  - **Heroku**
  - **Maven**

# How to run 
  - Goto **Postman**
  - Hit `http://localhost:8080/api/v1/submit` and request type **POST**
  - Write any program in supported language.
  - Convert json format. [Here](https://jsonformatter.org/json-stringify-online)
  - Should provide these field - `solutionSourceCode` `stdin` `language`
  - **Don't forget to add headers as json format**

If you found any issue, please report in **Issue** section.