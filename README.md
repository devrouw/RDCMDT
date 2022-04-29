<div id="top"></div>

[![LinkedIn][linkedin-shield]][linkedin-url]



<!-- PROJECT LOGO -->
<br />
<div align="center">

  <h3 align="center">RDCMDT Home Assignment</h3>

  <p align="center">
    This project is built to complete the home assignment of RDCMDT Projects
    <br />
    <a href="https://github.com/devrouw/RDCMDT"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/devrouw/RDCMDT">View Demo</a>
    ·
    <a href="https://github.com/devrouw/RDCMDT/issues">Report Bug</a>
    ·
    <a href="https://github.com/devrouw/RDCMDT/issues">Request Feature</a>
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

This project is Android Native apps built using Kotlin which already implementing MVVM architecture.

The project contains several screen:
* Login Screen
* Register Screen
* Balance & Transactions Screen
* Transfer Screen

<p align="right">(<a href="#top">back to top</a>)</p>



### Built With

The project is built using the following framework:

* [Android Studio](https://developer.android.com/studio)
* [Kotlin](https://kotlinlang.org/)
* [Moshi](https://github.com/square/moshi)
* [Retrofit](https://square.github.io/retrofit/)
* [Hilt Dagger](https://dagger.dev/hilt/)
* [Mockito](https://site.mockito.org/)

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started

The project can be opened using Android Studio or can also downloaded as apk bundle to see the output of the project.


### Project Structure

_Below I list the project folder structure and its function._

1. `di` - to store all files related to Depedency Injection
2. `helper` - this folder is used to store all function that is being used globally by other classes.
3. `repository` - contains all repository files which function is to manage the business logic.
4. `service` - containing another 2 folders which is `model` and `network`. `model` contains files that represent the state of the app and `network` contains files to generate network calls.
5. `view` - contains all the files that output the UI/views.
6. `viewmodel` - contains all files that manage UI-related data.

<p align="right">(<a href="#top">back to top</a>)</p>


<!-- CONTACT -->
## Contact

Novilawati - [Linkedin](https://www.linkedin.com/in/novi-lawati-350710113/) - novilawati49@gmail.com

Project Link: [https://github.com/devrouw/RDCMDT](https://github.com/devrouw/RDCMDT)

<p align="right">(<a href="#top">back to top</a>)</p>


<!-- MARKDOWN LINKS & IMAGES -->
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/in/novi-lawati-350710113
[product-screenshot]: images/screenshot.png
