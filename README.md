
# SOS Mobile Application for Accident Prediction and Rescue System

## Overview
In this era of crime, personal safety problem is getting worst and worst. There are many cases happened such as robbery, burglary, snatching, rapping and etc. These cases will seriously affect social stability and could reduce people step out their door. Furthermore, everyone is worrying about their personal safety and also their love one, especially their family and beloved partner. For some of the people, they tend to worry other people safety more than themselves. According to National Crime Records Bureau (NCRB) data released recently, more than 1.55 lakh people were killed only in road crashes across India in 2021 — an average of 426 daily or 18 every hour. The most likely reason for an individual’s death in an accident is lack of the first aid provision as Emergency response time is extremely vital when it involves incidents involving vehicle accidents. Accidents are identified by utilizing three sensors for example accelerometer, force resistive sensor and gyroscope to get precise outcomes. More often, we can't find the accurate location of the accident when we don't have the exact idea where the accident has occurred. The purpose of this project is to design and implement automated system that uses smartphone to detect the accidents and sends emergency message along with GPS location on Google Map. In this project we are utilizing android smartphone to detect the accidents and report it to the emergency contacts added by the user.

## System Specification

### Hardware Requirements:
#### For development:
• **Ram-** minimum 128 MB to 1GB

• **Space on disk:** 250 (at the least)

• **Processor-** Intel i3 or higher
#### For running on a device:
• **Operating System:** Android 3.0 or higher

• **Disk space:** 6 MB (at the least)

### Software Requirements:
#### For development:
• **Operating system-** Windows 7 or higher/Mac OS X 15.8 or later/Linux

• **Technologies used-** Java, SQLite, Android Studio, Google maps v2 API

• **Platform-** Android SDK Framework 10 or higher
#### For running on a device:
• **Operating System:** Phone or tablet running Android 3.0 or higher

• Cellular capabilities for SMS messages
## Algorithms

1. if the parameter > threshold value of the parameter, then

2. if θ > threshold value of θ
(Among 100 samples after satisfying the condition in Line 1), then

3. return fall detection
(Alarm is set & emergency alert is sent)

4. return no fall detection
### ❖ It uses Fall-Detection Algorithm Using 3-Axis Acceleration Combination of Simple Threshold for Fall Detection.
Where,

**i:-** sample number

**𝐴_(𝑥^((𝑖)) ):-** x-axial

**𝐴_(𝑦^((𝑖)) ) :−** y-axial

**𝐴_(𝑧^((𝑖)) ):-** z-axial accelerations of 𝑖^𝑡ℎ sample

**𝜃 :-** Euler angle between accelerometer

x-axis and the vertical direction.

### Steps

➢ Measure 3-axial acceleration from the accelerometer.

➢ Extract data in learning and evaluation range.

➢ Calculate fall-feature parameters (x-axis, y-axis, z-axis)

➢ Apply the parameters to the simple threshold method

➢ Determine finally falls or ADLs from the likelihood

➢ Extract the likelihood of falls

➢ Apply the parameters of the possible falls to HMM (Hidden Markov Model).

➢ Determine possible falls from the simple threshold method.
## Screenshots

![App Screenshot](https://via.placeholder.com/468x300?text=App+Screenshot+Here)


## Authors

- Nibid Raj Shrestha
- Jayanth S
- M Roopa Shree
- Narayana Swamy K