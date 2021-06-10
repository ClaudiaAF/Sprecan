<!--

***
***
*** To avoid retyping too much info. Do a search and replace for the following:
*** github_username, repo_name, twitter_handle, email, project_title, project_description
-->



<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->
![GitHub language count](https://img.shields.io/github/languages/count/ClaudiaAF/Sprecan?colorB=d0ac4b)
![GitHub repo size](https://img.shields.io/github/repo-size/ClaudiaAF/Sprecan?colorB=d0ac4b)
![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/ClaudiaAF/Sprecan?colorB=d0ac4b)
![GitHub watchers](https://img.shields.io/github/watchers/ClaudiaAF/Sprecan?colorB=d0ac4b)



<!-- PROJECT LOGO -->
<br />
<p align="center">
  <a href="https://github.com/github_username/repo_name">
    <img src="https://user-images.githubusercontent.com/64257497/118951076-11661f80-b95b-11eb-9244-a633ee8dcdd7.png "width="195" alt="logo" >
  </a>

  <h3 align="center">Sprecan</h3>

  <p align="center">
    Android Chat Application
    <br />
    <a href="https://github.com/claudiaAF/Sprecan"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/github_username/repo_name">View Demo</a>
    ·
    <a href="https://github.com/github_username/repo_name/issues">Report Bug</a>
    ·
    <a href="https://github.com/github_username/repo_name/issues">Request Feature</a>
  </p>
</p>



<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary><h2 style="display: inline-block">Table of Contents</h2></summary>
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
    <li><a href="#features-and-functions">Features and Functions</a></li>
    <li>
      <a href="#concept-process">Concept Process</a>
      <ul>
        <li><a href="#ideation">Ideation</a></li>
        <li><a href="#wireframes">Wireframes</a></li>
        <li><a href="#user-flow">User-Flow Diagrams</a></li>
        <li><a href="#database-erd">Database ERD</a></li>
      </ul>
    </li>
    <li>
      <a href="#development-process">Development Process</a>
      <ul>
        <li><a href="#implementation">Implementation</a></li>
        <li><a href="#peer-reviews">Peer Reviews</a></li>
      </ul>
    </li>
    <li><a href="#video-demonstration">Video Demonstration</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgements">Acknowledgements</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project


![mockup header](https://user-images.githubusercontent.com/64257497/121544302-e6c33000-ca09-11eb-8bce-00280f77ae9e.png)


Communication has been around since the dawn of time, it constantly altered in linguisticss, dialects, and methods as the years went on. During these earlier days, communication between two people was valued very much. 
</br>
</br>
Sending messages to someone of a far distance took much more effort and time, comparing this situation to current day is almost unfathomable.
My idea thus came about of taking the value that communication used to hold and implementing that in a modern means of communication. 
</br>
</br>
And so, Sprecan was born. Sprecan is a medieval-inspired, closed-group chat application. This app aims to bring the ancient value of communication into a modern technology.

### Built With

* [Android Studio](https://developer.android.com/studio)
* [Kotlin](https://kotlinlang.org/)
* [Firebase Firestore](https://firebase.google.com/)


<!-- GETTING STARTED -->
## Getting Started

To get a local copy up and running follow these simple steps.

### Prerequisites

Having the latest version of Android Studio installed on your PC will be required. Inclusion of the Kotlin plugin will also be needed.


### Installation

1. Open Android Studio
   Then navigate to `File` > `New` > `From Version Control` > `Git`
  
   
2. Copy and paste my repo link `https://github.com/ClaudiaAF/Sprecan.git` into the URL field and continue to click the `Clone` option.



<!-- USAGE EXAMPLES -->
## Features and Functions

### Features
* Users can securely login or create their own account
* Validation is implemented to assist the user
* The app remembers the users logged in session after closing and re-entering the app
* The user can edit their profile image and name as they wish
* Push notifications to display confirmation for when the user has saved their newly updated profile, and for when the user logs out
* Users can chat with their companions
* Companions list can be viewed

### Functions
* `Recycler view` and `Groupie` was used for the list of companions, active chats and in the chat view
* `Snackbar` was used for validation
*  `Google authentication` was utilised for the sign in and sign up
*  `Firebase firestore` was used for the backing `NOSQL database`
*  `Constants` object for user details
*  `Firebase Storage` was made use of for uploading profile images
*  `Glide` was used to handle the image uploading
*  `PushNotifications` were enabled

## Concept Process

### Ideation

![Group 135](https://user-images.githubusercontent.com/64257497/121551128-a9fa3780-ca0f-11eb-9c77-c5f622158240.png)
</br>
![Group 134](https://user-images.githubusercontent.com/64257497/121551193-b8e0ea00-ca0f-11eb-9154-25d2a3865b55.png)
</br>
### Wireframes

<img width="1920" alt="Group 137" src="https://user-images.githubusercontent.com/64257497/121552912-1f1a3c80-ca11-11eb-9383-9ae1bbf97203.png">

### User-Flow
<img width="1928" alt="Group 139" src="https://user-images.githubusercontent.com/64257497/121553262-6b657c80-ca11-11eb-8d71-86ff99274e63.png">

### Database ERD
<img width="1920" alt="Group 142" src="https://user-images.githubusercontent.com/64257497/121553834-ef1f6900-ca11-11eb-9ada-5bb9bf282276.png">

## Development Process

### Implementation
#### Challenges

* Learning about Firebase and Firestore
* Implementing Firebase with Kotlin
* Understanding how to structure the database for the chat function
* Implementing recycler views
* Handling the image uploads for the profile image
* All the chat logic and implementation

### Peer Reviews

#### Feedback
* Everyone enjoyed the concept and theme
* The colour scheme was complimented
* Some text seemed illegible against the dark blue background
* Accessing the profile was not clear
* Creating a new chat was also not too obvious

#### Future Changes
* Add a group chat option
* Show the latest message underneath the contacts name in the chats list
* Enable push notifications for new messages instead

## Mockups
![Frame 3](https://user-images.githubusercontent.com/64257497/121555704-85a05a00-ca13-11eb-829f-91f066bee90d.png)
![Frame 2](https://user-images.githubusercontent.com/64257497/121555727-8afda480-ca13-11eb-9854-88d6fb39b98c.png)
![Frame 4](https://user-images.githubusercontent.com/64257497/121555734-8cc76800-ca13-11eb-8903-4081b4cfed44.png)

## Video Demonstration
[Click Here](https://drive.google.com/file/d/1-ievpGxwskUznCHiUxSafsSu5_XjOndl/view?usp=sharing) to view the video demonstration of Sprecan

<!-- ROADMAP -->
## Roadmap

See the [open issues](https://github.com/github_username/repo_name/issues) for a list of proposed features (and known issues).


<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to be learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request



<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE` for more information.



<!-- CONTACT -->
## Contact

* **Claudia Ferreira** - 180181@virtualwindow.co.za
* **Project Link** - https://github.com/ClaudiaAF/Sprecan.git



<!-- ACKNOWLEDGEMENTS -->
## Acknowledgements

* [Kotlin documentation](https://kotlinlang.org/docs/home.html)
* [Firebase documentation](https://firebase.google.com/docs/reference)
* [Armand Pretorius](https://github.com/ArmandPretorius)
* [Background image in sign in/sign up page](https://media.sciencephoto.com/c0/38/76/05/c0387605-800px-wm.jpg)





<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/github_username/repo.svg?style=for-the-badge
[contributors-url]: https://github.com/github_username/repo/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/github_username/repo.svg?style=for-the-badge
[forks-url]: https://github.com/github_username/repo/network/members
[stars-shield]: https://img.shields.io/github/stars/github_username/repo.svg?style=for-the-badge
[stars-url]: https://github.com/github_username/repo/stargazers
[issues-shield]: https://img.shields.io/github/issues/github_username/repo.svg?style=for-the-badge
[issues-url]: https://github.com/github_username/repo/issues
[license-shield]: https://img.shields.io/github/license/github_username/repo.svg?style=for-the-badge
[license-url]: https://github.com/github_username/repo/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=d0ac4b
[linkedin-url]: https://linkedin.com/in/ClaudiaAF
