# Heart to Heart for iOS

## Introduction
**Heart to Heart** is a SNS application. This app is similar to Facebook. You can also download Heart to Heart for Android [here](https://github.com/yologger/heart_to_heart_android). Heart to heart is based on **MVVM** with **Clean Architecture**.

## Screenshots
<img src="/imgs/1.jpg" width="200">
<img src="/imgs/create_post.gif" width="200">
<img src="/imgs/theme.gif" width="200">
<img src="/imgs/update.gif" width="200">

## Prerequisite
You have to set up server-side application to run this. You can download [here](https://github.com/yologger/heart_to_heart_server).

## Dependencies
Heart to Heart for iOS has following dependencies:
* Carthage(Build Tool)
* Swift 5.0
* [Swinject](https://github.com/Swinject/Swinject) for DI
* [SwinjectAutoregistration](https://github.com/Swinject/SwinjectAutoregistration) for DI
* [RxSwift](https://github.com/ReactiveX/RxSwift)
* [Alamofire](https://github.com/Alamofire/Alamofire)
* [AlamofireImage](https://github.com/Alamofire/AlamofireImage)
* [Kingfisher](https://github.com/onevcat/Kingfisher)
* [SnapKit](https://github.com/SnapKit/SnapKit)
* [ObjectMapper](https://github.com/tristanhimmelman/ObjectMapper)
* [SwiftyJSON](https://github.com/SwiftyJSON/SwiftyJSON)
* [TLPhotoPicker](https://github.com/tilltue/TLPhotoPicker)
* [ImageSlideshow](https://github.com/zvonicek/ImageSlideshow)
* [XLPagerTabStrip](https://github.com/xmartlabs/XLPagerTabStrip)


## Features
#### `Implemented`
* Sign up
* Authorization (OAuth 2.0)
* Create new post
    - Choose and upload multiple images
* Show all posts
    - Infinite scrolling
* Upload avatar image

#### `Not Implemented Yet`
* Dark theme
* TextField validation
* Search
* Follow other users
* Like post
* Find password
* Change password
